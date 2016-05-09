package hadoop;

/**
 * Created by huay on 9/05/2016.
 */
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

//import java.util.Random;
//import java.util.Iterator;
//import java.util.StringTokenizer;
//import java.util.ArrayList;
//import org.json.*;
//import org.apache.hadoop.fs.FileSystem;
//import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
//import org.apache.hadoop.util.GenericOptionsParser;



// { "_id" : XX, "user_id" : XXXXXXXX, "text" : "XXXXXXXXXXXXXX", "created_at" : "XXXXXX" }
// { "_id" : XXXXXX, "name" : "XXXX" }

class Message
{
    private int userId;

    public void parse(JSONObject json) throws JSONException
    {
        if (json.has("user_id"))
        {
            userId = json.optInt("user_id", Integer.MIN_VALUE);
        }
        else
        {
            userId = Integer.MIN_VALUE;
        }
    }

    public void parse(Text value)
    {
        parse(new JSONObject(value.toString()));
    }

    public String getUserId()
    {
        return String.valueOf(userId);
    }

    public String getOne()
    {
        return new JSONObject().put("one", "1").toString();
    }
}

class User
{
    private int id;
    private String name;

    public void parse(JSONObject json) throws JSONException
    {
        if (json.has("_id"))
        {
            id = json.optInt("_id", Integer.MIN_VALUE);
        }
        else
        {
            id = Integer.MIN_VALUE;
        }

        if (json.has("name"))
        {
            name = json.optString("name", "");
        }
        else
        {
            name = "";
        }
    }

    public String getId()
    {
        return String.valueOf(id);
    }

    public String getName()
    {
        return new JSONObject().put("name", name).toString();
    }
}

class SortMapperParser
{
    private int key;
    private String value;

    public void parse(String str)
    {
        String[] values = str.split("\\s");
        key = Integer.valueOf(values[0]);
        value = values[1];
    }

    public void parse(Text value)
    {
        parse(value.toString());
    }

    public int getKey()
    {
        return key;
    }

    public String getValue() {
        return value;
    }
}

class CountReducerParser
{
    private int key;
    private String value;

    public void parse(JSONObject json)
    {
        try {
            if (json.has("one"))
            {
                key++;
            }
            else
            {
                value = json.optString("name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parse(Text value)
    {
        parse(new JSONObject(value.toString()));
    }

    public int getKey()
    {
        return key;
    }

    public String getValue()
    {
        return value;
    }
}


class CountMapperParser
{
    String key = "";
    String value = "";

    public void parse(JSONObject json) throws JSONException
    {
        if (json.has("name"))
        {
            User user = new User();
            user.parse(json);

            key = user.getId();
            value = user.getName();
        }
        else
        {
            Message message = new Message();
            message.parse(json);

            key = message.getUserId();
            value = message.getOne();
        }
    }

    public void parse(Text value)
    {
        try{
            parse(new JSONObject(value.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getKey()
    {
        return key;
    }

    public String getValue()
    {
        return value;
    }
}

public class UserCount
{
    public static class CountMapper extends Mapper<Object, Text, Text, Text>
    {
        CountMapperParser parser = new CountMapperParser();

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException
        {
            //implement here
            //使用IntWritable.set(int)和Text.set(String)来对IntWritable和Text的object赋值
            //可以参考http://wiki.apache.org/hadoop/WordCount来写程序

            parser.parse(value);

            context.write(new Text(parser.getKey()), new Text(parser.getValue()));
        }
    }

    public static class CountReducer extends Reducer<Text,Text,Text,IntWritable>
    {
        private CountReducerParser parser = new CountReducerParser();

        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException
        {
            //implement here

            int total = 0;
            String name = "";
            for (Text value: values)
            {
                parser.parse(value);
                total = parser.getKey();
                name = parser.getValue();
            }
            context.write(new Text(name), new IntWritable(total));
        }
    }

    public static class SortMapper extends Mapper<Object, Text, IntWritable,Text>
    {
        SortMapperParser parser = new SortMapperParser();

        public void map(Object key, Text value, Context context) throws IOException, InterruptedException
        {
            //implement here
            parser.parse(value);

            context.write(new IntWritable(parser.getKey()), new Text(parser.getValue()));
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


    public static void main(String[] args) throws Exception
    {


        Configuration conf = new Configuration();
        Job job = new Job(conf, "NameCount-count");
        job.setJarByClass(UserCount.class);
        job.setMapperClass(CountMapper.class);
        job.setReducerClass(CountReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path("/input-user"));
        Path tempDir = new Path("temp");
        FileOutputFormat.setOutputPath(job, tempDir);
        //implement here
        //在这里你可以加入你的另一个job来进行排序
        //可以使用“job.waitForCompletion(true)“，该方法会开始job并等待job结束，返回值是true代表job成功，否则代表job失败
        //在SortJob中使用“sortJob.setSortComparatorClass(IntDecreasingComparator.class)”来把你的输出排序方式设置为你自己写的IntDecreasingComparator


        // System.exit(job.waitForCompletion(true) ? 0 : 1);
        job.waitForCompletion(true);

        Job sortJob = Job.getInstance(conf, "NameCount-sort");
        sortJob.setJarByClass(UserCount.class);
        sortJob.setMapperClass(SortMapper.class);
        sortJob.setReducerClass(SortReducer.class);
        sortJob.setOutputKeyClass(Text.class);
        sortJob.setOutputValueClass(Text.class);
        sortJob.setSortComparatorClass(IntDecreasingComparator.class);

        FileInputFormat.addInputPath(sortJob, tempDir);
        FileOutputFormat.setOutputPath(sortJob, new Path("/output-user"));

        sortJob.waitForCompletion(true);

    }
}

