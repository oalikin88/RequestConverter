/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.requestconverter.connection;

import com.mycompany.requestconverter.data.Settings;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 041AlikinOS
 */
public class DBConnection {


    private Connection connection;
    private Settings settings;

  
    
    public DBConnection() {
        settings = new Settings();
        
        
        try {
            Map<String, String> prepareSettings = settings.prepareSettings();
            settings.getSettings(prepareSettings);
            connection = DriverManager.getConnection("jdbc:mysql://" + settings.getUrl() + ":" + settings.getPort() + "/" + settings.getDbName()
                    + "?useSSL=false&allowPublicKeyRetrieval=true", settings.getUsername(), settings.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnection() {
        return connection;
    }  
}
