package m03_IO_basics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Locations implements Map<Integer, Location> {
    private static Map<Integer, Location> locations = new HashMap<>();

    // wyjątki muszą być obsłużone albo w bloku catch, albo w deklaracji metody,
    // czyli za pomocą throws IOException:
    // public static void main(String[] args) throws IOException {}
    // (wtedy już nie trzeba używać bloku catch)
    public static void main(String[] args) throws IOException {

        /*
        FileWriter localFile = null;
        try{
            localFile = new FileWriter("locations.txt");
            for (Location location : locations.values()){
                localFile.write(location.getLocationID() + "," + location.getDescription() + "\n");
            }
        } catch (IOException e){
            System.out.println("In catch block");
            e.printStackTrace();
        } finally {
            // blok try musi mieć też blok catch i/lub finally (ewentualnie throws w deklaracji metody)
            // finally wykona się zawsze po bloku try
            System.out.println("In finally block");
            try{
                // bardzo ważne jest zamknięcie zapisu!
                System.out.println("Attempt to close localFile");
                if (localFile != null){
                    localFile.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
         */

        // kod, który jest powyżej, ale tym razem przy wykorzystaniu try with resources
        // w deklaracji metody trzeba zrobić obsługę wyjątków (throws)
        try(FileWriter localFile = new FileWriter("locations.txt");
            FileWriter dirFile = new FileWriter("directions.txt")){
            for (Location location : locations.values()){
                localFile.write(location.getLocationID() + "," + location.getDescription() + "\n");
                // dla każdej z lokacji wypisujemy też możliwe wyjścia z niej:
                for (String direction : location.getExits().keySet()){
                    dirFile.write(location.getLocationID() + "," + direction + "," + location.getExits().get(direction) + "\n");
                }
            }
        }
        // try with resources automatycznie zamknie FileWritera
    }

    // static sprawia, że jest tylko 1 kopia danych we wszystkich instancjach tej klasy
    // blok "static" jest wykonywany tylko raz, kiedy klasa jest ładowana
    static {
        try(Scanner s = new Scanner(new FileReader("locations_big.txt"))){
            s.useDelimiter(",");
            while (s.hasNextLine()){
                int loc = s.nextInt();
                s.skip(s.delimiter());
                String description = s.nextLine();
                System.out.println("imported loc: " + loc + ": " + description);
                Map<String, Integer> tempExit = new HashMap<>();
                locations.put(loc, new Location(loc, description, tempExit));
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        // próba wczytania kierunków z pliku (przy wykorzyst. BufferedReadera):
        try(Scanner s = new Scanner(new BufferedReader(new FileReader("directions_big.txt")))){
            s.useDelimiter(",");
            while (s.hasNextLine()){
//                int loc = s.nextInt();
//                s.skip(s.delimiter());
//                String direction = s.next();
//                s.skip(s.delimiter());
//                // ostatnią cyfrę odczytujemy jako String (przy wykorzyst. nextLine), bo na końcu wiersza nic nie ma:
//                String dest = s.nextLine();
//                int destination = Integer.parseInt(dest);
                String input = s.nextLine();
                String[] data = input.split(",");
                int loc = Integer.parseInt(data[0]);
                String direction = data[1];
                int destination = Integer.parseInt(data[2]);

                System.out.println(loc + ": " + direction + ": " + destination);
                Location location = locations.get(loc);
                location.addExit(direction, destination);
            }
        } catch (IOException e){
            e.printStackTrace();
        }


//        Map<String, Integer> tempExit = new HashMap<String, Integer>();
//        locations.put(0, new Location(0, "You are sitting in front of a computer learning Java",null));
//
//        tempExit = new HashMap<String, Integer>();
//        tempExit.put("W", 2);
//        tempExit.put("E", 3);
//        tempExit.put("S", 4);
//        tempExit.put("N", 5);
//        locations.put(1, new Location(1, "You are standing at the end of a road before a small brick building",tempExit));
//
//        tempExit = new HashMap<String, Integer>();
//        tempExit.put("N", 5);
//        locations.put(2, new Location(2, "You are at the top of a hill",tempExit));
//
//        tempExit = new HashMap<String, Integer>();
//        tempExit.put("W", 1);
//        locations.put(3, new Location(3, "You are inside a building, a well house for a small spring",tempExit));
//
//        tempExit = new HashMap<String, Integer>();
//        tempExit.put("N", 1);
//        tempExit.put("W", 2);
//        locations.put(4, new Location(4, "You are in a valley beside a stream",tempExit));
//
//        tempExit = new HashMap<String, Integer>();
//        tempExit.put("S", 1);
//        tempExit.put("W", 2);
//        locations.put(5, new Location(5, "You are in the forest",tempExit));
    }

    @Override
    public int size() {
        return locations.size();
    }

    @Override
    public boolean isEmpty() {
        return locations.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return locations.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return locations.containsValue(value);
    }

    @Override
    public Location get(Object key) {
        return locations.get(key);
    }

    @Override
    public Location put(Integer key, Location value) {
        return locations.put(key, value);
    }

    @Override
    public Location remove(Object key) {
        return locations.remove(key);
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Location> m) {

    }

    @Override
    public void clear() {
        locations.clear();
    }

    @Override
    public Set<Integer> keySet() {
        return locations.keySet();
    }

    @Override
    public Collection<Location> values() {
        return locations.values();
    }

    @Override
    public Set<Entry<Integer, Location>> entrySet() {
        return locations.entrySet();
    }
}
