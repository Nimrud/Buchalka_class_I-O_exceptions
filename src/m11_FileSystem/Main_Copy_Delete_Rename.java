package m11_FileSystem;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main_Copy_Delete_Rename {
    public static void main(String[] args) {

        // KOPIOWANIE pliku:
        Path sourceFile = FileSystems.getDefault().getPath("src", "m11a", "file1.txt");
        Path copyFile = FileSystems.getDefault().getPath("src", "m11a", "file1copy.txt");
        try {
            Files.copy(sourceFile, copyFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
