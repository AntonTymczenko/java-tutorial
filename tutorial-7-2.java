/**
 * Write a program that reads a text (for example, of a book) from a file
 * and using one chain of stream operations ceates a map where lengths of words
 * are keys, and the values are lists of non-repeating words from the text
 * with lengths corresponding to the key, but only those with all letters
 * different (ignoring the case).
 */


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.regex.Pattern;

public class DiffLettWords {
  public static void main(String[] args) {
    boolean WITH_UTF8 = true;
    String book = WITH_UTF8
      ? "schultz_sklepy_cynamonowe_UTF8.txt"
      : "melville_moby_dick.txt";

    int minLen = 5; // ignore words shorter than minLen
    try (Stream<String> lines = Files.lines(Paths.get(book))) {
      /**
       * lengths of words are keys, the values are lists of non-repeating words
       * only those with all letters are different (ignoring the case)
       */
      Map<Integer,List<String>> map =
        /* one chain of stream operations */
        lines
          .flatMap(line -> Arrays.stream(line.split(
            // by non-letters
            "\\P{L}+"
          )))
          .distinct()
          .filter(word -> {
            List<Character> chars = word.toLowerCase()
              .chars()
              .mapToObj(e -> (char)e)
              .distinct()
              .collect(Collectors.toList());

            return chars.size() == word.length();
          })
          .collect(Collectors.groupingBy(String::length));

      // just printing
      List<Integer> lastTwo = map.keySet().stream()
              .sorted()
              .collect(Collectors.toList());
      System.out.println("Two lists of the longest " +
                  "words with all letters different:");
      int len = lastTwo.get(lastTwo.size() - 2);
      System.out.println("length " + len + ": " + map.get(len));
      len = lastTwo.get(lastTwo.size() - 1);
      System.out.println("length " + len + ": " + map.get(len));
      /**
       * Expected outputs:
       *
       * Two lists of the longest words with all letters different:
         length 13: [subordinately]
         length 14: [outspreadingly]
       *
       *  Two lists of the longest words with all letters different:
          length 13: [bezksiężycową, chłodniejszym, ilustrowanych,
                      kapelmistrzów, pergaminowych, przykucniętej,
                      strzelającymi]
          length 14: [przykucniętego]
       */
    } catch(IOException e) {
      System.out.println("Something wrong...");
      e.printStackTrace();
      System.exit(1);
    }
  }
}