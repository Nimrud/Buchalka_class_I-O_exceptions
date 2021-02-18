package m03a_IO_challenge;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
Challenge:
Part 1:
Modify the program so that it uses a BufferedReader (not Scanner) to read in the locations data.
Part 2:
Modify the main method of the Locations class so that it uses a BufferedWriter to write data.
Open the locations_1.txt and directions_1.txt files to check that the data has been written successfully.
You will then need to make another change to the program to allow for the 0 (Quit) exits
before using the newly created files.
Hint: You may want to change the three instances of HashMap to LinkedHashMap in the Location class
and one instance in the Locations class so that you can compare the files more easily.
 */
public class Main {
    private static Locations locations = new Locations();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Map<String, String> vocabulary = new HashMap<>();
        vocabulary.put("QUIT", "Q");
        vocabulary.put("NORTH", "N");
        vocabulary.put("SOUTH", "S");
        vocabulary.put("WEST", "W");
        vocabulary.put("EAST", "E");


        int loc = 1;
        while(true) {
            System.out.println(locations.get(loc).getDescription());

            if(loc == 0) {
                break;
            }

            Map<String, Integer> exits = locations.get(loc).getExits();
            System.out.print("Available exits are ");
            for(String exit: exits.keySet()) {
                System.out.print(exit + ", ");
            }
            System.out.print(" ||  Your choice: ");

            String direction = scanner.nextLine().toUpperCase();
            if(direction.length() > 1) {
                String[] words = direction.split(" ");
                for(String word: words) {
                    if(vocabulary.containsKey(word)) {
                        direction = vocabulary.get(word);
                        break;
                    }
                }
            }

            if(exits.containsKey(direction)) {
                loc = exits.get(direction);

            } else {
                System.out.println("You cannot go in that direction");
            }
        }
    }
}