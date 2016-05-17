package ds;

/**
 * Created by huay on 17/05/2016.
 */
public class ListNode<T> {
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
        return this;
    }

    public ListNode<T> insertAsSucc(T e) {
        return null;
    }
}
