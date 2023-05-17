/**
 Write a program which displays a GUI with
    • a text area with the content of a text file;
    • a panel with two buttons: pressing one of them closes the application, pressing
      the other displays a dialog of type `JFileChooser` allowing the user to select
      a file — only files with extension `txt` (or, e.g., java) should appear;
    • a label with the name of the file currently shown in the text area.
  Selecting a file will look like this:
  ...
  and after a file has been selected, we should see its content in a
  text area with scroll

  Note: You can force the text area to scroll to the bottom after a call to append like this:
    textArea.setCaretPosition(textArea.getDocument().getLength());
 */


import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MyWindow extends JFrame {
  private JPanel mainPanel, row2, buttonPanel;
  private JTextArea textArea;
  private JButton button1, button2;
  private JFileChooser fileChooser;

  public static void main (String[] args) {
    new MyWindow();
  }

  public MyWindow () {
    this.initialize();
  }

  private void initialize () {
    setTitle("Tutorial 10. Task 2");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(500,190));

    textArea = new JTextArea(20,30);
    JScrollPane row1 = new JScrollPane(textArea);

    row2 = new JPanel(new BorderLayout());
    button1 = new JButton("Select File");
    button1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        selectFile();
      }
    });

    button2 = new JButton("Exit");
    button2.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed (ActionEvent e) {
        System.exit(0);
      }
    });

    buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(button1);
    buttonPanel.add(button2);
    row2.add(buttonPanel, BorderLayout.CENTER);

    mainPanel = new JPanel(new BorderLayout());
    mainPanel.add(row1);
    mainPanel.add(row2, BorderLayout.SOUTH);
    add(mainPanel);
    pack();
    setVisible(true);
  }

  private void selectFile () {
    fileChooser = new JFileChooser(System.getProperty("user.dir"));
    fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt", "java"));

    int i = fileChooser.showOpenDialog(null);

    if (i != JFileChooser.APPROVE_OPTION) {
      return;
    }

    File selectedFile = fileChooser.getSelectedFile();
    setTitle(selectedFile.toPath().toString());

    String textFromFile = this.readContent(selectedFile);
    textArea.setText(textFromFile);
  }

  private String readContent (File file) {
    try {
      String content = Files.readString(file.toPath());
      return content;
    } catch (IOException e) {
      e.printStackTrace();
      return "";
    }
  }
}
