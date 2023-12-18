package com.gritacademy.recursion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;

import java.io.IOException;

public class RecursionApplication extends Application {
    private static boolean stopAll;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RecursionApplication.class.getResource("recursion-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        infinity(0);

    }

    public static void main(String[] args) {
        launch();
    }


    public static void infinity(int count) {
        //if(stopAll) return;
        if (count > 10) return; // recurr upp till 10
        System.out.println("INFINITY: " + count);
        Dialog dialog = new Dialog();
        dialog.setTitle("Recursion");
        dialog.setHeaderText("you have called this method " + (count + 1) + " times keep recurring?");
        ButtonType loginButtonType = ButtonType.YES;
        ButtonType cancelButtonType = ButtonType.CANCEL;

        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, cancelButtonType);
        dialog.showAndWait();
        if (dialog.getResult() == ButtonType.YES)
            infinity(++count);
    }


}