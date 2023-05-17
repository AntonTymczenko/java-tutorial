import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class HelloWorldG {
  public static void main(String[] args) {
    JFrame fr = new JFrame("Window title – Hello!");
    fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JLabel label = new JLabel("Hello, my first GUI program");
    // all properties have reasonable defaults, but...
    label.setFont(new Font("Serif",Font.BOLD,70));
    label.setBackground(Color.ORANGE);
    label.setOpaque(true);
    label.setForeground(new Color(0,0,102));
    fr.add(label); // frame.getContentPane().add(label)
    fr.pack();
    // fr.setSize(600,400);
    fr.setLocationRelativeTo(null);
    fr.setVisible(true);
  }
}