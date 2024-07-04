package org.example.onlineexchange;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import javax.imageio.IIOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.ResourceBundle;

public class Wallet implements Initializable{
    @FXML
    private Text totalCapital;
    @FXML
    private Text usdWealth;
    @FXML
    private Text eurWealth;
    @FXML
    private Text gbpWealth;
    @FXML
    private Text tomanWealth;
    @FXML
    private Text yenWealth;
    @FXML
    private LineChart yearlyWealth;
    @FXML
    private Label time;
    int lastMinute = -1;
    private volatile boolean stop = false;
    public XYChart.Series<String, Number> regressionLineYear;
    double yen;
    double usd;
    double toman;
    double gbp;
    double eur;
    double total;
    double[] totalMoney = new double[HomePage.USD.length*1000];
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setLabels();
            showTime();
            setLineChartYear();
            showTime1();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
                            setLabels();
                            setLineChartYear();
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

    public void setLabels () throws SQLException {
        PreparedStatement findUserStatement = Main.connection.prepareStatement("SELECT * FROM usersdata WHERE username = ?");
        findUserStatement.setString(1, Main.username);
        ResultSet resultSet = findUserStatement.executeQuery();
        if(resultSet.next()){
             usd=Double.parseDouble(resultSet.getString("amountOfUSD"));
             eur=Double.parseDouble(resultSet.getString("amountOfEUR"));
             yen =Double.parseDouble(resultSet.getString("amountOfYEN"));
             toman=Double.parseDouble(resultSet.getString("amountOfTOMAN"));
             gbp=Double.parseDouble(resultSet.getString("amountOfGBP"));
            usdWealth.setText(String.valueOf(usd));
            eurWealth.setText(String.valueOf(eur));
            yenWealth.setText(String.valueOf(yen));
            tomanWealth.setText(String.valueOf(toman));
            gbpWealth.setText(String.valueOf(gbp));
            total = usd*Double.parseDouble(HomePage.USDinfo[2])+yen*Double.parseDouble(HomePage.YENinfo[2])+eur*Double.parseDouble(HomePage.EURinfo[2])+toman*Double.parseDouble(HomePage.TOMANinfo[2])+gbp*Double.parseDouble(HomePage.GBPinfo[2]);
            totalCapital.setText(String.valueOf(total));
        }
        for (int i = 0; i < HomePage.USD.length; i++) {
            totalMoney[i]=usd*HomePage.USD[i]+yen*HomePage.YEN[i]+eur*HomePage.EUR[i]+toman*HomePage.TOMAN[i]+gbp*HomePage.GBP[i];
        }
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
    public double linearRegressionData (double[] currency){
        SimpleRegression regression = new SimpleRegression();

        for (int i = 0; i < currency.length; i++) {
            regression.addData(i + 1, currency[i]);
        }
        return regression.getSlope();
    }
    public void setLineChartYear (){
        double slope=linearRegressionData(totalMoney);
        regressionLineYear = new XYChart.Series<>();
        regressionLineYear.getData().add(new XYChart.Data<>("2024",  total));
        regressionLineYear.getData().add(new XYChart.Data<>("2025", total + slope*( 365 * 24 * 60)));
        regressionLineYear.getData().add(new XYChart.Data<>("2026", total + slope*(2*365 * 24 * 60)));
        regressionLineYear.getData().add(new XYChart.Data<>("2027",total + slope*(3*365 * 24 * 60)));
        yearlyWealth.getData().add(regressionLineYear);
    }

}
