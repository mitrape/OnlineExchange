package org.example.onlineexchange;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuButton;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

                        lastMinute = currentMinute;
                    }
                });
            }
        });
        thread.start();
    }
    public static void exportTransactionsToCSV(String username, String outputPath) {
        String query = "SELECT 'tomantransaction' AS TableName, Transaction, amount, SellOrBuy, date FROM tomantransaction WHERE username = ? "
                + "UNION ALL SELECT 'eurtransaction' AS TableName, Transaction, amount, SellOrBuy, date FROM eurtransaction WHERE username = ? "
                + "UNION ALL SELECT 'usdtransaction' AS TableName, Transaction, amount, SellOrBuy, date FROM usdtransaction WHERE username = ?"
                + "UNION ALL SELECT 'yentransaction' AS TableName, Transaction, amount, SellOrBuy, date FROM usdtransaction WHERE username = ?"
                + "UNION ALL SELECT 'gbptransaction' AS TableName, Transaction, amount, SellOrBuy, date FROM usdtransaction WHERE username = ?";

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
        String query1 = "SELECT 'tomanopenrequests' AS TableName, tomanOpenRequests, amount, SellOrBuy, date FROM tomanopenrequests WHERE username = ? ";
        String query2 = "SELECT 'europenrequests' AS TableName, eurOpenRequests, amount, SellOrBuy, date FROM europenrequests WHERE username = ? ";
        String query3 = "SELECT 'usdopenrequests' AS TableName, usdOpenRequests, amount, SellOrBuy, date FROM usdopenrequests WHERE username = ?";
        String query4 = "SELECT 'yenopenrequests' AS TableName, yenOpenRequests, amount, SellOrBuy, date FROM yenopenrequests WHERE username = ?";
        String query5 = "SELECT 'gbpopenrequests' AS TableName, gbpOpenRequests, amount, SellOrBuy, date FROM gbpopenrequests WHERE username = ?";

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


}
