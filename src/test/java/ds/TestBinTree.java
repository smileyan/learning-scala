package ds;

import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by huay on 7/06/2016.
 */
public class TestBinTree {
    @Test
    public void testUpdateHeight() {
        BinNode<Integer> node = new BinNode<>(1);
        Assert.assertThat(BinTree.updateHeight(node), Is.is(0));

        BinNode<Integer> lc = new BinNode<>(2, node);
        node.lc = lc;
        Assert.assertThat(BinTree.updateHeight(node), Is.is(1));
        Assert.assertThat(BinTree.updateHeight(lc), Is.is(0));

        BinNode<Integer> rc = new BinNode<>(3, node);
        node.rc = rc;
        Assert.assertThat(BinTree.updateHeight(node), Is.is(1));
        Assert.assertThat(BinTree.updateHeight(lc), Is.is(0));
        Assert.assertThat(BinTree.updateHeight(rc), Is.is(0));
    }

    @Test
    public void testSize() {
        BinTree<Integer> tree = new BinTree<>();

        Assert.assertThat(tree.size(), Is.is(0));

        tree.insertAsRoot(1);
        Assert.assertThat(tree.size(), Is.is(1));

        BinNode<Integer> lc = new BinNode<>();
        tree.insertAsLC(lc, 2);
        Assert.assertThat(tree.size(), Is.is(2));

        BinNode<Integer> rc = new BinNode<>();
        tree.insertAsLC(rc, 3);
        Assert.assertThat(tree.size(), Is.is(3));
    }

    @Test
    public void testEmpty() {
        BinTree<Integer> tree = new BinTree<>();
        Assert.assertThat(tree.empty(), Is.is(true));

        tree.insertAsRoot(1);
        Assert.assertThat(tree.empty(), Is.is(false));
    }
}
