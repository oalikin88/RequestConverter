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
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
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

    private static String pathToSpr = System.getProperty("user.dir") + "/data/spr.csv";
    private static String pathToRequest = System.getProperty("user.dir") + "/data/request.csv";
    private static String pathToSprHistory = System.getProperty("user.dir") + "/data/spr_history.csv";
    private static String pathToRequestHistory = System.getProperty("user.dir") + "/data/request_history.csv";

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date currentDate;

    public List<String> getContent() throws IOException, URISyntaxException {
        File file;
        try {
            Path getPath = Paths.get(pathToSpr);
            file = new File(getPath.toUri());

            List<String> list = Files.readAllLines(file.toPath());
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
            List<String> list = Files.readAllLines(file.toPath());
            return list;
        } catch (IOException e) {
            e.getStackTrace();
            throw new IOException("Отсутствует файл request.csv");
        }
    }

    public List<String> getSprHistoryContent() throws IOException, URISyntaxException {

        File file;

        try {
            Path getPath = Paths.get(pathToSprHistory);
            file = new File(getPath.toUri());
            List<String> list = Files.readAllLines(file.toPath());
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
            List<String> list = Files.readAllLines(file.toPath());
            return list;
        } catch (IOException e) {
            e.getStackTrace();
            throw new IOException("Отсутствует файл request_history.csv");
        }

    }

//    public void eraseSprData() throws IOException, URISyntaxException {
//
//        try {
//
//            Path getPath = copyToTempFile(App.class.getClassLoader().getResource(pathToSpr), ".tmp");
//            Files.newBufferedWriter(getPath, StandardOpenOption.TRUNCATE_EXISTING);
//            System.out.println(getPath + " очищен.");
//
//        } catch (IOException e) {
//            e.getStackTrace();
//            throw new IOException("Отсутствует файл spr.csv");
//        }
//
//    }
//
//    public void eraseRequestData() throws IOException, URISyntaxException {
//        try {
//            Path getPath = copyToTempFile(App.class.getClassLoader().getResource(pathToRequest), ".tmp");
//            Files.newBufferedWriter(getPath, StandardOpenOption.TRUNCATE_EXISTING);
//            System.out.println(getPath + " очищен.");
//        } catch (IOException e) {
//            e.getStackTrace();
//            throw new IOException("Отсутствует файл request_history.csv");
//        }
//    }
    public void writeSprData(List<Record> inputList) throws URISyntaxException {

        File file1;
        Path getPath1;
        FileWriter writer = null;

        try {
            getPath1 = Paths.get(pathToSpr);
            file1 = new File(getPath1.toUri());
            writer = new FileWriter(file1);
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

            System.out.println("Запись в файл spr.csv прошла успешно.");
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (writer != null)
            try {

                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Content.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

//    public void eraseSprHistory() throws IOException, URISyntaxException {
//        try {
//            Path getPath = copyToTempFile(App.class.getClassLoader().getResource(pathToSprHistory), ".tmp");
//            Files.newBufferedWriter(getPath, StandardOpenOption.TRUNCATE_EXISTING);
//            System.out.println(getPath + " очищен.");
//        } catch (IOException e) {
//            e.getStackTrace();
//            throw new IOException("Отсутствует файл spr_history.csv");
//        }
//    }
    public void writeSprHistory(List<DataHistory> inputList) throws IOException {
        File file1;
        Path getPath1;
        FileWriter writer = null;

        try {
            getPath1 = Paths.get(pathToSprHistory);
            file1 = new File(getPath1.toUri());
            writer = new FileWriter(file1);
            for (DataHistory data : inputList) {
                Date date = data.getDate();
                String action = data.getAction();
                int id = data.getId();
                writer.write(df.format(date) + ";" + action + ";" + id);

                if (inputList.iterator().hasNext()) {
                    writer.write("\n");
                }
            }

            System.out.println("Запись в файл spr_history.csv прошла успешно.");
        } catch (IOException ex) {
            Logger.getLogger(Content.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(Content.class.getName()).log(Level.SEVERE, null, ex);

                }
            }
        }
    }

    public void writeRequestHistory(List<DataHistory> inputList) throws IOException, URISyntaxException {
        File file1;
        Path getPath1;
        FileWriter writer = null;
        try {
            getPath1 = Paths.get(pathToRequestHistory);
            file1 = new File(getPath1.toUri());
            writer = new FileWriter(file1);
            for (DataHistory data : inputList) {
                Date date = data.getDate();
                String action = data.getAction();
                int id = data.getId();
                writer.write(df.format(date) + ";" + action + ";" + id);

                if (inputList.iterator().hasNext()) {
                    writer.write("\n");
                }
            }
            System.out.println("Запись в файл request_history.csv прошла успешно.");
        } catch (IOException ex) {
            Logger.getLogger(Content.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(Content.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

//    public void eraseRequestHistory() throws IOException, URISyntaxException {
//        try {
//            Path getPath = copyToTempFile(App.class.getClassLoader().getResource(pathToRequestHistory), ".tmp");
//            Files.newBufferedWriter(getPath, StandardOpenOption.TRUNCATE_EXISTING);
//            System.out.println(getPath + " очищен.");
//        } catch (IOException e) {
//            e.getStackTrace();
//            throw new IOException("Отсутствует файл request_history.csv");
//        }
//    }
    public void writeRequestData(List<Request> inputList) throws URISyntaxException {
        File file1;
        Path getPath1;
        FileWriter writer = null;

        try {
            getPath1 = Paths.get(pathToRequest);
            file1 = new File(getPath1.toUri());
            writer = new FileWriter(file1);

            for (Request rec : inputList) {
                String name = rec.getName();
                String shortName = rec.getShortName();
                writer.write(name + ";" + shortName);

                if (inputList.iterator().hasNext()) {
                    writer.write("\n");
                }
            }
            System.out.println("Запись в файл request.csv прошла успешно.");
        } catch (IOException ex) {
            Logger.getLogger(Content.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                    Logger.getLogger(Content.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
