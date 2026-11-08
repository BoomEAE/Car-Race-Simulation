/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.assignment02;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;
import javafx.util.Duration;

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
    private Slider simSpeedSlider;
    @FXML
    private TextField simSpeedField;
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
    
    private CarRaceModel carRaceModels[] = new CarRaceModel[4];
    private TranslateTransition translateTransitions[] = new TranslateTransition[4];
    private RotateTransition rotateTransitions[] = new RotateTransition[4];
    private Label podiumPositionsLabels[][];
    private double trackLength = 500;
    private int carsFinished = 0;
    private boolean isSimPaused = false;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //
        podiumPositionsLabels = new Label[][]{{typeLabel1, colorLabel1}, {typeLabel2, colorLabel2}, {typeLabel3, colorLabel3}, {typeLabel4, colorLabel4}};
        
        //
        carRaceModels[0] = new CarRaceModel("Nissan Skyline", "32", new double[]{car1.getLayoutX(), car1.getLayoutY()}, speedGen(), "Gray", car1, car1Rim);
        carRaceModels[1] = new CarRaceModel("Mazda RX", "7", new double[]{car2.getLayoutX(), car2.getLayoutY()}, speedGen(), "Orange", car2, car2Rim);
        carRaceModels[2] = new CarRaceModel("Toyota AE", "86", new double[]{car3.getLayoutX(), car3.getLayoutY()}, speedGen(), "White", car3, car3Rim);
        carRaceModels[3] = new CarRaceModel("Mitsubishi Evo", "8", new double[]{car4.getLayoutX(), car4.getLayoutY()}, speedGen(), "Yellow", car4, car4Rim);
        
        //
        for(int i = 0; i < 4; i++){
            CarRaceModel carRaceModel = carRaceModels[i];
            Group car = carRaceModel.getCar();
            Duration animationDuration = animationDuration(carRaceModel);
            translateTransitions[i] = new TranslateTransition(animationDuration, car);
            rotateTransitions[i] = new RotateTransition(animationDuration, carRaceModel.getCarRim());
            rotateTransitions[i].setByAngle(360);
            rotateTransitions[i].setCycleCount(Animation.INDEFINITE);
            
            int index[] = {i};
            translateTransitions[i].setOnFinished(event -> {
                int carIndex = index[0];
                rotateTransitions[carIndex].stop();
                updateLeaderboard(carIndex);
            });
        }
        
        //
        simSpeedField.textProperty().bind(simSpeedSlider.valueProperty().asString("%.1fx"));
        simSpeedSlider.setMajorTickUnit(0.5);
        simSpeedSlider.setMinorTickCount(0);
        simSpeedSlider.setShowTickMarks(true);
        simSpeedSlider.setSnapToTicks(true);
        
        //
        simSpeedSlider.valueProperty().addListener((ov, oldValue, newValue) -> {
            for(int i = 0; i < 4; i++){
                translateTransitions[i].stop();
                
                translateTransitions[i].setDuration(animationDuration(carRaceModels[i]));
                
                if(!isSimPaused){
                    translateTransitions[i].play();
                }
            }
        });
    }  
    
    @FXML
    private void startSimulation(ActionEvent event) {
        if(carsFinished != 4){
            for(int i = 0; i < 4; i++){
                translateTransitions[i].setFromX(carRaceModels[i].getPosition()[0]);
                translateTransitions[i].setToX(trackLength);
                translateTransitions[i].setDuration(animationDuration(carRaceModels[i]));
                translateTransitions[i].play();
                rotateTransitions[i].play();
            }
        }
        
        startButton.setDisable(true);
    }

    @FXML
    private void pauseSimulation(ActionEvent event) {
        isSimPaused = true;
        
        for(int i = 0; i < 4; i++){
            translateTransitions[i].stop();
            rotateTransitions[i].stop();
            carRaceModels[i].setPosition(new double[]{carRaceModels[i].getCar().getTranslateX(), carRaceModels[i].getPosition()[1]});
        }
        
        startButton.setDisable(false);
    }

    @FXML
    private void resetSimulation(ActionEvent event) {
        for(int i = 0; i < 4; i++){
            translateTransitions[i].stop();
            rotateTransitions[i].stop();
            carRaceModels[i].setSpeed(speedGen());
            carRaceModels[i].setPosition(new double[]{0, carRaceModels[i].getPosition()[1]});
            carRaceModels[i].getCar().setTranslateX(0);
            carRaceModels[i].getCar().setLayoutX(0);
            System.out.println(carRaceModels[i].getCar().getLayoutX());
            translateTransitions[i].setFromX(0);
            translateTransitions[i].setToX(trackLength);
            podiumPositionsLabels[i][0].setText("N/A");
            podiumPositionsLabels[i][1].setText("N/A");
            carsFinished = 0;
        }
        
        startButton.setDisable(false);
    }
    
    private double speedGen(){
        Random rand = new Random();
        return (rand.nextDouble() * 50) + 10;
    }
    
    private Duration animationDuration(CarRaceModel carRaceModel){
        Group car = carRaceModel.getCar();
        return new Duration((trackLength - car.getLayoutX())/(simSpeedSlider.getValue()*carRaceModel.getSpeed()*100000/3600000));
    }
    
    private void updateLeaderboard(int carIndex){
        for(Label[] positionLabels : podiumPositionsLabels){
            if(positionLabels[0].getText().equals("N/A") && positionLabels[1].getText().equals("N/A")){
                positionLabels[0].setText(carRaceModels[carIndex].getType());
                positionLabels[1].setText(carRaceModels[carIndex].getColor());
                break;
            }
        }
        carsFinished++;
    }

}
