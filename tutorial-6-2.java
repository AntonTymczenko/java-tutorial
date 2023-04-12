import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 Collatz sequence (known also as hailstone sequence or Ulam sequence)
 is a sequence starting from a natural number a[0] and whose terms are calculated
 according to the rule
 a[n+1] = a[n] / 2 for even a[n]
 and a[n+1] = 3 * a[n+1] for odd a[n].
 */


public class Main {
  public static void main(String... args) {
    int ini = 77031, count = -1, maxel = 0;
    Hailstone hailstone = new Hailstone(ini);

    int foo = 1;
    for (int h : hailstone) {
      System.out.println(foo++ + ": " + h);
      if (h > maxel) maxel = h;
      ++count;
    }

    /**
     * It should print, in one line and separated by spaces, three numbers:
     * the starting value (ini, in this example 77031),
     * number of steps until 1 is reached (count)
     * and the value of the maximum element of the sequence (maxel).
     *
     * For example, if the starting value were 10,
     * the sequence would be [10, 5, 16, 8, 4, 2, 1],
     * and therefore the three numbers printed by the program would be 10 6 16.
     */
    System.out.println(ini + " " + count + " " + maxel);
  }
}

/**
 * Your task is to create a class Hailstone, objects of which represent Collatz
 * sequences. The constructor takes the starting number (a0), which you may assume
 * is a natural number larger than 1.
 *
 * The iteration stops after returning, as the last value, the number 1.
 */
class Hailstone implements Iterable<Integer> {
  private int value;

  Hailstone (int start) {
    this.value = start;
  }

  public Iterator<Integer> iterator () {
    return new HailstoneIterator(this.value);
  }
}

class HailstoneIterator implements Iterator<Integer> {
  private int current;

  HailstoneIterator (int current) {
    this.current = current;
  }

  @Override
  public boolean hasNext () {
    return this.current != 1;
  }

  @Override
  public Integer next () {
    if (!this.hasNext()) throw new NoSuchElementException();
    int res;

    if (this.current % 2 == 0) {
      res = (int)(this.current / 2);
    } else {
      res = (int)(this.current * 3 + 1);
    }

    this.current = res;
    return res;
  }

}