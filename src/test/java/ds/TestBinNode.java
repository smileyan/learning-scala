package ds;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by huay on 5/06/2016.
 */
public class TestBinNode {

    BinNode<Integer> subject;

    @Before
    public void setUp() {
        subject = new BinNode<>(1);
    }

    @After
    public void tearDown() {
        subject = null;
    }

    @Test
    public void testBasic() {

        Assert.assertThat(1, Is.is(subject.data));

        BinNode<Integer> lc = new BinNode<>(2, subject);
        BinNode<Integer> rc = new BinNode<>(3, subject);

        subject.lc = lc;
        Assert.assertThat(lc, Is.is(subject.lc));

        subject.rc = rc;
        Assert.assertThat(rc, Is.is(subject.rc));

        Assert.assertThat(lc.parent, Is.is(subject));
    }

    @Test
    public void testIsRoot() {
        Assert.assertThat(BinNode.isRoot(subject), Is.is(true));

        BinNode<Integer> lc = new BinNode<>(2, subject);
        Assert.assertThat(BinNode.isRoot(lc), Is.is(false));
    }

    @Test
    public void testIsLChild() {
        BinNode<Integer> lc = new BinNode<>(2, subject);
        subject.lc = lc;
        Assert.assertThat(BinNode.isLChild(lc), Is.is(true));
    }

    @Test
    public void testIsRChild() {
        BinNode<Integer> rc = new BinNode<>(2, subject);
        subject.rc = rc;
        Assert.assertThat(BinNode.isRChild(rc), Is.is(true));
    }

    @Test
    public void testHasParent() {
        Assert.assertThat(BinNode.hasParent(subject), Is.is(false));

        BinNode<Integer> rc = new BinNode<>(2, subject);
        Assert.assertThat(BinNode.hasParent(rc), Is.is(true));
    }

    @Test
    public void testHasLChild() {
        Assert.assertThat(BinNode.hasLChild(subject), Is.is(false));

        BinNode<Integer> lc = new BinNode<>(2, subject);
        subject.lc = lc;
        Assert.assertThat(BinNode.hasLChild(subject), Is.is(true));
    }

    @Test
    public void testHasRChild() {
        Assert.assertThat(BinNode.hasRChild(subject), Is.is(false));

        BinNode<Integer> rc = new BinNode<>(2, subject);
        subject.rc = rc;
        Assert.assertThat(BinNode.hasRChild(subject), Is.is(true));
    }

    @Test
    public void testHasBothChind() {
        Assert.assertThat(BinNode.hasBothChild(subject), Is.is(false));

        BinNode<Integer> lc = new BinNode<>(2, subject);
        subject.lc = lc;
        Assert.assertThat(BinNode.hasBothChild(subject), Is.is(false));

        BinNode<Integer> rc = new BinNode<>(2, subject);
        subject.rc = rc;
        Assert.assertThat(BinNode.hasBothChild(subject), Is.is(true));
    }

    @Test
    public void testIsLeaf() {
        Assert.assertThat(BinNode.isLeaf(subject), Is.is(true));

        BinNode<Integer> lc = new BinNode<>(2, subject);
        subject.lc = lc;
        Assert.assertThat(BinNode.isLeaf(subject), Is.is(false));
    }

    @Test
    public void testSibling() {
        BinNode<Integer> lc = new BinNode<>(2, subject);
        subject.lc = lc;
        BinNode<Integer> rc = new BinNode<>(2, subject);
        subject.rc = rc;

        Assert.assertThat(BinNode.sibling(lc), Is.is(rc));
        Assert.assertThat(BinNode.sibling(rc), Is.is(lc));
    }
}
