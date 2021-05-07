package sample.utilities;

import java.util.ArrayList;
import java.util.HashMap;


public class TrieNode implements Comparable<TrieNode> {

    private char value;
    private boolean isKey;
    private boolean isRoot;

    private HashMap<Character, TrieNode> offspring;

    public TrieNode(char value, boolean isKey) {
        this.value = value;
        this.isRoot = false;
        this.isKey = isKey;
        offspring = new HashMap<>();
    }

    public TrieNode(char value, boolean isKey, boolean isRoot) {
        this.value = value;
        this.isKey = isKey;
        this.isRoot = isRoot;
        offspring = new HashMap<>();
    }

    public TrieNode(char value) {
        this.value = value;
        this.isKey = false;
        this.isRoot = false;
        offspring = new HashMap<>();
    }

    public ArrayList<TrieNode> getAllOffspring() {
        return new ArrayList<>(offspring.values());
    }

    public TrieNode getOffspring(char x) {

        TrieNode node = null;

        if (offspring.containsKey(x)) {
            node = offspring.get(x);
        }

        return node;
    }

    public boolean setOffspring(TrieNode newNode) {
        boolean added = false;

        if (getOffspring(newNode.value) == null) {
            offspring.put(newNode.value, newNode);
            added = true;
        }
        return added;
    }

    public void setKey(boolean key) {
        isKey = key;
    }

    public boolean isKey() {
        return isKey;
    }

    public char getValue() {
        return this.value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public boolean isRoot() {
        return isRoot;
    }

    @Override
    public int compareTo(TrieNode o) {
        return Character.compare(this.getValue(), o.getValue());
    }

    public static void main(String[] args) {
        TrieNode root = new TrieNode(' ', false, true);
        TrieNode t1 = new TrieNode('H');
        TrieNode t2 = new TrieNode('i', true);
        TrieNode t3 = new TrieNode('i', true);
        t3.setKey(true);

        t2.setOffspring(t3);
        t1.setOffspring(t2);
        root.setOffspring(t1);

        boolean isSame = t3.equals(t2);
        System.out.println(isSame);


    }
}