package m03a_IO_challenge;

import java.io.*;
import java.util.*;

public class Locations implements Map<Integer, Location> {
    private static Map<Integer, Location> locations = new LinkedHashMap<>();

    public static void main(String[] args) throws IOException {
        try(BufferedWriter buffWriterLocations = new BufferedWriter(new FileWriter("locations_1.txt"));
            BufferedWriter buffWriterDirections = new BufferedWriter(new FileWriter("directions_1.txt"))){
            for (Location location : locations.values()){
                buffWriterLocations.write(location.getLocationID() + "," + location.getDescription() + "\n");
                for (String direction : location.getExits().keySet()){
                    if (!direction.equalsIgnoreCase("Q")){
                        buffWriterDirections.write(location.getLocationID() + "," + direction + "," + location.getExits().get(direction) + "\n");
                    }
                }
            }
        }
    }

    static {
        try(BufferedReader buffRead = new BufferedReader(new FileReader("locations_big.txt"))){
            String input;
            while ((input = buffRead.readLine()) != null){
                String[] data = input.split(",");  // UWAGA! To usuwa przecinki również z tekstu!
                int loc = Integer.parseInt(data[0]);

                String description;
                StringBuilder sb = new StringBuilder();

                int tabLength = data.length;
                // tworzę nową, tymczasową tablicę i kopiuję do niej elementy z pierwszej tablicy
                // ale bez pierwszego elementu, dodaję też usunięte powyżej przecinki
                String[] tempArray = new String[tabLength-1];
                for (int i = 1; i < tabLength; i++){
                    sb.append(",").append(tempArray[i-1] = data[i]);
                }
                // usuwam dodany na początku Stringa przecinek
                description = sb.substring(1);

                System.out.println("imported loc: " + loc + ": " + description);
                Map<String, Integer> tempExit = new HashMap<>();
                locations.put(loc, new Location(loc, description, tempExit));
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        try(BufferedReader buffRead = new BufferedReader(new FileReader("directions_big.txt"))){
            String input;
            while ((input = buffRead.readLine()) != null){
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
