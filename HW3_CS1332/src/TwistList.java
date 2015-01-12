
/**
 * This class extends LinkedList, but there's a twist. Read the documentation
 * for each method. Note that the data here is Comparable.
 */
public class TwistList<E extends Comparable<E>> extends LinkedList<E> {

    /**
     * If the data is less than the head, add to the front of the list.
     * Otherwise, find the first index where one of the two adjacent nodes are
     * greater than the data, and the other is less than the data. If such an
     * index does not exist, add the data to the end of the list.
     *
     * When the above process is complete call swing with the index of the newly
     * added data.
     */
    @Override
    public void add(E e) {
        Node newNode = new Node(e);
        Node cur, prev, next;
        int index = 0;
        boolean isAdded = false;

        if (e.compareTo(head.getData()) < 0) {
            // add to the front of the list
            newNode.setNext(head);
            head = newNode;
            isAdded = true;
        } 
        else {
            cur = head.getNext();
            prev = head;

            for (; index < size; ++index) {

                // check to see if one of the adjacent nodes is less
                // than the data, and the other is greater than the data
                if (e.compareTo((E) prev.getData()) < 0) {
                    if (e.compareTo((E) cur.getNext()) > 0) {
                        // add the node here
                        next = prev.getNext();
                        prev.setNext(newNode);
                        newNode.setNext(next);
                        
                        isAdded = true;
                    }
                } 
                else if (e.compareTo((E) prev.getData()) > 0) {
                    if (e.compareTo((E) cur.getNext()) < 0) {
                        // add the node here
                        next = prev.getNext();
                        prev.setNext(newNode);
                        newNode.setNext(next);
                        
                        isAdded = true;
                    }
                }
            }
        }
        if (!isAdded) {
            // add to the end of the list
            super.add(e);
        }
        
        swing(index);
    }

    /**
     * Reverses the order of the list between the start and stop index
     * inclusively.
     *
     * Assume the indices given are valid and start <= stop
     *

     *
     * @param start The beginning index of the sub section to be reversed
     * @param stop The end index (inclusive) of the sub section to be reversed
     */
    public void reverse(int start, int stop) {
        Object[] dataCopy = new Object[stop - start];

        for (int i = 0; i < dataCopy.length; ++i) {
            dataCopy[i] = this.get(i);
        }

        for (int i = dataCopy.length; i > -1; --i) {
            // index 0 gets the last element's data
            this.set(dataCopy.length - i, (E) dataCopy[i]);
        }
    }

    /**
     * This method will take in an index and move everything after that index to
     * the front of the list
     *
     * Assume the index given is valid
     *
     * @param index The index at which to cut the list
     */
    public void flipFlop(int index) {
        Object[] lastItems = new Object[size - index];
        Object[] firstItems = new Object[index];

        for (int i = index; i < size; ++i) {
            lastItems[i] = get(i);
        }

        for (int i = 0; i < index; ++i) {
            firstItems[i] = get(i);
        }

        // clear the list, then add the new items
        clear();
        for (Object first : firstItems) {
            add((E) first);
        }
        for (Object last : lastItems) {
            add((E) last);
        }


    }

    /**
     * This method will reverse the order of the first half of the list up to
     * the index argument (inclusive), and also reverse the second half of the
     * list from index + 1 to the end of the list
     *
     * Assume the index given is valid, however the second half may be empty
     *
     * @param index The index to swing around
     */
    public void swing(int index) {
        reverse(0, index);

        if (index == size + 1) {
            return; //second half empty
        }
        reverse(index + 1, size - 1);
    }
}
