module com.example.comp336_42 {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.comp336_42 to javafx.fxml;
    exports com.example.comp336_42;
}