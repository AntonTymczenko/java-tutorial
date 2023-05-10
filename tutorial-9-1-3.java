import java.awt.*;
import javax.swing.*;

public class MyWindow extends JFrame {
  private JPanel mainPanel, row1, row2, inputPanel, buttonPanel1, buttonPanel2;
  private JTextField textField1, textField2, textField3;
  private JTextArea textArea;
  private JButton[] buttons;

  public static void main (String[] args) {
    new MyWindow();
  }

  public MyWindow () {
    setTitle("Tutorial 9. Task 1.3");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(500,190));


    textArea = new JTextArea("TextArea 1");

    int BUTTONS_LENGTH = 8;
    int side = (int)Math.ceil(Math.sqrt(BUTTONS_LENGTH / 2));
    buttonPanel1 = new JPanel(new GridLayout(side, side));
    buttonPanel2 = new JPanel(new GridLayout(side, side));
    buttons = new JButton[BUTTONS_LENGTH];

    for (int i=0; i < BUTTONS_LENGTH; i++) {
      buttons[i] = new JButton("B0" + (i+1));
      if (i < BUTTONS_LENGTH / 2) {
        buttonPanel1.add(buttons[i]);
      } else {
        buttonPanel2.add(buttons[i]);
      }
    }

    inputPanel = new JPanel(new GridLayout(3, 1));
    textField1 = new JTextField("TextField 1");
    textField2 = new JTextField("TextField 2");
    textField3 = new JTextField("TextField 3");
    inputPanel.add(textField1);
    inputPanel.add(textField2);
    inputPanel.add(textField3);

    row1 = new JPanel(new BorderLayout());
    row1.add(textArea);

    row2 = new JPanel(new BorderLayout());
    row2.add(buttonPanel1, BorderLayout.WEST);
    row2.add(inputPanel, BorderLayout.CENTER);
    row2.add(buttonPanel2, BorderLayout.EAST);

    mainPanel = new JPanel(new BorderLayout());

    mainPanel.add(row1);
    mainPanel.add(row2, BorderLayout.SOUTH);

    add(mainPanel);
    pack();
    setVisible(true);
  }
}
