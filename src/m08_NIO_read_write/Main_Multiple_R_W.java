package m08_NIO_read_write;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Main_Multiple_R_W {
    public static void main(String[] args) {
        // Zapis wielu danych do bufora przed zapisem do kanału (pliku(
        // oraz odczyt wielu danych z pliku do bufora
        try(FileOutputStream binFile = new FileOutputStream("JavaNIO_data2.dat");
            FileChannel binChannel = binFile.getChannel()) {

            // ZAPIS:
            ByteBuffer buffer = ByteBuffer.allocate(100);
            byte[] outputBytes = "Hello World!".getBytes();
            buffer.put(outputBytes);
            buffer.putInt(431);
            buffer.putInt(-648);
            byte[] outputBytes2 = "Nice to meet you".getBytes();
            buffer.put(outputBytes2).putInt(133).putInt(-26);    // chaining
            buffer.flip();
            // flip() używamy, bo w tym momencie kończymy zapis do bufora
            // następnie ODCZYTUJEMY z bufora, aby zapisać jego zawartość do kanału
            // każda zmiana odczyt-zapis musi być poprzedzona .flip()
            binChannel.write(buffer);

            // ODCZYT:
            RandomAccessFile ra = new RandomAccessFile("JavaNIO_data2.dat", "rwd");
            FileChannel channel = ra.getChannel();
            ByteBuffer readBuffer = ByteBuffer.allocate(100);
            // nowy bufor tworzymy, aby mieć pewność, że odczytujemy zawartość z pliku,
            // a nie z bufora, którego użyliśmy wcześniej do zapisu
            channel.read(readBuffer);
            readBuffer.flip();
            byte[] inputString = new byte[outputBytes.length];
            readBuffer.get(inputString);
            System.out.println("inputString = " + new String(inputString));
            System.out.println("int1 = " + readBuffer.getInt());
            System.out.println("int2 = " + readBuffer.getInt());
            byte[] inputString2 = new byte[outputBytes2.length];
            readBuffer.get(inputString2);
            System.out.println("inputString2 = " + new String(inputString2));
            System.out.println("int3 = " + readBuffer.getInt());
            System.out.println("int4 = " + readBuffer.getInt());
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
