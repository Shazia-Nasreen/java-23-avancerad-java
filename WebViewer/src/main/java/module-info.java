module com.example.webviewer {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.webviewer to javafx.fxml;
    exports com.example.webviewer;
}