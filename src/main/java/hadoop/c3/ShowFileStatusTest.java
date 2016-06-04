package hadoop.c3;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by huay on 30/05/2016.
 */
public class ShowFileStatusTest {

    FileSystem fs;
    Configuration conf;

    @Before
    public void setUp() throws IOException {
        conf = new Configuration();
        fs = FileSystem.get(conf);

        if (System.getProperty("test.build.data") == null) {
            System.setProperty("test.build.data", "/tmp");
        }
        OutputStream out = fs.create(new Path("/dir/file"));
        out.write("content".getBytes("UTF-8"));
        out.close();
    }

    @After
    public void tearDown() throws IOException {
        if (fs != null) {
            fs.close();
        }
    }

    @Test(expected = FileNotFoundException.class)
    public void throwsFileNotFoundForNonExistentFile() throws IOException {
        fs.getFileStatus(new Path("no-such-file"));
    }

}
