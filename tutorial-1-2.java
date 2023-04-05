import java.util.InputMismatchException;
import java.util.Scanner;

public class main {
  private static int YEAR_MIN = 2000;
  private static int YEAR_MAX = YEAR_MIN + 127;

  private static int SHIFT_FROMY = 4*6 + 1;
  private static int SHIFT_FROMM = 4*5 + 1;
  private static int SHIFT_FROMD = 4*4;
  private static int SHIFT_TOY = 4*2 + 1;
  private static int SHIFT_TOM = 4 + 1;

  private static String OUTPUT_FORMAT = "%4d/%02d/%02d";

  public static void main (String[] args) {
      int y1 = 2000;
      int m1 = 2;
      int d1 = 3;
      int y2 = 2127;
      int m2 = 11;
      int d2 = 29;
      System.out.println("RAW: " + y1 + ", " + m1 + ", " + d1
        + " - " + y2 + ", " + m2 + ", " + d2);
      int period = pack(y1, m1, d1, y2, m2, d2);
      showPeriod(period);
  }

  private static int pack(int fromy,int fromm,int fromd,
                          int   toy,int   tom,int   tod) {
    if (fromy > YEAR_MAX || toy > YEAR_MAX || fromy < YEAR_MIN || toy < YEAR_MIN) {
      throw new InputMismatchException("Year should be between "
        + YEAR_MIN + " and " + YEAR_MAX);
    }
    // YYY_YYYY_MMMM_D_DDDD YYY_YYYY_MMMM_D_DDDD
    // YYYY_YYYM_MMMD_DDDD_YYYY_YYYM_MMMD_DDDD
    return ((fromy - YEAR_MIN) << SHIFT_FROMY)
      | (fromm << SHIFT_FROMM)
      | (fromd << SHIFT_FROMD)
      | ((toy - YEAR_MIN) << SHIFT_TOY)
      | (tom << SHIFT_TOM)
      | tod;

  }

  public static String toBinaryInteger (int num) {
    String binaryString = "";
    for (int i = 31; i >=0; i--) {
      int bit = (int)(Math.pow(2,i));
      binaryString += (num & bit) == bit ? "1" : "0";
      if ((i != 0) && (i % 4 == 0)) {
        binaryString += "_";
      }
    }
    return binaryString;
  }

  private static void showPeriod(int period) {
    System.out.println(period);
    /*
    // debugging
    System.out.println("YYYY_YYYM_MMMD_DDDD_YYYY_YYYM_MMMD_DDDD");
    System.out.println(toBinaryInteger(period));
    */

    // logic
    int fromy = (period >>> SHIFT_FROMY)
      + YEAR_MIN;
    int fromm = (period << (32 - SHIFT_FROMY))
      >>> SHIFT_FROMM + (32 - SHIFT_FROMY);
    int fromd = (period << (32 - SHIFT_FROMM))
      >>> SHIFT_FROMD + (32 - SHIFT_FROMM);
    int toy = ((period << (32 - SHIFT_FROMD))
      >>> SHIFT_TOY + (32 - SHIFT_FROMD))
      + YEAR_MIN;
    int tom = (period << (32 - SHIFT_TOY))
      >>> SHIFT_TOM + (32 - SHIFT_TOY);
    int tod = (period << (32 - SHIFT_TOM))
      >>> (32 - SHIFT_TOM);


    System.out.format(OUTPUT_FORMAT, fromy, fromm, fromd);
    System.out.print("–");
    System.out.format(OUTPUT_FORMAT, toy, tom, tod);
    System.out.println();
  }
}