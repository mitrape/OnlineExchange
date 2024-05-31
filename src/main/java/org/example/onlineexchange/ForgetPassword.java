package org.example.onlineexchange;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

import java.io.IOException;
import java.util.Properties;

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
    @FXML
    private Label MessageReceivedCode;
    @FXML
    private Button ResendCode;

    static boolean emailSent;
    static Integer code;

    public boolean sendEmail (String recipientEmail){
        final String username = "onlineexchangedandp@gmail.com";
        final String password = "aakhpnjipafgxuiw";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(prop,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("onlineexchangedandp@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(recipientEmail)
            );
            message.setSubject("log in with code");
            Random rand = new Random();
            code = rand.nextInt(10000);
            message.setText(code.toString());

            Transport.send(message);
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void ClickOnLoginButton (ActionEvent event) throws IOException {
        String enteredCode = EnterCode.getText();
        if(enteredCode.equals(code.toString())){
            //بره صفحه های بعدی
        }
        else{
            // نشون بده غلطه
        }
    }
    public void ClickOnSubmitButton (ActionEvent event) throws IOException {
        String email = EnterEmail.getText();
        // بره ببینه همچین جییملی هست یا نه
        emailSent = sendEmail(email);
        if(emailSent){
            //باید تمام دکمه های جدید رو نمایان کنه
        }
    }
    public void ClickOnExit (ActionEvent event) throws IOException {
        System.exit(0);
    }
    public void ClickOnSignUpButton (ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("signUp");
    }

}
