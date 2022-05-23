/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.ps.repository.db;

import java.sql.SQLException;
import rs.ac.bg.fon.ps.repository.Repository;

/**
 *
 * @author Korisnik
 */
public interface DBRepository<T> extends Repository<T> {
     public default void connect() throws SQLException {
        DBConnectionFactory.getInstance().getConnection();
    }

    public default void disconnect() throws SQLException {
        DBConnectionFactory.getInstance().getConnection().close();
    }

    public default void commit() throws SQLException {
        DBConnectionFactory.getInstance().getConnection().commit();
    }
    
    public default void rollback() throws SQLException {
        DBConnectionFactory.getInstance().getConnection().rollback();
    }
}
