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
public class UcitajClana extends AbstractGenericOperation {

    String uslov;
    private List<OpstiDomenskiObjekat> list;
    private Object param2;
    private Object Clan;
    String where;
  

    public UcitajClana(String uslov) {
        this.where = "c.ime like '%"+uslov+"%'";
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
        list = repository.getAllLeftJoin((Clan)param, new TreningGrupa(), where);
    }

}
