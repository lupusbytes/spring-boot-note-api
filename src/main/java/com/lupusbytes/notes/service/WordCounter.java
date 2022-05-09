package com.lupusbytes.notes.service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class WordCounter {
    public static Map<String, Integer> GetWordCountMap(String text) {
        Map<String, Integer> wordCountMap = new LinkedHashMap<>();

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
