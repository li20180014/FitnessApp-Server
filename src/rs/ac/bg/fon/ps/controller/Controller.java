/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ps.domain.Clan;
import rs.ac.bg.fon.ps.domain.OpstiDomenskiObjekat;
import rs.ac.bg.fon.ps.domain.ProgramRada;
import rs.ac.bg.fon.ps.domain.TrenerKluba;
import rs.ac.bg.fon.ps.domain.TreningGrupa;
import rs.ac.bg.fon.ps.domain.Zaduzenje;
import rs.ac.bg.fon.ps.model.TableModelUsers;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;
import rs.ac.bg.fon.ps.operation.clan.IzmeniClana;
import rs.ac.bg.fon.ps.operation.clan.UcitajClana;
import rs.ac.bg.fon.ps.operation.clan.ObrisiClana;
import rs.ac.bg.fon.ps.operation.clan.NadjiClanove;
import rs.ac.bg.fon.ps.operation.clan.ZapamtiClana;
import rs.ac.bg.fon.ps.operation.programrada.UcitajListuProgramaRada;
import rs.ac.bg.fon.ps.operation.programrada.ZapamtiProgramRada;
import rs.ac.bg.fon.ps.operation.trenerKluba.PrijaviTreneraKluba;
import rs.ac.bg.fon.ps.operation.trenerKluba.UcitajTrenere;
import rs.ac.bg.fon.ps.operation.trenerKluba.ZapamtiTreneraKluba;
import rs.ac.bg.fon.ps.operation.treningGrupa.IzmeniTreningGrupu;
import rs.ac.bg.fon.ps.operation.treningGrupa.UcitajTreningGrupu;
import rs.ac.bg.fon.ps.operation.treningGrupa.NadjiTreningGrupe;
import rs.ac.bg.fon.ps.operation.treningGrupa.ZapamtiTreningGrupu;
import rs.ac.bg.fon.ps.operation.zaduzenje.UcitajZaduzenja;
import rs.ac.bg.fon.ps.view.form.FrmAktivniKorisnici;

import rs.ac.bg.fon.ps.view.form.FrmServer;

/**
 *
 * @author Korisnik
 */
public class Controller {

    private static Controller instance;
    private FrmAktivniKorisnici frmAktivniKorisnici;
    private List<TrenerKluba> activeUsers;

    private Controller() {
        activeUsers = new ArrayList<>();
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void setFrmAktivni(FrmAktivniKorisnici frmAktivniKorisnici) {
        this.frmAktivniKorisnici = frmAktivniKorisnici;

    }

    public FrmAktivniKorisnici getFrmAktivni() {
        return frmAktivniKorisnici;
    }

    public void addUser(TrenerKluba tk) {
        tk.setDatumLogovanja(new Date());
        activeUsers.add(tk);
        if (frmAktivniKorisnici != null) {
            frmAktivniKorisnici.getTmu().dodajKorisnika(tk);
        }

    }

    public void removeUser(TrenerKluba tk) {
        activeUsers.remove(tk);
        if (frmAktivniKorisnici != null) {
            frmAktivniKorisnici.getTmu().removeUser(tk);
        }
    }

    public List<TrenerKluba> getActiveUsers() {
        return activeUsers;
    }

    public List<TrenerKluba> getUserList() {
        AbstractGenericOperation operation = new UcitajTrenere();
        try {
            operation.execute(new TrenerKluba());
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        List<OpstiDomenskiObjekat> treneri = ((UcitajTrenere) operation).getList();
        List<TrenerKluba> tk = new ArrayList<>();
        for (OpstiDomenskiObjekat odo : treneri) {
            TrenerKluba t = (TrenerKluba) odo;
            tk.add(t);
        }
        return tk;
    }

    public TrenerKluba prijaviTreneraKluba(String username, String password) throws Exception {
        AbstractGenericOperation operation = new PrijaviTreneraKluba(username, password);
        operation.execute(new TrenerKluba());
        return ((PrijaviTreneraKluba) operation).getTrenerKluba();
    }

    public boolean zapamtiTreneraKluba(TrenerKluba trenerKluba) throws Exception {
        AbstractGenericOperation operation2 = new ZapamtiTreneraKluba();
        operation2.execute(trenerKluba);
        if (frmAktivniKorisnici != null) {
        frmAktivniKorisnici.getTmu().dodajUListuPostojecih(trenerKluba);
        }
        return true;
    }

    public boolean zapamtiProgramRada(ProgramRada pr) throws Exception {
        AbstractGenericOperation operation3 = new ZapamtiProgramRada();
        operation3.execute(pr);
        return true;
    }

    public List<OpstiDomenskiObjekat> getProgramRada(ProgramRada programRada) throws Exception {
        AbstractGenericOperation operation4 = new UcitajListuProgramaRada();
        operation4.execute(programRada);
        return ((UcitajListuProgramaRada) operation4).getList();
    }

    public boolean zapamtiTreningGrupu(TreningGrupa tg) throws Exception {
        AbstractGenericOperation operation5 = new ZapamtiTreningGrupu();
        operation5.execute(tg);
        return true;
    }

    public List<OpstiDomenskiObjekat> getTreningGrupe(TreningGrupa treningGrupa) throws Exception {
        AbstractGenericOperation operation6 = new NadjiTreningGrupe(new ProgramRada());
        operation6.execute(treningGrupa);
        return ((NadjiTreningGrupe) operation6).getList();
    }

    public boolean izmeniTreningGrupu(TreningGrupa tg) throws Exception {
        AbstractGenericOperation operation7 = new IzmeniTreningGrupu();
        operation7.execute(tg);
        return true;
    }

    public boolean zapamtiClana(Clan clan) throws Exception {
        AbstractGenericOperation operation2 = new ZapamtiClana();
        operation2.execute(clan);
        return true;
    }

    public List<OpstiDomenskiObjekat> getClanovi(Clan clan) throws Exception {
        AbstractGenericOperation operation = new NadjiClanove(new TreningGrupa());
        operation.execute(clan);
        return ((NadjiClanove) operation).getList();
    }

    public boolean obrisiClana(Clan clan) throws Exception {
        AbstractGenericOperation operation = new ObrisiClana();
        operation.execute(clan);
        return true;
    }

    public List<OpstiDomenskiObjekat> getClanoviPoUslovu(String uslov) throws Exception {
        AbstractGenericOperation operation = new UcitajClana(uslov);
        operation.execute(new Clan());
        return ((UcitajClana) operation).getList();
    }

    public List<OpstiDomenskiObjekat> ucitajTrenere(TrenerKluba trenerKluba) throws Exception {
        AbstractGenericOperation operation = new UcitajTrenere();
        operation.execute(trenerKluba);
        return ((UcitajTrenere) operation).getList();
    }

    public boolean izmeniClana(Clan c) throws Exception {
        AbstractGenericOperation operation7 = new IzmeniClana();
        operation7.execute(c);
        return true;
    }

    public List<OpstiDomenskiObjekat> getGrupePoUslovu(String uslov) throws Exception {
        AbstractGenericOperation operation = new UcitajTreningGrupu(uslov);
        operation.execute(new TreningGrupa());
        return ((UcitajTreningGrupu) operation).getList();
    }

    public List<OpstiDomenskiObjekat> getZaduzenjaPoUslovu(int grupaId) throws Exception {
        AbstractGenericOperation operation = new UcitajZaduzenja(grupaId);
        operation.execute(new Zaduzenje());
        return ((UcitajZaduzenja) operation).getList();
    }

}
