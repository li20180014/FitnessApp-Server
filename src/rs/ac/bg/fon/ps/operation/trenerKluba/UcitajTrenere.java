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
public class UcitajTrenere extends AbstractGenericOperation {

    private List<OpstiDomenskiObjekat> list;

    public List<OpstiDomenskiObjekat> getList() {
        return list;
    }

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof TrenerKluba)) {
            throw new Exception("Pogresno uneti podaci za Trenera!");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        list = repository.getAll(param);
    }

}
