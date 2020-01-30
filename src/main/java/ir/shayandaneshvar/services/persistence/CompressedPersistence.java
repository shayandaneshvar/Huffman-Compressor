package ir.shayandaneshvar.services.persistence;

public class CompressedPersistence implements Persistence<String, String> {
    private static final String EXTENSION = ".shct";

    public static String getEXTENSION() {
        return EXTENSION;
    }

    @Override
    public void write(String address, String s) {
        throw new UnsupportedOperationException();

    }

    @Override
    public String read(String address) {
        throw new UnsupportedOperationException();
    }
}
