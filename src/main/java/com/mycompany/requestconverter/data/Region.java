/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.requestconverter.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alikin Oleg
 */
public class Region {
    
    private String regionCode;
    private String regionName;
    private List<Department> departments = new ArrayList<>();

    public Region() {
    }
    
    public Region(String regionCode, String regionName) {
        this.regionCode = regionCode;
        this.regionName = regionName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        if(!regionCode.isEmpty() && !regionCode.isBlank()) {
            this.regionCode = regionCode;
        }
        
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        if(!regionName.isEmpty() && !regionName.isBlank()) {
        this.regionName = regionName;
        }
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
    
    public void addDepartment(Department department) {
        this.departments.add(department);
    }
    
}
