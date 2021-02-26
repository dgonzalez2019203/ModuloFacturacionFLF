package org.moduloFacturacion.bean;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class Animations {
    
    
    public void animacion(AnchorPane anchor, AnchorPane anchor2){
       FadeTransition ft = new FadeTransition();
       ft.setFromValue(0);
       ft.setToValue(1);
       ft.setDuration(Duration.seconds(2));
       ft.setNode(anchor);
       ft.setCycleCount(1);
       ft.play();
       
       
       TranslateTransition tt = new TranslateTransition();
       tt.setFromY(-80);
       tt.setToY(0);
       tt.setDuration(Duration.seconds(1.0));
       tt.setNode(anchor);
       tt.setCycleCount(1);
       tt.play();
       
       FadeTransition ft1 = new FadeTransition();
       ft1.setFromValue(0);
       ft1.setToValue(1);
       ft1.setDuration(Duration.seconds(2));
       ft1.setNode(anchor2);
       ft1.setCycleCount(1);
       ft1.play();
       
       
       TranslateTransition tt1 = new TranslateTransition();
       tt1.setFromY(-80);
       tt1.setToY(0);
       tt1.setDuration(Duration.seconds(1.0));
       tt1.setNode(anchor2);
       tt1.setCycleCount(1);
       tt1.play();
    
    }
}
