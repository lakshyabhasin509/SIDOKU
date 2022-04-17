module com.example.sidoku {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.sidoku to javafx.fxml;
    exports com.example.sidoku;
}