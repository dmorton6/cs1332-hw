/**
 *
 * @author Daniel Morton
 * @version 1.0
 */
public class Node<K extends Comparable<? super K>, V> {

    // Instance variables
    private K key;
    private V value;
    
    /**
    * Parameterless constructor for the Node class
    */
    public Node (K key, V value) {
       this.key = key;
       this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
