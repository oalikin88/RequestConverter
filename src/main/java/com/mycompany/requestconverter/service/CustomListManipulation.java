/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.requestconverter.service;

import com.mycompany.requestconverter.data.Record;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TreeItem;

/**
 *
 * @author 041AlikinOS
 */
public class CustomListManipulation {

    // получение человекочитаемого списка запросов
    public static List<String> getRequestList(List<String> inputList) {

        List<String> list = new ArrayList<>();
        for (String s : inputList) {
            int i = s.indexOf(";");
            list.add(s.substring(0, i));
        }

        return list;
    }
    
    public static List<Record> getRecords(String[][] array) {
        
        List<Record> records = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            Record record = new Record();
            for (int j = 0; j < 4; j++) {
                
                record.setSubject(array[i][0]);
                record.setOpfr(array[i][1]);
                record.setUpfr(array[i][2]);
                record.setName(array[i][3]);
                
            }
            records.add(record);
            }
        
        
        
    return records;
    }
    
    
    public static List<Record> getOpfr(List<Record> inputList) {
    
        

        List<Record> parentsList = new ArrayList<>();

        for (int i = 0; i < inputList.size(); i++) {
            if (inputList.get(i).getOpfr().equals("000") && inputList.get(i).getUpfr().equals("000")) {
                parentsList.add(inputList.get(i));
                System.out.println(inputList.get(i));
            }
        }     
        return parentsList;
    }
    
    public static List<String> getOpfrNames(List<Record> inputList) {
        
        List<String> list = new ArrayList<>();
        for(Record value : inputList) {
            list.add(value.getOpfr());
            System.out.println(value.getOpfr());
        }

        return list;
    }
    
    
    // получение списка ОПФР из общего массива
    public static List<String> getOpfrList(String[][] array) {
        //List<String> parent = new ArrayList<>();
        List<String> list = new ArrayList<>();

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (array[i][1].equals("000") && array[i][2].equals("000")) {
                    list.add(array[i][3]);
                    System.out.println(array[i][3]);

                }
            }
        }
        return list;
    }
    // получение списка УПФР из общего массива
    public static List<Record> getUpfrList(List<Record> inputList, String element) {
        
        Record record = new Record();
        List<Record> records = new ArrayList<>();
        
        for(int i = 0; i < inputList.size(); i++) {
            if(inputList.get(i).getName().equals(element)) {
                record = inputList.get(i);
            }
        }
        
   
            for (int j = 0; j < inputList.size(); j++) {
                if (inputList.get(j).getSubject().equals(record.getSubject())
                        && (!inputList.get(j).getOpfr().equals(record.getOpfr())
                        || !inputList.get(j).getUpfr().equals(record.getUpfr()))) {
                    records.add(inputList.get(j));
                }
            }
            return records;
        }
        
    
    // получение списка УПФР при изменении элемента в choicebox ОПФР
    public static List<String> getChangeUpfrList(String[][] array, String target) {

        String buf = "";
        List<String> list = new ArrayList<>();
        
        for (int i = 0; i < array.length; i++) {
            if (array[i][3].equals(target)) {
                buf = array[i][0];
            }
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i][0].equals(buf) && !array[i][2].equals("000")) {

                list.add(array[i][3]);
            }
        }

        return list;
    }

}
