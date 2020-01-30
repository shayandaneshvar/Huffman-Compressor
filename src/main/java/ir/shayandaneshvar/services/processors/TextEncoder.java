package ir.shayandaneshvar.services.processors;

@FunctionalInterface
public interface TextEncoder<T, U, V> {
    V encode(T input, U dictionary);
}
