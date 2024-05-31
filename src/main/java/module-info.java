module org.example.onlineexchange {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.mail;

    opens org.example.onlineexchange to javafx.fxml;
    exports org.example.onlineexchange;
}