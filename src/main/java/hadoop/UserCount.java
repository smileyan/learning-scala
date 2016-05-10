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


class KeyAndValue<K,V> {
    private K k;
    private V v;

    public KeyAndValue(K k, V v) {
        this.k = k;
        this.v = v;
    }

    public void setK(K k) {
        this.k = k;
    }

    public void setV(V v) {
        this.v = v;
    }

    public K getK() {
        return k;
    }

    public V getV() {
        return v;
    }
}

class Message
{
    private Object userId;

    public void parse(JSONObject json) throws JSONException
    {
        if (json.has("user_id"))
        {
            userId = json.get("user_id");
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
    private String id;
    private String name;

    public void parse(JSONObject json) throws JSONException
    {
        if (json.has("_id"))
        {
            id = json.get("_id").toString();
        }
        else
        {
            id = "no_user_id";
        }

        if (json.has("name"))
        {
            name = json.optString("name", "");
        }
        else
        {
            name = "no_user_name";
        }
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return new JSONObject().put("name", name).toString();
    }
}



class CountReducerParser
{
    boolean _name = true;
    String name = "";

    public boolean hasName() {
        return _name;
    }

    public String getName()
    {
        return this.name;
    }

    public KeyAndValue<String, Integer> parse(KeyAndValue<String, String> input)
    {
        JSONObject json = new JSONObject(input.getV());
        if (json.has("name"))
        {
            this.name = json.optString("name");
            _name = true;
            return new KeyAndValue<>(name.replace("\t",""), 0);
        }
        else if (json.has("one"))
        {
            _name = false;
            return new KeyAndValue<>(input.getK(), 1);
        }
        else
        {
            _name = false;
            return new KeyAndValue<>(input.getK(), 0);
        }
    }

}


class CountMapperParser
{
    private boolean _isValid = false;

    public boolean isValid() {
        return this._isValid;
    }

    public KeyAndValue<String, String> parse(JSONObject json) throws JSONException
    {
        if (json.has("name") && json.has("_id"))
        {
            this._isValid = true;

            User user = new User();
            user.parse(json);

            return new KeyAndValue(user.getId(), user.getName());
        }
        else if (json.has("user_id") && json.has("_id") && json.has("text") && json.has("created_at"))
        {
            this._isValid = true;

            Message message = new Message();
            message.parse(json);

            return new KeyAndValue(message.getUserId(), message.getOne());
        }
        else
        {
            this._isValid = false;
            return null;
        }
    }

    public KeyAndValue<String, String> parse(KeyAndValue<Object, String> kv)
    {

        try {
            return parse(new JSONObject(kv.getV()));
        } catch (JSONException e) {
            e.printStackTrace();
            _isValid = false;
            return new KeyAndValue("", "");
        }
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

            KeyAndValue inputKV = new KeyAndValue(key, value.toString());
            KeyAndValue<String, String> outputKV = parser.parse(inputKV);

            if (parser.isValid())
            {
                context.write(new Text(outputKV.getK()), new Text(outputKV.getV()));

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
                CountReducerParser crp = new CountReducerParser();
                KeyAndValue<String, Integer> kv = crp.parse(new KeyAndValue(key.toString(), value.toString()));

                if (crp.hasName())
                {
                    name = kv.getK();
                }
                else
                {
                    total = total + kv.getV();
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
            parser.parse(value);

            if (parser.getKey() > 0) {
                context.write(new IntWritable(parser.getKey()), new Text(parser.getValue()));
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


    public static void main(String[] args) throws Exception
    {


        Configuration conf = new Configuration();
//        Job job = new Job(conf, "NameCount-count");
//        job.setJarByClass(UserCount.class);
//        job.setMapperClass(CountMapper.class);
//        job.setReducerClass(CountReducer.class);
//        job.setOutputKeyClass(Text.class);
//        job.setOutputValueClass(Text.class);
//
//        FileInputFormat.addInputPath(job, new Path("/input-user"));
//        Path tempDir = new Path("temp");
//        FileOutputFormat.setOutputPath(job, tempDir);
//        //implement here
//        //在这里你可以加入你的另一个job来进行排序
//        //可以使用“job.waitForCompletion(true)“，该方法会开始job并等待job结束，返回值是true代表job成功，否则代表job失败
//        //在SortJob中使用“sortJob.setSortComparatorClass(IntDecreasingComparator.class)”来把你的输出排序方式设置为你自己写的IntDecreasingComparator
//
//
//        // System.exit(job.waitForCompletion(true) ? 0 : 1);
//        job.waitForCompletion(true);

        Job sortJob = Job.getInstance(conf, "NameCount-sort");
        sortJob.setJarByClass(UserCount.class);
        sortJob.setMapperClass(SortMapper.class);
        sortJob.setReducerClass(SortReducer.class);
        sortJob.setOutputKeyClass(IntWritable.class);
        sortJob.setOutputValueClass(Text.class);
        sortJob.setSortComparatorClass(IntDecreasingComparator.class);

        Path tempDir1 = new Path("temp/part-r-00000");
        FileInputFormat.addInputPath(sortJob, tempDir1);
        FileOutputFormat.setOutputPath(sortJob, new Path("op_temp"));

        sortJob.waitForCompletion(true);

    }
}



class SortMapperParser
{
    private int key;
    private String value;

    public void parse(String str)
    {
        String[] values = str.split("\t");
        if (values.length > 1) {
            value = values[0];
            key = Integer.valueOf(values[values.length - 1].trim());
        }
        else
        {
            value = "";
            key = -1;
        }
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