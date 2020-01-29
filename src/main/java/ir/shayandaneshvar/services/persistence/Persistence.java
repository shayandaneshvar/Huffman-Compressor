package ir.shayandaneshvar.services.persistence;

public interface Persistence<U, T> {
    void write(U address, T t);

    T read(U address);
}
