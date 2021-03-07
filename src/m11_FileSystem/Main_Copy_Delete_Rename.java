package m11_FileSystem;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Main_Copy_Delete_Rename {
    public static void main(String[] args) {

        try {
            // KOPIOWANIE pliku:
            Path sourceFile = FileSystems.getDefault().getPath("src", "m11a", "file1.txt");
            Path copyFile = FileSystems.getDefault().getPath("src", "m11a", "file1copy.txt");
            Files.copy(sourceFile, copyFile);
            // jeśli chcemy skopiować na miejsce innego pliku, to dodajemy trzeci parametr:
            //Files.copy(sourceFile, copyFile, StandardCopyOption.REPLACE_EXISTING);

            // kopiowanie folderu (bez zawartości!):
//            sourceFile = FileSystems.getDefault().getPath("src", "m11a", "Dir1");
//            copyFile = FileSystems.getDefault().getPath("src", "m11a", "Dir4");
//            Files.copy(sourceFile, copyFile);

            // PRZENIESIENIE pliku:
            Path fileToMove = FileSystems.getDefault().getPath("src", "m11a", "SubdirectoryFile.txt");
            Path destination = FileSystems.getDefault().getPath("src", "m11a", "Dir1", "SubFileCopy.txt");
            Files.move(fileToMove, destination);

            // ZMIANA nazwy - można użyć metody move() - ona kasuje stary plik i tworzy kopię z nową nazwą

            // SKASOWANIE pliku:
            //Files.delete(destination);
            Files.deleteIfExists(destination);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
