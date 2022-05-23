/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.repository.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ps.properties.DBProperties;

/**
 *
 * @author Korisnik
 */
public class DBConnectionFactory {

    private static DBConnectionFactory instance;
    private Connection connection;
    private String url, username, password;

    public static DBConnectionFactory getInstance() {
        if (instance == null) {
            instance = new DBConnectionFactory();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException{
        if (connection == null || connection.isClosed()) {
            readConfigProperties();     
            //String url= "jdbc:mysql://localhost:3306/seminarskiteretana";
            connection = DriverManager.getConnection(url, username, password);
            connection.setAutoCommit(false);
        }
        return connection;
    }

    private void readConfigProperties() {
      
            DBProperties properties = new DBProperties();
            url = properties.getURL();
            username = properties.getUsername();
            password = properties.getPassword();
            
    }
}
