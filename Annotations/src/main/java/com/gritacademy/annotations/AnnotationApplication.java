package com.gritacademy.annotations;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*****************************
 * @author Alrik He
 * @version 1.0.0
 * @see java.util.Arrays
 * @since 1.0.0
 * @version 1.0.1
 *******************************/
public class AnnotationApplication extends Application {


    /**
     * <p>This is a simple description of the method. . .
     * <a href="http://www.supermanisthegreatest.com">Superman!</a>
     * </p>
     *
     * @param stage javafx stage
     * @see <a href="http://www.link_to_jira/HERO-402">HERO-402</a>
     * @since 1.0
     */
    @Override
    public void start(Stage stage) throws IOException, InvocationTargetException, IllegalAccessException {
        //  FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 420, 340);
        Group g = new Group();
        Scene scene = new Scene(g, 420, 340);
        stage.setTitle("JAVAFX UTAN SCENEBUILDER!");
        stage.setScene(scene);
        VBox vb = new VBox();
        vb.getChildren().add(new Button("check meta info stuff"));
        g.getChildren().add(vb);

        stage.show();

        runBrowser("http://stackoverflow.com"); //webbrowser


        //System.out.println(shuffleList("String", "hej","Alrik","HE","blabla"));
        //System.out.println(shuffleList(5,8,415256,789,415,6));

        Human h = new Human(50);
        getObjectFields(h); // reflect get fields
        getObjectMethods(h);
        setObject("hej" ,new Button());

        if (h.getClass().isAnnotationPresent(metaInfo.class)) {
            System.out.println("har annotation");

            for (Method m : h.getClass().getMethods()) {
                if (m.isAnnotationPresent(metaInfo.class))
                    System.out.println(m.getName());
            }
        }

    }

    /**
     * @param args default java parameter
     * @see <a href="http://specialportfolio.se">alriks portfolio</a>
     */
    public static void main(String[] args) {
        launch();
    }

    public  <E> List<E> shuffleList(E... data){ //generic
         final E type = data[0];
        List<E> l = new ArrayList<E>() {{addAll(List.of(data));}};

        Collections.shuffle(l);
        System.out.println("This type in the list is: "+type.getClass().getSimpleName());
        return l;
    }

    public <T,G> Object setObject(T obj,G secondObject){
        return obj;
    }
    public void runBrowser(String _url) throws IOException {
        Runtime rt = Runtime.getRuntime();
        String url = _url;
        if (System.getProperty("os.name").contains("Windows"))
            rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
        else  //mac
            rt.exec("open " + url);
    }


    public void getObjectFields( Object obj) throws IllegalAccessException {
        Field[] fields = obj.getClass().getDeclaredFields();
        fields[1].setAccessible(true);
        fields[1].set(obj,"surname");
        List<String> actualFieldNames = getFieldNames(fields);

        System.out.println(obj.getClass().getSimpleName()+" fields are "+ actualFieldNames);

    }
    // Alrik fixa metoder
     public void getObjectMethods( Object obj) {

        Method[] methods = obj.getClass().getMethods();



        System.out.println(Arrays.deepToString( methods));

    }



    private static List<String> getFieldNames(Field[] fields) {
        List<String> fieldNames = new ArrayList<>();
        for (Field field : fields)
            fieldNames.add( field.getName());
        return fieldNames;
    }
}