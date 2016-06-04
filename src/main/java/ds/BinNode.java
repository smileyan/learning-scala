package ds;

/**
 * Created by huay on 4/06/2016.
 */
public class BinNode<T> implements Comparable<T> {
    public T data;
    public BinNode<T> parent;
    public BinNode<T> lc;
    public BinNode<T> rc;
    public int height;
    int npl;

    public BinNode () {
        parent = null;
        lc = null;
        rc = null;
        height = 0;
        npl = 0;
    }

    public BinNode(T e) {
        new BinNode<>(e, null);
    }

    public BinNode(T e, BinNode<T> p) {
        new BinNode<>(e, p, null, null, 0, 0);
    }

    public BinNode(T e, BinNode<T> p, BinNode<T> lc, BinNode<T> rc, int height, int npl) {
        this.data = e;
        this.parent = p;
        this.lc = lc;
        this.rc = rc;
        this.height = height;
        this.npl = npl;
    }

    @Override
    public int compareTo(T o) {
        return this.compareTo(o);
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
        return !isRChild(x);
    }

    public static boolean hasLChild(BinNode x) {
        return x.lc == null;
    }

    public static boolean hasRChild(BinNode x) {
        return x.rc == null;
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
        lc = new BinNode<>(e, this);
        return lc;
    }

    public BinNode<T> insertAsRc(T e) {
        rc = new BinNode<>(e, this);
        return rc;
    }
}
