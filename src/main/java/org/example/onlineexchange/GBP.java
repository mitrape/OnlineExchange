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
import java.util.Collections;
import java.util.Date;
import java.util.ResourceBundle;

import java.time.LocalDate;

public class GBP implements Initializable {

    @FXML
    private Label timeLabel ;

    @FXML
    private Label priceLabel ;
    @FXML
    private Label changeLabel ;

    public double lastGBPprice ;

    public XYChart.Series<String, Number> regressionLineYear;
    public XYChart.Series<String, Number> regressionLineMinute;
    public XYChart.Series<String, Number> regressionLineHour;
    public XYChart.Series<String, Number> regressionLineDay;
    public XYChart.Series<String, Number> regressionLineWeek;
    public XYChart.Series<String, Number> regressionLineMonth;



    @FXML
    private TableView<String> TransactionTable;
    @FXML
    private TableColumn<String, String> TransactionColumn;
    @FXML
    private TableView <String> OpenRequestsTable ;
    @FXML
    private TableColumn <String,String> OpenRequestsColumn ;
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
    ObservableList <String> TransactionList = FXCollections.observableArrayList();
    ObservableList <String> OpenRequestsList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showTime();
        setTable();
        setChangeAndSetPrice();
        showTime1();
        lastGBPprice = Double.parseDouble(HomePage.GBPinfo[2]);
        lineChartMinute.setVisible(false);
        lineChartHour.setVisible(true);
        lineChartDay.setVisible(false);
        lineChartWeek.setVisible(false);
        lineChartMonth.setVisible(false);
        lineChartYear.setVisible(false);

