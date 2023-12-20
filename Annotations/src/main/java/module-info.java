module com.gritacademy.annotations {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    opens com.gritacademy.annotations to javafx.fxml;
    exports com.gritacademy.annotations;
}