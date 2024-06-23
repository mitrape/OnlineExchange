package org.example.onlineexchange;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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
    private Text errorText;

    @FXML
    private TableView <Transaction> userTable;
    @FXML
    private TableView <Transaction> marketTable;

    @FXML
    private TableColumn<Transaction, Double> userPrice;
    @FXML
    private TableColumn<Transaction, Double> userTurnover;
    @FXML
    private TableColumn<Transaction, String> userTransactionType ;
    @FXML
    private TableColumn<Transaction, Double> Price;
    @FXML
    private TableColumn<Transaction, Double> Turnover;
    @FXML
    private TableColumn<Transaction, String> TransactionType ;

    int lastMinute = -1;
    private volatile boolean stop = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Group group = new Group();
        group.getChildren().add(sellButton);
        group.getChildren().add(buyButton);
        showTime();
    }
    public void showTime(){
        Thread thread = new Thread(() -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss a");
            while (!stop){
                try {
                    Thread.sleep(1000+1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                final Date[] now = {new Date()};
                final String timenow = simpleDateFormat.format(now[0]);

                Platform.runLater(() -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(now[0]);
                    int currentMinute = calendar.get(Calendar.MINUTE);
                    if (currentMinute != lastMinute) {
                        // A new minute has passed, call your update function here
                        try {
                            setTable();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        lastMinute = currentMinute;
                    }
                });
            }
        });
        thread.start();
    }
    public void ClickOnTOMAN (ActionEvent event) throws SQLException {
        CurrencyButton.setText("TOMAN");
        setTable();
    }
    public void ClickOnYEN (ActionEvent event) throws SQLException {
        CurrencyButton.setText("YEN");
        setTable();
    }
    public void ClickOnEUR (ActionEvent event) throws SQLException {
        CurrencyButton.setText("EUR");
        setTable();
    }
    public void ClickOnGBP (ActionEvent event) throws SQLException {
        CurrencyButton.setText("GBP");
        setTable();
    }
    public void ClickOnUSD (ActionEvent event) throws SQLException {
        CurrencyButton.setText("USD");
        setTable();
    }
    public void ClickOnExit (ActionEvent event){
        System.exit(0);
    }
    public void ClickOnDone (ActionEvent event) throws SQLException {
        errorText.setVisible(false);
        //String sql = "UPDATE usersdata SET photoName = ? WHERE username = ?";
        //                PreparedStatement pstmt = Main.connection.prepareStatement(sql);
        //                pstmt.setString(1, selectedFile.getName());
        //                pstmt.setString(2, Main.username);
        //                pstmt.executeUpdate();

        // Statement statement1 = Main.connection.createStatement();
        //            ResultSet resultSet1 = statement1.executeQuery("SELECT * FROM tomanopenrequests");
        //            while (resultSet1.next()) {
        //                Double OpenRequest = resultSet1.getDouble("tomanOpenRequests");
        //                Double amount = resultSet1.getDouble("amount");
        //                String SellOrBuy = resultSet1.getString("SellOrBuy");
        //                transaction = new Transaction(OpenRequest, amount, SellOrBuy);
        //                marketTable.getItems().add(transaction);
        //            }

        String price = PriceTextField.getText();
        String turnover = TurnoverTextField.getText();

        if(buyButton.isPressed()){
            if(price==null || turnover==null){
                errorText.setText("please fill all the text fields");
                errorText.setVisible(true);
            }
            else {
                PreparedStatement prestm = Main.connection.prepareStatement("SELECT * FROM usersdata WHERE username = ?");
                prestm.setString(1,Main.username);
                ResultSet resultSet = prestm.executeQuery();
                while (resultSet.next()){
                    Double userMoney = resultSet.getDouble("money");
                    if(userMoney<(Double.parseDouble(price)*Double.parseDouble(turnover))){
                        errorText.setText("you don't have enough money!");
                        errorText.setVisible(true);
                    }
                    else {
                        // do the transaction
                        continueTransaction(price,turnover,"buy");
                    }
                }
            }
        }
        else if (sellButton.isPressed()){
            if(price==null || turnover==null){
                errorText.setText("please fill all the text fields");
                errorText.setVisible(true);
            }
            else {
                // do the transaction
                continueTransaction(price,turnover,"sell");
            }
        }
        else {
            errorText.setText("please choose buy or sell");
            errorText.setVisible(true);
        }
    }
    public void setTable () throws SQLException {

        userTable.getItems().clear();
        marketTable.getItems().clear();

        userPrice.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("OpenRequests"));
        userTurnover.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("amount"));
        userTransactionType.setCellValueFactory(new PropertyValueFactory<Transaction, String>("SellOrBuy"));

        Price.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("OpenRequests"));
        Turnover.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("amount"));
        TransactionType.setCellValueFactory(new PropertyValueFactory<Transaction, String>("SellOrBuy"));
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
                Double OpenRequest = resultSet.getDouble("Transaction");
                Double amount = resultSet.getDouble("amount");
                String SellOrBuy = resultSet.getString("SellOrBuy");
                transaction = new Transaction(OpenRequest, amount, SellOrBuy);
                userTable.getItems().add(transaction);
            }
        }


        else if (CurrencyButton.getText().equals("USD")){
            Transaction transaction = null;
            Statement statement1 = Main.connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery("SELECT * FROM usdopenrequests");
            while (resultSet1.next()) {
                Double OpenRequest = resultSet1.getDouble("usdOpenRequests");
                Double amount = resultSet1.getDouble("amount");
                String SellOrBuy = resultSet1.getString("SellOrBuy");
                transaction = new Transaction(OpenRequest, amount, SellOrBuy);
                marketTable.getItems().add(transaction);
            }

            PreparedStatement statement = Main.connection.prepareStatement("SELECT * FROM usdtransaction WHERE username = ?");
            statement.setString(1,Main.username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Double OpenRequest = resultSet.getDouble("Transaction");
                Double amount = resultSet.getDouble("amount");
                String SellOrBuy = resultSet.getString("SellOrBuy");
                transaction = new Transaction(OpenRequest, amount, SellOrBuy);
                userTable.getItems().add(transaction);
            }
        }


        else if (CurrencyButton.getText().equals("YEN")){
            Transaction transaction = null;
            Statement statement1 = Main.connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery("SELECT * FROM yenopenrequests");
            while (resultSet1.next()) {
                Double OpenRequest = resultSet1.getDouble("yenOpenRequests");
                Double amount = resultSet1.getDouble("amount");
                String SellOrBuy = resultSet1.getString("SellOrBuy");
                transaction = new Transaction(OpenRequest, amount, SellOrBuy);
                marketTable.getItems().add(transaction);
            }

            PreparedStatement statement = Main.connection.prepareStatement("SELECT * FROM yentransaction WHERE username = ?");
            statement.setString(1,Main.username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Double OpenRequest = resultSet.getDouble("Transaction");
                Double amount = resultSet.getDouble("amount");
                String SellOrBuy = resultSet.getString("SellOrBuy");
                transaction = new Transaction(OpenRequest, amount, SellOrBuy);
                userTable.getItems().add(transaction);
            }
        }
        else if (CurrencyButton.getText().equals("EUR")){
            Transaction transaction = null;
            Statement statement1 = Main.connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery("SELECT * FROM europenrequests");
            while (resultSet1.next()) {
                Double OpenRequest = resultSet1.getDouble("eurOpenRequests");
                Double amount = resultSet1.getDouble("amount");
                String SellOrBuy = resultSet1.getString("SellOrBuy");
                transaction = new Transaction(OpenRequest, amount, SellOrBuy);
                marketTable.getItems().add(transaction);
            }

            PreparedStatement statement = Main.connection.prepareStatement("SELECT * FROM eurtransaction WHERE username = ?");
            statement.setString(1,Main.username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Double OpenRequest = resultSet.getDouble("Transaction");
                Double amount = resultSet.getDouble("amount");
                String SellOrBuy = resultSet.getString("SellOrBuy");
                transaction = new Transaction(OpenRequest, amount, SellOrBuy);
                userTable.getItems().add(transaction);
            }
        }
        else{
            //GBP
            Transaction transaction = null;
            Statement statement1 = Main.connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery("SELECT * FROM gbpopenrequests");
            while (resultSet1.next()) {
                Double OpenRequest = resultSet1.getDouble("gbpOpenRequests");
                Double amount = resultSet1.getDouble("amount");
                String SellOrBuy = resultSet1.getString("SellOrBuy");
                transaction = new Transaction(OpenRequest, amount, SellOrBuy);
                marketTable.getItems().add(transaction);
            }

            PreparedStatement statement = Main.connection.prepareStatement("SELECT * FROM gbptransaction WHERE username = ?");
            statement.setString(1,Main.username);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Double OpenRequest = resultSet.getDouble("Transaction");
                Double amount = resultSet.getDouble("amount");
                String SellOrBuy = resultSet.getString("SellOrBuy");
                transaction = new Transaction(OpenRequest, amount, SellOrBuy);
                userTable.getItems().add(transaction);
            }
        }

    }
    public void continueTransaction (String Price , String Turnover , String SellOrBuy) throws SQLException {
        PreparedStatement preparedStatement = Main.connection.prepareStatement("SELECT * FROM "+CurrencyButton.getText().toLowerCase()+"openrequests WHERE "
                +CurrencyButton.getText().toLowerCase()+"OpenRequests = ? AND amount = ? AND SellOrBuy = ?");
        if(SellOrBuy.equals("sell")){
            preparedStatement.setString(1,Price);
            preparedStatement.setString(2,Turnover);
            preparedStatement.setString(3,"buy");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){

            }
        }
        else{
            preparedStatement.setString(1,Price);
            preparedStatement.setString(2,Turnover);
            preparedStatement.setString(3,"sell");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){

            }
        }
    }
}
