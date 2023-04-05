import java.util.Arrays;
import java.awt.Color;
import java.util.Comparator;
import java.util.Collections;
import java.util.List;

public class Main {
  public static void main(String[] args) {
    List<MyColor> list = Arrays.asList(
        new MyColor(  1,  2,  3),
        new MyColor(255,  0,  0),
        new MyColor( 55, 55,100),
        new MyColor( 10,255, 10)
    );

    System.out.println(list);

    Collections.sort(list);
    System.out.println(list);

    Collections.sort(
        list, new MyColorCompar(ColComponentEnum.RED));
    System.out.println(list);
    Collections.sort(
        list, new MyColorCompar(ColComponentEnum.GREEN));
    System.out.println(list);
    Collections.sort(
        list, new MyColorCompar(ColComponentEnum.BLUE));
    System.out.println(list);
  }
}


enum ColComponentEnum {
  RED,
  GREEN,
  BLUE
}


class MyColor extends Color implements Comparable<MyColor> {
  MyColor (int r, int g, int b) {
    super(r,g,b);
  }

  @Override
  public String toString () {
    return "(" + this.getRed() + "," + this.getGreen() + "," + this.getBlue() + ")";
  }

  /*
   * The class should define a natural order based on the sum of components
   */
  public int compareTo (MyColor c) {
    return this.getRed() + this.getGreen() + this.getBlue() -
           (c.getRed() + c.getGreen() + c.getBlue());
  }
}

class MyColorCompar implements Comparator<MyColor> {
  ColComponentEnum compareBy;

  MyColorCompar (ColComponentEnum compareBy) {
    this.compareBy = compareBy;
  }

  @Override
  public int compare (MyColor a, MyColor b) {
    return switch (this.compareBy) {
      case RED -> a.getRed() - b.getRed();
      case GREEN -> a.getGreen() - b.getGreen();
      case BLUE -> a.getBlue() - b.getBlue();
    };
  }
}
