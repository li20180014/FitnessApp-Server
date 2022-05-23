/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.model;

import java.text.SimpleDateFormat;
import rs.ac.bg.fon.ps.domain.TrenerKluba;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ps.controller.Controller;

/**
 *
 * @author Korisnik
 */
public class TableModelUsers extends AbstractTableModel {

    List<TrenerKluba> postojeciTreneri;
    List<TrenerKluba> activeUsers = Controller.getInstance().getActiveUsers();
    String[] kolone = new String[]{"Ime", "Prezime", "Aktivan", "Datum poslednjeg pristupa"};

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY HH:mm:ss");

    public TableModelUsers() {
        postojeciTreneri = Controller.getInstance().getUserList();
    }

    @Override
    public int getRowCount() {
        return postojeciTreneri.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        TrenerKluba trener = postojeciTreneri.get(rowIndex);
        switch (columnIndex) {
            
            case 0:
                return trener.getIme();
            case 1:
                return trener.getPrezime();
            case 2:
                return daLiJeAktivan(trener);
            case 3:
                return vratiDatum(trener);

            default:
                return "";
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    public void dodajKorisnika(TrenerKluba tk) {
        activeUsers.add(tk);
        fireTableDataChanged();
    }
     public void dodajUListuPostojecih(TrenerKluba tk) {
        postojeciTreneri.add(tk);
        fireTableDataChanged();
    }

    public void removeUser(TrenerKluba tk) {
        activeUsers.remove(tk);
        fireTableDataChanged();
    }

    private String daLiJeAktivan(TrenerKluba trener) {
        if (activeUsers.isEmpty()) {
            return "Inactive";
        }
        for (TrenerKluba trenerKluba : activeUsers) {
            if (trenerKluba.equals(trener)) {
                return "Active";
            }
        }

        return "Inactive";
    }

    private String vratiDatum(TrenerKluba trener) {

        for (TrenerKluba activeUser : activeUsers) {
            if (activeUser.equals(trener)) {
                trener.setDatumLogovanja(activeUser.getDatumLogovanja());
                return sdf.format(trener.getDatumLogovanja());
            }
        }
        
        if (trener.getDatumLogovanja()!=null) {
            return sdf.format(trener.getDatumLogovanja());
        }

        return "Nije pristupao sistemu";
        
    }

}
