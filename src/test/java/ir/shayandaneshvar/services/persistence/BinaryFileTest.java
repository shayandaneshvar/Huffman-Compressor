package ir.shayandaneshvar.services.persistence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.RandomAccessFile;

public class BinaryFileTest {
    private static String save = "abcd\n10101101001101111111011";
    private static String ad = "C:\\Users\\TOP\\Desktop\\AP " +
            "Assignments\\huffmancompressor\\src\\test\\java\\ir" +
            "\\shayandaneshvar\\services\\persistence\\sTest.bin";

    @Test
    public void testReadAndWrite() throws IOException {
        BinaryFile bf = new BinaryFile(ad, FileOption.WRITE);
        String chars = save.substring(0, 5);
        System.err.println("writing chars :" + chars);
        String ints = save.substring(5);
        System.err.println("writing ints :" + ints);
        bf.writeChars(chars);
        bf.writeBits(ints);
        System.err.println("wrote stuff");
        bf.close();
        System.err.println("reading stuff");
        bf = new BinaryFile(ad, FileOption.READ);
        String readChars = bf.readChars();
        System.err.println("read chars:" + readChars);
        String readInts = bf.readBits();
        System.err.println("read ints:" + readInts);
        bf.close();
        Assertions.assertEquals(chars,readChars);
        Assertions.assertEquals(ints,readInts);

    }

    @Test
    public void testing() throws IOException {
        RandomAccessFile raf = new RandomAccessFile(ad, "rw");
        String s = "abc";
        byte z = 3;
        byte x = 0;
        byte y = 1;
        for (char c : s.toCharArray()) {
            raf.writeChar(c);
        }
        raf.writeByte(z);
        raf.writeByte(x);
        raf.writeByte(y);
        raf.seek(0);
        System.out.println(String.valueOf(raf.readChar()) + String.valueOf(raf.readChar()) + String.valueOf(raf.readChar()));
        System.out.println(raf.readByte());
        System.out.println(raf.readByte());
        System.out.println(raf.readByte());
    }
}

