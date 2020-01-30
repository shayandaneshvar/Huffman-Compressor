package ir.shayandaneshvar.model;

import com.sun.istack.internal.NotNull;

import java.util.*;

public class HuffmanTree implements Iterable<BinaryNode> {
    @NotNull
    private final BinaryNode root;
    private Iterator<BinaryNode> preOrderIterator;

    private void prepareIterator() {
        preOrderIterator = new Iterator<BinaryNode>() {
            private Stack<BinaryNode> stack = new Stack<>();
            private boolean newlyInitialized = true;

            @Override
            public boolean hasNext() {
                handleInit();
                return !stack.isEmpty();
            }

            private void handleInit() {
                if (newlyInitialized) {
                    stack.push(root);
                    newlyInitialized = false;
                }
            }

            @Override
            public BinaryNode next() {
                handleInit();
                if (hasNext()) {
                    BinaryNode node = stack.pop();
                    Optional.ofNullable(node.getRight()).ifPresent(x -> {
                        stack.push(x);
                        System.out.println(stack.peek());
                    });
                    Optional.ofNullable(node.getLeft()).ifPresent(z -> stack.push(z));
                    return node;
                } else {
                    throw new NoSuchElementException("No more elements!");
                }
            }
        };
    }

    public HuffmanTree(BinaryNode node) {
        root = node;
//        prepareIterator();
    }

    /**
     *
     * @return a dictionary
     */
    public Map<String, String> encode() {
        calculateHuffmanValues(this);
        Map<String, String> map = new HashMap<>();
        Iterator<BinaryNode> iterator = iterator();
        while (iterator.hasNext()) {
            BinaryNode node = iterator.next();
            map.put(node.getCharacters(), node.getHuffmanValue());
        }
        Iterator<String> keyItr = map.keySet().iterator();
        while (keyItr.hasNext()) {
            String string = keyItr.next();
            if (string.length() > 1) {
                keyItr.remove();
            }
        }
        return map;
    }

    public static void calculateHuffmanValues(HuffmanTree tree) {
        Iterator<BinaryNode> iterator = tree.iterator();
        while (iterator.hasNext()) {
            BinaryNode next = iterator.next();
            Optional.ofNullable(next.getLeft()).ifPresent(x -> x.appendToHuffmanValue(next.getHuffmanValue() + "0"));
            Optional.ofNullable(next.getRight()).ifPresent(z -> z.appendToHuffmanValue(next.getHuffmanValue() + "1"));
        }
    }

    public BinaryNode getRoot() {
        return root;
    }

    /**
     * @return a pre-Order iterator
     */
    @Override
    public Iterator<BinaryNode> iterator() {
        prepareIterator();
        return preOrderIterator;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Pre-Order : ");
        Iterator<BinaryNode> iterator = iterator();
        while (iterator.hasNext()) {
            stringBuilder.append("(" + iterator.next().getCharacters() + ")");
        }
        return stringBuilder.toString();
    }
}
