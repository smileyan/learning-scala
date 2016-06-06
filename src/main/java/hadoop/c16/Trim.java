package hadoop.c16;

import org.apache.pig.PrimitiveEvalFunc;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by huay on 6/06/2016.
 */
public class Trim extends PrimitiveEvalFunc<String, String> {
    @Override
    public String exec(String input) throws IOException {
        return input.trim();
    }

    @Test
    public void testTrim() throws IOException{
        Trim trim = new Trim();
        Assert.assertThat(trim.exec(" hello "), Is.is("hello"));
    }
}
