package ir.shayandaneshvar.services.processors;

@FunctionalInterface
public interface TextDecoder<T, U, V> {
    V decode(T cipher, U dictionary);
}
