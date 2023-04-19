/**
 * Create an iterable class Words so that the following program
 * reading from file CountWords.txt in the user’s home directory (in UTF-8 encoding) prints in consecutive lines words from the file with numbers of their occurrences.
 *
 * The CountWords class must not be modified.
 * The class Words should not contain any explicit loops (use streams).
 *
 * the result should be (order of lines may be different):
    Göteborg -> 2
    Kitty -> 3
    Łódź -> 3
    Paul -> 3
 */

import java.util.*;
import java.util.stream.Stream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CountWords {
  public static void main(String[] args) {
    String file = "input-for-tut-7-4.txt";
    // String file = System.getProperty("user.home") + "/CountWords.txt";

    for (Map.Entry<String, Integer> e : new Words(file))
      System.out.println(e.getKey() + " -> " + e.getValue());
  }
}

class Words implements Iterable<Map.Entry<String,Integer>> {
  private Map<String, Integer> map = new HashMap<>();

  Words (String filepath) {
    try (Stream<String> lines = Files.lines(Paths.get(filepath))) {
      lines
        .flatMap(line -> Arrays.stream(line.split(
          // by non-letters
          "\\P{L}+"
        )))
        .map(String::toLowerCase)
        .map(word -> word.substring(0,1).toUpperCase()
          + word.substring(1))
        .forEach(word -> {
          int count = this.map.getOrDefault(word, 0);
          this.map.put(word, ++count);
        });

    } catch(IOException e) {
      System.out.println("Error reading a file " + filepath);
      e.printStackTrace();
    }
  }

  public Iterator<Map.Entry<String,Integer>> iterator () {
    return this.map.entrySet().iterator();
  }
}