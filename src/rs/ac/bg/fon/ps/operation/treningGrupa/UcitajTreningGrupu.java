/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operation.treningGrupa;

import java.util.List;
import rs.ac.bg.fon.ps.domain.OpstiDomenskiObjekat;
import rs.ac.bg.fon.ps.domain.ProgramRada;
import rs.ac.bg.fon.ps.domain.TreningGrupa;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

/**
 *
 * @author Korisnik
 */
public class UcitajTreningGrupu extends AbstractGenericOperation {

    String where;
    private List<OpstiDomenskiObjekat> list;

    public UcitajTreningGrupu(String uslov) {
        this.where = "tg.nazivGrupe like '%" + uslov + "%'";
    }

    public List<OpstiDomenskiObjekat> getList() {
        return list;
    }
    
    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof TreningGrupa)) {
            throw new Exception("Pogresno uneti podaci za Trening Grupu");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        list = repository.getAllLeftJoin((TreningGrupa) param, new ProgramRada(), where);
    }

}
