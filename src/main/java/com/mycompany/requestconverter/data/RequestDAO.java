/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.requestconverter.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 041AlikinOS
 */
public class RequestDAO {
    
     protected Connection connection = null;

    public RequestDAO(Connection connection) {
        this.connection = connection;
    }
    
    
    public List<Request> readAllRequest() {
        
        List <Request> list = new ArrayList<>();
        
        
        return null;
    }
    
    
}
