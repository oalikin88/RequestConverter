/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.requestconverter.data;

/**
 *
 * @author Alikin Oleg
 */
public class SubRequest {
    private int id;
    private int requestId;
    private String subRequestCode;
    private String subRequestName;

    public SubRequest() {
    }

    public SubRequest(String subRequestCode, String subRequestName, int requestId) {
        this.subRequestCode = subRequestCode;
        this.subRequestName = subRequestName;
        this.requestId = requestId;
    }

    public int getId() {
        return id;
    }


    public String getSubRequestCode() {
        return subRequestCode;
    }

    public void setSubRequestCode(String subRequestCode) {
        if (!subRequestCode.isEmpty() && !subRequestCode.isBlank()) {
            this.subRequestCode = subRequestCode.trim();
        }
    }

    public String getSubRequestName() {
        return subRequestName;
    }

    public void setSubRequestName(String subRequestName) {
        if (!subRequestName.isEmpty() && !subRequestName.isBlank()) {
            this.subRequestName = subRequestName.trim();
        }
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        if (requestId > 0) {
            this.requestId = requestId;
        }
    }

    public void setId(int id) {
        if (id > 0) {
            this.id = id;
        }
    }
    
    
}
