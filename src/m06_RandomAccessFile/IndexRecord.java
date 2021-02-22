package m06_RandomAccessFile;

public class IndexRecord {
    private int startByte;
    private int length;

    public IndexRecord(int startByte, int length) {
        this.startByte = startByte;
        this.length = length;
    }

    public int getStartByte() {
        return startByte;
    }

    public IndexRecord setStartByte(int startByte) {
        this.startByte = startByte;
        return this;
    }

    public int getLength() {
        return length;
    }

    public IndexRecord setLength(int length) {
        this.length = length;
        return this;
    }
}