        setLineChartYear();
        setLineChartMinute();
        setLineChartHour();
        setLineChartDay();
        setLineChartWeek();
        setLineChartMonth();
    }

    public void showTime1(){
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
                        TransactionList.clear();
                        OpenRequestsList.clear();

                        setChangeAndSetPrice();
                        setTable();

                        regressionLineHour.getData().removeAll(Collections.singleton(lineChartHour.getData().setAll()));
                        regressionLineMinute.getData().removeAll(Collections.singleton(lineChartMinute.getData().setAll()));
                        regressionLineDay.getData().removeAll(Collections.singleton(lineChartDay.getData().setAll()));
                        regressionLineWeek.getData().removeAll(Collections.singleton(lineChartWeek.getData().setAll()));
                        regressionLineMonth.getData().removeAll(Collections.singleton(lineChartMonth.getData().setAll()));


                        setLineChartMinute();
                        setLineChartHour();
                        setLineChartDay();
                        setLineChartWeek();
                        setLineChartMonth();

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
            ResultSet resultSet1 = statement1.executeQuery("SELECT * FROM gbptransaction");
            while (resultSet1.next()){
                if(resultSet1.getString("demo").equals("false")) {
                    TransactionList.add(String.format("%.4f",resultSet1.getDouble("Transaction")*resultSet1.getDouble("amount"))+"->"+resultSet1.getString("SellOrBuy"));
                }
                if(Main.demoState.equals("true") && resultSet1.getString("username").equals(Main.username) && resultSet1.getString("demo").equals("true")) {
                    TransactionList.add(String.format("%.4f",resultSet1.getDouble("Transaction")*resultSet1.getDouble("amount"))+"->"+resultSet1.getString("SellOrBuy"));
                }
            }
            TransactionTable.setItems(TransactionList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            Statement statement1 = Main.connection.createStatement();
            ResultSet resultSet1 = statement1.executeQuery("SELECT * FROM gbpopenrequests");
            while (resultSet1.next()){
                if(resultSet1.getString("demo").equals("false")) {
                    OpenRequestsList.add(String.format("%.4f",resultSet1.getDouble("gbpOpenRequests")*resultSet1.getDouble("amount"))+"->"+resultSet1.getString("SellOrBuy"));
                }
                if(Main.demoState.equals("true") && resultSet1.getString("username").equals(Main.username) && resultSet1.getString("demo").equals("true")){
                    OpenRequestsList.add(String.format("%.4f",resultSet1.getDouble("gbpOpenRequests")*resultSet1.getDouble("amount"))+"->"+resultSet1.getString("SellOrBuy"));
                }
            }
            OpenRequestsTable.setItems(OpenRequestsList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void setChangeAndSetPrice (){
        priceLabel.setText(String.format("%.4f",Double.parseDouble(HomePage.GBPinfo[2])));
        changeLabel.setText(String.format("%.2f",(Double.parseDouble(HomePage.GBPinfo[2]) - Double.parseDouble(HomePage.GBPinfo[3])) / (Double.parseDouble(HomePage.GBPinfo[3])) * 100 ) +"%");
    }

    public void setLineChartYear (){
        regressionLineYear = new XYChart.Series<>();

        regressionLineYear.getData().add(new XYChart.Data<>("2024",lastGBPprice ));
        regressionLineYear.getData().add(new XYChart.Data<>("2025", lastGBPprice + HomePage.slopeGBP*(+365 * 24 * 60)));
        regressionLineYear.getData().add(new XYChart.Data<>("2026", lastGBPprice + HomePage.slopeGBP*(+2 * 365 * 24 * 60)));
        regressionLineYear.getData().add(new XYChart.Data<>("2027",  lastGBPprice + HomePage.slopeGBP*(+3 * 365 * 24 * 60)));

        lineChartYear.getData().add(regressionLineYear);
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

        regressionLineMinute = new XYChart.Series<>();
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

        regressionLineMinute.getData().add(new XYChart.Data<>(time5, HomePage.GBP[HomePage.GBP.length-5]));
        regressionLineMinute.getData().add(new XYChart.Data<>(time4, HomePage.GBP[HomePage.GBP.length-4]));
        regressionLineMinute.getData().add(new XYChart.Data<>(time3, HomePage.GBP[HomePage.GBP.length-3]));
        regressionLineMinute.getData().add(new XYChart.Data<>(time2, HomePage.GBP[HomePage.GBP.length-2]));
        regressionLineMinute.getData().add(new XYChart.Data<>(time1, HomePage.GBP[HomePage.GBP.length-1]));
        regressionLineMinute.getData().add(new XYChart.Data<>(time0, Double.parseDouble(HomePage.GBPinfo[2])));

        lineChartMinute.getData().add(regressionLineMinute);
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

        regressionLineHour = new XYChart.Series<>();
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

        regressionLineHour.getData().add(new XYChart.Data<>(time5, HomePage.GBP[HomePage.GBP.length-5*60+1]));
        regressionLineHour.getData().add(new XYChart.Data<>(time4, HomePage.GBP[HomePage.GBP.length-4*60+1]));
        regressionLineHour.getData().add(new XYChart.Data<>(time3, HomePage.GBP[HomePage.GBP.length-3*60+1]));
        regressionLineHour.getData().add(new XYChart.Data<>(time2, HomePage.GBP[HomePage.GBP.length-2*60+1]));
        regressionLineHour.getData().add(new XYChart.Data<>(time1, HomePage.GBP[HomePage.GBP.length-1*60+1]));
        regressionLineHour.getData().add(new XYChart.Data<>(time0, Double.parseDouble(HomePage.GBPinfo[2])));

        lineChartHour.getData().add(regressionLineHour);
    }

    public void setLineChartDay (){

        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);

        String time4;
        String time3;
        String time2;
        String time1;

        regressionLineDay = new XYChart.Series<>();
        if(day<4){
            time4 = (month+1)+"/"+(day+30-4);
        }
        else{
            time4 = (month+1)+"/"+(day-4);
        }
        if(day<3){
            time3 = (month+1)+"/"+(day+30-3);
        }
        else{
            time3 = (month+1)+"/"+(day-3);
        }
        if(day<2){
            time2 = (month+1)+"/"+(day+30-2);
        }
        else{
            time2 = (month+1)+"/"+(day-2);
        }
        if(day<1){
            time1 = (month+1)+"/"+(day+30-1);
        }
        else{
            time1 = (month+1)+"/"+(day-1);
        }

        String time0 = (month+1)+"/"+day;

        regressionLineDay.getData().add(new XYChart.Data<>(time4, Double.parseDouble(HomePage.GBPinfo[2])+HomePage.slopeGBP*(-4*24*60)));
        regressionLineDay.getData().add(new XYChart.Data<>(time3, Double.parseDouble(HomePage.GBPinfo[2])+HomePage.slopeGBP*(-3*24*60)));
        regressionLineDay.getData().add(new XYChart.Data<>(time2, Double.parseDouble(HomePage.GBPinfo[2])+HomePage.slopeGBP*(-2*24*60)));
        regressionLineDay.getData().add(new XYChart.Data<>(time1, Double.parseDouble(HomePage.GBPinfo[2])+HomePage.slopeGBP*(-24*60)));
        regressionLineDay.getData().add(new XYChart.Data<>(time0, Double.parseDouble(HomePage.GBPinfo[2])));

        lineChartDay.getData().add(regressionLineDay);
    }

    public void setLineChartWeek (){

        regressionLineWeek = new XYChart.Series<>();
        LocalDate today = LocalDate.now();

        regressionLineWeek.getData().add(new XYChart.Data<>(String.valueOf(today), Double.parseDouble(HomePage.GBPinfo[2])));
        regressionLineWeek.getData().add(new XYChart.Data<>(String.valueOf(today.plusDays(7)), Double.parseDouble(HomePage.GBPinfo[2])+HomePage.slopeGBP*(7*24*60)));
        regressionLineWeek.getData().add(new XYChart.Data<>(String.valueOf(today.plusDays(14)), Double.parseDouble(HomePage.GBPinfo[2])+HomePage.slopeGBP*(2*7*24*60)));
        regressionLineWeek.getData().add(new XYChart.Data<>(String.valueOf(today.plusDays(21)), Double.parseDouble(HomePage.GBPinfo[2])+HomePage.slopeGBP*(3*7*24*60)));
        regressionLineWeek.getData().add(new XYChart.Data<>(String.valueOf(today.plusDays(28)), Double.parseDouble(HomePage.GBPinfo[2])+HomePage.slopeGBP*(4*7*24*60)));

        lineChartWeek.getData().add(regressionLineWeek);
    }

    public void setLineChartMonth (){

        regressionLineMonth = new XYChart.Series<>();
        LocalDate today = LocalDate.now();

        regressionLineMonth.getData().add(new XYChart.Data<>(String.valueOf(today), Double.parseDouble(HomePage.GBPinfo[2])));
        regressionLineMonth.getData().add(new XYChart.Data<>(String.valueOf(today.plusMonths(1)), Double.parseDouble(HomePage.GBPinfo[2])+HomePage.slopeGBP*(+1*30*24*60)));
        regressionLineMonth.getData().add(new XYChart.Data<>(String.valueOf(today.plusMonths(2)), Double.parseDouble(HomePage.GBPinfo[2])+HomePage.slopeGBP*(+2*30*24*60)));
        regressionLineMonth.getData().add(new XYChart.Data<>(String.valueOf(today.plusMonths(3)), Double.parseDouble(HomePage.GBPinfo[2])+HomePage.slopeGBP*(+3*30*24*60)));
        regressionLineMonth.getData().add(new XYChart.Data<>(String.valueOf(today.plusMonths(4)), Double.parseDouble(HomePage.GBPinfo[2])+HomePage.slopeGBP*(+4*30*24*60)));

        lineChartMonth.getData().add(regressionLineMonth);
    }


}

