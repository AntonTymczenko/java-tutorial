import java.awt.*;
import javax.swing.*;

public class MyWindow extends JFrame {
  private JPanel mainPanel, inputPanel, outputPanel, buttonPanel;
  private JTextField textField1, textField2, textField3;
  private JTextArea textArea;
  private JButton[] buttons;

  public static void main (String[] args) {
    new MyWindow();
  }

  public MyWindow () {
    setTitle("Tutorial 9. Task 1.1");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(500,300));

    mainPanel = new JPanel(new BorderLayout());

    inputPanel = new JPanel(new GridLayout(3, 1));
    textField1 = new JTextField("TextField 1");
    textField2 = new JTextField("TextField 2");
    textField3 = new JTextField("TextField 3");
    inputPanel.add(textField1);
    inputPanel.add(textField2);
    inputPanel.add(textField3);

    outputPanel = new JPanel(new BorderLayout());
    textArea = new JTextArea("TextArea 1");

    JScrollPane scrollPane = new JScrollPane(textArea);
    outputPanel.add(scrollPane, BorderLayout.CENTER);

    buttonPanel = new JPanel(new GridLayout(3,3));
    buttons = new JButton[9];

    for (int i=0; i < 9; i++) {
      buttons[i] = new JButton("B0" + (i+1));
      buttonPanel.add(buttons[i]);
    }

    mainPanel.add(inputPanel, BorderLayout.NORTH);
    mainPanel.add(outputPanel, BorderLayout.CENTER);
    mainPanel.add(buttonPanel, BorderLayout.EAST);
    add(mainPanel);
    pack();
    setVisible(true);
  }
}
