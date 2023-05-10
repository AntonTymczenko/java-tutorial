/**
  Write an application displaying a GUI with two buttons and one text field.
  Expected functionality:
  • Clicking the left button copies the text from this button to the text field;
  • Pressing ENTER in the text field copies its text onto the left button;

  • Clicking the right button changes the title of the window to the text from
    the text field.

  The listener of the right button should be an object of a separate class, while
  listeners of the left button and the text field should be implemented using lambdas.
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyWindow extends JFrame {
  private String inputCity = "Paris";
  private String toReplace = "Warsaw";
  private JPanel mainPanel;
  private JTextField textField;
  private JButton button1, button2;

  public static void main (String[] args) {
    new MyWindow();
  }

  public MyWindow () {
    setTitle(this.inputCity);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(500,100));

    button1 = new JButton(this.toReplace);
    button2 = new JButton("Change title...");

    textField = new JTextField(this.inputCity, 20);

    mainPanel = new JPanel(new FlowLayout());

    mainPanel.add(button1);
    mainPanel.add(button2);
    mainPanel.add(textField);

    add(mainPanel);

    // listeners
    button1.addActionListener(e -> textField.setText(this.toReplace));
    textField.addActionListener(e -> {
      this.toReplace = textField.getText();
      button1.setText(this.toReplace);
    });
    button2.addActionListener(new RightButtonListener(this, textField));

    pack();
    setVisible(true);
  }
}

class RightButtonListener implements ActionListener {
  private JTextField field;
  private JFrame frame;

  RightButtonListener (JFrame frame, JTextField field) {
    this.frame = frame;
    this.field = field;
  }

  @Override
  public void actionPerformed (ActionEvent e) {
    this.frame.setTitle(this.field.getText());
  }
}