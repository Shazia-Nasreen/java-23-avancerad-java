package com.gritacademy.pointgraph;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import static com.gritacademy.pointgraph.PointController.fileChooser;

public class PointApplication extends Application {

    public static Scene scene;

    /*****************************
        Author : Alrik He
        Date: 2023
    *******************************/
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PointApplication.class.getResource("Point-view.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("pointGraph");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        System.out.println(Mode.DEFAULT.ordinal()); //index
        System.out.println(Mode.DEFAULT); //skriva ut string direkt
        System.out.println(Mode.DEFAULT==Mode.DEFAULT); //jämnförelse

        //  setStyle("-fx-background-color: red");


        PointController controller = fxmlLoader.getController();
        //controller.createGrid();
    }

    public static void main(String[] args) {
        launch();
    }
}