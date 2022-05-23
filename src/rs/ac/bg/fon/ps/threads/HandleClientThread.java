/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.threads;

import rs.ac.bg.fon.ps.transfer.Request;
import rs.ac.bg.fon.ps.transfer.Response;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ps.controller.Controller;
import rs.ac.bg.fon.ps.domain.Clan;
import rs.ac.bg.fon.ps.domain.OpstiDomenskiObjekat;
import rs.ac.bg.fon.ps.domain.ProgramRada;
import rs.ac.bg.fon.ps.domain.TrenerKluba;
import rs.ac.bg.fon.ps.domain.TreningGrupa;
import rs.ac.bg.fon.ps.domain.Zaduzenje;
import rs.ac.bg.fon.ps.operations.Operation;
import rs.ac.bg.fon.ps.transfer.Receiver;
import rs.ac.bg.fon.ps.transfer.Sender;

/**
 *
 * @author Korisnik
 */
public class HandleClientThread extends Thread {

    ServerThread serverThread;
    Socket socket;
    Sender sender;
    Receiver receiver;
    TrenerKluba tk;

    HandleClientThread(ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
        sender = new Sender(socket);
        receiver = new Receiver(socket);
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {

        try {
            while (!serverThread.getEnd()) {
                Request request = (Request) receiver.receive();
                Response response = new Response();
                try {
                    obradiOdgovor(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                    response.setException(e);
                }
                sender.send(response);
            }
        } catch (Exception ex) {
            System.out.println("Korisnik " + tk.getIme() + " se izlogovao!");
            Controller.getInstance().removeUser(tk);
        }

    }

    private void obradiOdgovor(Request request, Response response) throws Exception {

        switch (request.getOperation()) {

            case Operation.LOGIN:
                login(request, response);
                break;
            case Operation.ZAPAMTI_TRENERA_KLUBA:
                zapamtiTreneraKluba(request, response);
                break;
            case Operation.ZAPAMTI_PROGRAM_RADA:
                zapamtiProgramRada(request, response);
                break;
            case Operation.VRATI_PROGRAME_RADA:
                vratiProgrameRada(request, response);
                break;
            case Operation.ZAPAMTI_TRENING_GRUPU:
                zapamtiTreningGrupu(request, response);
                break;
            case Operation.UCITAJ_TRENING_GRUPU:
                vratiTreningGrupe(request, response);
                break;
            case Operation.IZMENI_TRENING_GRUPU:
                izmeniTreningGrupu(request, response);
                break;
            case Operation.ZAPAMTI_CLANA:
                zapamtiClana(request, response);
                break;
            case Operation.NADJI_CLANOVE:
                ucitajClanove(request, response);
                break;
            case Operation.OBRISI_CLANA:
                obrisiClana(request, response);
                break;
            case Operation.UCITAJ_CLANA:
                nadjiClanaPoUslovu(request, response);
                break;
            case Operation.UCITAJ_TRENERE:
                ucitajTrenere(request, response);
                break;
            case Operation.IZMENI_CLANA:
                izmeniClana(request, response);
                break;
            case Operation.NADJI_TRENING_GRUPE:
                nadjiGrupu(request, response);
                break;
            case Operation.UCITAJ_ZADUZENJA:
                ucitajZaduzenja(request, response);
                break;
            default:
                System.out.println("Invalid operation");
                break;
        }
    }

    private void login(Request request, Response response) throws Exception {
        TrenerKluba trenerKluba = (TrenerKluba) request.getArgument();
        TrenerKluba active = Controller.getInstance().prijaviTreneraKluba(trenerKluba.getUsername(), trenerKluba.getPassword());
        response.setResult(active);
        this.tk = active;
        Controller.getInstance().addUser(tk);

    }

    private void zapamtiTreneraKluba(Request request, Response response) throws Exception {
        TrenerKluba trenerKluba = (TrenerKluba) request.getArgument();
        boolean uspesno = Controller.getInstance().zapamtiTreneraKluba(trenerKluba);
        response.setResult(uspesno);
    }

    private void zapamtiProgramRada(Request request, Response response) throws IOException, Exception {
        ProgramRada pr = (ProgramRada) request.getArgument();
        boolean result = Controller.getInstance().zapamtiProgramRada(pr);
        response.setResult(result);
    }

    private void vratiProgrameRada(Request request, Response response) throws IOException, Exception {
        List<OpstiDomenskiObjekat> programi = Controller.getInstance().getProgramRada(new ProgramRada());
        response.setResult(programi);
    }

    private void zapamtiTreningGrupu(Request request, Response response) throws IOException, Exception {
        TreningGrupa tg  =  (TreningGrupa) request.getArgument();
        boolean result = Controller.getInstance().zapamtiTreningGrupu(tg);
        response.setResult(result);
    }

    private void vratiTreningGrupe(Request request, Response response) throws IOException, Exception {
        List<OpstiDomenskiObjekat> grupe = Controller.getInstance().getTreningGrupe(new TreningGrupa());
        response.setResult(grupe);
    }

    private void izmeniTreningGrupu(Request request, Response response) throws IOException, Exception {
       TreningGrupa tg = (TreningGrupa) request.getArgument();
        boolean result = Controller.getInstance().izmeniTreningGrupu(tg);
        response.setResult(result);
    }

    private void zapamtiClana(Request request, Response response) throws Exception {
        Clan clan = (Clan) request.getArgument();
        boolean result = Controller.getInstance().zapamtiClana(clan);
        response.setResult(result);
    }

    private void ucitajClanove(Request request, Response response) throws Exception {
        List<OpstiDomenskiObjekat> clanovi = Controller.getInstance().getClanovi(new Clan());
        response.setResult(clanovi);
    }

    private void obrisiClana(Request request, Response response) throws Exception {
        Clan clan = (Clan) request.getArgument();
        boolean result = Controller.getInstance().obrisiClana(clan);
        response.setResult(result);
    }

    private void nadjiClanaPoUslovu(Request request, Response response) throws Exception {
        String uslov = (String) request.getArgument();
        List<OpstiDomenskiObjekat> clanovi = Controller.getInstance().getClanoviPoUslovu(uslov);
        response.setResult(clanovi);
    }

    private void ucitajTrenere(Request request, Response response) throws Exception {
        List<OpstiDomenskiObjekat> treneri = Controller.getInstance().ucitajTrenere(new TrenerKluba());
        response.setResult(treneri);
    }

    private void izmeniClana(Request request, Response response) throws Exception {
        Clan c = (Clan) request.getArgument();
        boolean result = Controller.getInstance().izmeniClana(c);
        response.setResult(result);
    }

    private void nadjiGrupu(Request request, Response response) throws Exception {
        String uslov = (String) request.getArgument();
        List<OpstiDomenskiObjekat> grupe = Controller.getInstance().getGrupePoUslovu(uslov);
        response.setResult(grupe);
    }

    private void ucitajZaduzenja(Request request, Response response) throws Exception {
        TreningGrupa tg = (TreningGrupa) request.getArgument();
        List<OpstiDomenskiObjekat> zaduzenja = Controller.getInstance().getZaduzenjaPoUslovu(tg.getGrupaId());
        response.setResult(zaduzenja);
    }

}
