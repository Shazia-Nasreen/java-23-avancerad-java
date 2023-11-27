import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static ArrayList<String> aryL = new ArrayList<>();

    public static void main(String[] args) {
        String[][] array2d = new String[3][11];
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
    }
}