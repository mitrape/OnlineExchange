package org.example.onlineexchange;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class USD implements Initializable {

    @FXML
    private Label timeLabel ;

    @FXML
    private Label priceLabel ;
    @FXML
    private Label changeLabel ;



    @FXML
    private TableView<Double> TransactionTable;
    @FXML
    private TableColumn<Double, Double> TransactionColumn;
    @FXML
    private TableView <Double> OpenRequestsTable ;
    @FXML
    private TableColumn <Double,Double> OpenRequestsColumn ;
    @FXML
    private LineChart lineChartMinute;
    @FXML
    private LineChart lineChartDay;
    @FXML
    private LineChart lineChartWeek;
    @FXML
    private LineChart lineChartMonth;
    @FXML
    private LineChart lineChartYear;

    ObservableList <Double> TransactionList = FXCollections.observableArrayList();
    ObservableList <Double> OpenRequestsList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showTime();
        setTable();
        setChangeAndSetPrice();
    }


    public void ClickOnBack (ActionEvent event) throws IOException {
        Main m = new Main() ;
        m.changeScene("homePage");
    }
    public void ClickOnExit (ActionEvent event) throws IOException {
        System.exit(0);
    }
    public void showTime (){
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            String formattedTime = currentTime.format(formatter);
            timeLabel.setText(formattedTime);
        }), new KeyFrame(Duration.seconds(1)));

        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }
    public void setTable (){
        TransactionColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
        OpenRequestsColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
        try {
            Statement statement1 = Main.connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery("SELECT usdTransaction FROM usd");
            while (resultSet1.next()){
                Double usdTransaction = resultSet1.getDouble("usdTransaction");
                TransactionList.add(usdTransaction);
            }
            TransactionTable.setItems(TransactionList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            Statement statement1 = Main.connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery("SELECT usdOpenRequests FROM usd");
            while (resultSet1.next()){
                Double usdOpenRequests = resultSet1.getDouble("usdOpenRequests");
                OpenRequestsList.add(usdOpenRequests);
            }
            OpenRequestsTable.setItems(OpenRequestsList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void setChangeAndSetPrice (){
        priceLabel.setText(String.format("%.4f",Double.parseDouble(HomePage.USDinfo[2])));
        changeLabel.setText(String.format("%.2f",(Double.parseDouble(HomePage.USDinfo[2]) - Double.parseDouble(HomePage.USDinfo[3])) / (Double.parseDouble(HomePage.USDinfo[3])) * 100 ) +"%");
    }


}
