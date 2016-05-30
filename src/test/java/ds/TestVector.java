package ds;

import org.apache.hadoop.conf.Configuration;
import org.junit.Assert;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by huay on 24/01/2016.
 */
public class TestVector {

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

        Vector<Integer> v = new Vector<>(Integer.class,new Integer[]{2,4,3,5,2,5,78,95}, 0, 6);

        v.sort(0, 6);

        Assert.assertEquals(v.get(0) * 1, 2);
        Assert.assertEquals(v.get(5) * 1, 5);
    }

    @Test
    public void testSimpleDateFormat() {
        String[] dates = { "Jan 11,  2003", "6/11/1969","08/22/54" };

        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");

        String p1 = "MMM dd y";
        String p2 = "MM dd y";

        DateFormat df;
        for (int i = 0; i < dates.length; i++) {
            String date = dates[i].replace(",", " ").replace("/", " ");

            if (Character.isDigit(date.trim().charAt(0))) {
                df = new SimpleDateFormat(p2);
            } else {
                df = new SimpleDateFormat(p1);
            }

            try {
                System.out.println(formatter.format(df.parse(date)));
            } catch (ParseException p) {
                System.out.println("date ==== " + date);
            }
        }
    }
}