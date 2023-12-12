module com.gritacademy.opponentgrouprandomizer {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.gritacademy.opponentgrouprandomizer to javafx.fxml;
    exports com.gritacademy.opponentgrouprandomizer;
}