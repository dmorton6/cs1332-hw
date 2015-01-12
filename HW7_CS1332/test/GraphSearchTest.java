import java.util.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Daniel
 */
public class GraphSearchTest {

    /**
     * Test of search method, of class GraphSearch.
     */
    @Test
    public <T> void testSearch() {
        //Structure<T> sq = (Structure<T>) new StructureQueue<>();
        Structure<T> st = (Structure<T>) new StructureStack<>();
        
        T node1 = (T) new Integer(1);
        T node2 = (T) new Integer(2);
        T node3 = (T) new Integer(3);
        T node4 = (T) new Integer(4);
        T node5 = (T) new Integer(5);
        T node6 = (T) new Integer(6);
        T node7 = (T) new Integer(7);
        T node8 = (T) new Integer(8);
        
        List<T> neighbors1 = (List<T>) new ArrayList<T>();
        List<T> neighbors2 = (List<T>) new ArrayList<T>();
        List<T> neighbors3 = (List<T>) new ArrayList<T>();
        List<T> neighbors4 = (List<T>) new ArrayList<T>();
        List<T> neighbors5 = (List<T>) new ArrayList<T>();
        List<T> neighbors6 = (List<T>) new ArrayList<T>();
        List<T> neighbors7 = (List<T>) new ArrayList<T>();
        List<T> neighbors8 = (List<T>) new ArrayList<T>();
        
        neighbors1.add(node2);
        neighbors2.add(node1);
        
        neighbors3.add(node4);
        neighbors3.add(node5);
        
        neighbors4.add(node3);
        neighbors5.add(node3);
        
        neighbors4.add(node8);
        neighbors8.add(node4);
        
        neighbors5.add(node6);
        neighbors6.add(node5);
        
        neighbors6.add(node7);
        neighbors7.add(node6);

        Map<T, List<T>> nodeMap = (Map<T, List<T>>) new HashMap<T, List<T>>();
        nodeMap.put(node1, neighbors1);
        nodeMap.put(node2, neighbors2);
        nodeMap.put(node3, neighbors3);
        nodeMap.put(node4, neighbors4);
        nodeMap.put(node5, neighbors5);
        nodeMap.put(node6, neighbors6);
        nodeMap.put(node7, neighbors7);
        nodeMap.put(node8, neighbors8);
        
        boolean isReachable = GraphSearch.search(node3, st, nodeMap, node2);
        assertTrue(isReachable);

    }

    /**
     * Test of dsp method, of class GraphSearch.
     */


}