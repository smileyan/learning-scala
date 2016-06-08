package hadoop.c15;

import com.cloudera.sqoop.lib.RecordParser;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.Tool;

import java.io.IOException;

/**
 * Created by huay on 8/06/2016.
 */
public class MaxWidgetId extends Configured implements Tool{

    public static class MaxWidgetMapper
        extends Mapper<LongWritable, Text, LongWritable, Widget> {

        private Widget maxWidget = null;

        public void map(LongWritable k, Text v, Context context) {
            Widget widget = new Widget();
            try {
                widget.parse(v);
            } catch (RecordParser.ParseError pe) {
                return;
            }

            Integer id = widget.get_id();

            if (null == id) {
                return;
            } else {
                if (maxWidget == null
                        || id.intValue() > maxWidget.get_id().intValue()) {
                    maxWidget = widget;
                }
            }
        }

        public void cleanup(Context context) throws IOException, InterruptedException{
            if (null != maxWidget) {
                context.write(new LongWritable(0), maxWidget);
            }
        }

    }

    @Override
    public int run(String[] args) throws Exception {
        return 0;
    }
}
