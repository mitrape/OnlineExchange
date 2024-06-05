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

public class SignUp {
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
    private Text usernameMessage;
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
        User user = new User(username.getText(),password.getText(),firstName.getText(),lastName.getText(),email.getText(),phoneNumber.getText());
        if(username.getText().isEmpty()){
            usernameMessage.setText("Please fill this field");
            usernameMessage.setVisible(true);
        }
        else if(!user.correctInfo[0]){
            usernameMessage.setText("Incorrect username input");
            usernameMessage.setVisible(true);
        }
        else {
            usernameMessage.setVisible(false);
        }
        if(firstName.getText().isEmpty()){
            firstnameMessage.setText("Please fill this field");
            firstnameMessage.setVisible(true);
        }
        else if(!user.correctInfo[3]){
            firstnameMessage.setText("Incorrect name input");
            firstnameMessage.setVisible(true);
        }
        else {
            firstnameMessage.setVisible(false);
        }
        if(password.getText().isEmpty()){
            passwordMessage.setText("Please fill this field");
            passwordMessage.setVisible(true);
        }
        else if(!user.correctInfo[1]){
            passwordMessage.setText("Password not safe");
            passwordMessage.setVisible(true);
        }
        else {
            passwordMessage.setVisible(false);
        }
        if(lastName.getText().isEmpty()){
            lastnameMessage.setText("Please fill this field");
            lastnameMessage.setVisible(true);
        }
        else if(!user.correctInfo[4]){
            lastnameMessage.setText("Incorrect lastname input");
            lastnameMessage.setVisible(true);
        }
        else {
            lastnameMessage.setVisible(false);
        }
        if(email.getText().isEmpty()){
            emailMessage.setText("Please fill this field");
            emailMessage.setVisible(true);
        }
        else if(!user.correctInfo[5]){
            emailMessage.setText("Email does not exist");
            emailMessage.setVisible(true);
        }
        else {
            emailMessage.setVisible(false);
        }
        if(phoneNumber.getText().isEmpty()){
            phoneNumberMessage.setText("Please fill this field");
            phoneNumberMessage.setVisible(true);
        }
        else if(!user.correctInfo[6]){
            phoneNumberMessage.setText("Number does not exist");
            phoneNumberMessage.setVisible(true);
        }
        else {
            phoneNumberMessage.setVisible(false);
        }
        if(repeatPassword.getText().isEmpty()){
            repeatPasswordMessage.setText("Please fill this field");
            repeatPasswordMessage.setVisible(true);
        }
        else if(!repeatPassword.getText().equals(password.getText())){
            repeatPasswordMessage.setText("Password is not the same");
            repeatPasswordMessage.setVisible(true);
        }
        else{
            repeatPasswordMessage.setVisible(false);
            user.correctInfo[2]=true;
        }
        //hamin kar baraie code
        boolean swFinal = true;
        if(username.getText().isEmpty() || firstName.getText().isEmpty() || lastName.getText().isEmpty() || password.getText().isEmpty() || repeatPassword.getText().isEmpty() || phoneNumber.getText().isEmpty() || email.getText().isEmpty()){
            swFinal = false;
        }
        for (int i = 0; i < 7; i++) {
            if(!user.correctInfo[i]) {
                swFinal = false;
                break;
            }
        }
        if(swFinal){
            Main m = new Main();
            m.changeScene("mainPage");
        }
    }

    public void ClickOnExit (ActionEvent event) throws IOException {
        System.exit(0);
    }

    public void ClickOnChooseFile (ActionEvent event) throws IOException {

    }

}
