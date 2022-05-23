/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operation.programrada;

import rs.ac.bg.fon.ps.domain.Aktivnosti;
import rs.ac.bg.fon.ps.domain.ProgramRada;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

/**
 *
 * @author Korisnik
 */
public class ZapamtiProgramRada extends AbstractGenericOperation {

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof ProgramRada)) {
            throw new Exception("Pogresno uneti podaci za Program Rada");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        int id = repository.addReturnKey((ProgramRada) param);

        ProgramRada pr = (ProgramRada) param;
        pr.setId(id);
        
        for (Aktivnosti a : pr.getListaAktivnosti()) {
            a.setProgram(pr);
            repository.add(a);
        }

    }

}
