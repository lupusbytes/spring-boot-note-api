package com.lupusbytes.notes.service;

import org.hamcrest.collection.IsMapContaining;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
public class WordCounterTests {
    @Test
    public void GetWordCountMap() {
        // Arrange
        String input = "note is just a note";

        // Act
        Map<String, Integer> result = WordCounter.GetWordCountMap(input);

        // Assert
        assertThat(result, IsMapContaining.hasEntry("note", 2));
        assertThat(result, IsMapContaining.hasEntry("is", 1));
        assertThat(result, IsMapContaining.hasEntry("just", 1));
        assertThat(result, IsMapContaining.hasEntry("a", 1));
    }

    @Test
    public void GetWordCountMapShouldBeSortedByEntry() {
        // Arrange
        String input = "a b c d e f g";

        // Act
        Map<String, Integer> result = WordCounter.GetWordCountMap(input);

        // Assert
        int index = 0;
        for (String word : result.keySet()) {
            assertEquals(input.substring(index, index + 1), word);
            index += 2;
        }
    }
}
