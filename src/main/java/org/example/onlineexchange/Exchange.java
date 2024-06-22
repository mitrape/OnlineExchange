package org.example.onlineexchange;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class Exchange implements Initializable {

    private Slider mySlider ;
    int myCurrency ;

    public void ClickOnDone (ActionEvent event){

    }
    ToggleGroup tg = new ToggleGroup();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mySlider.valueProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue <? extends Number>)
        });
    }
}
