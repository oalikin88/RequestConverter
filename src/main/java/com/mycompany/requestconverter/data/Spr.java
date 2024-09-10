/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.requestconverter.data;

import com.mycompany.requestconverter.service.Content;
import com.mycompany.requestconverter.service.ConvertList;
import com.mycompany.requestconverter.service.CustomListManipulation;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 041AlikinOS
 */
public class Spr {
//    private Set<Region> regions = new HashSet<>();
//    private Set<Department> departments = new HashSet<>();
//    private SprType sprType;
//    private ClientDAO clientDAO;
//    private Content content;
//    private static Map<String, String> SPR = new HashMap<>();
//    private  String[][] inputContent;
//    
//    public Spr(SprType sprType) {
//        this.sprType = sprType;
//        clientDAO = new ClientDAO();
//        content = new Content();
//        if(sprType.getTitle().equals("spr")) {
//            SPR.put(sprType.getTitle(), "Запросы пенсионно-социального характера");
//        } else {
//            SPR.put(sprType.getTitle(), "Запросы выплатных дел");
//        }
//    }
//    
//    public Set<Region> getRegions() {
//        try {
//            regions = clientDAO.findAllRegions();
//        } catch (IOException ex) {
//            Logger.getLogger(Spr.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (URISyntaxException ex) {
//            Logger.getLogger(Spr.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(Spr.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(Spr.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return regions;
//    }
//    
//    public Set<Record> getRecordsFromLocal() {
//      //  String request = this.sprType.getTitle();
//        List <String> contentSpr = new ArrayList<>();
//        content = new Content();
//        try {
//            contentSpr = content.getContentSpr(this.getSprType().getTitle());
//        } catch (IOException ex) {
//            Logger.getLogger(Spr.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (URISyntaxException ex) {
//            Logger.getLogger(Spr.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        inputContent = ConvertList.listToTwoArray(contentSpr);
//        List<Record> result = CustomListManipulation.getRecords(inputContent);
//        
//    return result;
//    }
//    
//    public List<Region> getRegionsFromDB() {
//        try {
//        records = clientDAO.findAllRecords(request);
//        } catch (IOException ex) {
//            Logger.getLogger(Spr.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (URISyntaxException ex) {
//            Logger.getLogger(Spr.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(Spr.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(Spr.class.getName()).log(Level.SEVERE, null, ex);
//        }    
//        return records;
//    }
//    
//    public List<Record> getRecords() {
//        return records;
//    }
//
//    public SprType getSprType() {
//        return sprType;
//    }
//
//    public String[][] getInputContent() {
//        return inputContent;
//    }
//    
//    public static Map<String, String> getSPR() {
//        return SPR;
//    }
//    
//    
//    public void writeSpr(List<Record> inputList) {
//        
//        try {
//            content.writeSprData(inputList, this.getSprType().getTitle());
//        } catch (URISyntaxException ex) {
//            Logger.getLogger(Spr.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//    }
//    
//    
//    public void writeHistory(List<DataHistory> inputList) {
//        
//        try {
//            content.writeSprHistory(inputList, this.getSprType().getTitle());
//        } catch (IOException ex) {
//            Logger.getLogger(Spr.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    
//    }
//    
//    public List<String> getHistory() {
//        List<String> sprHistoryContent = null;
//        try {
//            sprHistoryContent = content.getSprHistoryContent(this.sprType.getTitle());
//        } catch (IOException ex) {
//            Logger.getLogger(Spr.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (URISyntaxException ex) {
//            Logger.getLogger(Spr.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        return sprHistoryContent;
//    }
//    
//    public List<DataHistory> getLastChangeFromHistory() {
//    List<DataHistory> lastChangeFromSpr = null;
//        try {
//            lastChangeFromSpr = clientDAO.getLastChangeFromSpr(this.getSprType().getTitle());
//        } catch (ParseException ex) {
//            Logger.getLogger(Spr.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(Spr.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (URISyntaxException ex) {
//            Logger.getLogger(Spr.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(Spr.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException ex) {
//            Logger.getLogger(Spr.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        return lastChangeFromSpr;
//    }
    
}
