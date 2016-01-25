package ds;

import org.junit.Test;

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


}