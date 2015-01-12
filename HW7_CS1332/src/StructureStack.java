/**
 * A structure that behaves like a stack but implements the Structure interface
 * Feel free to build on existing java implementations, no need to build from
 * scratch. (you can import and use something like LinkedList for example)
 * 
 * @param <T>
 */
public class StructureStack<T> implements Structure<T> {

    private int size = 0;
    private T[] dataArr = (T[]) new Object[60];

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            dataArr[i] = null;
        }
        size = 0;
    }

    @Override
    public void add(T node) {
        if (size > dataArr.length - 1) {
            dataArr = (T[]) new Object[dataArr.length * 2];
        }
        dataArr[size++] = node;
    }

    @Override
    public T remove() {
        // LIFO  structure
        T node = dataArr[--size];
        dataArr[size + 1] = null;

        return node;
    }

}
