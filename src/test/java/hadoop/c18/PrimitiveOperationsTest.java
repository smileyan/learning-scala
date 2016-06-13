package hadoop.c18;

import com.google.common.collect.Iterables;
import org.apache.crunch.*;
import org.apache.crunch.fn.Aggregators;
import org.apache.crunch.impl.mem.MemPipeline;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

import static org.apache.crunch.types.writable.Writables.*;

/**
 * Created by huay on 9/06/2016.
 */
public class PrimitiveOperationsTest implements Serializable {

    @Test
    public void testPCollectionUnion() throws Exception {
        PCollection<Integer> a = MemPipeline.collectionOf(1, 3);
        PCollection<Integer> b = MemPipeline.collectionOf(2);
        PCollection<Integer> c = a.union(b);
        Assert.assertThat("{1,3,2}", Is.is(PCollections.dump(c)));
    }

    @Test
    public void testPCollectionParallelDo() throws Exception {
        PCollection<String> a = MemPipeline.collectionOf("cherry", "apple", "banana");
        PCollection<Integer> b = a.parallelDo(new DoFn<String, Integer>() {
            @Override
            public void process(String input, Emitter<Integer> emitter) {
                emitter.emit(input.length());
            }
        }, ints());
        Assert.assertThat("{6,5,6}", Is.is(PCollections.dump(b)));
    }

    @Test
    public void testPCollectionParallelDoMap() throws Exception {
        PCollection<String> a = MemPipeline.collectionOf("cherry", "apple", "banana");
        PCollection<Integer> b = a.parallelDo(new MapFn<String, Integer>() {
            @Override
            public Integer map(String input) {
                return input.length();
            }
        }, ints());

        Assert.assertThat("{6,5,6}", Is.is(PCollections.dump(b)));
    }

    @Test
    public void testPCollectionFilter() throws Exception {
        PCollection<String> a = MemPipeline.collectionOf("cherry", "apple", "banana");
        PCollection<String> b = a.filter(new FilterFn<String>() {
            @Override
            public boolean accept(String input) {
                return input.length() % 2 == 0;
            }
        });

        Assert.assertThat("{cherry,banana}", Is.is(PCollections.dump(b)));
    }

    @Test
    public void testPCollectionParallelDoExtractKey() throws Exception {
        PCollection<String> a = MemPipeline.collectionOf("cherry", "apple", "banana");
        PTable<Integer, String> b = a.parallelDo(new DoFn<String, Pair<Integer, String>>() {
            @Override
            public void process(String input, Emitter<Pair<Integer, String>> emitter) {
                emitter.emit(Pair.of(input.length(), input));
            }
        }, tableOf(ints(), strings()));

        Assert.assertEquals("{(6,cherry),(5,apple),(6,banana)}", PCollections.dump(b));

        // PCollection<String> a1 = MemPipeline.collectionOf("cherry", "apple", "banana");
        // PTable<Integer, String> c = a1.by(new MapFn<String, Integer>() {
        //      @Override
        //      public Integer map(String input) {
        //          return input.length();
        //      }
        // }, ints());

        // Assert.assertEquals("{(6,cherry),(5,apple),(6,banana)}", PCollections.dump(c));
    }

    @Test
    public void testGrouping() throws Exception {
        PCollection<String> a = MemPipeline.collectionOf("cherry", "apple", "banana");
        PTable<Integer, String> b = a.parallelDo(new DoFn<String, Pair<Integer, String>>() {
            @Override
            public void process(String input, Emitter<Pair<Integer, String>> emitter) {
                emitter.emit(Pair.of(input.length(), input));
            }
        }, tableOf(ints(), strings()));
        PGroupedTable<Integer, String> c = b.groupByKey();
        Assert.assertThat("{(5,[apple]),(6,[cherry,banana])}", Is.is(PCollections.dump(c)));

        PGroupedTable<Integer, String> c1 = b.groupByKey();
        PTable<Integer, String> d = c1.combineValues(Aggregators.STRING_CONCAT(";", false));
        Assert.assertThat("{(5,apple),(6,cherry;banana)}", Is.is(PCollections.dump(d)));

        d = b.groupByKey().combineValues(new CombineFn<Integer, String>() { // since value iterator is single use
            @Override
            public void process(Pair<Integer, Iterable<String>> input, Emitter<Pair<Integer, String>> emitter) {
                StringBuffer sb = new StringBuffer();
                for (Iterator i = input.second().iterator(); i.hasNext(); ) {
                    sb.append(i.next());
                    if (i.hasNext()) {
                        sb.append(";");
                    }
                }
                emitter.emit(Pair.of(input.first(), sb.toString()));
            }
        });
        Assert.assertThat("{(5,apple),(6,cherry;banana)}", Is.is(PCollections.dump(d)));

        c = b.groupByKey();

        PTable<Integer, Integer> f = c.mapValues(new MapFn<Iterable<String>, Integer>() {
            @Override
            public Integer map(Iterable<String> input) {
                return Iterables.size(input);
            }
        }, ints());
        Assert.assertThat("{(5,1),(6,2)}", Is.is(PCollections.dump(f)));

        PTable<Integer, String> g = b.groupByKey().ungroup();
        Assert.assertThat("{(5,apple),(6,cherry),(6,banana)}", Is.is(PCollections.dump(g)));
    }

    @Test
    public void testPTableCollectValues() throws Exception {
        PCollection<String> a = MemPipeline.typedCollectionOf(strings(), "cherry", "apple", "banana");
        PTable<Integer, String> b = a.by(new MapFn<String, Integer>() {
            @Override
            public Integer map(String input) {
                return input.length();
            }
        }, ints());
        Assert.assertThat("{(6,cherry),(5,apple),(6,banana)}", Is.is(PCollections.dump(b)));

        PTable<Integer, Collection<String>> c = b.collectValues();
        Assert.assertThat("{(5,[apple]),(6,[cherry, banana])}", Is.is(PCollections.dump(c)));
    }
    @Test
    public void testPCollectionStats() throws Exception {
        PCollection<String> a = MemPipeline.typedCollectionOf(strings(),
                "cherry", "apple", "banana", "banana");

        Assert.assertThat(4L, Is.is(a.length().getValue()));

        Assert.assertThat("apple", Is.is(a.min().getValue()));
        Assert.assertThat("cherry", Is.is(a.max().getValue()));

        PTable<String, Long> b = a.count();
        Assert.assertThat("{(apple,1),(banana,2),(cherry,1)}", Is.is(PCollections.dump(b)));

        PTable<String, Long> c = b.top(1);
        Assert.assertThat("{(banana,2)}", Is.is(PCollections.dump(c)));

        PTable<String, Long> d = b.bottom(2);
        Assert.assertThat("{(apple,1),(cherry,1)}", Is.is(PCollections.dump(d)));
    }
}
