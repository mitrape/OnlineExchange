package org.example.onlineexchange;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Swap{
    public boolean swYen;
    public boolean swUsd;
    public boolean swEur;
    public boolean swGbp;
    public boolean swToman;
    public boolean sw2Yen;
    public boolean sw2Usd;
    public boolean sw2Eur;
    public boolean sw2Gbp;
    public boolean sw2Toman;

    @FXML
    private Label outputAmount;
    @FXML
    private TextField inputAmount;
    @FXML
    private Text messageSameCurrency;
    public double input;
    public double output;


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
        sw2Yen = true;
        sw2Toman = false;
        sw2Eur = false;
        sw2Gbp = false;
        sw2Usd = false;
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
        sw2Eur = true;
        sw2Usd = false;
        sw2Gbp = false;
        sw2Toman = false;
        sw2Yen = false;
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
        sw2Gbp = true;
        sw2Yen = false;
        sw2Toman =false;
        sw2Eur = false;
        sw2Usd = false;
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
        sw2Usd = true;
        sw2Eur = false;
        sw2Toman = false;
        sw2Gbp = false;
        sw2Yen = false;
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
        sw2Toman = true;
        sw2Yen = false;
        sw2Gbp = false;
        sw2Eur = false;
        sw2Usd = false;
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
    public void ClickOnSwap (ActionEvent event) throws IOException, SQLException {
        PreparedStatement findUserStatement = Main.connection.prepareStatement("SELECT * FROM usersdata WHERE username = ?");
        findUserStatement.setString(1, Main.username);
        ResultSet resultSet = findUserStatement.executeQuery();
        PreparedStatement findUserStatementAdmin = Main.connection.prepareStatement("SELECT * FROM usersdata WHERE username = ?");
        findUserStatementAdmin.setString(1, "admin");
        ResultSet resultSet2 = findUserStatement.executeQuery();
        if (resultSet.next() && resultSet2.next()) {
            // User and admin found, update the money
            if (swYen && Double.parseDouble(resultSet.getString("amountOfYEN"))>=0) {
                PreparedStatement updateMoneyStatement = Main.connection.prepareStatement("UPDATE usersdata SET amountOfYEN = amountOfYEN + ? WHERE username = ?");
                updateMoneyStatement.setDouble(1, -input);
                updateMoneyStatement.setString(2, Main.username);

                if (sw2Gbp) {
                    PreparedStatement updateMoneyStatement1 = Main.connection.prepareStatement("UPDATE usersdata SET amountOfGBP = amountOfGBP + ? WHERE username = ?");
                    updateMoneyStatement1.setDouble(1, output*0.99);
                    updateMoneyStatement1.setString(2, Main.username);
                    PreparedStatement updateMoneyStatementAdmin = Main.connection.prepareStatement("UPDATE usersdata SET amountOfYEN = amountOfYEN + ?, amountOfGBP = amountOfGBP + ? WHERE username = ?");
                    updateMoneyStatementAdmin.setDouble(1, input);
                    updateMoneyStatementAdmin.setDouble(2, output*0.01);
                    updateMoneyStatementAdmin.setString(3, "admin");

                } else if (sw2Toman) {

                } else if (sw2Usd) {

                } else if (sw2Eur) {

                }
            }

            else if (swUsd) {
                if (sw2Gbp) {

                } else if (sw2Toman) {

                } else if (sw2Yen) {

                } else if (sw2Eur) {

                }
            }


            else if (swEur) {
                if (sw2Gbp) {

                } else if (sw2Toman) {

                } else if (sw2Usd) {

                } else if (sw2Yen) {

                }
            }


            else if (swGbp) {
                if (sw2Yen) {

                } else if (sw2Toman) {

                } else if (sw2Usd) {

                } else if (sw2Eur) {

                }
            }


            else if (swToman) {
                if (sw2Gbp) {

                } else if (sw2Yen) {

                } else if (sw2Usd) {

                } else if (sw2Eur) {

                }
            }


            else {
                //message not enough currency
            }
        }
    }
}

