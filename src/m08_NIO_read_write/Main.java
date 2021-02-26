package m08_NIO_read_write;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try{
//            FileInputStream file = new FileInputStream("JavaNIO_data.txt");
//            FileChannel channel = file.getChannel();
            Path dataPath = FileSystems.getDefault().getPath("JavaNIO_data.txt");

            // zapis do pliku:
            Files.write(dataPath, "\nLine 4".getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
            // metoda write() zapisuje Stringa w postaci bajtowej, stąd trzeba użyć getBytes()
            // parametru StandardOpenOption.APPEND trzeba użyć, bo inaczej write() skasuje istniejące wcześniej zapisy

            // odczyt zawartości pliku:
            List<String> lines = Files.readAllLines(dataPath);
            for (String line : lines){
                System.out.println(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
