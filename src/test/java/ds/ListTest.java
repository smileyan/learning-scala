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

        Assert.assertEquals(conf.getInt("size", 10), 12);

    }
}