
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * This class will represent a modified linear probing hash table. The
 * modification is specified in the comments for the put method.
 */
public class HashTable<K, V> {

    private final double MAX_LOAD_FACTOR = 0.71;
    private final int INITIAL_TABLE_SIZE = 11;
    private int size; // number of elements in the table
    private MapEntry<K, V>[] table; // backing array

    /**
     * Initialize the instance variables Initialize the backing array to the
     * initial size given
     */
    public HashTable() {
        this.size = INITIAL_TABLE_SIZE;
        this.table = new MapEntry[INITIAL_TABLE_SIZE];
    }

    /**
     * Add the key value pair in the form of a MapEntry Use the default hash
     * code function for hashing This is a linear probing hash table so put the
     * entry in the table accordingly
     *
     * Make sure to use the given max load factor for resizing Also, resize by
     * doubling and adding one. In other words:
     *
     * newSize = (oldSize * 2) + 1
     *
     * The load factor should never exceed maxLoadFactor at any point. So if
     * adding this element will cause the load factor to be exceeded, you should
     * resize BEFORE adding it. Otherwise do not resize.
     *
     * IMPORTANT Modification: If the given key already exists in the table then
     * set it as the next entry for the already existing key. This means that
     * you will never be replacing values in the hashtable, only adding or
     * removing. This is similar to external chaining
     *
     * @param key This will never be null
     * @param value This can be null
     */
    public void put(K key, V value) {
        MapEntry newEntry = new MapEntry(key, value);        
        
        if (needsResizing()) {
            int newSize = (2*size) + 1;
            MapEntry<K, V>[] newTable = new MapEntry[newSize];

            // copy all elements from the old table into the new table
            regrow(newTable);

            // update instance variables
            size = newSize;
            table = newTable;
        }

        if (contains(key)) {
            // use external chaining
            // first, get the entry with that key
            for (MapEntry<K, V> mapEntry : table) {
                if (mapEntry == null) {
                    continue;
                }

                if (mapEntry.getKey() == key) {                    
                    // chain newEntry to this
                    while (mapEntry.getNext() != null) {
                        mapEntry = mapEntry.getNext();
                    }
                    mapEntry.setNext(newEntry);
                    break;
                }
            }
        }
        
        else {
            // the following will be the index in the table
            int index = Math.abs(newEntry.getKey().hashCode() % (2^(size) - 1));

            // use linear probing if the table slot is filled
            while (table[index] != null) {
                ++index;
                if (index > size) {
                    index = 0; // to avoid out of bounds errors
                }
            }
            table[index] = newEntry;;
        }
    }

    private void regrow(MapEntry<K, V>[] tempTable) {
        int newIndex;
        int newSize = tempTable.length;

        for (int oldIndex = 0; oldIndex < size; oldIndex++) {
            if (table[oldIndex] != null) {
                newIndex = Math.abs(table[oldIndex].getKey().hashCode()
                                    % (2^(newSize) - 1));

                while (tempTable[newIndex] != null) {
                    ++newIndex;
                }
                System.out.println("regrow: adding to index " + newIndex);
                tempTable[newIndex] = table[oldIndex];
            }
        }
        table = tempTable;
    }

    private boolean needsResizing() {
        int numEntries = 0;
        for (int i = 0; i < size; i++) {
            if (table[i] != null) {
                ++numEntries;
            }
        }

        return ((double) numEntries / size) > MAX_LOAD_FACTOR;
    }

    /**
     * Remove the entry with the given key.
     *
     * If there are multiple entries with the same key then remove the last one
     *
     * @param key
     * @return The value associated with the key removed
     */
    public V remove(K key) {

        MapEntry toRemove;
        V value;

        for (int i = 0; i < size; ++i) {
            // for-each loop considered but this is easier 
            // because the index is needed later
            MapEntry<K,V> mapEntry = table[i];
            
            if (mapEntry == null) {
                continue;
            }
            
            if (mapEntry.getKey() == key) {
                // check for external chaining
                if (mapEntry.getNext() == null) {
                    // no external chaining
                    value = (V) mapEntry.getValue();
                    table[i] = null;
                } 
                else {
                    // iterate to the node whose *next* is being removed
                    while (mapEntry.getNext().getNext() != null) {
                        mapEntry = mapEntry.getNext();
                    }
                    
                    toRemove = mapEntry.getNext();
                    value = (V) toRemove.getValue();
                    
                    mapEntry.setNext(null);
                }
                return value;
            }
        }

        return null;
    }
    
    private int indexOf(MapEntry mapEntry) {
        for (int i = 0; i < size; i++) {
            if (table[i].equals(mapEntry)) {
                return i;
            }            
        }
        return -1;
    }
    

    /**
     * Checks whether an entry with the given key exists in the hash table
     *
     * @param key The key to search for
     * @return Whether the key was found
     */
    public boolean contains(K key) {
        for (MapEntry<K, V> mapEntry : table) {
            if (mapEntry != null && mapEntry.getKey() == key) {
                return true;
            }
        }

        return false;
    }

    /**
     * Return a collection of all the values
     *
     * We recommend using an ArrayList here
     *
     * @return
     */
    public Collection<V> values() {
        ArrayList<V> values = new ArrayList<>(size);

        for (MapEntry<K, V> mapEntry : table) {
            if (mapEntry == null) {
                continue;
            }

            V value = mapEntry.getValue();
            if (!values.contains(value)) {
                values.add(value);
            }
        }
        return values;
    }

    /**
     * Return a set of all the distinct keys
     *
     * We recommend using a HashSet here
     *
     * Note that the map can contain multiple entries with the same key
     *
     * @return
     */
    public Set<K> keySet() {
        HashSet<K> keys = new HashSet<>(size);

        for (MapEntry<K, V> mapEntry : table) {
            if (mapEntry == null) {
                continue;
            }

            K key = mapEntry.getKey();
            if (!keys.contains(key)) {
                keys.add(key);
            }
        }
        return keys;
    }

    /**
     * Return the number of values associated with one key Return -1 if the key
     * does not exist in this table
     *
     * @param key
     * @return
     */
    public int keyValues(K key) {
        int numValues = 0;
        for (MapEntry<K, V> mapEntry : table) {
            if (mapEntry == null) {
                continue;
            }

            if (mapEntry.getKey() == key) {
                ++numValues;
            }
        }

        if (numValues > 0) {
            return numValues;
        }

        return -1; // if the key isn't in the table
    }

    /**
     * Return a set of all the unique key-value entries
     *
     * Note that two map entries with both the same key and value could exist in
     * the map.
     *
     * @return
     */
    public Set<MapEntry<K, V>> entrySet() {
        HashSet<MapEntry<K, V>> entrySet = new HashSet<>(size);

        for (MapEntry<K, V> mapEntry : table) {
            if (mapEntry == null) {
                continue;
            }

            if (!entrySet.contains(mapEntry)) {
                entrySet.add(mapEntry);
            }
        }
        return entrySet;
    }

    /**
     * Clears the hash table
     */
    public void clear() {
        size = 0;
        table = null;
    }

    /*
     * The following methods will be used for grading purposes do not modify them
     */
    public int size() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public MapEntry<K, V>[] getTable() {
        return table;
    }

    public void setTable(MapEntry<K, V>[] table) {
        this.table = table;
    }
}
