import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyWindow extends JFrame {
  public static void main (String[] args) {
    SwingUtilities.invokeLater(() -> new MyWindow());
  }

  public MyWindow () {
    setTitle("Two timestamps - stop/resume");

    setPreferredSize(new Dimension(800, 600));
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

    addWindowListener(new WindowAdapter(){
      @Override
      public void windowClosing (WindowEvent e) {
        int choice = JOptionPane
          .showConfirmDialog(null, "Close?", "Confirmation", JOptionPane.OK_CANCEL_OPTION);

        if (choice == JOptionPane.OK_OPTION) {
          dispose();
        }
      }
    });

    render();
  }

  public void render () {
    JPanel mainPanel = new JPanel(new GridLayout(1,2));

    mainPanel.add(new TimePanel("left"));
    mainPanel.add(new TimePanel("right"));
    add(mainPanel);

    pack();

    setVisible(true);
  }
}

class TimePanel extends JPanel {
  private String name;

  public TimePanel (String n) {
    super();
    this.name = n;

    render();
    initialize();
  }

  private void render () {
    JTextArea textArea = new JTextArea("TextArea " + this.name);
    JButton button = new JButton("GO");

    this.setLayout(new BorderLayout());
    this.add(new JScrollPane(textArea), BorderLayout.CENTER);
    this.add(button, BorderLayout.SOUTH);
  }

  private void initialize () {

    // TODO: start a thread
    System.out.println("Thread " + this.name + " initialized");
  }

}
