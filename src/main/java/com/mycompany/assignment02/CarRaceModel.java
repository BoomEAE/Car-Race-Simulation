/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment02;

import javafx.scene.Group;
import javafx.scene.shape.Line;

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
    private Group car;
    private Line carLines[];
    
    public CarRaceModel(String type, String number, double[] position, double speed, String color, Group car, Line carLines[]){
        this.type = type;
        this.number = number;
        this.position = position;
        this.speed = speed;
        this.color = color;
        this.car = car;
        this.carLines = carLines;
    }
    
    public String getType(){
        return type;
    }
    
    public String getNumber(){
        return number;
    }
    
    public void setPosition(double[] position){
        this.position = position;
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
    
    public Group getCar(){
        return car;
    }
    
    public Line[] getCarLines(){
        return carLines;
    }
}
