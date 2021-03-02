package m08_NIO_read_write;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Main_Seekable_Byte_Channel {
    public static void main(String[] args) {
        // Aby poruszać się po różnych zapisach w pliku, używamy Seekable Byte Channel
        // Seekable Byte Channel zapisuje pozycję każdego rekordu
        // dostępne metody:
//            read(ByteBuffer) - reads bytes beginning at the channel's current position, and after the read,
//                               updates the position accordingly.
//                               Note that now we're talking about the channel's position, not the byte buffer's position.
//                               Of course, the bytes will be placed into the buffer starting at its current position.
//            write(ByteBuffer) - the same as read, except it writes. There's one exception.
//                              If a datasource is opened in APPEND mode, then the first write will take place starting
//                              at the end of the datasource, rather than at position 0. After the write, the position
//                              will be updated accordingly.
//            position() - returns the channel's position.
//            position(long) - sets the channel's position to the passed value.
//            truncate(long) - truncates the size of the attached datasource to the passed value.

        try (FileOutputStream binFile = new FileOutputStream("JavaNIO_data2.dat");
            FileChannel binChannel = binFile.getChannel()){

            ByteBuffer buffer = ByteBuffer.allocate(100);

            // pierwszy rekord:
            byte[] outputBytes = "Hello World!".getBytes();
            buffer.put(outputBytes);

            // zapisujemy pozycję startową drugiej pozycji
            // pozycja startowa drugiego rekordu to liczba bajtów pierwszego rekordu (Stringa)
            long int1Position = outputBytes.length;
            buffer.putInt(333);

            // pozycja startowa trzeciego rekordu to suma liczba bajtów pierwszego i drugiego rekordu:
            long int2Position = int1Position + Integer.BYTES;
            buffer.putInt(-6793);

            // czwarty rekord:
            byte[] outputBytes2 = "Nice to meet you".getBytes();
            buffer.put(outputBytes2);

            // piąty rekord, poprzedzony zapisem jego pozycji startowej:
            long int3Position = int2Position + Integer.BYTES + outputBytes2.length;
            buffer.putInt(12);

            buffer.flip();
            binChannel.write(buffer);

            RandomAccessFile ra = new RandomAccessFile("JavaNIO_data2.dat", "rwd");
            FileChannel channel = ra.getChannel();
            ByteBuffer readBuffer = ByteBuffer.allocate(100);

            // METODA BUCHALKI:
            channel.position(int3Position);
            channel.read(readBuffer);
            readBuffer.flip();
            System.out.println("int 3 = " + readBuffer.getInt());
            // powyższa linijka to ZAPIS do konsoli, więc musi być otoczona flipami z obu stron (tam są ODCZYTY)

            readBuffer.flip();
            channel.position(int2Position);
            channel.read(readBuffer);
            readBuffer.flip();
            System.out.println("int 2 = " + readBuffer.getInt());

            // MOJA METODA:
//            channel.read(readBuffer);
//            readBuffer.flip();
//            System.out.println("int3 = " + readBuffer.getInt((int) int3Position));
//            System.out.println("int2 = " + readBuffer.getInt((int) int2Position));
//            System.out.println("int1 = " + readBuffer.getInt((int) int1Position));

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
