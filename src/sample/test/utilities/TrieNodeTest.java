package sample.test.utilities;

import org.junit.Test;
import sample.utilities.TrieNode;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TrieNodeTest {

    private TrieNode root;
    private TrieNode t1;
    private TrieNode t2;
    private TrieNode t3;
    private TrieNode t4;

    public TrieNodeTest() {
        root = new TrieNode(' ', false, true);
        t1 = new TrieNode('H');
        t2 = new TrieNode('i', true);
        t3 = new TrieNode('i', true);
        t4 = new TrieNode('e', true);

        t2.setOffspring(t3);
        t1.setOffspring(t2);
        t1.setOffspring(t4);
        root.setOffspring(t1);

    }

    @Test
    public void getOffspringTest() {
        TrieNode offspring = t1.getOffspring('i');

        assertEquals(t2, offspring);
    }

    @Test
    public void setOffspringTest() {
        TrieNode newNode = new TrieNode('i');
        t4.setOffspring(newNode);

        assertEquals(newNode, t4.getOffspring('i'));
    }

    @Test
    public void getAllOffspringTest() {
        ArrayList<TrieNode> allOffspring = t1.getAllOffspring();

        assertEquals(t4, allOffspring.get(0));
        assertEquals(t2, allOffspring.get(1));

    }

    @Test
    public void getValueTest() {
        TrieNode n = new TrieNode('a');
        assertEquals('a', n.getValue());
    }


}
