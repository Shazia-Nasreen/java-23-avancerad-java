import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String[][]  array2d= new String[3][11];
        try{
            File f = new File("src/Materiallista.csv");
            Scanner sc = new Scanner(f);
            while(sc.hasNext()) {
                String line = sc.nextLine();
                String[] array = line.split(",",3);
                //System.out.println(array[0]);
                System.out.println( Arrays.deepToString(array)  );
                //System.out.println(line);
            }
        }catch (Exception e) {
            System.out.println("ERROR"+e.toString());
        }
    }
}