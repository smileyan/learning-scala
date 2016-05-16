package ds;

import java.lang.reflect.Array;
import java.util.Random;

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

    public void expand() {
        if (_size < _capacity) return;
        if (_capacity < DEFAULT_CAPACITY) _capacity = DEFAULT_CAPACITY;

        T[] oldElem = _elem;
        _elem = (T[]) Array.newInstance(_elem[0].getClass(), _capacity << 2);

        for (int i = 0; i < _size; i++) {
            _elem[i] = oldElem[i];
        }
    }

    public void shrink() {
        if (_capacity < DEFAULT_CAPACITY << 1) return;
        if (_size << 2 > _capacity) return;

        T[] oldElem = _elem;
        _elem = (T[]) Array.newInstance(_elem[0].getClass(), _capacity >>= 1);

        for (int i = 0; i < _size; i++) {
            _elem[i] = oldElem[i];
        }
    }

    public int search(T[] a, T e, int lo, int hi) {
        while (lo < hi) {
            int mi = (lo + hi) >> 1;
            if (e.compareTo(a[mi]) < 0) {
                hi = mi;
            } else {
                lo = mi + 1;
            }
        }
        return --lo;
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

    public void sort(int lo, int hi) {
        switch (new Random().nextInt(2) % 2) {
            case 0: bubbleSort(lo, hi);
            case 1: mergeSort(lo, hi);
        }
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

    public void mergeSort(int lo, int hi) {
        if (hi - lo < 2) return;

        int mi = (lo + hi) >> 1;
        mergeSort(lo, mi);
        mergeSort(mi, hi);

        merge(lo, mi, hi);
    }

    private void merge(int lo, int mi, int hi) {

        int lb = mi - lo;
        int lc = hi - mi;

        T[] b = (T[]) Array.newInstance(_elem[0].getClass(), lb);
        for (int i = 0; i < lb; i++) {
            b[i] = _elem[lo + i];
        }

        for (int i = 0, j = 0, k = 0; (j < lb) || (k < lc);) {
            if ((j < lb) && (! (k < lc) || (b[j].compareTo(_elem[mi + k]) <= 0))) {
                _elem[lo + i] = b[j];
                i++;
                j++;
            }

            if ((k < lc) && (! (j < lb) || (_elem[mi + k].compareTo(b[j]) <  0))) {
                _elem[lo + i] = _elem[mi + k];
                i++;
                k++;
            }
        }
    }

    @Override
    public int compareTo(Vector<T> o) {
        return this.compareTo(o);
    }
}
