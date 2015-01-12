/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Collection;
import java.util.Set;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Daniel
 */
public class SkipListMapTest {

    /**
     * Test of firstKey method, of class SkipListMap.
     */
    @Test
    public void test() {
//        Node a = new Node(1, 'A');
//        Node b = new Node(2, 'B');
//        Node c = new Node(3, 'C');
//        Node j = new Node(10, 'J');
        
        SkipListMap<Integer, Character> map = new SkipListMap(new CoinFlipper());
        
        map.put(1, 'A');
        map.put(3, 'C');
        map.put(2, 'B');
        map.put(10, 'J');
        
        assertTrue(map.firstKey() == 1);
        assertTrue(map.lastKey() == 10);
        
        assertTrue(map.containsKey(2));
        assertFalse(map.containsKey(5));
        
        assertTrue(map.containsValue('C'));
        assertFalse(map.containsValue('H'));
        
        assertTrue(map.get(2) == 'B');
        
        assertTrue(map.size() == 4);
        
        map.remove(3);
        assertTrue(map.size() == 3);
        assertFalse(map.containsValue('C'));
        
        assertTrue(map.remove(65) == null);
        
        map.clear();
        assertTrue(map.size() == 0);
    }

}