module com.gritacademy.fileexplorerdemo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.gritacademy.fileexplorerdemo to javafx.fxml;
    exports com.gritacademy.fileexplorerdemo;
}