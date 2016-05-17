package ds;

/**
 * Created by huay on 17/05/2016.
 */
public class List<T> {
    private int _size;
    private ListNode<T> header;
    private ListNode<T> trailer;

    protected void init() {
        header = new ListNode<T>();
        trailer = new ListNode<T>();

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
}
