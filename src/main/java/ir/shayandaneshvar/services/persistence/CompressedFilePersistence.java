package ir.shayandaneshvar.services.persistence;

import javafx.util.Pair;

import java.io.IOException;

public class CompressedFilePersistence implements FilePersistence<String,
        Pair<String, String>> {
    private static final String EXTENSION = ".shct";

    public static String EXTENSION() {
        return EXTENSION;
    }

    /**
     * @param address
     * @param headerAndInfo (header,Info)=> h=> ex:pwd - i: [dic and cipher]
     */
    @Override
    public void write(String address, Pair<String, String> headerAndInfo) {
        String[] strArr = headerAndInfo.getValue().split("===");
        String header = strArr[0];
        String cipher = strArr[1];
        try (BinaryFile binaryFile = new BinaryFile(address, FileOption.WRITE)) {
            String buffer = headerAndInfo.getKey() + "===" + header;//appending header
            binaryFile.writeChars(buffer);//end of buffer
            binaryFile.writeBits(cipher);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param address file
     * @return (buffer, cipher)=>buffer => (pwd and more === dic)
     * @throws IOException when the format is wrong
     */
    @Override
    public Pair<String, String> read(String address) throws IOException {
        if (!address.contains(EXTENSION())) {
            throw new IOException("File format not correct");
        }
        try (BinaryFile bf = new BinaryFile(address, FileOption.READ)) {
            String buffer = bf.readChars();//contatins stuff like pwd and dic
            String cipher = bf.readBits();// cipher only
            return new Pair<>(buffer, cipher);
        }
    }
}
