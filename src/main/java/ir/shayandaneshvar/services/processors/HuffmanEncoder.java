package ir.shayandaneshvar.services.processors;

import ir.shayandaneshvar.model.BinaryNode;
import ir.shayandaneshvar.model.HuffmanTree;

import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanEncoder implements InfoExtractor<Map<Character, Integer>,
        Map<String, String>> {
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
}
