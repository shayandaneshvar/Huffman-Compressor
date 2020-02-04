package ir.shayandaneshvar.services.persistence;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SemiBufferedBinaryFile extends BinaryFile implements AutoCloseable {
    private List<Byte> bytes;

    SemiBufferedBinaryFile(String address, FileOption opt) throws IOException {
        super(address, opt);
        bytes = new LinkedList<>();
    }

    @Override
    protected void writeBit(byte bits) {
        setEndOfHeader(true);
        bytes.add(bits);
        setPosition((short) (getPosition() + 8));
        setTotalBits((short) (getTotalBits() + 8));
    }

    @Override
    public void close() throws IOException {
        byte[] byteArray = new byte[bytes.size()];
        AtomicInteger index = new AtomicInteger(0);
        bytes.forEach(bits -> {
            byteArray[index.getAndIncrement()] = bits;
        });
        getRandomAccessFile().write(byteArray);
        super.close();
    }
}