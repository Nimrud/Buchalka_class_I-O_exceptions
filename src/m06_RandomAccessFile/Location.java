package m06_RandomAccessFile;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class Location implements Serializable {
    private final int locationID;
    private final String description;
    private final Map<String, Integer> exits;
    // przy serializacji zaleca się stworzenie pola serialVersionUID typu Long
    // mówi ono o "wersji" klasy - każda zmiana klasy oznacza konieczność zmiany "wersji"
    // poniższa wartość jest zapisywana do pliku, który tworzymy w klasie Locations,
    // a następnie jest odczytywana i porównywana z powrotem z poniższą przy starcie aplikacji
    // jeśli poniższa wartość oraz ta zapisana w pliku się różnią, to wyskakuje wyjątek
    private Long serialVersionUID = 1L;

    public Location(int locationID, String description, Map<String, Integer> exits) {
        this.locationID = locationID;
        this.description = description;
        if(exits != null) {
            this.exits = new LinkedHashMap<>(exits);
        } else {
            this.exits = new LinkedHashMap<>();
        }
        this.exits.put("Q", 0);
    }

    public int getLocationID() {
        return locationID;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, Integer> getExits() {
        return new LinkedHashMap<>(exits);
    }

    protected void addExit(String direction, int location){
        exits.put(direction, location);
    }
}