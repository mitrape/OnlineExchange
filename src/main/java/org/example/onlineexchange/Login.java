package org.example.onlineexchange;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Random;

public class Login {
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

    public String CAPTCHA = generateCaptchaString();

    public void login (ActionEvent event) throws IOException {
        checkLogin();
    }
    public String generateCaptchaString() {
        Random random = new Random();
        int length = 7 + (Math.abs(random.nextInt()) % 3);

        StringBuffer captchaStringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int baseCharNumber = Math.abs(random.nextInt()) % 62;
            int charNumber = 0;
            if (baseCharNumber < 26) {
                charNumber = 65 + baseCharNumber;
            }
            else if (baseCharNumber < 52){
                charNumber = 97 + (baseCharNumber - 26);
            }
            else {
                charNumber = 48 + (baseCharNumber - 52);
            }
            captchaStringBuffer.append((char)charNumber);
        }

        return captchaStringBuffer.toString();
    }
    private void checkLogin() throws IOException {
        Main m = new Main();
        boolean sw = false ;
        String Username = username.getText().toString();
        String Password = password.getText().toString();

        for (int i = 0 ; i < User.user.length && !sw ; i++){
           if(Username.equals(User.user[i].getUsername()) && Password.equals(User.user[i].getPassword())){
               sw=true;
           }
        }
        if(sw){
            loginMessage.setText("Successful!");
            //m.changeScene("afterLogin.fxml");
        }
        else {
            if(Username.isEmpty()||Password.isEmpty()){
                loginMessage.setText("Please enter your data!");
            }
            else{
                loginMessage.setText("wrong username or password!");
            }
        }
    }
    // باید کپچا رو درست کنم یعنی لیبلش ست تکست بشه
    //باید چنج سین رو درست کنم

}


