module com.gritacademy.javafxtabell {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.gritacademy.javafxtabell to javafx.fxml;
    exports com.gritacademy.javafxtabell;
}