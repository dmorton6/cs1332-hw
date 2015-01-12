/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Daniel
 */
public class hashTableTest {
    
    @Test
    public void hashTest() {
        HashTable<String, Integer> ht = new HashTable();
        
        ht.put("hey", 2);
        ht.put("hey", 3);
        ht.put("hey", 4);
        ht.put("hey", 5);
        ht.put("cool", 3);
        
        MapEntry[] table = ht.getTable();
        assertTrue(table[7] != null);
        assertTrue(table[8] == null);
        
        ht.remove("cool");
        assertTrue(table[3] == null);
        
        ht.remove("hey");
        assertFalse(table[7] == null);
        assertTrue(table[7].getNext().getNext() != null);
        assertTrue(table[7].getNext().getNext().getNext() == null);
    }
}
