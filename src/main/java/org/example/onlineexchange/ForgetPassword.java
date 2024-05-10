package org.example.onlineexchange;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ForgetPassword {
    @FXML
    private Label MessageEnterCode;
    @FXML
    private Label CodeLabel;
    @FXML
    private Button LoginButton;
    @FXML
    private Button ExitButton;
    @FXML
    private Label MessageEnterEmail;
    @FXML
    private Label WrongEmailMessage;
    @FXML
    private Button SubmitButton;
    @FXML
    private Label EmailLabel ;
    @FXML
    private Label WrongCodeMessage;
    @FXML
    private Button SignUpButton;
    @FXML
    private TextField EnterEmail ;
    @FXML
    private TextField EnterCode;

    public void ClickOnLoginButton (ActionEvent event) throws IOException {

    }
    public void ClickOnSubmitButton (ActionEvent event) throws IOException {

    }
    public void ClickOnExit (ActionEvent event) throws IOException {
        System.exit(0);
    }
    public void ClickOnSignUpButton (ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("signUp");
    }

}
