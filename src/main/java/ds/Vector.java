package ds;

import java.lang.reflect.Array;

/**
 * Created by huay on 14/05/2016.
 */
public class Vector<T extends Comparable<T>> implements Comparable<Vector<T>> {

    private int DEFAULT_CAPACITY = 1;
    private int _size;
    private int _capacity;
    private  T[] _elem;

    public void copyFrom(T[] a, int lo, int hi) {
        _capacity = 2 * (hi - lo);
        _size = 0;

        _elem = (T[]) Array.newInstance(a[0].getClass(),_capacity);
        while (lo < hi) {
            _elem[_size++] = a[lo++];
        }
    }

    public Vector(T[] a, int n) {
        copyFrom(a, 0, n);
    }

    public Vector(T[] a, int lo, int hi) {
        copyFrom(a, lo, hi);
    }

    public Vector(){

    }

    public Vector(Class<T> c, int capacity, int size, T v) {
        _capacity = capacity;

        _elem = (T[]) Array.newInstance(c, size);

        for (_size = 0; _size < size; _size++) {
            _elem[_size] = v;
        }
    }

    public T get(int rank) {
        return _elem[rank];
    }

    public Vector(int capacity, int size, T v) {
        _capacity = capacity;

        _elem = (T[])new Object[size];

        for (_size = 0; _size < size; _size++) {
            _elem[_size] = v;
        }
    }

    public int binSearch(T[] a,T e, int lo, int hi) {
        while (lo < hi) {
            int mi = (lo + hi) >> 1;

            if (e.compareTo(a[mi]) < 0) {
                hi = mi;     // e < a[mi]
            }
            else if (a[mi].compareTo(e) < 0) {
                lo = mi + 1; // a[mi] < e
            }
            else {
                return mi;   // e = a[mi]
            }
        }
        return -1;
    }

    public void bubbleSort(int lo, int hi) {
        while (!bubble(lo, hi--));
    }

    private boolean bubble(int lo, int hi) {
        boolean sorted = true;

        while ( ++lo < hi ) {
            if (_elem[lo - 1].compareTo(_elem[lo]) > 0) {
                sorted = false;
                swap (_elem[lo - 1], _elem[lo]);
            }
        }
        return  sorted;
    }

    private void swap(T e1, T e2) {
        T e3;
        e3 = e1;
        e1 = e2;
        e2 = e3;
    }

    @Override
    public int compareTo(Vector<T> o) {
        return this.compareTo(o);
    }


}
