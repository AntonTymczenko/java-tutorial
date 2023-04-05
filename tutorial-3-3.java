import java.util.Arrays;
import java.lang.Math;
import java.util.Comparator;

public class Compars {
    public static void main(String[] args) {
        Integer[] a = {1,5,33,12,98,15};
        printTable("Original    ", a);

        Arrays.sort(a,new MyComp(MyComp.Options.VALUE));
        printTable("ByVal       ", a);

        Arrays.sort(a,new MyComp(MyComp.Options.VALUE_REVERSED));
        printTable("ByValRev    ", a);

        Arrays.sort(a,new MyComp(MyComp.Options.NUM_OF_DIVISORS));
        printTable("ByNumOfDivs ", a);

        Arrays.sort(a,new MyComp(MyComp.Options.SUM_OF_DIGITS));
        printTable("BySumOfDigs ", a);
    }

    static void printTable(String mess, Integer[] a) {
        System.out.print(mess + "[ ");
        for (int d : a) System.out.print(d + " ");
        System.out.print("]\n");
    }
}

class MyComp implements Comparator<Integer> {
  static enum Options {
    VALUE, // by numerical value, in ascending order
    VALUE_REVERSED, // by numerical value, in descending order
    NUM_OF_DIVISORS, // by number of divisors
    SUM_OF_DIGITS // by sum of digits
  }

  Options compareBy;

  MyComp (Options compareBy) {
    this.compareBy = compareBy;
  }

  public static int numOfDivisors (int x) {
    int divisors = 1;
    for (int i = 2; i <= Math.abs(x); i++) {
      if (Math.abs(x) % i == 0) {
        divisors++;
      }
    }

    // System.out.println(x + " has " + divisors + " divisors");
    return divisors;
  }

  public static int sumOfDigits (int x) {
    int t = x;
    int sum = 0;
    while (t > 0) {
      sum += t % 10;
      t = t / 10;
    }

    return sum;
  }

  @Override
  public int compare (Integer a, Integer b) {
    return switch (this.compareBy) {
      case VALUE -> a - b;
      case VALUE_REVERSED -> b - a;
      case NUM_OF_DIVISORS -> numOfDivisors(a) - numOfDivisors(b);
      case SUM_OF_DIGITS -> sumOfDigits(a) - sumOfDigits(b);
    };
  }
}
