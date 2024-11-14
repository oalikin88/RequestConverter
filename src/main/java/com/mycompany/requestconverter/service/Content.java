/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.requestconverter.service;

import com.mycompany.requestconverter.App;
import com.mycompany.requestconverter.data.Spravochnik;
import com.mycompany.requestconverter.data.Department;
import com.mycompany.requestconverter.data.Fields;
import com.mycompany.requestconverter.data.Pattern;
import com.mycompany.requestconverter.data.Region;
import com.mycompany.requestconverter.data.Request;
import com.mycompany.requestconverter.data.SubRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 041AlikinOS
 */
public class Content {
    
    
    public static String pathToRegions = System.getProperty("user.dir") + "/data/regions.csv";
    public static String pathToFields = System.getProperty("user.dir") + "/data/fields.csv";
    public static String pathToCatalog = System.getProperty("user.dir") + "/data/spravochnik.csv";
    public static String pathToDepartments = System.getProperty("user.dir") + "/data/departments.csv";
    public static String pathToRequest = System.getProperty("user.dir") + "/data/request.csv";
    public static String pathToSubRequest = System.getProperty("user.dir") + "/data/sub_request.csv";
    private static String pathToSprHistory = System.getProperty("user.dir") + "/data/spr_history.csv";
    private static String pathToRequestHistory = System.getProperty("user.dir") + "/data/request_history.csv";

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date currentDate;

