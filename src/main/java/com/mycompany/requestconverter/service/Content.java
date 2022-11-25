/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.requestconverter.service;

import com.mycompany.requestconverter.App;
import com.mycompany.requestconverter.data.DataHistory;
import com.mycompany.requestconverter.data.Record;
import com.mycompany.requestconverter.data.Request;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
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

    private static String pathToSpr = "com/mycompany/requestconverter/service/spr.csv";
    private static String pathToRequest = "com/mycompany/requestconverter/service/request.csv";
    private static String pathToSprHistory = "com/mycompany/requestconverter/service/spr_history.csv";
    private static String pathToRequestHistory = "com/mycompany/requestconverter/service/request_history.csv";

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date currentDate;

    public static Path copyToTempFile(URL url, String suffix) throws IOException {
        // 'suffix' will default to ".tmp" if null
        Path tempFile = Files.createTempFile(null, suffix);
        try ( InputStream in = url.openStream();  OutputStream out = Files.newOutputStream(tempFile)) {
            in.transferTo(out); // 'transferTo' method added in Java 9
        }
        return tempFile;
    }

    public List<String> getContent() throws IOException, URISyntaxException {

        try {
           
            Path getPath = copyToTempFile(App.class.getClassLoader().getResource(pathToSpr), ".tmp");
            List<String> list = Files.readAllLines(getPath);

            return list;
        } catch (IOException e) {
            e.getStackTrace();
            throw new IOException("Отсутствует файл spr.csv");
        }
    }

    public List<String> getRequests() throws IOException, URISyntaxException {
        try {
            Path getPath = copyToTempFile(App.class.getClassLoader().getResource(pathToRequest), ".tmp");
            List<String> list = Files.readAllLines(getPath);
            return list;
        } catch (IOException e) {
            e.getStackTrace();
            throw new IOException("Отсутствует файл request.csv");
        }
    }

    public List<String> getSprHistoryContent() throws IOException, URISyntaxException {
        try {
            Path getPath = copyToTempFile(App.class.getClassLoader().getResource(pathToSprHistory), ".tmp");
            List<String> list = Files.readAllLines(getPath);
            return list;
        } catch (IOException e) {
            e.getStackTrace();
            throw new IOException("Отсутствует файл spr_history.csv");
        }

    }

    public List<String> getRequestHistoryContent() throws IOException, URISyntaxException {
        try {
           Path getPath = copyToTempFile(App.class.getClassLoader().getResource(pathToRequestHistory), ".tmp");
            List<String> list = Files.readAllLines(getPath);
            return list;
        } catch (IOException e) {
            e.getStackTrace();
            throw new IOException("Отсутствует файл request_history.csv");
        }

    }

    public void eraseSprData() throws IOException, URISyntaxException {
        try {
            Path getPath = copyToTempFile(App.class.getClassLoader().getResource(pathToSpr), ".tmp");
            Files.newBufferedWriter(getPath, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println(getPath + " очищен.");
        } catch (IOException e) {
            e.getStackTrace();
            throw new IOException("Отсутствует файл spr_history.csv");
        }
    }

    public void eraseRequestData() throws IOException, URISyntaxException {
        try {
            Path getPath = copyToTempFile(App.class.getClassLoader().getResource(pathToRequest), ".tmp");
            Files.newBufferedWriter(getPath, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println(getPath + " очищен.");
        } catch (IOException e) {
            e.getStackTrace();
            throw new IOException("Отсутствует файл request_history.csv");
        }
    }

    public void writeSprData(List<Record> inputList) throws URISyntaxException {
        FileWriter writer = null;
        try {
            Path getPath = copyToTempFile(App.class.getClassLoader().getResource(pathToSpr), ".tmp");
            writer = new FileWriter(getPath.toFile());
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
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void eraseSprHistory() throws IOException, URISyntaxException {
        try {
            Path getPath = copyToTempFile(App.class.getClassLoader().getResource(pathToSprHistory), ".tmp");
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
            Path getPath = copyToTempFile(App.class.getClassLoader().getResource(pathToSprHistory), ".tmp");
            writer = new FileWriter(getPath.toFile());
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

    public void writeRequestHistory(List<DataHistory> inputList) throws IOException, URISyntaxException {
        FileWriter writer = null;
        try {
            Path getPath = copyToTempFile(App.class.getClassLoader().getResource(pathToRequestHistory), ".tmp");
            writer = new FileWriter(getPath.toFile());
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

    public void eraseRequestHistory() throws IOException, URISyntaxException {
        try {
            Path getPath = copyToTempFile(App.class.getClassLoader().getResource(pathToRequestHistory), ".tmp");
            Files.newBufferedWriter(getPath, StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println(getPath + " очищен.");
        } catch (IOException e) {
            e.getStackTrace();
            throw new IOException("Отсутствует файл request_history.csv");
        }
    }

    public void writeRequestData(List<Request> inputList) throws URISyntaxException {
        FileWriter writer = null;
        try {
            Path getPath = copyToTempFile(App.class.getClassLoader().getResource(pathToRequest), ".tmp");
            writer = new FileWriter(getPath.toFile());
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
