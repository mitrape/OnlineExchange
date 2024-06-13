module org.example.onlineexchange {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.mail ;
    requires commons.math3;

    opens org.example.onlineexchange to javafx.fxml;
    exports org.example.onlineexchange;
}