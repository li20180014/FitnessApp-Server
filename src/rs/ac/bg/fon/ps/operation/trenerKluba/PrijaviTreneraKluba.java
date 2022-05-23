/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operation.trenerKluba;

import java.util.List;
import rs.ac.bg.fon.ps.controller.Controller;
import rs.ac.bg.fon.ps.domain.OpstiDomenskiObjekat;
import rs.ac.bg.fon.ps.domain.TrenerKluba;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

/**
 *
 * @author Korisnik
 */
public class PrijaviTreneraKluba extends AbstractGenericOperation {

    private List<OpstiDomenskiObjekat> list;
    String username;
    String password;

    public PrijaviTreneraKluba(String username, String password) {
        this.username=username;
        this.password=password;
    }
    
    public TrenerKluba getTrenerKluba() throws Exception{
        TrenerKluba tk=null;
        for (OpstiDomenskiObjekat odo : list) {
            TrenerKluba trener = (TrenerKluba) odo;
            if (trener.getUsername().equals(username) && trener.getPassword().equals(password)) {
                tk = trener;
            }
        }
        if (tk==null) {
          throw new Exception("Trener ne postoji!");
        }
        return tk;
    }
    
    @Override
    protected void preconditions(Object param) throws Exception {
        List<TrenerKluba> activeUsers = Controller.getInstance().getActiveUsers();
        for (TrenerKluba activeUser : activeUsers) {
            if (activeUser.getUsername().equals(username) && activeUser.getPassword().equals(password)) {
                throw new Exception("Korisnik je vec ulogovan!");
            }
        }
        
        if (param == null || !(param instanceof TrenerKluba)) {
            throw new Exception("Pogresno uneti podaci za Trenera!");
        }
        
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        list = repository.getAll(param);
    }

}
