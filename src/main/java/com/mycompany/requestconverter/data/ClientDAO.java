/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.requestconverter.data;

import com.mycompany.requestconverter.connection.DBConnection;
import com.mycompany.requestconverter.exceptions.DaoException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 041AlikinOS
 */
public class ClientDAO {
    
    private static final String GET_LAST_CHANGE_FROM_SPR = "SELECT * FROM requestconverter.spr_history WHERE dt_datetime IN (SELECT max(dt_datetime) FROM requestconverter.spr_history)";
    private static final String GET_LAST_CHANGE_FROM_REQUEST = "SELECT * FROM requestconverter.request_history WHERE dt_datetime IN (SELECT max(dt_datetime) FROM requestconverter.request_history)";
    
    private static final String FIND_ALL_REGIONS = "SELECT region_id, region_code, region_name, spravochnik_id FROM regions ORDER BY region_code ";
    private static final String FIND_ALL_SPR = "SELECT id, name FROM spravochnik";
    private static final String FIND_ALL_DEPARTMENTS = "SELECT department_id, region_code, territory_code, department_code, department_name, spravochnik_id FROM departments ORDER BY region_code, department_code ";
    private static final String FIND_ALL_REQUEST =  "select r.request_id, r.request_code, r.request_name, r.spravochnik_id, s.name, p.pattern_id, p.pattern from request r " +
                                                    "join pattern p " +
                                                    "on p.pattern_id = r.pattern_id " +
                                                    "join spravochnik s " +
                                                    "on r.spravochnik_id = s.id ";
    private static final String FIND_ALL_SUB_REQUEST = "SELECT sreq.sub_request_id, sreq.sub_request_code, sreq.sub_request_name, sreq.request_id FROM sub_request sreq";
   
    private static final String FIND_FIELDS_FOR_PATTERNS = "select p.pattern_id, f.field_id, f.field  from pattern p " +
                                                            "join pattern_fields pf " +
                                                            "on p.pattern_id = pf.pattern_id " +
                                                            "join fields f " +
                                                            "on pf.field_id = f.field_id ";
    
    
//    public List<Record> findAllRecords(String sprType) throws IOException, URISyntaxException, ClassNotFoundException, SQLException {
//        String request = FIND_ALL_SPR.replace("spr", sprType);
//        DBConnection dBConnection = new DBConnection();
//         try ( var connection = dBConnection.getConnection();  var preparedStatement = connection.prepareStatement(request)) {
//            var resultSet = preparedStatement.executeQuery();
//            List<Record> records = new ArrayList<>();
//            while (resultSet.next()) {
//                records.add(buildRecord(resultSet));
//            }
//            return records;
//        } catch (SQLException throwable) {
//            throwable.printStackTrace();
//            throw new DaoException(throwable);
//        }
//    }
    
    public List<Spravochnik> findAllSpr() throws IOException, URISyntaxException, ClassNotFoundException, SQLException {
        DBConnection dBConnection = new DBConnection();
        try ( var connection = dBConnection.getConnection();  var preparedStatement = connection.prepareStatement(FIND_ALL_SPR)) {
            var resultSet = preparedStatement.executeQuery();
            List<Spravochnik> catalogs = new ArrayList<>();
            while(resultSet.next()) {
               catalogs.add(buildSpr(resultSet));
            }
            return catalogs;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw new DaoException(throwable);
        }
    }
    
    
    public List<Fields> findAllFields() throws IOException, URISyntaxException, ClassNotFoundException, SQLException {
        DBConnection dBConnection = new DBConnection();
        try ( var connection = dBConnection.getConnection();  var preparedStatement = connection.prepareStatement(FIND_FIELDS_FOR_PATTERNS)) {
            var resultSet = preparedStatement.executeQuery();
            List<Fields> fields = new ArrayList<>();
            while(resultSet.next()) {
                fields.add(buildField(resultSet));
            }
            return fields;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw new DaoException(throwable);
        }
    }
    
    public List<Region> findAllRegions() throws IOException, URISyntaxException, ClassNotFoundException, SQLException {
        DBConnection dBConnection = new DBConnection();
        try ( var connection = dBConnection.getConnection();  var preparedStatement = connection.prepareStatement(FIND_ALL_REGIONS)) {
            var resultSet = preparedStatement.executeQuery();
            List<Region> regions = new ArrayList<>();
            while(resultSet.next()) {
                regions.add(buildRegion(resultSet));
            }
            return regions;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw new DaoException(throwable);
        }
    }
    
    public List<Department> findAllDepartments() throws IOException, URISyntaxException, ClassNotFoundException, SQLException {
        DBConnection dBConnection = new DBConnection();
        try ( var connection = dBConnection.getConnection();  var preparedStatement = connection.prepareStatement(FIND_ALL_DEPARTMENTS)) {
            var resultSet = preparedStatement.executeQuery();
            List<Department> departments = new ArrayList<>();
            while(resultSet.next()) {
                departments.add(buildDepartment(resultSet));
            }
            return departments;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw new DaoException(throwable);
        }
    }
    
    
      public List<Request> findAllRequests() throws IOException, URISyntaxException, ClassNotFoundException, SQLException {
          DBConnection dBConnection = new DBConnection();
        try ( var connection = dBConnection.getConnection();  var preparedStatement = connection.prepareStatement(FIND_ALL_REQUEST)) {
            var resultSet = preparedStatement.executeQuery();
            List<Request> requests = new ArrayList<>();
            while (resultSet.next()) {
                requests.add(buildRequest(resultSet));
            }
            return requests;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw new DaoException(throwable);
        } 
    }
      
