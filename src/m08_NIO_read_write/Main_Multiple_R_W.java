package m08_NIO_read_write;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Main_Multiple_R_W {
    public static void main(String[] args) {
        // Zapis wielu danych do bufora przed zapisem do kanału (pliku(
        // oraz odczyt wielu danych z pliku do bufora
        try(FileOutputStream binFile = new FileOutputStream("JavaNIO_data.dat");
            FileChannel binChannel = binFile.getChannel()) {

            ByteBuffer buffer = ByteBuffer.allocate(100);
            byte[] outputBytes = "Hello World!".getBytes();
            buffer.put(outputBytes);
            buffer.putInt(431);
            buffer.putInt(-648);
            byte[] outputBytes2 = "Nice to meet you".getBytes();
            buffer.put(outputBytes2);
            buffer.flip();
            binChannel.write(buffer);

        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
