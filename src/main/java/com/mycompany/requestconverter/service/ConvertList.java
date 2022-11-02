/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.requestconverter.service;

import com.mycompany.requestconverter.data.DataHistory;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 041AlikinOS
 */
public class ConvertList {

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date;

    public static String[][] listToTwoArray(List<String> list) {

        int rows = list.size();
        int cells = 4;
        String[][] array = new String[rows][cells];
        int listIndex = 0;
        String[] arrayBuf = new String[4];
        for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
            arrayBuf = list.get(listIndex++).split(";");
            for (int cellIndex = 0; cellIndex < arrayBuf.length; cellIndex++) {

                array[rowIndex][cellIndex] = arrayBuf[cellIndex];

            }
        }

        return array;
    }

    public List<DataHistory> getDataHistory(List<String> inputList) throws ParseException {
        List<DataHistory> list = new ArrayList<>();
        for (int i = 0; i < inputList.size(); i++) {
            String[] array = new String[3];
            array = inputList.get(i).split(";");
            DataHistory dataHistory = new DataHistory();
            date = df.parse(array[0]);
            dataHistory.setDate(date);
            dataHistory.setAction(array[1]);
            dataHistory.setId(Integer.parseInt(array[2]));
            list.add(dataHistory);
        }
        return list;
    }
    
    

}
