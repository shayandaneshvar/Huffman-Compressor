package ir.shayandaneshvar.services.persistence;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SemiBufferedBinaryNode extends BinaryFile implements AutoCloseable {
    private List<Byte> bytes;

    SemiBufferedBinaryNode(String address, FileOption opt) throws IOException {
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
        bytes.forEach(bits -> {
            try {
                super.writeBit(bits);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        super.close();
    }
}