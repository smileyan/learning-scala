package ds;

import org.apache.hadoop.conf.Configuration;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by huay on 24/01/2016.
 */
public class VectorTest {

//    List<String> EMPTYLS = emptyList();

//    @Test(expected = EmptyListHasNoHead.class)
//    public void callingHeadOnAnEmptyListRaises() {
//        EMPTYLS.head();
//    }
//
//    @Test(expected = EmptyListHasNoTail.class)
//    public void callingTailOnAnEmptyListRaises() {
//        EMPTYLS.tail();
//    }

    @Test
    public void testConfiguration() {
        Configuration conf = new Configuration();
        conf.addResource("configuration-1.xml");

        Assert.assertEquals(conf.getInt("size", 10), 10);

    }

    @Test
    public void testBinSearch() {
        Vector<Integer> v = new Vector<>(Integer.class);
        Integer[] a = new Integer[3];
        a[0] = 1;
        a[1] = 4;
        a[2] = 5;

        int o = v.search(a, 1, 0, 2);
        Assert.assertEquals(0, o);

        int j = v.binSearch(a, 4, 0, 2);
        Assert.assertEquals(1, j);

        int m = v.search(a, 5, 0, 2);
        Assert.assertEquals(1, m);

        int n = v.search(a, 5, 0, 3);
        Assert.assertEquals(2, n);
    }

    @Test
    public void testSort() {

        Vector<Integer> v = new Vector<>(new Integer[]{2,4,3,5,2,5,78,95}, 0, 6);

        v.sort(0, 6);

        Assert.assertEquals(v.get(0) * 1, 2);
        Assert.assertEquals(v.get(5) * 1, 5);
    }
}