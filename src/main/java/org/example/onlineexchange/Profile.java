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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    @FXML
    private Label usernameLabel ;
    @FXML
    private Text lastNameMessage ;
    @FXML
    private Text nameMessage ;
    @FXML
    private Text passwordMessage ;
    @FXML
    private Text phoneNumberMessage ;
    @FXML
    private Text emailMessage ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PreparedStatement findUser = null;
        ResultSet resultSet = null;
        String photoName = null;
        String email = null;
        String phoneNumber = null;
        String password = null;
        String lastName = null;
        String firstName = null;
        String username = null;
        try {
            findUser = Main.connection.prepareStatement("SELECT photoName,username,firstName,lastName,password,phoneNumber,email FROM usersdata WHERE username = ?");
            findUser.setString(1,Main.username);
            resultSet = findUser.executeQuery();
            while (resultSet.next()){
                photoName = resultSet.getString("photoName");

                username = resultSet.getString("username");

                firstName = resultSet.getString("firstName");

                lastName = resultSet.getString("lastName");

                password = resultSet.getString("password");

                phoneNumber = resultSet.getString("phoneNumber");

                email = resultSet.getString("email");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//       Image image = new Image("D:\\programming projects\\OnlineExchange\\src\\image\\"+photoName);
//        int cropWidth = 484;
//        int cropHeight = 361;
//        WritableImage croppedImage = new WritableImage(image.getPixelReader(), 0, 0, cropWidth, cropHeight);
//        photo.setImage(croppedImage);
        usernameLabel.setText(username);
        nameLabel.setText(firstName);
        lastnameLabel.setText(lastName);
        passwordLabel.setText(password);
        phonenumberLabel.setText(phoneNumber);
        emailLabel.setText(email);
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
        //اینجا بیاد عکس با اسم تکراری بزنه رو هندل نکردم
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose an image file");
            File selectedFile = fileChooser.showOpenDialog(Main.stg);
            if (selectedFile != null) {

                String sql = "UPDATE usersdata SET photoName = ? WHERE username = ?";
                PreparedStatement pstmt = Main.connection.prepareStatement(sql);
                pstmt.setString(1, selectedFile.getName());
                pstmt.setString(2, Main.username);
                pstmt.executeUpdate();

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
    public void ClickOnDoneButton (ActionEvent event) throws IOException, SQLException {

        String Name = nameTextBox.getText();
        String lastname = lastnameTextBox.getText();
        String Password = passwordTextBox.getText();
        String PhoneNumber = phoneNumberTextBox.getText();
        String Email = emailTextBox.getText();


        boolean nameFill = Name.isEmpty();
        boolean lastNameFill = lastname.isEmpty();
        boolean passwordFill = Password.isEmpty();
        boolean phoneNumberFill = PhoneNumber.isEmpty();
        boolean emailFill = Email.isEmpty();

        User user = new User();
        boolean[] inputCheker = new boolean[5];
        for (int i = 0; i < 5; i++) {
            inputCheker[i] = true;
        }

        if(!nameFill){
            user.setFirstname(nameTextBox.getText());
            if(user.correctInfo[3]){
                String sql = "UPDATE usersdata SET firstName = ? WHERE username = ?";
                PreparedStatement pstmt = null;
                try {
                    pstmt = Main.connection.prepareStatement(sql);
                    pstmt.setString(1,nameTextBox.getText());
                    pstmt.setString(2, Main.username);
                    pstmt.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                nameMessage.setVisible(false);
                inputCheker[0] = true;
            }
            else {
                nameMessage.setText("incorrect name");
                nameMessage.setVisible(true);
                inputCheker[0] = false;
            }
        }
        else{
            nameMessage.setVisible(false);
        }


        if(!lastNameFill){
            user.setLastname(lastnameTextBox.getText());
            if(user.correctInfo[4]){
                String sql = "UPDATE usersdata SET lastName = ? WHERE username = ?";
                PreparedStatement pstmt = null;
                try {
                    pstmt = Main.connection.prepareStatement(sql);
                    pstmt.setString(1,lastnameTextBox.getText());
                    pstmt.setString(2, Main.username);
                    pstmt.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                lastNameMessage.setVisible(false);
                inputCheker[1] = true;
            }
            else{
                lastNameMessage.setText("incorrect last name");
                lastNameMessage.setVisible(true);
                inputCheker[1] = false;
            }
        }
        else{
            lastNameMessage.setVisible(false);
        }


        if(!passwordFill){
            user.setPassword(passwordTextBox.getText());
            if(user.correctInfo[1]){
                String sql = "UPDATE usersdata SET password = ? WHERE username = ?";
                PreparedStatement pstmt = null;
                try {
                    pstmt = Main.connection.prepareStatement(sql);
                    pstmt.setString(1,passwordTextBox.getText());
                    pstmt.setString(2, Main.username);
                    pstmt.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                passwordMessage.setVisible(false);
                inputCheker[2] = true;
            }
            else{
                passwordMessage.setText("weak password");
                passwordMessage.setVisible(true);
                inputCheker[2] = false;
            }
        }
        else{
            passwordMessage.setVisible(false);
        }


        if(!phoneNumberFill){
            PreparedStatement psCheckUsernameExists = null;
            ResultSet resultSet = null;
            psCheckUsernameExists = Main.connection.prepareStatement("SELECT * FROM usersdata WHERE phoneNumber = ?");
            psCheckUsernameExists.setString(1, phoneNumberTextBox.getText());
            resultSet = psCheckUsernameExists.executeQuery();
            if(resultSet.isBeforeFirst()){
                phoneNumberMessage.setText("phone number already exist");
                phoneNumberMessage.setVisible(true);
                inputCheker[3] = false ;
            }
            else{
                user.setPhoneNumber(phoneNumberTextBox.getText());
                if(user.correctInfo[6]){
                    String sql = "UPDATE usersdata SET phoneNumber = ? WHERE username = ?";
                    PreparedStatement pstmt = null;
                    try {
                        pstmt = Main.connection.prepareStatement(sql);
                        pstmt.setString(1,phoneNumberTextBox.getText());
                        pstmt.setString(2, Main.username);
                        pstmt.executeUpdate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    phoneNumberMessage.setVisible(false);
                    inputCheker[3] = true;
                }
                else {
                    phoneNumberMessage.setText("incorrect phone number");
                    phoneNumberMessage.setVisible(true);
                    inputCheker[3] = false;
                }
            }
        }
        else{
            phoneNumberMessage.setVisible(false);
        }


        if(!emailFill){
            PreparedStatement psCheckUsernameExists = null;
            ResultSet resultSet = null;
            psCheckUsernameExists = Main.connection.prepareStatement("SELECT * FROM usersdata WHERE email = ?");
            psCheckUsernameExists.setString(1,emailTextBox.getText());
            resultSet = psCheckUsernameExists.executeQuery();
            if(resultSet.isBeforeFirst()){
                emailMessage.setText("email already exist");
                emailMessage.setVisible(true);
                inputCheker[4] = false;
            }
            else{
                user.setEmail(emailTextBox.getText());
                if(user.correctInfo[5]){
                    String sql = "UPDATE usersdata SET email = ? WHERE username = ?";
                    PreparedStatement pstmt = null;
                    try {
                        pstmt = Main.connection.prepareStatement(sql);
                        pstmt.setString(1,emailTextBox.getText());
                        pstmt.setString(2, Main.username);
                        pstmt.executeUpdate();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    emailMessage.setVisible(false);
                    inputCheker[4] = true;
                }
                else{
                    emailMessage.setText("incorrect email");
                    emailMessage.setVisible(true);
                    inputCheker[4] = false;
                }
            }
        }
        else{
            emailMessage.setVisible(false);
        }
        boolean checkFlag = true;
        for (int i = 0; i < 5; i++) {
            if(!inputCheker[i]){
                checkFlag = false;
                break;
            }
        }
        if(checkFlag) {
            PreparedStatement findUser = null;
            ResultSet resultSet = null;
            String email = null;
            String phoneNumber = null;
            String password = null;
            String lastName = null;
            String firstName = null;
            String username = null;
            try {
                findUser = Main.connection.prepareStatement("SELECT username,firstName,lastName,password,phoneNumber,email FROM usersdata WHERE username = ?");
                findUser.setString(1,Main.username);
                resultSet = findUser.executeQuery();
                while (resultSet.next()){
                    username = resultSet.getString("username");

                    firstName = resultSet.getString("firstName");

                    lastName = resultSet.getString("lastName");

                    password = resultSet.getString("password");

                    phoneNumber = resultSet.getString("phoneNumber");

                    email = resultSet.getString("email");

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            usernameLabel.setText(username);
            nameLabel.setText(firstName);
            lastnameLabel.setText(lastName);
            passwordLabel.setText(password);
            phonenumberLabel.setText(phoneNumber);
            emailLabel.setText(email);
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


}
