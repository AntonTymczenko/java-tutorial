import java.util.Arrays;
import java.util.Comparator;


public class Main {
  private static <T> void printArray (String message, T[] items) {
    System.out.println("   *** " + message + "***");
    for (T item: items) {
      System.out.println(item);
    }
  }

  public static void main (String[] args) {
    Person[] persons = {
        new Person("Max",  Sex.M, Size.XL, Country.NL),
        new Person("Jan",  Sex.M, Size.S,  Country.PL),
        new Person("Eva",  Sex.F, Size.XS, Country.NL),
        new Person("Lina", Sex.F, Size.L,  Country.DE),
        new Person("Mila", Sex.F, Size.S,  Country.DE),
        new Person("Ola",  Sex.F, Size.M,  Country.PL),
    };

    Comparator<Person> sexThenSize = (p1, p2) -> {
      boolean sameSex = p1.sex == p2.sex;
      if (!sameSex) {
        return p1.sex.ordinal() - p2.sex.ordinal();
      } else {
        return p1.size.ordinal() - p2.size.ordinal();
      }
    };
    Arrays.sort(persons, sexThenSize);
    printArray("Persons by sex and then size", persons);

    Arrays.sort(persons, (p1, p2) -> {
      boolean sameSize = p1.size == p2.size;
      if (!sameSize) {
        return p1.size.ordinal() - p2.size.ordinal();
      } else {
        return p1.name.compareTo(p2.name);
      }
    });
    printArray("Persons by size and then name", persons);

    Country[] countries = Country.values();
    Arrays.sort(countries, (c1, c2) -> {
      return c1.toString().compareTo(c2.toString());
    });
    printArray("Countries by name", countries);

      /*
      should print

        *** Persons by sex and then size ***
      Eva(F, XS, Nederland)
      Mila(F, S, Deutschland)
      Ola(F, M, Polska)
      Lina(F, L, Deutschland)
      Jan(M, S, Polska)
      Max(M, XL, Nederland)
        *** Persons by size and then name ***
      Eva(F, XS, Nederland)
      Jan(M, S, Polska)
      Mila(F, S, Deutschland)
      Ola(F, M, Polska)
      Lina(F, L, Deutschland)
      Max(M, XL, Nederland)
        *** Countries by name ***
      Deutschland
      Nederland
      Polska
      */
  }
}

enum Sex {
  F,
  M
}

enum Size {
  XS,
  S,
  M,
  L,
  XL
}

enum Country {
  PL {
    @Override
    public String toString() {
      return "Polska";
    }
  },
  NL {
    @Override
    public String toString() {
      return "Nederland";
    }
  },
  DE {
    @Override
    public String toString() {
      return "Deutschland";
    }
  }
}


class Person {
  String name;
  Sex sex;
  Size size;
  Country country;

  Person (String name, Sex sex, Size size, Country country) {
    this.name = name;
    this.sex = sex;
    this.size = size;
    this.country = country;
  }

  @Override
  public String toString () {
    return this.name + "(" + this.sex.name() + ", "
      + this.size.name() + ", "
      + this.country + ")";
  }
}
