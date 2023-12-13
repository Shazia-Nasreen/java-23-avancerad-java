module com.gritcacdemy.fetchimgintojavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires minimal.json;

    opens com.gritcacdemy.fetchimgintojavafx to javafx.fxml;
    exports com.gritcacdemy.fetchimgintojavafx;
}