    public List<SubRequest> findAllSubRequests() throws IOException, URISyntaxException, ClassNotFoundException, SQLException {
          DBConnection dBConnection = new DBConnection();
        try ( var connection = dBConnection.getConnection();  var preparedStatement = connection.prepareStatement(FIND_ALL_SUB_REQUEST)) {
            var resultSet = preparedStatement.executeQuery();
            List<SubRequest> requests = new ArrayList<>();
            while (resultSet.next()) {
                requests.add(buildSubRequest(resultSet));
            }
            return requests;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw new DaoException(throwable);
        } 
    }
      
      
//         private Record buildRecord(ResultSet resultSet) throws SQLException {
//
//        Record record = new Record();
//        record.setId(resultSet.getInt("id"));
//        record.setSubject(resultSet.getString("subject"));
//        record.setOpfr(resultSet.getString("opfr"));
//        record.setUpfr(resultSet.getString("upfr"));
//        record.setName(resultSet.getString("name"));
//        return record;
//    }
      
    
       private Spravochnik buildSpr(ResultSet resultSet) throws SQLException {
           Spravochnik spravochnik = new Spravochnik();
           spravochnik.setId(resultSet.getInt("id"));
           spravochnik.setName(resultSet.getString("name"));
           return spravochnik;
       }
    
       private Region buildRegion(ResultSet resultSet) throws SQLException {

        Region region = new Region();
        region.setRegionId(resultSet.getInt("region_id"));
        region.setRegionCode(resultSet.getString("region_code"));
        region.setRegionName(resultSet.getString("region_name"));
        region.setSpravochnikId(resultSet.getInt("spravochnik_id"));
        return region;
    }
       
        private Fields buildField(ResultSet resultSet) throws SQLException {

        Fields field = new Fields();
        field.setFieldId(resultSet.getInt("field_id"));
        field.setField(resultSet.getString("field"));
        field.setPatternId(resultSet.getInt("pattern_id"));
        return field;
    }
       
        private Department buildDepartment(ResultSet resultSet) throws SQLException {

        Department department = new Department();
        department.setDepartmentId(resultSet.getInt("department_id"));
        department.setRegionCode(resultSet.getString("region_code"));
        department.setTerritoryCode(resultSet.getString("territory_code"));
        department.setDepartmentCode(resultSet.getString("department_code"));
        department.setDepartmentName(resultSet.getString("department_name"));
        department.setSpravochnikId(resultSet.getInt("spravochnik_id"));
        return department;
    }
    
    private Request buildRequest(ResultSet resultSet) throws SQLException {
        
        Request request = new Request();
        Spravochnik spr = new Spravochnik();
        Pattern pattern = new Pattern();
        
        request.setId(resultSet.getInt("request_id"));
        request.setName(resultSet.getString("request_name"));
        request.setRequestCode(resultSet.getString("request_code"));
        spr.setId(resultSet.getInt("spravochnik_id"));
        spr.setName(resultSet.getString("name"));
        pattern.setPattern(resultSet.getString("pattern"));
        pattern.setPatternId(resultSet.getInt("pattern_id"));
        request.setPattern(pattern);
        request.setSpravochnik(spr);
        return request;       
    }
    
    private SubRequest buildSubRequest(ResultSet resultSet) throws SQLException {
        SubRequest subRequest = new SubRequest();
        subRequest.setId(resultSet.getInt("sub_request_id"));
        subRequest.setSubRequestCode(resultSet.getString("sub_request_code"));
        subRequest.setSubRequestName(resultSet.getString("sub_request_name"));
        subRequest.setRequestId(resultSet.getInt("request_id"));
        return subRequest;       
    }
    
//    public List<DataHistory> getLastChangeFromSpr(String sprType) throws ParseException, IOException, URISyntaxException, ClassNotFoundException, SQLException {
//        String request = GET_LAST_CHANGE_FROM_SPR.replace("spr", sprType);
//       DBConnection dBConnection = new DBConnection();
//        try ( var connection = dBConnection.getConnection();
//                var preparedStatement = connection.prepareStatement(request)) {             
//            var resultSet = preparedStatement.executeQuery();
//            List<DataHistory> sprs = new ArrayList<>();
//            while (resultSet.next()) {
//                sprs.add(buildDataHistory(resultSet));
//            }
//            return sprs;
//        } catch (Exception throwable) {
//            throwable.printStackTrace();
//            throw new DaoException(throwable);
//        } 
//
//    } 
//    
//     public List<DataHistory> getLastChangeFromRequest() throws ParseException, IOException, URISyntaxException, ClassNotFoundException, SQLException {
//         DBConnection dBConnection = new DBConnection();
//        try ( var connection = dBConnection.getConnection();  var preparedStatement = connection.prepareStatement(GET_LAST_CHANGE_FROM_REQUEST)) {
//            var resultSet = preparedStatement.executeQuery();
//            List<DataHistory> requests = new ArrayList<>();
//            while (resultSet.next()) {
//                requests.add(buildDataHistory(resultSet));
//            }
//            return requests;
//        } catch (SQLException throwable) {
//            throwable.printStackTrace();
//            throw new DaoException(throwable);
//        } 
//    }
//    
//    
//       private DataHistory buildDataHistory(ResultSet resultSet) throws SQLException, ParseException {
//      DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        
//        DataHistory dataHistory = new DataHistory();
//        dataHistory.setId(resultSet.getInt("id"));
//        dataHistory.setAction(resultSet.getString("action"));
//        String datetime = resultSet.getString("dt_datetime");
//        java.util.Date parseDate = df.parse(datetime);
//        dataHistory.setDate(parseDate);
//       
//        return dataHistory;
//    }
    
    
}
