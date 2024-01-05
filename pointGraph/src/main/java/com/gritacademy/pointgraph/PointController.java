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
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.gritacademy.pointgraph.PointApplication.scene;
import static javafx.scene.text.Font.font;


public class PointController {
    public static final byte CONSTANT_THRESH_HOLD = 10;
    public static final double RAD_TO_DEG_FACTOR = 57.2957795131;
    public static final Point2D ORIGIN = new Point2D(0, 0);
    private short selectedNode;
    private Font notationFont = font("Nova Flat", FontWeight.BOLD, 28.0);
    private Font defaultFont = font("Nova Flat", 12);
    private double generalCurviture;
    private int averageX;
    private int averageY;
    private short amount;
    private float amountMultiplyFactor;
    private boolean focusOnPoint;
    private float zoom = 1f;

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

    public static byte POINT_SIZE = 20, POINT_RADIUS = (byte) (POINT_SIZE * 0.5);
    public static final double MAGNITUDE = 30;
    public static final double MAGNITUDE_FACTOR = 1 / MAGNITUDE;
    public static short canvasWidth = 1000;
    public static short canvasHalfWidth = (short) (canvasWidth * 0.5f);
    public static short canvasHeight = 600;
    public static short canvasHalfHeight = (short) (canvasHeight * 0.5f);
    String turn = "player1";
    private Point2D offset = new Point2D(0, 0);
    private Point2D mousePanCoordDiff = new Point2D(0, 0);
    private Point2D mousePanoriginCoord = new Point2D(0, 0);

    private Point2D invertedOffset = new Point2D(offset.getX() * -1, offset.getY() * -1);


    //final String[] MODE= {"DEFAULT","ORDERED_X","NO_LINES","ADD","PAN","MOVE"};
    private GraphicsContext gc, gf;
    private static List<Point2D> points = new ArrayList<>();
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

    @FXML
    private MenuBar menuBar;

