package hadoop.c5;

import hadoop.MaxTemperature;
import hadoop.MaxTemperatureMapper;
import hadoop.MaxTemperatureReducer;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.StringUtils;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by huay on 31/05/2016.
 */
public class MaxTemperatureWithCompression {
    public static void main(String[] args) throws Exception {
        if(args.length != 2) {
            System.err.println("Usage: MaxTemperatureWithCompression <input path>" +
            "<output path>");
            System.exit(-1);
        }

        Job job = Job.getInstance();

        job.setJarByClass(MaxTemperature.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileOutputFormat.setCompressOutput(job, true);
        FileOutputFormat.setOutputCompressorClass(job, GzipCodec.class);

        job.setMapperClass(MaxTemperatureMapper.class);
        job.setCombinerClass(MaxTemperatureReducer.class);
        job.setReducerClass(MaxTemperatureReducer.class);

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

    @Test
    public void testSerialize() throws IOException {
        IntWritable writable = new IntWritable(163);
        byte[] bytes = serialize(writable);

        assertThat(bytes.length, is(4));
        assertThat(StringUtils.byteToHexString(bytes), is("000000a3"));
    }

    @Test
    public void testDeserialize() throws IOException {
        IntWritable writable = new IntWritable(163);
        byte[] bytes = serialize(writable);

        IntWritable newWritable = new IntWritable();
        deserialize(newWritable, bytes);

        assertThat(newWritable.get(), is(163));
    }

    @Test
    public void testComparator() throws IOException {
        RawComparator<IntWritable> comparator = WritableComparator.get(IntWritable.class);

        IntWritable w1 = new IntWritable(163);
        IntWritable w2 = new IntWritable(67);

        assertThat(comparator.compare(w1, w2), greaterThan(0));
    }

    public static byte[] serialize(Writable writable) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DataOutputStream dataout = new DataOutputStream(out);

        writable.write(dataout);
        dataout.close();
        return out.toByteArray();
    }

    public static byte[] deserialize(Writable writable, byte[] bytes) throws IOException{
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        DataInputStream dataIn = new DataInputStream(in);
        writable.readFields(dataIn);
        dataIn.close();
        return bytes;
    }



}
