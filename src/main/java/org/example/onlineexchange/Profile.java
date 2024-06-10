package org.example.onlineexchange;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class Profile implements Initializable {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // avaz konm
        photo.setImage(User.user[0].getProfilePhoto());
    }

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
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose an image file");
            File selectedFile = fileChooser.showOpenDialog(Main.stg);
            if (selectedFile != null) {
                Path sourcePath = selectedFile.toPath();
                Path destinationPath = Paths.get("/Users/melikadehestani/Desktop/uni/FOP advanced/final proj/OnlineExchange/src/image"+selectedFile.getName());
                Files.copy(sourcePath, destinationPath);
                Image image = new Image(sourcePath.toUri().toString());
                int cropWidth = 484;
                int cropHeight = 361;
                WritableImage croppedImage = new WritableImage(image.getPixelReader(), 0, 0, cropWidth, cropHeight);
                photo.setImage(croppedImage);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void ClickOnEditInfoButton (ActionEvent event) throws IOException {
        nameTextBox.setVisible(true);
        lastnameTextBox.setVisible(true);
        passwordTextBox.setVisible(true);
        phoneNumberTextBox.setVisible(true);
        emailTextBox.setVisible(true);
        choosePhotoButton.setVisible(true);
        photo.setVisible(false);
        historyButton.setVisible(false);
        walletButton.setVisible(false);
        editInfoButton.setVisible(false);
        exitButton.setVisible(true);
        nameLabel.setVisible(false);
        lastnameLabel.setVisible(false);
        passwordLabel.setVisible(false);
        phonenumberLabel.setVisible(false);
        emailLabel.setVisible(false);
        doneButton.setVisible(true);
    }
    public void ClickOnDoneButton (ActionEvent event) throws IOException {
        nameTextBox.setVisible(false);
        lastnameTextBox.setVisible(false);
        passwordTextBox.setVisible(false);
        phoneNumberTextBox.setVisible(false);
        emailTextBox.setVisible(false);
        choosePhotoButton.setVisible(false);
        photo.setVisible(true);
        historyButton.setVisible(true);
        walletButton.setVisible(true);
        editInfoButton.setVisible(true);
        exitButton.setVisible(true);
        nameLabel.setVisible(true);
        lastnameLabel.setVisible(true);
        passwordLabel.setVisible(true);
        phonenumberLabel.setVisible(true);
        emailLabel.setVisible(true);
        doneButton.setVisible(false);
    }


}
