package hadoop.c17;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 * Created by huay on 2/06/2016.
 */
public class Strip extends UDF {
    private Text result = new Text();

    public Text evaluate(Text str) {
        if (str == null) {
            return null;
        }

        result.set(StringUtils.strip(str.toString()));
        return result;
    }

    public Text evaluate(Text str, String stripChars) {
        if (str == null) {
            return null;
        }

        result.set(StringUtils.strip(str.toString(), stripChars));
        return result;
    }
}

// 1. a udf must be a subclass of org.apache.hadoop.hive.ql.exec.UDF
// 2. A UDF must implement at least one evaluate() method.