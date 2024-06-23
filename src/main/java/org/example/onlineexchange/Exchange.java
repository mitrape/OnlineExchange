package org.example.onlineexchange;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class Exchange implements Initializable {
    @FXML
    private MenuButton CurrencyButton;
    @FXML
    private Button sellButton ;
    @FXML
    private Button buyButton;
    @FXML
    private TextField PriceTextField;
    @FXML
    private TextField TurnoverTextField;
    @FXML
    private TableColumn<Transaction, Double> userPrice;
    @FXML
    private TableColumn<Transaction, Double> userTurnover;
    @FXML
    private TableColumn<Transaction, String> userTransactoinType ;
    @FXML
    private TableColumn<Transaction, Double> Price;
    @FXML
    private TableColumn<Transaction, Double> Turnover;
    @FXML
    private TableColumn<Transaction, String> TransactoinType ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Group group = new Group();
        group.getChildren().add(sellButton);
        group.getChildren().add(buyButton);
    }

    public void ClickOnTOMAN (ActionEvent event){
        CurrencyButton.setText("TOMAN");
    }
    public void ClickOnYEN (ActionEvent event){
        CurrencyButton.setText("YEN");
    }
    public void ClickOnEUR (ActionEvent event){
        CurrencyButton.setText("EUR");
    }
    public void ClickOnGBP (ActionEvent event){
        CurrencyButton.setText("GBP");
    }
    public void ClickOnUSD (ActionEvent event){
        CurrencyButton.setText("USD");
    }
    public void ClickOnExit (ActionEvent event){
        System.exit(0);
    }
    public void ClickOnDone (ActionEvent event){

    }
}
