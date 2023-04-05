public class Main {
  public static void main(String[] args) {
    TwoStringsOper[] a = {
        new Concat(),
        new ConcatRev(),
        new Initials(),
        new Separ(" loves ")
    };

    for (TwoStringsOper op : a) {
        System.out.println(op.apply("Mary", "John"));
    }

    /*
    should print:
        MaryJohn
        JohnMary
        MJ
        Mary loves John
    */
  }
}

interface TwoStringsOper {
  String apply (String s1, String s2);
}

/**
 * where the operation on strings returns their concatenation
 */
class Concat implements TwoStringsOper {
  @Override
  public String apply (String s1, String s2) {
    return s1 + s2;
  }
}

 /*
  *  their concatenation, but in the reverse order(class ConcatRev);
  */
class ConcatRev implements TwoStringsOper {
  @Override
  public String apply (String s1, String s2) {
    return s2 + s1;
  }
}

/**
 * returns a string consisting of the first letters of the two strings
 */
class Initials implements TwoStringsOper {
  @Override
  public String apply (String s1, String s2) {
    return "" + s1.charAt(0) + s2.charAt(0);
  }
}

/**
 * their concatenation, but separated by a separator passed to the constructor
 */
class Separ implements TwoStringsOper {
  String separator;

  Separ (String separator) {
    this.separator = separator;
  }

  @Override
  public String apply (String s1, String s2) {
    return s1 + this.separator + s2;
  }
}