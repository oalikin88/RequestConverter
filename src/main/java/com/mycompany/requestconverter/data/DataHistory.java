/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.requestconverter.data;

import java.util.Date;





/**
 *
 * @author 041AlikinOS
 */
public class DataHistory {
    
    private int id;
    private String action;
    private Date date;

    public DataHistory() {
    }

    public DataHistory(String action, Date date) {
        this.action = action;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getAction() {
        return action;
    }

    public Date getDate() {
        return date;
    }

    
    

    public void setId(int id) {
        this.id = id;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    

    @Override
    public String toString() {
        
        return "DataHistory{" + "id=" + id + ", action=" + action + ", date=" + date + '}' + "\n";
    }
    
    
}
