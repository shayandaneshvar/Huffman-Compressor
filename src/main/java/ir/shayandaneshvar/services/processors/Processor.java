package ir.shayandaneshvar.services.processors;

import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
public class Processor {
    private CompressedInfoExtractor compressedExtractor;
    private HuffmanEncoder huffmanEncoder;
    private TextInfoExtractor textExtractor;

    public Processor() {
        compressedExtractor = new CompressedInfoExtractor();
        huffmanEncoder = new HuffmanEncoder();
        textExtractor = new TextInfoExtractor();
    }

}
