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
public class IzmeniTreningGrupu extends AbstractGenericOperation {

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof TreningGrupa)) {
            throw new Exception("Pogresno uneti podaci za Trening Grupu");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        TreningGrupa tg = (TreningGrupa) param;
        switch (tg.getStatus()) {
            case "NEPROMENJEN":
                repository.edit(tg);
                break;

            case "PROMENJEN":
                String where = "z.grupaId =" + tg.getGrupaId();
                List<Zaduzenje> postojecaZaduzenja = repository.getTwoJoinsGroupBy(new Zaduzenje(), new TrenerKluba(), new TreningGrupa(), where);
                List<Zaduzenje> novaZaduzenja = new ArrayList<>();
                List<TrenerKluba> treneri = tg.getZaduzeniTreneri();

                for (TrenerKluba trenerKluba : treneri) {
                    Zaduzenje z = new Zaduzenje(trenerKluba, tg);
                    novaZaduzenja.add(z);
                }
                List<Zaduzenje> obrisiStara = new ArrayList<>();
                List<Zaduzenje> dodajNova = new ArrayList<>();

                for (Zaduzenje novo : novaZaduzenja) {
                    if (!postojecaZaduzenja.contains(novo)) {
                        dodajNova.add(novo);
                    }
                }

                for (Zaduzenje staro : postojecaZaduzenja) {
                    if (!novaZaduzenja.contains(staro)) {
                        obrisiStara.add(staro);
                    }
                }

                if (!obrisiStara.isEmpty()) {
                    for (Zaduzenje zaduzenje : obrisiStara) {
                        repository.delete(zaduzenje);
                    }
                }

                if (!dodajNova.isEmpty()) {
                    for (Zaduzenje zaduzenje : dodajNova) {
                        repository.add(zaduzenje);
                    }
                }
                repository.edit(tg);
                break;
            default:
                break;
        }

    }

}
