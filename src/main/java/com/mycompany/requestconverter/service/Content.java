/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.requestconverter.service;

import com.mycompany.requestconverter.App;
import com.mycompany.requestconverter.data.DataHistory;
import com.mycompany.requestconverter.data.Record;
import com.mycompany.requestconverter.data.Request;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 041AlikinOS
 */
public class Content {

    public static String pathToSpr = System.getProperty("user.dir") + "/data/spr.csv";
    private static String pathToRequest = System.getProperty("user.dir") + "/data/request.csv";
    private static String pathToSprHistory = System.getProperty("user.dir") + "/data/spr_history.csv";
    private static String pathToRequestHistory = System.getProperty("user.dir") + "/data/request_history.csv";

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date currentDate;

    public List<String> getContentSpr(String target) throws IOException, URISyntaxException {
        String request = pathToSpr.replace("spr", target);
        File file;
        try {
            Path getPath = Paths.get(request);
            file = new File(getPath.toUri());
            List<String> list = Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));
            return list;
        } catch (IOException e) {
            e.getStackTrace();
            throw new IOException("Отсутствует файл spr.csv");
        }
    }
    

    public List<String> getRequests() throws IOException, URISyntaxException {
        File file;
        try {
            Path getPath = Paths.get(pathToRequest);
            file = new File(getPath.toUri());
            List<String> list = Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));
            return list;
        } catch (IOException e) {
            e.getStackTrace();
            throw new IOException("Отсутствует файл request.csv");
        }
    }

    public List<String> getSprHistoryContent(String target) throws IOException, URISyntaxException {
        String request = pathToSprHistory.replace("spr", target);
        File file;

        try {
            Path getPath = Paths.get(request);
            file = new File(getPath.toUri());
            List<String> list = Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));
            return list;
        } catch (IOException e) {
            e.getStackTrace();
            throw new IOException("Отсутствует файл spr_history.csv");
        }

    }

    public List<String> getRequestHistoryContent() throws IOException, URISyntaxException {
        File file;
        try {
            Path getPath = Paths.get(pathToRequestHistory);
            file = new File(getPath.toUri());
            List<String> list = Files.readAllLines(file.toPath(), Charset.forName("UTF-8"));
            return list;
        } catch (IOException e) {
            e.getStackTrace();
            throw new IOException("Отсутствует файл request_history.csv");
        }

    }

    public void writeSprData(List<Record> inputList, String target) throws URISyntaxException {
        String request = pathToSpr.replace("spr", target);
        File file1;
        Path getPath1;
        FileWriter writer = null;
        OutputStreamWriter wr = null;

        try {
            getPath1 = Paths.get(request);
            file1 = new File(getPath1.toUri());
            wr = new OutputStreamWriter(new FileOutputStream(file1), StandardCharsets.UTF_8);
            
            for (Record rec : inputList) {
                String subject = rec.getSubject();
                String opfr = rec.getOpfr();
                String upfr = rec.getUpfr();
                String name = rec.getName();
                wr.write(subject + ";" + opfr + ";" + upfr + ";" + name);
                if (inputList.iterator().hasNext()) {
                    wr.write("\n");
                }
            }

            System.out.println("Запись в файл spr.csv прошла успешно.");
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (wr != null)
            try {
                wr.close();
            } catch (IOException ex) {
                Logger.getLogger(Content.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }

    }
    
    public void writeSprHistory(List<DataHistory> inputList, String target) throws IOException {
        String request = pathToSprHistory.replace("spr", target);
        File file1;
        Path getPath1;
        OutputStreamWriter wr  = null;

        try {
            getPath1 = Paths.get(request);
            file1 = new File(getPath1.toUri());
            wr = new OutputStreamWriter(new FileOutputStream(file1), StandardCharsets.UTF_8);
            for (DataHistory data : inputList) {
                Date date = data.getDate();
                String action = data.getAction();
                int id = data.getId();
                wr.write(df.format(date) + ";" + action + ";" + id);

                if (inputList.iterator().hasNext()) {
                    wr.write("\n");
                }
            }

            System.out.println("Запись в файл spr_history.csv прошла успешно.");
        } catch (IOException ex) {
            Logger.getLogger(Content.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (wr != null) {
                try {
                    wr.close();
                } catch (IOException ex) {
                    Logger.getLogger(Content.class.getName()).log(Level.SEVERE, null, ex);

                }
            }
        }
    }

    public void writeRequestHistory(List<DataHistory> inputList) throws IOException, URISyntaxException {
        File file1;
        Path getPath1;
        OutputStreamWriter wr = null;
        try {
            getPath1 = Paths.get(pathToRequestHistory);
            file1 = new File(getPath1.toUri());
            wr = new OutputStreamWriter(new FileOutputStream(file1), StandardCharsets.UTF_8);
            for (DataHistory data : inputList) {
                Date date = data.getDate();
                String action = data.getAction();
                int id = data.getId();
                wr.write(df.format(date) + ";" + action + ";" + id);

                if (inputList.iterator().hasNext()) {
                    wr.write("\n");
                }
            }
            System.out.println("Запись в файл request_history.csv прошла успешно.");
        } catch (IOException ex) {
            Logger.getLogger(Content.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (wr != null) {
                try {
                    wr.close();
                } catch (IOException ex) {
                    Logger.getLogger(Content.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void writeRequestData(List<Request> inputList) throws URISyntaxException {
        File file1;
        Path getPath1;
        FileWriter writer = null;
        OutputStreamWriter wr = null;

        try {
            getPath1 = Paths.get(pathToRequest);
            file1 = new File(getPath1.toUri());
            wr = new OutputStreamWriter(new FileOutputStream(file1), StandardCharsets.UTF_8);
            

            for (Request rec : inputList) {
                String name = rec.getName();
                String shortName = rec.getShortName();
                wr.write(name + ";" + shortName);

                if (inputList.iterator().hasNext()) {
                    wr.write("\n");
                }
            }
            System.out.println("Запись в файл request.csv прошла успешно.");
        } catch (IOException ex) {
            Logger.getLogger(Content.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (wr != null) {
                try {
                    wr.close();
                } catch (IOException ex) {
                    Logger.getLogger(Content.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
