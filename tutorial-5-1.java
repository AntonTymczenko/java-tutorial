import java.util.*;

public class Main {

  public static void main (String[] args) {
    /*
      Write a program creating a map of type
        Map<String,List<Car>>
      Keys are the names of salons.

      The program should print
      1. the contents of the map
      2. the make, price and salon of the least expensive car
    */
    String[] arr = {
        "salon A", "Mercedes", "130000",
        "salon B", "Mercedes", "120000",
        "salon C", "Ford", "110000",
        "salon B", "Opel", "90000",
        "salon C", "Honda", "95000",
        "salon A", "Ford", "105000",
        "salon A", "Renault", "75000"
    };

    Map<String, List<Car>> map = parseArrayToMap(arr);

    printMap(map);
    printCheapestCar(map);
  }

  public static Map<String, List<Car>> parseArrayToMap (String[] arr) {
    Map<String, List<Car>> map = new HashMap<>();

    String currentSalon = "";
    String currentMake = "";
    String currentPrice = "";

    for (int i = 0; i < arr.length; i++) {
      int j = i % 3;
      switch (j) {
        case 0: { // salon
          currentSalon = arr[i];
          break;
        }
        case 1: { // make
          currentMake = arr[i];
          break;
        }
        case 2: { // price
          currentPrice = arr[i];
          int price = Integer.parseInt(currentPrice, 10);
          Car currentCar = new Car(currentMake, price);

          List<Car> carsList = map.getOrDefault(
            currentSalon,
            new ArrayList<Car>()
          );
          carsList.add(currentCar);
          map.put(currentSalon, carsList);
        }
      }
    };

    return map;
  }

  public static void printMap (Map<String, List<Car>> map) {
    System.out.print("{");
    Iterator<Map.Entry<String, List<Car>>> itrSalons = map.entrySet().iterator();

    while (itrSalons.hasNext()) {
      Map.Entry<String, List<Car>> salonEntry = itrSalons.next();
      System.out.print(salonEntry.getKey() + "=[");
      List<Car> carsList = salonEntry.getValue();
      Car c;

      Iterator<Car> itrCars = carsList.iterator();
      while (itrCars.hasNext()) {
        c = itrCars.next();
        System.out.print(c + (itrCars.hasNext() ? ", " : ""));
      }
      System.out.println("]" + (itrSalons.hasNext() ? "," : "}"));
    }
  }

  public static void printCheapestCar (Map<String, List<Car>> map) {
    String s = map.keySet().toArray()[0].toString();
    Car c = map.get(s).get(0);

    for (String salonKey : map.keySet()) {
      List<Car> carsInSalon = map.get(salonKey);
      for (Car car : carsInSalon) {
        if (car.compareTo(c) < 0) {
          c = car;
          s = salonKey;
        }
      }
    }

    System.out.println("\n" + c.make + " in " + s + " for " + c.price);
  }
}

class Car implements Comparable<Car> {
  String make;
  int price;

  Car (String make, int price) {
    this.make = make;
    this.price = price;
  }

  @Override
  public String toString () {
    return this.make + " " + this.price;
  }

  @Override
  public int compareTo (Car c) {
    return this.price - c.price;
  }
}