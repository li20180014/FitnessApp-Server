/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.ps.operation;

import java.sql.SQLException;
import rs.ac.bg.fon.ps.repository.Repository;
import rs.ac.bg.fon.ps.repository.db.DBRepository;
import rs.ac.bg.fon.ps.repository.db.impl.RepositoryDBGeneric;

/**
 *
 * @author Korisnik
 */
public abstract class AbstractGenericOperation {
    
    protected final Repository repository;

    public AbstractGenericOperation() {
        this.repository = new RepositoryDBGeneric();
    }
      
     public final void execute(Object param) throws Exception{
        try{
            preconditions(param);
            startTransaction();
            executeOperation(param);
            commitTransaction();
        } catch(Exception ex){
            ex.printStackTrace();
            rollbackTransaction();
            throw ex;
        }
        finally{
            disconnect();
        }
    }
     
    protected abstract void preconditions(Object param) throws Exception;

    private void startTransaction() throws SQLException{
        ((DBRepository)repository).connect();
    }

    protected abstract void executeOperation(Object param) throws Exception;

    private void commitTransaction() throws SQLException {
        ((DBRepository)repository).commit();
    }

    private void rollbackTransaction() throws SQLException {
        ((DBRepository)repository).rollback();
    }

    private void disconnect() throws SQLException {
        ((DBRepository)repository).disconnect();
    }
}
