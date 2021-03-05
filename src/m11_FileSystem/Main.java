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
        printFile(path2);

        Path path3 = Paths.get("/home/krzysztof/workspace/Buchalka/OtherDirectory.txt");
        //Path path3 = FileSystems.getDefault().getPath("../OtherDirectory.txt");
        printFile(path3);
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
