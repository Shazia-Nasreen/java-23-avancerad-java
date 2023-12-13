package com.gritcacdemy.fetchimgintojavafx;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Group root = new Group();
        Scene scene = new Scene(root, 320, 540);
        VBox vb = new VBox();



        ImageView iv = new ImageView("https://learn.g2.com/hs-fs/hubfs/Types%20of%20API-1.jpg?width=590&name=Types%20of%20API-1.jpg");
        ImageView iv2 = new ImageView("https://learn.g2.com/hs-fs/hubfs/Types%20of%20API-1.jpg?width=590&name=Types%20of%20API-1.jpg");
        vb.getChildren().add( new TextField("test)"));
        vb.getChildren().add( new Button("hello"));
        vb.getChildren().add( iv  );
        vb.getChildren().add( iv2  );


        JsonValue jv = Json.parse("{\"url\": \"http://alrik.com/bild.jpeg\" , \"age\" : 55 , \"array\": [ { \"ability1\" : \"thunder\"}  , { \"ability2\" : \"zap\"}   ]        }");
        JsonObject ja = jv.asObject();
        JsonArray ar = ja.get("array").asArray();
        System.out.println(ar.get(0).asObject().get("ability1"));


        root.getChildren().add(vb);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setResizable(false);

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}