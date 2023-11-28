import com.eclipsesource.json.Json;

import javax.swing.*;
import java.util.ArrayList;

public class Main {

    Json j;

    public static void main(String[] args) {

        //Organism o = new Organism() ;
        Human h= new Human();
        Human h2= new Human();
        Human h3= new Human();
        Human h4= new Human();
        Human h5= new Human();
        Human h6= new Human();
        SuperHuman sh= new SuperHuman();
        Bacteria b1= new Bacteria();

        ArrayList<Organism> arO = new ArrayList<>();
        ArrayList<SmartI> arS = new ArrayList<>();
        arS.add(sh);

        for (SmartI tempI: arS
        ) {
            tempI.getSmart();
            System.out.println(((Human)tempI).nickname);

    /*        if(tempI instanceof Human)
                System.out.println(((Human)tempO).unique);
            else
                ((Bacteria)tempO).doSound();*/

        }


        arO.add(h);
        arO.add(h2);
        arO.add(h3);
        arO.add(h4);
        arO.add(h5);
        arO.add(h6);
        arO.add(b1);


        h6.unique=false;
        h3.unique=false;
        for (Organism tempO: arO) {

            if(tempO instanceof Human)
            System.out.println(((Human)tempO).unique);
            else
             ((Bacteria)tempO).doSound();

        }

        System.out.println( h.getClass().getCanonicalName()  );
        System.out.println("Hello world!");


        JFileChooser j = new JFileChooser("src");
        j.showSaveDialog(null);

            System.out.println(j.getSelectedFile().getPath());


    }
}