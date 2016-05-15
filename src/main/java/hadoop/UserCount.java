package hadoop;

/**
 * Created by huay on 9/05/2016.
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.GzipCodec;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

class Message
{
    private String userId;

    public static String USER_ID_TAG = "user_id";
    public static String ONE_TAG = "o";

    public void parse(JSONObject json) throws JSONException
    {
        userId = json.get(Message.USER_ID_TAG).toString();
    }

    public String getUserId()
    {
        return userId;
    }

    public JSONObject buildJson()
    {
        return new JSONObject().put(Message.ONE_TAG, "1");
    }
}

class User
{
    public static String NAME_TAG = "name";
    public static String ID_TAG = "_id";

    private String id;
    private String name;

    public void parse(JSONObject json) throws JSONException
    {
        id = json.get(User.ID_TAG).toString();
        name = json.getString(User.NAME_TAG);
    }

    public String getId()
    {
        return id;
    }

    public JSONObject buildJson()
    {
        return new JSONObject().put(User.NAME_TAG, name);
    }
}

class CountMapperParser
{
    private boolean _isValid = false;
    private String k = "";
    private String v = "";

    public void parse(Object k, String v)
    {
        try
        {
            internal_parse(new JSONObject(v));
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            this._isValid = false;
        }
    }

    private void internal_parse(JSONObject json) throws JSONException
    {
        if (json.has(User.NAME_TAG) && json.has(User.ID_TAG))
        {
            this._isValid = true;

            User user = new User();
            user.parse(json);

            this.k = user.getId();
            this.v = user.buildJson().toString();
        }
        else if (json.has(Message.USER_ID_TAG))
        {
            this._isValid = true;

            Message message = new Message();
            message.parse(json);

            this.k = message.getUserId();
            this.v = message.buildJson().toString();
        }
        else
        {
            this._isValid = false;
        }
    }

    public String getK() {
        return this.k;
    }

    public String getV() {
        return this.v;
    }

    public boolean isValid()
    {
        return this._isValid;
    }
}

class CountReducerParser
{
    private boolean _isUser = false;
    private boolean _isValid = false;
    private String k = "";
    private Integer v = Integer.MIN_VALUE;

    public void parse(String key, String value)
    {
        try
        {
            internal_parse(key, value);
        }
        catch (Exception e)
        {
            _isValid = false;
        }
    }

    private void internal_parse(String k, String v) throws Exception
    {
        JSONObject json = new JSONObject(v);
        if (json.has(Message.ONE_TAG))
        {
            this._isValid = true;
            this._isUser = false;

            this.k = k;
            this.v = 1;

        }
        else if (json.has(User.NAME_TAG))
        {
            this._isValid = true;
            this._isUser = true;

            String name = json.optString(User.NAME_TAG);
            if (!name.contains("\t"))
            {
                this.k = name;
                this.v = 0;
            }
            else
            {
                this.k = name.replace("\t","");
                this.v = 0;
            }
        }
        else
        {
            this._isValid = false;
        }
    }

    public Integer getV() {
        return this.v;
    }

    public String getK() {
        return this.k;
    }

    public boolean isValid()
    {
        return this._isValid;
    }

    public boolean isUser()
    {
        return this._isUser;
    }
}

class SortMapperParser
{
    private int k;
    private String v;
    private boolean _isValid;

    public void parse(Object key, String value)
    {
        try
        {
            internal_parse(value);
        }
        catch (Exception e)
        {
            this._isValid = false;
            e.printStackTrace();
        }
    }

    private void internal_parse(String str)
    {
        String[] values = str.trim().split("\t");
        if (values.length > 1)
        {
            this._isValid = true;
            this.v = values[0];
            this.k = Integer.valueOf(values[values.length - 1].trim());
        }
        else
        {
            this._isValid = false;
        }
    }

    public boolean isValid()
    {
        return this._isValid;
    }

    public int getK()
    {
        return this.k;
    }

    public String getV()
    {
        return this.v;
    }
}

public class UserCount
{
    public static class CountMapper extends Mapper<Object, Text, Text, Text>
    {


        public void map(Object key, Text value, Context context) throws IOException, InterruptedException
        {
            //implement here
            //使用IntWritable.set(int)和Text.set(String)来对IntWritable和Text的object赋值
            //可以参考http://wiki.apache.org/hadoop/WordCount来写程序

            CountMapperParser parser = new CountMapperParser();
            parser.parse(key, value.toString());

            if (parser.isValid())
            {
                context.write(new Text(parser.getK()), new Text(parser.getV()));
            }
        }
    }

    public static class CountReducer extends Reducer<Text,Text,Text,IntWritable>
    {
        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException
        {
            //implement here
            int total = 0;
            String name = "";

            for (Text value: values)
            {
                CountReducerParser parser = new CountReducerParser();
                parser.parse(key.toString(), value.toString());

                if (parser.isValid()) {
                    if (parser.isUser()) {
                        name = parser.getK();
                    } else {
                        total = total + parser.getV();
                    }
                }
            }

            if (!name.equals(""))
            {
                context.write(new Text(name), new IntWritable(total));
            }
        }
    }

    public static class SortMapper extends Mapper<Object, Text, IntWritable,Text>
    {
        SortMapperParser parser = new SortMapperParser();

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException
        {
            //implement here
            parser.parse(key, value.toString());

            if (parser.isValid())
            {
                context.write(new IntWritable(parser.getK()), new Text(parser.getV()));
            }
        }
    }

    public static class SortReducer extends Reducer<IntWritable,Text,IntWritable,Text>
    {

        public void reduce(IntWritable key,Iterable<Text> values, Context context)throws IOException, InterruptedException
        {
            //implement here
            for (Text value : values)
            {
                context.write(key, value);
            }
        }
    }

    private static class IntDecreasingComparator extends IntWritable.Comparator
    {
        //注意默认的comparator是Increasing的，所以你完全没有必要明白下面两个method的意义
        //返回值为-1,0,1中的一个
        public int compare(WritableComparable a, WritableComparable b)
        {
            //implement here
            return 0 - super.compare(a, b);
        }

        public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2)
        {
            //implement here
            return 0 - super.compare(b1, s1, l1, b2, s2, l2);
        }
    }

    private static void runNameCount(Configuration conf, FileSystem fs) throws Exception {
        Job job = Job.getInstance(conf, "NameCount-count");
        job.setJarByClass(UserCount.class);
        job.setMapperClass(CountMapper.class);
        // job.setCombinerClass(CountReducer.class);
        job.setReducerClass(CountReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setOutputFormatClass(SequenceFileOutputFormat.class);

        conf.setBoolean(Job.MAP_OUTPUT_COMPRESS, true);
        conf.setClass(Job.MAP_OUTPUT_COMPRESS_CODEC, GzipCodec.class, CompressionCodec.class);

        FileInputFormat.addInputPath(job, new Path("/input-user"));

        Path tempDir = new Path("temp");
        if (fs.exists(tempDir)){
            fs.delete(tempDir, true);
        }
        FileOutputFormat.setOutputPath(job, tempDir);
        // FileOutputFormat.setCompressOutput(job, true);
        // FileOutputFormat.setOutputCompressorClass(job, GzipCodec.class);
        //implement here
        //在这里你可以加入你的另一个job来进行排序
        //可以使用“job.waitForCompletion(true)“，该方法会开始job并等待job结束，返回值是true代表job成功，否则代表job失败
        //在SortJob中使用“sortJob.setSortComparatorClass(IntDecreasingComparator.class)”来把你的输出排序方式设置为你自己写的IntDecreasingComparator


        // System.exit(job.waitForCompletion(true) ? 0 : 1);
        job.waitForCompletion(true);
    }

    private static void runSortJob(Configuration conf, FileSystem fs) throws Exception {
        Job sortJob = Job.getInstance(conf, "NameCount-sort");
        sortJob.setJarByClass(UserCount.class);
        sortJob.setMapperClass(SortMapper.class);
        sortJob.setReducerClass(SortReducer.class);
        sortJob.setOutputKeyClass(IntWritable.class);
        sortJob.setOutputValueClass(Text.class);
        sortJob.setSortComparatorClass(IntDecreasingComparator.class);

        sortJob.setInputFormatClass(SequenceFileInputFormat.class);

        FileInputFormat.addInputPath(sortJob, new Path("temp"));

        Path sort_output = new Path("op_temp");
        if (fs.exists(sort_output))
        {
            fs.delete(sort_output, true);
        }
        FileOutputFormat.setOutputPath(sortJob, sort_output);

        sortJob.waitForCompletion(true);
    }

    private static void printConf(Configuration conf) {
        for (Map.Entry<String, String> entry : conf) {
            System.out.printf("%s=%s\n", entry.getKey(), entry.getValue());
        }
    }

    public static void main(String[] args) throws Exception
    {
        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        printConf(conf);


        runNameCount(conf, fs);
        runSortJob(conf, fs);
    }
}
