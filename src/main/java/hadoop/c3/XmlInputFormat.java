package hadoop.c3;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DataOutputBuffer;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Created by hua on 20/06/16.
 */
public class XmlInputFormat extends TextInputFormat {


    static {

    }
    private static final Logger log =
            LoggerFactory.getLogger(XmlInputFormat.class);

    public static final String START_TAG_KEY = "xmlinput.start";
    public static final String END_TAG_KEY = "xmlinput.end";

    @Override
    public RecordReader<LongWritable, Text> createRecordReader(
            InputSplit split, TaskAttemptContext context) {
        try {
            return new XmlRecordReader((FileSplit) split,
                    getConfiguration(context));
        } catch (IOException ioe) {
            log.warn("Error while creating XmlRecordReader", ioe);
            return null;
        }
    }

    /**
     * Invokes a method and rethrows any exception as runtime excetpions.
     */
    private static Object invoke(Method method, Object obj, Object... args) {
        try {
            return method.invoke(obj, args);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("Can't invoke method " + method.getName(), e);
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException("Can't invoke method " + method.getName(), e);
        }
    }

    public static Configuration getConfiguration(JobContext context) {
        Method GET_CONFIGURATION_METHOD =null;
        final String PACKAGE = "org.apache.hadoop.mapreduce";
        try {
            GET_CONFIGURATION_METHOD = Class.forName(PACKAGE + ".JobContext")
                    .getMethod("getConfiguration");
        } catch (ClassNotFoundException cnfe) {

        } catch (NoSuchMethodException nsme) {

        }
        return (Configuration) invoke(GET_CONFIGURATION_METHOD, context);
    }
    /**
     * XMLRecordReader class to read through a given xml document to
     * output xml blocks as records as specified
     * by the start tag and end tag
     */
    public static class XmlRecordReader
            extends RecordReader<LongWritable, Text> {

        private final byte[] startTag;
        private final byte[] endTag;
        private final long start;
        private final long end;
        private final FSDataInputStream fsin;
        private final DataOutputBuffer buffer = new DataOutputBuffer();
        private LongWritable currentKey;
        private Text currentValue;

        public XmlRecordReader(FileSplit split, Configuration conf)
                throws IOException {
            startTag = conf.get(START_TAG_KEY).getBytes("UTF-8");
            endTag = conf.get(END_TAG_KEY).getBytes("UTF-8");

            // open the file and seek to the start of the split
            start = split.getStart();
            end = start + split.getLength();
            Path file = split.getPath();
            FileSystem fs = file.getFileSystem(conf);
            fsin = fs.open(split.getPath());
            fsin.seek(start);
        }

        private boolean next(LongWritable key, Text value)
                throws IOException {
            if (fsin.getPos() < end && readUntilMatch(startTag, false)) {
                try {
                    buffer.write(startTag);
                    if (readUntilMatch(endTag, true)) {
                        key.set(fsin.getPos());
                        value.set(buffer.getData(), 0, buffer.getLength());
                        return true;
                    }
                } finally {
                    buffer.reset();
                }
            }
            return false;
        }

        @Override
        public void close() throws IOException {
            fsin.close();
        }

        @Override
        public float getProgress() throws IOException {
            return (fsin.getPos() - start) / (float) (end - start);
        }

        private boolean readUntilMatch(byte[] match, boolean withinBlock)
                throws IOException {
            int i = 0;
            while (true) {
                int b = fsin.read();
                // end of file:
                if (b == -1) {
                    return false;
                }
                // save to buffer:
                if (withinBlock) {
                    buffer.write(b);
                }

                // check if we're matching:
                if (b == match[i]) {
                    i++;
                    if (i >= match.length) {
                        return true;
                    }
                } else {
                    i = 0;
                }
                // see if we've passed the stop point:
                if (!withinBlock && i == 0 && fsin.getPos() >= end) {
                    return false;
                }
            }
        }

        @Override
        public LongWritable getCurrentKey()
                throws IOException, InterruptedException {
            return currentKey;
        }

        @Override
        public Text getCurrentValue()
                throws IOException, InterruptedException {
            return currentValue;
        }

        @Override
        public void initialize(InputSplit split,
                               TaskAttemptContext context)
                throws IOException, InterruptedException {
        }

        @Override
        public boolean nextKeyValue()
                throws IOException, InterruptedException {
            currentKey = new LongWritable();
            currentValue = new Text();
            return next(currentKey, currentValue);
        }
    }
}
