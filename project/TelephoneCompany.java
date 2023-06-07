import java.util.Random;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;
import java.util.Collections;
import java.util.stream.Collectors;

public class TelephoneCompany  {
  public static void main (String[] args) {
    String dataFilePath = "input.txt";
    generateInputFile(dataFilePath, 50, 15, 60);

    PhoneCompany app = new PhoneCompany(dataFilePath, 10); // 2nd arg is N

    app.initialize();
    app.printCallerLongestTotal();
    app.printCalleeLongestTotal();
    app.printCallerMadeMostUnique();
    app.printCalleeReceivedMostUnique();
    app.printCallersMadeMostCalls();
    app.printCalleesReceivedMostCalls();
    app.printCallersMadeLeastCalls();
    app.printCalleesReceivedLeastCalls();

    app.getFullInfoForCustomer();
  }

  private static int getRandomId (int range) {
    return (int)Math.ceil(Math.random() * range);
  };

  public static void generateInputFile (
    String path,
    double mean, // seconds
    double deviation, //seconds
    double maxMinutes
  ) {
    // R (number of call records)
    int linesCount = (int)(Math.round(Math.random() * 20_000 ) + 190_000); // 190-210k
    // K (company customer identifiers)
    int customersQuantity = (int)(Math.round(Math.random() * 100) + 450); // 450 - 550

    Random randomizer = new Random();

    try (
      BufferedWriter writer = Files.newBufferedWriter(Paths.get(path));
    ){
      for (int i = 1; i <= linesCount; i++) {
        int id1 = getRandomId(customersQuantity);
        int id2;
        do {
          id2 = getRandomId(customersQuantity);
        } while (id2 == id1);

        double dur;
        do {
          dur = randomizer.nextGaussian() * deviation + mean;
        } while (dur < 1 || dur > maxMinutes * 60); // at least 1s
        int duration = (int)dur;

        String line = "" + id1 + " " + id2 + " " + duration + "\n";
        writer.write(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}


class PhoneCompany {
  private List<Call> data;
  private String filePath;
  private int n;

  PhoneCompany (String path, int n) {
    this.filePath = path;
    this.n = n;
  }

  public void initialize () {
    this.data = new ArrayList<>();

    try {
      Files.lines(Paths.get(this.filePath))
        .map(line -> line.split(" "))
        .map(bits -> new Call(
          Integer.parseInt(bits[0]),
          Integer.parseInt(bits[1]),
          Integer.parseInt(bits[2])
        ))
        .forEach(this.data::add);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * list of N customers who talked for the longest time as callers (and this time)
   */
  public void printCallerLongestTotal () {
    System.out.println("Top " + this.n + " callers with the longest total duration:");

    Map<Integer, Integer> map = new HashMap<>();
    for (Call call : this.data) {
      int id = call.getCaller();
      int dur = call.getDuration();
      map.put(id, map.getOrDefault(id, 0) + dur);
    }

    List<Map.Entry<Integer, Integer>> callers = map
      .entrySet()
      .stream()
      .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
      .limit(this.n)
      .collect(Collectors.toList());

    for (Map.Entry<Integer, Integer> entry : callers) {
      int id = entry.getKey();
      int totalDur = entry.getValue();
      System.out.println("Caller ID: " + id + ", Total Duration: "
        + Call.getDurationString(totalDur));
    }

  };

  /**
   * list of N customers who talked for the longest time as callees (and this time)
   */
  public void printCalleeLongestTotal () {
    System.out.println("\nTop " + this.n + " callees with the longest total duration:");

    Map<Integer, Integer> map = new HashMap<>();
    for (Call call : this.data) {
      int id = call.getCallee();
      int dur = call.getDuration();
      map.put(id, map.getOrDefault(id, 0) + dur);
    }

    List<Map.Entry<Integer, Integer>> callees = map
      .entrySet()
      .stream()
      .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
      .limit(this.n)
      .collect(Collectors.toList());

    for (Map.Entry<Integer, Integer> entry : callees) {
      int id = entry.getKey();
      int totalDur = entry.getValue();
      System.out.println("Callee ID: " + id + ", Total Duration: "
        + Call.getDurationString(totalDur));
    }
  };

  /**
   * list of N customers who called the largest number of other customers
   */
  public void printCallerMadeMostUnique () {
    System.out.println("\nTop " + this.n
      + " who called the largest number of other unique customers:");

    Map<Integer, Set<Integer>> map = new HashMap<>();
    for (Call call : this.data) {
      int id = call.getCaller();
      int callee = call.getCallee();
      Set<Integer> set = map.getOrDefault(id, new HashSet<>());
      set.add(callee);
      map.put(id, set);
    }

    List<Map.Entry<Integer, Set<Integer>>> topCustomers = map
      .entrySet()
      .stream()
      .sorted((a,b) -> Integer.compare(b.getValue().size(), a.getValue().size()))
      .limit(this.n)
      .collect(Collectors.toList());

    for (Map.Entry<Integer, Set<Integer>> entry : topCustomers) {
      int id = entry.getKey();
      int count = entry.getValue().size();
      System.out.println("Caller ID: " + id
        + ", Callee count: " + count);
    }
  }

  /**
   * list of N customers who received calls from the largest number
   * of other customers
   */
  public void printCalleeReceivedMostUnique () {
    System.out.println("\nTop " + this.n
      + " who received calls from the largest number of other unique customers:");

    Map<Integer, Set<Integer>> map = new HashMap<>();
    for (Call call : this.data) {
      int id = call.getCallee();
      int caller = call.getCaller();
      Set<Integer> set = map.getOrDefault(id, new HashSet<>());
      set.add(caller);
      map.put(id, set);
    }

    List<Map.Entry<Integer, Set<Integer>>> topCustomers = map
      .entrySet()
      .stream()
      .sorted((a,b) -> Integer.compare(b.getValue().size(), a.getValue().size()))
      .limit(this.n)
      .collect(Collectors.toList());

    for (Map.Entry<Integer, Set<Integer>> entry : topCustomers) {
      int id = entry.getKey();
      int count = entry.getValue().size();
      System.out.println("Callee ID: " + id
        + ", Caller count: " + count);
    }
  }

  /**
   * list of N customers who made the largest number of calls
   */
  public void printCallersMadeMostCalls () {
    System.out.println("\nTop " + this.n
      + " callers who made the largest number of calls:");

    Map<Integer, Integer> map = new HashMap<>();
    for (Call call : this.data) {
      int id = call.getCaller();
      map.put(id, map.getOrDefault(id, 0) + 1);
    }

    List<Map.Entry<Integer, Integer>> callers = map
      .entrySet()
      .stream()
      .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
      .limit(this.n)
      .collect(Collectors.toList());

    for (Map.Entry<Integer, Integer> entry : callers) {
      int id = entry.getKey();
      int calledTimes = entry.getValue();
      System.out.println("Caller ID: " + id
        + ", Total number of outgoing calls: " + calledTimes);
    }
  }

  /**
   * list of N customers who received the largest number of calls
   */
  public void printCalleesReceivedMostCalls () {
    System.out.println("\nTop " + this.n
      + " callees who received the largest number of calls:");

    Map<Integer, Integer> map = new HashMap<>();
    for (Call call : this.data) {
      int id = call.getCallee();
      map.put(id, map.getOrDefault(id, 0) + 1);
    }

    List<Map.Entry<Integer, Integer>> customers = map
      .entrySet()
      .stream()
      .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
      .limit(this.n)
      .collect(Collectors.toList());

    for (Map.Entry<Integer, Integer> entry : customers) {
      int id = entry.getKey();
      int count = entry.getValue();
      System.out.println("Callee ID: " + id
        + ", Total number of incoming calls: "
        + count);
    }
  }

  /**
   * list of N customers who made the smallest number of calls
   */
  public void printCallersMadeLeastCalls () {
    System.out.println("\nTop " + this.n
      + " callers who made the smallest number of calls:");

    Map<Integer, Integer> map = new HashMap<>();
    for (Call call : this.data) {
      int id = call.getCaller();
      map.put(id, map.getOrDefault(id, 0) + 1);
    }

    List<Map.Entry<Integer, Integer>> callers = map
      .entrySet()
      .stream()
      .sorted(Map.Entry.comparingByValue())
      .limit(this.n)
      .collect(Collectors.toList());

    for (Map.Entry<Integer, Integer> entry : callers) {
      int id = entry.getKey();
      int calledTimes = entry.getValue();
      System.out.println("Caller ID: " + id
        + ", Total number of outgoing calls: "
        + calledTimes);
    }
  }

  /**
   * list of N customers who received the smallest number of calls
   */
  public void printCalleesReceivedLeastCalls () {
    System.out.println("\nTop " + this.n
      + " callees who received the smallest number of calls:");

    Map<Integer, Integer> map = new HashMap<>();
    for (Call call : this.data) {
      int id = call.getCallee();
      map.put(id, map.getOrDefault(id, 0) + 1);
    }

    List<Map.Entry<Integer, Integer>> customers = map
      .entrySet()
      .stream()
      .sorted(Map.Entry.comparingByValue())
      .limit(this.n)
      .collect(Collectors.toList());

    for (Map.Entry<Integer, Integer> entry : customers) {
      int id = entry.getKey();
      int count = entry.getValue();
      System.out.println("Callee ID: " + id
        + ", Total number of incoming calls: "
        + count);
    }
  }

  /**
   * Full information about customer no K:
   *   - how many calls they made and received,
   *   - for how many seconds they have to pay
   */
  public void getFullInfoForCustomer (int id) {
    _getFullInfoForCustomer(id);
  }

  public void getFullInfoForCustomer () {
    int size = this.data.size();
    int someId = 0;
    if (size != 0 ) {
      Call randomCallRecord = this.data.get((int)(Math.random() * size));
      someId = randomCallRecord.getCaller();
    }
    _getFullInfoForCustomer(someId);
  }

  public void _getFullInfoForCustomer (int id) {
    int incomingCount = 0;
    int outgoingCount = 0;
    int secondsToPayFor = 0; // only outgoing are counted

    for (Call call : this.data) {
      int id1 = call.getCaller();
      int id2 = call.getCallee();
      int dur = call.getDuration();

      if (id1 == id) {
        outgoingCount++;
        secondsToPayFor += dur;
      } else if (id2 == id) {
        incomingCount++;
      }
    }

    System.out.println("\nCustomer ID=" + id + " has made "
      + outgoingCount + " outgoing calls, received " + incomingCount
      + " calls. Has to pay for " + secondsToPayFor + " outgoing seconds ("
      + Call.getDurationString(secondsToPayFor) + ")");
  }
}


class Call {
  private int caller, callee, durationInSeconds;

  Call (int id1, int id2, int s) {
    this.caller = id1;
    this.callee = id2;
    this.durationInSeconds = s;
  }

  public int getDuration () {
    return this.durationInSeconds;
  }

  public int getCaller () {
    return this.caller;
  }

  public int getCallee () {
    return this.callee;
  }

  public static String getDurationString (int s) {
    int minute = 60;
    int hour = 60 * minute;
    int day = 24 * hour;

    int days = s / day;
    int hours = (s % day) / hour;
    int minutes = (s % day % hour) / minute;
    int seconds = s % day % hour % minute;

    return (days > 0 ? (days + "d ") : "")
      + ((days > 0 || hours > 0) ? (hours + "h ") : "")
      + (minutes + "m ") + (seconds + "s");
  }
}