/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.IntArrayData;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Daniel
 */
public class BinHeapTest {

    @Test
    public void testAdd() {
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        
        Integer two = (Integer) 2;
        Integer one = (Integer) 1;
        Integer three = (Integer) 3;
        Integer five = (Integer) 5;
        
        heap.add(five);
        heap.add(three);
        heap.add(two);
        heap.add(one);
        
        assertTrue(heap.peek() == 1);
        
        heap.remove();
        assertTrue(heap.peek() == 2);
        
        heap.remove();
        assertTrue(heap.peek() == 3);
    }

}