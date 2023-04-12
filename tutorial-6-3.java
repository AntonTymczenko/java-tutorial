/**
 * Write a program which reads a file containing an unknown number of lines
 * which look like this

    Mary 12c 78
    Jane 12c 90
    Bill 13c 68
    Kate 12c 76
    John 13c 66

  * Each line corresponds to a student with a given name, group id and test score.
  *
  * Using streams, create a map with group ids as keys and list of students
  * belonging to a given group as values; print these lists.
  *
  * Assuming that toString method in class Student is appropriately implemented,
  * the output could be something like

    Group 13c: [Bill(13c)-68, John(13c)-66]
    Group 12c: [Mary(12c)-78, Jane(12c)-90, Kate(12c)-76]
  *
  * Important: Do not use explicit loops!
 */

import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
	public static void main (String[] args) {
    Map<String, List<Student>> map = readAFile("input-for-tut-6-3.txt");

    System.out.println(map);
  }

	public static Map<String, List<Student>> readAFile (String filename) {
		BufferedReader reader;
    List<String> list = new ArrayList<String>();
    Map<String, List<Student>> map = new HashMap<>(){
      @Override
      public String toString () {
        String res = "";


        Iterator<String> keys = this.keySet().iterator();
        while (keys.hasNext()) {
          String key = keys.next();
          res = res.concat("Group " + key + ": "
            + this.get(key).toString()
            + (keys.hasNext() ? "\n" : ""));
        }

        return res;
      }
    };
    String pattern = "^(?<name>\\w+)\\s+(?<group>\\d{2}\\w)\\s+(?<score>\\d{1,3})$";

		try {
			reader = new BufferedReader(new FileReader(filename));
			String line = reader.readLine();

			while (line != null) {
        Matcher m = Pattern
          .compile(pattern)
          .matcher(line);

        m.find();
        String name = m.group("name");
        String groupId = m.group("group");
        String score = m.group("score");

        int scoreInt = Integer.parseInt(score);

        Student currentStudent = new Student(name, groupId, scoreInt);

        List<Student> group = map.getOrDefault(
          groupId,
          new ArrayList<Student>()
        );
        group.add(currentStudent);
        map.put(groupId, group);

				line = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

    return map;
	}
}

class Student {
  String name;
  String groupId;
  int score;

  Student (String name, String groupId, int score) {
    this.name = name;
    this.groupId = groupId;
    this.score = score;
  }

  @Override
  public String toString () {
    return this.name + "(" + this.groupId + ")"+ "-" + this.score;
  }
}
