module com.example.comp336_42 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.comp336_42 to javafx.fxml;
    exports com.example.comp336_42;
}