package org.example.onlineexchange;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SignUp {
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

    public void ClickOnExit (ActionEvent event) throws IOException {
        System.exit(0);
    }
    public void ClickOnSignUp (ActionEvent event) throws  IOException {

    }
    public void ClickOnChooseFile (ActionEvent event) throws IOException {

    }

}
