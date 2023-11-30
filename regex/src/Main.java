import java.io.Console;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int s = 0, e = 0, pe;
            System.out.println("Enter your regex: ");
            String regex = sc.nextLine();
            System.out.println("\"" + regex + "\"");

            Pattern pattern =
                    Pattern.compile(regex);

            System.out.println("Enter input string to search: ");

            String text = sc.nextLine();
            Matcher matcher =
                    pattern.matcher(text);

            boolean found = false;
            while (matcher.find()) {
                pe = e;
               /* System.out.println(
                        matcher.group()
                                + "\n from index " + matcher.start()
                                + "\n to index " + matcher.end());*/
                s = matcher.start();
                e = matcher.end();
                System.out.print(text.substring(pe, s));

                String mark = matcher.group();
                String marked = String.join("\u0332", mark.split("", -1));
                System.out.print(marked);
                found = true;
            }

            //System.out.println("\uD83C\uDD81\uD83C\uDD74\uD83C\uDD85\uD83C\uDD78\uD83C\uDD74\uD83C\uDD86🟦\uD83C\uDD81\uD83C\uDD74\uD83C\uDD85\uD83C\uDD78\uD83C\uDD74\uD83C\uDD86\uD83C\uDDE6🟥🟧🟨🟩🟪🟫✓, ✔, \uD83D\uDDF6, ✗, ✘, ✪, ★, ☐, ☑, ☒, ⚐, ⚑, ◆, ⬧🛑🔶🔷🔸🔹🔺🔻");


            if (!found) {
                System.out.println(("No match found..."));
            } else System.out.print(text.substring(e, text.length()));

            System.out.println("");
           // sc.next();
            sc.reset();


        }
    }
}