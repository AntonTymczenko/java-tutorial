/**
• The program creates several threads passing to their constructors
  consecutive letters; each thread prints from time to time its letter
  (the time interval between printing could be a random number of milliseconds
  from the interval [100, 1000]).
• All threads are started, but the first is suspended.
• Another thread, let’s call it the main thread, from time to time
  (say, every 5 seconds) resumes the thread that is suspended and suspends
  the next one — cyclically, i.e., if the last one is resumed, the first is suspended.

• After several such cycles (for example, ten of them), the main thread stops
  all the threads which in turn print a message just before exiting.

Important:
All threads are created once, at the beginning of the program.
The methods stop, resume, suspend, and destroy from the Thread class
are inherently unsafe and must not be used!

The output of the program could look as shown below. Here, five threads
were launched, corresponding to letters ’a’, . . . ,’e’,
but increasing the number of threads/letters should be a matter of modification
of one line only.
    ebdcebcdbedcbedcdbecbdedbce
    Resuming a, suspending b: aedcaedcaecdeadceacdeacdeacade
    Resuming b, suspending c: bdaebdebaaedbdbeaedabedbaebda
    Resuming c, suspending d: cbeacebaceacbaecbabecabecbacea
    Resuming d, suspending e: dbacdbacbadcbacddcbadacbdcabd
    Resuming e, suspending a: ecbdecbdecbdecbdecdbecdbecbd
    Resuming a, suspending b: aecdaecdeacdecadedcaedacdeace
    Resuming b, suspending c: bdaedbaedbedabedabedaebdeabd
    Resuming c, suspending d: cbaeacebabecacebaecbaebcaebceab
    Resuming d, suspending e: dcabdcabdcadbcacdbabcdabcdab
    Resuming e, suspending a: e
    Thread b exits
    Thread d exits
    Thread a exits
    Thread e exits
    Thread c exits

 */


public class Main {
  public static void main(String[] args) {
    String lettersString = "abcde";
    Letters letters = new Letters(lettersString);
    letters.start();
    for (int i = 1; i <= 10; i++) {
      try {
        Thread.sleep(2_000);
      } catch(InterruptedException _ignore) { }
      letters.switchSuspendedLetter();
    }
    letters.stop();
    System.out.println();
  }
}

class Letters {
  int length = 0;
  int suspendedIndex = -1;
  Letter[] threads;

  public Letters (String s) {
    this.length = s.length();
    this.threads = new Letter[s.length()];
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      this.threads[i] = new Letter(c);
    }
  }

  public void start () {
    this.switchSuspendedLetter();
    for (Thread t: this.threads) {
      t.start();
    }
  }

  public void stop () {
    for (Thread t: this.threads) {
      t.interrupt();
    }
  }

  public void switchSuspendedLetter () {
    int index = (this.suspendedIndex + 1) % this.length;
    Letter curr = this.threads[index];

    if (this.suspendedIndex > -1) {
      Letter prev = this.threads[this.suspendedIndex];
      System.out.print("\nResuming " + prev.getName()
        + ", suspending " + curr.getName() + ": ");
      prev.setSuspended(false);

    }

    curr.setSuspended(true);

    this.suspendedIndex = index;
  }
}


class Letter extends Thread {
  private volatile boolean suspended;
  private int interval;

  Letter (char c) {
    this.suspended = false;
    this.setName("" + c);
    this.interval = (int)(Math.random() * 900 + 100);
  }

  @Override
  public void run() {
    while (true) {
      try {
        while (!this.suspended) {
          System.out.print(this.getName());
          this.sleep(this.interval);
        }
        if (this.suspended) {
          synchronized (this) {
            while (this.suspended) { wait(); }
          }
        }
      } catch (InterruptedException _ex) {
        System.out.println("Thread " + this.getName() + " exits");
        return;
      }
    }
  }

  synchronized public void setSuspended (boolean value) {
    this.suspended = value;
    this.notify();
  }
}