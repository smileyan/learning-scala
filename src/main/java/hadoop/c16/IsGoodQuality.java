package hadoop.c16;

import org.apache.pig.FilterFunc;
import org.apache.pig.FuncSpec;
import org.apache.pig.backend.executionengine.ExecException;
import org.apache.pig.data.DataType;
import org.apache.pig.data.Tuple;
import org.apache.pig.impl.logicalLayer.FrontendException;
import org.apache.pig.impl.logicalLayer.schema.Schema;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huay on 3/06/2016.
 */
public class IsGoodQuality extends FilterFunc {
    @Override
    public Boolean exec(Tuple input) throws IOException {
        if (input == null || input.size() == 0) {
            return false;
        }

        try {
            Object object = input.get(0);
            if (object == null) {
                return false;
            }
            int i = (Integer) object;
            return i == 0 || i == 1 || i == 4 || i == 5 || i == 9;
        } catch (ExecException e) {
            throw new IOException(e);
        }
    }

    @Override
    public List<FuncSpec> getArgToFuncMapping() throws FrontendException {
        List<FuncSpec> funcSpecs = new ArrayList<>();
        funcSpecs.add(new FuncSpec(this.getClass().getName(),
                new Schema(new Schema.FieldSchema(null,
                        DataType.INTEGER))));

        return funcSpecs;
    }
}
