package org.example.onlineexchange;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import java.util.List;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class Admin implements Initializable {
    @FXML
    private TableView<TransactionUsername> TransactionTable;
    @FXML
    private TableColumn<TransactionUsername, String> currencyTran;
    @FXML
    private TableColumn<TransactionUsername, Double> priceTran;
    @FXML
    private TableColumn<TransactionUsername, Double> turnoverTran;
    @FXML
    private TableColumn<TransactionUsername, String> sellorbuyTran;
    @FXML
    private TableColumn<TransactionUsername, String> userTran;
    @FXML
    private TableColumn<TransactionUsername, String> dateTran;
    @FXML
    private Label time;
    int lastMinute = -1;
    private volatile boolean stop = false;
    public static boolean swExchange=true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showTime();
        try {
            allTransactions();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        showTime1();
    }
    public void showTime1() {
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
                            allTransactions();
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
    public void showTime (){
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedTime = currentTime.format(formatter);
            time.setText(formattedTime);
        }), new KeyFrame(Duration.seconds(1)));

        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    public void clickOnWallet(ActionEvent e) throws IOException {
        Main m = new Main();
        m.openNewWindow("Wallet","wallet");
    }
    public void clickOnOpen(ActionEvent e) throws IOException{
        swExchange=true;
    }
    public void clickOnClose(ActionEvent e) throws IOException{
        swExchange=false;
    }
    public void allTransactions () throws SQLException {
        TransactionTable.getItems().clear();

        currencyTran.setCellValueFactory(new PropertyValueFactory<TransactionUsername, String>("currency"));
        priceTran.setCellValueFactory(new PropertyValueFactory<TransactionUsername, Double>("transaction"));
        turnoverTran.setCellValueFactory(new PropertyValueFactory<TransactionUsername, Double>("amount"));
        sellorbuyTran.setCellValueFactory(new PropertyValueFactory<TransactionUsername, String>("sellOrBuy"));
        userTran.setCellValueFactory(new PropertyValueFactory<TransactionUsername, String>("username"));
        dateTran.setCellValueFactory(new PropertyValueFactory<TransactionUsername, String>("date"));


        PreparedStatement stmt1 = Main.connection.prepareStatement("SELECT * FROM tomantransaction ");
        ResultSet tomanResult = stmt1.executeQuery();
        while (tomanResult.next()) {
            TransactionTable.getItems().add(new TransactionUsername("toman", tomanResult.getDouble("Transaction"), tomanResult.getDouble("amount"), tomanResult.getString("SellOrBuy"), tomanResult.getString("username"),tomanResult.getString("date")));
        }


        PreparedStatement stmt2 = Main.connection.prepareStatement("SELECT * FROM eurtransaction ");
        ResultSet eurResult = stmt2.executeQuery();
        while (eurResult.next()) {
            TransactionTable.getItems().add(new TransactionUsername("eur", eurResult.getDouble("Transaction"), eurResult.getDouble("amount"), eurResult.getString("SellOrBuy"),eurResult.getString("username"), eurResult.getString("date")));
        }


        PreparedStatement stmt3 = Main.connection.prepareStatement("SELECT * FROM usdtransaction ");
        ResultSet usdResult = stmt3.executeQuery();
        while (usdResult.next()) {
            TransactionTable.getItems().add(new TransactionUsername("usd", usdResult.getDouble("Transaction"), usdResult.getDouble("amount"), usdResult.getString("SellOrBuy"),usdResult.getString("username"), usdResult.getString("date")));
        }


        PreparedStatement stmt4 = Main.connection.prepareStatement("SELECT * FROM yentransaction ");
        ResultSet yenResult = stmt4.executeQuery();
        while (yenResult.next()) {
            TransactionTable.getItems().add(new TransactionUsername("yen", yenResult.getDouble("Transaction"), yenResult.getDouble("amount"), yenResult.getString("SellOrBuy"),yenResult.getString("username"), yenResult.getString("date")));
        }


        PreparedStatement stmt5 = Main.connection.prepareStatement("SELECT * FROM gbptransaction  ");
        ResultSet gbpResult = stmt5.executeQuery();
        while (gbpResult.next()) {
            TransactionTable.getItems().add(new TransactionUsername("gbp", gbpResult.getDouble("Transaction"), gbpResult.getDouble("amount"), gbpResult.getString("SellOrBuy"),gbpResult.getString("username"), gbpResult.getString("date")));
        }
    }

}
