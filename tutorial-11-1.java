/**
 * Problem 1
   Write a program drawing a snowman consisting of three balls the diameters
   of which are in 1:2:3 proportion. These proportions should be kept when we
   resize the window. The snowman should always fill the full height of the window:
 */

import java.awt.*;
import javax.swing.*;

public class MyWindow extends JFrame {
  public static void main (String[] args) {
    SwingUtilities.invokeLater(() -> new MyWindow());
  }

  public MyWindow () {
    setTitle("Snowman");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(600,800));
    add(new SnowmanPanel());
    pack();
    setVisible(true);
  }
}

class SnowmanPanel extends JPanel {
  public SnowmanPanel () {
    super();
    setBackground(Color.BLUE);
  }

  @Override
  public void paintComponent (Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.WHITE);

    int h = getRootPane().getHeight();
    int w = getRootPane().getWidth();


    int ball1 = h / 6;
    int ball2 = ball1 * 2;
    int ball3 = ball1 * 3;

    int x3 = (w - ball3) / 2;
    int x2 = x3 + (ball3 - ball2) / 2;
    int x1 = x2 + (ball2 - ball1) / 2;


    int topOffset = getInsets().top;
    g.fillOval(x1, topOffset, ball1, ball1);
    g.fillOval(x2, topOffset + ball1, ball2, ball2);
    g.fillOval(x3, topOffset + ball1 + ball2, ball3, ball3);
  }
}