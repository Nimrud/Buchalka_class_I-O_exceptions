package m09_copying_files;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Main {
    public static void main(String[] args) {
        try (FileOutputStream binFile = new FileOutputStream("JavaNIO_data3.dat");
             FileChannel binChannel = binFile.getChannel()){

            long str1Pos = 0;
            byte[] outputString = "Welcome Home".getBytes();
            long int1Pos = outputString.length;
            long str2Pos = int1Pos + Integer.BYTES;
            byte[] outputString2 = "Goodbye".getBytes();

            ByteBuffer intBuffer = ByteBuffer.allocate(Integer.BYTES);

            intBuffer.putInt(5577);
            intBuffer.flip();
            binChannel.position(int1Pos);
            binChannel.write(intBuffer);

            binChannel.position(str1Pos);
            binChannel.write(ByteBuffer.wrap(outputString));
            binChannel.position(str2Pos);
            binChannel.write(ByteBuffer.wrap(outputString2));

            // plik wyjściowy (odczyt):
            RandomAccessFile ra = new RandomAccessFile("JavaNIO_data3.dat", "rwd");
            FileChannel channel = ra.getChannel();

            // kopia:
            RandomAccessFile copyFile = new RandomAccessFile("dataCopy.dat", "rw");
            FileChannel copyChannel = copyFile.getChannel();
            channel.position(0);
            // 2 opcje kopiowania:
            // transferFrom()
            long numTransferred = copyChannel.transferFrom(channel,0, channel.size());
            // transferTo()
            //long numTransferred = channel.transferTo(0, channel.size(), copyChannel);
            System.out.println("Number of bytes transferred = " + numTransferred);

            channel.close();
            ra.close();
            copyChannel.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
