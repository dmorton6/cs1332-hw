
import java.util.Collection;

/**
 * This is a circular, singly linked list.
 */
public class LinkedList<E> implements List<E> {

    protected Node<E> head;
    protected int size;
    
    /* Note: not sure if a constructor is necessary here.
     * I tried testing the code with CompilationTest.java and it 
     * wouldn't work with or without a constructor here.
     * And it complained of not having a constructor.
     * 
     * So I'm not sure if this code will error out or not, but that 
     * is a reason why if it does.
     */
    
    @Override
    public void add(E e) {
        Node newNode = new Node(e);

        if (this.isEmpty()) {
            // this is the only element in the list
            head = newNode;
        } 
        else {
            // adding to the end of the list
            Node cur = head;
            while (cur.getNext() != head) {
                cur = cur.getNext();
            }
            cur.setNext(newNode);
        }
        newNode.setNext(head);
        ++size;
    }

    /*
     * You will want to look at Iterator, Iterable, and 
     * how to use a for-each loop for this method.
     */
    @Override
    public void addAll(Collection<? extends E> c) {
        for (E e : c) {
            add(e);
        }
    }

    @Override
    public void clear() {
        for (int i = size-1; i > -1; --i) {
            remove(i);
        }
    }

    @Override
    public boolean contains(Object o) {
        int index = indexOf(o);
        
        return (index != -1);
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        int curIndex = 0;
        Node curNode = head;

        while (curIndex < index) {
            if (curNode.getNext() == head) {
                // we've reached the end of the list unexpectedly
                throw new IndexOutOfBoundsException();
            }
            curNode = curNode.getNext();
            ++curIndex;
        }

        // not sure why the typecast is necessary but it seems to be
        return (E) curNode.getData();
    }

    @Override
    public int indexOf(Object o) throws IndexOutOfBoundsException {
        Node curNode = head;
        int curIndex = 0;

        while (curNode.getData() != o) {
            if (curNode.getNext() == head && curIndex == size - 1) {
                if (curIndex == size - 1) {
                    // we have reached the end of the list, and the specified
                    // element is not in the list
                    return -1;
                }
                else {
                    // we are trying to index an element that does not exist
                    throw new IndexOutOfBoundsException();
                }
            }
            curNode = curNode.getNext();
            ++curIndex;
        }
        return curIndex;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        E dataRemoved = get(index);
        Node cur = head;
        Node prev, next;
        boolean removingLast = false;
        
        if (size == 1) {
            // we just need to remove the head
            head = null;
            --size;
            return dataRemoved;
        }
        
        if (index == 0) {
            // removing the head from the list
            // but the head is not the only element
            
            // go to the last element in the list
            for (int i = 0; i < size-1; ++i) {
                cur = cur.getNext();
            }
            prev = cur;
            cur = cur.getNext(); // set cur to former head
            cur = null; // set former head to null
            
            next = head.getNext(); // going to be the new head
            
            prev.setNext(next);
            head = next;
            
            --size;
            return dataRemoved;
        }

        // not removing the head from the list 
        
        // iterate to the element previous to the element to be removed
        for (int i = 0; i < index - 1; ++i) {
            if (cur.getNext() == head) {
                // we've reached the end of the list unexpectedly
                throw new IndexOutOfBoundsException();
            }
            cur = cur.getNext();
        }
        prev = cur;

        // iterate to the element after it
        for (int i = 0; i < 2; i++) {
            if (cur.getNext() == head) {
                removingLast = true;
                break;
            }
            cur = cur.getNext();
        }
        next = cur;
        
        // remove the proper element
        cur = prev.getNext();
        cur = null;
        
        // change the previous element's "next" reference
        if (removingLast) {
            prev.setNext(head);
        }
        else {
            prev.setNext(next);
        }
        
        --size;
        return dataRemoved;
    }

    @Override
    public E remove(Object o) {
        int indexToRemove = indexOf(o);
        E dataRemoved = remove(indexToRemove);
        
        return dataRemoved;
    }

    @Override
    public E set(int index, E e) throws IndexOutOfBoundsException {
        Node cur = head;
        for (int i = 0; i < index; i++) {
            if (cur.getNext() == head) {
                // unexpected end of list
                throw new IndexOutOfBoundsException();
            }
        }
        cur.setData(e);
        return e;
    }

    @Override
    public int size() {
        return size;
    }

    /*
     * The following methods are for grading. Do not modify them, and do not use them.
     */
    public void setSize(int size) {
        this.size = size;
    }

    public Node<E> getHead() {
        return head;
    }

    public void setHead(Node<E> head) {
        this.head = head;
    }
}
