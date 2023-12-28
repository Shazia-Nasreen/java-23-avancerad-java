package com.gritacademy.fileexplorerdemo;


import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class javaFxFileApplication extends Application {
    public static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(javaFxFileApplication.class.getResource("hello-view.fxml"));
        scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("open file!");
        stage.setScene(scene);
        stage.show();
        stage.getIcons().add(new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/1/15/Cat_August_2010-4.jpg/362px-Cat_August_2010-4.jpg"));
        // stage.getIcons().add(new Image("src/limitlessInteractions-v1.png"));
        // stage.getIcons().add(new Image(HelloApplication.class.getResourceAsStream( "limitlessInteractions-v1.png" )));
        FileChooser fileC = new FileChooser();

        fileC.setInitialDirectory(new File("src")); // init path annars C
        fileC.setTitle("Open File");

        fileC.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TABLE FILES", "*.csv", "*.json", "*.xml"),
                new FileChooser.ExtensionFilter("csv", "*.csv"),
                new FileChooser.ExtensionFilter("json", "*.json"),
                new FileChooser.ExtensionFilter("xml", "*.xml"),
                new FileChooser.ExtensionFilter("ALL FILES", "*.*")
        );

        File file = fileC.showOpenDialog(stage.getScene().getWindow());


        if (file != null) {
            System.out.println(file.getPath());
            if (file.getName().endsWith(".json")) {
                //read json
                JsonValue jv = Json.parse("{\"age\": 31}");
                JsonArray ja = jv.asArray();
                System.out.println(ja);
            }
            if (file.getName().endsWith(".csv")) {
                System.out.println("csv!!!");
            }
        } else {
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