/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operation.clan;

import java.util.List;
import rs.ac.bg.fon.ps.domain.Clan;
import rs.ac.bg.fon.ps.domain.OpstiDomenskiObjekat;
import rs.ac.bg.fon.ps.domain.TreningGrupa;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

/**
 *
 * @author Korisnik
 */
public class ZapamtiClana extends AbstractGenericOperation {

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Clan)) {
            throw new Exception("Pogresno uneti podaci za Clana");
        }
        
        Clan novi = (Clan) param;
        List<OpstiDomenskiObjekat> clanovi = repository.getAllLeftJoin((Clan) param, new TreningGrupa());;

        if (!clanovi.isEmpty()) {
            for (OpstiDomenskiObjekat odo : clanovi) {
                Clan c = (Clan) odo;
                if (c.getEmail().equals(novi.getEmail())) {
                    throw new Exception("Clan vec postoji u sistemu!");
                }
            }
        }

    }

    @Override
    protected void executeOperation(Object param) throws Exception {

        Clan c = (Clan) param;
        repository.add(c);
    }

}
