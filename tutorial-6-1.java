import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import static java.lang.System.out;


public class PersonsColls {
  public static void main (String[] args) {
      List<Person> list = Arrays.asList(
          new Person("Alice", 2000),
          new Person("Brenda", 2001),
          new Person("Cecilia", 2002)
      );

      out.println(Person.isInColl(list, "Brenda", 2001));
      out.println(Person.isInColl(list, "Debby", 2001));
      out.println();

      Set<Person> tSet = new TreeSet<>(list);
      out.println(Person.isInColl(tSet, "Brenda", 2001));
      out.println(Person.isInColl(tSet, "Debby", 2001));
      out.println();

      Set<Person> hSet = new HashSet<>(list);
      out.println(Person.isInColl(hSet, "Brenda", 2001));
      out.println(Person.isInColl(hSet, "Debby", 2001));

      /**
       * expected:
       *
       *  true
          false

          true
          false

          true
          false
       */
  }
}

class Person implements Comparable<Person> {
  String name;
  int birthYear;

  Person (String name, int birthYear) {
    this.name = name;
    this.birthYear = birthYear;
  }

  /**
   * checks if collection coll contains a person
   * with given `name` and `birthYear`
   */
  public static boolean isInColl(
    Collection<Person> coll,
    String name,
    int year
  ) {
      return coll.contains(new Person(name, year));
  }

  @Override
  public boolean equals(Object ob) {
    if (ob == null || getClass() != ob.getClass()) return false;
    Person p = (Person)ob;

    return this.name.equals(p.name) && this.birthYear == p.birthYear;
  }

  @Override
  public int compareTo (Person p) {
    int nameCompare = this.name.compareTo(p.name);
    if (nameCompare == 0) {
      return this.birthYear - p.birthYear;
    }
    return nameCompare;
  }

  @Override
  public int hashCode () {
    return this.name.hashCode() + this.birthYear;
  }
}

