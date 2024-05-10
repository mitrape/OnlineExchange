package org.example.onlineexchange;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class SignUp implements Initializable {
    @FXML
    private TextField Username ;
    @FXML
    private TextField FirstName ;
    @FXML
    private TextField LastName ;
    @FXML
    private TextField PhoneNumber ;
    @FXML
    private PasswordField Password ;
    @FXML
    private PasswordField RepeatPassword ;
    @FXML
    private TextField Email ;
    @FXML
    private Button ChooseFile ;
    @FXML
    private TextField Code ;
    @FXML
    private Button Exit ;
    @FXML
    private Button SignUp ;
    @FXML
    private Label captcha ;

    static String CAPTCHA ;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CAPTCHA = Login.generateCaptcha(3);
        captcha.setText(CAPTCHA);
    }


    public void ClickOnExit (ActionEvent event) throws IOException {
        System.exit(0);
    }
    public void ClickOnSignUp (ActionEvent event) throws  IOException {

    }
    public void ClickOnChooseFile (ActionEvent event) throws IOException {

    }

}
