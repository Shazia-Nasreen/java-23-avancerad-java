package com.gritacademy.annotations;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/*****************************
 * @author Alrik He
 * @version 1.0.0
 * @see java.util.Arrays
 * @since 1.0.0
 *******************************/
public class AnnotationApplication extends Application {



    /**
     * <p>This is a simple description of the method. . .
     * <a href="http://www.supermanisthegreatest.com">Superman!</a>
     * </p>
     * @param stage javafx stage
     * @return void
     * @see <a href="http://www.link_to_jira/HERO-402">HERO-402</a>
     * @since 1.0
     */
    @Override
    public void start(Stage stage) throws IOException, InvocationTargetException, IllegalAccessException {
      //  FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 420, 340);
        Group g= new Group();
        Scene scene = new Scene(g, 420, 340);
        stage.setTitle("JAVAFX UTAN SCENEBUILDER!");
        stage.setScene(scene);

        VBox vb=new VBox();
        vb.getChildren().add(  new Button("check meta info stuff"));

        g.getChildren().add(vb);

        stage.show();

        Human h = new Human( 50);

        if(h.getClass().isAnnotationPresent(metaInfo.class)) {
            System.out.println("har annotation");


            for (Method m: h.getClass().getMethods()) {
                if(m.isAnnotationPresent(metaInfo.class))
                    System.out.println(m.getName());
            }
        }

    }
    /**
     @link <a href="http://www.link_to_jira/HERO-404">HERO-404</a>
     @param args default java parameter
     */
    public static void main(String[] args) {



        launch();

    }
}