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
import javafx.scene.chart.XYChart;
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

    public double lastUSDprice ;


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
    private LineChart lineChartHour;
    @FXML
    private LineChart lineChartDay;
    @FXML
    private LineChart lineChartWeek;
    @FXML
    private LineChart lineChartMonth;
    @FXML
    private LineChart lineChartYear;
    int lastMinute = -1;
    private volatile boolean stop = false;
    ObservableList <Double> TransactionList = FXCollections.observableArrayList();
    ObservableList <Double> OpenRequestsList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showTime();
        setTable();
        setChangeAndSetPrice();
        showTime1();
        lastUSDprice = Double.parseDouble(HomePage.USDinfo[2]);
        lineChartMinute.setVisible(false);
        lineChartHour.setVisible(true);
        lineChartDay.setVisible(false);
        lineChartWeek.setVisible(false);
        lineChartMonth.setVisible(false);
        lineChartYear.setVisible(false);

        setLineChartYear();
        setLineChartMinute();
        setLineChartHour();
    }

    public void showTime1(){
        Thread thread = new Thread(() -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss a");
            while (!stop){
                try {
                    Thread.sleep(1000);
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
//                        TransactionList.clear();
//                        OpenRequestsList.clear();
                        setChangeAndSetPrice();
                        setTable();
                        //lineChartMinute
                        // باید کلیز کنخم

                        setLineChartMinute();
                        setLineChartHour();


                        lastMinute = currentMinute;
                    }
                });
            }
        });
        thread.start();
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

    public void setLineChartYear (){
        XYChart.Series<String, Number> regressionLine = new XYChart.Series<>();

        regressionLine.getData().add(new XYChart.Data<>("2021",  lastUSDprice + HomePage.slopeUSD*(-3 * 365 * 24 * 60)));
        regressionLine.getData().add(new XYChart.Data<>("2022", lastUSDprice + HomePage.slopeUSD*(-2 * 365 * 24 * 60)));
        regressionLine.getData().add(new XYChart.Data<>("2023", lastUSDprice + HomePage.slopeUSD*(-365 * 24 * 60)));
        regressionLine.getData().add(new XYChart.Data<>("2024",lastUSDprice ));

        lineChartYear.getData().add(regressionLine);
    }

    public void setLineChartMinute (){

        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        String time5;
        String time4;
        String time3;
        String time2;
        String time1;

        XYChart.Series<String, Number> regressionLine = new XYChart.Series<>();
        if(minute<5){
            time5 = (hour-1)+":"+(60+minute-5);
        }
        else{
            time5 = hour+":"+(minute-5);
        }
        if(minute<4){
            time4 = (hour-1)+":"+(60+minute-4);
        }
        else{
            time4 = hour+":"+(minute-4);
        }
        if(minute<3){
            time3 = (hour-1)+":"+(60+minute-3);
        }
        else{
            time3 = hour+":"+(minute-3);
        }
        if(minute<2){
            time2 = (hour-1)+":"+(60+minute-2);
        }
        else{
            time2 = hour+":"+(minute-2);
        }
        if(minute<1){
            time1 = (hour-1)+":"+(60+minute-1);
        }
        else{
            time1 = hour+":"+(minute-1);
        }
        String time0 = hour+":"+minute;

        regressionLine.getData().add(new XYChart.Data<>(time5, HomePage.USD[HomePage.USD.length-6]));
        regressionLine.getData().add(new XYChart.Data<>(time4, HomePage.USD[HomePage.USD.length-5]));
        regressionLine.getData().add(new XYChart.Data<>(time3, HomePage.USD[HomePage.USD.length-4]));
        regressionLine.getData().add(new XYChart.Data<>(time2, HomePage.USD[HomePage.USD.length-3]));
        regressionLine.getData().add(new XYChart.Data<>(time1, HomePage.USD[HomePage.USD.length-2]));
        regressionLine.getData().add(new XYChart.Data<>(time0, HomePage.USD[HomePage.USD.length-1]));

        lineChartMinute.getData().add(regressionLine);
    }
    public void ClickOnMinute (ActionEvent event) throws IOException {
        lineChartMinute.setVisible(true);
        lineChartHour.setVisible(false);
        lineChartDay.setVisible(false);
        lineChartWeek.setVisible(false);
        lineChartMonth.setVisible(false);
        lineChartYear.setVisible(false);
    }
    public void ClickOnHour (ActionEvent event) throws IOException {
        lineChartMinute.setVisible(false);
        lineChartHour.setVisible(true);
        lineChartDay.setVisible(false);
        lineChartWeek.setVisible(false);
        lineChartMonth.setVisible(false);
        lineChartYear.setVisible(false);
    }
    public void ClickOnDay (ActionEvent event) throws IOException {
        lineChartMinute.setVisible(false);
        lineChartHour.setVisible(false);
        lineChartDay.setVisible(true);
        lineChartWeek.setVisible(false);
        lineChartMonth.setVisible(false);
        lineChartYear.setVisible(false);
    }
    public void ClickOnWeek (ActionEvent event) throws IOException {
        lineChartMinute.setVisible(false);
        lineChartHour.setVisible(false);
        lineChartDay.setVisible(false);
        lineChartWeek.setVisible(true);
        lineChartMonth.setVisible(false);
        lineChartYear.setVisible(false);
    }
    public void ClickOnMonth (ActionEvent event) throws IOException {
        lineChartMinute.setVisible(false);
        lineChartHour.setVisible(false);
        lineChartDay.setVisible(false);
        lineChartWeek.setVisible(false);
        lineChartMonth.setVisible(true);
        lineChartYear.setVisible(false);
    }
    public void ClickOnYear (ActionEvent event) throws IOException {
        lineChartMinute.setVisible(false);
        lineChartHour.setVisible(false);
        lineChartDay.setVisible(false);
        lineChartWeek.setVisible(false);
        lineChartMonth.setVisible(false);
        lineChartYear.setVisible(true);
    }

    public void setLineChartHour (){

        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);

        String time5;
        String time4;
        String time3;
        String time2;
        String time1;

        XYChart.Series<String, Number> regressionLine = new XYChart.Series<>();
        if(hour<5){
            time5 = (hour+12-5)+":"+minute;
        }
        else{
            time5 = (hour-5)+":"+minute;
        }
        if(hour<4){
            time4 = (hour+12-4)+":"+minute;
        }
        else{
            time4 = (hour-4)+":"+minute;
        }
        if(hour<3){
            time3 = (hour+12-3)+":"+minute;
        }
        else{
            time3 = (hour-3)+":"+minute;
        }
        if(hour<2){
            time2 = (hour+12-2)+":"+minute;
        }
        else{
            time2 = (hour-2)+":"+minute;
        }
        if(hour<1){
            time1 = (hour+12-1)+":"+minute;
        }
        else{
            time1 = (hour-1)+":"+minute;
        }
        String time0 = hour+":"+minute;

        regressionLine.getData().add(new XYChart.Data<>(time5, HomePage.USD[HomePage.USD.length-5*60]));
        regressionLine.getData().add(new XYChart.Data<>(time4, HomePage.USD[HomePage.USD.length-4*60]));
        regressionLine.getData().add(new XYChart.Data<>(time3, HomePage.USD[HomePage.USD.length-3*60]));
        regressionLine.getData().add(new XYChart.Data<>(time2, HomePage.USD[HomePage.USD.length-2*60]));
        regressionLine.getData().add(new XYChart.Data<>(time1, HomePage.USD[HomePage.USD.length-1*60]));
        regressionLine.getData().add(new XYChart.Data<>(time0, HomePage.USD[HomePage.USD.length-1]));

        lineChartHour.getData().add(regressionLine);
    }



}

