/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.requestconverter.data;

import com.mycompany.requestconverter.connection.DBConnection;
import com.mycompany.requestconverter.exceptions.DaoException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 041AlikinOS
 */
public class ClientDAO {
    
    private static final String GET_LAST_CHANGE_FROM_SPR = "SELECT * FROM requestconverter.spr_history WHERE dt_datetime IN (SELECT max(dt_datetime) FROM requestconverter.spr_history)";
    private static final String GET_LAST_CHANGE_FROM_REQUEST = "SELECT * FROM requestconverter.request_history WHERE dt_datetime IN (SELECT max(dt_datetime) FROM requestconverter.request_history)";
    
    private static final String FIND_ALL_SPR = "SELECT id, subject, opfr, upfr, name FROM spr";
    private static final String FIND_ALL_REQUEST = "SELECT id, name, short_name FROM request";
    
    public List<Record> findAllRecords() {
        try ( var connection = DBConnection.getInstance().getConnection();  var preparedStatement = connection.prepareStatement(FIND_ALL_SPR)) {
            var resultSet = preparedStatement.executeQuery();
            List<Record> records = new ArrayList<>();
            while (resultSet.next()) {
                records.add(buildRecord(resultSet));
            }
            return records;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw new DaoException(throwable);
        }
    }
    
    
      public List<Request> findAllRequests() {
        try ( var connection = DBConnection.getInstance().getConnection();  var preparedStatement = connection.prepareStatement(FIND_ALL_REQUEST)) {
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
      
      
         private Record buildRecord(ResultSet resultSet) throws SQLException {

        Record record = new Record();
        record.setId(resultSet.getInt("id"));
        record.setSubject(resultSet.getString("subject"));
        record.setOpfr(resultSet.getString("opfr"));
        record.setUpfr(resultSet.getString("upfr"));
        record.setName(resultSet.getString("name"));
        return record;
    }
    
    private Request buildRequest(ResultSet resultSet) throws SQLException {
        Request request = new Request();
        request.setId(resultSet.getInt("id"));
        request.setName(resultSet.getString("name"));
        request.setShortName(resultSet.getString("short_name"));
        return request;       
    }
    
    public List<DataHistory> getLastChangeFromSpr() throws ParseException {
        try ( var connection = DBConnection.getInstance().getConnection();  var preparedStatement = connection.prepareStatement(GET_LAST_CHANGE_FROM_SPR)) {
            var resultSet = preparedStatement.executeQuery();
            List<DataHistory> sprs = new ArrayList<>();
            while (resultSet.next()) {
                sprs.add(buildDataHistory(resultSet));
            }
            return sprs;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw new DaoException(throwable);
        }
    }
    
     public List<DataHistory> getLastChangeFromRequest() throws ParseException {
        try ( var connection = DBConnection.getInstance().getConnection();  var preparedStatement = connection.prepareStatement(GET_LAST_CHANGE_FROM_REQUEST)) {
            var resultSet = preparedStatement.executeQuery();
            List<DataHistory> requests = new ArrayList<>();
            while (resultSet.next()) {
                requests.add(buildDataHistory(resultSet));
            }
            return requests;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            throw new DaoException(throwable);
        }
    }
    
    
       private DataHistory buildDataHistory(ResultSet resultSet) throws SQLException, ParseException {
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        
        DataHistory dataHistory = new DataHistory();
        dataHistory.setId(resultSet.getInt("id"));
        dataHistory.setAction(resultSet.getString("action"));
        String datetime = resultSet.getString("dt_datetime");
        java.util.Date parseDate = df.parse(datetime);
        dataHistory.setDate(parseDate);
       
        return dataHistory;
    }
    
    
}
