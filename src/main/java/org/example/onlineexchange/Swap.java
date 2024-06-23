package org.example.onlineexchange;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Swap{
    public boolean swYen;
    public boolean swUsd;
    public boolean swEur;
    public boolean swGbp;
    public boolean swToman;
    @FXML
    public Label outputAmount;
    @FXML
    public TextField inputAmount;
    @FXML
    public Text messageSameCurrency;
    public double input;
    public double output;
//    public void ClickOnYenFirst(javafx.event.ActionEvent event) {
//    }

    public void ClickOnYenFirst (ActionEvent e) throws IOException{
        swYen = true;
        swToman = false;
        swEur = false;
        swGbp = false;
        swUsd = false;

    }
    public void ClickOnEurFirst (ActionEvent e) throws IOException{
        swEur = true;
        swUsd = false;
        swGbp = false;
        swToman = false;
        swYen = false;

    }
    public void ClickOnGbpFirst (ActionEvent e) throws IOException{
        swGbp = true;
        swYen = false;
        swToman =false;
        swEur = false;
        swUsd = false;

    }
    public void ClickOnUsdFirst (ActionEvent e) throws IOException{
        swUsd = true;
        swEur = false;
        swToman = false;
        swGbp = false;
        swYen = false;

    }
    public void ClickOnTomanFirst (ActionEvent e) throws IOException{
        swToman = true;
        swYen = false;
        swGbp = false;
        swEur = false;
        swUsd = false;

    }
    public void ClickOnYenSecond (ActionEvent e) throws IOException{
        input = Double.parseDouble(inputAmount.getText());
        if(swEur){
            output = input * Double.parseDouble(HomePage.YENinfo[2])/Double.parseDouble(HomePage.EURinfo[2]);
            outputAmount.setText(String.valueOf(output));
            messageSameCurrency.setVisible(false);
        }
        else if(swUsd){
            output = input * Double.parseDouble(HomePage.YENinfo[2])/Double.parseDouble(HomePage.USDinfo[2]);
            outputAmount.setText(String.valueOf(output));
            messageSameCurrency.setVisible(false);
        }
        else if(swGbp){
            output = input * Double.parseDouble(HomePage.YENinfo[2])/Double.parseDouble(HomePage.GBPinfo[2]);
            outputAmount.setText(String.valueOf(output));
            messageSameCurrency.setVisible(false);
        }
        else if(swToman){
            output = input * Double.parseDouble(HomePage.YENinfo[2])/Double.parseDouble(HomePage.TOMANinfo[2]);
            outputAmount.setText(String.valueOf(output));
            messageSameCurrency.setVisible(false);
        }
        else{
            messageSameCurrency.setVisible(true);
        }
    }
    public void ClickOnEurSecond (ActionEvent e) throws IOException{
        input = Double.parseDouble(inputAmount.getText());
        if(swYen){
            output = input * Double.parseDouble(HomePage.EURinfo[2])/Double.parseDouble(HomePage.YENinfo[2]);
            outputAmount.setText(String.valueOf(output));
            messageSameCurrency.setVisible(false);

        }
        else if(swUsd){
            output = input * Double.parseDouble(HomePage.EURinfo[2])/Double.parseDouble(HomePage.USDinfo[2]);
            outputAmount.setText(String.valueOf(output));
            messageSameCurrency.setVisible(false);


        }
        else if(swGbp){
            output = input * Double.parseDouble(HomePage.EURinfo[2])/Double.parseDouble(HomePage.GBPinfo[2]);
            outputAmount.setText(String.valueOf(output));
            messageSameCurrency.setVisible(false);


        }
        else if(swToman){
            output = input * Double.parseDouble(HomePage.EURinfo[2])/Double.parseDouble(HomePage.TOMANinfo[2]);
            outputAmount.setText(String.valueOf(output));
            messageSameCurrency.setVisible(false);


        }
        else{
            messageSameCurrency.setVisible(true);

        }
    }
    public void ClickOnGbpSecond (ActionEvent e) throws IOException{
        input = Double.parseDouble(inputAmount.getText());
        if(swEur){
            output = input * Double.parseDouble(HomePage.GBPinfo[2])/Double.parseDouble(HomePage.EURinfo[2]);
            outputAmount.setText(String.valueOf(output));
            messageSameCurrency.setVisible(false);


        }
        else if(swUsd){
            output = input * Double.parseDouble(HomePage.GBPinfo[2])/Double.parseDouble(HomePage.USDinfo[2]);
            outputAmount.setText(String.valueOf(output));
            messageSameCurrency.setVisible(false);

        }
        else if(swYen){
            output = input * Double.parseDouble(HomePage.GBPinfo[2])/Double.parseDouble(HomePage.YENinfo[2]);
            outputAmount.setText(String.valueOf(output));
            messageSameCurrency.setVisible(false);

        }
        else if(swToman){
            output = input * Double.parseDouble(HomePage.GBPinfo[2])/Double.parseDouble(HomePage.TOMANinfo[2]);
            outputAmount.setText(String.valueOf(output));
            messageSameCurrency.setVisible(false);

        }
        else{
            messageSameCurrency.setVisible(true);
        }
    }
    public void ClickOnUsdSecond (ActionEvent e) throws IOException{
        input = Double.parseDouble(inputAmount.getText());
        if(swEur){
            output = input * Double.parseDouble(HomePage.USDinfo[2])/Double.parseDouble(HomePage.EURinfo[2]);
            outputAmount.setText(String.valueOf(output));
            messageSameCurrency.setVisible(false);

        }
        else if(swYen){
            output = input * Double.parseDouble(HomePage.USDinfo[2])/Double.parseDouble(HomePage.YENinfo[2]);
            outputAmount.setText(String.valueOf(output));
            messageSameCurrency.setVisible(false);

        }
        else if(swGbp){
            output = input * Double.parseDouble(HomePage.USDinfo[2])/Double.parseDouble(HomePage.GBPinfo[2]);
            outputAmount.setText(String.valueOf(output));
            messageSameCurrency.setVisible(false);

        }
        else if(swToman){
            output = input * Double.parseDouble(HomePage.USDinfo[2])/Double.parseDouble(HomePage.TOMANinfo[2]);
            outputAmount.setText(String.valueOf(output));
            messageSameCurrency.setVisible(false);

        }
        else{
            messageSameCurrency.setVisible(true);

        }
    }
    public void ClickOnTomanSecond (ActionEvent e) throws IOException{
        input = Double.parseDouble(inputAmount.getText());
        if(swEur){
            output = input * Double.parseDouble(HomePage.TOMANinfo[2])/Double.parseDouble(HomePage.EURinfo[2]);
            outputAmount.setText(String.valueOf(output));
            messageSameCurrency.setVisible(false);

        }
        else if(swUsd){
            output = input * Double.parseDouble(HomePage.TOMANinfo[2])/Double.parseDouble(HomePage.USDinfo[2]);
            outputAmount.setText(String.valueOf(output));
            messageSameCurrency.setVisible(false);

        }
        else if(swGbp){
            output = input * Double.parseDouble(HomePage.TOMANinfo[2])/Double.parseDouble(HomePage.GBPinfo[2]);
            outputAmount.setText(String.valueOf(output));
            messageSameCurrency.setVisible(false);

        }
        else if(swYen){
            output = input * Double.parseDouble(HomePage.TOMANinfo[2])/Double.parseDouble(HomePage.YENinfo[2]);
            outputAmount.setText(String.valueOf(output));
            messageSameCurrency.setVisible(false);

        }
        else{
            messageSameCurrency.setVisible(true);

        }
    }
    public void ClickOnSwap (MouseEvent event) throws IOException{

    }



}

