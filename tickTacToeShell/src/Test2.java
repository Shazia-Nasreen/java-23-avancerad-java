import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Test2 extends JFrame {

    public static ArrayList<String> aryL = new ArrayList<>();

    private JPanel panel;
    private JPanel panelCells;
    private JTextField sdfTextField;
    private Scanner sc;

    private int amountOfCol = 0, amountOfRow = 0 ;
    Test2(){ //construtor
        setContentPane(panel);

        setTitle("test");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        try {
            File f = new File("src/Materiallista.json");
            sc = new Scanner(f);
            String page= "";
            while (sc.hasNext()) {
                String line = sc.nextLine();
                System.out.println(line);
                page+= line;
                System.out.println(line.length());
            }
            sc.close();
            System.out.println(page); // utanför
              String[] array = page.split("}");

            for (int i = 0 ;  i < array.length ; i++) {
                //System.out.println(array[i]+"\n");
            }
            System.out.println(array[0]);
            String[] firstsplit= array[0].split(",");
            amountOfCol= firstsplit.length; // 3
            for (int i = 0; i <3; i++) {  //första header data !!!!
                //System.out.println(firstsplit[i]);
                //System.out.println(firstsplit[i].split(":")[0]);
                System.out.println(firstsplit[i].split(":")[0].split("\"")[1]);
            }


            for (int i = 0; i <3; i++) {  //Andra header data !!!!
                System.out.println(firstsplit[i].split("\"",5)[3]);
            }

            System.out.println(aryL);

         /*   for (String s : aryL) {
                System.out.println(s);
            }
*/
        } catch (Exception e) {
            System.out.println("ERROR" + e.toString());
        }

        /*    try {
            File f = new File("src/Materiallista.csv");
            Scanner sc = new Scanner(f);
            while (sc.hasNext()) {
                String line = sc.nextLine();
                String[] array = line.split(",", 3);
                aryL.addAll(Arrays.asList(array));
                //System.out.println(array[0]);
                System.out.println(Arrays.deepToString(array));
                //System.out.println(line);
            }
            System.out.println(aryL);

         *//*   for (String s : aryL) {
                System.out.println(s);
            }
*//*
        } catch (Exception e) {
            System.out.println("ERROR" + e.toString());
        }*/
        int i = 0;
        for (Component jt : panelCells.getComponents()) { // panel innehåller
            System.out.println(((JTextField)jt).getText());
            ((JTextField)jt).setText(aryL.get(i));
            i++;
        }
    }
    public static void main(String[] args) { // main
        new Test2();
    }


}
