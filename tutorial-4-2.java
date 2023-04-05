import java.util.Arrays;

public class Main {
  /*
   * takes two arrays of equal size, one of references of type T and the other
   * of type R, and also an object, say trans, implementing the Transform interface.
   *
   * fills the second array with results of applying the apply function invoked
   * on trans to all objects from the first array.
   */
  private static <T, R>
  void transform(T[] in, R[] out, Transform<T, R> trans) {
    for (int i = 0; i < in.length; i++) {
      out[i] = trans.apply(in[i]);
    }
  }

  public static void main (String[] args) {
      String[]  sin = {"Alice", "Sue", "Janet", "Bea"};
      System.out.println(Arrays.toString(sin) + '\n');

      Integer[] iout = new Integer[sin.length];
      /*
       * with an object of StrToInt type — types of arrays
       * are then String and Integer;
       */
      transform(sin, iout, new StrToInt());
      System.out.println(Arrays.toString(iout));

      Character[] cout = new Character[sin.length];
      /*
       * with an object of an anonymous class which implements the Transform
       * interface in such a way that its apply method takes a String
       * and returns its first character (as Character);
       */
      transform(sin, cout, new Transform<String, Character>(){
        public Character apply (String s) {
          return s.charAt(0);
        }
      });
      System.out.println(Arrays.toString(cout));

      String[] sout = new String[sin.length];
      /*
       * with a lambda which transforms strings into the same strings but
       * in upper case.
       */
      transform(sin, sout, (s) -> {
        return s.toUpperCase();
      });
      System.out.println(Arrays.toString(sout));

      /*
      should print
      [Alice, Sue, Janet, Bea]
      [5, 3, 5, 3]
      [A, S, J, B]
      [ALICE, SUE, JANET, BEA]
      */
  }
}


/* A functional generic interface */
@FunctionalInterface
interface Transform<T, R> {
  R apply(T s);
}



class StrToInt implements Transform<String, Integer> {
  public Integer apply (String s) {
    return s.length();
  }
}


