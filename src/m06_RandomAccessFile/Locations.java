package m06_RandomAccessFile;

import java.io.*;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Locations implements Map<Integer, Location> {
    private static Map<Integer, Location> locations = new LinkedHashMap<>();

    // Opis Tima Buchalki:
    // 1. This first four bytes will contain the number of locations (bytes 0-3)
    // 2. The next four bytes will contain the start offset of the locations section (bytes 4-7)
    // 3. The next section of the file will contain the index (the index is 1692 bytes long.  It will start at byte 8 and end at byte 1699
    // 4. The final section of the file will contain the location records (the data). It will start at byte 1700

    public static void main(String[] args) throws IOException {
        try(RandomAccessFile raf = new RandomAccessFile("locations_rand.dat", "rwd")){
            // drugi parametr powyżej (rwd) oznacza, że chcemy otworzyć plik do odczytu i zapisu
            // oraz że chcemy, aby zapisywanie odbywało się synchronicznie (to dobra praktyka)
            // za synchronizację odpowiada klasa RandomAccessFile
            raf.writeInt(locations.size());   // punkt 1 z opisu Buchalki (^)
            int indexSize = locations.size() * 3 * Integer.BYTES;
            // cyfra 3, bo każdy rekord zawiera 3 liczby:
            // 1) locationID
            // 2) pozycję (offset) danej lokacji w bazie (pliku)
            // 3) wielkość (długość) danego rekordu
            // na końcu zamieniamy to na wartość w bajtach
            int locationStart = (int) (indexSize + raf.getFilePointer() + Integer.BYTES);
            // powyżej obliczanie pozycji lokacji
            raf.writeInt(locationStart);

        }
    }

    static {
        try(ObjectInputStream locFile = new ObjectInputStream(new BufferedInputStream(new FileInputStream("locations.dat")))){
            boolean endOfFile = false;
            while (!endOfFile){
                try{
                    Location location = (Location) locFile.readObject();
                    System.out.println("Read location " + location.getLocationID() + " : " + location.getDescription());
                    System.out.println("Found " + location.getExits().size() + " exits");

                    locations.put(location.getLocationID(), location);
                } catch (EOFException e){
                    endOfFile = true;
                }
            }
        } catch (InvalidClassException e){
            System.out.println("InvalidClassException " + e.getMessage());
        } catch (IOException io) {
            System.out.println("==> IO Exception <==");
        } catch (ClassNotFoundException e){
            System.out.println("ClassNotFoundException " + e.getMessage());
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
