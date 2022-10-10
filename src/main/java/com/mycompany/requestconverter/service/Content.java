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
    
    
      public List<String> getContent() throws IOException {
        
        Path path = Paths.get("C:\\Users\\041AlikinOS\\Documents\\NetBeansProjects\\RequestConverter\\src\\main\\java\\com\\mycompany\\requestconverter\\data\\spr.csv");
        List<String> list = Files.readAllLines(path);
        return list;
    }
    
}
