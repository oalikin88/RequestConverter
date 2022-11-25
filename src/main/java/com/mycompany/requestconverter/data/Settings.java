/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.requestconverter.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 041AlikinOS
 */
public class Settings {
     private String url;
    private String port;
    private String dbName;
    private String username;
    private String password;
    private String path = System.getProperty("user.dir") + "/data/settings.csv";
    private Map<String, String> settingsMap = new HashMap<>();
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    public Map<String, String> getSettingsMap() {
        return settingsMap;
    }

    public void setSettingsMap(Map<String, String> settingsMap) {
        this.settingsMap = settingsMap;
    }
    
    
        public Map<String, String> prepareSettings() throws IOException, URISyntaxException {
        try {
              
            
            Path getPath = Paths.get(path);
            List<String> list = Files.readAllLines(getPath);
            for(int i = 0; i < list.size(); i++) {
              String[] buf = list.get(i).split(":");
               settingsMap.put(buf[0], buf[1]);
            }
            return settingsMap;
        } catch (IOException e) {
            e.getStackTrace();
            throw new IOException("Отсутствует файл settings, текущий путь: " + path);
        }
    }
        
        public Settings getSettings(Map<String, String> input) {
            
            Settings currentSettings = null;
            
            if(!input.isEmpty()) {
                url = input.get("URL");
                port = input.get("PORT");
                dbName = input.get("DB_NAME");
                username = input.get("USER");
                password = input.get("PASSWORD");
            }
            
            
            return currentSettings;
        }
        
        public Map<String, String> changeSettings(Map<String, String> settingsOld, Settings input) {
         
        settingsOld.replace("URL", input.url);
        settingsOld.replace("PORT", input.port);
        settingsOld.replace("DB_NAME", input.dbName);
        settingsOld.replace("USER", input.username);
        settingsOld.replace("PASSWORD", input.password);
        return settingsOld;
        }
        
        public void saveSettings(Map<String, String> settingsNew) {
            BufferedWriter writer = null;
            try {
                 Path getPath = Paths.get(path);
                
                writer = new BufferedWriter(new FileWriter(getPath.toFile()));
                for(Map.Entry<String, String> entry : settingsNew.entrySet()) {
               writer.write(entry.getKey() + ":" + entry.getValue());
               writer.newLine();
            }
                writer.flush();
            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
}