    public List<Spravochnik> getSpravochnikContent() throws IOException {
      Path out;

        try {
            out = Files.createFile(Paths.get(pathToCatalog));
        } catch (FileAlreadyExistsException faee) {
            out = Paths.get(pathToCatalog);
        }
        
        List<Spravochnik> catalogs = new ArrayList<>();
        String lineCatalog;
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(out.toFile(), Charset.forName("UTF-8")));
            while ((lineCatalog = reader.readLine()) != null) {
                String[] parts = lineCatalog.split(";");
                Spravochnik catalog = new Spravochnik();
                catalog.setId(Integer.parseInt(parts[0]));
                catalog.setName(parts[1]);
                catalogs.add(catalog);
            }
        } catch(IOException e) {
            e.getStackTrace();
            throw new IOException("Отсутствует файл catalog.csv");
        } finally{
            reader.close();
        }
        return catalogs;
    }
    
    
    public Set<Region> getRegionsContent() throws IOException, URISyntaxException, NullPointerException {
       // String request = pathToRegions.replace("spr", target);
       //File file;
       Path out;

        try {
            out = Files.createFile(Paths.get(pathToRegions));
        } catch (FileAlreadyExistsException faee) {
            out = Paths.get(pathToRegions);
        }
       
       
        Set<Region> regions = new HashSet<>();
        
         String lineRegions;
         BufferedReader readerRegions = null;
         try {
          readerRegions = new BufferedReader(new FileReader(out.toFile(), Charset.forName("UTF-8")));
    while ((lineRegions = readerRegions.readLine()) != null)
    {
          String[] parts = lineRegions.split(";");
          Region region = new Region(Integer.parseInt(parts[0]), parts[1], parts[2], Integer.parseInt(parts[3]));
          regions.add(region);
         
    }
        
        } catch (IOException e) {
            
            e.getStackTrace();
            throw new IOException("Отсутствует файл regions.csv");
        } finally {
             
             readerRegions.close();
             
         }
         return regions;
    }
    
    
     public List<Department> getDepartmentsContent() throws IOException, URISyntaxException {
       // String request = pathToRegions.replace("spr", target);
       //File file;
       
       Path out;

        try {
            out = Files.createFile(Paths.get(pathToDepartments));
        } catch (FileAlreadyExistsException faee) {
            out = Paths.get(pathToDepartments);
        }
       
        List<Department> departments = new ArrayList<>();
        
         String lineDepartments;
         BufferedReader readerDepartments = null;
         try {
          readerDepartments = new BufferedReader(new FileReader(out.toFile(), Charset.forName("UTF-8")));
    while ((lineDepartments = readerDepartments.readLine()) != null)
    {
          String[] parts = lineDepartments.split(";");
          Department department = new Department(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], parts[4], Integer.parseInt(parts[5]));
          departments.add(department);
         
    }
        
        } catch (IOException e) {
            e.getStackTrace();
            throw new IOException("Отсутствует файл departments.csv");
        } finally {
             readerDepartments.close();
         }
         return departments;
    }
    
    public Set<Region> finalizeRegions(List<Region> inputRegions, List<Department> inputDepartments) {
        Set<Region> out = new HashSet<>();
        for(Region region : inputRegions) {
            for(Department department : inputDepartments) {
                if(region.getRegionCode().trim().equals(department.getRegionCode().trim()) && region.getSpravochnikId() == department.getSpravochnikId()) {
                    region.addDepartment(department);
                }
            }
            out.add(region);
        }
        return out;
    }

    public List<Request> getRequests() throws IOException  {
         Path out;

        try {
            out = Files.createFile(Paths.get(pathToRequest));
        } catch (FileAlreadyExistsException faee) {
            out = Paths.get(pathToRequest);
        }
        List<Request> requests = new ArrayList<>();
        
         String lineRequest;
         BufferedReader readerRequest = null;
         try {
          readerRequest = new BufferedReader(new FileReader(out.toFile(), Charset.forName("UTF-8")));
    while ((lineRequest = readerRequest.readLine()) != null)
    {
          String[] parts = lineRequest.split(";");
          Request request = new Request();
          Spravochnik spr = new Spravochnik();
          Pattern pattern = new Pattern();
          request.setId(Integer.parseInt(parts[0]));
          request.setRequestCode(parts[1]);
          request.setName(parts[2]);
          spr.setId(Integer.parseInt(parts[3]));
          spr.setName(parts[4]);
          pattern.setPatternId(Integer.parseInt(parts[5]));
          pattern.setPattern(parts[6]);
          request.setSpravochnik(spr);
          request.setPattern(pattern);
          requests.add(request);
         
    }
        
        } catch (IOException e) {
            e.getStackTrace();
            throw new IOException("Отсутствует файл departments.csv");
        } finally {
             readerRequest.close();
         }
         return requests;
    }
    
    public List<Fields> getFields() throws IOException  {
         Path out;

        try {
            out = Files.createFile(Paths.get(pathToFields));
        } catch (FileAlreadyExistsException faee) {
            out = Paths.get(pathToFields);
        }
        List<Fields> fields = new ArrayList<>();
        
         String lineRequest;
         BufferedReader readerRequest = null;
         try {
          readerRequest = new BufferedReader(new FileReader(out.toFile(), Charset.forName("UTF-8")));
    while ((lineRequest = readerRequest.readLine()) != null)
    {
          String[] parts = lineRequest.split(";");
          Fields field = new Fields();
          field.setPatternId(Integer.parseInt(parts[0]));
          field.setFieldId(Integer.parseInt(parts[1]));
          field.setField(parts[2]);
          fields.add(field);
    }
        
        } catch (IOException e) {
            e.getStackTrace();
            throw new IOException("Отсутствует файл departments.csv");
        } finally {
             readerRequest.close();
         }
         return fields;
    }
    
    
    
    public List<SubRequest> getSubRequests() throws IOException  {
        
        Path out;

        try {
            out = Files.createFile(Paths.get(pathToSubRequest));
        } catch (FileAlreadyExistsException faee) {
            out = Paths.get(pathToSubRequest);
        }
        
        List<SubRequest> subRequests = new ArrayList<>();
        
         String lineSubRequest;
         BufferedReader readerSubRequest = null;
         try {
          readerSubRequest = new BufferedReader(new FileReader(out.toFile(), Charset.forName("UTF-8")));
    while ((lineSubRequest = readerSubRequest.readLine()) != null)
    {
          String[] parts = lineSubRequest.split(";");
          SubRequest subRequest = new SubRequest();
          subRequest.setId(Integer.parseInt(parts[0]));
          subRequest.setSubRequestCode(parts[1]);
          subRequest.setSubRequestName(parts[2]);
          subRequest.setRequestId(Integer.parseInt(parts[3]));
          subRequests.add(subRequest);
         
    }
        
        } catch (IOException e) {
            e.getStackTrace();
            throw new IOException("Отсутствует файл sub_request.csv");
        } finally {
             readerSubRequest.close();
         }
         return subRequests;
    }
    
    public List<Request> finalizeRequest(List<Request> inputRequests, List<SubRequest> subRequests) {
        List<Request> out = new ArrayList();
        for (Request request : inputRequests) {
            for (SubRequest subRequest : subRequests) {
                if (request.getId() == subRequest.getRequestId()) {
                    request.addSubRequest(subRequest);
                }
            }
             out.add(request);
        }
        return out;
    }
    
    public List<Request> addFieldsToRequests(List<Request> inputRequests, List<Fields> fields) {
        List<Request> out = new ArrayList<>();
        for(Request req : inputRequests) {
            for(Fields field : fields) {
                if(req.getPattern().getPatternId() == field.getPatternId()) {
                    req.getPattern().getFields().add(field);
                }
            }
            out.add(req);
        }
        return out;
    }
    
    // получение имени компьютера
    public String getComputerName() {
        Map<String, String> env = System.getenv();
        if (env.containsKey("COMPUTERNAME")) {
            return env.get("COMPUTERNAME");
        } else if (env.containsKey("HOSTNAME")) {
            return env.get("HOSTNAME");
        } else {
            return "Unknown Computer";
        }
    }

