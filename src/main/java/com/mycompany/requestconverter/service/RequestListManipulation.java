/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.requestconverter.service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 041AlikinOS
 */
public class RequestListManipulation {
    
    public static List<String> getRequestList(List<String> inputList) {
        
        List<String> list = new ArrayList<>();
        for (String s : inputList) {
            int i = s.indexOf(";");
            list.add(s.substring(0, i));
        }
        
        return list;
    }
    
    
}
