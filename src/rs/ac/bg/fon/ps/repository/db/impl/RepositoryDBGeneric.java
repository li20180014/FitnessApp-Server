/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.ps.repository.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import rs.ac.bg.fon.ps.domain.OpstiDomenskiObjekat;
import rs.ac.bg.fon.ps.repository.db.DBConnectionFactory;
import rs.ac.bg.fon.ps.repository.db.DBRepository;

/**
 *
 * @author Korisnik
 */
public class RepositoryDBGeneric implements DBRepository<OpstiDomenskiObjekat> {

    @Override
    public void add(OpstiDomenskiObjekat param) throws Exception {
        try {
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ")
                    .append(param.getTableName())
                    .append(" (").append(param.getColumnNamesForInsert()).append(")")
                    .append(" VALUES (")
                    .append(param.getInsertValues())
                    .append(")");
            String query = sb.toString();
            System.out.println(query);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Greska prilikom dodavanja u bazu!");
        }
    }

    @Override
    public void edit(OpstiDomenskiObjekat entity) throws Exception {
        try {
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("UPDATE ")
                    .append(entity.getTableName()).append(" SET ")
                    .append(entity.getUpdateValues(entity))
                    .append(" WHERE ")
                    .append(entity.getUpdateString());
            String query = sb.toString();
            System.out.println(query);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Greska prilikom izmene objekata u bazi!");
        }
    }

    @Override
    public void delete(OpstiDomenskiObjekat entity) throws Exception {
        try {
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM ")
                    .append(entity.getTableName())
                    .append(" WHERE ")
                    .append(entity.getDeleteString());
            String query = sb.toString();
            System.out.println(query);
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Greska prilikom brisanja iz baze!");
        }
    }

    @Override
    public List<OpstiDomenskiObjekat> getAll(OpstiDomenskiObjekat param) throws Exception {
        try {
            String query = "SELECT * from " + param.getTableName();
            Connection connection = DBConnectionFactory.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            return param.readList(rs);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Greska prilikom ucitavanja objekata iz baze!");
        }
    }

    @Override
    public int addReturnKey(OpstiDomenskiObjekat param) throws Exception {

        try {
            Connection connection = DBConnectionFactory.getInstance().getConnection();
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO ")
                    .append(param.getTableName())
                    .append(" (").append(param.getColumnNamesForInsert()).append(")")
                    .append(" VALUES (")
                    .append(param.getInsertValues())
                    .append(")");
            String query = sb.toString();
            System.out.println(query);
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();

            ResultSet rsKey = statement.getGeneratedKeys();

            if (rsKey.next()) {
                int id = rsKey.getInt(1);
                rsKey.close();
                statement.close();

                return id;
            }

            throw new Exception();

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Greska prilikom dodavanja u bazu!");
        }
    }

    @Override
    public List<OpstiDomenskiObjekat> getAllLeftJoin(OpstiDomenskiObjekat first, OpstiDomenskiObjekat second) throws Exception {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ")
                    .append(first.getTableName()).append(" ").append(first.getAlias())
                    .append(" JOIN ").append(second.getTableName()).append(" ").append(second.getAlias())
                    .append(" ON ").append(first.getAlias()).append(".").append(first.getForeignKey())
                    .append(" = ").append(second.getAlias()).append(".").append(second.getPrimaryKey());

            String query = sb.toString();
            System.out.println(query);
            Connection connection = DBConnectionFactory.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            return first.readList(rs);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Greska prilikom ucitavanja objekata iz baze!");
        }
    }

    @Override
    public List<OpstiDomenskiObjekat> getAll(OpstiDomenskiObjekat param, String where) throws Exception {
        try {
            String sql = "select * from " + param.getTableName() + " where " + where;

            Connection connection = DBConnectionFactory.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            return param.readList(rs);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Greska prilikom ucitavanja objekata iz baze!");
        }
    }

    @Override
    public List<OpstiDomenskiObjekat> getTwoJoinsGroupBy(OpstiDomenskiObjekat first, OpstiDomenskiObjekat second, OpstiDomenskiObjekat third, String where) throws Exception {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ")
                    .append(first.getTableName()).append(" ").append(first.getAlias())
                    .append(" JOIN ").append(second.getTableName()).append(" ").append(second.getAlias())
                    .append(" ON ").append(first.getAlias()).append(".").append(first.getForeignKey())
                    .append(" = ").append(second.getAlias()).append(".").append(second.getPrimaryKey())
                    .append(" JOIN ").append(third.getTableName()).append(" ").append(third.getAlias())
                    .append(" ON ").append(first.getAlias()).append(".").append(first.getSecondForeignKey())
                    .append(" = ").append(third.getAlias()).append(".").append(third.getPrimaryKey())
                    .append(" WHERE ").append(where);

            String query = sb.toString();
            System.out.println(query);
            Connection connection = DBConnectionFactory.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            return first.readList(rs);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Greska prilikom ucitavanja objekata iz baze!");
        }
    }

    @Override
    public List<OpstiDomenskiObjekat> getAllLeftJoin(OpstiDomenskiObjekat first, OpstiDomenskiObjekat second, String where) throws Exception {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT * FROM ")
                    .append(first.getTableName()).append(" ").append(first.getAlias())
                    .append(" JOIN ").append(second.getTableName()).append(" ").append(second.getAlias())
                    .append(" ON ").append(first.getAlias()).append(".").append(first.getForeignKey())
                    .append(" = ").append(second.getAlias()).append(".").append(second.getPrimaryKey())
                    .append(" WHERE ").append(where);

            String query = sb.toString();
            System.out.println(query);
            Connection connection = DBConnectionFactory.getInstance().getConnection();

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            return first.readList(rs);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Greska prilikom ucitavanja objekata iz baze!");
        }
    }

}
