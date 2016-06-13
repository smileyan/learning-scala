package hadoop.c18;

import org.apache.crunch.DoFn;
import org.apache.crunch.Emitter;
import org.apache.crunch.PCollection;
import org.apache.crunch.Pipeline;
import org.apache.crunch.impl.mr.MRPipeline;
import org.apache.crunch.io.To;
import org.apache.crunch.types.writable.Writables;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * Created by huay on 11/06/2016.
 */
public class MyPipeline extends Configured implements Tool{
    public static void main(String[] args) throws Exception {
        ToolRunner.run(new Configuration(), new MyPipeline(), args);
    }

    @Override
    public int run(String[] args) throws Exception {
        Pipeline pipeline = new MRPipeline(MyPipeline.class, getConf());
        PCollection<String> lines = pipeline.readTextFile(args[0]);

        PCollection<String> words = splitLines(lines);
        words.count().write(To.textFile(args[1]));

        pipeline.done();

        return 0;
    }

    public static PCollection<String> splitLines(PCollection<String> lines) {
        // This will work fine because the DoFn is defined inside of a static method.
        return lines.parallelDo(new DoFn<String, String>() {
            @Override
            public void process(String line, Emitter<String> emitter) {
                for (String word : line.split("\\s+")) {
                    emitter.emit(word);
                }
            }
        }, Writables.strings());
    }
}
