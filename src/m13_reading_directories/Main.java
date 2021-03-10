package m13_reading_directories;

import java.io.IOException;
import java.nio.file.*;

public class Main {
    public static void main(String[] args) {

        DirectoryStream.Filter<Path> filter =
                new DirectoryStream.Filter<Path>() {
                    @Override
                    public boolean accept(Path entry) throws IOException {
                        return (Files.isRegularFile(entry));
                        // zwraca tylko pliki (pomija foldery)
                    }
                };
        Path directory = FileSystems.getDefault().getPath("src/m11a");

//        try (DirectoryStream<Path> contents = Files.newDirectoryStream(directory, "*.{dat,txt}")) {
//            // powyżej jako drugi parametr (nieobowiązkowy) możemy podać typ poszukiwanych plików
//            for (Path file : contents) {
//                System.out.println(file.getFileName());
//            }
        try (DirectoryStream<Path> contents = Files.newDirectoryStream(directory, filter)) {
            for (Path file : contents) {
                System.out.println(file.getFileName());
            }
        } catch (IOException | DirectoryIteratorException e) {
            e.printStackTrace();
        }
    }
}
