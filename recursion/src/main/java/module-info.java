module com.gritacademy.recursion {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.gritacademy.recursion to javafx.fxml;
    exports com.gritacademy.recursion;
}