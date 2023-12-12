package com.gritacademy.treadsgochuchu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Set;

public class TreadsApplication extends Application {
    public static int order=0;
    public static Train2 t2;
    public static Train2 t3;

    @Override
    public void start(Stage stage) throws IOException, InterruptedException {


        FXMLLoader fxmlLoader = new FXMLLoader(TreadsApplication.class.getResource("threads-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 720, 540);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        System.out.println(Thread.activeCount()); //hur många threads
        System.out.println(Thread.currentThread().getName()); //namnet
        System.out.println(Thread.currentThread().isAlive()); //existerar den
        //Thread.currentThread().setDaemon(true); //bakgrund log prioritet går inte på main tråden
        System.out.println(Thread.currentThread().isDaemon()); //existerar den

        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        System.out.println(threadSet);
        for (Thread t: threadSet) {
            System.out.println(t.getName());
        }

      /*  for (int i =3; i>0; i--){
            System.out.println("countdown:"+i);
            Thread.sleep(500);
        }*/
    }

    public static void main(String[] args) {
    /*    t2 = new Train2();
        t2.start();

        t3 = new Train2();
        t3.start();*/


        launch();
    }
}