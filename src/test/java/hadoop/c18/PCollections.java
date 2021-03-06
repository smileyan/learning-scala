package hadoop.c18;

/**
 * Created by huay on 9/06/2016.
 */

import org.apache.crunch.*;
import org.apache.crunch.types.PTableType;
import org.apache.crunch.types.PTypeFamily;

import java.util.Iterator;

import static org.apache.crunch.types.writable.Writables.strings;

public class PCollections {

    public static <S> String dump(PCollection<S> collection) {
        StringBuilder sb = new StringBuilder("{");
        for (Iterator<S> i = collection.materialize().iterator(); i.hasNext(); ) {
            sb.append(i.next());
            if (i.hasNext()) {
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public static <K, V> String dump(PTable<K, V> table) {
        StringBuilder sb = new StringBuilder("{");
        for (Iterator<Pair<K, V>> i = table.materialize().iterator(); i.hasNext(); ) {
            Pair<K, V> pair = i.next();
            sb.append("(").append(pair.first()).append(",").append(pair.second()).append(")");
            if (i.hasNext()) {
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public static <K, V> String dump(PGroupedTable<K, V> groupedTable) {
        return dump(groupedTable.mapValues(new MapFn<Iterable<V>, String>() {
            @Override
            public String map(Iterable<V> input) {
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                for (Iterator<V> i = input.iterator(); i.hasNext(); ) {
                    sb.append(i.next());
                    if (i.hasNext()) {
                        sb.append(",");
                    }
                }
                sb.append("]");
                return sb.toString();
            }
        }, strings()));
    }

    public static <K, V> PTable<V, K> invert(PTable<K, V> table) {
        PTypeFamily tf = table.getTypeFamily();
        PTableType<V, K> type = tf.tableOf(table.getValueType(), table.getKeyType());
        return table.parallelDo(new InversePairFn<K, V>(), type);
    }
}

class InversePairFn<S, T> extends DoFn<Pair<S, T>, Pair<T, S>> {
    @Override
    public void process(Pair<S, T> input, Emitter<Pair<T, S>> emitter) {
        emitter.emit(Pair.of(input.second(), input.first()));
    }
}