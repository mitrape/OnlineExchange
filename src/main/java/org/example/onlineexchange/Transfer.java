package org.example.onlineexchange;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Transfer implements Initializable {
    @FXML
    private TextField walletIDTextField ;
    @FXML
    private RadioButton currencyRadioButton = new RadioButton() ;
    @FXML
    private RadioButton moneyRadioButton = new RadioButton();
    @FXML
    private Text walletIDError ;
    @FXML
    private Text transferOptionError;
    @FXML
    private Label moneyLabel ;
    @FXML
    private Slider moneySlider = new Slider();
    @FXML
    private Text moneyError ;
    @FXML
    private Text walletFoundError ;
    @FXML
    private Text currencyNameError ;
    @FXML
    private Text amountError ;
    @FXML
    private Slider currencySlider = new Slider() ;
    @FXML
    private Label amountLabel ;
    @FXML
    private Label doneCurrencyLabel ;
    @FXML
    private TextField emailTextField ;
    @FXML
    private MenuButton currencyMenuButton ;
    @FXML
    private Label enoughCurrencyError ;


    private static String walletID ;
    Double money = null;
    Double amount = null;

    Date date = Calendar.getInstance().getTime();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup = new ToggleGroup();
        currencyRadioButton.setToggleGroup(toggleGroup);
        moneyRadioButton.setToggleGroup(toggleGroup);

        moneySlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                money = moneySlider.getValue();
                moneyLabel.setText(String.valueOf(money));
            }
        });

        currencySlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                amount = currencySlider.getValue();
                amountLabel.setText(String.valueOf(amount));
            }
        });
    }
    public void ClickOnNext(javafx.event.ActionEvent event) throws IOException, SQLException {
        walletIDError.setVisible(false);
        transferOptionError.setVisible(false);
        walletFoundError.setVisible(false);
        if(!walletIDTextField.getText().isEmpty()){
            walletID = walletIDTextField.getText();
            PreparedStatement ps = Main.connection.prepareStatement("SELECT * FROM usersdata WHERE username = ?");
            ps.setString(1,walletID);
            ResultSet rs = ps.executeQuery();
            if (rs.isBeforeFirst()){
                if(currencyRadioButton.isSelected()){
                    Main m = new Main();
                    m.changeScene("currencyTransfer");
                }
                else if(moneyRadioButton.isSelected()){
                    Main m = new Main();
                    m.changeScene("moneyTransfer");
                }
                else{
                    transferOptionError.setVisible(true);
                }
            }
            else {
                walletFoundError.setVisible(true);
            }
        }
        else {
            walletIDError.setVisible(true);
        }
    }

    public void ClickOnDoneMoney(ActionEvent event) throws SQLException {
        moneyError.setVisible(false);
        if (!moneyLabel.getText().equals("0.00")){
            PreparedStatement ps = Main.connection.prepareStatement("UPDATE usersdata SET money = money + ?  WHERE username = ?");
            ps.setDouble(1,money);
            ps.setString(2,walletID);
            ps.executeUpdate();
            moneyError.setText("DONE!");
            moneyError.setVisible(true);
        }
        else {
            moneyError.setText("please choose an amount for transfer");
            moneyError.setVisible(true);
        }

    }


    public void ClickOnDoneCurrency(ActionEvent event) throws SQLException {
        currencyNameError.setVisible(false);
        amountError.setVisible(false);
        enoughCurrencyError.setVisible(false);
        doneCurrencyLabel.setVisible(false);
        if(!currencyMenuButton.getText().equals("choose currency")){
            if(!amountLabel.getText().equals("0.00")){

                    PreparedStatement ps = Main.connection.prepareStatement("SELECT * FROM usersdata WHERE username = ?");
                    ps.setString(1,Main.username);
                    ResultSet rs = ps.executeQuery();
                    Double userCurrencyAmount = 0.00;
                    while (rs.next()){
                        userCurrencyAmount = rs.getDouble("amountOf"+currencyMenuButton.getText());
                    }
                    if(userCurrencyAmount >= amount){

                        PreparedStatement ps1 = Main.connection.prepareStatement("UPDATE usersdata SET amountOf"+currencyMenuButton.getText()+" = amountOf"+currencyMenuButton.getText()+" + ? WHERE username = ?");
                        ps1.setDouble(1,amount);
                        ps1.setString(2,walletID);
                        ps1.executeUpdate();

                        PreparedStatement ps2 = Main.connection.prepareStatement("UPDATE usersdata SET amountOf"+currencyMenuButton.getText()+" = amountOf"+currencyMenuButton.getText()+" - ? WHERE username = ?");
                        ps2.setDouble(1,amount);
                        ps2.setString(2,Main.username);
                        ps2.executeUpdate();

                        if(!emailTextField.getText().isEmpty()){
                            sendEmail(emailTextField.getText(),walletID,dateFormat.format(date),amountLabel.getText(),currencyMenuButton.getText());
                        }

                        doneCurrencyLabel.setVisible(true);
                    }
                    else {
                        enoughCurrencyError.setVisible(true);
                    }
            }
            else {
                amountError.setVisible(true);
            }
        }
        else {
            currencyNameError.setVisible(true);
        }
    }

    public void ClickOnUSD(ActionEvent event) {
        currencyMenuButton.setText("USD");
    }

    public void ClickOnTOMAN(ActionEvent event) {
        currencyMenuButton.setText("TOMAN");
    }

    public void ClickOnYEN(ActionEvent event) {
        currencyMenuButton.setText("YEN");
    }

    public void ClickOnGBP(ActionEvent event) {
        currencyMenuButton.setText("GBP");
    }

    public void ClickOnEUR(ActionEvent event) {
        currencyMenuButton.setText("EUR");
    }

    public boolean sendEmail (String recipientEmail , String walletID , String date , String amount , String currency){
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
            message.setSubject("Transaction Receipt");
            message.setText("Dear User\nThe transfer was successful!\nِDestination account : "+walletID+"\nAmount of transferred currency : "+amount+"\nCurrencyType : "+currency+"\nDate of transfer : "+date);

            Transport.send(message);
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
