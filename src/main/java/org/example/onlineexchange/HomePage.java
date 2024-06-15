
package org.example.onlineexchange;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.util.Duration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class HomePage implements Initializable{

    public boolean orderPrices = false; // which means that it is getting bigger at the end
    public boolean orderChanges = false;
    public boolean orderMaxPrice = false;
    public boolean orderMinPrice = false;

    public String[] data;
    public String[] time;
    public Double[] USD;
    public Double[] EUR;
    public Double[] TOMAN;
    public Double[] YEN;
    public Double[] GBP;

    @FXML
    private Button username ;
    @FXML
    private Label timeLabel ;
    @FXML
    private Label pos00 ;
    @FXML
    private Label pos01 ;
    @FXML
    private Label pos02 ;
    @FXML
    private Label pos03 ;
    @FXML
    private Label pos04 ;
    @FXML
    private Label pos10 ;
    @FXML
    private Label pos11 ;
    @FXML
    private Label pos12 ;
    @FXML
    private Label pos13 ;
    @FXML
    private Label pos14 ;
    @FXML
    private Label pos20 ;
    @FXML
    private Label pos21 ;
    @FXML
    private Label pos22 ;
    @FXML
    private Label pos23 ;
    @FXML
    private Label pos24 ;
    @FXML
    private Label pos30 ;
    @FXML
    private Label pos31 ;
    @FXML
    private Label pos32 ;
    @FXML
    private Label pos33 ;
    @FXML
    private Label pos34 ;
    @FXML
    private Label pos40 ;
    @FXML
    private Label pos41 ;
    @FXML
    private Label pos42 ;
    @FXML
    private Label pos43 ;
    @FXML
    private Label pos44 ;

    @FXML
    private ImageView img0 ;
    @FXML
    private ImageView img1 ;
    @FXML
    private ImageView img2 ;
    @FXML
    private ImageView img3 ;
    @FXML
    private ImageView img4 ;

    public ArrayList<Double> usd = new ArrayList<>();
    public ArrayList<Double> eur = new ArrayList<>();
    public ArrayList<Double> toman = new ArrayList<>();
    public ArrayList<Double> yen = new ArrayList<>();
    public ArrayList<Double> gbp = new ArrayList<>();



    // 0->photo path 1->currency name 2->price 3->last price 4->max price 5->min price 6->position
    public static String[] USDinfo = new String[7];
    public static String[] EURinfo = new String[7];
    public static String[] TOMANinfo = new String[7];
    public static String[] YENinfo = new String[7];
    public static String[] GBPinfo = new String[7];



    public String [] name = new String[5];
    public Double [] prices = new Double[5];
    public Double [] changes = new Double[5];
    public Double [] maxPrice = new Double[5];
    public Double [] minPrice = new Double[5];



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showTime();

        username.setText(Main.username);


        USDinfo [0] = "USD.jpg";
        Image USD1 = new Image(USDinfo[0]);
        img0.setImage(USD1);

        USDinfo [1] = "USD";


        EURinfo [0] = "EUR.jpg";
        Image EUR1 = new Image(EURinfo[0]);
        img1.setImage(EUR1);

        EURinfo [1] = "EUR";


        TOMANinfo [0] = "Toman.jpg";
        Image TOMAN1 = new Image(TOMANinfo[0]);
        img2.setImage(TOMAN1);

        TOMANinfo [1] = "TOMAN";


        YENinfo [0] = "YEN.jpg";
        Image YEN1 = new Image(YENinfo[0]);
        img3.setImage(YEN1);

        YENinfo [1] = "YEN";


        GBPinfo [0] = "GBP.jpg";
        Image GBP1 = new Image(GBPinfo[0]);
        img4.setImage(GBP1);

        GBPinfo [1] = "GBP";

        USDinfo[6] = "0";
        EURinfo[6] = "1";
        TOMANinfo[6] = "2";
        YENinfo[6] ="3";
        GBPinfo[6] = "4";

        readCVS();
        setLabelsFirst();
        showTime1();

    }
    public void ClickOnProfile (ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("profile");
    }
    public void ClickOnExit (ActionEvent event) throws IOException{
        System.exit(0);
    }
    public void ClickOnTransfer (ActionEvent event) throws IOException{
        Main m = new Main();
        m.changeScene("transfer");
    }
    public void ClickOnExchange (ActionEvent event)throws IOException{
        Main m = new Main();
        m.changeScene("exchange");
    }
    public void ClickOnSwap (ActionEvent event) throws IOException{
        Main m = new Main();
        m.changeScene("swap");
    }
    public void ClickOnLogin (ActionEvent event) throws IOException{
        Main m = new Main();
        m.changeScene("login");
    }
    public void ClickOnSignUp (ActionEvent event) throws IOException{
        Main m = new Main();
        m.changeScene("signUp");
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
    public void readCVS (){
        String csvFile = "prices.csv";
        String line = "";
        String cvsSplitBy = ",";

        ArrayList<String> stringArray1 = new ArrayList<>();
        ArrayList<String> stringArray2 = new ArrayList<>();
        usd = new ArrayList<>();
        eur = new ArrayList<>();
        toman = new ArrayList<>();
        yen = new ArrayList<>();
        gbp = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    String[] data = line.split(cvsSplitBy);
                    stringArray1.add(data[0]);
                    stringArray2.add(data[1]);
                    usd.add(Double.parseDouble(data[2]));
                    eur.add(Double.parseDouble(data[3]));
                    toman.add(Double.parseDouble(data[4]));
                    yen.add(Double.parseDouble(data[5]));
                    gbp.add(Double.parseDouble(data[6]));
                }
            }

            data = stringArray1.toArray(new String[stringArray1.size()]);
            time = stringArray2.toArray(new String[stringArray2.size()]);
            USD = usd.toArray(new Double[usd.size()]);
            EUR = eur.toArray(new Double[eur.size()]);
            TOMAN = toman.toArray(new Double[toman.size()]);
            YEN = yen.toArray(new Double[yen.size()]);
            GBP = gbp.toArray(new Double[gbp.size()]);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Double linearRegression (Double[] currency){
        SimpleRegression regression = new SimpleRegression();

        for (int i = 0; i < currency.length; i++) {
            regression.addData(i + 1, currency[i]);
        }
        double nextX = currency.length + 1;
        Double predictedY = regression.predict(nextX);
        return predictedY;
    }
    public void setLabelsFirst(){
        pos00.setText(USDinfo[1]);
        pos01.setText(EURinfo[1]);
        pos02.setText(TOMANinfo[1]);
        pos03.setText(YENinfo[1]);
        pos04.setText(GBPinfo[1]);

        USDinfo[2] = String.valueOf(linearRegression(USD));
        pos10.setText(String.format("%.4f",Double.parseDouble(USDinfo[2])));
        EURinfo[2] = String.valueOf(linearRegression(EUR));
        pos11.setText(String.format("%.4f",Double.parseDouble(EURinfo[2])));
        TOMANinfo[2] = String.valueOf(linearRegression(TOMAN));
        pos12.setText(String.format("%.4f",Double.parseDouble(TOMANinfo[2])));
        YENinfo[2] = String.valueOf(linearRegression(YEN));
        pos13.setText(String.format("%.4f",Double.parseDouble(YENinfo[2])));
        GBPinfo[2] = String.valueOf(linearRegression(GBP));
        pos14.setText(String.format("%.4f",Double.parseDouble(GBPinfo[2])));


        int dataLength = data.length;

        USDinfo[3] = String.valueOf(USD[dataLength-1]);
        EURinfo[3] = String.valueOf(EUR[dataLength-1]);
        TOMANinfo[3] = String.valueOf(TOMAN[dataLength-1]);
        YENinfo[3] = String.valueOf(YEN[dataLength-1]);
        GBPinfo[3] = String.valueOf(GBP[dataLength-1]);

        pos20.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
        pos21.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
        pos22.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
        pos23.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
        pos24.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");

        Double maxUSD = USD[0];
        Double minUSD = USD[0];
        for (int i = 0; i < USD.length; i++) {
            if(USD[i] > maxUSD){
                maxUSD = USD[i];
            }
            if(USD[i] < minUSD){
                minUSD = USD[i];
            }
        }

        Double maxEUR = EUR[0];
        Double minEUR = EUR[0];
        for (int i = 0; i < EUR.length; i++) {
            if(EUR[i] > maxEUR){
                maxEUR = EUR[i];
            }
            if(EUR[i] < minEUR){
                minEUR = EUR[i];
            }
        }

        Double maxTOMAN = TOMAN[0];
        Double minTOMAN = TOMAN[0];
        for (int i = 0; i < TOMAN.length; i++) {
            if(TOMAN[i] > maxTOMAN){
                maxTOMAN = TOMAN[i];
            }
            if(TOMAN[i] < minTOMAN){
                minTOMAN = TOMAN[i];
            }
        }

        Double maxYEN = YEN[0];
        Double minYEN = YEN[0];
        for (int i = 0; i < YEN.length; i++) {
            if(YEN[i] > maxYEN){
                maxYEN = YEN[i];
            }
            if(YEN[i] < minYEN){
                minYEN = YEN[i];
            }
        }

        Double maxGBP = GBP[0];
        Double minGBP = GBP[0];
        for (int i = 0; i < GBP.length; i++) {
            if(GBP[i] > maxGBP){
                maxGBP = GBP[i];
            }
            if(GBP[i] < minGBP){
                minGBP = GBP[i];
            }
        }


        USDinfo[4] = String.valueOf(maxUSD);
        pos30.setText(USDinfo[4]);

        EURinfo[4] = String.valueOf(maxEUR);
        pos31.setText(EURinfo[4]);

        TOMANinfo[4] = String.valueOf(maxTOMAN);
        pos32.setText(TOMANinfo[4]);

        YENinfo[4] = String.valueOf(maxYEN);
        pos33.setText(YENinfo[4]);

        GBPinfo[4] = String.valueOf(maxGBP);
        pos34.setText(GBPinfo[4]);


        USDinfo[5] = String.valueOf(minUSD);
        pos40.setText(USDinfo[5]);

        EURinfo[5] = String.valueOf(minEUR);
        pos41.setText(EURinfo[5]);

        TOMANinfo[5] = String.valueOf(minTOMAN);
        pos42.setText(TOMANinfo[5]);

        YENinfo[5] = String.valueOf(minYEN);
        pos43.setText(YENinfo[5]);

        GBPinfo[5] = String.valueOf(minGBP);
        pos44.setText(GBPinfo[5]);
    }
    int lastMinute = -1;
    private volatile boolean stop = false;
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

                    ChangeLabels();
                    lastMinute = currentMinute;
                }
            });
        }
    });
    thread.start();
}


    public void ChangeLabels () {

        USDinfo[3] = USDinfo[2];
        EURinfo[3] = EURinfo[2];
        TOMANinfo[3] = TOMANinfo[2];
        YENinfo[3] = YENinfo[2];
        GBPinfo[3] = GBPinfo[2];

        usd.add(Double.parseDouble(USDinfo[2]));
        eur.add(Double.parseDouble(EURinfo[2]));
        toman.add(Double.parseDouble(TOMANinfo[2]));
        yen.add(Double.parseDouble(YENinfo[2]));
        gbp.add(Double.parseDouble(GBPinfo[2]));

        USD = usd.toArray(new Double[usd.size()]);
        EUR = eur.toArray(new Double[eur.size()]);
        TOMAN = toman.toArray(new Double[toman.size()]);
        YEN = yen.toArray(new Double[yen.size()]);
        GBP = gbp.toArray(new Double[gbp.size()]);
        USDinfo[2] = String.format("%.4f",linearRegression(USD));
        EURinfo[2] = String.format("%.4f",linearRegression(EUR));
        TOMANinfo[2] = String.format("%.4f",linearRegression(TOMAN));
        YENinfo[2] = String.format("%.4f",linearRegression(YEN));
        GBPinfo[2] = String.format("%.4f",linearRegression(GBP));

        Double maxUSD = USD[0];
        Double minUSD = USD[0];
        for (int i = 0; i < USD.length; i++) {
            if(USD[i] > maxUSD){
                maxUSD = USD[i];
            }
            if(USD[i] < minUSD){
                minUSD = USD[i];
            }
        }

        Double maxEUR = EUR[0];
        Double minEUR = EUR[0];
        for (int i = 0; i < EUR.length; i++) {
            if(EUR[i] > maxEUR){
                maxEUR = EUR[i];
            }
            if(EUR[i] < minEUR){
                minEUR = EUR[i];
            }
        }

        Double maxTOMAN = TOMAN[0];
        Double minTOMAN = TOMAN[0];
        for (int i = 0; i < TOMAN.length; i++) {
            if(TOMAN[i] > maxTOMAN){
                maxTOMAN = TOMAN[i];
            }
            if(TOMAN[i] < minTOMAN){
                minTOMAN = TOMAN[i];
            }
        }

        Double maxYEN = YEN[0];
        Double minYEN = YEN[0];
        for (int i = 0; i < YEN.length; i++) {
            if(YEN[i] > maxYEN){
                maxYEN = YEN[i];
            }
            if(YEN[i] < minYEN){
                minYEN = YEN[i];
            }
        }

        Double maxGBP = GBP[0];
        Double minGBP = GBP[0];
        for (int i = 0; i < GBP.length; i++) {
            if(GBP[i] > maxGBP){
                maxGBP = GBP[i];
            }
            if(GBP[i] < minGBP){
                minGBP = GBP[i];
            }
        }

        USDinfo[4] = String.valueOf(maxUSD);
        EURinfo[4] = String.valueOf(maxEUR);
        TOMANinfo[4] = String.valueOf(maxTOMAN);
        YENinfo[4] = String.valueOf(maxYEN);
        GBPinfo[4] = String.valueOf(maxGBP);
        USDinfo[5] = String.valueOf(minUSD);
        EURinfo[5] = String.valueOf(minEUR);
        TOMANinfo[5] = String.valueOf(minTOMAN);
        YENinfo[5] = String.valueOf(minYEN);
        GBPinfo[5] = String.valueOf(minGBP);

        if(pos00.getText().equals("TOMAN")){
            pos10.setText(TOMANinfo[2]);
            pos20.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
            pos30.setText(TOMANinfo[4]);
            pos40.setText(TOMANinfo[5]);
            TOMANinfo[6] = "0";
        }
        else if (pos01.getText().equals("TOMAN")) {
            pos11.setText(TOMANinfo[2]);
            pos21.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
            pos31.setText(TOMANinfo[4]);
            pos41.setText(TOMANinfo[5]);
            TOMANinfo[6] = "1";
        }
        else if (pos02.getText().equals("TOMAN")){
            pos12.setText(TOMANinfo[2]);
            pos22.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
            pos32.setText(TOMANinfo[4]);
            pos42.setText(TOMANinfo[5]);
            TOMANinfo[6]= "2";
        }
        else if(pos03.getText().equals("TOMAN")){
            pos13.setText(TOMANinfo[2]);
            pos23.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
            pos33.setText(TOMANinfo[4]);
            pos43.setText(TOMANinfo[5]);
            TOMANinfo[6]= "3";
        }
        else{
            pos14.setText(TOMANinfo[2]);
            pos24.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
            pos34.setText(TOMANinfo[4]);
            pos44.setText(TOMANinfo[5]);
            TOMANinfo[6]= "4";
        }


        if(pos00.getText().equals("EUR")){
            pos10.setText(EURinfo[2]);
            pos20.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
            pos30.setText(EURinfo[4]);
            pos40.setText(EURinfo[5]);
            EURinfo[6] = "0";
        }
        else if (pos01.getText().equals("EUR")) {
            pos11.setText(EURinfo[2]);
            pos21.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
            pos31.setText(EURinfo[4]);
            pos41.setText(EURinfo[5]);
            EURinfo[6] = "1";
        }
        else if (pos02.getText().equals("EUR")){
            pos12.setText(EURinfo[2]);
            pos22.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
            pos32.setText(EURinfo[4]);
            pos42.setText(EURinfo[5]);
            EURinfo[6]= "2";
        }
        else if(pos03.getText().equals("EUR")){
            pos13.setText(EURinfo[2]);
            pos23.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
            pos33.setText(EURinfo[4]);
            pos43.setText(EURinfo[5]);
            EURinfo[6]= "3";
        }
        else{
            pos14.setText(EURinfo[2]);
            pos24.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
            pos34.setText(EURinfo[4]);
            pos44.setText(EURinfo[5]);
            EURinfo[6]= "4";
        }

        if(pos00.getText().equals("USD")){
            pos10.setText(USDinfo[2]);
            pos20.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
            pos30.setText(USDinfo[4]);
            pos40.setText(USDinfo[5]);
            USDinfo[6] = "0";
        }
        else if (pos01.getText().equals("USD")) {
            pos11.setText(USDinfo[2]);
            pos21.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
            pos31.setText(USDinfo[4]);
            pos41.setText(USDinfo[5]);
            USDinfo[6] = "1";
        }
        else if (pos02.getText().equals("USD")){
            pos12.setText(USDinfo[2]);
            pos22.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
            pos32.setText(USDinfo[4]);
            pos42.setText(USDinfo[5]);
            USDinfo[6]= "2";
        }
        else if(pos03.getText().equals("USD")){
            pos13.setText(USDinfo[2]);
            pos23.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
            pos33.setText(USDinfo[4]);
            pos43.setText(USDinfo[5]);
            USDinfo[6]= "3";
        }
        else{
            pos14.setText(USDinfo[2]);
            pos24.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
            pos34.setText(USDinfo[4]);
            pos44.setText(USDinfo[5]);
            USDinfo[6]= "4";
        }

        if(pos00.getText().equals("YEN")){
            pos10.setText(YENinfo[2]);
            pos20.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
            pos30.setText(YENinfo[4]);
            pos40.setText(YENinfo[5]);
            YENinfo[6] = "0";
        }
        else if (pos01.getText().equals("YEN")) {
            pos11.setText(YENinfo[2]);
            pos21.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
            pos31.setText(YENinfo[4]);
            pos41.setText(YENinfo[5]);
            YENinfo[6] = "1";
        }
        else if (pos02.getText().equals("YEN")){
            pos12.setText(YENinfo[2]);
            pos22.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
            pos32.setText(YENinfo[4]);
            pos42.setText(YENinfo[5]);
            YENinfo[6]= "2";
        }
        else if(pos03.getText().equals("YEN")){
            pos13.setText(YENinfo[2]);
            pos23.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
            pos33.setText(YENinfo[4]);
            pos43.setText(YENinfo[5]);
            YENinfo[6]= "3";
        }
        else{
            pos14.setText(YENinfo[2]);
            pos24.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
            pos34.setText(YENinfo[4]);
            pos44.setText(YENinfo[5]);
            YENinfo[6]= "4";
        }

        if(pos00.getText().equals("GBP")){
            pos10.setText(GBPinfo[2]);
            pos20.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
            pos30.setText(GBPinfo[4]);
            pos40.setText(GBPinfo[5]);
            GBPinfo[6] = "0";
        }
        else if (pos01.getText().equals("GBP")) {
            pos11.setText(GBPinfo[2]);
            pos21.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
            pos31.setText(GBPinfo[4]);
            pos41.setText(GBPinfo[5]);
            GBPinfo[6] = "1";
        }
        else if (pos02.getText().equals("GBP")){
            pos12.setText(GBPinfo[2]);
            pos22.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
            pos32.setText(GBPinfo[4]);
            pos42.setText(GBPinfo[5]);
            GBPinfo[6]= "2";
        }
        else if(pos03.equals("GBP")){
            pos23.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
            pos33.setText(GBPinfo[4]);
            pos43.setText(GBPinfo[5]);
            GBPinfo[6]= "3";
        }
        else{
            pos14.setText(GBPinfo[2]);
            pos24.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
            pos34.setText(GBPinfo[4]);
            pos44.setText(GBPinfo[5]);
            GBPinfo[6]= "4";
        }
    }


    public void ClickOnPrice (ActionEvent event) throws IOException{
        name [0] = "TOMAN";
        name [1] = "USD";
        name [2] = "YEN";
        name [3] = "EUR";
        name [4] = "GBP";
        prices[0] = Double.parseDouble(TOMANinfo[2]);
        prices[1] = Double.parseDouble(USDinfo[2]);
        prices[2] = Double.parseDouble(YENinfo[2]);
        prices[3] = Double.parseDouble(EURinfo[2]);
        prices[4] = Double.parseDouble(GBPinfo[2]);

        for (int i = 0; i < 4; i++) {
            for (int j = i+1; j < 5; j++) {
                if(prices[j]>prices[i]){

                    String tempStr = name[i];
                    name[i] = name [j];
                    name[j] = tempStr;

                    Double tempDbl = prices[i];
                    prices[i] = prices [j];
                    prices[j] = tempDbl;
                }
            }
        }
        if(orderPrices){
            orderPrices = false;
            // aval bayad add bozorg bashe
            if(name[0].equals("TOMAN")){
                img0.setImage(new Image(TOMANinfo[0]));
                pos00.setText(TOMANinfo[1]);
                pos10.setText(TOMANinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos30.setText(TOMANinfo[4]);
                pos40.setText(TOMANinfo[5]);
                TOMANinfo[6] = "0";
            }
            else if (name[1].equals("TOMAN")) {
                img1.setImage(new Image(TOMANinfo[0]));
                pos01.setText(TOMANinfo[1]);
                pos11.setText(TOMANinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos31.setText(TOMANinfo[4]);
                pos41.setText(TOMANinfo[5]);
                TOMANinfo[6] = "1";
            }
            else if (name[2].equals("TOMAN")){
                img2.setImage(new Image(TOMANinfo[0]));
                pos02.setText(TOMANinfo[1]);
                pos12.setText(TOMANinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos32.setText(TOMANinfo[4]);
                pos42.setText(TOMANinfo[5]);
                TOMANinfo[6]= "2";
            }
            else if(name[3].equals("TOMAN")){
                img3.setImage(new Image(TOMANinfo[0]));
                pos03.setText(TOMANinfo[1]);
                pos13.setText(TOMANinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos33.setText(TOMANinfo[4]);
                pos43.setText(TOMANinfo[5]);
                TOMANinfo[6]= "3";
            }
            else{
                img4.setImage(new Image(TOMANinfo[0]));
                pos04.setText(TOMANinfo[1]);
                pos14.setText(TOMANinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos34.setText(TOMANinfo[4]);
                pos44.setText(TOMANinfo[5]);
                TOMANinfo[6]= "4";
            }


            if(name[0].equals("EUR")){
                img0.setImage(new Image(EURinfo[0]));
                pos00.setText(EURinfo[1]);
                pos10.setText(EURinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos30.setText(EURinfo[4]);
                pos40.setText(EURinfo[5]);
                EURinfo[6] = "0";
            }
            else if (name[1].equals("EUR")) {
                img1.setImage(new Image(EURinfo[0]));
                pos01.setText(EURinfo[1]);
                pos11.setText(EURinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos31.setText(EURinfo[4]);
                pos41.setText(EURinfo[5]);
                EURinfo[6] = "1";
            }
            else if (name[2].equals("EUR")){
                img2.setImage(new Image(EURinfo[0]));
                pos02.setText(EURinfo[1]);
                pos12.setText(EURinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos32.setText(EURinfo[4]);
                pos42.setText(EURinfo[5]);
                EURinfo[6]= "2";
            }
            else if(name[3].equals("EUR")){
                img3.setImage(new Image(EURinfo[0]));
                pos03.setText(EURinfo[1]);
                pos13.setText(EURinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos33.setText(EURinfo[4]);
                pos43.setText(EURinfo[5]);
                EURinfo[6]= "3";
            }
            else{
                img4.setImage(new Image(EURinfo[0]));
                pos04.setText(EURinfo[1]);
                pos14.setText(EURinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos34.setText(EURinfo[4]);
                pos44.setText(EURinfo[5]);
                EURinfo[6]= "4";
            }

            if(name[0].equals("USD")){
                img0.setImage(new Image(USDinfo[0]));
                pos00.setText(USDinfo[1]);
                pos10.setText(USDinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos30.setText(USDinfo[4]);
                pos40.setText(USDinfo[5]);
                USDinfo[6] = "0";
            }
            else if (name[1].equals("USD")) {
                img1.setImage(new Image(USDinfo[0]));
                pos01.setText(USDinfo[1]);
                pos11.setText(USDinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos31.setText(USDinfo[4]);
                pos41.setText(USDinfo[5]);
                USDinfo[6] = "1";
            }
            else if (name[2].equals("USD")){
                img2.setImage(new Image(USDinfo[0]));
                pos02.setText(USDinfo[1]);
                pos12.setText(USDinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos32.setText(USDinfo[4]);
                pos42.setText(USDinfo[5]);
                USDinfo[6]= "2";
            }
            else if(name[3].equals("USD")){
                img3.setImage(new Image(USDinfo[0]));
                pos03.setText(USDinfo[1]);
                pos13.setText(USDinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos33.setText(USDinfo[4]);
                pos43.setText(USDinfo[5]);
                USDinfo[6]= "3";
            }
            else{
                img4.setImage(new Image(USDinfo[0]));
                pos04.setText(USDinfo[1]);
                pos14.setText(USDinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos34.setText(USDinfo[4]);
                pos44.setText(USDinfo[5]);
                USDinfo[6]= "4";
            }

            if(name[0].equals("YEN")){
                img0.setImage(new Image(YENinfo[0]));
                pos00.setText(YENinfo[1]);
                pos10.setText(YENinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos30.setText(YENinfo[4]);
                pos40.setText(YENinfo[5]);
                YENinfo[6] = "0";
            }
            else if (name[1].equals("YEN")) {
                img1.setImage(new Image(YENinfo[0]));
                pos01.setText(YENinfo[1]);
                pos11.setText(YENinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos31.setText(YENinfo[4]);
                pos41.setText(YENinfo[5]);
                YENinfo[6] = "1";
            }
            else if (name[2].equals("YEN")){
                img2.setImage(new Image(YENinfo[0]));
                pos02.setText(YENinfo[1]);
                pos12.setText(YENinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos32.setText(YENinfo[4]);
                pos42.setText(YENinfo[5]);
                YENinfo[6]= "2";
            }
            else if(name[3].equals("YEN")){
                img3.setImage(new Image(YENinfo[0]));
                pos03.setText(YENinfo[1]);
                pos13.setText(YENinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos33.setText(YENinfo[4]);
                pos43.setText(YENinfo[5]);
                YENinfo[6]= "3";
            }
            else{
                img4.setImage(new Image(YENinfo[0]));
                pos04.setText(YENinfo[1]);
                pos14.setText(YENinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos34.setText(YENinfo[4]);
                pos44.setText(YENinfo[5]);
                YENinfo[6]= "4";
            }

            if(name[0].equals("GBP")){
                img0.setImage(new Image(GBPinfo[0]));
                pos00.setText(GBPinfo[1]);
                pos10.setText(GBPinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos30.setText(GBPinfo[4]);
                pos40.setText(GBPinfo[5]);
                GBPinfo[6] = "0";
            }
            else if (name[1].equals("GBP")) {
                img1.setImage(new Image(GBPinfo[0]));
                pos01.setText(GBPinfo[1]);
                pos11.setText(GBPinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos31.setText(GBPinfo[4]);
                pos41.setText(GBPinfo[5]);
                GBPinfo[6] = "1";
            }
            else if (name[2].equals("GBP")){
                img2.setImage(new Image(GBPinfo[0]));
                pos02.setText(GBPinfo[1]);
                pos12.setText(GBPinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos32.setText(GBPinfo[4]);
                pos42.setText(GBPinfo[5]);
                GBPinfo[6]= "2";
            }
            else if(name[3].equals("GBP")){
                img3.setImage(new Image(GBPinfo[0]));
                pos03.setText(GBPinfo[1]);
                pos13.setText(GBPinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos33.setText(GBPinfo[4]);
                pos43.setText(GBPinfo[5]);
                GBPinfo[6]= "3";
            }
            else{
                img4.setImage(new Image(GBPinfo[0]));
                pos04.setText(GBPinfo[1]);
                pos14.setText(GBPinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos34.setText(GBPinfo[4]);
                pos44.setText(GBPinfo[5]);
                GBPinfo[6]= "4";
            }


        }
        else {
            orderPrices = true;
            if(name[0].equals("TOMAN")){
                img4.setImage(new Image(TOMANinfo[0]));
                pos04.setText(TOMANinfo[1]);
                pos14.setText(TOMANinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos34.setText(TOMANinfo[4]);
                pos44.setText(TOMANinfo[5]);
                TOMANinfo[6] = "4";
            }
            else if (name[1].equals("TOMAN")) {
                img3.setImage(new Image(TOMANinfo[0]));
                pos03.setText(TOMANinfo[1]);
                pos13.setText(TOMANinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos33.setText(TOMANinfo[4]);
                pos43.setText(TOMANinfo[5]);
                TOMANinfo[6] = "3";
            }
            else if (name[2].equals("TOMAN")){
                img2.setImage(new Image(TOMANinfo[0]));
                pos02.setText(TOMANinfo[1]);
                pos12.setText(TOMANinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos32.setText(TOMANinfo[4]);
                pos42.setText(TOMANinfo[5]);
                TOMANinfo[6]= "2";
            }
            else if(name[3].equals("TOMAN")){
                img1.setImage(new Image(TOMANinfo[0]));
                pos01.setText(TOMANinfo[1]);
                pos11.setText(TOMANinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos31.setText(TOMANinfo[4]);
                pos41.setText(TOMANinfo[5]);
                TOMANinfo[6]= "1";
            }
            else{
                img0.setImage(new Image(TOMANinfo[0]));
                pos00.setText(TOMANinfo[1]);
                pos10.setText(TOMANinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos30.setText(TOMANinfo[4]);
                pos40.setText(TOMANinfo[5]);
                TOMANinfo[6]= "0";
            }


            if(name[0].equals("EUR")){
                img4.setImage(new Image(EURinfo[0]));
                pos04.setText(EURinfo[1]);
                pos14.setText(EURinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos34.setText(EURinfo[4]);
                pos44.setText(EURinfo[5]);
                EURinfo[6] = "4";
            }
            else if (name[1].equals("EUR")) {
                img3.setImage(new Image(EURinfo[0]));
                pos03.setText(EURinfo[1]);
                pos13.setText(EURinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos33.setText(EURinfo[4]);
                pos43.setText(EURinfo[5]);
                EURinfo[6] = "3";
            }
            else if (name[2].equals("EUR")){
                img2.setImage(new Image(EURinfo[0]));
                pos02.setText(EURinfo[1]);
                pos12.setText(EURinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos32.setText(EURinfo[4]);
                pos42.setText(EURinfo[5]);
                EURinfo[6]= "2";
            }
            else if(name[3].equals("EUR")){
                img1.setImage(new Image(EURinfo[0]));
                pos01.setText(EURinfo[1]);
                pos11.setText(EURinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos31.setText(EURinfo[4]);
                pos41.setText(EURinfo[5]);
                EURinfo[6]= "1";
            }
            else{
                img0.setImage(new Image(EURinfo[0]));
                pos00.setText(EURinfo[1]);
                pos10.setText(EURinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos30.setText(EURinfo[4]);
                pos40.setText(EURinfo[5]);
                EURinfo[6]= "0";
            }

            if(name[0].equals("USD")){
                img4.setImage(new Image(USDinfo[0]));
                pos04.setText(USDinfo[1]);
                pos14.setText(USDinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos34.setText(USDinfo[4]);
                pos44.setText(USDinfo[5]);
                USDinfo[6] = "4";
            }
            else if (name[1].equals("USD")) {
                img3.setImage(new Image(USDinfo[0]));
                pos03.setText(USDinfo[1]);
                pos13.setText(USDinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos33.setText(USDinfo[4]);
                pos43.setText(USDinfo[5]);
                USDinfo[6] = "3";
            }
            else if (name[2].equals("USD")){
                img2.setImage(new Image(USDinfo[0]));
                pos02.setText(USDinfo[1]);
                pos12.setText(USDinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos32.setText(USDinfo[4]);
                pos42.setText(USDinfo[5]);
                USDinfo[6]= "2";
            }
            else if(name[3].equals("USD")){
                img1.setImage(new Image(USDinfo[0]));
                pos01.setText(USDinfo[1]);
                pos11.setText(USDinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos31.setText(USDinfo[4]);
                pos41.setText(USDinfo[5]);
                USDinfo[6]= "1";
            }
            else{
                img0.setImage(new Image(USDinfo[0]));
                pos00.setText(USDinfo[1]);
                pos10.setText(USDinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos30.setText(USDinfo[4]);
                pos40.setText(USDinfo[5]);
                USDinfo[6]= "0";
            }

            if(name[0].equals("YEN")){
                img4.setImage(new Image(YENinfo[0]));
                pos04.setText(YENinfo[1]);
                pos14.setText(YENinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos34.setText(YENinfo[4]);
                pos44.setText(YENinfo[5]);
                YENinfo[6] = "4";
            }
            else if (name[1].equals("YEN")) {
                img3.setImage(new Image(YENinfo[0]));
                pos03.setText(YENinfo[1]);
                pos13.setText(YENinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos33.setText(YENinfo[4]);
                pos43.setText(YENinfo[5]);
                YENinfo[6] = "3";
            }
            else if (name[2].equals("YEN")){
                img2.setImage(new Image(YENinfo[0]));
                pos02.setText(YENinfo[1]);
                pos12.setText(YENinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos32.setText(YENinfo[4]);
                pos42.setText(YENinfo[5]);
                YENinfo[6]= "2";
            }
            else if(name[3].equals("YEN")){
                img1.setImage(new Image(YENinfo[0]));
                pos01.setText(YENinfo[1]);
                pos11.setText(YENinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos31.setText(YENinfo[4]);
                pos41.setText(YENinfo[5]);
                YENinfo[6]= "1";
            }
            else{
                img0.setImage(new Image(YENinfo[0]));
                pos00.setText(YENinfo[1]);
                pos10.setText(YENinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos30.setText(YENinfo[4]);
                pos40.setText(YENinfo[5]);
                YENinfo[6]= "0";
            }

            if(name[0].equals("GBP")){
                img4.setImage(new Image(GBPinfo[0]));
                pos04.setText(GBPinfo[1]);
                pos14.setText(GBPinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos34.setText(GBPinfo[4]);
                pos44.setText(GBPinfo[5]);
                GBPinfo[6] = "4";
            }
            else if (name[1].equals("GBP")) {
                img3.setImage(new Image(GBPinfo[0]));
                pos03.setText(GBPinfo[1]);
                pos13.setText(GBPinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos33.setText(GBPinfo[4]);
                pos43.setText(GBPinfo[5]);
                GBPinfo[6] = "3";
            }
            else if (name[2].equals("GBP")){
                img2.setImage(new Image(GBPinfo[0]));
                pos02.setText(GBPinfo[1]);
                pos12.setText(GBPinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos32.setText(GBPinfo[4]);
                pos42.setText(GBPinfo[5]);
                GBPinfo[6]= "2";
            }
            else if(name[3].equals("GBP")){
                img1.setImage(new Image(GBPinfo[0]));
                pos01.setText(GBPinfo[1]);
                pos11.setText(GBPinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos31.setText(GBPinfo[4]);
                pos41.setText(GBPinfo[5]);
                GBPinfo[6]= "1";
            }
            else{
                img0.setImage(new Image(GBPinfo[0]));
                pos00.setText(GBPinfo[1]);
                pos10.setText(GBPinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos30.setText(GBPinfo[4]);
                pos40.setText(GBPinfo[5]);
                GBPinfo[6]= "0";
            }
        }
    }
    public void ClickOnChanges (ActionEvent event) {
        name[0] = "TOMAN";
        name[1] = "USD";
        name[2] = "YEN";
        name[3] = "EUR";
        name[4] = "GBP";
        changes[0] = Math.abs(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3]))/Double.parseDouble(TOMANinfo[3]);
        changes[1] = Math.abs(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3]))/Double.parseDouble(USDinfo[3]);
        changes[2] = Math.abs(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3]))/Double.parseDouble(YENinfo[3]);
        changes[3] = Math.abs(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3]))/Double.parseDouble(EURinfo[3]);
        changes[4] = Math.abs(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3]))/Double.parseDouble(GBPinfo[3]);

        for (int i = 0; i < 4; i++) {
            for (int j = i+1; j < 5; j++) {
                if (changes[j] > changes[i]) {

                    String tempStr = name[i];
                    name[i] = name[j];
                    name[j] = tempStr;

                    Double tempDbl = changes[i];
                    changes[i] = changes[j];
                    changes[j] = tempDbl;
                }
            }
        }
        if (orderChanges) {
            orderChanges = false;
            // aval bayad add bozorg bashe
            if (name[0].equals("TOMAN")) {
                img0.setImage(new Image(TOMANinfo[0]));
                pos00.setText(TOMANinfo[1]);
                pos10.setText(TOMANinfo[2]);
                pos20.setText(String.format("%.2f", (Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100) + "%");
                pos30.setText(TOMANinfo[4]);
                pos40.setText(TOMANinfo[5]);
                TOMANinfo[6] = "0";
            } else if (name[1].equals("TOMAN")) {
                img1.setImage(new Image(TOMANinfo[0]));
                pos01.setText(TOMANinfo[1]);
                pos11.setText(TOMANinfo[2]);
                pos21.setText(String.format("%.2f", (Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100) + "%");
                pos31.setText(TOMANinfo[4]);
                pos41.setText(TOMANinfo[5]);
                TOMANinfo[6] = "1";
            } else if (name[2].equals("TOMAN")) {
                img2.setImage(new Image(TOMANinfo[0]));
                pos02.setText(TOMANinfo[1]);
                pos12.setText(TOMANinfo[2]);
                pos22.setText(String.format("%.2f", (Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100) + "%");
                pos32.setText(TOMANinfo[4]);
                pos42.setText(TOMANinfo[5]);
                TOMANinfo[6] = "2";
            } else if (name[3].equals("TOMAN")) {
                img3.setImage(new Image(TOMANinfo[0]));
                pos03.setText(TOMANinfo[1]);
                pos13.setText(TOMANinfo[2]);
                pos23.setText(String.format("%.2f", (Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100) + "%");
                pos33.setText(TOMANinfo[4]);
                pos43.setText(TOMANinfo[5]);
                TOMANinfo[6] = "3";
            } else {
                img4.setImage(new Image(TOMANinfo[0]));
                pos04.setText(TOMANinfo[1]);
                pos14.setText(TOMANinfo[2]);
                pos24.setText(String.format("%.2f", (Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100) + "%");
                pos34.setText(TOMANinfo[4]);
                pos44.setText(TOMANinfo[5]);
                TOMANinfo[6] = "4";
            }


            if (name[0].equals("EUR")) {
                img0.setImage(new Image(EURinfo[0]));
                pos00.setText(EURinfo[1]);
                pos10.setText(EURinfo[2]);
                pos20.setText(String.format("%.2f", (Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100) + "%");
                pos30.setText(EURinfo[4]);
                pos40.setText(EURinfo[5]);
                EURinfo[6] = "0";
            } else if (name[1].equals("EUR")) {
                img1.setImage(new Image(EURinfo[0]));
                pos01.setText(EURinfo[1]);
                pos11.setText(EURinfo[2]);
                pos21.setText(String.format("%.2f", (Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100) + "%");
                pos31.setText(EURinfo[4]);
                pos41.setText(EURinfo[5]);
                EURinfo[6] = "1";
            } else if (name[2].equals("EUR")) {
                img2.setImage(new Image(EURinfo[0]));
                pos02.setText(EURinfo[1]);
                pos12.setText(EURinfo[2]);
                pos22.setText(String.format("%.2f", (Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100) + "%");
                pos32.setText(EURinfo[4]);
                pos42.setText(EURinfo[5]);
                EURinfo[6] = "2";
            } else if (name[3].equals("EUR")) {
                img3.setImage(new Image(EURinfo[0]));
                pos03.setText(EURinfo[1]);
                pos13.setText(EURinfo[2]);
                pos23.setText(String.format("%.2f", (Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100) + "%");
                pos33.setText(EURinfo[4]);
                pos43.setText(EURinfo[5]);
                EURinfo[6] = "3";
            } else {
                img4.setImage(new Image(EURinfo[0]));
                pos04.setText(EURinfo[1]);
                pos14.setText(EURinfo[2]);
                pos24.setText(String.format("%.2f", (Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100) + "%");
                pos34.setText(EURinfo[4]);
                pos44.setText(EURinfo[5]);
                EURinfo[6] = "4";
            }

            if (name[0].equals("USD")) {
                img0.setImage(new Image(USDinfo[0]));
                pos00.setText(USDinfo[1]);
                pos10.setText(USDinfo[2]);
                pos20.setText(String.format("%.2f", (Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100) + "%");
                pos30.setText(USDinfo[4]);
                pos40.setText(USDinfo[5]);
                USDinfo[6] = "0";
            } else if (name[1].equals("USD")) {
                img1.setImage(new Image(USDinfo[0]));
                pos01.setText(USDinfo[1]);
                pos11.setText(USDinfo[2]);
                pos21.setText(String.format("%.2f", (Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100) + "%");
                pos31.setText(USDinfo[4]);
                pos41.setText(USDinfo[5]);
                USDinfo[6] = "1";
            } else if (name[2].equals("USD")) {
                img2.setImage(new Image(USDinfo[0]));
                pos02.setText(USDinfo[1]);
                pos12.setText(USDinfo[2]);
                pos22.setText(String.format("%.2f", (Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100) + "%");
                pos32.setText(USDinfo[4]);
                pos42.setText(USDinfo[5]);
                USDinfo[6] = "2";
            } else if (name[3].equals("USD")) {
                img3.setImage(new Image(USDinfo[0]));
                pos03.setText(USDinfo[1]);
                pos13.setText(USDinfo[2]);
                pos23.setText(String.format("%.2f", (Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100) + "%");
                pos33.setText(USDinfo[4]);
                pos43.setText(USDinfo[5]);
                USDinfo[6] = "3";
            } else {
                img4.setImage(new Image(USDinfo[0]));
                pos04.setText(USDinfo[1]);
                pos14.setText(USDinfo[2]);
                pos24.setText(String.format("%.2f", (Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100) + "%");
                pos34.setText(USDinfo[4]);
                pos44.setText(USDinfo[5]);
                USDinfo[6] = "4";
            }

            if (name[0].equals("YEN")) {
                img0.setImage(new Image(YENinfo[0]));
                pos00.setText(YENinfo[1]);
                pos10.setText(YENinfo[2]);
                pos20.setText(String.format("%.2f", (Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100) + "%");
                pos30.setText(YENinfo[4]);
                pos40.setText(YENinfo[5]);
                YENinfo[6] = "0";
            } else if (name[1].equals("YEN")) {
                img1.setImage(new Image(YENinfo[0]));
                pos01.setText(YENinfo[1]);
                pos11.setText(YENinfo[2]);
                pos21.setText(String.format("%.2f", (Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100) + "%");
                pos31.setText(YENinfo[4]);
                pos41.setText(YENinfo[5]);
                YENinfo[6] = "1";
            } else if (name[2].equals("YEN")) {
                img2.setImage(new Image(YENinfo[0]));
                pos02.setText(YENinfo[1]);
                pos12.setText(YENinfo[2]);
                pos22.setText(String.format("%.2f", (Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100) + "%");
                pos32.setText(YENinfo[4]);
                pos42.setText(YENinfo[5]);
                YENinfo[6] = "2";
            } else if (name[3].equals("YEN")) {
                img3.setImage(new Image(YENinfo[0]));
                pos03.setText(YENinfo[1]);
                pos13.setText(YENinfo[2]);
                pos23.setText(String.format("%.2f", (Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100) + "%");
                pos33.setText(YENinfo[4]);
                pos43.setText(YENinfo[5]);
                YENinfo[6] = "3";
            } else {
                img4.setImage(new Image(YENinfo[0]));
                pos04.setText(YENinfo[1]);
                pos14.setText(YENinfo[2]);
                pos24.setText(String.format("%.2f", (Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100) + "%");
                pos34.setText(YENinfo[4]);
                pos44.setText(YENinfo[5]);
                YENinfo[6] = "4";
            }

            if (name[0].equals("GBP")) {
                img0.setImage(new Image(GBPinfo[0]));
                pos00.setText(GBPinfo[1]);
                pos10.setText(GBPinfo[2]);
                pos20.setText(String.format("%.2f", (Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100) + "%");
                pos30.setText(GBPinfo[4]);
                pos40.setText(GBPinfo[5]);
                GBPinfo[6] = "0";
            } else if (name[1].equals("GBP")) {
                img1.setImage(new Image(GBPinfo[0]));
                pos01.setText(GBPinfo[1]);
                pos11.setText(GBPinfo[2]);
                pos21.setText(String.format("%.2f", (Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100) + "%");
                pos31.setText(GBPinfo[4]);
                pos41.setText(GBPinfo[5]);
                GBPinfo[6] = "1";
            } else if (name[2].equals("GBP")) {
                img2.setImage(new Image(GBPinfo[0]));
                pos02.setText(GBPinfo[1]);
                pos12.setText(GBPinfo[2]);
                pos22.setText(String.format("%.2f", (Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100) + "%");
                pos32.setText(GBPinfo[4]);
                pos42.setText(GBPinfo[5]);
                GBPinfo[6] = "2";
            } else if (name[3].equals("GBP")) {
                img3.setImage(new Image(GBPinfo[0]));
                pos03.setText(GBPinfo[1]);
                pos13.setText(GBPinfo[2]);
                pos23.setText(String.format("%.2f", (Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100) + "%");
                pos33.setText(GBPinfo[4]);
                pos43.setText(GBPinfo[5]);
                GBPinfo[6] = "3";
            } else {
                img4.setImage(new Image(GBPinfo[0]));
                pos04.setText(GBPinfo[1]);
                pos14.setText(GBPinfo[2]);
                pos24.setText(String.format("%.2f", (Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100) + "%");
                pos34.setText(GBPinfo[4]);
                pos44.setText(GBPinfo[5]);
                GBPinfo[6] = "4";
            }


        } else {
            orderChanges = true;
            if (name[0].equals("TOMAN")) {
                img4.setImage(new Image(TOMANinfo[0]));
                pos04.setText(TOMANinfo[1]);
                pos14.setText(TOMANinfo[2]);
                pos24.setText(String.format("%.2f", (Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100) + "%");
                pos34.setText(TOMANinfo[4]);
                pos44.setText(TOMANinfo[5]);
                TOMANinfo[6] = "4";
            } else if (name[1].equals("TOMAN")) {
                img3.setImage(new Image(TOMANinfo[0]));
                pos03.setText(TOMANinfo[1]);
                pos13.setText(TOMANinfo[2]);
                pos23.setText(String.format("%.2f", (Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100) + "%");
                pos33.setText(TOMANinfo[4]);
                pos43.setText(TOMANinfo[5]);
                TOMANinfo[6] = "3";
            } else if (name[2].equals("TOMAN")) {
                img2.setImage(new Image(TOMANinfo[0]));
                pos02.setText(TOMANinfo[1]);
                pos12.setText(TOMANinfo[2]);
                pos22.setText(String.format("%.2f", (Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100) + "%");
                pos32.setText(TOMANinfo[4]);
                pos42.setText(TOMANinfo[5]);
                TOMANinfo[6] = "2";
            } else if (name[3].equals("TOMAN")) {
                img1.setImage(new Image(TOMANinfo[0]));
                pos01.setText(TOMANinfo[1]);
                pos11.setText(TOMANinfo[2]);
                pos21.setText(String.format("%.2f", (Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100) + "%");
                pos31.setText(TOMANinfo[4]);
                pos41.setText(TOMANinfo[5]);
                TOMANinfo[6] = "1";
            } else {
                img0.setImage(new Image(TOMANinfo[0]));
                pos00.setText(TOMANinfo[1]);
                pos10.setText(TOMANinfo[2]);
                pos20.setText(String.format("%.2f", (Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100) + "%");
                pos30.setText(TOMANinfo[4]);
                pos40.setText(TOMANinfo[5]);
                TOMANinfo[6] = "0";
            }


            if (name[0].equals("EUR")) {
                img4.setImage(new Image(EURinfo[0]));
                pos04.setText(EURinfo[1]);
                pos14.setText(EURinfo[2]);
                pos24.setText(String.format("%.2f", (Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100) + "%");
                pos34.setText(EURinfo[4]);
                pos44.setText(EURinfo[5]);
                EURinfo[6] = "4";
            } else if (name[1].equals("EUR")) {
                img3.setImage(new Image(EURinfo[0]));
                pos03.setText(EURinfo[1]);
                pos13.setText(EURinfo[2]);
                pos23.setText(String.format("%.2f", (Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100) + "%");
                pos33.setText(EURinfo[4]);
                pos43.setText(EURinfo[5]);
                EURinfo[6] = "3";
            } else if (name[2].equals("EUR")) {
                img2.setImage(new Image(EURinfo[0]));
                pos02.setText(EURinfo[1]);
                pos12.setText(EURinfo[2]);
                pos22.setText(String.format("%.2f", (Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100) + "%");
                pos32.setText(EURinfo[4]);
                pos42.setText(EURinfo[5]);
                EURinfo[6] = "2";
            } else if (name[3].equals("EUR")) {
                img1.setImage(new Image(EURinfo[0]));
                pos01.setText(EURinfo[1]);
                pos11.setText(EURinfo[2]);
                pos21.setText(String.format("%.2f", (Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100) + "%");
                pos31.setText(EURinfo[4]);
                pos41.setText(EURinfo[5]);
                EURinfo[6] = "1";
            } else {
                img0.setImage(new Image(EURinfo[0]));
                pos00.setText(EURinfo[1]);
                pos10.setText(EURinfo[2]);
                pos20.setText(String.format("%.2f", (Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100) + "%");
                pos30.setText(EURinfo[4]);
                pos40.setText(EURinfo[5]);
                EURinfo[6] = "0";
            }

            if (name[0].equals("USD")) {
                img4.setImage(new Image(USDinfo[0]));
                pos04.setText(USDinfo[1]);
                pos14.setText(USDinfo[2]);
                pos24.setText(String.format("%.2f", (Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100) + "%");
                pos34.setText(USDinfo[4]);
                pos44.setText(USDinfo[5]);
                USDinfo[6] = "4";
            } else if (name[1].equals("USD")) {
                img3.setImage(new Image(USDinfo[0]));
                pos03.setText(USDinfo[1]);
                pos13.setText(USDinfo[2]);
                pos23.setText(String.format("%.2f", (Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100) + "%");
                pos33.setText(USDinfo[4]);
                pos43.setText(USDinfo[5]);
                USDinfo[6] = "3";
            } else if (name[2].equals("USD")) {
                img2.setImage(new Image(USDinfo[0]));
                pos02.setText(USDinfo[1]);
                pos12.setText(USDinfo[2]);
                pos22.setText(String.format("%.2f", (Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100) + "%");
                pos32.setText(USDinfo[4]);
                pos42.setText(USDinfo[5]);
                USDinfo[6] = "2";
            } else if (name[3].equals("USD")) {
                img1.setImage(new Image(USDinfo[0]));
                pos01.setText(USDinfo[1]);
                pos11.setText(USDinfo[2]);
                pos21.setText(String.format("%.2f", (Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100) + "%");
                pos31.setText(USDinfo[4]);
                pos41.setText(USDinfo[5]);
                USDinfo[6] = "1";
            } else {
                img0.setImage(new Image(USDinfo[0]));
                pos00.setText(USDinfo[1]);
                pos10.setText(USDinfo[2]);
                pos20.setText(String.format("%.2f", (Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100) + "%");
                pos30.setText(USDinfo[4]);
                pos40.setText(USDinfo[5]);
                USDinfo[6] = "0";
            }

            if (name[0].equals("YEN")) {
                img4.setImage(new Image(YENinfo[0]));
                pos04.setText(YENinfo[1]);
                pos14.setText(YENinfo[2]);
                pos24.setText(String.format("%.2f", (Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100) + "%");
                pos34.setText(YENinfo[4]);
                pos44.setText(YENinfo[5]);
                YENinfo[6] = "4";
            } else if (name[1].equals("YEN")) {
                img3.setImage(new Image(YENinfo[0]));
                pos03.setText(YENinfo[1]);
                pos13.setText(YENinfo[2]);
                pos23.setText(String.format("%.2f", (Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100) + "%");
                pos33.setText(YENinfo[4]);
                pos43.setText(YENinfo[5]);
                YENinfo[6] = "3";
            } else if (name[2].equals("YEN")) {
                img2.setImage(new Image(YENinfo[0]));
                pos02.setText(YENinfo[1]);
                pos12.setText(YENinfo[2]);
                pos22.setText(String.format("%.2f", (Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100) + "%");
                pos32.setText(YENinfo[4]);
                pos42.setText(YENinfo[5]);
                YENinfo[6] = "2";
            } else if (name[3].equals("YEN")) {
                img1.setImage(new Image(YENinfo[0]));
                pos01.setText(YENinfo[1]);
                pos11.setText(YENinfo[2]);
                pos21.setText(String.format("%.2f", (Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100) + "%");
                pos31.setText(YENinfo[4]);
                pos41.setText(YENinfo[5]);
                YENinfo[6] = "1";
            } else {
                img0.setImage(new Image(YENinfo[0]));
                pos00.setText(YENinfo[1]);
                pos10.setText(YENinfo[2]);
                pos20.setText(String.format("%.2f", (Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100) + "%");
                pos30.setText(YENinfo[4]);
                pos40.setText(YENinfo[5]);
                YENinfo[6] = "0";
            }

            if (name[0].equals("GBP")) {
                img4.setImage(new Image(GBPinfo[0]));
                pos04.setText(GBPinfo[1]);
                pos14.setText(GBPinfo[2]);
                pos24.setText(String.format("%.2f", (Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100) + "%");
                pos34.setText(GBPinfo[4]);
                pos44.setText(GBPinfo[5]);
                GBPinfo[6] = "4";
            } else if (name[1].equals("GBP")) {
                img3.setImage(new Image(GBPinfo[0]));
                pos03.setText(GBPinfo[1]);
                pos13.setText(GBPinfo[2]);
                pos23.setText(String.format("%.2f", (Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100) + "%");
                pos33.setText(GBPinfo[4]);
                pos43.setText(GBPinfo[5]);
                GBPinfo[6] = "3";
            } else if (name[2].equals("GBP")) {
                img2.setImage(new Image(GBPinfo[0]));
                pos02.setText(GBPinfo[1]);
                pos12.setText(GBPinfo[2]);
                pos22.setText(String.format("%.2f", (Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100) + "%");
                pos32.setText(GBPinfo[4]);
                pos42.setText(GBPinfo[5]);
                GBPinfo[6] = "2";
            } else if (name[3].equals("GBP")) {
                img1.setImage(new Image(GBPinfo[0]));
                pos01.setText(GBPinfo[1]);
                pos11.setText(GBPinfo[2]);
                pos21.setText(String.format("%.2f", (Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100) + "%");
                pos31.setText(GBPinfo[4]);
                pos41.setText(GBPinfo[5]);
                GBPinfo[6] = "1";
            } else {
                img0.setImage(new Image(GBPinfo[0]));
                pos00.setText(GBPinfo[1]);
                pos10.setText(GBPinfo[2]);
                pos20.setText(String.format("%.2f", (Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100) + "%");
                pos30.setText(GBPinfo[4]);
                pos40.setText(GBPinfo[5]);
                GBPinfo[6] = "0";
            }
        }
    }
    public void ClickOnMaxPrice (ActionEvent event){
        name [0] = "TOMAN";
        name [1] = "USD";
        name [2] = "YEN";
        name [3] = "EUR";
        name [4] = "GBP";
        maxPrice[0] = Double.parseDouble(TOMANinfo[4]);
        maxPrice[1] = Double.parseDouble(USDinfo[4]);
        maxPrice[2] = Double.parseDouble(YENinfo[4]);
        maxPrice[3] = Double.parseDouble(EURinfo[4]);
        maxPrice[4] = Double.parseDouble(GBPinfo[4]);

        for (int i = 0; i < 4; i++) {
            for (int j = i+1; j < 5; j++) {
                if(maxPrice[j]>maxPrice[i]){

                    String tempStr = name[i];
                    name[i] = name [j];
                    name[j] = tempStr;

                    Double tempDbl = maxPrice[i];
                    maxPrice[i] = maxPrice [j];
                    maxPrice[j] = tempDbl;
                }
            }
        }
        if(orderMaxPrice){
            orderMaxPrice = false;
            // aval bayad add bozorg bashe
            if(name[0].equals("TOMAN")){
                img0.setImage(new Image(TOMANinfo[0]));
                pos00.setText(TOMANinfo[1]);
                pos10.setText(TOMANinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos30.setText(TOMANinfo[4]);
                pos40.setText(TOMANinfo[5]);
                TOMANinfo[6] = "0";
            }
            else if (name[1].equals("TOMAN")) {
                img1.setImage(new Image(TOMANinfo[0]));
                pos01.setText(TOMANinfo[1]);
                pos11.setText(TOMANinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos31.setText(TOMANinfo[4]);
                pos41.setText(TOMANinfo[5]);
                TOMANinfo[6] = "1";
            }
            else if (name[2].equals("TOMAN")){
                img2.setImage(new Image(TOMANinfo[0]));
                pos02.setText(TOMANinfo[1]);
                pos12.setText(TOMANinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos32.setText(TOMANinfo[4]);
                pos42.setText(TOMANinfo[5]);
                TOMANinfo[6]= "2";
            }
            else if(name[3].equals("TOMAN")){
                img3.setImage(new Image(TOMANinfo[0]));
                pos03.setText(TOMANinfo[1]);
                pos13.setText(TOMANinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos33.setText(TOMANinfo[4]);
                pos43.setText(TOMANinfo[5]);
                TOMANinfo[6]= "3";
            }
            else{
                img4.setImage(new Image(TOMANinfo[0]));
                pos04.setText(TOMANinfo[1]);
                pos14.setText(TOMANinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos34.setText(TOMANinfo[4]);
                pos44.setText(TOMANinfo[5]);
                TOMANinfo[6]= "4";
            }


            if(name[0].equals("EUR")){
                img0.setImage(new Image(EURinfo[0]));
                pos00.setText(EURinfo[1]);
                pos10.setText(EURinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos30.setText(EURinfo[4]);
                pos40.setText(EURinfo[5]);
                EURinfo[6] = "0";
            }
            else if (name[1].equals("EUR")) {
                img1.setImage(new Image(EURinfo[0]));
                pos01.setText(EURinfo[1]);
                pos11.setText(EURinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos31.setText(EURinfo[4]);
                pos41.setText(EURinfo[5]);
                EURinfo[6] = "1";
            }
            else if (name[2].equals("EUR")){
                img2.setImage(new Image(EURinfo[0]));
                pos02.setText(EURinfo[1]);
                pos12.setText(EURinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos32.setText(EURinfo[4]);
                pos42.setText(EURinfo[5]);
                EURinfo[6]= "2";
            }
            else if(name[3].equals("EUR")){
                img3.setImage(new Image(EURinfo[0]));
                pos03.setText(EURinfo[1]);
                pos13.setText(EURinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos33.setText(EURinfo[4]);
                pos43.setText(EURinfo[5]);
                EURinfo[6]= "3";
            }
            else{
                img4.setImage(new Image(EURinfo[0]));
                pos04.setText(EURinfo[1]);
                pos14.setText(EURinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos34.setText(EURinfo[4]);
                pos44.setText(EURinfo[5]);
                EURinfo[6]= "4";
            }

            if(name[0].equals("USD")){
                img0.setImage(new Image(USDinfo[0]));
                pos00.setText(USDinfo[1]);
                pos10.setText(USDinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos30.setText(USDinfo[4]);
                pos40.setText(USDinfo[5]);
                USDinfo[6] = "0";
            }
            else if (name[1].equals("USD")) {
                img1.setImage(new Image(USDinfo[0]));
                pos01.setText(USDinfo[1]);
                pos11.setText(USDinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos31.setText(USDinfo[4]);
                pos41.setText(USDinfo[5]);
                USDinfo[6] = "1";
            }
            else if (name[2].equals("USD")){
                img2.setImage(new Image(USDinfo[0]));
                pos02.setText(USDinfo[1]);
                pos12.setText(USDinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos32.setText(USDinfo[4]);
                pos42.setText(USDinfo[5]);
                USDinfo[6]= "2";
            }
            else if(name[3].equals("USD")){
                img3.setImage(new Image(USDinfo[0]));
                pos03.setText(USDinfo[1]);
                pos13.setText(USDinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos33.setText(USDinfo[4]);
                pos43.setText(USDinfo[5]);
                USDinfo[6]= "3";
            }
            else{
                img4.setImage(new Image(USDinfo[0]));
                pos04.setText(USDinfo[1]);
                pos14.setText(USDinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos34.setText(USDinfo[4]);
                pos44.setText(USDinfo[5]);
                USDinfo[6]= "4";
            }

            if(name[0].equals("YEN")){
                img0.setImage(new Image(YENinfo[0]));
                pos00.setText(YENinfo[1]);
                pos10.setText(YENinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos30.setText(YENinfo[4]);
                pos40.setText(YENinfo[5]);
                YENinfo[6] = "0";
            }
            else if (name[1].equals("YEN")) {
                img1.setImage(new Image(YENinfo[0]));
                pos01.setText(YENinfo[1]);
                pos11.setText(YENinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos31.setText(YENinfo[4]);
                pos41.setText(YENinfo[5]);
                YENinfo[6] = "1";
            }
            else if (name[2].equals("YEN")){
                img2.setImage(new Image(YENinfo[0]));
                pos02.setText(YENinfo[1]);
                pos12.setText(YENinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos32.setText(YENinfo[4]);
                pos42.setText(YENinfo[5]);
                YENinfo[6]= "2";
            }
            else if(name[3].equals("YEN")){
                img3.setImage(new Image(YENinfo[0]));
                pos03.setText(YENinfo[1]);
                pos13.setText(YENinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos33.setText(YENinfo[4]);
                pos43.setText(YENinfo[5]);
                YENinfo[6]= "3";
            }
            else{
                img4.setImage(new Image(YENinfo[0]));
                pos04.setText(YENinfo[1]);
                pos14.setText(YENinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos34.setText(YENinfo[4]);
                pos44.setText(YENinfo[5]);
                YENinfo[6]= "4";
            }

            if(name[0].equals("GBP")){
                img0.setImage(new Image(GBPinfo[0]));
                pos00.setText(GBPinfo[1]);
                pos10.setText(GBPinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos30.setText(GBPinfo[4]);
                pos40.setText(GBPinfo[5]);
                GBPinfo[6] = "0";
            }
            else if (name[1].equals("GBP")) {
                img1.setImage(new Image(GBPinfo[0]));
                pos01.setText(GBPinfo[1]);
                pos11.setText(GBPinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos31.setText(GBPinfo[4]);
                pos41.setText(GBPinfo[5]);
                GBPinfo[6] = "1";
            }
            else if (name[2].equals("GBP")){
                img2.setImage(new Image(GBPinfo[0]));
                pos02.setText(GBPinfo[1]);
                pos12.setText(GBPinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos32.setText(GBPinfo[4]);
                pos42.setText(GBPinfo[5]);
                GBPinfo[6]= "2";
            }
            else if(name[3].equals("GBP")){
                img3.setImage(new Image(GBPinfo[0]));
                pos03.setText(GBPinfo[1]);
                pos13.setText(GBPinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos33.setText(GBPinfo[4]);
                pos43.setText(GBPinfo[5]);
                GBPinfo[6]= "3";
            }
            else{
                img4.setImage(new Image(GBPinfo[0]));
                pos04.setText(GBPinfo[1]);
                pos14.setText(GBPinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos34.setText(GBPinfo[4]);
                pos44.setText(GBPinfo[5]);
                GBPinfo[6]= "4";
            }


        }
        else {
            orderMaxPrice = true;
            if(name[0].equals("TOMAN")){
                img4.setImage(new Image(TOMANinfo[0]));
                pos04.setText(TOMANinfo[1]);
                pos14.setText(TOMANinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos34.setText(TOMANinfo[4]);
                pos44.setText(TOMANinfo[5]);
                TOMANinfo[6] = "4";
            }
            else if (name[1].equals("TOMAN")) {
                img3.setImage(new Image(TOMANinfo[0]));
                pos03.setText(TOMANinfo[1]);
                pos13.setText(TOMANinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos33.setText(TOMANinfo[4]);
                pos43.setText(TOMANinfo[5]);
                TOMANinfo[6] = "3";
            }
            else if (name[2].equals("TOMAN")){
                img2.setImage(new Image(TOMANinfo[0]));
                pos02.setText(TOMANinfo[1]);
                pos12.setText(TOMANinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos32.setText(TOMANinfo[4]);
                pos42.setText(TOMANinfo[5]);
                TOMANinfo[6]= "2";
            }
            else if(name[3].equals("TOMAN")){
                img1.setImage(new Image(TOMANinfo[0]));
                pos01.setText(TOMANinfo[1]);
                pos11.setText(TOMANinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos31.setText(TOMANinfo[4]);
                pos41.setText(TOMANinfo[5]);
                TOMANinfo[6]= "1";
            }
            else{
                img0.setImage(new Image(TOMANinfo[0]));
                pos00.setText(TOMANinfo[1]);
                pos10.setText(TOMANinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos30.setText(TOMANinfo[4]);
                pos40.setText(TOMANinfo[5]);
                TOMANinfo[6]= "0";
            }


            if(name[0].equals("EUR")){
                img4.setImage(new Image(EURinfo[0]));
                pos04.setText(EURinfo[1]);
                pos14.setText(EURinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos34.setText(EURinfo[4]);
                pos44.setText(EURinfo[5]);
                EURinfo[6] = "4";
            }
            else if (name[1].equals("EUR")) {
                img3.setImage(new Image(EURinfo[0]));
                pos03.setText(EURinfo[1]);
                pos13.setText(EURinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos33.setText(EURinfo[4]);
                pos43.setText(EURinfo[5]);
                EURinfo[6] = "3";
            }
            else if (name[2].equals("EUR")){
                img2.setImage(new Image(EURinfo[0]));
                pos02.setText(EURinfo[1]);
                pos12.setText(EURinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos32.setText(EURinfo[4]);
                pos42.setText(EURinfo[5]);
                EURinfo[6]= "2";
            }
            else if(name[3].equals("EUR")){
                img1.setImage(new Image(EURinfo[0]));
                pos01.setText(EURinfo[1]);
                pos11.setText(EURinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos31.setText(EURinfo[4]);
                pos41.setText(EURinfo[5]);
                EURinfo[6]= "1";
            }
            else{
                img0.setImage(new Image(EURinfo[0]));
                pos00.setText(EURinfo[1]);
                pos10.setText(EURinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos30.setText(EURinfo[4]);
                pos40.setText(EURinfo[5]);
                EURinfo[6]= "0";
            }

            if(name[0].equals("USD")){
                img4.setImage(new Image(USDinfo[0]));
                pos04.setText(USDinfo[1]);
                pos14.setText(USDinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos34.setText(USDinfo[4]);
                pos44.setText(USDinfo[5]);
                USDinfo[6] = "4";
            }
            else if (name[1].equals("USD")) {
                img3.setImage(new Image(USDinfo[0]));
                pos03.setText(USDinfo[1]);
                pos13.setText(USDinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos33.setText(USDinfo[4]);
                pos43.setText(USDinfo[5]);
                USDinfo[6] = "3";
            }
            else if (name[2].equals("USD")){
                img2.setImage(new Image(USDinfo[0]));
                pos02.setText(USDinfo[1]);
                pos12.setText(USDinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos32.setText(USDinfo[4]);
                pos42.setText(USDinfo[5]);
                USDinfo[6]= "2";
            }
            else if(name[3].equals("USD")){
                img1.setImage(new Image(USDinfo[0]));
                pos01.setText(USDinfo[1]);
                pos11.setText(USDinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos31.setText(USDinfo[4]);
                pos41.setText(USDinfo[5]);
                USDinfo[6]= "1";
            }
            else{
                img0.setImage(new Image(USDinfo[0]));
                pos00.setText(USDinfo[1]);
                pos10.setText(USDinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos30.setText(USDinfo[4]);
                pos40.setText(USDinfo[5]);
                USDinfo[6]= "0";
            }

            if(name[0].equals("YEN")){
                img4.setImage(new Image(YENinfo[0]));
                pos04.setText(YENinfo[1]);
                pos14.setText(YENinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos34.setText(YENinfo[4]);
                pos44.setText(YENinfo[5]);
                YENinfo[6] = "4";
            }
            else if (name[1].equals("YEN")) {
                img3.setImage(new Image(YENinfo[0]));
                pos03.setText(YENinfo[1]);
                pos13.setText(YENinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos33.setText(YENinfo[4]);
                pos43.setText(YENinfo[5]);
                YENinfo[6] = "3";
            }
            else if (name[2].equals("YEN")){
                img2.setImage(new Image(YENinfo[0]));
                pos02.setText(YENinfo[1]);
                pos12.setText(YENinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos32.setText(YENinfo[4]);
                pos42.setText(YENinfo[5]);
                YENinfo[6]= "2";
            }
            else if(name[3].equals("YEN")){
                img1.setImage(new Image(YENinfo[0]));
                pos01.setText(YENinfo[1]);
                pos11.setText(YENinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos31.setText(YENinfo[4]);
                pos41.setText(YENinfo[5]);
                YENinfo[6]= "1";
            }
            else{
                img0.setImage(new Image(YENinfo[0]));
                pos00.setText(YENinfo[1]);
                pos10.setText(YENinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos30.setText(YENinfo[4]);
                pos40.setText(YENinfo[5]);
                YENinfo[6]= "0";
            }

            if(name[0].equals("GBP")){
                img4.setImage(new Image(GBPinfo[0]));
                pos04.setText(GBPinfo[1]);
                pos14.setText(GBPinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos34.setText(GBPinfo[4]);
                pos44.setText(GBPinfo[5]);
                GBPinfo[6] = "4";
            }
            else if (name[1].equals("GBP")) {
                img3.setImage(new Image(GBPinfo[0]));
                pos03.setText(GBPinfo[1]);
                pos13.setText(GBPinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos33.setText(GBPinfo[4]);
                pos43.setText(GBPinfo[5]);
                GBPinfo[6] = "3";
            }
            else if (name[2].equals("GBP")){
                img2.setImage(new Image(GBPinfo[0]));
                pos02.setText(GBPinfo[1]);
                pos12.setText(GBPinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos32.setText(GBPinfo[4]);
                pos42.setText(GBPinfo[5]);
                GBPinfo[6]= "2";
            }
            else if(name[3].equals("GBP")){
                img1.setImage(new Image(GBPinfo[0]));
                pos01.setText(GBPinfo[1]);
                pos11.setText(GBPinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos31.setText(GBPinfo[4]);
                pos41.setText(GBPinfo[5]);
                GBPinfo[6]= "1";
            }
            else{
                img0.setImage(new Image(GBPinfo[0]));
                pos00.setText(GBPinfo[1]);
                pos10.setText(GBPinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos30.setText(GBPinfo[4]);
                pos40.setText(GBPinfo[5]);
                GBPinfo[6]= "0";
            }
        }
    }
    public void ClickOnMinPrice (ActionEvent event){
        name [0] = "TOMAN";
        name [1] = "USD";
        name [2] = "YEN";
        name [3] = "EUR";
        name [4] = "GBP";
        minPrice[0] = Double.parseDouble(TOMANinfo[5]);
        minPrice[1] = Double.parseDouble(USDinfo[5]);
        minPrice[2] = Double.parseDouble(YENinfo[5]);
        minPrice[3] = Double.parseDouble(EURinfo[5]);
        minPrice[4] = Double.parseDouble(GBPinfo[5]);

        for (int i = 0; i < 4; i++) {
            for (int j = i+1; j < 5; j++) {
                if(minPrice[j]>minPrice[i]){

                    String tempStr = name[i];
                    name[i] = name [j];
                    name[j] = tempStr;

                    Double tempDbl = minPrice[i];
                    minPrice[i] = minPrice [j];
                    minPrice[j] = tempDbl;
                }
            }
        }
        if(orderMinPrice){
            orderMinPrice = false;
            // aval bayad add bozorg bashe
            if(name[0].equals("TOMAN")){
                img0.setImage(new Image(TOMANinfo[0]));
                pos00.setText(TOMANinfo[1]);
                pos10.setText(TOMANinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos30.setText(TOMANinfo[4]);
                pos40.setText(TOMANinfo[5]);
                TOMANinfo[6] = "0";
            }
            else if (name[1].equals("TOMAN")) {
                img1.setImage(new Image(TOMANinfo[0]));
                pos01.setText(TOMANinfo[1]);
                pos11.setText(TOMANinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos31.setText(TOMANinfo[4]);
                pos41.setText(TOMANinfo[5]);
                TOMANinfo[6] = "1";
            }
            else if (name[2].equals("TOMAN")){
                img2.setImage(new Image(TOMANinfo[0]));
                pos02.setText(TOMANinfo[1]);
                pos12.setText(TOMANinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos32.setText(TOMANinfo[4]);
                pos42.setText(TOMANinfo[5]);
                TOMANinfo[6]= "2";
            }
            else if(name[3].equals("TOMAN")){
                img3.setImage(new Image(TOMANinfo[0]));
                pos03.setText(TOMANinfo[1]);
                pos13.setText(TOMANinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos33.setText(TOMANinfo[4]);
                pos43.setText(TOMANinfo[5]);
                TOMANinfo[6]= "3";
            }
            else{
                img4.setImage(new Image(TOMANinfo[0]));
                pos04.setText(TOMANinfo[1]);
                pos14.setText(TOMANinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos34.setText(TOMANinfo[4]);
                pos44.setText(TOMANinfo[5]);
                TOMANinfo[6]= "4";
            }


            if(name[0].equals("EUR")){
                img0.setImage(new Image(EURinfo[0]));
                pos00.setText(EURinfo[1]);
                pos10.setText(EURinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos30.setText(EURinfo[4]);
                pos40.setText(EURinfo[5]);
                EURinfo[6] = "0";
            }
            else if (name[1].equals("EUR")) {
                img1.setImage(new Image(EURinfo[0]));
                pos01.setText(EURinfo[1]);
                pos11.setText(EURinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos31.setText(EURinfo[4]);
                pos41.setText(EURinfo[5]);
                EURinfo[6] = "1";
            }
            else if (name[2].equals("EUR")){
                img2.setImage(new Image(EURinfo[0]));
                pos02.setText(EURinfo[1]);
                pos12.setText(EURinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos32.setText(EURinfo[4]);
                pos42.setText(EURinfo[5]);
                EURinfo[6]= "2";
            }
            else if(name[3].equals("EUR")){
                img3.setImage(new Image(EURinfo[0]));
                pos03.setText(EURinfo[1]);
                pos13.setText(EURinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos33.setText(EURinfo[4]);
                pos43.setText(EURinfo[5]);
                EURinfo[6]= "3";
            }
            else{
                img4.setImage(new Image(EURinfo[0]));
                pos04.setText(EURinfo[1]);
                pos14.setText(EURinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos34.setText(EURinfo[4]);
                pos44.setText(EURinfo[5]);
                EURinfo[6]= "4";
            }

            if(name[0].equals("USD")){
                img0.setImage(new Image(USDinfo[0]));
                pos00.setText(USDinfo[1]);
                pos10.setText(USDinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos30.setText(USDinfo[4]);
                pos40.setText(USDinfo[5]);
                USDinfo[6] = "0";
            }
            else if (name[1].equals("USD")) {
                img1.setImage(new Image(USDinfo[0]));
                pos01.setText(USDinfo[1]);
                pos11.setText(USDinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos31.setText(USDinfo[4]);
                pos41.setText(USDinfo[5]);
                USDinfo[6] = "1";
            }
            else if (name[2].equals("USD")){
                img2.setImage(new Image(USDinfo[0]));
                pos02.setText(USDinfo[1]);
                pos12.setText(USDinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos32.setText(USDinfo[4]);
                pos42.setText(USDinfo[5]);
                USDinfo[6]= "2";
            }
            else if(name[3].equals("USD")){
                img3.setImage(new Image(USDinfo[0]));
                pos03.setText(USDinfo[1]);
                pos13.setText(USDinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos33.setText(USDinfo[4]);
                pos43.setText(USDinfo[5]);
                USDinfo[6]= "3";
            }
            else{
                img4.setImage(new Image(USDinfo[0]));
                pos04.setText(USDinfo[1]);
                pos14.setText(USDinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos34.setText(USDinfo[4]);
                pos44.setText(USDinfo[5]);
                USDinfo[6]= "4";
            }

            if(name[0].equals("YEN")){
                img0.setImage(new Image(YENinfo[0]));
                pos00.setText(YENinfo[1]);
                pos10.setText(YENinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos30.setText(YENinfo[4]);
                pos40.setText(YENinfo[5]);
                YENinfo[6] = "0";
            }
            else if (name[1].equals("YEN")) {
                img1.setImage(new Image(YENinfo[0]));
                pos01.setText(YENinfo[1]);
                pos11.setText(YENinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos31.setText(YENinfo[4]);
                pos41.setText(YENinfo[5]);
                YENinfo[6] = "1";
            }
            else if (name[2].equals("YEN")){
                img2.setImage(new Image(YENinfo[0]));
                pos02.setText(YENinfo[1]);
                pos12.setText(YENinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos32.setText(YENinfo[4]);
                pos42.setText(YENinfo[5]);
                YENinfo[6]= "2";
            }
            else if(name[3].equals("YEN")){
                img3.setImage(new Image(YENinfo[0]));
                pos03.setText(YENinfo[1]);
                pos13.setText(YENinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos33.setText(YENinfo[4]);
                pos43.setText(YENinfo[5]);
                YENinfo[6]= "3";
            }
            else{
                img4.setImage(new Image(YENinfo[0]));
                pos04.setText(YENinfo[1]);
                pos14.setText(YENinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos34.setText(YENinfo[4]);
                pos44.setText(YENinfo[5]);
                YENinfo[6]= "4";
            }

            if(name[0].equals("GBP")){
                img0.setImage(new Image(GBPinfo[0]));
                pos00.setText(GBPinfo[1]);
                pos10.setText(GBPinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos30.setText(GBPinfo[4]);
                pos40.setText(GBPinfo[5]);
                GBPinfo[6] = "0";
            }
            else if (name[1].equals("GBP")) {
                img1.setImage(new Image(GBPinfo[0]));
                pos01.setText(GBPinfo[1]);
                pos11.setText(GBPinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos31.setText(GBPinfo[4]);
                pos41.setText(GBPinfo[5]);
                GBPinfo[6] = "1";
            }
            else if (name[2].equals("GBP")){
                img2.setImage(new Image(GBPinfo[0]));
                pos02.setText(GBPinfo[1]);
                pos12.setText(GBPinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos32.setText(GBPinfo[4]);
                pos42.setText(GBPinfo[5]);
                GBPinfo[6]= "2";
            }
            else if(name[3].equals("GBP")){
                img3.setImage(new Image(GBPinfo[0]));
                pos03.setText(GBPinfo[1]);
                pos13.setText(GBPinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos33.setText(GBPinfo[4]);
                pos43.setText(GBPinfo[5]);
                GBPinfo[6]= "3";
            }
            else{
                img4.setImage(new Image(GBPinfo[0]));
                pos04.setText(GBPinfo[1]);
                pos14.setText(GBPinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos34.setText(GBPinfo[4]);
                pos44.setText(GBPinfo[5]);
                GBPinfo[6]= "4";
            }


        }
        else {
            orderMinPrice = true;
            if(name[0].equals("TOMAN")){
                img4.setImage(new Image(TOMANinfo[0]));
                pos04.setText(TOMANinfo[1]);
                pos14.setText(TOMANinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos34.setText(TOMANinfo[4]);
                pos44.setText(TOMANinfo[5]);
                TOMANinfo[6] = "4";
            }
            else if (name[1].equals("TOMAN")) {
                img3.setImage(new Image(TOMANinfo[0]));
                pos03.setText(TOMANinfo[1]);
                pos13.setText(TOMANinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos33.setText(TOMANinfo[4]);
                pos43.setText(TOMANinfo[5]);
                TOMANinfo[6] = "3";
            }
            else if (name[2].equals("TOMAN")){
                img2.setImage(new Image(TOMANinfo[0]));
                pos02.setText(TOMANinfo[1]);
                pos12.setText(TOMANinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos32.setText(TOMANinfo[4]);
                pos42.setText(TOMANinfo[5]);
                TOMANinfo[6]= "2";
            }
            else if(name[3].equals("TOMAN")){
                img1.setImage(new Image(TOMANinfo[0]));
                pos01.setText(TOMANinfo[1]);
                pos11.setText(TOMANinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos31.setText(TOMANinfo[4]);
                pos41.setText(TOMANinfo[5]);
                TOMANinfo[6]= "1";
            }
            else{
                img0.setImage(new Image(TOMANinfo[0]));
                pos00.setText(TOMANinfo[1]);
                pos10.setText(TOMANinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3])) / (Double.parseDouble(TOMANinfo[3])) * 100 ) +"%");
                pos30.setText(TOMANinfo[4]);
                pos40.setText(TOMANinfo[5]);
                TOMANinfo[6]= "0";
            }


            if(name[0].equals("EUR")){
                img4.setImage(new Image(EURinfo[0]));
                pos04.setText(EURinfo[1]);
                pos14.setText(EURinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos34.setText(EURinfo[4]);
                pos44.setText(EURinfo[5]);
                EURinfo[6] = "4";
            }
            else if (name[1].equals("EUR")) {
                img3.setImage(new Image(EURinfo[0]));
                pos03.setText(EURinfo[1]);
                pos13.setText(EURinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos33.setText(EURinfo[4]);
                pos43.setText(EURinfo[5]);
                EURinfo[6] = "3";
            }
            else if (name[2].equals("EUR")){
                img2.setImage(new Image(EURinfo[0]));
                pos02.setText(EURinfo[1]);
                pos12.setText(EURinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos32.setText(EURinfo[4]);
                pos42.setText(EURinfo[5]);
                EURinfo[6]= "2";
            }
            else if(name[3].equals("EUR")){
                img1.setImage(new Image(EURinfo[0]));
                pos01.setText(EURinfo[1]);
                pos11.setText(EURinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos31.setText(EURinfo[4]);
                pos41.setText(EURinfo[5]);
                EURinfo[6]= "1";
            }
            else{
                img0.setImage(new Image(EURinfo[0]));
                pos00.setText(EURinfo[1]);
                pos10.setText(EURinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3])) / (Double.parseDouble(EURinfo[3])) * 100 ) +"%");
                pos30.setText(EURinfo[4]);
                pos40.setText(EURinfo[5]);
                EURinfo[6]= "0";
            }

            if(name[0].equals("USD")){
                img4.setImage(new Image(USDinfo[0]));
                pos04.setText(USDinfo[1]);
                pos14.setText(USDinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos34.setText(USDinfo[4]);
                pos44.setText(USDinfo[5]);
                USDinfo[6] = "4";
            }
            else if (name[1].equals("USD")) {
                img3.setImage(new Image(USDinfo[0]));
                pos03.setText(USDinfo[1]);
                pos13.setText(USDinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos33.setText(USDinfo[4]);
                pos43.setText(USDinfo[5]);
                USDinfo[6] = "3";
            }
            else if (name[2].equals("USD")){
                img2.setImage(new Image(USDinfo[0]));
                pos02.setText(USDinfo[1]);
                pos12.setText(USDinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos32.setText(USDinfo[4]);
                pos42.setText(USDinfo[5]);
                USDinfo[6]= "2";
            }
            else if(name[3].equals("USD")){
                img1.setImage(new Image(USDinfo[0]));
                pos01.setText(USDinfo[1]);
                pos11.setText(USDinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos31.setText(USDinfo[4]);
                pos41.setText(USDinfo[5]);
                USDinfo[6]= "1";
            }
            else{
                img0.setImage(new Image(USDinfo[0]));
                pos00.setText(USDinfo[1]);
                pos10.setText(USDinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3])) / (Double.parseDouble(USDinfo[3])) * 100 ) +"%");
                pos30.setText(USDinfo[4]);
                pos40.setText(USDinfo[5]);
                USDinfo[6]= "0";
            }

            if(name[0].equals("YEN")){
                img4.setImage(new Image(YENinfo[0]));
                pos04.setText(YENinfo[1]);
                pos14.setText(YENinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos34.setText(YENinfo[4]);
                pos44.setText(YENinfo[5]);
                YENinfo[6] = "4";
            }
            else if (name[1].equals("YEN")) {
                img3.setImage(new Image(YENinfo[0]));
                pos03.setText(YENinfo[1]);
                pos13.setText(YENinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos33.setText(YENinfo[4]);
                pos43.setText(YENinfo[5]);
                YENinfo[6] = "3";
            }
            else if (name[2].equals("YEN")){
                img2.setImage(new Image(YENinfo[0]));
                pos02.setText(YENinfo[1]);
                pos12.setText(YENinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos32.setText(YENinfo[4]);
                pos42.setText(YENinfo[5]);
                YENinfo[6]= "2";
            }
            else if(name[3].equals("YEN")){
                img1.setImage(new Image(YENinfo[0]));
                pos01.setText(YENinfo[1]);
                pos11.setText(YENinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos31.setText(YENinfo[4]);
                pos41.setText(YENinfo[5]);
                YENinfo[6]= "1";
            }
            else{
                img0.setImage(new Image(YENinfo[0]));
                pos00.setText(YENinfo[1]);
                pos10.setText(YENinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3])) / (Double.parseDouble(YENinfo[3])) * 100 ) +"%");
                pos30.setText(YENinfo[4]);
                pos40.setText(YENinfo[5]);
                YENinfo[6]= "0";
            }

            if(name[0].equals("GBP")){
                img4.setImage(new Image(GBPinfo[0]));
                pos04.setText(GBPinfo[1]);
                pos14.setText(GBPinfo[2]);
                pos24.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos34.setText(GBPinfo[4]);
                pos44.setText(GBPinfo[5]);
                GBPinfo[6] = "4";
            }
            else if (name[1].equals("GBP")) {
                img3.setImage(new Image(GBPinfo[0]));
                pos03.setText(GBPinfo[1]);
                pos13.setText(GBPinfo[2]);
                pos23.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos33.setText(GBPinfo[4]);
                pos43.setText(GBPinfo[5]);
                GBPinfo[6] = "3";
            }
            else if (name[2].equals("GBP")){
                img2.setImage(new Image(GBPinfo[0]));
                pos02.setText(GBPinfo[1]);
                pos12.setText(GBPinfo[2]);
                pos22.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos32.setText(GBPinfo[4]);
                pos42.setText(GBPinfo[5]);
                GBPinfo[6]= "2";
            }
            else if(name[3].equals("GBP")){
                img1.setImage(new Image(GBPinfo[0]));
                pos01.setText(GBPinfo[1]);
                pos11.setText(GBPinfo[2]);
                pos21.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos31.setText(GBPinfo[4]);
                pos41.setText(GBPinfo[5]);
                GBPinfo[6]= "1";
            }
            else{
                img0.setImage(new Image(GBPinfo[0]));
                pos00.setText(GBPinfo[1]);
                pos10.setText(GBPinfo[2]);
                pos20.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3])) / (Double.parseDouble(GBPinfo[3])) * 100 ) +"%");
                pos30.setText(GBPinfo[4]);
                pos40.setText(GBPinfo[5]);
                GBPinfo[6]= "0";
            }
        }
    }
    public void ClickOn0 (MouseEvent event) throws IOException {
        String name = pos00.getText();
        if(name.equals("TOMAN")){
            Main m = new Main();
            m.openNewWindow("TOMAN","toman");
        }
        else if (name.equals("USD")){
            Main m = new Main();
            m.openNewWindow("USD","usd");
        }
        else if (name.equals("GBP")){
            Main m = new Main();
            m.openNewWindow("GBP","gbp");
        }
        else if (name.equals("YEN")){
            Main m = new Main();
            m.openNewWindow("YEN","yen");
        }
        else {
            Main m = new Main();
            m.openNewWindow("EUR","eur");
        }
    }
    public void ClickOn1 (MouseEvent event) throws IOException {
        String name = pos01.getText();
        if(name.equals("TOMAN")){
            Main m = new Main();
            m.openNewWindow("TOMAN","toman");
        }
        else if (name.equals("USD")){
            Main m = new Main();
            m.openNewWindow("USD","usd");
        }
        else if (name.equals("GBP")){
            Main m = new Main();
            m.openNewWindow("GBP","gbp");
        }
        else if (name.equals("YEN")){
            Main m = new Main();
            m.openNewWindow("YEN","yen");
        }
        else {
            Main m = new Main();
            m.openNewWindow("EUR","eur");
        }
    }
    public void ClickOn2 (MouseEvent event) throws IOException {
        String name = pos02.getText();
        if(name.equals("TOMAN")){
            Main m = new Main();
            m.openNewWindow("TOMAN","toman");
        }
        else if (name.equals("USD")){
            Main m = new Main();
            m.openNewWindow("USD","usd");
        }
        else if (name.equals("GBP")){
            Main m = new Main();
            m.openNewWindow("GBP","gbp");
        }
        else if (name.equals("YEN")){
            Main m = new Main();
            m.openNewWindow("YEN","yen");
        }
        else {
            Main m = new Main();
            m.openNewWindow("EUR","eur");
        }
    }
    public void ClickOn3 (MouseEvent event) throws IOException {
        String name = pos03.getText();
        if(name.equals("TOMAN")){
            Main m = new Main();
            m.openNewWindow("TOMAN","toman");
        }
        else if (name.equals("USD")){
            Main m = new Main();
            m.openNewWindow("USD","usd");
        }
        else if (name.equals("GBP")){
            Main m = new Main();
            m.openNewWindow("GBP","gbp");
        }
        else if (name.equals("YEN")){
            Main m = new Main();
            m.openNewWindow("YEN","yen");
        }
        else {
            Main m = new Main();
            m.openNewWindow("EUR","eur");
        }
    }
    public void ClickOn4 (MouseEvent event) throws IOException {
        String name = pos04.getText();
        if(name.equals("TOMAN")){
            Main m = new Main();
            m.openNewWindow("TOMAN","toman");
        }
        else if (name.equals("USD")){
            Main m = new Main();
            m.openNewWindow("USD","usd");
        }
        else if (name.equals("GBP")){
            Main m = new Main();
            m.openNewWindow("GBP","gbp");
        }
        else if (name.equals("YEN")){
            Main m = new Main();
            m.openNewWindow("YEN","yen");
        }
        else {
            Main m = new Main();
            m.openNewWindow("EUR","eur");
        }
    }
}
