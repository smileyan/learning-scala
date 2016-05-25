package ds;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by huay on 23/05/2016.
 */
public class TestStack {
    @Test
    public void testBasic() {
        Stack<Integer> s = new Stack<Integer>(Integer.class);

        Assert.assertTrue(s.empty());

        s.push(5);
        s.push(3);
        Assert.assertEquals(3 - s.pop(), 0);

        s.push(7);
        s.push(3);

        Assert.assertEquals(3 - s.top(), 0);

        Assert.assertFalse(s.empty());

        s.push(11);

        Assert.assertEquals(4, s.size());
    }

    @Test
    public void testConvert() {
        Stack<Character> ss = Stack.convert(12345, 8);

        Assert.assertEquals(ss.pop().charValue(), '3');
        Assert.assertEquals(ss.pop().charValue(), '0');
        Assert.assertEquals(ss.pop().charValue(), '0');
        Assert.assertEquals(ss.pop().charValue(), '7');
        Assert.assertEquals(ss.pop().charValue(), '1');
    }

    @Test
    public void testParen() {
        Character[] c = {'{','(',')','[','(','{','}',')',']','}'};
        boolean result = Stack.paren(c, 0, c.length - 1);

        Assert.assertEquals(result, true);

        Character[] a = {'(',')','[','(','{','}',')',']','}'};
        boolean a_result = Stack.paren(a, 0, a.length - 1);

        Assert.assertEquals(a_result, false);
    }
}
