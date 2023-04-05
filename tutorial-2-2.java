import java.util.Arrays;

public class StringFilter {
  public static void main (String[] args) {
    String[] arr = {"Alice", "Sue", "Janet", "Bea"};
    System.out.println(Arrays.toString(arr));
    //  [Alice, Sue, Janet, Bea]

    /**
     * an object of LenFilter initialized in this way that its test method
     * selects only strings of the length greater than 4;
     */
    LenFilter f1 = new LenFilter(5);
    String[] a1 = SFilter.filter(arr, f1);
    System.out.println(Arrays.toString(a1));
    // expected:    [Alice, Janet]

    /**
     * an object of an anonymous class which implements the SFilter interface
     * in such a way that it selects only strings whose first letter is earlier
     * in the alphabet than ’D’ but later or equal ’A’;
     */
    SFilter f2 = new SFilter() {
      @Override
      public boolean test (String s) {
        char c = s.charAt(0);
        return c < 'D' && c >= 'A';
      }
    };
    String[] a2 = SFilter.filter(arr, f2);
    System.out.println(Arrays.toString(a2));
    // expected: [Alice, Bea]

    /**
     * a lambda which selects only those strings whose first letter is later
     * in the alphabet than ’H’ but earlier or equal ’Z’.
     */
    String[] a3 = SFilter.filter(arr, (s) -> {
      char c = s.charAt(0);
      return c < 'Z' && c >= 'H';
    });
    System.out.println(Arrays.toString(a3));
    // expected: [Sue, Janet]
  }
}

/**
 * Define a (functional) interface SFilter declaring one method test which takes
 * a String and returns a boolean.
 */
@FunctionalInterface
interface SFilter {
  boolean test(String s);

  /**
   * The interface defines also a static function
   * which takes an array of Strings and an object implementing the SFilter interface.
   * The function returns another array of Strings which contains only those elements
   * of the original array for which calling `test` on filt returns true.
   */
  public static String[] filter(String[] arr, SFilter filt) {
    int length = 0;
    for (String e: arr) {
      if (filt.test(e)) {
        length++;
      }
    }

    String[] output = new String[length];

    int i = 0;
    for (String e: arr) {
      if (filt.test(e)) {
        output[i++] = e;
      }
    }

    return output;
  }
}

/**
 * Define also a class LenFilter which implements the interface. The class has
 * one field minLen of type int which is set by the constructor;
 * the implementation of the function `test` returns `true` if, and only if,
 * the string passed as the arguments is of length at least minLen.
 */
class LenFilter implements SFilter {
  int minLen;

  LenFilter(int minLen) {
    this.minLen = minLen;
  }

  public boolean test (String s) {
    return s.length() >= this.minLen;
  }
}
