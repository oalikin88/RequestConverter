/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.requestconverter.service;

import java.util.List;

/**
 *
 * @author 041AlikinOS
 */
public class RequestFormirovationService {
    
    public static String getRequestValue(List<String> list, String inValue) {
        String out = "";
        for (String s : list) {
                int i = s.indexOf(";");
                if (s.substring(0, i).toLowerCase().trim().equals(inValue.toLowerCase().trim())) {

                    out = s.substring(++i, s.length());
                }
            }
        return out;
    }
    
    public static String getRequestCode(String[][] array, String value) {
        
        String s = "";
         for (int i = 0; i < array.length; i++) {
                for (int j = 0; j < 4; j++) {
                    if (array[i][j].toLowerCase().trim().equals(value.toLowerCase().trim())) {
                        s += array[i][j - 3];
                        s += array[i][j - 2];
                        s += array[i][j - 1];

                    }
                }
            }
        
        return s;
    }
    
}
