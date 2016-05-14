package ds;

import org.apache.hadoop.conf.Configuration;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

import static ds.ListModule.*;
/**
 * Created by huay on 24/01/2016.
 */
public class ListTest {

    List<String> EMPTYLS = emptyList();
    List<Long>   EMPTYLL = emptyList();

    @Test(expected = EmptyListHasNoHead.class)
    public void callingHeadOnAnEmptyListRaises() {
        EMPTYLS.head();
    }

    @Test(expected = EmptyListHasNoTail.class)
    public void callingTailOnAnEmptyListRaises() {
        EMPTYLS.tail();
    }

//    static void fromArrayToColletion(Object[] a, Collection<?> c) {
//        for (Object o: a) {
//            c.add(o);
//        }
//
//    }
    static <T> void fromArrayToCollection(T[] a, Collection<T> c) {
        for (T o: a) {
            c.add(o);
        }
    }

    @Test
    public void testConfiguration() {
        Configuration conf = new Configuration();
        conf.addResource("configuration-1.xml");

        Assert.assertEquals(conf.getInt("size", 10), 10);

    }
    @Test
    public void testBinSearch() {
        Vector<Integer> v = new Vector<>();
        Integer[] a = new Integer[3];
        a[0] = 1;
        a[1] = 4;
        a[2] = 5;

        int o = v.binSearch(a, 1, 0, 2);
        Assert.assertEquals(0, o);

        int j = v.binSearch(a, 4, 0, 2);
        Assert.assertEquals(1, j);

        int m = v.binSearch(a, 5, 0, 2);
        Assert.assertEquals(-1, m);
    }

    @Test
    public void testBubbleSort() {

        Vector<Integer> v = new Vector<>(new Integer[]{2,4,3,5,2,5,78,95}, 0, 6);

        v.bubbleSort(0, 6);

        Assert.assertEquals(v.get(0) * 1, 2);
        Assert.assertEquals(v.get(5) * 1, 5);
    }
}