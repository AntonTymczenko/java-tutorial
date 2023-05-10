import java.awt.*;
import javax.swing.*;

public class MyWindow extends JFrame {
  private JPanel mainPanel, row1, row2, row3, buttonPanel1, buttonPanel2, tfPanel;
  private JTextField textField1, textField2, textField3;
  private JTextArea textArea;
  private JButton[] buttons;

  public static void main (String[] args) {
    new MyWindow();
  }

  public MyWindow () {
    setTitle("Tutorial 9. Task 1.4");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(500,190));

    int BUTTONS_COUNT = 8;
    buttonPanel1 = new JPanel(new GridLayout(1,BUTTONS_COUNT/2));
    buttonPanel2 = new JPanel(new GridLayout(1,BUTTONS_COUNT/2));
    buttons = new JButton[BUTTONS_COUNT];

    for (int i=0; i < BUTTONS_COUNT; i++) {
      buttons[i] = new JButton((i < 9 ? "0": "") + (i+1));
      if (i < BUTTONS_COUNT / 2) {
        buttonPanel1.add(buttons[i]);
      } else {
        buttonPanel2.add(buttons[i]);
      }
    }

    row1 = new JPanel(new BorderLayout());
    row1.add(buttonPanel1);

    row2 = new JPanel(new GridLayout(1,2, 5, 0));
    textArea = new JTextArea("TextArea 1");
    tfPanel = new JPanel(new GridLayout(3,1));
    textField1 = new JTextField("TextField 1");
    textField2 = new JTextField("TextField 2");
    textField3 = new JTextField("TextField 3");
    tfPanel.add(textField1);
    tfPanel.add(textField2);
    tfPanel.add(textField3);
    row2.add(textArea);
    row2.add(tfPanel);

    row3 = new JPanel(new BorderLayout());
    row3.add(buttonPanel2, BorderLayout.CENTER);

    mainPanel = new JPanel(new BorderLayout());

    mainPanel.add(row1, BorderLayout.NORTH);
    mainPanel.add(row2);
    mainPanel.add(row3, BorderLayout.SOUTH);

    add(mainPanel);
    pack();
    setVisible(true);
  }
}
