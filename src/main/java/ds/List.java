package ds;

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

    public T get(int i) {
        ListNode<T> p = first();
        while (0 < i--) p = p.succ;
        return p.data;
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
