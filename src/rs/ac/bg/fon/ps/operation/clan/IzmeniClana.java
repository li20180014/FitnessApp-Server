/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.operation.clan;

import rs.ac.bg.fon.ps.domain.Clan;
import rs.ac.bg.fon.ps.operation.AbstractGenericOperation;

/**
 *
 * @author Korisnik
 */
public class IzmeniClana extends AbstractGenericOperation {

    @Override
    protected void preconditions(Object param) throws Exception {
        if (param == null || !(param instanceof Clan)) {
            throw new Exception("Pogresno uneti podaci za Clana");
        }
    }

    @Override
    protected void executeOperation(Object param) throws Exception {
        repository.edit((Clan) param);
    }

}
