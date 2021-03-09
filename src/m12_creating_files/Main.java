package m12_creating_files;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class Main {
    public static void main(String[] args) {
        try {
            Path fileToCreate = FileSystems.getDefault().getPath("src", "m12_creating_files", "file1.txt");
            Files.createFile(fileToCreate);
            // jeśli chcemy zapisać jakąś treść w pliku, to korzystamy z kanałów (channels) lub strumieni (streams) - folder m08

            // tworzenie folderu:
            Path directoryToCreate = FileSystems.getDefault().getPath("src", "m12_creating_files", "Dir5/Dir6/Dir7");
            Files.createDirectories(directoryToCreate);

            // wyświetlanie atrybutów pliku:
            Path file2 = FileSystems.getDefault().getPath("src/m12_creating_files/file2.txt");
            long size = Files.size(file2);
            System.out.println("File's size is: " + size);
            System.out.println("Last modified: " + Files.getLastModifiedTime(file2));

            // pobranie wszystkich właściwości pliku za jednym razem:
            BasicFileAttributes attributes = Files.readAttributes(file2, BasicFileAttributes.class);
            System.out.println("Size: " + attributes.size());
            System.out.println("Last modified: " + attributes.lastModifiedTime());
            System.out.println("Created: " + attributes.creationTime());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
