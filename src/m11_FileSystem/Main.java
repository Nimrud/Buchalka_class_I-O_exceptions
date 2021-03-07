package m11_FileSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {

        Path path1 = FileSystems.getDefault().getPath("WorkingDirectoryFile.txt");
        printFile(path1);

        Path path2 = FileSystems.getDefault().getPath("src", "m11a", "SubdirectoryFile.txt");
        //Path path2 = FileSystems.getDefault().getPath("src/m11a/SubdirectoryFile.txt");
        //Path path2 = Paths.get(".", "src", "m11a", "SubdirectoryFile.txt");
        printFile(path2);

        Path path3 = Paths.get("/home/krzysztof/workspace/Buchalka/OtherDirectory.txt");
        //Path path3 = FileSystems.getDefault().getPath("../OtherDirectory.txt");
        printFile(path3);

        // kropka reprezentuje folder bieżący
        Path path4 = Paths.get(".");
        System.out.println(path4.toAbsolutePath());

        // Paths nie sprawdza, czy plik istnieje!
        Path path5 = Paths.get("aaabbb", "whatever.txt");
        System.out.println(path5.toAbsolutePath());
        // Lepiej sprawdzić na samym początku, czy jest w ogóle taki plik:
        System.out.println("Exists = " + Files.exists(path5));
        // Można też sprawdzić, czy mamy dostęp do pliku:
        System.out.println("Is readable = " + Files.isReadable(path2));
        System.out.println("Is writable = " + Files.isWritable(path2));
        System.out.println("Is executable = " + Files.isExecutable(path2));
    }

    public static void printFile(Path path){
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while((line = reader.readLine()) != null){
                System.out.println(line);
            }
            System.out.println("===");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
