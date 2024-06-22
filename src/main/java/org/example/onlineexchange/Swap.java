package org.example.onlineexchange;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class Swap{
    public boolean swYen;
    public boolean swUsd;
    public boolean swEur;
    public boolean swGbp;
    public boolean swToman;
    @FXML
    public Label amountOfCurrecyLabel;
    @FXML
    public TextField inputAmount;
    public double input;
    public void ClickOnSwap (MouseEvent event){

    }

    public void ClickOnYenFirst (ActionEvent e){
        swYen = true;
        swToman = false;
        swEur = false;
        swGbp = false;
        swUsd = false;
        input = Double.parseDouble(inputAmount.getText());
    }
    public void ClickOnEurFirst (ActionEvent e){
        swEur = true;
        swUsd = false;
        swGbp = false;
        swToman = false;
        swYen = false;
        input = Double.parseDouble(inputAmount.getText());
    }
    public void ClickOnGbpFirst (ActionEvent e){
        swGbp = true;
        swYen = false;
        swToman =false;
        swEur = false;
        swUsd = false;
        input = Double.parseDouble(inputAmount.getText());
    }
    public void ClickOnUsdFirst (ActionEvent e){
        swUsd = true;
        swEur = false;
        swToman = false;
        swGbp = false;
        swYen = false;
        input = Double.parseDouble(inputAmount.getText());
    }
    public void ClickOnTomanFirst (ActionEvent e){
        swToman = true;
        swYen = false;
        swGbp = false;
        swEur = false;
        swUsd = false;
        input = Double.parseDouble(inputAmount.getText());
    }
    public void ClickOnYenSecond (ActionEvent e){
        if(swEur){

        }
        else if(swUsd){

        }
        else if(swGbp){

        }
        else if(swToman){

        }
        else{

        }
    }
    public void ClickOnEurSecond (ActionEvent e){
        if(swEur){

        }
        else if(swUsd){

        }
        else if(swGbp){

        }
        else if(swToman){

        }
        else{

        }
    }
    public void ClickOnGbpSecond (ActionEvent e){
        if(swEur){

        }
        else if(swUsd){

        }
        else if(swGbp){

        }
        else if(swToman){

        }
        else{

        }
    }
    public void ClickOnUsdSecond (ActionEvent e){
        if(swEur){

        }
        else if(swUsd){

        }
        else if(swGbp){

        }
        else if(swToman){

        }
        else{

        }
    }
    public void ClickOnTomanSecond (ActionEvent e){
        if(swEur){

        }
        else if(swUsd){

        }
        else if(swGbp){

        }
        else if(swToman){

        }
        else{

        }
    }


}

