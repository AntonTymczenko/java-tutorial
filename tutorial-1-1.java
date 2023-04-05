import java.util.Scanner;

public class main {
  public static void main (String[] args) {
    Scanner scan = new Scanner(System.in);
    System.out.println("Enter an integer: ");
    int n = scan.nextInt();
    scan.close();
    String binaryString = Integer.toBinaryString(n);
    System.out.println("Bit: " + binaryString);
    System.out.println();

    int SEVENTH_BIT = (int)(Math.pow(2,7));
    boolean is7thBitSet = (n & SEVENTH_BIT) == SEVENTH_BIT;
    System.out.println("Is 7th bit set? " + is7thBitSet);

    int numberOf1s = 0;
    for (int i = 0; i < 32; i++) {
      if (((1 << i) & n) != 0) {
        numberOf1s++;
      }
    }
    System.out.println("No. of 1s is " + numberOf1s);

    int mostSignificant = -1;

    for (int i = 31; i >= 0 ; i--) {
      if (((1 << i) & n) != 0) {
        mostSignificant = i;
        break;
      }
    }
    System.out.println("Most significant bit set: " + mostSignificant);


    int lastByte = n & 0xFF;
    int secondByte = (n >> 8) & 0xFF;

    int prefix = (n >> 16) << 16;
    int lastTwoSwapped = (lastByte << 8) | secondByte;  //

    int swapped = prefix | lastTwoSwapped;
    // your code here
    System.out.println("With 2 least significant bytes"
      + " swapped: " + swapped);

  }
}