/**
 * The user sets height and weight using the sliders (JSlider), entersx
 * a name in the text field and sets the size of clothes
 * with the combo-box (JComboBox). The values in the combo-box correspond
 * to constants XS, S, M, L and XL of the enumeration Size.
 * After entering the required data, the user presses the Add person button.
 * An object of class Person is then created and added to a list (JList<Person>)
 * shown in the upper part of the interface.
 *
 * Small persons (size XS) are shown on a green background,
 * while big persons (size XL) on red.
 *
 * Height (cm): 100 - 200
 * Weight (kg): 40 - 120
 *
 * input 'name'
 * size -selector (enum) : XS, S, M, L, XL
 * button 'add person'
 * button 'exit'
 */


import java.util.Hashtable;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
// import javax.swing.DefaultListModel;

public class MyWindow extends JFrame {
  public static void main (String[] args) {
    SwingUtilities.invokeLater(() -> new MyWindow());
  }

  public MyWindow () {
    setTitle("Tutorial-12-problem-1");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(800,800));
    setLayout(new BorderLayout());

    // list
    DefaultListModel<Person> listModel = new DefaultListModel<>();
    listModel.addElement(new Person(170, 56, "Mary", Size.M));
    listModel.addElement(new Person(160, 50, "Jane", Size.XL));
    listModel.addElement(new Person(168, 62, "Jill", Size.M));
    listModel.addElement(new Person(168, 50, "Susan", Size.XS));
    listModel.addElement(new Person(160, 54, "Rachel", Size.M));

    JList<Person> list = new JList<Person>(listModel);
    JScrollPane scrollPane = new JScrollPane(list);

    add(scrollPane, BorderLayout.CENTER);

    JPanel bottomPane = new JPanel(new GridLayout(3,1));


    // height
    MySlider height = new MySlider("Height [cm]", 100, 200);
    bottomPane.add(height);

    // weight
    MySlider weight = new MySlider("Weight [kg]", 40, 120, 10);
    bottomPane.add(weight);

    // size combo box
    // JPanel bottomCombos = new JPanel(new GridLayout(1,))
    JComboBox size = new JComboBox<Size>(Size.class.getEnumConstants());
    bottomPane.add(size);

    // JLabel size = new JLabel("size:",RIGHT);

    add(bottomPane, BorderLayout.SOUTH);


    // add(button, BorderLayout.SOUTH);
    pack();
    setVisible(true);
  }
}

enum Size {
  XS,
  S,
  M,
  L,
  XL
}

class MySlider extends JSlider {
  public MySlider (String title, int min, int max) {
    this(title, min, max,  (int)((max - min) / 10));
  }

  public MySlider (String title, int min, int max, int step) {
    super(JSlider.HORIZONTAL, min, max, (int)(min + (max-min) /2));

    Hashtable<Integer,JLabel> ticks = new Hashtable<>();
    for (int i = min; i <= max; i+=step) {
      JLabel h = new JLabel("" + i, JLabel.CENTER);
      ticks.put(i, h);
    }

    this.setLabelTable(ticks);
    this.setPaintLabels(true);
    this.setMajorTickSpacing(1);
    this.setPaintTicks(true);
    this.setPaintTrack(true);
    this.setSnapToTicks(true);
    this.setBorder(BorderFactory.createTitledBorder(title));
    // this.addChangeListener(this);
  }
}

class Person {
  private int height, weight;
  private String name;
  private Size size;

  Person (int h, int w, String n, Size s) {
    this.height = h;
    this.weight = w;
    this.name = n;
    this.size = s;
  }

  @Override
  public String toString () {
    return this.name + " (h=" + this.height + ", w=" + this.weight
      + ", size=" + this.size.name() + ")";
  }

}