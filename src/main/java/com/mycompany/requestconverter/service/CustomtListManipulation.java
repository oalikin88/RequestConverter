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
public class CustomtListManipulation {

    // получение человекочитаемого списка запросов
    public static List<String> getRequestList(List<String> inputList) {

        List<String> list = new ArrayList<>();
        for (String s : inputList) {
            int i = s.indexOf(";");
            list.add(s.substring(0, i));
        }

        return list;
    }
    // получение списка ОПФР из общего массива
    public static List<String> getOpfrList(String[][] array) {

        List<String> list = new ArrayList<>();

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < 4; j++) {
                if (j == 2 && array[i][j].equals("000")) {
                    list.add(array[i][3]);

                }
            }
        }
        return list;
    }
    // получение списка УПФР из общего массива
    public static List<String> getUpfrList(String array[][], int element) {

        List<String> upfrList = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {

            if (array[i][0].equals(array[element][0]) && !array[i][2].equals("000")) {

                upfrList.add(array[i][3]);

            }
        }
        return upfrList;
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
