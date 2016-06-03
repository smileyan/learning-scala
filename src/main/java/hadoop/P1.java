package hadoop;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by huay on 2/06/2016.
 * database: problem1
 * table name: customer
 * Sample Data (birthday is in bold)
 * |-----------------------------------------------------|
 * | 1904287  |  Christopher Rodriguez  |  Jan 11, 2003  |
 * | 96391595 |  Thomas Stewart	        |  6/17/1969     |
 * | 2236067  |  John Nelson            |  08/22/54      |
 * -------------------------------------------------------
 *
 *
 * Output Requirements
 *
 * 1. Create a new table named solution in the problem1 database of the Hive metastore
 * 2. Your solution table must have its data stored in the HDFS directory /user/cert/problem1/solution
 * 3. Your solution table must have exactly the same columns as the customer table in the same order, as well as keeping the existing file format
 * 4. For every row in the solution table, replace the contents of the birthday field with a date string in “MM/DD/YY” format.
 *    1. MM is the zero-padded month (01-12),
 *    2. DD is the zero-padded day (01-31),
 *    3. YY is the zero-padded 2-digit year (00-99)
 *
 * End of Sample Problem
 */
public class P1 extends UDF {
    /**
     * CREATE DATABASE IF NOT EXISTS problem1;
     *
     * USE problem1;
     *
     * CREATE TABLE customer (id INT, name STRING, birthday STRING);
     *
     * INSERT INTO customer VALUES (1904287, 'Christopher Rodriguez', 'Jan 11, 2003');
     * INSERT INTO customer VALUES (96391595, 'Thomas Stewart', '6/17/1969');
     * INSERT INTO customer VALUES (1904287, 'John Nelson', '08/22/54');
     *
     * SHOW CREATE TABLE customer;
     *
     * CREATE TEMPORARY FUNCTION my_todate AS hadoop.P1;
     *
     * CREATE TABLE solution
     *   AS
     * SELECT id, name, my_todate(birthday)
     * FROM customer;
     *
     */

    private SimpleDateFormat mmm_dd_yyyy = new SimpleDateFormat("MMM dd yyyy");
    private SimpleDateFormat mm_dd_yy = new SimpleDateFormat("MM/dd/yy");

    private SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");

    public Text evaluate(Text date) throws ParseException{
        if (date == null) {
            return null;
        }

        String date_s = date.toString();

        SimpleDateFormat sdf = null;
        if (Character.isDigit(date_s.charAt(0))) {
            sdf = mm_dd_yy;
        } else {
            sdf = mmm_dd_yyyy;
        }

        Date d = sdf.parse(date_s.replace(",", ""));
        return new Text(format.format(d));
    }

    @Test
    public void test() throws ParseException {
        Text r = evaluate(new Text("Jan 11, 2003 "));
        Assert.assertThat(r.toString(), Is.is("01/11/03"));

        r = evaluate(new Text("6/17/1969"));
        Assert.assertThat(r.toString(), Is.is("06/17/69"));

        r = evaluate(new Text("08/22/54"));
        Assert.assertThat(r.toString(), Is.is("08/22/54"));
    }
}
