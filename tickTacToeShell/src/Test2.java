import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test2 extends JFrame {


    private JPanel panel;
    private JPanel panelCells;


    Test2(){ //construtor
        setContentPane(panel);

        setTitle("test");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

    }
    public static void main(String[] args) { // main
        new Test2();
    }

    
}
