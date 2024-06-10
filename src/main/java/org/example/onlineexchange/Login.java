package org.example.onlineexchange;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private ImageView captcha ;
    @FXML
    private Label loginMessage;
    @FXML
    private Label UsernameMessage;
    @FXML
    private Label PasswordMessage;
    @FXML
    private Label CodeMessage;
    @FXML
    private Label incorrectPassOrUsername;

    public static String[] Captchaa = {"33189","42553","23085","08652","86291","46639"};
    public static String CAPTCHA ;

    public void setCaptcha (){
        Random rand = new Random();
        int randomNumber = rand.nextInt(6)+1;
        Image Captcha = new Image("captcha"+randomNumber+".jpg");
        captcha.setImage(Captcha);
        CAPTCHA = Captchaa[randomNumber-1];
    }

    @Override
    public void initialize(URL location, ResourceBundle rb){
        setCaptcha();
    }

    public void ClickOnLogin (ActionEvent event) throws IOException, SQLException {
        String Username = username.getText();
        String Password = password.getText();
        String Code = code.getText();

        boolean userFill = Username.isEmpty();
        boolean passFill = Password.isEmpty();
        boolean codeFill = Code.isEmpty();

        boolean swUsername = false;
        boolean swPassword = false;
        boolean swCode = false;

        PreparedStatement preStatement = null;
        ResultSet resultSet = null;

        preStatement = Main.connection.prepareStatement("SELECT password FROM  userdata WHERE username = ?");
        preStatement.setString(1,Username);
        resultSet = preStatement.executeQuery();

        if(!userFill){
            UsernameMessage.setVisible(false);
            if(resultSet.isBeforeFirst()){
                loginMessage.setVisible(false);
                swUsername=true;
            }
            else {
                loginMessage.setVisible(true);
            }
        }
        else{
            UsernameMessage.setVisible(true);
        }
        if(!passFill){
            PasswordMessage.setVisible(false);
            if(swUsername){
                while (resultSet.next()){
                    String retrievedPass = resultSet.getString("password");
                    if(retrievedPass.equals(Password)){
                        swPassword = true;
                        loginMessage.setVisible(false);
                        break;
                    }
                }
                if(!swPassword){
                    loginMessage.setVisible(true);
                }
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
        try {
            if (swUsername && swPassword && swCode) {
                Main m = new Main();
                Main.username = Username;
                m.changeScene("homePage");
                loginMessage.setVisible(false);
                UsernameMessage.setVisible(false);
                PasswordMessage.setVisible(false);
                CodeMessage.setVisible(false);
                loginMessage.setVisible(false);
            } else {
                setCaptcha();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(resultSet != null){
                try {
                    resultSet.close();
                }
                catch (SQLException e1){
                    e1.printStackTrace();
                }
            }
            if(preStatement != null){
                try {
                    preStatement.close();
                }
                catch (SQLException e2){
                    e2.printStackTrace();
                }
            }
            if(Main.connection != null){
                try{
                    Main.connection.close();
                }
                catch (SQLException e3){
                    e3.printStackTrace();
                }
            }
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


