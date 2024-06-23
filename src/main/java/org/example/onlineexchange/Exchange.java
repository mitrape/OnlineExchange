package org.example.onlineexchange;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import org.w3c.dom.Text;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    private TableView <Transaction> userTable;
    @FXML
    private TableView <Transaction> marketTable;

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
    public void setTable () throws SQLException {
        userPrice.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("OpenRequests"));
        userTurnover.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("amount"));
        userTransactoinType.setCellValueFactory(new PropertyValueFactory<Transaction, String>("SellOrBuy"));

        Price.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("OpenRequests"));
        Turnover.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("amount"));
        TransactoinType.setCellValueFactory(new PropertyValueFactory<Transaction, String>("SellOrBuy"));

        if(CurrencyButton.getText().equals("TOMAN")){
            Transaction transaction = null;
            Statement statement1 = Main.connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery("SELECT * FROM tomanopenrequests");
            while (resultSet1.next()) {
                Double OpenRequest = resultSet1.getDouble("tomanOpenRequests");
                Double amount = resultSet1.getDouble("amount");
                String SellOrBuy = resultSet1.getString("SellOrBuy");
                transaction = new Transaction(OpenRequest, amount, SellOrBuy);
                marketTable.getItems().add(transaction);
            }

            PreparedStatement statement = Main.connection.prepareStatement("SELECT * FROM tomantransaction WHERE username = ?");
            statement.setString(1,Main.username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Double OpenRequest = resultSet1.getDouble("Transaction");
                Double amount = resultSet1.getDouble("amount");
                String SellOrBuy = resultSet1.getString("SellOrBuy");
                transaction = new Transaction(OpenRequest, amount, SellOrBuy);
                userTable.getItems().add(transaction);
            }


        }
        else if (CurrencyButton.getText().equals("USD")){

        }
        else if (CurrencyButton.getText().equals("YEN")){

        }
        else if (CurrencyButton.getText().equals("EUR")){

        }
        else{
            //GBP

        }

    }
}
