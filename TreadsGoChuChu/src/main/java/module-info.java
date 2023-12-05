module com.gritacademy.treadsgochuchu {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.gritacademy.treadsgochuchu to javafx.fxml;
    exports com.gritacademy.treadsgochuchu;
}