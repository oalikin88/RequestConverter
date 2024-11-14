/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.requestconverter.data;

import java.util.regex.Pattern;

/**
 *
 * @author 041slavkovia
 */
public class ConvertExecutor {
    
        public String convertToFileName(ConvertOptions options) {
        String result = options.getPattern();
        result = replacePattern("ООО", options.getOo(), result);
        result = replacePattern("ТТТ", options.getTo(), result);
        result = replacePattern("РРР", options.getRo(), result);
        result = replacePattern("ФАМ", options.getSurname(), result);
        result = replacePattern("СНЛ", options.getSnils(), result);
        result = replacePattern("И", safeGetInitials(options.getName()), result);
        result = replacePattern("О", safeGetInitials(options.getMiddlename()), result);
        result = replacePattern("ИИ", options.getName(), result);
        result = replacePattern("ОО", options.getMiddlename(), result);
        result = replacePattern("ЗПР", options.getRequest(), result);
        result = replacePattern("ПЗПР", options.getSubRequest(), result);
        result = replacePattern("ДД", options.getDay(), result);
        result = replacePattern("ММ", options.getMonth(), result);
        result = replacePattern("КСНОМ", options.getDepartmentNumber(), result);
        result = replacePattern("Р", options.getNumber(), result);
        return result;
    }

    private String safeGetInitials(String value) {
        if (value == null || value.isBlank() || value.isEmpty()) {
            return null;
        }
        return value.substring(0, 1);
    }

    private String replacePattern(String patternString, String newValue,
            String target) {
        if (newValue == null) {
            return target;
        }
        Pattern pattern = Pattern.compile("\\{" + patternString + "\\}");
        return pattern.matcher(target).replaceAll(newValue);
    }
    
    
}
