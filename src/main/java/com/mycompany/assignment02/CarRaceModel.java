/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment02;

import javafx.scene.paint.Color;

/**
 *
 * @author Eric Andre Evsei
 */
public class CarRaceModel {
    private String type;
    private String number;
    private double[] position;
    private double speed;
    private String color;
    
    public CarRaceModel(String type, String number, double[] position, double speed, String color){
        this.type = type;
        this.number = number;
        this.position = position;
        this.speed = speed;
        this.color = color;
    }
    
    public String getType(){
        return type;
    }
    
    public String getNumber(){
        return number;
    }
    
    public double[] getPosition(){
        return position;
    }
    
    public void setSpeed(double speed){
        this.speed = speed;
    }
    
    public double getSpeed(){
        return speed;
    }
    
    public String getColor(){
        return color;
    }
}
