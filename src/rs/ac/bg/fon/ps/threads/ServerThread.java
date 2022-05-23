/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.threads;

import rs.ac.bg.fon.ps.properties.DBProperties;
import rs.ac.bg.fon.ps.view.form.FrmServer;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import rs.ac.bg.fon.ps.domain.TrenerKluba;

/**
 *
 * @author Korisnik
 */
public class ServerThread extends Thread {

    static boolean end = true;
    ServerSocket serverSocket;
    List<HandleClientThread> clients;
    

    public ServerThread() {
        clients = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(9000);
            while (!end) {
                System.out.println("Waiting for connection...");
                Socket socket = serverSocket.accept();
                System.out.println("Connected!");
                HandleClientThread clientThread = new HandleClientThread(this, socket);
                clients.add(clientThread);
                clientThread.start();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void stopServer() {
        try {
            end = true;
            for (HandleClientThread clientThread : clients) {
                clientThread.interrupt();
                clientThread.getSocket().close();
            }
            serverSocket.close();
            clients = new ArrayList<>();
            System.out.println("Server closed!");
        } catch (Exception ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setEnd(boolean end) {
        ServerThread.end = end;
    }

    public static boolean getEnd() {
        return end;
    }
}
