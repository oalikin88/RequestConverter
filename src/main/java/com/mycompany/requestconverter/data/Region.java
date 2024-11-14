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
    private int regionId;
    private String regionCode;
    private String regionName;
    private int spravochnikId;
    private List<Department> departments = new ArrayList<>();

    public Region() {
    }
    
    public Region(int regionId, String regionCode, String regionName, int spravochnikId) {
        this.regionId = regionId;
        this.regionCode = regionCode;
        this.regionName = regionName;
        this.spravochnikId = spravochnikId;
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

    public int getSpravochnikId() {
        return spravochnikId;
    }

    public void setSpravochnikId(int spravochnikId) {
        this.spravochnikId = spravochnikId;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.regionId;
        hash = 37 * hash + this.spravochnikId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Region other = (Region) obj;
        if (this.regionId != other.regionId) {
            return false;
        }
        return this.spravochnikId == other.spravochnikId;
    }
    
    
}
