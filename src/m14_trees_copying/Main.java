package m14_trees_copying;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {

        System.out.println("== Copy Dir2 to Dir4/Dir2Copy ==");

        Path dir2Path = FileSystems.getDefault().getPath("src" + File.separator + "m11a"
                + File.separator + "Dir2");
        Path copyPath = FileSystems.getDefault().getPath("src" + File.separator + "m11a"
                + File.separator + "Dir4" + File.separator + "Dir2Copy");
        try {
            Files.walkFileTree(dir2Path, new CopyFiles(dir2Path, copyPath));
        } catch (IOException e){
            System.out.println(e.getMessage());
        }


        // Zamiast używania File (JavaIO) lepiej używać Path (JavaNIO):
        File file = new File("src" + File.separator + "m11a" + File.separator + "file1.txt");
        Path convertedPath = file.toPath();
        System.out.println("Converted path: " + convertedPath);

    }
}
