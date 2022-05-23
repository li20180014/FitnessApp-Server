/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operation.treningGrupa;

import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ps.domain.TrenerKluba;
import rs.ac.bg.fon.ps.domain.TreningGrupa;
import rs.ac.bg.fon.ps.domain.Zaduzenje;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

/**
 *
 * @author Korisnik
 */
public class ZapamtiTreningGrupu extends AbstractGenericOperation {
    

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof TreningGrupa)) {
            throw new Exception("Pogresno uneti podaci za Trening Grupu");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        TreningGrupa tg = (TreningGrupa) param;
        int id = repository.addReturnKey(tg);
        tg.setGrupaId(id);
        List<TrenerKluba> treneri = tg.getZaduzeniTreneri();
        List<Zaduzenje> zaduzenja = new ArrayList<>();
        for (TrenerKluba trenerKluba : treneri) {
            Zaduzenje z = new Zaduzenje(trenerKluba, tg);
            zaduzenja.add(z);
        }
        for (Zaduzenje zaduzenje : zaduzenja) {
            zaduzenje.getTreningGrupa().setGrupaId(id);
            repository.add(zaduzenje);
        }
        
        
    }



    
}
