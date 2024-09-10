/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.requestconverter.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 041AlikinOS
 */
public class Request {

    private int id;
    private String name;
    private String requestCode;
    private List<SubRequest> subRequests = new ArrayList<>();
    
    public Request() {
    }

    public Request(String name) {
        this.name = name;
    }

    public Request(String name, String requestCode) {
        this.name = name;
        this.requestCode = requestCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id > 0) {
            this.id = id;
        }
    }

    
    public List<SubRequest> getSubRequests() {
        return subRequests;
    }

    public void setSubRequests(List<SubRequest> subRequests) {
        this.subRequests = subRequests;
    }
    
    public void addSubRequest(SubRequest subRequest) {
        this.subRequests.add(subRequest);
    }


    @Override
    public String toString() {
        return this.name;
    }
}
