/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.assignment02;

//Github repo: https://github.com/BoomEAE/Car-Race-Simulation.git

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
/**
 * FXML Controller class
 *
 * @author Eric Andre Evsei
 */
public class CarRaceFXMLController implements Initializable {
    @FXML
    private Rectangle pavementRect;
    @FXML
    private Group car1;
    @FXML
    private Line car1Line2;
    @FXML
    private Line car1Line1;
    @FXML
    private Group car2;
    @FXML
    private Line car2Line2;
    @FXML
    private Line car2Line1;
    @FXML
    private Group car3;
    @FXML
    private Line car3Line2;
    @FXML
    private Line car3Line1;
    @FXML
    private Group car4;
    @FXML
    private Line car4Line2;
    @FXML
    private Line car4Line1;
    @FXML
    private Button startButton;
    @FXML
    private Slider simSpeedSlider;
    @FXML
    private TextField simSpeedField;
    @FXML
    private Label raceProgressLbl;
    @FXML
    private Label raceDetailsLbl;
    @FXML
    private Label pos1Label;
    @FXML
    private ImageView imageView1st;
    @FXML
    private Label pos2Label;
    @FXML
    private ImageView imageView2nd;
    @FXML
    private Label pos3Label;
    @FXML
    private ImageView imageView3rd;
    @FXML
    private Label pos4Label;
    @FXML
    private ImageView imageView4th;
    
    private CarRaceModel carRaceModels[] = new CarRaceModel[4];
    
    //Images used for the podium
    private Image carModelImages[] = {
        new Image(getClass().getResource("/images/car1.png").toExternalForm()),
        new Image(getClass().getResource("/images/car2.png").toExternalForm()),
        new Image(getClass().getResource("/images/car3.png").toExternalForm()),
        new Image(getClass().getResource("/images/car4.png").toExternalForm())
    };
    
    private ImageView podiumImageViews[];
    private TranslateTransition translateTransitions[] = new TranslateTransition[4];
    private RotateTransition rotateTransitions[] = new RotateTransition[8];
    private Label podiumPositionsLabels[];
    private double trackLength = 930;
    private int carsFinished = 0;
    private boolean isSimPausedOrDone = true;
    
    //Keep track of who won first placeso we can update the info on the race when it's done accordingly
    private int carPos1st = 0;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Default info on race
        raceProgressLbl.setText("Race not started!");
        raceDetailsLbl.setText("Awaiting start of race...");
        
        //A 2d list of all podium position labels in order
        podiumPositionsLabels = new Label[]{pos1Label, pos2Label, pos3Label, pos4Label};
        
        //A list of all the podium image views that will show the car that won that position
        podiumImageViews = new ImageView[]{imageView1st, imageView2nd, imageView3rd, imageView4th};
        
        //Instance of CarRaceModels for each car, and constructor setup
        carRaceModels[0] = new CarRaceModel("Nissan Skyline", "32", new double[]{car1.getLayoutX(), car1.getLayoutY()}, speedGen(), "Gray", car1, new Line[]{car1Line1, car1Line2});
        carRaceModels[1] = new CarRaceModel("Mazda RX", "7", new double[]{car2.getLayoutX(), car2.getLayoutY()}, speedGen(), "Orange", car2, new Line[]{car2Line1, car2Line2});
        carRaceModels[2] = new CarRaceModel("Toyota AE", "86", new double[]{car3.getLayoutX(), car3.getLayoutY()}, speedGen(), "White", car3, new Line[]{car3Line1, car3Line2});
        carRaceModels[3] = new CarRaceModel("Mitsubishi Evo", "8", new double[]{car4.getLayoutX(), car4.getLayoutY()}, speedGen(), "Yellow", car4, new Line[]{car4Line1, car4Line2});
        
        //Setup for TranslateTransitions and RotateTransitions for each car
        for(int i = 0; i < 4; i++){
            
            //The car model, and the car itself
            CarRaceModel carRaceModel = carRaceModels[i];
            Group car = carRaceModel.getCar();
            
            //Get the duration of the animation for each car (the duration depends on the speed of the car)
            Duration animationDuration = animationDuration(carRaceModel);
            
            //There are only 2 car lines per car
            for(int j = 0; j < 2; j++){
                int rtsIndexPos = i * 2;
                if(j == 1) rtsIndexPos += 1;
                
                //RotateTransition setup for the specified line according to j index, and store it in rotateTransitions
                rotateTransitions[rtsIndexPos] = new RotateTransition(animationDuration, carRaceModel.getCarLines()[j]);
                rotateTransitions[rtsIndexPos].setFromAngle(0);
                rotateTransitions[rtsIndexPos].setByAngle(360);
                rotateTransitions[rtsIndexPos].setCycleCount(Animation.INDEFINITE);
            }
            
            //Setup of TranslateTransition of the car, and storing it in translatetransitions
            translateTransitions[i] = new TranslateTransition(animationDuration, car);
            
            //Event that happens when the TranslateTransition of the car is complete
            int index[] = {i};
            translateTransitions[i].setOnFinished(event -> {
                int carIndex = index[0];
                int rtsIndexPos = carIndex * 2;
                rotateTransitions[rtsIndexPos].stop();
                rotateTransitions[rtsIndexPos+1].stop();
                updateLeaderboard(carIndex);
            });
        }
        
