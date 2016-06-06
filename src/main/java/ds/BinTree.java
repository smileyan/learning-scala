package ds;

import java.util.function.Function;

/**
 * Created by huay on 4/06/2016.
 */
public class BinTree<T extends Comparable<T>> implements Comparable<BinTree<T>>{
    private int _size;
    private BinNode<T> _root;


    public BinTree() {
        _size = 0;
        _root = null;
    }

    public int size() {
        return _size;
    }

    public boolean empty() {
        return _root == null;
    }

    public BinNode<T> root() {
        return _root;
    }

    public int updateHeight(BinNode<T> x) {
        x.height = 1 + Math.max(BinNode.stature(x.lc), BinNode.stature(x.rc));
        return x.height;
    }

    public void updateHeightAbove(BinNode<T> x) {
        while (x != null) {
            updateHeight(x);
            x = x.parent;
        }
    }

    public BinNode<T> insertAsRoot(T e) {
        _size = 1;
        _root = new BinNode<>(e);
        return _root;
    }

    public BinNode<T> insertAsLC(BinNode<T> x, T e) {
        _size++;
        x.insertAsLC(e);
        updateHeightAbove(x);
        return x.lc;
    }

    public BinNode<T> insertAsRC(BinNode<T> x, T e) {
        _size++;
        x.insertAsRc(e);
        updateHeightAbove(x);
        return x.rc;
    }

    public BinNode<T> attachAsLC(BinNode<T> x, BinTree<T> s) {
        x.lc = s.root();
        x.lc.parent = x;

        _size += s.size();
        updateHeightAbove(x);

        return x;
    }

    public BinNode<T> attachAsRC(BinNode<T> x, BinTree<T> s) {
        x.rc = s.root();
        x.rc.parent = x;

        _size += s.size();
        updateHeightAbove(x);

        return x;
    }

    public int remove(BinNode<T> x) {
        BinNode from = BinNode.fromParentTo(x);
        from = null;

        updateHeightAbove(x.parent);

        int n = removeAt(x);
        _size -= n;
        return n;
    }

    public int removeAt(BinNode<T> x) {
        if (x == null) {
            return 0;
        }

        int n = 1 + removeAt(x.lc) + removeAt(x.rc);
        return n;
    }

    @Override
    public int compareTo(BinTree<T> o) {
        return this.compareTo(o);
    }

    public void travPre_R(BinNode<T> x, Function<T, Void> fun) {
        if (!(x == null)) {
            return;
        }
        fun.apply(x.data);

        travPre_R(x.lc, fun);
        travPre_R(x.rc, fun);
    }

    private void visitAlongLeftBranch(BinNode<T> x, Function<T, Void> visit, java.util.Stack<BinNode<T>> s) {
        while (x != null) {
            visit.apply(x.data);
            s.push(x.rc);
            x = x.lc;
        }
    }

//    public void travPre_I2(final BinNode<T> x, Function<T, Void> visit) {
//        java.util.Stack<BinNode<T>> s = new java.util.Stack<>();
//        //Stack<BinNode<T>> s = new Stack<>(null);
//        while (true) {
//            visitAlongLeftBranch(x, visit, s);
//
//            if (s.empty()) {
//                break;
//            } else {
//                x = s.pop();
//            }
//        }
//    }
}
