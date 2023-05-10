import java.awt.*;
import javax.swing.*;

public class MyWindow extends JFrame {
  private JPanel mainPanel, row1, row2, row3, buttonPanel;
  private JTextField textField1, textField2, textField3;
  private JTextArea textArea1, textArea2;
  private JButton[] buttons;

  public static void main (String[] args) {
    new MyWindow();
  }

  public MyWindow () {
    setTitle("Tutorial 9. Task 1.5");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(500,190));

    buttonPanel = new JPanel(new GridLayout(1,5));
    int BUTTONS_COUNT = 5;
    buttons = new JButton[BUTTONS_COUNT];

    for (int i=0; i < BUTTONS_COUNT; i++) {
      buttons[i] = new JButton((i < 9 ? "0": "") + (i+1));
      buttonPanel.add(buttons[i]);
    }

    textField1 = new JTextField("TextField 1");
    textField2 = new JTextField("TextField 2");
    textField3 = new JTextField("TextField 3");
    row1 = new JPanel(new GridLayout(3,1));
    row1.add(textField1);
    row1.add(textField2);
    row1.add(textField3);

    row2 = new JPanel(new GridLayout(1,2, 5, 0));
    textArea1 = new JTextArea("TextArea 1");
    textArea2 = new JTextArea("TextArea 2");
    row2.add(textArea1);
    row2.add(textArea2);

    row3 = new JPanel(new BorderLayout());
    row3.add(buttonPanel);

    mainPanel = new JPanel(new BorderLayout());

    mainPanel.add(row1, BorderLayout.NORTH);
    mainPanel.add(row2);
    mainPanel.add(row3, BorderLayout.SOUTH);

    add(mainPanel);
    pack();
    setVisible(true);
  }
}
