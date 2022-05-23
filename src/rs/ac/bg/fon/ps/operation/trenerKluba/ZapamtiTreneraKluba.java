/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operation.trenerKluba;

import java.util.List;
import rs.ac.bg.fon.ps.domain.OpstiDomenskiObjekat;
import rs.ac.bg.fon.ps.domain.TrenerKluba;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

/**
 *
 * @author Korisnik
 */
public class ZapamtiTreneraKluba extends AbstractGenericOperation {

    @Override
    protected void preconditions(Object param) throws Exception {
        
         if (param == null || !(param instanceof TrenerKluba)) {
            throw new Exception("Pogresno uneti podaci za Trenera!");
        }
        TrenerKluba novi = (TrenerKluba) param;
        List<OpstiDomenskiObjekat> treneri = repository.getAll(param);
        for (OpstiDomenskiObjekat odo : treneri) {
            TrenerKluba postojeci = (TrenerKluba) odo;
            if (postojeci.getUsername().equals(novi.getUsername())) {
                throw new Exception("Trener vec postoji u sistemu!");
            }
        }
       
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        
        TrenerKluba trener = (TrenerKluba) param;      
        repository.add(trener);
    }

}
