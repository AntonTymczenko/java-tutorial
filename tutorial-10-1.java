/**
 * Write an application displaying a GUI with a text area and two buttons
 * — "Back" and "Show words". The user enters an arbitrary text
 * and after clicking the "Show words" button a list of all words from the text
 * is displayed in the text area; each word in lower case and once only
 *
 * Clicking now the "Back" button restores the original text in the text area,
 * for further editing.
 *
 * Note: when the text area displays the text to be edited, "Back" button should
 * be disabled while when the area displays the list of words, "Show words"
 * button shouldn’t be active (`button.setEnabled(true/false)`).
 */


import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MyWindow extends JFrame {
  private JPanel mainPanel, row2, buttonPanel;
  private JTextArea textArea;
  private JButton buttonBack, buttonShowWords;
  private String originalText = "";
  private boolean isInputMode = true;

  public static void main (String[] args) {
    new MyWindow();
  }

  public MyWindow () {
    this.initialize();
    this.addListeners();
  }

  private void initialize () {
    setTitle("Tutorial 10. Task 1");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(500,190));

    textArea = new JTextArea(20,30);
    JScrollPane row1 = new JScrollPane(textArea);

    row2 = new JPanel(new BorderLayout());
    buttonBack = new JButton("Back");
    buttonShowWords = new JButton("Show words");


    buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(buttonBack);
    buttonPanel.add(buttonShowWords);
    row2.add(buttonPanel, BorderLayout.CENTER);

    mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(row1);
    mainPanel.add(row2, BorderLayout.SOUTH);
    add(mainPanel);
    this.renderOnButtonClick(this.originalText);
    pack();
    setVisible(true);
  }

  private void addListeners () {
    buttonBack.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        isInputMode = true;
        renderOnButtonClick(originalText);
      }
    });

    buttonShowWords.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        isInputMode = false;
        originalText = textArea.getText();
        String[] words = originalText.split("\\P{L}+");

        Set<String> uniqueWordsSet = new HashSet<>();
        for (String word: words) {
          uniqueWordsSet.add(word.toLowerCase());
        }

        StringBuilder builder = new StringBuilder();
        for (String word : uniqueWordsSet) {
          builder.append(word).append("\n");
        }

        renderOnButtonClick(builder.toString());
      }
    });
  }

  private void renderOnButtonClick (String textToShow) {
    textArea.setText(textToShow);
    buttonBack.setEnabled(!this.isInputMode);
    buttonShowWords.setEnabled(this.isInputMode);
  }
}