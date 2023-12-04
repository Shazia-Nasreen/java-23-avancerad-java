module com.gritacademy.fileexplorerdemo {
    requires javafx.controls;
    requires javafx.fxml;
    requires minimal.json;


    opens com.gritacademy.fileexplorerdemo to javafx.fxml;
    exports com.gritacademy.fileexplorerdemo;
}