public class Main {
  public static void main (String[] args) {
    Reversible[] revers = new Reversible[] {
      new ReversibleString("Cat"),
      new ReversibleDouble(2),
      new ReversibleDouble(3),
      new ReversibleString("Dog"),
      new ReversibleString("Alice in Wonderland"),
      new ReversibleDouble(10),
    };

    System.out.println("Original:");
    for (Reversible r : revers) System.out.println(r);

    for (Reversible r : revers) { r.reverse(); }

    System.out.println("Reversed:");
    for (Reversible r : revers) System.out.println(r);

    System.out.println("Reversed again and modified:");
    /**
     * all strings are prepended by the word Text:,
     * while numbers are incremented by 10.
     */
    for (Reversible r : revers) {
      r.reverse();
      if (r instanceof ReversibleDouble) {
        System.out.println(10.0 + Double.parseDouble(r.toString()));
      } else if (r instanceof ReversibleString) {
        System.out.println("Text: " + r.toString());
      }
    }
  }
}

interface Reversible {
  void reverse();
}


class ReversibleString implements Reversible {
  private String value;

  ReversibleString (String v) {
    this.value = v;
  }

  public void reverse () {
    this.value = new StringBuilder(this.value).reverse().toString();
  }

  @Override
  public String toString () {
    return "" + this.value;
  }
}

class ReversibleDouble implements Reversible {
  private double value;

  ReversibleDouble (double v) {
    this.value = v;
  }

  public void reverse () {
    this.value = 1 / this.value;
  }

  @Override
  public String toString () {
    return "" + this.value;
  }
}