package ir.shayandaneshvar.services.processors;

import ir.shayandaneshvar.model.BinaryNode;
import ir.shayandaneshvar.model.HuffmanTree;
import javafx.util.Pair;

import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanEncoder implements TextEncoder<String, Map<String, String>,
        String>, InfoExtractor<Map<Character, Integer>, Map<String, String>> {
    /**
     * @param input repetition of each character  ex:"c"=>2
     * @return a dictionary  ex:"a"=>"001"
     */
    @Override
    public Map<String, String> extract(Map<Character, Integer> input) {
        PriorityQueue<BinaryNode> queue = new PriorityQueue<>(BinaryNode::compareTo);
        input.forEach((x, y) -> queue.add(new BinaryNode(String.valueOf(x), y)));
        while (queue.size() > 1) {
            BinaryNode node = queue.poll();
            BinaryNode node1 = queue.poll();
            BinaryNode newNode = new BinaryNode(node.getCharacters() +
                    node1.getCharacters(), node.getValue() + node1.getValue())
                    .setLeft(node).setRight(node1);
            queue.add(newNode);
        }
        HuffmanTree tree = new HuffmanTree(queue.poll());
        return tree.encode();
    }

    /**
     * @param input      the input text
     * @param dictionary mapped values ex: "a"=>"001"
     * @return encoded text
     */
    @Override
    public String encode(final String input, Map<String, String> dictionary) {
        StringBuilder builder = new StringBuilder();
        for (char character : input.toCharArray()) {
            String mappedValue = dictionary.get(String.valueOf(character));
            assert mappedValue != null;
            builder.append(mappedValue);
        }
        return builder.toString();
    }

    public Pair<Map<String, String>, String> getDicCipherPair(Map<String,
            String> dictionary,
                                                              String cipher) {
        return new Pair<>(dictionary, cipher);
    }

    public String appendDicToCipher(Pair<Map<String, String>, String> pair) {
        StringBuilder builder = new StringBuilder();
        pair.getKey().forEach((x, y) -> builder.append(x + "=>" + y + "\n"));
        builder.append("===");//end of dic
        builder.append(pair.getValue());
        return builder.toString();
    }
}
