package org.example.onlineexchange;

import javafx.event.ActionEvent;

import java.io.IOException;

public class USD {
    public void ClickOnBack (ActionEvent event) throws IOException {
        Main m = new Main() ;
        m.changeScene("homePage");
    }
}
