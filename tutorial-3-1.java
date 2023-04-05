import java.lang.Math;

public class Main {
  /**
   * 1. passing an object of class Parabola and limits a i b (for example
   *    for function x2 − x + 5/4 for x ∈ [0, 1]);
   * 2. passing an object of an anonymous class implementing the FunDD
   *    interface and limits a i b (for example for function
   *    sqrt((x − 0.75)^2 + 1) for x ∈ [0, 2]);
   * 3. passing a lambda and limits a i b (for example for function
   *    x^2(x − 2) for x ∈ [0, 2]).
   */
  public static void main (String[] args) {
    Parabola f1 = new Parabola(1,-1, 5.0/4 );
    double min1 = FunDD.xminim(f1, 0, 1);
    System.out.println("Parabola x2 − x + 5/4 for x ∈ [0, 1]): " + min1);

    FunDD f2 = new FunDD(){
      @Override
      public double fun (double x) {
        return  Math.sqrt(Math.pow((x - 0.75),2) + 1);
      }
    };
    double min2 = FunDD.xminim(f2, 0, 2);
    System.out.println("sqrt((x − 0.75)^2 + 1) for x ∈ [0, 2]): " + min2);

    double min3 = FunDD.xminim((x) -> {
      return Math.pow(x,2)*(x-2);
    }, 0, 2);
    System.out.println("x^2(x − 2) for x ∈ [0, 2]): " + min3);

    // the results should be 1/2, 3/4 and 4/3 (with high accuracy).
  }
}

/**  FunctionalInterface should have 1 and only 1 method */
@FunctionalInterface
interface FunDD {
    double fun (double x);

    /**
     * 1. takes a reference f to an object of a class implementing
     *    the FunDD interface and limits a i b;
     * 2. finds the value of the argument x from the range [a,b] for which
     *    the method fun of the object f assumes the minimum value
     *    (i.e., finds the location of the minimum of the function on [a, b]);
     *    a somewhat primitive way to find it would be to calculate fun(x)
     *    for values of the argument between a and b for every value with
     *    a (small) fixed step (e.g., 1e-5).
     */
    static double xminim (FunDD f, double a, double b) {
        double xmin = a;
        double min = f.fun(xmin);

        for (double x = a + 1e-5; x <= b; x += 1e-5) {
          double y = f.fun(x);

          if (y < min) {
            xmin = x;
            min = y;
          }
        }

        return xmin;
    }
}


/**
 * with fields a, b and c of type double in which the method fun calculates
 * the value of the quadratic function ax2 +bx+c.
 */
class Parabola implements FunDD {
  private double a, b, c;

  Parabola (double a, double b, double c) {
    this.a = a;
    this.b = b;
    this.c = c;
  }

  @Override
  public double fun (double x) {
    return this.a * x * x + this.b * x + this.c;
  }
}

