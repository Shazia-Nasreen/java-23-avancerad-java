module com.gritacademy.pointgraph {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.gritacademy.pointgraph to javafx.fxml;
    exports com.gritacademy.pointgraph;
}