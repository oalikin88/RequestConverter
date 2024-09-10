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
    private String regionCode;
    private String departmentCode;
    private String departmentName;

    public Department() {
    }
    
    public Department(String regionCode, String departmentCode, String departmentName) {
        this.regionCode = regionCode;
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
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
    
    
}
