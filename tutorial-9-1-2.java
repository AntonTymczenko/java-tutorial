import java.awt.*;
import javax.swing.*;

public class MyWindow extends JFrame {
  private JPanel mainPanel, row1, row2, buttonPanel;
  private JTextField textField4;
  private JTextArea textArea1, textArea2;
  private JButton[] buttons;

  public static void main (String[] args) {
    new MyWindow();
  }

  public MyWindow () {
    setTitle("Tutorial 9. Task 1.2");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(500,190));


    textArea1 = new JTextArea("TextArea 1");
    textArea2 = new JTextArea("TextArea 2");

    buttonPanel = new JPanel(new GridLayout(3,4));
    buttons = new JButton[12];

    for (int i=0; i < 12; i++) {
      buttons[i] = new JButton((i < 9 ? "0": "") + (i+1));
      buttonPanel.add(buttons[i]);
    }

    row1 = new JPanel(new BorderLayout());
    row1.add(textArea1, BorderLayout.WEST);
    row1.add(buttonPanel, BorderLayout.CENTER);
    row1.add(textArea2, BorderLayout.EAST);

    row2 = new JPanel(new BorderLayout());
    textField4 = new JTextField("TextField 4");
    row2.add(textField4);

    mainPanel = new JPanel(new BorderLayout());

    mainPanel.add(row1, BorderLayout.NORTH);
    mainPanel.add(row2, BorderLayout.CENTER);

    add(mainPanel);
    pack();
    setVisible(true);
  }
}
