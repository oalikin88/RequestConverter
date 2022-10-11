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
public class UpfrList {
    
    public static List getUpfrList(String array[][], int element) {
        
        List<String> upfrList = new ArrayList<>();
         for(int i = 0; i < array.length; i++) {
           
                if(array[i][0].equals(array[element][0]) && !array[i][2].equals("000")) {
                   
                    upfrList.add(array[i][3]);
                
                }
            }
         return upfrList;
    }
}
