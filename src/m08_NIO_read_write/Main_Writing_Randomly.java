package m08_NIO_read_write;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Main_Writing_Randomly {
    public static void main(String[] args) {
        try(FileOutputStream binFile = new FileOutputStream("JavaNIO_data3.dat");
            FileChannel binChannel = binFile.getChannel()) {

            long str1Pos = 0;
            byte[] outputString = "Welcome".getBytes();
            long int1Pos = outputString.length;
            long int2Pos = int1Pos + Integer.BYTES;
            long str2Pos = int2Pos + Integer.BYTES;
            byte[] outputString2 = "It was nice to meet you".getBytes();
            long int3Pos = str2Pos + outputString2.length;

            ByteBuffer intBuffer = ByteBuffer.allocate(Integer.BYTES);

            intBuffer.putInt(446);
            intBuffer.flip();
            binChannel.position(int1Pos);
            binChannel.write(intBuffer);

            intBuffer.flip();
            intBuffer.putInt(-2001);
            intBuffer.flip();
            binChannel.position(int2Pos);
            binChannel.write(intBuffer);

            intBuffer.flip();
            intBuffer.putInt(62);
            intBuffer.flip();
            binChannel.position(int3Pos);
            binChannel.write(intBuffer);

            binChannel.position(str1Pos);
            binChannel.write(ByteBuffer.wrap(outputString));
            binChannel.position(str2Pos);
            binChannel.write(ByteBuffer.wrap(outputString2));


            RandomAccessFile ra = new RandomAccessFile("JavaNIO_data3.dat", "rwd");
            FileChannel channel = ra.getChannel();
            ByteBuffer readBuffer = ByteBuffer.allocate(100);

            byte[] text1 = new byte[outputString.length];
            channel.position(str1Pos);
            channel.read(ByteBuffer.wrap(text1));
            System.out.println("str 1 = " + new String(text1));

            channel.position(int3Pos);
            channel.read(readBuffer);
            readBuffer.flip();
            System.out.println("int 3 = " + readBuffer.getInt());

            readBuffer.flip();
            channel.position(int2Pos);
            channel.read(readBuffer);
            readBuffer.flip();
            System.out.println("int 2 = " + readBuffer.getInt());

            readBuffer.flip();
            channel.position(int1Pos);
            channel.read(readBuffer);
            readBuffer.flip();
            System.out.println("int 1 = " + readBuffer.getInt());

            byte[] text2 = new byte[outputString2.length];
            channel.position(str2Pos);
            channel.read(ByteBuffer.wrap(text2));
            System.out.println("str 2 = " + new String(text2));

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
