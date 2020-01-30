package ir.shayandaneshvar.services.persistence;

import java.io.IOException;

public interface FilePersistence<U, T> {
    void write(U address, T t)throws IOException;

    T read(U address) throws IOException;
}
