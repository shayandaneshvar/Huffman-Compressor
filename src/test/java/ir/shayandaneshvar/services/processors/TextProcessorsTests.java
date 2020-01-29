package ir.shayandaneshvar.services.processors;

import ir.shayandaneshvar.model.BinaryNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.PriorityQueue;

public class TextProcessorsTests {
    private static TextInfoExtractor textInfoExtractor;
    private static CompressedInfoExtractor compressedInfoExtractor;
    private static HuffmanEncoder huffmanEncoder;
    private static Map<Character, Integer> map;
    private static final String string = "Hello World";

    @BeforeAll
    public static void init() {
        textInfoExtractor = new TextInfoExtractor();
        compressedInfoExtractor = new CompressedInfoExtractor();
        huffmanEncoder = new HuffmanEncoder();
    }

    @BeforeEach
    public void prepare() {
        map = textInfoExtractor.extract(string);
    }

    @Test
    public void testTextInfoExtractor() {
        Assertions.assertEquals(map.get('l'), 3);
        Assertions.assertEquals(map.get('H'), 1);
        Assertions.assertEquals(map.get('e'), 1);
        Assertions.assertEquals(map.get('r'), 1);
        Assertions.assertEquals(map.get('W'), 1);
        Assertions.assertEquals(map.get('d'), 1);
        Assertions.assertEquals(map.get('w'), null);
        Assertions.assertEquals(map.get('o'), 2);
    }

    @Test
    public void testHuffmanEncoder() {
        Map<String, String> encoded = huffmanEncoder.extract(map);
        encoded.forEach((x, y) -> System.out.println(x + "=>" + y));
    }

    @Test
    public void testPriority() {
        BinaryNode node = new BinaryNode("a", 1);
        BinaryNode node1 = new BinaryNode("b", 2);
        BinaryNode node2 = new BinaryNode("c", 3);
        PriorityQueue<BinaryNode> queue =
                new PriorityQueue<>((binaryNode, o) -> -1 * binaryNode.compareTo(o));
        queue.add(node);
        queue.add(node1);
        queue.add(node2);
        Assertions.assertEquals(queue.poll().getValue(),3);
        Assertions.assertEquals(queue.poll().getValue(),2);
        Assertions.assertEquals(queue.poll().getValue(),1);
    }
}
