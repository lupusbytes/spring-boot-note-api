package com.lupusbytes.notes.service;

import java.util.HashMap;
import java.util.Map;

public class WordCounter {
    public static Map<String, Integer> GetWordCountMap(String text) {
        HashMap<String, Integer> wordCountMap = new HashMap<>();

        for(String word : text.split(" ")) {
            word = word.toLowerCase();
            if (wordCountMap.containsKey(word)) {
                wordCountMap.compute(word, (key, oldCount) -> oldCount + 1);
            } else {
                wordCountMap.put(word, 1);
            }
        }
        return wordCountMap;
    }
}
