package ir.shayandaneshvar.services.persistence;

import lombok.Getter;

import java.io.IOException;
import java.io.RandomAccessFile;

enum FileOption {READ, WRITE}

/**
 * @author s.shayan daneshvar
 * Helper Class for reading and writing binary
 * <p>
 * BinaryFile Structure:
 * ____________________
 * | Doc Length:Short |
 * | char buffer:Short|
 * | excess Bits:Byte |
 * |__________________| _______________
 * |   header info    |  \ char buffer\
 * |  bunch of chars  |  /             \
 * |__________________|_/               \_ doc length
 * |    information   |                 |
 * | in form of bytes |                |
 * |__________________| ______________| -> excess bits written to file in a byte
 */
@Getter
public class BinaryFile implements AutoCloseable {
    private final RandomAccessFile randomAccessFile;
    private short position;
    private short charBuffer;
    private short totalBits;
    private byte excessBits;
    private boolean endOfHeader;
    private FileOption option;

    protected RandomAccessFile getRandomAccessFile() {
        return randomAccessFile;
    }

    protected void setEndOfHeader(boolean b) {
        endOfHeader = b;
    }

    protected void setPosition(short pos) {
        position = pos;
    }

    protected void setTotalBits(short bits) {
        totalBits = bits;
    }


    BinaryFile(String address, FileOption opt) throws IOException {
        endOfHeader = false;
        position = 32 + 8;
        excessBits = 0;
        option = opt;
        if (opt == FileOption.WRITE) {
            randomAccessFile = new RandomAccessFile(address, "rw");
            charBuffer = 0;
            totalBits = 40;
            randomAccessFile.writeShort(totalBits);
            randomAccessFile.writeShort(charBuffer);
            randomAccessFile.writeByte(excessBits);
        } else if (opt == FileOption.READ) {
            randomAccessFile = new RandomAccessFile(address, "r");
            totalBits = randomAccessFile.readShort();
            charBuffer = randomAccessFile.readShort();
            excessBits = randomAccessFile.readByte();
        } else {
            throw new IllegalArgumentException("Unknown option");
        }
    }

    private char readChar() throws IOException, IllegalStateException {
        if (position - 40 >= charBuffer * 8) {
            throw new IllegalStateException("No more chars in the buffer");
        }
        char c = randomAccessFile.readChar();
        position += 8;
        if (position - 40 == charBuffer * 8) {
            endOfHeader = true;
        }
        return c;
    }

    String readChars() throws IOException {
        StringBuilder result = new StringBuilder();
        while (position - 40 < charBuffer * 8) {
            result.append(readChar());
        }
        return result.toString();
    }

    private byte readBit() throws IOException {
        if (!endOfHeader) {
            throw new IllegalStateException("Still in buffer!");
        }
        position += 8;
        return randomAccessFile.readByte();
    }

    String readBits() throws IOException {
        StringBuilder result = new StringBuilder();
        int[] curBits = new int[8];
        byte mask = 1;
        do {
            byte read = readBit();
            for (int i = 0; i < 8; i++, read = (byte) (read >> 1)) {
                curBits[7 - i] = read & mask;
            }
            if (isEndOfFile()) {
                break;
            }
            for (int i = 0; i < 8; i++) {
                result.append(curBits[i]);
            }
        } while (!isEndOfFile());
        for (int i = excessBits; i < 8; i++) {
            result.append(curBits[i]);
        }
        return result.toString();
    }

    protected void writeChar(char character) throws IOException {
        if (endOfHeader) {
            throw new IllegalStateException("writing buffer ends when write " +
                    "bit(s) is called!");
        } else if (option == FileOption.READ) {
            throw new IllegalStateException("Cannot Write when opened for " +
                    "reading only!");
        }
        randomAccessFile.writeChar(character);
        totalBits += 8;
        charBuffer += 1;
    }

    protected void writeChars(String characters) throws IOException {
        for (char character : characters.toCharArray()) {
            writeChar(character);
        }
    }

    /**
     * good for calling only once!
     * requires edit for more calling
     *
     * @param string of integers in binary form
     * @throws IOException thrown under different circumstances
     */
    protected void writeBits(String string) throws IOException {
        if (option == FileOption.READ) {
            throw new IllegalStateException("Cannot Write when opened for " +
                    "reading only!");
        }
        byte bits = 0;
        for (int i = 0; i < string.length(); i++) {
            if (i != 0 && i % 8 == 0) {
                writeBit(bits);
                bits = 0;
            }
            byte num = (byte) Integer.parseInt(String.valueOf(string.toCharArray()[i]));
            bits = (byte) (num | bits << 1);
        }
        writeBit(bits);
        excessBits = (byte) ((8 - string.length() % 8) % 8);
    }

    protected void writeBit(byte bits) throws IOException {
        endOfHeader = true;
        randomAccessFile.writeByte(bits);
        position += 8;
        totalBits += 8;
    }

    /**
     * use with caution!
     *
     * @return whether the point is at the end of file
     */
    private boolean isEndOfFile() {
        return position == totalBits;
    }

    @Override
    public void close() throws IOException {
        if (option == FileOption.WRITE) {
            randomAccessFile.seek(0);
            randomAccessFile.writeShort(totalBits);//doc len
            randomAccessFile.writeShort(charBuffer);// char buffer
            randomAccessFile.writeByte(excessBits);
        }
        randomAccessFile.close();
    }
}