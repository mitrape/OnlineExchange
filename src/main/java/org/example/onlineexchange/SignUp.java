package org.example.onlineexchange;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class SignUp implements Initializable {
    @FXML
    private TextField username ;
    @FXML
    private TextField firstName ;
    @FXML
    private TextField lastName ;
    @FXML
    private TextField phoneNumber ;
    @FXML
    private PasswordField password ;
    @FXML
    private PasswordField repeatPassword ;
    @FXML
    private TextField email ;
    @FXML
    private Button chooseFile ;
    @FXML
    private TextField code ;
    @FXML
    private Button exit ;
    @FXML
    private Button signUp ;
    @FXML
    private Label captcha ;
    @FXML
    private TextField usernameMessage;
    @FXML
    private Text firstnameMessage;
    @FXML
    private Text lastnameMessage;
    @FXML
    private Text phoneNumberMessage;
    @FXML
    private Text passwordMessage;
    @FXML
    private Text repeatPasswordMessage;
    @FXML
    private Text emailMessage;
    @FXML
    private Text codeMessage;
    @FXML
    private Text fileMessage;


    static String CAPTCHA ;

    public void ClickOnSignUp (ActionEvent event) throws  IOException {

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CAPTCHA = Login.generateCaptcha(3);
        captcha.setText(CAPTCHA);
    }


    public void ClickOnExit (ActionEvent event) throws IOException {
        System.exit(0);
    }

    public void ClickOnChooseFile (ActionEvent event) throws IOException {

    }

}
