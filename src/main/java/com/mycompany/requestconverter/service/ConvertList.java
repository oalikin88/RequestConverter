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
public class ConvertList {
    
        public static String[][] listToTwoArray(List<String> list) {
        
        int rows = list.size();
        int cells = 4;
        String[][] array = new String[rows][cells];
        int listIndex = 0;
        String[] arrayBuf = new String[4];
        for(int rowIndex = 0; rowIndex < rows; rowIndex++) {
            arrayBuf = list.get(listIndex++).split(";");
            for(int cellIndex = 0; cellIndex < arrayBuf.length; cellIndex++) {
                
                     
                     array[rowIndex][cellIndex] = arrayBuf[cellIndex];
                
                } 
            }
        
        
        return array;
    }
    
}
