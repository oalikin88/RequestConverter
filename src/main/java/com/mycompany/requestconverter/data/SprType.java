/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.requestconverter.data;

/**
 *
 * @author 041AlikinOS
 */
public enum SprType {
    
    SPR_REGIONS("regions"),
    SPR_DEPARTMENTS("departments");
    
    private String title;

    private SprType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
    
    
    
    
}
