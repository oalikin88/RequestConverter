/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.requestconverter.service;

import com.mycompany.requestconverter.data.DataHistory;
import com.mycompany.requestconverter.data.Record;
import com.mycompany.requestconverter.data.Request;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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

    private static final String pathToSpr = "src/main/java/com/mycompany/requestconverter/data/spr.csv";
    private static final String pathToRequest = "src/main/java/com/mycompany/requestconverter/data/request.csv";
    private static final String pathToSprHistory = "src/main/java/com/mycompany/requestconverter/data/spr_history.csv";
    private static final String pathToRequestHistory = "src/main/java/com/mycompany/requestconverter/data/request_history.csv";

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date currentDate;

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

    public static List<String> getSprHistoryContent() throws IOException {
        try {
            Path getPath = Paths.get(pathToSprHistory);
            List<String> list = Files.readAllLines(getPath);
            return list;
        } catch (IOException e) {
            e.getStackTrace();
            throw new IOException("Отсутствует файл spr_history.csv");
        }

    }

    public static List<String> getRequestHistoryContent() throws IOException {
        try {
            Path getPath = Paths.get(pathToRequestHistory);
            List<String> list = Files.readAllLines(getPath);
            return list;
        } catch (IOException e) {
            e.getStackTrace();
            throw new IOException("Отсутствует файл request_history.csv");
        }

    }

    public static void eraseSprData() throws IOException {
        try {
            Path getPath = Paths.get(pathToSpr);
            Files.newBufferedWriter(getPath, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println(getPath + " очищен.");
        } catch (IOException e) {
            e.getStackTrace();
            throw new IOException("Отсутствует файл spr_history.csv");
        }
    }

    public static void eraseRequestData() throws IOException {
        try {
            Path getPath = Paths.get(pathToRequestHistory);
            Files.newBufferedWriter(getPath, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println(getPath + " очищен.");
        } catch (IOException e) {
            e.getStackTrace();
            throw new IOException("Отсутствует файл request_history.csv");
        }
    }

    public static void writeSprData(List<Record> inputList) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(pathToSpr, Charset.forName("UTF-8"));
            for (Record rec : inputList) {
                String subject = rec.getSubject();
                String opfr = rec.getOpfr();
                String upfr = rec.getUpfr();
                String name = rec.getName();
                writer.write(subject + ";" + opfr + ";" + upfr + ";" + name);
                if (inputList.iterator().hasNext()) {
                    writer.write("\n");
                }
            }
            writer.close();
            System.out.println("Запись в файл spr.csv прошла успешно.");
        } catch (IOException ex) {
            Logger.getLogger(Content.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Content.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void eraseSprHistory() throws IOException {
        try {
            Path getPath = Paths.get(pathToSprHistory);
            Files.newBufferedWriter(getPath, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println(getPath + " очищен.");
        } catch (IOException e) {
            e.getStackTrace();
            throw new IOException("Отсутствует файл spr_history.csv");
        }
    }

    public void writeSprHistory(List<DataHistory> inputList) throws IOException {
        FileWriter writer = null;
        try {
            writer = new FileWriter(pathToSprHistory, Charset.forName("UTF-8"));
            for (DataHistory data : inputList) {
                Date date = data.getDate();
                String action = data.getAction();
                int id = data.getId();
                writer.write(df.format(date) + ";" + action + ";" + id);

                if (inputList.iterator().hasNext()) {
                    writer.write("\n");
                }
            }
            writer.close();
            System.out.println("Запись в файл spr_history.csv прошла успешно.");
        } catch (IOException ex) {
            Logger.getLogger(Content.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Content.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void writeRequestHistory(List<DataHistory> inputList) throws IOException {
        FileWriter writer = null;
        try {
            writer = new FileWriter(pathToRequestHistory, Charset.forName("UTF-8"));
            for (DataHistory data : inputList) {
                Date date = data.getDate();
                String action = data.getAction();
                int id = data.getId();
                writer.write(df.format(date) + ";" + action + ";" + id);

                if (inputList.iterator().hasNext()) {
                    writer.write("\n");
                }
            }
            writer.close();
            System.out.println("Запись в файл request_history.csv прошла успешно.");
        } catch (IOException ex) {
            Logger.getLogger(Content.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Content.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void eraseRequestHistory() throws IOException {
        try {
            Path getPath = Paths.get(pathToRequestHistory);
            Files.newBufferedWriter(getPath, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println(getPath + " очищен.");
        } catch (IOException e) {
            e.getStackTrace();
            throw new IOException("Отсутствует файл request_history.csv");
        }
    }

    public static void writeRequestData(List<Request> inputList) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(pathToRequest, Charset.forName("UTF-8"));
            for (Request rec : inputList) {
                String name = rec.getName();
                String shortName = rec.getShortName();
                writer.write(name + ";" + shortName);
            

            if (inputList.iterator().hasNext()) {
                writer.write("\n");
            }
            }
            writer.close();
            System.out.println("Запись в файл request.csv прошла успешно.");
        } catch (IOException ex) {
            Logger.getLogger(Content.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Content.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
