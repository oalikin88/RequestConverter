/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.requestconverter.data;

import lombok.Builder;
import lombok.Getter;

/**
 *
 * @author 041slavkovia
 */


@Getter
@Builder

public class ConvertOptions {
    /**
     * <pre>Шаблон с использованием которого будут конвертироваться данные.
     * Используются следующие параметры:
     * <ul>
     *  <li><b>{OOO}</b> - код отделения</li>
     *  <li><b>{ТТТ}</b> - код территориального органа</li>
     *  <li><b>{РРР}</b> - код районного органа</li>
     *  <li><b>{ФАМ}</b> - фамилия</li>
     *  <li><b>{И}</b> - имя сокращенное до одной буквы</li>
     *  <li><b>{ИИ}</b> - имя полное</li>
     *  <li><b>{О}</b> - отчество сокращенное до одной буквы</li>
     *  <li><b>{ОО}</b> - отчество полное</li>
     *  <li><b>{СНЛ}</b> - СНИЛС в полном виде</li>
     *  <li><b>{ЗПР}</b> - Код запроса </li>
     *  <li><b>{ПЗПР}</b> - Подзапрос </li>
     *  <li><b>{ДД}</b> - Дата </li>
     *  <li><b>{ММ}</b> - Месяц </li>
     *  <li><b>{КСНОМ}</b> - номер клиентской службы, принявшей и направляющей документы </li>
     *  <li><b>{Р}</b> - порядок в запросе сведений об умерших </li>
     * </ul>
     * </pre>
     */
    
    private String pattern;
    private String snils;
    private String surname;
    private String name;
    private String middlename;
    private String oo;
    private String ro;
    private String to;
    private String request;
    private String subRequest;
    private String day;
    private String month;
    private String departmentNumber;
    private String number;
    
    
}
