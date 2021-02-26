package m08_NIO_read_write;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Main_BinaryFiles {
    public static void main(String[] args) {
        try(FileOutputStream binFile = new FileOutputStream("JavaNIO_data.dat");
            FileChannel binChannel = binFile.getChannel()) {

            // zapisywanie Stringa:
            byte[] outputBytes = "Hello World!".getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(outputBytes);
            int numBytes = binChannel.write(buffer);
            System.out.println("Number of bytes written: " + numBytes);

            // zapisywanie inta:
            ByteBuffer intBuffer = ByteBuffer.allocate(Integer.BYTES);
            intBuffer.putInt(113);  // putInt zapisuje inta do bufora i następnie ZMIENIA pozycję bufora
            intBuffer.flip();      // flip() resetuje bufor do pozycji 0
            numBytes = binChannel.write(intBuffer);    // write() odczytuje i zapisuje zawartość bufora - począwszy od jego pozycji
            // więc aby zapisać inta, trzeba najpierw przestawić pozycję bufora z powrotem na początek! => flip()
            // metoda wrap() - zapis byte array powyżej - automatycznie resetuje bufor do pozycji 0 - nie trzeba używać flip()
            System.out.println("Number of bytes written: " + numBytes);

            intBuffer.flip();
            intBuffer.putInt(-85364);
            intBuffer.flip();
            numBytes = binChannel.write(intBuffer);
            System.out.println("Number of bytes written: " + numBytes);

            // jeśli dokonujemy wielokrotnego zapisu do bufora zanim zapiszemy bufor do kanału (pliku),
            // to nie musimy używać flip()
            // ale trzeba go użyć za każdym razem, gdy zmieniamy odczyt na zapis (lub zapis na odczyt)
            // czyli: jak już napełniliśmy bufor zapisami, to robimy flip() i zapisujemy bufor do kanału (pliku)
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
