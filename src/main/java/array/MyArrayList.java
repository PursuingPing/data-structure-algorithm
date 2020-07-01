package array;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayList<T> implements Iterable<T>{

    private static final int DEFAULT_CAPACITY = 10;

    private int theSize;
    private T[] theItems;

    public MyArrayList() {
        doClear();
    }

    public void clear() {
        doClear();
    }

    public int size() {
        return theSize;
    }

    public boolean isEmpty() {
        return theSize == 0;
    }

    public void trimToSize() {
        ensureCapacity(size());
    }

    public T get(int idx) {
        validateIndex(idx);
        return theItems[idx];

    }

    public T set(int idx, T val) {
        validateIndex(idx);
        T oldValue = theItems[idx];
        theItems[idx] = val;
        return oldValue;

    }

    public boolean add(T val) {

        add(size(), val);
        return true;
    }

    public void add(int idx, T val) {

        if (theItems.length == size()) {
            ensureCapacity(size() * 2 + 1);
        }
        for (int i = idx; i < size() - 1; i++ ) {
            theItems[i] = theItems[i-1];
         }
        theItems[idx] = val;

        theSize ++;
    }

    private T remove(int idx) {
        validateIndex(idx);
        T removedItem = theItems[idx];

        for(int i = idx; i < size() -1 ; i++) {
            theItems[i] = theItems[i+1];
        }

        theSize--;
        return removedItem;
    }

    private void validateIndex(int idx) {
        if (idx < 0 || idx >= size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }


    public Iterator<T> iterator() {
        return new MyArrayListIterator();
    }

    private class MyArrayListIterator implements Iterator<T> {

        private int current = 0;

        @Override
        public boolean hasNext() {

            return current < size();
        }

        @Override
        public void remove() {
            MyArrayList.this.remove(--current);
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return theItems[current++];
        }
    }

    private void doClear() {
        theSize = 0;
        ensureCapacity(DEFAULT_CAPACITY);

    }

    public void ensureCapacity(int newCapacity) {
        if (newCapacity < theSize) {
            return;
        }

        T[] old = theItems;
        theItems = (T[]) new Object[newCapacity];
        for (int i = 0; i < size(); i++ ){
            theItems[i] = old[i];
        }

    }

    public static void main(String[] args) {
        MyArrayList<Integer> myArrayList = new MyArrayList<>();
        myArrayList.add(1);

        System.out.println(myArrayList.get(0));
    }
}
