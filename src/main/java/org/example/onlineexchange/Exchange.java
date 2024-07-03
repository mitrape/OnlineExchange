package org.example.onlineexchange;

import javafx.application.Platform;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.ResourceBundle;



public class Exchange implements Initializable {
    @FXML
    private MenuButton CurrencyButton;
    @FXML
    private ToggleButton sellButton ;
    @FXML
    private ToggleButton buyButton;
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
    public String sellOrBuy = null;
    Date date = Calendar.getInstance().getTime();
    DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup = new ToggleGroup();
        buyButton.setToggleGroup(toggleGroup);
        sellButton.setToggleGroup(toggleGroup);

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
    public void ClickOnBuyButton (ActionEvent event) {
        sellOrBuy = "buy";
    }
    public void ClickOnSellButton (ActionEvent event){
        sellOrBuy = "sell";
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

        String price = PriceTextField.getText();
        String turnover = TurnoverTextField.getText();

        if(sellOrBuy!=null) {
            if(!CurrencyButton.getText().equals("currency")) {
                if (sellOrBuy.equals("buy")) {
                    if (PriceTextField.getText().isEmpty() || TurnoverTextField.getText().isEmpty()) {
                        errorText.setText("please fill all the text fields");
                        errorText.setVisible(true);
                    } else {
                        PreparedStatement prestm = Main.connection.prepareStatement("SELECT * FROM usersdata WHERE username = ?");
                        prestm.setString(1, Main.username);
                        ResultSet resultSet = prestm.executeQuery();
                        while (resultSet.next()) {
                            Double userMoney = resultSet.getDouble("money");
                            if (userMoney < (Double.parseDouble(price) * Double.parseDouble(turnover))) {
                                errorText.setText("you don't have enough money!");
                                errorText.setVisible(true);
                            } else {
                                // do the transaction
                                continueTransaction(Double.parseDouble(price), Double.parseDouble(turnover));
                            }
                        }
                    }
                } else {
                    //if (sellOrBuy.equals("sell"))
                    if (PriceTextField.getText().isEmpty() || TurnoverTextField.getText().isEmpty()) {
                        errorText.setText("please fill all the text fields");
                        errorText.setVisible(true);
                    } else {
                        PreparedStatement prestm = Main.connection.prepareStatement("SELECT * FROM usersdata WHERE username = ?");
                        prestm.setString(1, Main.username);
                        ResultSet resultSet = prestm.executeQuery();
                        while (resultSet.next()) {
                            double userCurrencyAmount = resultSet.getDouble("amountOf" + CurrencyButton.getText());
                            if (userCurrencyAmount < Double.parseDouble(turnover)) {
                                errorText.setText("you don't have enough money!");
                                errorText.setVisible(true);
                            } else {
                                // do the transaction
                                continueTransaction(Double.parseDouble(price), Double.parseDouble(turnover));
                            }
                        }
                    }
                }
            }
            else {
                errorText.setText("please choose currency");
                errorText.setVisible(true);
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
        if(!CurrencyButton.getText().isEmpty()) {
            userPrice.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("OpenRequests"));
            userTurnover.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("amount"));
            userTransactionType.setCellValueFactory(new PropertyValueFactory<Transaction, String>("SellOrBuy"));

            Price.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("OpenRequests"));
            Turnover.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("amount"));
            TransactionType.setCellValueFactory(new PropertyValueFactory<Transaction, String>("SellOrBuy"));
            if (CurrencyButton.getText().equals("TOMAN")) {
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
                statement.setString(1, Main.username);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Double OpenRequest = resultSet.getDouble("Transaction");
                    Double amount = resultSet.getDouble("amount");
                    String SellOrBuy = resultSet.getString("SellOrBuy");
                    transaction = new Transaction(OpenRequest, amount, SellOrBuy);
                    userTable.getItems().add(transaction);
                }
            } else if (CurrencyButton.getText().equals("USD")) {
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
                statement.setString(1, Main.username);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Double OpenRequest = resultSet.getDouble("Transaction");
                    Double amount = resultSet.getDouble("amount");
                    String SellOrBuy = resultSet.getString("SellOrBuy");
                    transaction = new Transaction(OpenRequest, amount, SellOrBuy);
                    userTable.getItems().add(transaction);
                }
            } else if (CurrencyButton.getText().equals("YEN")) {
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
                statement.setString(1, Main.username);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Double OpenRequest = resultSet.getDouble("Transaction");
                    Double amount = resultSet.getDouble("amount");
                    String SellOrBuy = resultSet.getString("SellOrBuy");
                    transaction = new Transaction(OpenRequest, amount, SellOrBuy);
                    userTable.getItems().add(transaction);
                }
            } else if (CurrencyButton.getText().equals("EUR")) {
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
                statement.setString(1, Main.username);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Double OpenRequest = resultSet.getDouble("Transaction");
                    Double amount = resultSet.getDouble("amount");
                    String SellOrBuy = resultSet.getString("SellOrBuy");
                    transaction = new Transaction(OpenRequest, amount, SellOrBuy);
                    userTable.getItems().add(transaction);
                }
            } else {
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
                statement.setString(1, Main.username);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Double OpenRequest = resultSet.getDouble("Transaction");
                    Double amount = resultSet.getDouble("amount");
                    String SellOrBuy = resultSet.getString("SellOrBuy");
                    transaction = new Transaction(OpenRequest, amount, SellOrBuy);
                    userTable.getItems().add(transaction);
                }
            }
        }


    }
    public void addToLine (Double Price , Double Turnover , String currency) throws SQLException {
        PreparedStatement p3 = Main.connection.prepareStatement("INSERT INTO "+currency.toLowerCase()+"openrequests ( "+currency+"OpenRequests , amount , SellOrBuy , username , date)VAlUES (? , ? , ? , ? , ?)");
        p3.setDouble(1,Price);
        p3.setDouble(2,Turnover);
        p3.setString(3,sellOrBuy);
        p3.setString(4,Main.username);
        p3.setString(5,dateFormat.format(date));
        p3.executeUpdate();
    }
    public void continueTransaction (Double Price , Double Turnover) throws SQLException {

        String currency = CurrencyButton.getText();
        String usernameSecond = null;
        double priceSecond = 0;
        double turnoverSecond = 0;
        String dateSecond = null;


        if(sellOrBuy.equals("sell")) {
            PreparedStatement p1 = Main.connection.prepareStatement("SELECT  * FROM " + currency.toLowerCase() + "openrequests WHERE SellOrBuy = ?");
            p1.setString(1,"buy");
            ResultSet r1 = p1.executeQuery();
            boolean sw = false;
            while (r1.next()){
                if(r1.getDouble("amount") == Turnover  &&  r1.getDouble(currency.toLowerCase()+"OpenRequests") >= Price){

                    sw = true;

                    usernameSecond = r1.getString("username");
                    priceSecond = r1.getDouble(currency.toLowerCase()+"OpenRequests");
                    turnoverSecond = r1.getDouble("amount");
                    dateSecond = r1.getString("date");

                    PreparedStatement p2 = Main.connection.prepareStatement("DELETE FROM " + currency.toLowerCase() + "openrequests WHERE username = ? AND date  = ?");
                    p2.setString(1,usernameSecond);
                    p2.setString(2,dateSecond);
                    p2.executeUpdate();

                    PreparedStatement p3 = Main.connection.prepareStatement("INSERT INTO "+currency.toLowerCase()+"transaction (Transaction , amount , SellOrBuy , username , date)VAlUES (? , ? , ? , ? , ?)");
                    p3.setDouble(1,priceSecond);
                    p3.setDouble(2,Turnover);
                    p3.setString(3,"sell");
                    p3.setString(4,Main.username);
                    p3.setString(5,dateFormat.format(date));
                    p3.executeUpdate();

                    PreparedStatement p4 = Main.connection.prepareStatement("INSERT INTO "+currency.toLowerCase()+"transaction (Transaction , amount , SellOrBuy , username , date)VAlUES (? , ? , ? , ? , ?)");
                    p4.setDouble(1,priceSecond);
                    p4.setDouble(2,Turnover);
                    p4.setString(3,"buy");
                    p4.setString(4,usernameSecond);
                    p4.setString(5,dateFormat.format(date));
                    p4.executeUpdate();

                    double money1 = priceSecond*Turnover ;

                    PreparedStatement p5 = Main.connection.prepareStatement("UPDATE usersdata SET money = money + ? WHERE username = ?");
                    p5.setDouble(1,money1*0.01);
                    p5.setString(2,"admin");
                    p5.executeUpdate();

                    PreparedStatement p6 = Main.connection.prepareStatement("UPDATE usersdata SET money = money + ? WHERE username = ?");
                    p6.setDouble(1,money1*0.99);
                    p6.setString(2,Main.username);
                    p6.executeUpdate();

                    PreparedStatement p7 = Main.connection.prepareStatement("UPDATE usersdata SET money = money - ? WHERE username = ?");
                    p7.setDouble(1,money1);
                    p7.setString(2,usernameSecond);
                    p7.executeUpdate();

                    PreparedStatement p8 = Main.connection.prepareStatement("UPDATE usersdata SET amountOf"+currency.toLowerCase()+" = amountOf"+currency.toLowerCase()+" - ? WHERE username = ?");
                    p8.setDouble(1,Turnover);
                    p8.setString(2,Main.username);
                    p8.executeUpdate();

                    PreparedStatement p9 = Main.connection.prepareStatement("UPDATE usersdata SET amountOf"+currency.toLowerCase()+" = amountOf"+currency.toLowerCase()+" + ? WHERE username = ?");
                    p9.setDouble(1,0.99*Turnover);
                    p9.setString(2,usernameSecond);
                    p9.executeUpdate();

                    PreparedStatement p10 = Main.connection.prepareStatement("UPDATE usersdata SET amountOf"+currency.toLowerCase()+" = amountOf"+currency.toLowerCase()+" + ? WHERE username = ?");
                    p10.setDouble(1,0.01*Turnover);
                    p10.setString(2,"admin");
                    p10.executeUpdate();

                    errorText.setText("TRANSACTION DONE!");
                    errorText.setVisible(true);
                    break;
                }
            }
            if(!sw){
                addToLine(Price,Turnover,currency.toLowerCase());
            }
        }
        else {
            // if user choose buy
            PreparedStatement p1 = Main.connection.prepareStatement("SELECT  * FROM " + currency.toLowerCase() + "openrequests WHERE SellOrBuy = ?");
            p1.setString(1,"sell");
            ResultSet r1 = p1.executeQuery();
            boolean sw = false;
            while (r1.next()){
                if(r1.getDouble("amount") == Turnover  &&  r1.getDouble(currency.toLowerCase()+"OpenRequests") <= Price){

                    sw = true;

                    usernameSecond = r1.getString("username");
                    priceSecond = r1.getDouble(currency.toLowerCase()+"OpenRequests");
                    turnoverSecond = r1.getDouble("amount");
                    dateSecond = r1.getString("date");

                    PreparedStatement p2 = Main.connection.prepareStatement("DELETE FROM " + currency.toLowerCase() + "openrequests WHERE username = ? AND date  = ?");
                    p2.setString(1,usernameSecond);
                    p2.setString(2,dateSecond);
                    p2.executeUpdate();

                    PreparedStatement p3 = Main.connection.prepareStatement("INSERT INTO "+currency.toLowerCase()+"transaction (Transaction , amount , SellOrBuy , username , date)VAlUES (? , ? , ? , ? , ?)");
                    p3.setDouble(1,Price);
                    p3.setDouble(2,Turnover);
                    p3.setString(3,"buy");
                    p3.setString(4,Main.username);
                    p3.setString(5,dateFormat.format(date));
                    p3.executeUpdate();

                    PreparedStatement p4 = Main.connection.prepareStatement("INSERT INTO "+currency.toLowerCase()+"transaction (Transaction , amount , SellOrBuy , username , date)VAlUES (? , ? , ? , ? , ?)");
                    p4.setDouble(1,Price);
                    p4.setDouble(2,Turnover);
                    p4.setString(3,"sell");
                    p4.setString(4,usernameSecond);
                    p4.setString(5,dateFormat.format(date));
                    p4.executeUpdate();

                    double money1 = Price*Turnover ;

                    PreparedStatement p5 = Main.connection.prepareStatement("UPDATE usersdata SET money = money + ? WHERE username = ?");
                    p5.setDouble(1,money1*0.01);
                    p5.setString(2,"admin");
                    p5.executeUpdate();

                    PreparedStatement p6 = Main.connection.prepareStatement("UPDATE usersdata SET money = money - ? WHERE username = ?");
                    p6.setDouble(1,money1);
                    p6.setString(2,Main.username);
                    p6.executeUpdate();

                    PreparedStatement p7 = Main.connection.prepareStatement("UPDATE usersdata SET money = money + ? WHERE username = ?");
                    p7.setDouble(1,0.99*money1);
                    p7.setString(2,usernameSecond);
                    p7.executeUpdate();

                    PreparedStatement p8 = Main.connection.prepareStatement("UPDATE usersdata SET amountOf"+currency.toLowerCase()+" = amountOf"+currency.toLowerCase()+" + ? WHERE username = ?");
                    p8.setDouble(1,0.99*Turnover);
                    p8.setString(2,Main.username);
                    p8.executeUpdate();

                    PreparedStatement p9 = Main.connection.prepareStatement("UPDATE usersdata SET amountOf"+currency.toLowerCase()+" = amountOf"+currency.toLowerCase()+" - ? WHERE username = ?");
                    p9.setDouble(1,Turnover);
                    p9.setString(2,usernameSecond);
                    p9.executeUpdate();

                    PreparedStatement p10 = Main.connection.prepareStatement("UPDATE usersdata SET amountOf"+currency.toLowerCase()+" = amountOf"+currency.toLowerCase()+" + ? WHERE username = ?");
                    p10.setDouble(1,0.01*Turnover);
                    p10.setString(2,"admin");
                    p10.executeUpdate();

                    errorText.setText("TRANSACTION DONE!");
                    errorText.setVisible(true);
                    break;
                }
            }
            if(!sw){
                addToLine(Price,Turnover,currency.toLowerCase());
            }
        }

    }
}
