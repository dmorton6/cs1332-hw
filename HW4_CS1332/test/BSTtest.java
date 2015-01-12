/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Daniel
 */
public class BSTtest {

    public BSTtest() {
    }

    @Test
    public void testAdd0() {
        BST<Integer> bst = new BST<>();
        bst.add(3);
        bst.add(5);
        bst.add(2);
        bst.add(null);
        bst.add(4);
        
        List<Integer> post = bst.postOrder();
        
        for (Integer integer : post) {
            System.out.println(integer);
        }
        
        // bst.reconstruct(bst.preOrder(), bst.postOrder());
        
        // test remove method
        /*
        assertTrue(bst.get(4) == 4);
        bst.remove(5);
        bst.remove(2);
        assertTrue(bst.get(2) == null);
        bst.remove(4);
        bst.remove(null);
        bst.remove(3);
        
        assertTrue(bst.isEmpty());
        */
    }
}
