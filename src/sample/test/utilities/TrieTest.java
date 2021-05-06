package sample.test.utilities;

import org.junit.Test;
import sample.utilities.Trie;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class TrieTest {

    private Trie trie;

    public TrieTest() {
        trie = new Trie();
        trie.add("Hello");
        trie.add("Hei");
        trie.add("GoodBye");
        trie.add("God");
    }

    @Test
    public void sizeTest() {
        assertEquals(4, trie.size());
    }

    @Test
    public void isEmptyTest() {
        assertFalse(trie.isEmpty());
    }


    @Test
    public void containsTest() {
        assertTrue(trie.contains("Hei"));
        assertFalse(trie.contains("abcdefg"));
        assertTrue(trie.contains("Goodbye"));
    }

    @Test
    public void addTest() {

        assertTrue(trie.add("Hallo"));
        assertTrue(trie.contains("Hallo"));
        assertFalse(trie.add("Hello"));
    }

    @Test
    public void addAllTest() {

        trie.addAll(Arrays.asList("Bye", "Hola", "Bonjour"));

        assertTrue(trie.contains("Bye"));
        assertTrue(trie.contains("Hola"));
        assertTrue(trie.contains("Bonjour"));
    }

    @Test
    public void getKeysFromPrefixTest() {
        ArrayList<String> keys = trie.getKeysFromPrefix("He");

        assertEquals(keys.size(), 2);
        assertEquals(keys.get(0), "hei");
        assertEquals(keys.get(1), "hello");
    }


}
