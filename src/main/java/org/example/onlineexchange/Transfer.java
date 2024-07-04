package org.example.onlineexchange;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

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


    private static String walletID ;
    Double money = null;

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
        if (money!=0){
            System.out.println(walletID);
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
}
