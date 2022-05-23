/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.ps.repository;

import java.util.List;

/**
 *
 * @author Korisnik
 */
public interface Repository<T> {

    void add(T param) throws Exception;

    void edit(T param) throws Exception;

    void delete(T param) throws Exception;
    
    List<T> getAll(T param) throws Exception;
    
    int addReturnKey(T param) throws Exception;
    
     List<T> getAllLeftJoin(T first, T second) throws Exception;
     
     List<T> getAll(T param, String where) throws Exception;
     
    List<T> getTwoJoinsGroupBy(T first, T second, T third, String where) throws Exception;
    
    List<T> getAllLeftJoin(T first, T second, String where) throws Exception;
}
