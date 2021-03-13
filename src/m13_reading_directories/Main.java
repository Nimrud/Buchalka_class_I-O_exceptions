package m13_reading_directories;

import java.io.File;
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
        //Path directory = FileSystems.getDefault().getPath("src/m11a");
        Path directory = FileSystems.getDefault().getPath("src" + File.separator + "m11a");  // patrz: komentarz na dole

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

        // separatory w ścieżkach różnią się pomiędzy systemami!
        // Windows: \
        // Linux, MacOS: /
        // z tego powodu lepiej nie kodować separatora "na sztywno", ale używać jednej z poniższych metod:
        String separator = File.separator;
        System.out.println(separator);

        separator = FileSystems.getDefault().getSeparator();
        System.out.println(separator);

        // TWORZENIE PLIKU TYMCZASOWEGO:
        try {
            Path tempFile = Files.createTempFile("myApp", ".appext");
            // pierwszy parametr to prefix pliku tymczasowego, drugi to suffix (rozszerzenie)
            System.out.println("Temporary file path = " + tempFile.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // PODGLĄD DYSKÓW SYSTEMOWYCH:
        Iterable<FileStore> stores = FileSystems.getDefault().getFileStores();
        for (FileStore store : stores) {
            System.out.println("Volume name/Drive letter: " + store);
            System.out.println("File store: " + store.name());
        }

        // ROOT DIRECTORY:
        System.out.println("=== Root Directory ===");
        Iterable<Path> paths = FileSystems.getDefault().getRootDirectories();
        for (Path path : paths) {
            System.out.println(path);
        }

        // ODCZYT WSZYSTKICH PLIKÓW I FOLDERÓW, włącznie z podfolderami:
        System.out.println("===> Walking tree for m11a <===");
        Path m11aPath = FileSystems.getDefault().getPath("src" + File.separator + "m11a");
        try {
            Files.walkFileTree(m11aPath, new Moving_thru_tree());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // W JAVA IO JEST INACZEJ!

        // Wydobywanie working directory w JavaIO:
        // to odpowiednik FileSystems.getDefault()
        File workingDirectory = new File("").getAbsoluteFile();
        System.out.println("Working directory: " + workingDirectory.getAbsolutePath());

        System.out.println("== printing Dir2 contents using list() ==");
        File dir2File = new File(workingDirectory, "src/m11a/Dir2");
        String[] dir2Contents = dir2File.list();
        for(int i = 0; i < dir2Contents.length; i++){
            System.out.println("i=" + i + dir2Contents[i]);
        }

        System.out.println("== printing Dir2 contents using listFiles() ==");
        File[] dir2Files = dir2File.listFiles();
        for (int i = 0; i < dir2Files.length; i++) {
            System.out.println("i=" + i + dir2Files[i].getName());
        }
    }
}
