public class Main {
  public static void main (String[] args) {
    Singer s1 = new Singer("Martin"){
      @Override
      public String sing () {
        return "Arrivederci, Roma...";
      }
    };
    Singer s2 = new Singer("Joplin"){
      @Override
      public String sing () {
        return "...for me and my Bobby MacGee";
      }
    };
    Singer s3 = new Singer("Houston"){
      @Override
      public String sing () {
        return "I will always love youuuu";
      }
    };

    Singer sng[] = {s1, s2, s3};
    for (Singer s : sng) System.out.println(s);
    System.out.println("\n" + Singer.loudest(sng));
    /*
    expected:
        (1) Martin: Arrivederci, Roma...
        (2) Joplin: ...for me and my Bobby MacGee
        (3) Houston: I will always love youuuu

        (2) Joplin: ...for me and my Bobby MacGee
    */
  }
}

abstract class Singer {
  private static int counter = 1;
  public String name;
  private int number = 1;

  Singer (String name) {
    this.name = name;
    this.number = counter++;
  }

  abstract String sing ();

  public String toString() {
    return "(" + this.number + ") " + this.name + ": " + this.sing();
  }

  public static Singer loudest (Singer[] singers) {
    int index = 0;
    int max = 0;
    for (int i = 0; i < singers.length; i++) {
      String currentSong = singers[i].sing();
      int currentSongLength = currentSong.length();
      int currentLoudness = 0;
      for (int j = 0; j < currentSongLength; j++ ) {
        char c = currentSong.charAt(j);
        if (c >= 'A' && c <= 'Z') {
          currentLoudness++;
        }
      }

      if (currentLoudness > max) {
        max = currentLoudness;
        index = i;
      }
    }

    return singers[index];
  }
}
