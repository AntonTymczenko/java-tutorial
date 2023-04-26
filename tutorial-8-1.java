/**
 * Create a class Letters which will be used to run several threads parallely.
 * The constructor of class Letters takes a string, subsequent letters of which
 * will be printed by separate threads (as many threads as are letters in
 * the string; each thread prints ‘its’ letters every second).
 *
 * The main function (of a separate class) creates one object of the class
 * Letters, then it starts all threads, sleeps for 5 seconds, and then terminates
 * all the threads.
 *
 * Important: The methods
     - stop,
     - resume,
     - suspend
     - destroy
   from the Thread class are inherently unsafe and must not be used!
 *
 * The program should write something like:
    Thread A starting
    Thread B starting
    Thread C starting
    Thread D starting
    ACDBDBACACDBCBDA
    Program completed.
 */


import java.util.*;

public class Main {
  public static void main(String[] args) {
    Letters letters = new Letters("ABCD");
    for (Thread t : letters)
        System.out.println(t.getName() + " starting");
    letters.start();
    try { Thread.sleep(5000); }
    catch(InterruptedException _ignore) { }
    letters.stop();
    System.out.println("\nProgram completed.");
  }
}

class Letters implements Iterable<Thread> {
  ArrayList<Thread> threads;

  public Letters (String s) {
    this.threads = new ArrayList<Thread>();
    for (int i = 0; i < s.length(); i++){

      this.threads.add(new Letter(s.charAt(i)) );
    }
  }

  public Iterator<Thread> iterator () {
    return threads.iterator();
  }

  public void stop () {
    for (Thread t: this.threads) {
      t.interrupt();
    }
  }

  public void start () {
    for (Thread t: this.threads) {
      t.start();
    }
  }
}

class Letter extends Thread {
  private char c;

  Letter (char c) {
    this.c = c;
    this.setName("Thread " + c);
  }

  @Override
  public void run() {
    while (true) {
      System.out.print(this.c);
      try {
        Thread.sleep(400);
      } catch (InterruptedException _ex) {
        return;
      }
    }
  }
}