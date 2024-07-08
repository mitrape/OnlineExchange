package org.example.onlineexchange;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;


public class History implements Initializable {
    int lastMinute = -1;
    private volatile boolean stop = false;
    @FXML
    private TableView<TransactionData> OpenRequestsTable;
    @FXML
    private TableView <TransactionData> TransactionTable;


    @FXML
    private TableColumn<TransactionData, String> currencyOpen;
    @FXML
    private TableColumn<TransactionData, Double> priceOpen;
    @FXML
    private TableColumn<TransactionData, Double> turnoverOpen;
    @FXML
    private TableColumn<TransactionData, String> sellorbuyOpen;
    @FXML
    private TableColumn<TransactionData, String> dateOpen;

    @FXML
    private TableColumn<TransactionData, String> currencyTran;
    @FXML
    private TableColumn<TransactionData, Double> priceTran;
    @FXML
    private TableColumn<TransactionData, Double> turnoverTran;
    @FXML
    private TableColumn<TransactionData, String> sellorbuyTran;
    @FXML
    private TableColumn<TransactionData, String> dateTran;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setLastTransactionTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            setPendingTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        changeTime();
    }

    public void changeTime(){
        Thread thread = new Thread(() -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss a");
            while (!stop){
                try {
                    Thread.sleep(1000+1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                final java.util.Date[] now = {new Date()};
                final String timenow = simpleDateFormat.format(now[0]);

                Platform.runLater(() -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(now[0]);
                    int currentMinute = calendar.get(Calendar.MINUTE);
                    if (currentMinute != lastMinute) {
                        // A new minute has passed, call your update function here
                        try {
                            setLastTransactionTable();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        try {
                            setPendingTable();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        lastMinute = currentMinute;
                    }
                });
            }
        });
        thread.start();
    }
    public static void exportTransactionsToCSV(String username, String outputPath) {
        String query;
        if(Main.demoState.equals("true")) {
            query = "SELECT 'tomantransaction' AS TableName, Transaction, amount, SellOrBuy, date FROM tomantransaction WHERE username = ? "
                    + "UNION ALL SELECT 'eurtransaction' AS TableName, Transaction, amount, SellOrBuy, date FROM eurtransaction WHERE username = ? "
                    + "UNION ALL SELECT 'usdtransaction' AS TableName, Transaction, amount, SellOrBuy, date FROM usdtransaction WHERE username = ?"
                    + "UNION ALL SELECT 'yentransaction' AS TableName, Transaction, amount, SellOrBuy, date FROM usdtransaction WHERE username = ?"
                    + "UNION ALL SELECT 'gbptransaction' AS TableName, Transaction, amount, SellOrBuy, date FROM usdtransaction WHERE username = ?";
        }
        else{
            query = "SELECT 'tomantransaction' AS TableName, Transaction, amount, SellOrBuy, date FROM tomantransaction WHERE username = ?, demo=false "
                    + "UNION ALL SELECT 'eurtransaction' AS TableName, Transaction, amount, SellOrBuy, date FROM eurtransaction WHERE username = ?, demo=false "
                    + "UNION ALL SELECT 'usdtransaction' AS TableName, Transaction, amount, SellOrBuy, date FROM usdtransaction WHERE username = ?, demo=false "
                    + "UNION ALL SELECT 'yentransaction' AS TableName, Transaction, amount, SellOrBuy, date FROM usdtransaction WHERE username = ?, demo=false "
                    + "UNION ALL SELECT 'gbptransaction' AS TableName, Transaction, amount, SellOrBuy, date FROM usdtransaction WHERE username = ?, demo=false";
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/users_personal_data", "root", "13832220");
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, username);
            statement.setString(3, username);
            statement.setString(4, username);
            statement.setString(5, username);

            try (ResultSet resultSet = statement.executeQuery();
                 FileWriter writer = new FileWriter(outputPath)) {

                writer.append("currency name,price,amount,SellOrBuy,date\n");

                while (resultSet.next()) {
                    writer.append(resultSet.getString("TableName")).append(",");
                    writer.append(resultSet.getString("Transaction")).append(",");
                    writer.append(resultSet.getString("amount")).append(",");
                    writer.append(resultSet.getString("SellOrBuy")).append(",");
                    writer.append(resultSet.getString("date")).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void exportOpenRequestsToCSV (String username, String outputPath) throws SQLException, IOException {
        String query1;
        String query2;
        String query3;
        String query4;
        String query5;
        if(Main.demoState.equals("true")) {
            query1 = "SELECT 'tomanopenrequests' AS TableName, tomanOpenRequests, amount, SellOrBuy, date FROM tomanopenrequests WHERE username = ? ";
            query2 = "SELECT 'europenrequests' AS TableName, eurOpenRequests, amount, SellOrBuy, date FROM europenrequests WHERE username = ? ";
            query3 = "SELECT 'usdopenrequests' AS TableName, usdOpenRequests, amount, SellOrBuy, date FROM usdopenrequests WHERE username = ?";
            query4 = "SELECT 'yenopenrequests' AS TableName, yenOpenRequests, amount, SellOrBuy, date FROM yenopenrequests WHERE username = ?";
            query5 = "SELECT 'gbpopenrequests' AS TableName, gbpOpenRequests, amount, SellOrBuy, date FROM gbpopenrequests WHERE username = ?";
        }
        else{
            query1 = "SELECT 'tomanopenrequests' AS TableName, tomanOpenRequests, amount, SellOrBuy, date FROM tomanopenrequests WHERE username = ? , demo=false ";
            query2 = "SELECT 'europenrequests' AS TableName, eurOpenRequests, amount, SellOrBuy, date FROM europenrequests WHERE username = ? , demo=false ";
            query3 = "SELECT 'usdopenrequests' AS TableName, usdOpenRequests, amount, SellOrBuy, date FROM usdopenrequests WHERE username = ?, demo=false ";
            query4 = "SELECT 'yenopenrequests' AS TableName, yenOpenRequests, amount, SellOrBuy, date FROM yenopenrequests WHERE username = ?, demo=false ";
            query5 = "SELECT 'gbpopenrequests' AS TableName, gbpOpenRequests, amount, SellOrBuy, date FROM gbpopenrequests WHERE username = ?, demo=false ";
        }

        PreparedStatement stm1 = Main.connection.prepareStatement(query1);
        PreparedStatement stm2 = Main.connection.prepareStatement(query2);
        PreparedStatement stm3 = Main.connection.prepareStatement(query3);
        PreparedStatement stm4 = Main.connection.prepareStatement(query4);
        PreparedStatement stm5 = Main.connection.prepareStatement(query5);

        stm1.setString(1, username);
        stm2.setString(1, username);
        stm3.setString(1, username);
        stm4.setString(1, username);
        stm5.setString(1, username);

        ResultSet rs1 = stm1.executeQuery();
        ResultSet rs2 = stm2.executeQuery();
        ResultSet rs3 = stm3.executeQuery();
        ResultSet rs4 = stm4.executeQuery();
        ResultSet rs5 = stm5.executeQuery();

        FileWriter writer = new FileWriter(outputPath);
        writer.append("currency name,price,amount,SellOrBuy,date\n");

        while (rs1.next()) {
            writer.append(rs1.getString("TableName")).append(",");
            writer.append(rs1.getString("tomanOpenRequests")).append(",");
            writer.append(rs1.getString("amount")).append(",");
            writer.append(rs1.getString("SellOrBuy")).append(",");
            writer.append(rs1.getString("date")).append("\n");
        }
        while (rs2.next()) {
            writer.append(rs2.getString("TableName")).append(",");
            writer.append(rs2.getString("eurOpenRequests")).append(",");
            writer.append(rs2.getString("amount")).append(",");
            writer.append(rs2.getString("SellOrBuy")).append(",");
            writer.append(rs2.getString("date")).append("\n");
        }
        while (rs3.next()) {
            writer.append(rs3.getString("TableName")).append(",");
            writer.append(rs3.getString("usdOpenRequests")).append(",");
            writer.append(rs3.getString("amount")).append(",");
            writer.append(rs3.getString("SellOrBuy")).append(",");
            writer.append(rs3.getString("date")).append("\n");
        }
        while (rs4.next()) {
            writer.append(rs4.getString("TableName")).append(",");
            writer.append(rs4.getString("yenOpenRequests")).append(",");
            writer.append(rs4.getString("amount")).append(",");
            writer.append(rs4.getString("SellOrBuy")).append(",");
            writer.append(rs4.getString("date")).append("\n");
        }
        while (rs5.next()) {
            writer.append(rs5.getString("TableName")).append(",");
            writer.append(rs5.getString("gbpOpenRequests")).append(",");
            writer.append(rs5.getString("amount")).append(",");
            writer.append(rs5.getString("SellOrBuy")).append(",");
            writer.append(rs5.getString("date")).append("\n");
        }
        writer.close();

    }
    public void ClickOnCVSTransaction(ActionEvent event)  {
        String path = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.setInitialFileName("output.cvs");
        File userDirectory = new File(System.getProperty("user.home"));
        fileChooser.setInitialDirectory(userDirectory);
        File selectedFile = fileChooser.showSaveDialog(Main.stg);
        if (selectedFile != null) {
            path=selectedFile.getParent();
            path = path+"\\"+selectedFile.getName()+".cvs";
            exportTransactionsToCSV(Main.username,path);
        }
    }
    public void ClickOnCVSPending(ActionEvent event) throws SQLException, IOException {
        String path = null;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.setInitialFileName("output.cvs");
        File userDirectory = new File(System.getProperty("user.home"));
        fileChooser.setInitialDirectory(userDirectory);
        File selectedFile = fileChooser.showSaveDialog(Main.stg);
        if (selectedFile != null) {
            path=selectedFile.getParent();
            path = path+"\\"+selectedFile.getName()+".cvs";
            exportOpenRequestsToCSV(Main.username,path);
        }
    }
    public void setPendingTable () throws SQLException {
        OpenRequestsTable.getItems().clear();

        currencyOpen.setCellValueFactory(new PropertyValueFactory<TransactionData, String>("currency"));
        priceOpen.setCellValueFactory(new PropertyValueFactory<TransactionData, Double>("transaction"));
        turnoverOpen.setCellValueFactory(new PropertyValueFactory<TransactionData, Double>("amount"));
        sellorbuyOpen.setCellValueFactory(new PropertyValueFactory<TransactionData, String>("sellOrBuy"));
        dateOpen.setCellValueFactory(new PropertyValueFactory<TransactionData, String>("date"));

        PreparedStatement stmt1 = Main.connection.prepareStatement("SELECT * FROM tomanopenrequests WHERE username =?");
        stmt1.setString(1,Main.username);
        ResultSet tomanResult = stmt1.executeQuery();
        while (tomanResult.next()) {
            if(tomanResult.getString("demo").equals("false")) {
                OpenRequestsTable.getItems().add(new TransactionData("toman", tomanResult.getDouble("tomanOpenRequests"), tomanResult.getDouble("amount"), tomanResult.getString("SellOrBuy"), tomanResult.getString("date")));

            }
            if(tomanResult.getString("username").equals(Main.username) && tomanResult.getString("demo").equals("true") && Main.demoState.equals("true")){
                OpenRequestsTable.getItems().add(new TransactionData("toman", tomanResult.getDouble("tomanOpenRequests"), tomanResult.getDouble("amount"), tomanResult.getString("SellOrBuy"), tomanResult.getString("date")));

            }
        }


        PreparedStatement stmt2 = Main.connection.prepareStatement("SELECT * FROM europenrequests WHERE username =?");
        stmt2.setString(1,Main.username);
        ResultSet eurResult = stmt2.executeQuery();
        while (eurResult.next()) {
            if(eurResult.getString("demo").equals("false")) {
                OpenRequestsTable.getItems().add(new TransactionData("eur", eurResult.getDouble("eurOpenRequests"), eurResult.getDouble("amount"), eurResult.getString("SellOrBuy"), eurResult.getString("date")));

            }
            if(eurResult.getString("username").equals(Main.username) && eurResult.getString("demo").equals("true") && Main.demoState.equals("true")){
                OpenRequestsTable.getItems().add(new TransactionData("eur", eurResult.getDouble("eurOpenRequests"), eurResult.getDouble("amount"), eurResult.getString("SellOrBuy"), eurResult.getString("date")));

            }
        }


        PreparedStatement stmt3 = Main.connection.prepareStatement("SELECT * FROM usdopenrequests WHERE username =?");
        stmt3.setString(1,Main.username);
        ResultSet usdResult = stmt3.executeQuery();
        while (usdResult.next()) {
            if(usdResult.getString("demo").equals("false")) {
                OpenRequestsTable.getItems().add(new TransactionData("usd", usdResult.getDouble("usdOpenRequests"), usdResult.getDouble("amount"), usdResult.getString("SellOrBuy"), usdResult.getString("date")));

            }
            if(usdResult.getString("username").equals(Main.username) && usdResult.getString("demo").equals("true") && Main.demoState.equals("true")){
                OpenRequestsTable.getItems().add(new TransactionData("usd", usdResult.getDouble("usdOpenRequests"), usdResult.getDouble("amount"), usdResult.getString("SellOrBuy"), usdResult.getString("date")));

            }
        }


        PreparedStatement stmt4 = Main.connection.prepareStatement("SELECT * FROM yenopenrequests WHERE username =?");
        stmt4.setString(1,Main.username);
        ResultSet yenResult = stmt4.executeQuery();
        while (yenResult.next()) {
            if(yenResult.getString("demo").equals("false")) {
                OpenRequestsTable.getItems().add(new TransactionData("yen", yenResult.getDouble("yenOpenRequests"), yenResult.getDouble("amount"), yenResult.getString("SellOrBuy"), yenResult.getString("date")));

            }
            if(yenResult.getString("username").equals(Main.username) && yenResult.getString("demo").equals("true") && Main.demoState.equals("true")){
                OpenRequestsTable.getItems().add(new TransactionData("yen", yenResult.getDouble("yenOpenRequests"), yenResult.getDouble("amount"), yenResult.getString("SellOrBuy"), yenResult.getString("date")));

            }}


        PreparedStatement stmt5 = Main.connection.prepareStatement("SELECT * FROM gbpopenrequests WHERE username =?");
        stmt5.setString(1,Main.username);
        ResultSet gbpResult = stmt5.executeQuery();
        while (gbpResult.next()) {
            if(gbpResult.getString("demo").equals("false")) {
                OpenRequestsTable.getItems().add(new TransactionData("gbp", gbpResult.getDouble("gbpOpenRequests"), gbpResult.getDouble("amount"), gbpResult.getString("SellOrBuy"), gbpResult.getString("date")));

            }
            if(gbpResult.getString("username").equals(Main.username) && gbpResult.getString("demo").equals("true") && Main.demoState.equals("true")){
                OpenRequestsTable.getItems().add(new TransactionData("gbp", gbpResult.getDouble("gbpOpenRequests"), gbpResult.getDouble("amount"), gbpResult.getString("SellOrBuy"), gbpResult.getString("date")));

            }}

    }
    public void setLastTransactionTable () throws SQLException {
        TransactionTable.getItems().clear();

        currencyTran.setCellValueFactory(new PropertyValueFactory<TransactionData, String>("currency"));
        priceTran.setCellValueFactory(new PropertyValueFactory<TransactionData, Double>("transaction"));
        turnoverTran.setCellValueFactory(new PropertyValueFactory<TransactionData, Double>("amount"));
        sellorbuyTran.setCellValueFactory(new PropertyValueFactory<TransactionData, String>("sellOrBuy"));
        dateTran.setCellValueFactory(new PropertyValueFactory<TransactionData, String>("date"));

        PreparedStatement stmt1 = Main.connection.prepareStatement("SELECT * FROM tomantransaction WHERE username =?");
        stmt1.setString(1,Main.username);
        ResultSet tomanResult = stmt1.executeQuery();
        while (tomanResult.next()) {
            if(tomanResult.getString("demo").equals("false")) {
                TransactionTable.getItems().add(new TransactionData("toman", tomanResult.getDouble("Transaction"), tomanResult.getDouble("amount"), tomanResult.getString("SellOrBuy"), tomanResult.getString("date")));

            }
            if(tomanResult.getString("username").equals(Main.username) && tomanResult.getString("demo").equals("true") && Main.demoState.equals("true")){
                TransactionTable.getItems().add(new TransactionData("toman", tomanResult.getDouble("Transaction"), tomanResult.getDouble("amount"), tomanResult.getString("SellOrBuy"), tomanResult.getString("date")));

            }
        }


        PreparedStatement stmt2 = Main.connection.prepareStatement("SELECT * FROM eurtransaction WHERE username =?");
        stmt2.setString(1,Main.username);
        ResultSet eurResult = stmt2.executeQuery();
        while (eurResult.next()) {
            if(eurResult.getString("demo").equals("false")) {
                TransactionTable.getItems().add(new TransactionData("eur", eurResult.getDouble("Transaction"), eurResult.getDouble("amount"), eurResult.getString("SellOrBuy"), eurResult.getString("date")));

            }
            if(eurResult.getString("username").equals(Main.username) && eurResult.getString("demo").equals("true") && Main.demoState.equals("true")){
                TransactionTable.getItems().add(new TransactionData("eur", eurResult.getDouble("Transaction"), eurResult.getDouble("amount"), eurResult.getString("SellOrBuy"), eurResult.getString("date")));

            }
        }


        PreparedStatement stmt3 = Main.connection.prepareStatement("SELECT * FROM usdtransaction WHERE username =?");
        stmt3.setString(1,Main.username);
        ResultSet usdResult = stmt3.executeQuery();
        while (usdResult.next()) {
            if(usdResult.getString("demo").equals("false")) {
                TransactionTable.getItems().add(new TransactionData("usd", usdResult.getDouble("Transaction"), usdResult.getDouble("amount"), usdResult.getString("SellOrBuy"), usdResult.getString("date")));

            }
            if(usdResult.getString("username").equals(Main.username) && usdResult.getString("demo").equals("true") && Main.demoState.equals("true")){
                TransactionTable.getItems().add(new TransactionData("usd", usdResult.getDouble("Transaction"), usdResult.getDouble("amount"), usdResult.getString("SellOrBuy"), usdResult.getString("date")));

            }
        }


        PreparedStatement stmt4 = Main.connection.prepareStatement("SELECT * FROM yentransaction WHERE username =?");
        stmt4.setString(1,Main.username);
        ResultSet yenResult = stmt4.executeQuery();
        while (yenResult.next()) {
            if(yenResult.getString("demo").equals("false")) {
                TransactionTable.getItems().add(new TransactionData("yen", yenResult.getDouble("Transaction"), yenResult.getDouble("amount"), yenResult.getString("SellOrBuy"), yenResult.getString("date")));

            }
            if(yenResult.getString("username").equals(Main.username) && yenResult.getString("demo").equals("true") && Main.demoState.equals("true")){
                TransactionTable.getItems().add(new TransactionData("yen", yenResult.getDouble("Transaction"), yenResult.getDouble("amount"), yenResult.getString("SellOrBuy"), yenResult.getString("date")));

            }}


        PreparedStatement stmt5 = Main.connection.prepareStatement("SELECT * FROM gbptransaction WHERE username =?");
        stmt5.setString(1,Main.username);
        ResultSet gbpResult = stmt5.executeQuery();
        while (gbpResult.next()) {
            if(gbpResult.getString("demo").equals("false")) {
                TransactionTable.getItems().add(new TransactionData("gbp", gbpResult.getDouble("Transaction"), gbpResult.getDouble("amount"), gbpResult.getString("SellOrBuy"), gbpResult.getString("date")));

            }
            if(gbpResult.getString("username").equals(Main.username) && gbpResult.getString("demo").equals("true") && Main.demoState.equals("true")){
                TransactionTable.getItems().add(new TransactionData("gbp", gbpResult.getDouble("Transaction"), gbpResult.getDouble("amount"), gbpResult.getString("SellOrBuy"), gbpResult.getString("date")));

            }
        }
    }

    public void ClickOnExit (ActionEvent event){
        System.exit(0);
    }
}

