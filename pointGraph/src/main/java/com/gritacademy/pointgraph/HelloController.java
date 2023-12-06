package com.gritacademy.pointgraph;


import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.gritacademy.pointgraph.HelloApplication.scene;


public class HelloController {

    String turn = "player1";

    class Alrik { //nested

        int age = 31;
        String name = "Alrik";

        Stuff alriksSaker = Stuff.EQUIPMENT;


        public Alrik(int age, String name) {
            this.age = age;
            this.name = name;
            alriksSaker = Stuff.BOOKS;
            System.out.println(alriksSaker + "hej");
            System.out.println(alriksSaker.name());
            switch (alriksSaker) {
                case BOOKS -> System.out.println("bara böcker nu!!");
                case EQUIPMENT -> System.out.println("bara böcker nu!!");
                case GEAR -> System.out.println("bara böcker nu!!");
            }
        }
    }


    //final String[] MODE= {"DEFAULT","ORDERED_X","NO_LINES","ADD","PAN","MOVE"};
    private GraphicsContext gc;
    private List<Point2D> points = new ArrayList<>();
    @FXML
    private Canvas canvas;

    @FXML
    private Slider hSlide;

    @FXML
    private TextField insertTextFieldX;

    @FXML
    private TextField insertTextFieldY;

    @FXML
    private Slider vSlide;

    @FXML
    private Label welcomeText;
    final private int gap = 100;
    private int row = 10, col = 10; // auto assign
    private ObservableList<Mode> allModes = FXCollections.observableArrayList(Mode.DEFAULT, Mode.ORDERED_X, Mode.ORDERED_Y, Mode.NO_LINES);
    @FXML
    private ChoiceBox<Mode> choiceBox;
    private Tooltip tooltip = new Tooltip("Tooltip Text");
    private Turn currentTurn = Turn.PLAYER_2;

    @FXML
    private void initialize() {

        List<String> arry = new ArrayList<>();
        arry.add("hej");
    /*    if(turn=="player1"){
            // ritar X på cell
            turn="player2";
        } else if(turn=="player2"){
            turn="player1";
        }*/

/*        if(turn.equals("player1") ){
            // ritar X på cell
            turn="player2";
        } else {
            turn="player1";
        }*/

        arry.add("tjenare");
        arry.add("hejsan");

        arry.add("tjenare");
        arry.add("hejsan");

        for (String s :arry) {
            System.out.println(s);
        }
        if (currentTurn.ordinal() % 2 == 0)
            System.out.println("player1 " + currentTurn);
        else
            System.out.println("player2 " + currentTurn);


        arry.add("tjenare");
        arry.add("hejsan");


        Alrik a = new Alrik(29, "alle");
        a.age = 31;

        choiceBox.setItems(allModes);
        choiceBox.setValue(Mode.DEFAULT);
        choiceBox.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Mode>() {
                    @Override
                    public void changed(ObservableValue<? extends Mode> observableValue, Mode mode, Mode selected) {
                        System.out.println(selected);

                    }
                }
        );

    }

    public HelloController() {
        canvas = new Canvas(1000, 600);
        canvas.setVisible(true);
        System.out.println("init canvas");
        //Tooltip.install(canvas, tooltip);

    }


    @FXML
    void onChoiceSelected(ContextMenuEvent event) {
        System.out.println("SELECTED MODE");
    }

    @FXML
    void onCanvasClick(MouseEvent event) {
        System.out.println(event.getButton());
        if (event.getButton() == MouseButton.SECONDARY)
            deleteNode(new Point2D(event.getX(), canvas.getHeight() - event.getY()));
        else
            draw(new Point2D(event.getX(), canvas.getHeight() - event.getY()));
    }

    @FXML
    void moveHorizontal(MouseEvent event) {
        System.out.println("slide H " + hSlide.getValue());
    }

    @FXML
    void moveVertical(MouseEvent event) {
        System.out.println("slide V " + vSlide.getValue());
    }

    @FXML
    void onChangeX() {
        String temp = insertTextFieldX.getText();
        if (!temp.matches("\\d\\.*")) {
     

    @FXML
    void onChangeY() {
        String temp = insertTextFieldY.getText();
        if (!temp.matches("\\d\\.*")) {
            insertTextFieldY.setText(temp.replaceAll("[^\\d\\.]", ""));
            insertTextFieldY.deselect();
            insertTextFieldY.positionCaret(insertTextFieldY.getLength());
        }
    }

    @FXML
    void onMouseMoveOnCanvas(MouseEvent event) {
        // System.out.println(event.getX()+":"+event.getY());
        final int CANVAS_H = (int) canvas.getHeight();
        scene.setCursor(Cursor.DEFAULT);
        for (Point2D p : points)
            if (p.distance(new Point2D(event.getX(), CANVAS_H - event.getY())) < 10) {
                // canvas.setTooltip(new Tooltip("Tooltip for Button"));
                System.out.println(p);
                tooltip.setText(event.getX() + ":" + event.getY());
                scene.setCursor(Cursor.HAND);
                break;
            }
    }

    @FXML
    void onInsert(ActionEvent event) {
        try {
            float valx = Float.parseFloat(insertTextFieldX.getText());
            float valy = Float.parseFloat(insertTextFieldY.getText());
            System.out.println(valx + " : " + valy);
            draw(new Point2D(Float.parseFloat(insertTextFieldX.getText()), Float.parseFloat(insertTextFieldY.getText())));
        } catch (NumberFormatException n) {
            System.out.println(n);
        }
    }

    @FXML
    void onReset(ActionEvent event) {
        points.clear();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    void draw(Point2D point) {
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.translate(0, canvas.getHeight());
        gc.scale(1, -1);
        drawGrid();

        gc.setFill(Color.GREY);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1.0);

        if (point != null) points.add(point);
        Point2D pastP = null;
        for (Point2D p : points) {
            gc.beginPath();

            if (pastP != null) {
                gc.moveTo(pastP.getX(), pastP.getY());
                gc.lineTo(p.getX(), p.getY());
            }
            pastP = p;
            gc.strokeOval(p.getX() - 5, p.getY() - 5, 10, 10);
            gc.stroke();
        }
        gc.scale(1, -1);
        gc.translate(0, -canvas.getHeight());


    }

    void drawGrid() {

        gc.setStroke(Color.LIGHTGRAY);
        gc.beginPath();
        for (int i = 0; i < row; i++) {
            gc.moveTo(0, i * gap);
            gc.lineTo(1000, i * gap);
        }
        for (int j = 0; j < col; j++) {
            gc.moveTo(j * gap, 0);
            gc.lineTo(j * gap, 700);
        }
        gc.stroke();
    }

    void deleteNode(Point2D target) {
        Point2D targetToRemove;
        for (Point2D p : points)
            if (p.distance(target) < 15) {
                targetToRemove = p;
                points.remove(p);
                break;
            }
        draw(null);
    }

}
