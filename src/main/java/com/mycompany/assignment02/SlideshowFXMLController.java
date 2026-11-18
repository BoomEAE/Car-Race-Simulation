/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.assignment02;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.FillTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
/**
 * FXML Controller class
 *
 * @author ERIC -Yep-
 */
public class SlideshowFXMLController implements Initializable {


    @FXML
    private Pane pane;
    @FXML
    private Rectangle background;
    @FXML
    private VBox welcomeMessage;
    @FXML
    private ImageView imageView4;
    @FXML
    private ImageView imageView3;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView imageView1;
    @FXML
    private Rectangle whiteRect;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //
        Media media = new Media(getClass().getResource("/sounds/slideshowMusic.wav").toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        
        //Fade out transition for whiteRect
        FadeTransition ft1 = new FadeTransition(new Duration(2000), whiteRect);
        ft1.setFromValue(1.0);
        ft1.setToValue(0.0);
        
        //An animation pause before the next animation
        PauseTransition pt1 = new PauseTransition(new Duration(2000));
        
        //Fade out transition for imageView1 (slide 1)
        FadeTransition ft2 = new FadeTransition(new Duration(2000), imageView1);
        ft2.setFromValue(1.0);
        ft2.setToValue(0.0);
        
        //An animation pause before the next animation
        PauseTransition pt2 = new PauseTransition(new Duration(2000));
        
        //Fade out transition for imageView2 (slide 2)
        FadeTransition ft3 = new FadeTransition(new Duration(2000), imageView2);
        ft3.setFromValue(1.0);
        ft3.setToValue(0.0);
        
        //An animation pause before the next animation
        PauseTransition pt3 = new PauseTransition(new Duration(2000));
        
        //Fade out transition for imageView3 (slide 3)
        FadeTransition ft4 = new FadeTransition(new Duration(2000), imageView3);
        ft4.setFromValue(1.0);
        ft4.setToValue(0.0);
        
        //An animation pause before the next animation
        PauseTransition pt4 = new PauseTransition(new Duration(2000));
        
        //Fade out transition for imageView4 (slide 4)
        FadeTransition ft5 = new FadeTransition(new Duration(2000), imageView4);
        ft5.setFromValue(1.0);
        ft5.setToValue(0.0);
        
        //An animation pause before the next animation
        PauseTransition pt5 = new PauseTransition(new Duration(1000));
        
        //Fade in transition for the welcome message, and choosing a different fill for the background at the same time
        FadeTransition ft6 = new FadeTransition(new Duration(2000), welcomeMessage);
        ft6.setFromValue(0.0);
        ft6.setToValue(1.0);
        FillTransition ft = new FillTransition(new Duration(2000), background);
        ft.setFromValue(Color.WHITESMOKE);
        ft.setToValue(Color.BLACK);
        ParallelTransition pt = new ParallelTransition(ft6, ft);
        
        //An animation pause before going to the main simulation scene
        PauseTransition pt6 = new PauseTransition(new Duration(1000));
        
        //Animations ordered one by one
        SequentialTransition st = new SequentialTransition(ft1, pt1, ft2, pt2, ft3, pt3, ft4, pt4, ft5, pt5, pt, pt6);
        st.play();
        
        //When the sequential transition is done, then we go to the main simualtion scene
        st.setOnFinished(event -> {
            mediaPlayer.stop();
            try{
                App.setRoot("CarRaceFXML");
            }catch(IOException e){}
        });
    }    
    
}
