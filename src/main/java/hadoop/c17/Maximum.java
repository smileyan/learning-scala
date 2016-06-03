package hadoop.c17;

import org.apache.hadoop.hive.ql.exec.UDAF;
import org.apache.hadoop.hive.ql.exec.UDAFEvaluator;
import org.apache.hadoop.io.IntWritable;

/**
 * Created by huay on 2/06/2016.
 */
public class Maximum extends UDAF {
    public static class MaximumIntUDAFEvaluator implements UDAFEvaluator {
        private IntWritable result;

        @Override
        public void init() {
            result = null;
        }

        public boolean iterate(IntWritable value) {
            if (value == null) {
                return true;
            }
            if (result == null) {
                result = new IntWritable(value.get());
            } else {
                result.set(Math.max(result.get(), value.get()));
            }
            return true;
        }
    }
}
