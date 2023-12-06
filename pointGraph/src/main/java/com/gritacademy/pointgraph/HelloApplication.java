package com.gritacademy.pointgraph;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    /*****************************
        Author : Alrik He
        Date: 2023
    *******************************/
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
        stage.setTitle("pointGraph");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


      //  setStyle("-fx-background-color: red");


        HelloController controller = fxmlLoader.getController();
        //controller.createGrid();
    }

    public static void main(String[] args) {
        launch();
    }
}