    @FXML
    private CheckBox focusOnNewPoint;
    private int gap = 100;
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
        menuBar.getMenus().clear();
        Menu[] m = new Menu[]{new Menu("File"), new Menu("Edit")};
        menuBar.getMenus().addAll(m);
        MenuItem open = new MenuItem("Open file");
        //  new ImageView(new Image("menusample/new.png")));
        open.setOnAction(t -> {
            System.out.println("open file");
            try {
                fileChooser((Stage) (canvas.getScene().getWindow()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        MenuItem save = new MenuItem("Save file");
        //  new ImageView(new Image("menusample/new.png")));
        save.setOnAction(t -> {
            System.out.println("Save file");
            try {
                saveFile((Stage) (canvas.getScene().getWindow()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        m[0].getItems().addAll(open, save);

        gc = canvas.getGraphicsContext2D();

        // canvasWidth = (short) canvas.getWidth();
        // canvasHeight = (short) canvas.getHeight();
        //draw();
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,canvasWidth,canvasHeight);

        setGridDimensions();
        drawGrid();
/*        offset = new Point2D(canvasWidth * MAGNITUDE, canvasHeight * MAGNITUDE);
        hSlide.setValue(canvasWidth * 0.5);
        vSlide.setValue(canvasHeight * 0.5);*/
        choiceBox.setItems(allModes);
        choiceBox.setValue(Mode.DEFAULT);
        choiceBox.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Mode>() {
                    @Override
                    public void changed(ObservableValue<? extends Mode> observableValue, Mode mode, Mode selected) {
                        //System.out.println(selected);
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
    void focusOnPointCheck(ActionEvent event) {
        //focusOnNewPoint.setSelected(focusOnNewPoint.isSelected());
        focusOnPoint = focusOnNewPoint.isSelected();
        System.out.println("tick"); //doesnt work
    }

    @FXML
    void onCanvasClick(MouseEvent event) {
        //System.out.println(event.getButton());
        switch (event.getButton()) {
            case MouseButton.SECONDARY ->
                    deleteNode(new Point2D((event.getX() + invertedOffset.getX())/zoom, (canvasHeight - event.getY() + offset.getY())/zoom));
           // case MouseButton.PRIMARY -> {
        /*        if (mouseMode != Mode.MOVE) {
                    draw(new Point2D(event.getX() + invertedOffset.getX(), canvasHeight - event.getY() + offset.getY())); //creatar
                    System.out.println("created");
                }*/
          //  }
        }
    }

    @FXML
    void onCanvasDragDown(MouseEvent event) {
        switch (event.getButton()) {
            case MouseButton.PRIMARY -> {
                selectedNode(event);
                if (mouseMode == Mode.DEFAULT)
                    draw(new Point2D((event.getX() + invertedOffset.getX()) / zoom, (canvasHeight - event.getY() + offset.getY()) / zoom)); //creatar
            }
            case MouseButton.MIDDLE -> {
                //System.out.println("middle");
                mouseMode = Mode.PAN;
                scene.setCursor(Cursor.OPEN_HAND);
                mousePanoriginCoord = new Point2D((event.getX() + invertedOffset.getX()) / zoom, (invertedOffset.getY() + event.getY()) / zoom);
                System.out.println("Enter");
            }
        }
    }

    @FXML
    void onMouseDragged(MouseEvent event) {
        // System.out.println(mouseMode);

        if (mouseMode == Mode.MOVE) {
            if (selectedNode != -1) {
                points.set(selectedNode, new Point2D((event.getX() + invertedOffset.getX())/zoom , (canvasHeight - event.getY() + offset.getY())/zoom));
                draw();
            }
            // points.set(points.indexOf(selectedNode), points.get(points.indexOf(selectedNode)).add(10,10) );
            //System.out.println("Move");
        }
        if (mouseMode == Mode.PAN) {
            //if (event.getButton() == MouseButton.MIDDLE) {
            scene.setCursor(Cursor.CLOSED_HAND);
            offset = new Point2D(event.getX() - mousePanoriginCoord.getX()*zoom, event.getY() - mousePanoriginCoord.getY()*zoom);

            vSlide.setValue(offset.getY() * MAGNITUDE_FACTOR);
            hSlide.setValue(-offset.getX() * MAGNITUDE_FACTOR);

            invertedOffset = new Point2D(-offset.getX(), -offset.getY());
            draw();
        }
    }

    @FXML
    void onMouseMoveOnCanvas(MouseEvent event) {
        // System.out.println(event.getX()+":"+event.getY());

        scene.setCursor(Cursor.CROSSHAIR);
        for (Point2D p : points)
            if (p.distance(new Point2D((event.getX() + invertedOffset.getX()) / zoom, (canvasHeight - event.getY() + offset.getY()) / zoom)) < POINT_RADIUS * zoom) {
                // canvas.setTooltip(new Tooltip("Tooltip for Button"));
                //System.out.println(p);
                tooltip.setText(event.getX() + ":" + event.getY());
                scene.setCursor(Cursor.HAND); // hover
                break;
            }
        draw(null);
        //  gc.scale(1, -1);
        //mouse coords
        gc.fillText("" + (int) ((event.getX() - offset.getX())/zoom) + ":" + (int) ((canvasHeight - event.getY() + offset.getY())/zoom), event.getX() + POINT_RADIUS, event.getY() + POINT_RADIUS);
        // gc.scale(1, -1);
    }

    @FXML
    void onCanvasDragReleased(MouseEvent event) {
        switch (mouseMode) {
            case Mode.MOVE -> {
                // selectedNode = new Point2D(event.getX() + offset.getX(), event.getX() + offset.getY());
                // if(selectedNode!=null) points.set(points.indexOf(selectedNode), new Point2D(event.getX() + invertedOffset.getX()+50, canvasHeight - event.getY() + offset.getY()+50));
                System.out.println("released point");
                if (mouseMode == Mode.MOVE) {
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
        Double hVal = -hSlide.getValue() * MAGNITUDE;
        System.out.println("slide H " + hVal);
        offset = new Point2D(hVal, offset.getY());
        invertedOffset = new Point2D(-hVal, -offset.getY());
        draw();
    }

    @FXML
    void onCanvasScroll(ScrollEvent event) {
        zoom *= 1 + event.getDeltaY() * 0.003;
        zoom = Math.clamp(zoom, 0.1f, 10f);
        if (0.95 < zoom && zoom < 1.05) zoom = 1;
        POINT_SIZE = (byte) (20 * zoom);
        POINT_RADIUS = (byte) (POINT_SIZE * 0.5);

        setGridDimensions();
        System.out.println(zoom);
        draw();
    }


    void setGridDimensions() {
        gap = (int) (100 * zoom);
        row = (short) Math.floor(canvasHeight / gap);
        col = (short) Math.floor(canvasWidth / gap);
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
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);
        drawGrid();

    }


    void draw(Point2D point) {
        if (point != null) {
            switch (choiceBox.getValue()) {
                case ORDERED_X -> {
                    points.add(point);
                    points.sort((o1, o2) -> (int) (o1.getX() - o2.getX()));
                    //do while??
                    /*short i = 0;
                    boolean higherThenBefore = false;
                     if (points.size() == 0) points.add(point);
                   else
                        do {

                  if (higherThenBefore) {
                            if (points.get(i).getX() >= point.getX())
                                points.add(i, point);

                        } else if (points.get(i).getX() < point.getX())
                            higherThenBefore = true;


                        i++;

                        if (i == points.size() || points.size() == 0) {
                            points.add(point);
                            break;
                        } else if (1 == points.size()) {
                            points.add((points.get(i).getX() > point.getX()) ? 0 : 1, point);
                            break;
                        } else if (points.get(i - 1).getX() < point.getX() && point.getX() <= points.get(i).getX()) {
                            points.add(i, point);
                            break;
                        }//else points.add(i-1, point);
                    } while (i < points.size());
*/
                }
                case ORDERED_Y -> points.add(point);
                case DEFAULT -> points.add(point);
            }
            if (focusOnPoint) setViewLocation(point);
        }
        draw();
    }

    void drawBackground() {
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.GREY);
        gc.fillRect(0, 0, canvasWidth, canvasHeight);
    }

    void draw() {
        drawBackground();
        drawGrid();
        if (!points.isEmpty()) {
            analyzePoints();
            checkConstantTC();
            checkLinearTC();
        }
        gc.translate(offset.getX(), canvasHeight + offset.getY());
        gc.scale(1 * zoom, -1 * zoom);

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

        gc.scale(1 / zoom, -1 / zoom);
        gc.translate(invertedOffset.getX(), -canvasHeight + invertedOffset.getY());
    }

    void drawGrid() {
        gc.setStroke(Color.LIGHTGRAY);
        gc.beginPath();

        for (byte i = -1; i <= row; i++) {//row
            drawLine(0 - gap, canvasWidth + gap, i * gap, i * gap);
            gc.strokeText(gap * (i + (int) ((offset.getY()) / gap)) + "", 0 + 5 * zoom, canvasHeight - i * gap + (offset.getY() % gap) + 10 * zoom);
        }
        for (byte j = -1; j <= col; j++) {   // col
            drawLine(j * gap, j * gap, 0 - gap, canvasHeight + gap);
            gc.strokeText(gap * (j + (int) (invertedOffset.getX() / gap)) + "", j * gap + offset.getX() % gap + 5 * zoom, canvasHeight - 10 * zoom);
        }

        gc.stroke();
    }

    private void drawLine(int x, int x2, int y, int y2) {
        gc.moveTo(x + offset.getX() % gap, canvasHeight - y + offset.getY() % gap);
        gc.lineTo(x2 + offset.getX() % gap, canvasHeight - y2 + offset.getY() % gap);
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
        if (mouseMode != Mode.MOVE) for (short i = 0; i < points.size(); i++)
            if (points.get(i).distance((event.getX()+ invertedOffset.getX())/zoom , (canvasHeight - event.getY() + offset.getY())/zoom  )< POINT_RADIUS *zoom) {
                selectedNode = i;
                mouseMode = Mode.MOVE;
                 System.out.println("selected point !!!!!");
                break;
            }
    }

    void analyzePoints() {
        generalCurviture = 0.0d;
        averageY = 0;
        averageX = 0;
        amount = (short) points.size();
        amountMultiplyFactor = (float) 1 / (amount - 1);
        Point2D pastP = null;
        for (Point2D p : points) {
            if (pastP != null) {
                generalCurviture += Math.atan2(p.getY() - pastP.getY(), p.getX() - pastP.getX());
            }
            averageX += p.getX();
            averageY += p.getY();
            pastP = p;
        }

        generalCurviture *= amountMultiplyFactor;
        //generalCurviture /= amount-1;

        averageY /= amount/zoom;
        averageX /= amount/zoom;
        generalCurviture *= RAD_TO_DEG_FACTOR;
        //generalCurviture *= 180/Math.PI;

        gc.beginPath();
        gc.setFill(Color.BLACK);
        if (!Double.isNaN(generalCurviture))
            gc.fillText(String.format("%.2f", generalCurviture) + "° degrees", canvasWidth - 160, 20);
        gc.stroke();
    }

    void checkConstantTC() {

        //System.out.println("Total diviation : " + diviatedTotalAngles);
        // System.out.println("average diviation : " + averageDiviation);
        //gc.beginPath();
        // gc.setFill(Color.BLACK);
        // gc.fillText(String.format("%.2f", generalCurviture) + "° angle", canvasWidth - 160, 20);
        //gc.stroke();

        //gc.setFont(new Font());
        if (generalCurviture < CONSTANT_THRESH_HOLD && generalCurviture > -CONSTANT_THRESH_HOLD) {
            gc.beginPath();
            gc.setTextAlign(TextAlignment.RIGHT);
            gc.setFont(notationFont);
            gc.setFill(Color.LIMEGREEN);
            gc.fillText("O(1) - Constant time complexity", canvasWidth - 40, 50);

            gc.setStroke(Color.LIMEGREEN);
            gc.setLineWidth(3);
            gc.setTextAlign(TextAlignment.LEFT);
            gc.moveTo(0, canvasHeight + offset.getY() - averageY);
            gc.lineTo(canvasWidth, canvasHeight + offset.getY()- averageY);
            gc.stroke();
        }
        drawAllComplexities();

        gc.beginPath();
        gc.setFont(defaultFont);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(1);


    }

    void checkLinearTC() {
        //float angle = 0.0f;

        float diviatedTotalAngles = 0.0f, averageDiviation = 0.0f;
        Point2D pastP = null;
        for (Point2D p : points) {
            if (pastP != null) {
                float angle = (float) (Math.atan2(p.getY() - pastP.getY(), p.getX() - pastP.getX()) * RAD_TO_DEG_FACTOR);
                // System.out.println(angle + "p : "+ generalCurviture );
                diviatedTotalAngles += Math.abs(angle - generalCurviture);
            }
            pastP = p;
        }
        averageDiviation = diviatedTotalAngles / amount;


        //gc.setFont(new Font());
        if (generalCurviture < 90 && generalCurviture > CONSTANT_THRESH_HOLD && averageDiviation < CONSTANT_THRESH_HOLD && averageDiviation > -CONSTANT_THRESH_HOLD) {
            gc.beginPath();
            gc.setTextAlign(TextAlignment.RIGHT);
            gc.setFont(notationFont);
            gc.setFill(Color.YELLOWGREEN);
            gc.fillText("O(n) - Linear time complexity", canvasWidth - 40, 50);

            gc.setStroke(Color.YELLOWGREEN);
            gc.setTextAlign(TextAlignment.LEFT);
            gc.setLineWidth(3);
            gc.moveTo(averageX + Math.cos(Math.toRadians(generalCurviture)) * -3000 + offset.getX(), -averageY - (Math.sin(Math.toRadians(generalCurviture)) * -3000) + canvasHeight + offset.getY());
            gc.lineTo(averageX + Math.cos(Math.toRadians(generalCurviture)) * 3000 + offset.getX(), -averageY - (Math.sin(Math.toRadians(generalCurviture)) * 3000) + canvasHeight + offset.getY());
            gc.stroke();
        }

        gc.beginPath();
        gc.setFont(defaultFont);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(1);

    }

    void drawAllComplexities() {
        final int pointOfPlateau = canvasWidth, PlateauLevel = 350, Plateau_precipice = 0;
        float angle = 25.0f;
        Point2D origin = new Point2D(0, 0);
        gc.beginPath();
        gc.setStroke(Color.GOLD);
        gc.setLineWidth(3);
        drawFormatedBezierCurve((int) origin.getX(), (int) origin.getY(), Plateau_precipice, PlateauLevel, pointOfPlateau, PlateauLevel);
        gc.moveTo(pointOfPlateau + offset.getX(), -PlateauLevel + canvasHeight + offset.getY());
        gc.lineTo(pointOfPlateau + offset.getX() + 3000, -PlateauLevel + canvasHeight + offset.getY());
        gc.stroke();
/*
        gc.beginPath();
        gc.setStroke(Color.YELLOWGREEN);
        gc.moveTo(origin.getX() + offset.getX(), -origin.getY() + canvasHeight + offset.getY());
        gc.lineTo(Math.cos(Math.toRadians(angle)) * 3000 + offset.getX(), -(Math.sin(Math.toRadians(angle)) * 3000) + canvasHeight + offset.getY());
        gc.stroke();
*/
    }

    void drawFormatedBezierCurve(int v, int v1, int v2, int v3, int v4, int v5) {
        gc.bezierCurveTo(v + offset.getX(), -v1 + canvasHeight + offset.getY(), v2 + offset.getX(), -v3 + canvasHeight + offset.getY(), v4 + offset.getX(), -v5 + canvasHeight + offset.getY());
    }

    void setViewLocation(Point2D point) {
        System.out.println(canvasHalfHeight);
        offset = new Point2D(-point.getX()*zoom + canvasHalfWidth, point.getY()*zoom - canvasHalfHeight);
        invertedOffset = new Point2D(offset.getX() * -1, offset.getY() * -1);
    }

    public static void fileChooser(Stage stage) throws IOException {
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

        if (!points.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "loading file will clear all current points ", new ButtonType("cancel", ButtonBar.ButtonData.CANCEL_CLOSE));
            alert.showAndWait();

        }
        File file = fileC.showOpenDialog(stage.getScene().getWindow());
        FileReader fr;
        if (file != null) {

            char[] data = new char[(int) file.length()];
            fr = new FileReader(file);
            try {
                //fr= new FileReader(file);
                if (file != null) {
                    fr.read(data);
                    String sData = String.valueOf(data);
                    points.clear();
                    if (file.getCanonicalPath().endsWith(".csv")) {
                        String[] dataArray = sData.split("\n");
                        for (String s : dataArray) {
                            String[] temp = s.split(",");
                            try {
                                Point2D p = new Point2D(Double.parseDouble(temp[0]), Double.parseDouble(temp[1]));
                                System.out.println(p);
                                points.add(p); //add to point list
                            } catch (NumberFormatException ne) {
                                System.out.println("not a number");
                            }
                        }

                    } else if (file.getCanonicalPath().endsWith(".json")) {
                        String dataString = sData.replaceAll("\n", "");
                        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
                        Matcher m = pattern.matcher(dataString);
                        m.find();
                        String mark = m.group();
                        System.out.println(mark);
                        Pattern inside = Pattern.compile("\\{(.*?)\\}");
                        m = inside.matcher(mark);
                        while (m.find()) {
                            String point = m.group();
                            Matcher mxy = Pattern.compile("\\\"x\\\":(.+?), \\\"y\\\":(.+?)}").matcher(point);
                            mxy.find();
                            System.out.println(mxy.group(1) + ":" + mxy.group(2));
                            points.add(
                                    new Point2D(Double.parseDouble(mxy.group(1)), Double.parseDouble(mxy.group(2)))
                            );
                        }


                    /*    for (String s : dataArray) {


                            String[] temp = s.split(",");
                            try {
                                Point2D p = new Point2D(Double.parseDouble(temp[0]), Double.parseDouble(temp[1]));
                                System.out.println(p);
                                points.add(p); //add to point list
                            } catch (NumberFormatException ne) {
                                System.out.println("not a number");
                            }
                        }*/
                        System.out.println("json!!!!E");
                    }
                } else {
                    System.out.println("NO file selected!!!"); // or something else
                }
            } finally {
                fr.close();
            }
        }
    }

    public static void saveFile(Stage stage) throws IOException {
        FileChooser fileC = new FileChooser();

        fileC.setInitialDirectory(new File("src")); // init path annars C
        fileC.setTitle("Save File");

        fileC.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TABLE FILES", "*.csv", "*.json", "*.xml"),
                new FileChooser.ExtensionFilter("csv", "*.csv"),
                new FileChooser.ExtensionFilter("json", "*.json"),
                new FileChooser.ExtensionFilter("xml", "*.xml"),
                new FileChooser.ExtensionFilter("ALL FILES", "*.*")
        );

        File file = fileC.showSaveDialog(stage.getScene().getWindow());
        if (file.getCanonicalPath().endsWith(".csv")) {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("x,y\n");
            for (Point2D p : points) {
                fileWriter.write(p.getX() + "," + p.getY() + "\n");
            }
            fileWriter.close();
        }
        if (file.getCanonicalPath().endsWith(".json")) {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("{\n\t\"points\":[\n");
            for (Point2D p : points) {
                fileWriter.write("\t\t{\"x\":" + (int) p.getX() + ", \"y\":" + (int) p.getY() + "}");
                if (points.getLast() == p) fileWriter.write("\n");
                else fileWriter.write(",\n");
            }
            fileWriter.write("\t]\n}");
            fileWriter.close();
        }
    }
}
