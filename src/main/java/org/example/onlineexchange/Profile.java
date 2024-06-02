package org.example.onlineexchange;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Profile {
    @FXML
    private TextField nameTextBox ;
    @FXML
    private TextField lastnameTextBox ;
    @FXML
    private TextField passwordTextBox ;
    @FXML
    private TextField phoneNumberTextBox ;
    @FXML
    private TextField emailTextBox ;
    @FXML
    private Button choosePhotoButton ;
    @FXML
    private ImageView photo ;
    @FXML
    private Button historyButton ;
    @FXML
    private Button walletButton ;
    @FXML
    private Button editInfoButton ;
    @FXML
    private Button exitButton ;
    @FXML
    private Label nameLabel ;
    @FXML
    private Label lastnameLabel ;
    @FXML
    private Label passwordLabel ;
    @FXML
    private Label phonenumberLabel ;
    @FXML
    private Label emailLabel ;
    @FXML
    private Button doneButton ;


    public void ClickOnExit (ActionEvent event) throws IOException {
        System.exit(0);
    }
    public void ClickOnHistoryButton (ActionEvent event) throws IOException {
        Main m1 = new Main();
        m1.changeScene("History");
    }
    public void ClickOnWalletButton (ActionEvent event) throws IOException {
        Main m2 = new Main();
        m2.changeScene("Wallet");
    }
    public void ClickOnChoosePhotoButton (ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an image file");
//        Stage newStage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(Main.stg);
//        Scene scene = new Scene(null);
//        newStage.setScene(scene);
//        newStage.setTitle("Photo Upload App");
//        newStage.show();
    }
    public void ClickOnEditInfoButton (ActionEvent event) throws IOException {
        nameTextBox.setVisible(true);
        lastnameTextBox.setVisible(true); ;
        passwordTextBox.setVisible(true);
        phoneNumberTextBox.setVisible(true);
        emailTextBox.setVisible(true);
        choosePhotoButton.setVisible(true); ;
        photo.setVisible(false); ;
        historyButton.setVisible(false); ;
        walletButton.setVisible(false);
        editInfoButton.setVisible(false); ;
        exitButton.setVisible(true);
        nameLabel.setVisible(false); ;
        lastnameLabel.setVisible(false);
        passwordLabel.setVisible(false); ;
        phonenumberLabel.setVisible(false);
        emailLabel.setVisible(false); ;
        doneButton.setVisible(true); ;
    }


}
