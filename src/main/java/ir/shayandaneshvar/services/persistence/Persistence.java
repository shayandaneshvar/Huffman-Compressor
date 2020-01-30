package ir.shayandaneshvar.services.persistence;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class Persistence {
    private CompressedFilePersistence compressed;
    private TextFilePersistence text;

    public Persistence() {
        compressed = new CompressedFilePersistence();
        text = new TextFilePersistence();
    }
}
