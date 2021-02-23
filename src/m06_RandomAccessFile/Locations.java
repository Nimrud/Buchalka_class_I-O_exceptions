package m06_RandomAccessFile;

import java.io.*;
import java.util.*;

public class Locations implements Map<Integer, Location> {
    private static Map<Integer, Location> locations = new LinkedHashMap<>();
    private static Map<Integer, IndexRecord> index = new LinkedHashMap<>();
    private static RandomAccessFile ra;

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

            long indexStart = raf.getFilePointer();

            int startPointer = locationStart;
            raf.seek(startPointer);

            for (Location location : locations.values()){
                // RandomAccessFile nie ma opcji zapisu/odczytu całych obiektów,
                // więc musimy to zrobić po kawałku:
                raf.writeInt(location.getLocationID());
                raf.writeUTF(location.getDescription());
                StringBuilder builder = new StringBuilder();
                for (String direction : location.getExits().keySet()){
                    if (!direction.equalsIgnoreCase("Q")){
                        builder.append(direction);
                        builder.append(",");
                        builder.append(location.getExits().get(direction));
                        builder.append(",");
                    }
                }
                raf.writeUTF(builder.toString());

                IndexRecord record = new IndexRecord(startPointer, (int) (raf.getFilePointer() - startPointer));
                index.put(location.getLocationID(), record);

                startPointer = (int) raf.getFilePointer();
            }

            raf.seek(indexStart);
            for (Integer locationID : index.keySet()){
                raf.writeInt(locationID);
                raf.writeInt(index.get(locationID).getStartByte());
                raf.writeInt(index.get(locationID).getLength());
            }
        }
    }

    static {
        try{
            ra = new RandomAccessFile("locations_rand.dat", "rwd");
            // liczba lokacji nie jest wykorzystywana, ale to dobra praktyka, żeby zapisywać liczbę rekordów w pliku:
            int numLocations = ra.readInt();
            long locationsStartPoint = ra.readInt();

            while (ra.getFilePointer() < locationsStartPoint){
                int locationID = ra.readInt();
                int locationsStart = ra.readInt();
                int locationLength = ra.readInt();

                IndexRecord record = new IndexRecord(locationsStart, locationLength);
                index.put(locationID, record);
            }
        } catch (IOException e){
            System.out.println("IOException in static initializer " + e.getMessage());
        }

        // poniższy kod wykorzystać trzeba, gdyby pojawiła się konieczność ponownego wczytania lokacji bez stworzonego pliku locations_rand.dat
        /*
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
         */
    }

    public Location getLocation(int locationID) throws IOException {
        IndexRecord record = index.get(locationID);
        ra.seek(record.getStartByte());
        int id = ra.readInt();
        String description = ra.readUTF();
        String exits = ra.readUTF();
        String[] exitPart = exits.split(",");

        Location location = new Location(locationID, description, null);

        if (locationID != 0){
            for (int i=0; i< exitPart.length; i++){
                System.out.println("exitPart = " + exitPart[i]);
                System.out.println("exitPart [+1] = " + exitPart[i+1]);
                String direction = exitPart[i];
                int destination = Integer.parseInt(exitPart[++i]);
                location.addExit(direction, destination);
            }
        }
        return location;
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

    public void close() throws IOException {
        ra.close();
    }
}
