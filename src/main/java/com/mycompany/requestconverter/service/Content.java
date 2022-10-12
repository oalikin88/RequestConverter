/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.requestconverter.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author 041AlikinOS
 */
public class Content {

    private static final String pathToSpr = "src/main/java/com/mycompany/requestconverter/data/spr.csv";
    private static final String pathToRequest = "src/main/java/com/mycompany/requestconverter/data/request.csv";

    public static List<String> getContent() throws IOException {
        try {
            Path getPath = Paths.get(pathToSpr);
            List<String> list = Files.readAllLines(getPath);
            return list;
        } catch (IOException e) {
            e.getStackTrace();
            throw new IOException("Отсутствует файл spr.csv");

        }

    }

    public static List<String> getRequests() throws IOException {
        try {
            Path getPath = Paths.get(pathToRequest);
            List<String> list = Files.readAllLines(getPath);
            return list;
        } catch (IOException e) {
            e.getStackTrace();
            throw new IOException("Отсутствует файл request.csv");
        }

    }

}
