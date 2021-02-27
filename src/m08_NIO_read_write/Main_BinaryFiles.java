package m08_NIO_read_write;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
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


            // ODCZYT - przy użyciu IO (a nie NIO):
            RandomAccessFile ra = new RandomAccessFile("JavaNIO_data.dat", "rwd");
            byte[] inputArray = new byte[outputBytes.length];
            ra.read(inputArray);
            System.out.println(new String(inputArray));

            long int1 = ra.readInt();
            long int2 = ra.readInt();
            System.out.println(int1);
            System.out.println(int2);

            // ODCZYT - przy użyciu pakietu NIO:
            RandomAccessFile ra2 = new RandomAccessFile("JavaNIO_data.dat", "rwd");
            FileChannel channel = ra2.getChannel();
            buffer.flip();
            long numBytesRead = channel.read(buffer);
            System.out.println("outputBytes: " + new String(outputBytes));

            // odczyt - druga metoda odczytu Stringa, z wykorzystaniem buffer.array()
            if (buffer.hasArray()){
                System.out.println("outputBytes [buffer.array()]: " + new String(buffer.array()));
            }

            // Absolute read:
            intBuffer.flip();
            channel.read(intBuffer);
            System.out.println("First int: " + intBuffer.getInt(0));
            intBuffer.flip();
            channel.read(intBuffer);
            System.out.println("Second int: " + intBuffer.getInt(0));
            // w absolute read podajemy pozycję indeksu, od którego bufor ma zacząć odczyt
            // redukuje to liczbę użyć metody .flip()

            // Relative read:
            //            intBuffer.flip();
            //            numBytesRead = channel.read(intBuffer);
            //            intBuffer.flip();
            //            System.out.println("First int: " + intBuffer.getInt());
            //            intBuffer.flip();
            //            numBytesRead = channel.read(intBuffer);
            //            intBuffer.flip();
            //            System.out.println("Second int: " + intBuffer.getInt());

            // ponieważ nie używamy tu try with resources, to na koniec trzeba pozamykać zasoby:
            channel.close();
            ra.close();

        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
