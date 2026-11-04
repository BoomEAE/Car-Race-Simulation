/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.assignment02;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.shape.Line;
/**
 * FXML Controller class
 *
 * @author ERIC -Yep-
 */
public class CarRaceFXMLController implements Initializable {


    @FXML
    private Group car1;
    @FXML
    private Line car1Rim;
    @FXML
    private Group car2;
    @FXML
    private Line car2Rim;
    @FXML
    private Group car3;
    @FXML
    private Line car3Rim;
    @FXML
    private Group car4;
    @FXML
    private Line car4Rim;
    @FXML
    private Button startButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button resetButton;
    @FXML
    private Slider simSpeedSlider;
    @FXML
    private Label simSpeedLabel;
    @FXML
    private Label typeLabel1;
    @FXML
    private Label colorLabel1;
    @FXML
    private Label typeLabel2;
    @FXML
    private Label colorLabel2;
    @FXML
    private Label typeLabel3;
    @FXML
    private Label colorLabel3;
    @FXML
    private Label typeLabel4;
    @FXML
    private Label colorLabel4;
    
    Random rand = new Random();
    private CarRaceModel carRaceModels[] = new CarRaceModel[4];
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        carRaceModels[0] = new CarRaceModel("Nissan Skyline", "32", new double[]{car1.getLayoutX(), car1.getLayoutY()}, (rand.nextDouble() * 50) + 10, "Gray");
        
        carRaceModels[1] = new CarRaceModel("Mazda RX", "7", new double[]{car2.getLayoutX(), car2.getLayoutY()}, (rand.nextDouble() * 50) + 10, "Orange");
        
        carRaceModels[2] = new CarRaceModel("Toyota AE", "86", new double[]{car3.getLayoutX(), car3.getLayoutY()}, (rand.nextDouble() * 50) + 10, "White");
        
        carRaceModels[3] = new CarRaceModel("Mitsubishi Evo", "8", new double[]{car4.getLayoutX(), car4.getLayoutY()}, (rand.nextDouble() * 50) + 10, "Yellow");
        
    }  
    
    @FXML
    private void startSimulation(ActionEvent event) {
    }

    @FXML
    private void pauseSimulation(ActionEvent event) {
    }

    @FXML
    private void resetSimulation(ActionEvent event) {
    }

}
