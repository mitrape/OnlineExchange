package org.example.onlineexchange;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static Stage stg;

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

    public static void main(String[] args) {
        User.user[0]=new User();
        User.user[0].setUsername("mP.1");
        User.user[0].setPassword("1Mp@2222");
        User.user[0].setEmail("miti1383@gmail.com");

        launch(args);

    }
}