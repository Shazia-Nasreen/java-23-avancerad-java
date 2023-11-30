package com.gritacademy.fileexplorerdemo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

import static com.gritacademy.fileexplorerdemo.javaFxFileApplication.scene;

public class JavaFXFileController {

    @FXML
    private Label welcomeText;
    @FXML
    private ScrollPane scrollP;
    @FXML
    void onHelloButtonClick(ActionEvent event) {

    }

    @FXML
    void onSaveButton(ActionEvent event) {
        System.out.println("SAVEE!!!!!!!"); // or something else
        FileChooser fileC = new FileChooser();
        fileC.setInitialDirectory(new File("src/main/java/com/gritacademy/fileexplorerdemo/tabels")); // init path annars C
        fileC.setTitle("Save File");

        fileC.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TABLE FILES", "*.csv", "*.json", "*.xml"),
                new FileChooser.ExtensionFilter("csv", "*.csv"),
                new FileChooser.ExtensionFilter("json", "*.json"),
                new FileChooser.ExtensionFilter("xml", "*.xml"),
                new FileChooser.ExtensionFilter("ALL FILES", "*.*")
        );

        File file = fileC.showSaveDialog(scene.getWindow());
        if (file != null) {
            System.out.println(file.getPath());
            writeFile(file);
        } else  {
            System.out.println("save canceled!!!!"); // or something else
        }
    }

    private void writeFile(File file) {
        try {
            System.out.println(file.getName());
            String  fileExtention = file.getName().split("\\.")[1];
            file.createNewFile(); //empty

            switch (fileExtention){
                case "xml"-> System.out.println("write mxl code here!!!");
                case "csv"-> System.out.println("write csv code here!!!");
                case "json"-> System.out.println("write json code here!!!");
                case "txt"-> {

                    PrintWriter writer = new PrintWriter(file.getPath(), "UTF-8");
                    writer.println("detta är en text fil");
                    writer.println("by: Alrik He");
                    writer.close();


                }
            }
        }catch (IOException io){
            System.out.println(io);
        }
    }

}

