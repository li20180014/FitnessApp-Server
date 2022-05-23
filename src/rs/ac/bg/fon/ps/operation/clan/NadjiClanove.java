/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operation.clan;

import java.util.List;
import rs.ac.bg.fon.ps.domain.Clan;
import rs.ac.bg.fon.ps.domain.OpstiDomenskiObjekat;
import rs.ac.bg.fon.ps.domain.ProgramRada;
import rs.ac.bg.fon.ps.domain.TreningGrupa;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

/**
 *
 * @author Korisnik
 */
public class NadjiClanove extends AbstractGenericOperation {

    private List<OpstiDomenskiObjekat> list;
    private Object param2;
  

    public NadjiClanove(Object param2) {
        this.param2 = param2;
    }

    public List<OpstiDomenskiObjekat> getList() {
        return list;
    }

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Clan)) {
            throw new Exception("Pogresno uneti podaci za Clana");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        list = repository.getAllLeftJoin((Clan) param, (TreningGrupa) param2);
    }

}
