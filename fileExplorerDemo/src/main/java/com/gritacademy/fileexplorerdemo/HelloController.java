package com.gritacademy.fileexplorerdemo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;

import java.io.File;

import static com.gritacademy.fileexplorerdemo.HelloApplication.scene;


public class HelloController {

    @FXML
    private Label welcomeText;

    @FXML
    void onHelloButtonClick(ActionEvent event) {
        FileChooser fileC = new FileChooser();
        fileC.setInitialDirectory(new File("src")); // init path annars C
        fileC.setTitle("Open File");

        fileC.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ALL FILES", "*.*"),
                new FileChooser.ExtensionFilter("ZIP", "*.zip"),
                new FileChooser.ExtensionFilter("PDF", "*.pdf"),
                new FileChooser.ExtensionFilter("TEXT", "*.txt"),
                new FileChooser.ExtensionFilter("IMAGE FILES", "*.jpg", "*.png", "*.gif")
        );

        File file = fileC.showOpenDialog(scene.getWindow());
        if (file != null) {
            System.out.println(file.getPath());

        } else  {
            System.out.println("error"); // or something else
        }


    }

}

