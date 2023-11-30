import com.eclipsesource.json.Json;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.lang.module.FindException;
import java.util.ArrayList;

public class Main {

    Json j;

    public static void main(String[] args) {

        //polymorphDemo();


        JFileChooser j = new JFileChooser("src");
        j.addChoosableFileFilter(new FileNameExtensionFilter("json file (.json)","json") );
        j.addChoosableFileFilter(new FileNameExtensionFilter("xml file (.xml)","xml") );
        j.addChoosableFileFilter(new FileNameExtensionFilter("csv file (.csv)","csv") );
        int option=j.showOpenDialog(null);

        //regex  \\ är en escape character

        switch (option){
            case JFileChooser.APPROVE_OPTION -> System.out.println("Open");
            case JFileChooser.CANCEL_OPTION -> System.out.println("cancel");
            case JFileChooser.ERROR_OPTION -> System.out.println("error");
        }

        try {
            String path = j.getSelectedFile().getPath();
            String fileType = path.split("\\.")[1]; // splittar på punkt
            File f = j.getSelectedFile();   // få ut file <-- better
            String fileDec = j.getTypeDescription(f); //Verbose info om filformatet
            System.out.println(path);
            System.out.println(fileType);
            System.out.println(fileDec);
        } catch (Exception e) {
            System.out.println("no file choosen" + e);
        }


    }

    private static void polymorphDemo() {
        //Organism o = new Organism() ;
        Human h = new Human();
        Human h2 = new Human();
        Human h3 = new Human();
        Human h4 = new Human();
        Human h5 = new Human();
        Human h6 = new Human();
        SuperHuman sh = new SuperHuman();
        Bacteria b1 = new Bacteria();

        ArrayList<Organism> arO = new ArrayList<>();
        ArrayList<SmartI> arS = new ArrayList<>();
        arS.add(sh);

        for (SmartI tempI : arS
        ) {
            tempI.getSmart();
            System.out.println(((Human) tempI).nickname);

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


        h6.unique = false;
        h3.unique = false;
        for (Organism tempO : arO) {

            if (tempO instanceof Human)
                System.out.println(((Human) tempO).unique);
            else
                ((Bacteria) tempO).doSound();

        }

        System.out.println(h.getClass().getCanonicalName());
        System.out.println("Hello world!");
    }
}