/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.requestconverter.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author 041AlikinOS
 */
public class ZipFileService {
    
    public static void zipSingleFile(Path source, String zipFileName)
            
        throws IOException {

        if (!Files.isRegularFile(source)) {
            System.err.println("Please provide a file.");
            return;
        }

        try (
            ZipOutputStream zos = new ZipOutputStream(
                new FileOutputStream(zipFileName));
            FileInputStream fis = new FileInputStream(source.toFile());
        ) {

            ZipEntry zipEntry = new ZipEntry(source.getFileName().toString());
            zos.setLevel(5);
            zos.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
            zos.closeEntry();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public static void zipMultipleFiles(List<File> list, String zipFileName) throws IOException {
    
        
        
        try(ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFileName));) {
           zos.setLevel(5);
        
        for(File f : list) {
            File fileToZip = new File(f.getAbsolutePath());
               try (FileInputStream fis = new FileInputStream(fileToZip)) {
                   ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                   
                   zos.putNextEntry(zipEntry);
                   
                   byte[] buffer = new byte[1024];
                   int len;
                   while ((len = fis.read(buffer)) > 0) {
                       zos.write(buffer, 0, len);
                   }  }
            zos.closeEntry();
           
        }
        
          
        } catch (Exception e) {
            e.printStackTrace();
        }
        
     
    }
    
}
