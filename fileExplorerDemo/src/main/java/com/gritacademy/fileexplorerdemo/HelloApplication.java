package com.gritacademy.fileexplorerdemo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    public static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
         scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("open file!");
        stage.setScene(scene);
        stage.show();

        FileChooser fileC = new FileChooser();
        fileC.setInitialDirectory(new File("src")); // init path annars C
        fileC.setTitle("Open File");

        fileC.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("csv", "*.csv"),
                new FileChooser.ExtensionFilter("json", "*.json"),
                new FileChooser.ExtensionFilter("xml", "*.xml"),
                new FileChooser.ExtensionFilter("ALL FILES", "*.*")
             /*   new FileChooser.ExtensionFilter("ZIP", "*.zip"),
                new FileChooser.ExtensionFilter("PDF", "*.pdf"),
                new FileChooser.ExtensionFilter("TEXT", "*.txt"),
                new FileChooser.ExtensionFilter("IMAGE FILES", "*.jpg", "*.png", "*.gif")
      */  );

        File file = fileC.showOpenDialog(stage.getScene().getWindow());
        if (file != null) {
            System.out.println(file.getPath());
        } else  {
            System.out.println("error"); // or something else
        }

    }

    public static void main(String[] args) {
        launch();
    }

/*    @Override
    public void init() {
        applicationInstance = this;
    }*/
}