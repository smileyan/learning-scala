package hadoop.c6;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by huay on 31/05/2016.
 */
public class MaxTemperatureMapper extends Mapper<LongWritable, Text, Text, IntWritable>{

    private NcdcRecordParser parser = new NcdcRecordParser();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        parser.parse(value);

        if (parser.isValidTemperature()) {
           context.write(new Text(parser.getYear()), new IntWritable(parser.getAirTemperature()));
        }
    }
}
