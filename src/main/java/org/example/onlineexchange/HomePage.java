package org.example.onlineexchange;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.util.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class HomePage implements Initializable {

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



    //  0->photo path   1->currency name   2->price   3->last price   4->max price   5->min price  6->position
    public String[] USDinfo = new String[7];
    public String[] EURinfo = new String[7];
    public String[] TOMANinfo = new String[7];
    public String[] YENinfo = new String[7];
    public String[] GBPinfo = new String[7];

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showTime();
        Reminder(60);
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



        readCVS();
        setLabelsFirst();

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
        Main m =  new Main();
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

        pos20.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3]))  /  (Double.parseDouble(USDinfo[3]))  *  100 ) +"%");
        pos21.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3]))  /  (Double.parseDouble(EURinfo[3]))  * 100 ) +"%");
        pos22.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3]))  /  (Double.parseDouble(TOMANinfo[3]))  * 100 ) +"%");
        pos23.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3]))  /  (Double.parseDouble(YENinfo[3]))  * 100 ) +"%");
        pos24.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3]))  /  (Double.parseDouble(GBPinfo[3]))  * 100 ) +"%");

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
    Timer timer;

    public void Reminder(int seconds) {
        timer = new Timer();
        timer.schedule(new RemindTask(), seconds* 1000L);
    }
    public class RemindTask extends TimerTask {
        public void run() {
            ChangeLabels();
        }
    }

    public void ChangeLabels (){

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

        pos20.setText(String.format("%.2f",(Double.parseDouble(USDinfo[2]) - Double.parseDouble(USDinfo[3]))  /  (Double.parseDouble(USDinfo[3]))  *  100 ) +"%");
        pos21.setText(String.format("%.2f",(Double.parseDouble(EURinfo[2]) - Double.parseDouble(EURinfo[3]))  /  (Double.parseDouble(EURinfo[3]))  * 100 ) +"%");
        pos22.setText(String.format("%.2f",(Double.parseDouble(TOMANinfo[2]) - Double.parseDouble(TOMANinfo[3]))  /  (Double.parseDouble(TOMANinfo[3]))  * 100 ) +"%");
        pos23.setText(String.format("%.2f",(Double.parseDouble(YENinfo[2]) - Double.parseDouble(YENinfo[3]))  /  (Double.parseDouble(YENinfo[3]))  * 100 ) +"%");
        pos24.setText(String.format("%.2f",(Double.parseDouble(GBPinfo[2]) - Double.parseDouble(GBPinfo[3]))  /  (Double.parseDouble(GBPinfo[3]))  * 100 ) +"%");

        if(Double.parseDouble(USDinfo[2]) > Double.parseDouble(USDinfo[4])){
            USDinfo[4] = USDinfo[2];

        }




    }
}
