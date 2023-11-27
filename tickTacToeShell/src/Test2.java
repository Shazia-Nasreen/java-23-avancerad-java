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


    Test2(){ //construtor
        setContentPane(panel);

        setTitle("test");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        try {
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

         /*   for (String s : aryL) {
                System.out.println(s);
            }
*/
        } catch (Exception e) {
            System.out.println("ERROR" + e.toString());
        }
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
