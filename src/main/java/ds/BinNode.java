package ds;

/**
 * Created by huay on 4/06/2016.
 */
public class BinNode<T extends Comparable<T>> {
    public T data = null;
    public BinNode<T> parent = null;
    public BinNode<T> lc = null;
    public BinNode<T> rc = null;
    public int height = 0;
    int npl = 0;

    private void init(T e, BinNode<T> p, BinNode<T> lc, BinNode<T> rc, int height, int npl) {
        this.data = e;
        this.parent = p;
        this.lc = lc;
        this.rc = rc;
        this.height = height;
        this.npl = npl;
    }

    public BinNode () {
       init(null, null, null, null, 0, 0);
    }

    public BinNode(T e) {
        init(e, null, null, null, 0, 0);
    }

    public BinNode(T e, BinNode<T> p) {
        init(e, p, null, null, 0, 0);
    }

    public BinNode(T e, BinNode<T> p, BinNode<T> lc, BinNode<T> rc, int height, int npl) {
       init(e, p, lc, rc, height, npl);
    }

    public static int stature(BinNode p) {
        if (p == null) {
            return -1;
        } else {
            return p.height;
        }
    }

    public static boolean isRoot(BinNode x) {
        if (x.parent == null) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isLChild(BinNode x) {
        if (!isRoot(x) && (x == x.parent.lc)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isRChild(BinNode x) {
        if (!isRoot(x) && (x == x.parent.rc)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean hasParent(BinNode x) {
        return !isRoot(x);
    }

    public static boolean hasLChild(BinNode x) {
        return x.lc != null;
    }

    public static boolean hasRChild(BinNode x) {
        return x.rc != null;
    }

    public static boolean hasChild(BinNode x) {
        return hasLChild(x) || hasRChild(x);
    }

    public static boolean hasBothChild(BinNode x) {
        return hasLChild(x) && hasRChild(x);
    }

    public static boolean isLeaf(BinNode x) {
        return !hasChild(x);
    }

    public static BinNode sibling(BinNode p) {
        if (isLChild(p)) {
            return p.parent.rc;
        } else {
            return p.parent.lc;
        }
    }

    public static BinNode uncle(BinNode x) {
        if (isLChild(x.parent)) {
            return x.parent.parent.rc;
        } else {
            return x.parent.parent.lc;
        }
    }

    public static BinNode fromParentTo(BinNode x) {
        if (isRoot(x)) {
            return x;
        } else if (isLChild(x)) {
            return x.parent.lc;
        } else {
            return x.parent.rc;
        }
    }

    public BinNode<T> insertAsLC(T e) {
        this.lc = new BinNode<>(e, this);
        return this.lc;
    }

    public BinNode<T> insertAsRc(T e) {
        this.rc = new BinNode<>(e, this);
        return this.rc;
    }
}
