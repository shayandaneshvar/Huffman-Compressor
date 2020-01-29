package ir.shayandaneshvar.services.processors;

@FunctionalInterface
public interface InfoExtractor<T, U> {
    U extract(T u);
}
