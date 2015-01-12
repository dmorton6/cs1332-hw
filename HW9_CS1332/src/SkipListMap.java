
import java.util.*;


public class SkipListMap<K extends Comparable<? super K>, V> 
                           implements IListMap<K, V> {

    protected CoinFlipper flipper;
    protected int size;
    
    private ArrayList<Node> nodes;
    private static final String NULL_INPUT = "Null input argument";
    
    /**
     * constructs a SkipListMap object that stores keys in ascending order. When
     * a key value pair is inserted, the flipper is called until it returns a
     * tails. If, for an pair, the flipper returns n heads, the corresponding
     * node has n + 1 levels
     *
     * the skip list should have an empty node at the beginning and end that do
     * not store any data. These are called sentinel nodes.
     *
     * @param flipper the source of randomness
     */
    public SkipListMap(CoinFlipper flipper) {
        this.size = 0;
        this.flipper = flipper;
        this.nodes = new ArrayList<>();
        
        nodes.add(new Node(null, null)); // sentinel node 1
        nodes.add(new Node(null, null)); // sentinel node 2
    }

    @Override
    public K firstKey() {
        if (size == 0) return null;
        
        // Note: index of 1 because the very first node is null
        return (K) nodes.get(1).getKey();
    }

    @Override
    public K lastKey() {
        if (size == 0) return null;
        
        // Note: index of "size" because the very last node is null
        return (K) nodes.get(size).getKey();
    }

    @Override
    public boolean containsKey(K key) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException(NULL_INPUT);
        }
        
        for (Node node : nodes) {
            if (node.getKey() == key) {
                return true;
            }
        }
        // key not found
        return false;
    }

    @Override
    public boolean containsValue(V value) throws IllegalArgumentException {
        if (value == null) {
            throw new IllegalArgumentException(NULL_INPUT);
        }
        
        for (Node node : nodes) {
            if (node.getValue() == value) {
                return true;
            }
        }
        // value not found
        return false;
    }
    
    
    /**
     * {@link IListMap#put(Comparable, Object) IListMap}
     *
     * if a node is updated, only the key value pair should be changed
     */
    @Override
    public V put(K key, V newValue) throws IllegalArgumentException {
        if (key == null || newValue == null) {
            throw new IllegalArgumentException(NULL_INPUT);
        }
        
        // number of levels of the Node
        int numLevels = 1;
        while (flipper.flipCoin() == CoinFlipper.Coin.HEADS) {
            ++numLevels;
        }
        
        if (size == 0) {
            // adding the first node to the list
            nodes.add(1, new Node(key, newValue));
            ++size;
            return newValue;
        }        
        
        K curKey;
        for (int i = 1; i <= nodes.size(); ++i) {
            curKey = (K) nodes.get(i).getKey();
            
            if (curKey == null || curKey.compareTo(key) >= 0) {
                // place the node here
                // a null value of "curKey" implies that we are inserting the 
                // node at the end of the list (before the last sentinel node)
                nodes.add(i, new Node(key, newValue));
                ++size;
                return newValue;
            }
        }
        
        // This line should never be reached
        return null;
    }

    @Override
    public V get(K key) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException(NULL_INPUT);
        }       
        
        for (Node node : nodes) {
            if (node.getKey() == key) {
                return (V) node.getValue();
            }
        }
        // key not found
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        nodes.clear();
        
        // re-create sentinel nodes
        nodes.add(new Node(null, null)); // sentinel node 1
        nodes.add(new Node(null, null)); // sentinel node 2
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>(size);
        
        for (Node node : nodes) {
            keys.add((K) node.getKey());
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
        Collection<V> values = new ArrayList<>(size);
        
        for (Node node : nodes) {
            values.add((V) node.getValue());
        }
        return values;
    }

    @Override
    public V remove(K key) throws IllegalArgumentException {
        if (key == null) {
            throw new IllegalArgumentException(NULL_INPUT);
        }
        
        boolean nodeFound = false;
        Node nodeToRemove = null;
        
        for (Node node : nodes) {
            if (node.getKey() == key) {
                // we have found the node to remove
                nodeToRemove = node;
                nodeFound = true;
                break;
            }
        }
        
        if (nodeFound) {
            V value = (V) nodeToRemove.getValue();
            nodes.remove(nodeToRemove);
            --size;
            return value;
        }
        
        return null;
    }
   
}
