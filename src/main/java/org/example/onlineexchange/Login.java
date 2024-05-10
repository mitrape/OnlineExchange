package org.example.onlineexchange;

import javafx.application.Platform;
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

public class Login implements Initializable {
    @FXML
    private Button login ;
    @FXML
    private Button signUp ;
    @FXML
    private Button forgetPassword;
    @FXML
    private Button exit;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private TextField code;
    @FXML
    private Label captcha ;
    @FXML
    private Label loginMessage;
    @FXML
    private Label UsernameMessage;
    @FXML
    private Label PasswordMessage;
    @FXML
    private Label CodeMessage;

    static String CAPTCHA ;

    static String generateCaptcha(int n)
    {
        Random rand = new Random(62);
        String chrs = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String captcha = "";
        while (n-->0){
            int index = (int)(Math.random()*62);
            captcha+=chrs.charAt(index);
        }
        return captcha;
    }

    @Override
    public void initialize(URL location, ResourceBundle rb){
            CAPTCHA = generateCaptcha(3);
            captcha.setText(CAPTCHA);
    }

    public void ClickOnLogin (ActionEvent event) throws IOException {
        String Username = username.getText();
        String Password = password.getText();
        String Code = code.getText();

        boolean userFill = Username.isEmpty();
        boolean passFill = Password.isEmpty();
        boolean codeFill = Code.isEmpty();

        boolean swUsername = false;
        boolean swPassword = false;
        boolean swCode = false;
        int numberOfUser=0 ;

        if(!userFill){
            UsernameMessage.setVisible(false);
            for (int i = 0 ; i<User.user.length && User.user[i] != null ; i++){
                if(User.user[i].getUsername().equals(Username)){
                    swUsername = true;
                    numberOfUser = i;
                    break;
                }
            }
        }
        else{
            UsernameMessage.setVisible(true);
        }
        if(!passFill){
            PasswordMessage.setVisible(false);
            if(!userFill && User.user[numberOfUser].getPassword().equals(Password)){
                swPassword = true;
            }
        }
        else {
            PasswordMessage.setVisible(true);
        }
        if(!codeFill){
            CodeMessage.setVisible(false);
            if(Code.equals(CAPTCHA)){
                swCode=true;
            }
        }
        else {
            CodeMessage.setVisible(true);
        }
        if(swUsername && swPassword && swCode){
            Main m = new Main();
            m.changeScene("mainPage");
            loginMessage.setVisible(false);
            UsernameMessage.setVisible(false);
            PasswordMessage.setVisible(false);
            CodeMessage.setVisible(false);
            loginMessage.setVisible(false);
            CAPTCHA = generateCaptcha(3);
            captcha.setText(CAPTCHA);
        }
        else{
            loginMessage.setVisible(true);
            CAPTCHA = generateCaptcha(3);
            captcha.setText(CAPTCHA);
        }
    }

    public void ClickOnSignUp (ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("signUp");
    }
    public void ClickOnExit (ActionEvent event) throws  IOException {
        System.exit(0);
    }
    public void ClickOnForgetPassword (ActionEvent event) throws  IOException {
        Main m = new Main();
        m.changeScene("forgetPassword");
    }

}


