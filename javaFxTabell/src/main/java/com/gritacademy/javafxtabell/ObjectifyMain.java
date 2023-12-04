package com.gritacademy.javafxtabell;
/*import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;*/
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class ObjectifyMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ObjectifyMain.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
       // stage.show();


/*
        List<Row> expirations = Arrays.asList(new Row[] {
                new Row("Alrik","He","31") ,
                new Row("Mashreka","Mannan","32")
        });


        Set<String> sortedSet = new TreeSet<>(expirations);

        for (Iterator<String> i = sortedSet.iterator(); i.hasNext();) {
            String item = i.next();
            System.out.println(item);
        }*/

    }

    public static void main(String[] args) {
        launch();
    }
}