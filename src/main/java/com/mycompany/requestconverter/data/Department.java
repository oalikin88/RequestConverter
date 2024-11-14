/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.requestconverter.data;

/**
 *
 * @author Alikin Oleg
 */
public class Department {
    private int departmentId;
    private String regionCode;
    private String territoryCode;
    private String departmentCode;
    private String departmentName;
    private int spravochnikId;

    public Department() {
    }
    
    public Department(int departmentId, String regionCode, String territoryCode, String departmentCode, String departmentName, int spravochnikId) {
        this.departmentId = departmentId;
        this.regionCode = regionCode;
        this.territoryCode = territoryCode;
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
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

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        if(!departmentCode.isEmpty() && !departmentCode.isBlank()){
        this.departmentCode = departmentCode;
        }
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        if(!departmentName.isEmpty() && !departmentName.isBlank()) {
        this.departmentName = departmentName;
        }
    }

    public int getSpravochnikId() {
        return spravochnikId;
    }

    public void setSpravochnikId(int spravochnikId) {
        this.spravochnikId = spravochnikId;
    }

    public String getTerritoryCode() {
        return territoryCode;
    }

    public void setTerritoryCode(String territoryCode) {
        this.territoryCode = territoryCode;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + this.departmentId;
        hash = 17 * hash + this.spravochnikId;
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
        final Department other = (Department) obj;
        if (this.departmentId != other.departmentId) {
            return false;
        }
        return this.spravochnikId == other.spravochnikId;
    }
    
    
    
    
}
