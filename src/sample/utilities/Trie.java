package sample.utilities;

import java.util.*;

public class Trie implements Collection<String>, Iterable<String> {

    private TrieNode root;
    private int size;


    public Trie() {
        root = new TrieNode(' ', false, true);
        size = 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public boolean contains(Object o) {
        if (o.getClass() == String.class) {

            String key = (String) o;
            key = key.toLowerCase();

            TrieNode temp = root;

            for (char c : key.toCharArray()) {
                TrieNode next = temp.getOffspring(c);

                if (next == null)
                    return false;
                temp = next;
            }
            return true;
        } else
            return false;
    }


    @Override
    public Iterator<String> iterator() {
        return new TrieIterator(this);
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(String k) {
        TrieNode temp = root;

        k = k.toLowerCase();

        for (char c : k.toCharArray()) {

            TrieNode next = temp.getOffspring(c);

            if (next == null) {
                next = new TrieNode(c);
                temp.setOffspring(next);

            }

            temp = next;
        }

        if (!temp.isKey()) {
            temp.setKey(true);
            size++;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {

        for (String s : c) {
            boolean added = add(s);
            if (!added) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    static class TrieIterator implements Iterator<String> {

        private Trie t;
        private int c;
        private TrieNode currentNode;
        private Stack<TrieNode> s = new Stack<>();
        private HashSet<TrieNode> seen = new HashSet<>();

        public TrieIterator(Trie t) {
            this.t = t;
            this.c = 0;
            s.push(t.root);
        }

        @Override
        public boolean hasNext() {
            return c < t.size();
        }


        @Override
        public String next() {
            StringBuilder sb = new StringBuilder();
            String key = "";

            if (c < t.size()) {

                while (!s.isEmpty()) {
                    currentNode = s.peek();


                    if (currentNode.isKey() && !seen.contains(currentNode)) { // if node is a key

                        //retrieve key
                        for (TrieNode n : s) {
                            if (!n.isRoot()) {  //ignore root
                                char v = n.getValue();
//                                System.out.println(n.getValue());
                                sb.append(v);
                            }
                        }

                        key = sb.toString();
                        c++; //key found
                    }

                    seen.add(currentNode);

                    //find a child node which has not been seen yet
                    Iterator<TrieNode> it = currentNode.getAllOffspring().iterator();
                    TrieNode next = null;
                    boolean found = false;
                    while (!found && it.hasNext()) {
                        next = it.next();
                        if (!seen.contains(next))
                            found = true;
                    }

                    if (!found)
                        s.pop();  //no offspring found, go back to parent node
                    else
                        s.push(next); //add next offspring

                    if (!key.equals("")) {
                        return key;  //if word is found the  return word
                    }
                }

            }
            throw new NoSuchElementException();
        }
    }

    public TrieNode getNodeOfPrefix(String prefix) {
        TrieNode temp = root;

        for (char c : prefix.toCharArray()) {


            if (temp.getOffspring(c) != null) {

                temp = temp.getOffspring(c);
            } else {
                throw new NoSuchElementException();
            }
        }
        return temp;
    }

    public ArrayList<String> getKeysFromPrefix(String prefix) {
        ArrayList<String> keys = new ArrayList<>();

        prefix = prefix.toLowerCase();

        TrieNode temp = getNodeOfPrefix(prefix);

        Stack<TrieNode> s = new Stack<>();
        HashSet<TrieNode> seen = new HashSet<>();

        StringBuilder sb = new StringBuilder();

        s.push(temp);

        String key = "";

        while (!s.empty()) {

            temp = s.peek();

            if (temp.isKey() && !seen.contains(temp)) { // if node is a key

                boolean first = true;
                //retrieve key
                for (TrieNode n : s) {

                    if (!first) {
                        if (!n.isRoot()) {  //ignore root
                            char v = n.getValue();
                            sb.append(v);
                        }
                    } else {
                        first = false;
                    }
                }

                key = sb.toString();
            }

            seen.add(temp);

            //find a child node which has not been seen yet
            Iterator<TrieNode> it = temp.getAllOffspring().iterator();
            TrieNode next = null;
            boolean found = false;

            while (!found && it.hasNext()) {
                next = it.next();
                if (!seen.contains(next))
                    found = true;
            }

            if (!found)
                s.pop();  //no offspring found, go back to parent node
            else
                s.push(next); //add next offspring

            if (!key.equals("")) {
                keys.add(prefix + key);
                key = "";
                sb.delete(0, sb.length());
            }

        }
        return keys;
    }

}

