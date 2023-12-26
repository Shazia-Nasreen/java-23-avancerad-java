package com.gritacademy.pointgraph;


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
import javafx.scene.text.Font;

import java.util.*;

import static com.gritacademy.pointgraph.PointApplication.scene;


public class PointController {
    public static final byte CONSTANT_THRESH_HOLD = 10;
    private int selectedNode;

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

    public static final byte POINT_SIZE = 20, POINT_RADIUS = (int) (POINT_SIZE * 0.5);
    public static final double MAGNITUDE = 30;
    public static final double MAGNITUDE_FACTOR = 1 / MAGNITUDE;
    public static short canvasWidth = 1000;
    public static short canvasHeight = 600;
    String turn = "player1";
    private Point2D offset = new Point2D(0, 0);
    private Point2D mousePanCoordDiff = new Point2D(0, 0);
    private Point2D mousePanoriginCoord = new Point2D(0, 0);

    private Point2D invertedOffset = new Point2D(offset.getX() * -1, offset.getY() * -1);


    //final String[] MODE= {"DEFAULT","ORDERED_X","NO_LINES","ADD","PAN","MOVE"};
    private GraphicsContext gc, gf;
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
    private int row, col; // auto assign
    private ObservableList<Mode> allModes = FXCollections.observableArrayList(Mode.DEFAULT, Mode.ORDERED_X, Mode.ORDERED_Y, Mode.NO_LINES);

    private Mode mouseMode = Mode.DEFAULT;
    @FXML
    private ChoiceBox<Mode> choiceBox;
    private Tooltip tooltip = new Tooltip("Tooltip Text");
    private Turn currentTurn = Turn.PLAYER_2;

    private int amountOfGuesses = 2147483647;
    private byte amountOfGuesses2 = 127; // 128 -> 1 byte -> 8 bits  -> 128 64 32 16 8 4 2 1
    private short amountOfGuesses3 = 32767;  //32768 -> -> 2 bytes -> 16 bits ->  ... 256 128 64 32 16 8 4 2 1
    private boolean noLines = false;

