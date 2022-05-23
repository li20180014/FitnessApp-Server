/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operation.zaduzenje;


import java.util.List;
import rs.ac.bg.fon.ps.domain.OpstiDomenskiObjekat;
import rs.ac.bg.fon.ps.domain.TrenerKluba;
import rs.ac.bg.fon.ps.domain.TreningGrupa;
import rs.ac.bg.fon.ps.domain.Zaduzenje;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

/**
 *
 * @author Korisnik
 */
public class UcitajZaduzenja extends AbstractGenericOperation {
    
    private List<OpstiDomenskiObjekat> list;
    String where;
    
    public List<OpstiDomenskiObjekat> getList() {
        return list;
    }

    public UcitajZaduzenja(int uslov) {
        where = "z.grupaId ="+uslov;
    }
    
  
    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Zaduzenje)) {
            throw new Exception("Pogresno uneti podaci za Zaduzenja");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        list = repository.getTwoJoinsGroupBy((Zaduzenje)param, new TrenerKluba(), new TreningGrupa(), where);
    }

}
