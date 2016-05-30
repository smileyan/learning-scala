package ds;

import java.util.function.Function;

/**
 * Created by huay on 17/05/2016.
 */
public class List<T extends Comparable<T>> implements Comparable<List<T>> {
    private int _size;
    private ListNode<T> header;
    private ListNode<T> trailer;

    protected void init() {
        header = new ListNode<>();
        trailer = new ListNode<>();

        header.succ = trailer;
        header.pred = null;

        trailer.pred = header;
        trailer.succ = null;

        _size = 0;
    }

    public List() {
        init();
    }

    public List(ListNode<T> p, int n) {
        copyNodes(p, n);
    }

    public List(List<T> l) {
        copyNodes(l.first(), l._size);
    }

    public List(List<T> l, int r, int n) {
        copyNodes(l.get(r), n);
    }

    public ListNode<T> get(int i) {
        ListNode<T> p = first();

        while (0 < i--) {
            p = p.succ;
        }

        return p;
    }

    public void selectionSort(ListNode<T> p, int n) {
        ListNode<T> head = p.pred;
        ListNode<T> tail = p;

        for (int i = 0; i < n; i++) {
            tail = tail.succ;
        }

        while (1 < n) {
            ListNode<T> max = selectMax(head.succ, n);

            insertB(tail, remove(max));

            tail = tail.pred;
            n--;
        }
    }

    public ListNode<T> selectMax(ListNode<T> p, int n) {
        ListNode<T> max = p;
        for (ListNode<T> cur = p; 1 < n; n--) {
            if (!((cur = cur.succ).data.compareTo(max.data) < 0)) {
                max = cur;
            }
        }
        return max;
    }

    public void insertionSort(ListNode<T> p, int n) {
        for (int r = 0; r < n; r++) {
            insertA(search(p.data, r, p), p.data);
            p = p.succ;
            remove(p.pred);
        }
    }

    public ListNode<T> search(T e, int n, ListNode<T> p) {
        while (0 <= n--) {
            p = p.pred;
            if (!(p.data.compareTo(e) > 0)) {
                break;
            }
        }
        return p;
    }

    public boolean empty() {
        return _size <= 0;
    }

    public ListNode<T> find(T e, int n, ListNode<T> p) {
        while (0 < n--) {
            p = p.pred;
            if (e.compareTo(p.data) == 0) {
                return p;
            }
        }
        return null;
    }

    public T remove(ListNode<T> p) { // 删除合法节点p,返回其数值
        T e = p.data; // 备份待删除节点数值(假定T类型可直接赋值)

        p.pred.succ = p.succ; // 后继
        p.succ.pred = p.pred; // 前驱

        _size--;

        return e; // 返回备份的数值
    }

    public void traverse(Function<T, Void> f) {
        for (ListNode<T> p = header.succ; p != trailer; p = p.succ) {
            f.apply(p.data);
        }
    }

    public int deduplicate() {
        if (_size < 2) return 0; // 平凡列表自然无重复

        int oldSize = _size; // 记录原规模
        ListNode<T> p = header;
        int r = 0; // p 从首节点开始

        while (trailer != (p = p.succ)) { // 依次直到末节点
            ListNode<T> q = find(p.data, r, p); // 在p的r个(真)前驱中查找雷同者
            if (q != null) {
                remove(q); // 若存在则删除
            } else {
                r++; // 否则秩加一
            }
        }
        return oldSize - _size;
    }

    public ListNode<T> insertAsFirst(T e) {
        _size++;
        return header.insertAsSucc(e);
    }

    public ListNode<T> insertAsLast(T e) {
        _size++;
        return trailer.insertAsPred(e);
    }

    public ListNode<T> insertA(ListNode<T> p, T e) { //After
        _size++;
        return p.insertAsSucc(e);
    }

    public ListNode<T> insertB(ListNode<T> p, T e) { //Before
        _size++;
        return p.insertAsPred(e);
    }

    public void copyNodes(ListNode<T> p, int n) {
        init();

        while(n-- > 0) {
            insertAsLast(p.data);
            p = p.succ;
        }
    }

    protected void clear() {

    }

    public int size() {
        return _size;
    }

    public ListNode<T> first() {
        return header.succ;
    }

    public ListNode<T> last() {
        return trailer.pred;
    }

    @Override
    public int compareTo(List<T> o) {
        return this.compareTo(o);
    }
}
