/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.requestconverter.service;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 *
 * @author 041AlikinOS
 */
public class Validator {
    
    public static void startWork(TextField target) {
    
        KeyFrame kf1 = new KeyFrame(Duration.millis(0), (t) -> {
                    target.setBorder(new Border(new BorderStroke(Color.BROWN, BorderStrokeStyle.SOLID, new CornerRadii(5.0), new BorderWidths(1, 1, 1, 1))));
                    
                });
                 KeyFrame kf2 = new KeyFrame(Duration.millis(500), (t) -> {
                    target.setBorder(Border.EMPTY);
                
                });
                Timeline tl = new Timeline(kf1, kf2);
                tl.setCycleCount(10);
                tl.setAutoReverse(true);
                tl.setOnFinished((b) -> {
                    target.setBorder(Border.EMPTY);
                });
                tl.play();
        
    }
    
}
