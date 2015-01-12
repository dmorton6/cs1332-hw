
import java.util.Comparator;

/**
 * This is an implementation of a heap that is backed by an array.
 *
 * This implementation will accept a comparator object that can be used to
 * define an ordering of the items contained in this heap, other than the
 * objects' default compareTo method (if they are comparable). This is useful if
 * you wanted to sort strings by their length rather than their lexicographic
 * ordering. That's just one example.
 *
 * Null should be treated as positive infinity if no comparator is provided. If
 * a comparator is provided, you should let it handle nulls, which means it
 * could possibly throw a NullPointerException, which in this case would be
 * fine.
 *
 * If a comparator is provided that should always be what you use to compare
 * objects. If no comparator is provided you may assume the objects are
 * Comparable and cast them to type Comparable<T> for comparisons. If they
 * happen to not be Comparable you do not need to handle anything, and you can
 * just let your cast throw a ClassCastException.
 *
 * This is a minimum heap, so the smallest item should always be at the root.
 *
 * @param <T> The type of objects in this heap
 */
public class BinaryHeap<T> implements Heap<T> {

    private static final int DEFAULT_SIZE = 11;
    private static final Comparator DEFAULT_COMPARATOR = null;
    private boolean isDefaultComparator;
    /**
     * The comparator that should be used to order the elements in this heap
     */
    private Comparator<T> comp;
    /**
     * The backing array of this heap
     */
    private T[] data;
    /**
     * The number of elements that have been added to this heap, this is NOT the
     * same as data.length
     */
    private int size;

    /**
     * Default constructor, this should initialize data to a default size (11 is
     * normally a good choice)
     *
     * This assumes that the generic objects are Comparable, you will need to
     * cast them when comparing since there are no bounds on the generic
     * parameter
     */
    public BinaryHeap() {
        this(DEFAULT_COMPARATOR); 
    }

    /**
     * Constructor that accepts a comparator to use with this heap. Also
     * initializes data to a default size.
     *
     * When a comparator is provided it should be preferred over the objects'
     * compareTo method
     *
     * If the comparator given is null you should attempt to cast the objects to
     * Comparable as if a comparator were not given
     *
     * @param comp
     */
    public BinaryHeap(Comparator<T> comp) {
        this.isDefaultComparator = (comp == DEFAULT_COMPARATOR);
        this.size = 0;
        this.data = (T[]) new Object[DEFAULT_SIZE];
    }

    @Override
    public void add(T item) {
        if (size == 0) {
            data[1] = item;
            ++size;
            return;
        }

        // add to the furthest-right child
        data[++size] = item;
        regrowHeap(size);
    }

    private void regrowHeap(int childIndex) {        
        int parentIndex = childIndex / 2; // integer division works great here
        T parentData = data[parentIndex];
        T childData = data[childIndex];
        
        if (isDefaultComparator) {
            if (parentData == null || 
                ((Comparable)parentData).compareTo(childData) > 0) {
                // parent data > child data... need swap
                swap(parentIndex, childIndex);
                
                if (parentIndex != 1) {
                    // regrow the heap again, but only if the parent is not
                    // already the minimum element
                    regrowHeap(parentIndex);
                }
            }
        }
        else {
            if (parentData == null ||
                comp.compare(parentData, childData) > 0) {
                //parent data > child data... need swap
                swap(parentIndex, childIndex);
            
                if (parentIndex != 1) {
                    // regrow the heap again, but only if the parent is not
                    // already the minimum element
                    regrowHeap(parentIndex);
                }
            }
        }
    }
    
    
    private void swap(int index1, int index2) {
        T temp = data[index1];
        data[index1] = data[index2];
        data[index2] = temp;
    }


    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    // returns min element
    public T peek() {
        T smallestItem = data[1];
        if (smallestItem == null) {
            return null;
        }

        for (T heapItem : data) {
            if (heapItem == null) continue;
            
            // check for minimum element
            if (isDefaultComparator) {
                if (((Comparable)smallestItem).compareTo(heapItem) > 0) {
                    smallestItem = heapItem;
                }
            }
            else {
                if (comp.compare(smallestItem, heapItem) < 0) {
                    smallestItem = heapItem;
                }
            }
        }
        return smallestItem;
    }

    @Override
    // removes min element and returns it
    public T remove() {
        T dataToRemove = data[1];
        if (dataToRemove == null) return null;
        
        regrowAfterRemove(1);
        
        --size;
        return dataToRemove;
    }
    
    private void regrowAfterRemove(int parentIndex) {
        if (parentIndex > size) return;
        
        int childIndex1 = parentIndex * 2;
        int childIndex2 = childIndex1 + 1;
        T child1 = data[childIndex1];
        T child2 = data[childIndex2];
        
        if (data[childIndex1] == null) {
            if (data[childIndex2] == null) {
                // don't swap... this element is at the bottom of the heap
                // instead, remove the element
                data[parentIndex] = null;
            }
            else {
                // swap min element with child 1
                swap(parentIndex, childIndex2);
                regrowAfterRemove(childIndex2);
            }
        }
        else {
            if (data[childIndex2] == null) {
                // swap min element with child 2
                swap(parentIndex, childIndex1);
                regrowAfterRemove(childIndex1);
            }
            else {
                // find min between children 1 and 2, then swap
                if (isDefaultComparator) {
                    if (((Comparable)child1).compareTo(child2) > 0) {
                        swap(parentIndex, childIndex2);
                        regrowAfterRemove(childIndex2);
                    }
                    else {
                        swap(parentIndex, childIndex1);
                        regrowAfterRemove(childIndex1);
                    }
                }
                else {
                    if (comp.compare(child1, child2) > 0) {
                        swap(parentIndex, childIndex2);
                        regrowAfterRemove(childIndex2);
                    }
                    else {
                        swap(parentIndex, childIndex1);
                        regrowAfterRemove(childIndex1);
                    }
                }
            }
        }
    }

    @Override
    public int size() {
        return size;
    }
}