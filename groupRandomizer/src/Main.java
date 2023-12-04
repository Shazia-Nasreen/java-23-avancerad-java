import java.io.*;
import java.util.*;

public class Main {
    static final int groupAmount = 11;

    public static void main(String[] args) {
        ArrayList<String> nameArray = new ArrayList<>();
        try {

/*            String currentPath = new java.io.File(".").getCanonicalPath();
            System.out.println("Current dir:" + currentPath);*/        //Är abstract files faktiskt abstrakt
            File namnfile = new File("src/nameList.txt");
            Scanner sc = new Scanner(namnfile);
            nameArray = new ArrayList<>();
            //Avslutas med vad somd standard? whitespace?
            while (sc.hasNext())
                nameArray.add(sc.nextLine());
            sc.close();

            System.out.println(Arrays.deepToString(nameArray.toArray()));
            Collections.shuffle(nameArray);


            for (int i = 0; i < nameArray.size(); i++)
                System.out.println(nameArray.set(i, i%groupAmount+1 + " " + nameArray.get(i)));


            Collections.sort(nameArray);
            //deep content är bara content pga arraylist ej är implicit typad?
            System.out.println(Arrays.toString(nameArray.toArray()));
            String result = Arrays.toString(nameArray.toArray()).replaceAll("[^a-zA-Z0-9 ,]", "").replace(",", "\n");
            System.out.println(result);
            try {
                File file = new File("src/result.txt");
                FileWriter fileWrite = new FileWriter(file);
                //BufferedWriter bufferedWriter = new BufferedWriter(fileWrite);
                fileWrite.write(result);
                fileWrite.close();
            } catch (Exception e) {
                System.out.println(e);
            }

            System.out.println("--- done! ---");

        } catch (Exception e) {
            System.out.println(e);
        }


    }
/*     void main() {
        System.out.println("Hello world!");
    }*/
}
