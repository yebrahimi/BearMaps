package bearmaps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;


public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    // private instance variables
    private ArrayList<Key<T>> pq;
    private HashMap<T, Integer> index;

    // private class
    private class Key<T> {
        private T item;
        private double priority;

        Key(T i, double p) {
            item = i;
            priority = p;
        }
    }

    public ArrayHeapMinPQ() {
        pq = new ArrayList<>();
        index = new HashMap<>();
    }

    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        index.put(item, size() - 1);
        pq.add(new Key<>(item, priority));
        swap1(size() - 1);
    }

    private void swap1(int i1) {
        int i2 = idx1(i1);
        if (pq.get(i2).priority > pq.get(i1).priority) {
            Key<T> temp1 = pq.get(i1);
            Key<T> temp2 = pq.get(i2);
            index.put(temp1.item, i2);
            index.put(temp2.item, i1);
            pq.set(i1, temp2);
            pq.set(i2, temp1);
            swap1(i2);
        }
    }

    private int idx1(int i) {
        if (i == 0) {
            return 0;
        } else {
            return (i - 1) / 2;
        }
    }

    private int idx2(int i1) {
        int i2;
        if (i1 * 2 + 1 < size()) {
            i2 = i1 * 2 + 1;
        } else {
            i2 = i1;
        }
        int i3;
        if (i1 * 2 + 2 < size()) {
            i3 = i1 * 2 + 2;
        } else {
            i3 = i2;
        }
        if (pq.get(i2).priority < pq.get(i3).priority) {
            return i2;
        } else {
            return i3;
        }
    }

    @Override
    public boolean contains(T item) {
        return index.containsKey(item);
    }

    @Override
    public T getSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        return pq.get(0).item;
    }

    @Override
    public T removeSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        } else {
            T removed = pq.get(0).item;
            index.remove(removed);
            int size = size() - 1;
            pq.set(0, pq.get(size));
            pq.remove(size);
            if (size != 0) {
                swap2(0);
            }
            return removed;
        }
    }

    private void swap2(int i1) {
        int i2 = idx2(i1);
        while (pq.get(i2).priority < pq.get(i1).priority) {
            Key<T> temp1 = pq.get(i1);
            Key<T> temp2 = pq.get(i2);
            index.put(temp1.item, i2);
            index.put(temp2.item, i1);
            pq.set(i1, temp2);
            pq.set(i2, temp1);
            i1 = i2;
            i2 = idx2(i2);
        }
    }

    @Override
    public int size() {
        return pq.size();
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        } else {
            int i = index.get(item);
            if (pq.get(i).priority >= priority) {
                pq.get(i).priority = priority;
                swap1(i);
            } else {
                pq.get(i).priority = priority;
                swap2(i);
            }
        }
    }
}
