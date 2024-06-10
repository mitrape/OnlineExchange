package org.example.onlineexchange;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class HomePage {
    @FXML
    private Button username ;
    public void ClickOnToman (ActionEvent event){

    }
    public void ClickOnDollar (ActionEvent event){

    }
    public void ClickOnYen (ActionEvent event){

    }
    public void ClickOnEuro (ActionEvent event){

    }
    public void ClickOnPound (ActionEvent event){

    }
    public void ClickOnProfile (ActionEvent event) throws IOException {
        Main m = new Main();
        m.changeScene("profile");
    }
}