    @FXML
    private void initialize() {

  /*      List<String> arry = new ArrayList<>();
        arry.add("hej");

        int i = 0;
        while (i < 10)
            System.out.println(++i);

        Mode mode = ADD;


        for (String s : arry)
            while (i < 3)
                if (i == 0)
                    System.out.println("first");
                else
                    System.out.println("other");

        System.out.println("SLEEP baby");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        System.out.println("proceed");
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

   /*     arry.add("tjenare");
        arry.add("hejsan");

        arry.add("tjenare");
        arry.add("hejsan");

        for (String s : arry) {
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
*/
        gc = canvas.getGraphicsContext2D();
        canvasWidth = (short) canvas.getWidth();
        canvasHeight = (short) canvas.getHeight();

        row = (short) Math.floor(canvasHeight / gap);
        col = (short) Math.floor(canvasWidth / gap);
/*        offset = new Point2D(canvasWidth * MAGNITUDE, canvasHeight * MAGNITUDE);
        hSlide.setValue(canvasWidth * 0.5);
        vSlide.setValue(canvasHeight * 0.5);*/
        choiceBox.setItems(allModes);
        choiceBox.setValue(Mode.DEFAULT);
        choiceBox.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Mode>() {
                    @Override
                    public void changed(ObservableValue<? extends Mode> observableValue, Mode mode, Mode selected) {
                        System.out.println(selected);
                        noLines = false;
                        switch (selected) {
                            case ORDERED_X -> points.sort((o1, o2) -> (int) (o1.getX() - o2.getX()));
                            case ORDERED_Y -> points.sort((o1, o2) -> (int) (o1.getY() - o2.getY()));
                            case NO_LINES -> noLines = true;
                        }
                        draw();
                    }
                }
        );

    }

    public PointController() {
        canvas = new Canvas(1000, 600);
        canvas.setVisible(true);
        System.out.println("init canvas");
        //Tooltip.install(canvas, tooltip);
    }

    @FXML
    void onKeySubmit(KeyEvent event) {
        onInsert(null);
        insertTextFieldX.requestFocus();
        insertTextFieldX.selectAll();
        insertTextFieldX.positionCaret(0);
    }

    @FXML
    void onChoiceSelected(ContextMenuEvent event) {
        System.out.println("SELECTED MODE"); //doesnt work
    }

    @FXML
    void onCanvasClick(MouseEvent event) {
        //System.out.println(event.getButton());
        switch (event.getButton()) {
            case MouseButton.SECONDARY ->
                    deleteNode(new Point2D(event.getX() + invertedOffset.getX(), canvasHeight - event.getY() + offset.getY()));
            case MouseButton.PRIMARY -> {
        /*        if (mouseMode != Mode.MOVE) {
                    draw(new Point2D(event.getX() + invertedOffset.getX(), canvasHeight - event.getY() + offset.getY())); //creatar
                    System.out.println("created");
                }*/
            }
        }
    }

    @FXML
    void onCanvasDragDown(MouseEvent event) {
        switch (event.getButton()) {
            case MouseButton.PRIMARY -> {
                selectedNode(event);
                if (mouseMode == Mode.DEFAULT)
                    draw(new Point2D(event.getX() + invertedOffset.getX(), canvasHeight - event.getY() + offset.getY())); //creatar

            }
            case MouseButton.MIDDLE -> {
                //System.out.println("middle");
                mouseMode = Mode.PAN;
                scene.setCursor(Cursor.OPEN_HAND);
                mousePanoriginCoord = new Point2D(invertedOffset.getX() + event.getX(), invertedOffset.getY() + event.getY());
                System.out.println("Enter");
            }
        }
    }

    @FXML
    void onMouseDragged(MouseEvent event) {
        System.out.println(mouseMode);

        if (mouseMode == Mode.MOVE) {
            System.out.println(points.indexOf(selectedNode) + " DRAGGED");
            if (selectedNode != -1) {
                points.set(selectedNode, new Point2D(event.getX() + invertedOffset.getX(), canvasHeight - event.getY() + offset.getY()));

                draw();
            }
            // points.set(points.indexOf(selectedNode), points.get(points.indexOf(selectedNode)).add(10,10) );
            System.out.println("Move");
        }
        if (mouseMode == Mode.PAN) {
            //if (event.getButton() == MouseButton.MIDDLE) {
            scene.setCursor(Cursor.CLOSED_HAND);
            offset = new Point2D(event.getX() - mousePanoriginCoord.getX(), event.getY() - mousePanoriginCoord.getY());

            vSlide.setValue(offset.getY() * MAGNITUDE_FACTOR);
            hSlide.setValue(offset.getX() * MAGNITUDE_FACTOR);

            invertedOffset = new Point2D(-offset.getX(), -offset.getY());
            draw();
        }
    }

    @FXML
    void onMouseMoveOnCanvas(MouseEvent event) {
        // System.out.println(event.getX()+":"+event.getY());

        scene.setCursor(Cursor.CROSSHAIR);
        for (Point2D p : points)
            if (p.distance(new Point2D(event.getX() + invertedOffset.getX(), canvasHeight - event.getY() + offset.getY())) < POINT_RADIUS) {
                // canvas.setTooltip(new Tooltip("Tooltip for Button"));
                System.out.println(p);
                tooltip.setText(event.getX() + ":" + event.getY());
                scene.setCursor(Cursor.HAND);
                break;
            }
        draw(null);
        //  gc.scale(1, -1);
        gc.fillText("" + (int) (event.getX() - offset.getX()) + ":" + (int) (canvasHeight - event.getY() + offset.getY()), event.getX() + POINT_RADIUS, event.getY() + POINT_RADIUS);
        // gc.scale(1, -1);
    }

    @FXML
    void onCanvasDragReleased(MouseEvent event) {
        switch (mouseMode) {
            case Mode.MOVE -> {
                // selectedNode = new Point2D(event.getX() + offset.getX(), event.getX() + offset.getY());
                // if(selectedNode!=null) points.set(points.indexOf(selectedNode), new Point2D(event.getX() + invertedOffset.getX()+50, canvasHeight - event.getY() + offset.getY()+50));
                System.out.println("released point");
                if(mouseMode == Mode.MOVE){

                    switch (choiceBox.getValue()) {
                        case ORDERED_X -> points.sort((o1, o2) -> (int) (o1.getX() - o2.getX()));
                        case ORDERED_Y -> points.sort((o1, o2) -> (int) (o1.getY() - o2.getY()));
                    }
                }
                selectedNode = -1;
                mouseMode = Mode.DEFAULT;
            }
            case Mode.PAN -> {
                //if (event.getButton() == MouseButton.MIDDLE) {
                System.out.println("released");
                scene.setCursor(Cursor.CROSSHAIR);
                mouseMode = Mode.DEFAULT;
            }

        }
    }

    @FXML
    void onCanvasExit() {
        scene.setCursor(Cursor.DEFAULT);

    }

    @FXML
    void moveHorizontal(MouseEvent event) {
        Double hVal = hSlide.getValue() * MAGNITUDE;
        System.out.println("slide H " + hVal);
        offset = new Point2D(hVal, offset.getY());
        invertedOffset = new Point2D(-hVal, -offset.getY());
        draw();
    }

    @FXML
    void moveVertical(MouseEvent event) {
        Double vVal = vSlide.getValue() * MAGNITUDE;
        System.out.println("slide V " + vVal);
        offset = new Point2D(offset.getX(), vVal);

        invertedOffset = new Point2D(-offset.getX(), -vVal);
        draw();
    }

    @FXML
    void onChangeX() {
        String temp = insertTextFieldX.getText();
        if (!temp.matches("\\d\\.*")) {
            insertTextFieldX.setText(temp.replaceAll("[^\\d\\.]", ""));
            insertTextFieldX.deselect();
            insertTextFieldX.positionCaret(insertTextFieldX.getLength());
        }
    }

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
        gc.fillRect(0, 0, canvasWidth, canvasHeight);
    }


    void draw(Point2D point) {
        if (point != null)
            switch (choiceBox.getValue()) {
                case ORDERED_X -> {
                    //do while??
                    short i = 0;
                    do {
                        i++;
                        if (i == points.size() || points.size() < 1) {
                            points.add(point);
                            break;
                        } else if (1 == points.size()) {
                            points.add((points.get(i).getX() > point.getX()) ? 0 : 1, point);
                            break;
                        } else if (points.get(i - 1).getX() < point.getX() && point.getX() <= points.get(i).getX()) {
                            points.add(i, point);
                            break;
                        }
                    } while (i < points.size());

                }
                case ORDERED_Y -> points.add(point);
                case DEFAULT -> points.add(point);
            }

        draw();

    }

    void draw() {


        gc.setFill(Color.WHITE);
        gc.setStroke(Color.GREY);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);
        drawGrid();
        if (!points.isEmpty()) checkConstantTC();

        gc.translate(offset.getX(), canvasHeight + offset.getY());
        gc.scale(1, -1);

        gc.setFill(Color.GREY);
        gc.setStroke(Color.BLACK);

        Point2D pastP = null;
        if (!points.isEmpty()) {
            for (Point2D p : points) {
                gc.beginPath();
                if (!noLines) {
                    if (pastP != null) {
                        gc.moveTo(pastP.getX(), pastP.getY());
                        gc.lineTo(p.getX(), p.getY());
                        gc.stroke();
                    }
                    gc.scale(1, -1);
                    gc.fillText("" + (int) p.getX() + ":" + (int) p.getY(), p.getX() + POINT_RADIUS, +canvasHeight - p.getY() - canvasHeight + POINT_RADIUS);
                    gc.scale(1, -1);

                }
                pastP = p;
                gc.strokeOval(p.getX() - POINT_RADIUS, p.getY() - POINT_RADIUS, POINT_SIZE, POINT_SIZE);
            }
            gc.fillOval(points.getLast().getX() - POINT_RADIUS, points.getLast().getY() - POINT_RADIUS, POINT_SIZE, POINT_SIZE);
            // gc.fillText("" + points.getLast().getX() + ":" + points.getLast().getY(), points.getLast().getX(), points.getLast().getY());

        }

        gc.scale(1, -1);
        gc.translate(invertedOffset.getX(), -canvasHeight + invertedOffset.getY());
    }

    void drawGrid() {
        gc.setStroke(Color.LIGHTGRAY);
        gc.beginPath();

        for (byte i = -1; i <= row; i++) {//row
            drawLine(0 - gap, canvasWidth + gap, i * gap, i * gap);
            gc.strokeText(gap * (i + (int) ((offset.getY()) / gap)) + "", 0 + 5, canvasHeight - i * gap + offset.getY() % gap + 10);
        }
        for (byte j = -1; j <= col; j++) {   // col
            drawLine(j * gap, j * gap, 0 - gap, canvasHeight + gap);
            gc.strokeText(gap * (j + (int) (invertedOffset.getX() / gap)) + "", j * gap + offset.getX() % gap + 5, canvasHeight - 10);
        }

        gc.stroke();
    }

    private void drawLine(int x, int x2, int y, int y2) {
        gc.moveTo(x + offset.getX() % gap, y + offset.getY() % gap);
        gc.lineTo(x2 + offset.getX() % gap, y2 + offset.getY() % gap);
    }
    /* private void drawLine(int x, int x2, int y, int y2) {
        gc.moveTo(x - offset.getX() + offset.getX() % gap + gap, y + offset.getY() + offset.getY() % gap + gap);
        gc.lineTo(x2 - offset.getX() + offset.getX() % gap + gap, y2 + offset.getY() % gap + gap);
    }*/


    void deleteNode(Point2D target) {
        for (Point2D p : points)
            if (p.distance(target) < POINT_RADIUS) {
                points.remove(p);
                break;
            }
        draw();
    }

    void selectedNode(MouseEvent event) {
        if (mouseMode != Mode.MOVE) for (int i = 0; i < points.size(); i++)
            if (points.get(i).distance(event.getX() + invertedOffset.getX(), canvasHeight - event.getY() + offset.getY()) < POINT_RADIUS) {
                selectedNode = i;
                mouseMode = Mode.MOVE;
                System.out.println("selected point !!!!!");
                break;
            }
    }

    void checkConstantTC() {
        double generalCurviture = 0.0d;
        int averageY = 0;
        short amount = (short) points.size();
        Point2D pastP = null;
        for (Point2D p : points) {
            if (pastP != null) {
                generalCurviture += Math.atan2(p.getY() - pastP.getY(), p.getX() - pastP.getX());
            }
            averageY += p.getY();

            pastP = p;
        }
        generalCurviture /= amount;
        averageY /= amount;

        generalCurviture *= 360 / Math.PI;
        System.out.println(" general curviture is : " + generalCurviture);
        gc.beginPath();
        gc.setStroke(Color.BLACK);

        gc.strokeText(Math.round(generalCurviture * 100) * 0.01 + "°", canvasWidth - 160, 20);
        gc.stroke();

        //gc.setFont(new Font());
        if (generalCurviture < CONSTANT_THRESH_HOLD && generalCurviture > -CONSTANT_THRESH_HOLD) {
            gc.beginPath();
            gc.strokeText("O(1)", canvasWidth - 160, 50);

            gc.setStroke(Color.LIMEGREEN);
            gc.setLineWidth(3);
            gc.moveTo(0, canvasHeight - averageY + offset.getY());
            gc.lineTo(canvasWidth, canvasHeight - averageY + offset.getY());
            gc.stroke();
        }
        gc.beginPath();

        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(1);


    }

}
