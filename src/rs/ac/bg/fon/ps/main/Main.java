/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import rs.ac.bg.fon.ps.repository.db.DBConnectionFactory;
import rs.ac.bg.fon.ps.view.form.FrmServer;

/**
 *
 * @author Korisnik
 */
public class Main {

    public static void main(String[] args) {

        FrmServer frmServer = new FrmServer();
        frmServer.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //frmServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frmServer.setUndecorated(true);
        frmServer.setVisible(true);
    }
}
