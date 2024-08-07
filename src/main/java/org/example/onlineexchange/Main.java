package org.example.onlineexchange;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;


public class Main extends Application {
    public static String username = null;
    public static Stage stg;
    public static Connection connection = null;
    public static String demoState = "false";
    public static Double userMoney ;
    public static Double userUSD ;
    public static Double userEUR ;
    public static Double userYEN ;
    public static Double userGBP ;
    public static Double userTOMAN ;

    @Override
    public void start(Stage PrimaryStage) throws IOException {
        stg = PrimaryStage;
        PrimaryStage.setResizable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Image icon = new Image("logo.jpg");
        PrimaryStage.getIcons().add(icon);
        PrimaryStage.setTitle("Online Exchange-Login");
        PrimaryStage.setScene(scene);
        PrimaryStage.show();
    }
    public void changeScene (String fxml) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml+".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stg.setScene(scene);
        stg.show();
    }
    public void openNewWindow (String title, String fxml) throws  IOException{
        Stage newStage = new Stage();
        newStage.setTitle(title);
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml+".fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        newStage.setScene(scene);
        newStage.show();
    }

    void createConnection () throws Exception{
        final String URL = "jdbc:mysql://127.0.0.1:3307/users_personal_data";
        final String USER = "root";
        final String PASSWORD = "13832220";
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void go (){
        Main m = new Main();
        try {
            m.createConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        launch();
    }
}