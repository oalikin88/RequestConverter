/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.requestconverter.data;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Alikin Oleg
 */
@Getter
@Setter
public class Pattern {
    private int patternId;
    private String pattern;
    private List<Fields> fields = new ArrayList<>();
    
    public void addField(Fields field) {
        fields.add(field);
    }
}