        //Setup for the sim speed slider
        simSpeedField.textProperty().bind(simSpeedSlider.valueProperty().asString("%.1fx"));
        simSpeedSlider.setMajorTickUnit(0.5);
        simSpeedSlider.setMinorTickCount(0);
        simSpeedSlider.setShowTickMarks(true);
        simSpeedSlider.setSnapToTicks(true);
        
        //Add a listner to slider to change the speed of the simulation
        simSpeedSlider.valueProperty().addListener((ov, oldValue, newValue) -> {
            for(int i = 0; i < 4; i++){
                
                //We stop the animation, either way, and calculate the remaining duration x the sim speed set by the user
                translateTransitions[i].stop();
                translateTransitions[i].setDuration(animationDuration(carRaceModels[i]));
                
                //If the simulation was running, then just directly play all animations like nothing ever happened
                if(!isSimPausedOrDone){
                    translateTransitions[i].play();
                }
            }
        });
    }  
    
    @FXML
    private void startSimulation(ActionEvent event) {
        
        //Set the progress of the race, and info
        raceProgressLbl.setText("Race in progress!");
        raceDetailsLbl.setText("Awaiting results...");
        
        //If the simualtion was paused, but that the race was not complete
        //then start from where the simulation was paused
        if(carsFinished != 4){
            for(int i = 0; i < 4; i++){
                translateTransitions[i].setFromX(carRaceModels[i].getPosition()[0]);
                translateTransitions[i].setToX(trackLength);
                
                //Calculate the speed of the animation according to how far the car is from the finish line
                translateTransitions[i].setDuration(animationDuration(carRaceModels[i]));
                
                //play animations
                translateTransitions[i].play();
                int rtsIndexPos = i * 2;
                rotateTransitions[rtsIndexPos].play();
                rotateTransitions[rtsIndexPos+1].play();
            }
        }
        
        //Disable start button, since the simualtion is already running
        startButton.setDisable(true);
    }

    @FXML
    private void pauseSimulation(ActionEvent event) {
        
        //Set true since we want to track the state of the simualtion
        isSimPausedOrDone = true;
        
        //Set the progress of the race, and info
        raceProgressLbl.setText("Race paused!");
        raceDetailsLbl.setText("Racers are taking a break");
        
        //Stop every animation, and set the new position by storing it in the carRaceModel
        for(int i = 0; i < 4; i++){
            translateTransitions[i].stop();
            rotateTransitions[i].stop();
            carRaceModels[i].setPosition(new double[]{carRaceModels[i].getCar().getTranslateX(), carRaceModels[i].getPosition()[1]});
        }
        
        //Reenable the start button
        startButton.setDisable(false);
    }

    @FXML
    private void resetSimulation(ActionEvent event) {
        
        //Default info on race
        raceProgressLbl.setText("Race not started!");
        raceDetailsLbl.setText("Awaiting start of race...");
        
        //Reset everything such as labels, speeds (new speed), and positions (starting position)
        for(int i = 0; i < 4; i++){
            int rtsIndexPos = i * 2;
            rotateTransitions[rtsIndexPos].stop();
            rotateTransitions[rtsIndexPos+1].stop();
            translateTransitions[i].stop();
            carRaceModels[i].setSpeed(speedGen());
            carRaceModels[i].setPosition(new double[]{15, carRaceModels[i].getPosition()[1]});
            carRaceModels[i].getCar().setTranslateX(0);
            carRaceModels[i].getCar().setLayoutX(15);
            System.out.println(carRaceModels[i].getCar().getLayoutX());
            translateTransitions[i].setFromX(15);
            translateTransitions[i].setToX(trackLength);
            podiumPositionsLabels[i].setText("N/A");
            podiumImageViews[i].setImage(null);
            carsFinished = 0;
        }
        
        //Reenable start button
        startButton.setDisable(false);
    }
    
    //Get a random speed
    private double speedGen(){
        Random rand = new Random();
        return (rand.nextDouble() * 30) + 10;
    }
    
    //Calculate the duration according to the distance left between the car and the finish line
    private Duration animationDuration(CarRaceModel carRaceModel){
        Group car = carRaceModel.getCar();
        return new Duration((trackLength - car.getLayoutX())/(simSpeedSlider.getValue()*carRaceModel.getSpeed()*100000/3600000));
    }
    
    //To update the leaderboard
    private void updateLeaderboard(int carIndex){
        
        //Loop and check if (starting from the 1st position in the leaderboard) the position as been taken
        //If not, update the labels, and the imageview to show the car that won the position
        for(int i = 0; i < podiumPositionsLabels.length; i++){
            if(podiumPositionsLabels[i].getText().equals("N/A")){
                podiumPositionsLabels[i].setText(carRaceModels[carIndex].getType() + " #" + carRaceModels[carIndex].getNumber() + " (" + carRaceModels[carIndex].getColor() + ")");
                podiumImageViews[i].setImage(carModelImages[carIndex]);
                
                //Store the index of position 1 for later
                if(i == 0){
                    carPos1st = carIndex;
                }
                break;
            }
        }
        carsFinished++;
        
        //When the race is finished, update the race info ,and who won
        if(carsFinished == 4){
            raceProgressLbl.setText("Race Finished!");
            raceDetailsLbl.setText("Congradulations! " + carRaceModels[carPos1st].getType() + " #" + carRaceModels[carPos1st].getNumber() + " (" + carRaceModels[carPos1st].getColor() + ") is the winner!");
        }
    }

}
