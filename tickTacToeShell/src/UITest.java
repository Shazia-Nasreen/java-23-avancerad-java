import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UITest extends JFrame {

    private JButton button1;
    private JPanel mainPanel;

    public UITest(){
        setContentPane(mainPanel);
        setTitle("test");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("hello world");
            }
        });
    }
    public static void main(String[] args) {
        new UITest();

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
