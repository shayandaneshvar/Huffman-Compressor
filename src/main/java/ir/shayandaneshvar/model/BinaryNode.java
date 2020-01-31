package ir.shayandaneshvar.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class BinaryNode implements Comparable<BinaryNode> {
    private final String characters;
    private final Integer value;
    private BinaryNode right;
    private BinaryNode left;
    private final StringBuilder huffmanValue = new StringBuilder();

    @Override
    public int compareTo(BinaryNode o) {
        if (this.value.equals(o.value)) {
            return 0;
        } else if (this.value > o.value) {
            return 1;
        } else {
            return -1;
        }
    }

    public String getHuffmanValue() {
        return huffmanValue.toString();
    }

    public String appendToHuffmanValue(String str) {
        return huffmanValue.append(str).toString();
    }
}
