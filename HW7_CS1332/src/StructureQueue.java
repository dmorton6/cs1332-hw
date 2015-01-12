/**
 * A structure that behaves like a queue but implements the Structure interface
 * Feel free to build on existing java implementations, no need to build from
 * scratch. (you can import and use something like LinkedList for example)
 * 
 * @param <T>
 */
public class StructureQueue<T> implements Structure<T> {

    private int size = 0;
    private int nextToRemove = 0;
    private T[] dataArr = (T[]) new Object[60];
    
    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public void clear() {
        for (T t : dataArr) {
            t = null;
        }
        size = 0;
        nextToRemove = 0;
    }

    @Override
    public void add(T node) {
        if (size > dataArr.length - 1) {
            // resize data array
            dataArr = (T[]) new Object[dataArr.length * 2];
        }
        dataArr[size++] = node;
    }

    @Override
    public T remove() {
        // FIFO data structure, so remove from the front index number
        T node = dataArr[nextToRemove];
        dataArr[nextToRemove++] = null;
        
        --size;
        return node;
    }

}
