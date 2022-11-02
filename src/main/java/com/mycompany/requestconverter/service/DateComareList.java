/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.requestconverter.service;

import com.mycompany.requestconverter.data.DataHistory;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 041AlikinOS
 */
public class DateComareList {
    
    public static int compareDataHistoryList(List<DataHistory> listFromServer, List<DataHistory> listFromData) {
        
        listFromServer.sort((o1, o2) -> {
            return o1.getDate().compareTo(o2.getDate());
        });
        Date date1 = listFromServer.get(0).getDate();
        
        listFromData.sort((o1, o2) -> {
            return o1.getDate().compareTo(o2.getDate());
        });
        
        Date date2 = listFromData.get(0).getDate();
        if(date1.equals(date2)) {
            return 1;
        }
        return -1;
        
    }
    

    
}
