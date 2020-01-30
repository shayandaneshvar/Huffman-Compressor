package ir.shayandaneshvar.services;

import ir.shayandaneshvar.services.persistence.Persistence;
import ir.shayandaneshvar.services.processors.Processor;
import ir.shayandaneshvar.services.security.Security;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public final class ServiceProvider {
    private static final ServiceProvider PROVIDER = new ServiceProvider();
    private Persistence persistence;
    private Security security;
    private Processor processor;

    private ServiceProvider() {
        persistence = new Persistence();
        security = new Security();
        processor = new Processor();
    }

    public static ServiceProvider provide() {
        return PROVIDER;
    }
}
