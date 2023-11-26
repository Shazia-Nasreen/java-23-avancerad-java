import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


public class WordleJFrame extends JFrame {

    /*****************************
        Author : Alrik He
        Date: 2023
    *******************************/

    // skapa classer för sam-atbete
    // mer ord randomisa det
    //features : läs från file ,lägg till fler game modes
    private final String[] wordArray = {"TEST"};
    private ArrayList<JTextField> jfArray = new ArrayList<>();

    private JPanel gamearea;
    private String result = "";


    WordleJFrame() {

        gamearea = new JPanel();
        gamearea.setBounds(200, 200, 520, 200);
        JLabel label = new JLabel("Wordle");

        gamearea.add(label);
        gamearea.setLayout(new BoxLayout(gamearea, BoxLayout.LINE_AXIS));
        for (char c : wordArray[0].toCharArray()) {
            JTextField jt = new JTextField("", 1);
            jfArray.add(jt);
            jt.addKeyListener(
                    new KeyAdapter() {
                        /* public void keyTyped(KeyEvent e) {
                        }

                        public void keyPressed(KeyEvent e) {
                        }*/

                        public void keyReleased(KeyEvent e) {
                            char c = e.getKeyChar();
                            if ('A' <= c && c <= 'z') // a-Z
                                jt.setText(String.valueOf(c) );
                            else
                                jt.setText("");
                        }
                    });
            jt.setHorizontalAlignment(JTextField.CENTER);
            jt.setFont(new Font(Font.SERIF, Font.BOLD, 50));
            jt.setAlignmentX(0.5f);
            gamearea.add(jt);
        }

        JButton b = new JButton("submit word");
        b.addActionListener(e -> {
            for (JTextField jf : jfArray) {
                result += jf.getText();
            }

            System.out.println("submited");
            System.out.println(result);
            feedback();
        });
        gamearea.add(b);

        gamearea.setVisible(true);
        this.add(gamearea);
        this.setBounds(200, 200, 600, 600);
    }

    private void feedback() {
        // EXEMPEL PÅ SVAR
        System.out.println("letter " + result.charAt(0) + " :is on the wrong place");
        System.out.println("letter " + result.charAt(1) + " :is on the correct place");
        System.out.println("letter " + result.charAt(2) + " :is doesnt exist");
        System.out.println("letter " + result.charAt(3) + " :is doesnt exist");
  /*      System.out.println("letter " + jfArray.get(0).getText() + " :is on the wrong place");
        System.out.println("letter " + jfArray.get(1).getText() + " :is on the correct place");
        System.out.println("letter " + jfArray.get(2).getText() + " :is doesnt exist");
        System.out.println("letter " + jfArray.get(3).getText() + " :is doesnt exist");*/
    }


    void makeHistory(int amountOfTries) {
        //skapa rutor med färger som beskriver om ordet är korrekt
        //grön rätt bokstav + rätt plats
        //Gul rätt bokstav + fel plats
        //Vit är fel bokstav + existerar ej i ordet
    }

}