//    public List<String> getSprHistoryContent(String target) throws IOException, URISyntaxException {
//        String request = pathToSprHistory.replace("spr", target);
//        File file;
//
//        try {
//            Path getPath = Paths.get(request);
//            file = new File(getPath.toUri());
//            List<String> list = Files.readAllLines(file.toPath());
//            return list;
//        } catch (IOException e) {
//            e.getStackTrace();
//            throw new IOException("Отсутствует файл spr_history.csv");
//        }
//
//    }
//
//    public List<String> getRequestHistoryContent() throws IOException, URISyntaxException {
//        File file;
//        try {
//            Path getPath = Paths.get(pathToRequestHistory);
//            file = new File(getPath.toUri());
//            List<String> list = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
//            return list;
//        } catch (IOException e) {
//            e.getStackTrace();
//            throw new IOException("Отсутствует файл request_history.csv");
//        }
//
//    }

    public void writeRegionData(List<Region> inputList, String target) throws URISyntaxException {
 
        File file;
        Path getPath;
        FileWriter writer = null;

        try {
            getPath = Paths.get(target);
            file = new File(getPath.toUri());
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            for (Region rec : inputList) {
                String regionId = "" + rec.getRegionId();
                String regionCode = rec.getRegionCode();
                String regionName = rec.getRegionName();
                String spravochnikId = "" + rec.getSpravochnikId();
                writer.write(regionId + ";" + regionCode + ";" + regionName + ";" + spravochnikId);
                if (inputList.iterator().hasNext()) {
                    writer.write("\n");
                }
            }

            System.out.println("Запись в файл прошла успешно.");
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
    
    public void writeDepartmentData(List<Department> inputList, String target) throws URISyntaxException {
 
        File file;
        Path getPath;
        FileWriter writer = null;

        try {
            getPath = Paths.get(target);
            file = new File(getPath.toUri());
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            for (Department rec : inputList) {
                String departmentId = "" + rec.getDepartmentId();
                String regionCode = rec.getRegionCode();
                String territoryCode = rec.getTerritoryCode();
                String departmentCode = rec.getDepartmentCode();
                String departmentName = rec.getDepartmentName();
                String spravochnikId = "" + rec.getSpravochnikId();
                writer.write(departmentId + ";" + regionCode + ";" + territoryCode + ";" + departmentCode + ";" + departmentName + ";" + spravochnikId);
                if (inputList.iterator().hasNext()) {
                    writer.write("\n");
                }
            }

            System.out.println("Запись в файл прошла успешно.");
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
    
    
       public void writeCatalogData(List<Spravochnik> inputList, String target) throws URISyntaxException {
 
        File file;
        Path getPath;
        FileWriter writer = null;

        try {
            getPath = Paths.get(target);
            file = new File(getPath.toUri());
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            for (Spravochnik rec : inputList) {
                String catalogId = "" + rec.getId();
                String name = rec.getName();
                writer.write(catalogId + ";" + name);
                if (inputList.iterator().hasNext()) {
                    writer.write("\n");
                }
            }

            System.out.println("Запись в файл прошла успешно.");
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
    
       
       public void writeFieldsData(List<Fields> inputList, String target) throws URISyntaxException {
 
        File file;
        Path getPath;
        FileWriter writer = null;

        try {
            getPath = Paths.get(target);
            file = new File(getPath.toUri());
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            for (Fields rec : inputList) {
                String patternId = "" + rec.getPatternId();
                String fieldId = "" + rec.getFieldId();
                String field = rec.getField();
                writer.write(patternId + ";" + fieldId + ";" + field);
                if (inputList.iterator().hasNext()) {
                    writer.write("\n");
                }
            }

            System.out.println("Запись в файл прошла успешно.");
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
       
       
    
    public void writeRequestData(List<Request> inputList, String target) throws URISyntaxException {
 
        File file;
        Path getPath;
        FileWriter writer = null;

        try {
            getPath = Paths.get(target);
            file = new File(getPath.toUri());
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            for (Request rec : inputList) {
                String requestId = "" + rec.getId();
                String requestCode = rec.getRequestCode();
                String requestName = rec.getName();
                String requestPattern = rec.getPattern().getPattern();
                String requestPatternId = "" + rec.getPattern().getPatternId();
                String spravochnikId = "" + rec.getSpravochnik().getId();
                String spravochnikName = rec.getSpravochnik().getName();
                writer.write(requestId + ";" + requestCode + ";" + requestName + ";" + spravochnikId + ";" + spravochnikName + ";" + requestPatternId + ";" + requestPattern);
                if (inputList.iterator().hasNext()) {
                    writer.write("\n");
                }
            }

            System.out.println("Запись в файл прошла успешно.");
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
    
        public void writeSubRequestData(List<SubRequest> inputList, String target) throws URISyntaxException {
 
        File file;
        Path getPath;
        FileWriter writer = null;

        try {
            getPath = Paths.get(target);
            file = new File(getPath.toUri());
            writer = new FileWriter(file, StandardCharsets.UTF_8);
            for (SubRequest rec : inputList) {
                String subRequestId = "" + rec.getId();
                String requestCode = rec.getSubRequestCode();
                String requestName = rec.getSubRequestName();
                String requestId = "" + rec.getRequestId();
                writer.write(subRequestId + ";" + requestCode + ";" + requestName + ";" + requestId);
                if (inputList.iterator().hasNext()) {
                    writer.write("\n");
                }
            }

            System.out.println("Запись в файл прошла успешно.");
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
    
    
//    public void writeSprHistory(List<DataHistory> inputList, String target) throws IOException {
//        String request = pathToSprHistory.replace("spr", target);
//        File file1;
//        Path getPath1;
//        FileWriter writer = null;
//
//        try {
//            getPath1 = Paths.get(request);
//            file1 = new File(getPath1.toUri());
//            writer = new FileWriter(file1, StandardCharsets.UTF_8);
//            for (DataHistory data : inputList) {
//                Date date = data.getDate();
//                String action = data.getAction();
//                int id = data.getId();
//                writer.write(df.format(date) + ";" + action + ";" + id);
//
//                if (inputList.iterator().hasNext()) {
//                    writer.write("\n");
//                }
//            }
//
//            System.out.println("Запись в файл spr_history.csv прошла успешно.");
//        } catch (IOException ex) {
//            Logger.getLogger(Content.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            if (writer != null) {
//                try {
//                    writer.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(Content.class.getName()).log(Level.SEVERE, null, ex);
//
//                }
//            }
//        }
//    }

//    public void writeRequestHistory(List<DataHistory> inputList) throws IOException, URISyntaxException {
//        File file1;
//        Path getPath1;
//        FileWriter writer = null;
//        try {
//            getPath1 = Paths.get(pathToRequestHistory);
//            file1 = new File(getPath1.toUri());
//            writer = new FileWriter(file1, StandardCharsets.UTF_8);
//            for (DataHistory data : inputList) {
//                Date date = data.getDate();
//                String action = data.getAction();
//                int id = data.getId();
//                writer.write(df.format(date) + ";" + action + ";" + id);
//
//                if (inputList.iterator().hasNext()) {
//                    writer.write("\n");
//                }
//            }
//            System.out.println("Запись в файл request_history.csv прошла успешно.");
//        } catch (IOException ex) {
//            Logger.getLogger(Content.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            if (writer != null) {
//                try {
//                    writer.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(Content.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//    }

//    public void writeRequestData(List<Request> inputList) throws URISyntaxException {
//        File file1;
//        Path getPath1;
//        FileWriter writer = null;
//
//        try {
//            getPath1 = Paths.get(pathToRequest);
//            file1 = new File(getPath1.toUri());
//            writer = new FileWriter(file1, StandardCharsets.UTF_8);
//
//            for (Request rec : inputList) {
//                String name = rec.getName();
//                String shortName = rec.getShortName();
//                writer.write(name + ";" + shortName);
//
//                if (inputList.iterator().hasNext()) {
//                    writer.write("\n");
//                }
//            }
//            System.out.println("Запись в файл request.csv прошла успешно.");
//        } catch (IOException ex) {
//            Logger.getLogger(Content.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            if (writer != null) {
//                try {
//                    writer.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(Content.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
//    }

}
