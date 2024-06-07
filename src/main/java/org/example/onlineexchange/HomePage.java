package org.example.onlineexchange;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class HomePage {
        @FXML
        private Button profileButton;
        public void ClickOnProfileButton(ActionEvent event) throws IOException {
            Main m2 = new Main();
            m2.changeScene("profile");
        }
}
