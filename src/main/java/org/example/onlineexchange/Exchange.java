package org.example.onlineexchange;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import java.net.URL;
import java.util.ResourceBundle;

public class Exchange implements Initializable {

    private Slider mySlider ;
    @FXML
    private Label labelSlider;
    double myCurrency ;

//    public void ClickOnDone (ActionEvent event){
//
//    }
    ToggleGroup tg = new ToggleGroup();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        myCurrency = mySlider.getValue();
        labelSlider.setText(Double.toString(myCurrency));
        mySlider.valueProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                myCurrency = mySlider.getValue();
                labelSlider.setText(Double.toString(myCurrency));
            }
        });
    }
}
