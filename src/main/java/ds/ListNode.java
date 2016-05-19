package ds;

/**
 * Created by huay on 17/05/2016.
 */
public class ListNode<T extends Comparable<T>> {
    public T data;
    public ListNode<T> pred;
    public ListNode<T> succ;

    public ListNode() {

    }

    public ListNode(T e, ListNode<T> p, ListNode<T> s) {
        data = e;
        pred = p;
        succ = s;
    }

    public ListNode<T> insertAsPred(T e) {
        ListNode<T> x = new ListNode(e, pred, this);

        pred.succ = x;
        pred = x;

        return x;
    }

    public ListNode<T> insertAsSucc(T e) {
        ListNode<T> x = new ListNode(e, this, succ);

        succ.pred = x;
        succ = x;

        return x;
    }
}
