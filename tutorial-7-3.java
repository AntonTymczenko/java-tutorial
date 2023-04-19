/**
 * Write a program which defines a very simple class Person (as shown below)
 * and then, in class StreamNames, defines a static function getPersons which
 * takes a list of Strings and returns a list of Persons
    • with names taken from the input list of Strings;
    • with only names of length greater than 3;
    • with names modified so they all start with a capital letter followed
      by lowercase letters;
    • is sorted alphabetically by names;
    • contains only the first three objects obtained.
  * Implementation of the function must consist of exactly one statement: return statement.
 */


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class StreamNames {
  public static void main (String[] args) {
    List<String> list = Arrays.asList("john", "al", "KENNY", "jenny","noemi");
    System.out.println(getPersons(list));
  }

  static List<Person> getPersons(List<String> list) {
    return list.stream()
      .filter(name -> name.length() > 3)
      .map(String::toLowerCase)
      .sorted()
      .map(name -> new Person(name.substring(0,1).toUpperCase()
        + name.substring(1)))
      .limit(3)
      .collect(Collectors.toList());
  }
}

// should be in a separate file
class Person {
  private String name;

  Person(String n) { this.name = n; }

  @Override
  public String toString() { return this.name; }
}