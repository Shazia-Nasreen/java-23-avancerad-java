module com.gritacademy.javafxdemo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.gritacademy.javafxdemo to javafx.fxml;
    exports com.gritacademy.javafxdemo;
}