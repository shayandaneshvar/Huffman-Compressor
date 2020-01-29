package ir.shayandaneshvar.services.processors;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class TextInfoExtractor implements InfoExtractor<String, Map<Character, Integer>> {


    @Override
    public Map<Character, Integer> extract(String str) {
        Map<Character, Integer> map = new HashMap<>();
        Set<Character> characters = new TreeSet<>();
        for (char c : str.toCharArray()) {
            characters.add(c);
        }
        characters.forEach(c -> {
            int count = 0;
            for (char character : str.toCharArray()) {
                if (character == c) {
                    count++;
                }
            }
            System.out.println(count);
            map.put(c, count);
        });
        return map;
    }
}
