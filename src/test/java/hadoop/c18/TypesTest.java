package hadoop.c18;

import com.google.common.collect.Lists;
import org.apache.crunch.*;
import org.apache.crunch.impl.mr.MRPipeline;
import org.apache.crunch.io.From;
import org.apache.crunch.lib.Sort;
import org.apache.crunch.test.TemporaryPath;
import org.apache.crunch.types.avro.Avros;
import org.apache.crunch.types.writable.WritableTypeFamily;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huay on 10/06/2016.
 */
public class TypesTest implements Serializable {

    @Rule
    public transient TemporaryPath tmpDir = new TemporaryPath();

    @Test
    public void testDefaultPType() throws Exception {
        String inputPath = tmpDir.copyResourceFileName("sample.txt");
        Pipeline pipeline = new MRPipeline(getClass());
        PCollection<String> lines = pipeline.read(From.textFile(inputPath));

        Assert.assertThat(WritableTypeFamily.getInstance(), Is.is(lines.getPType().getFamily()));
    }

    @Test
    public void testAvroReflect() throws Exception{
        String inputPath = tmpDir.copyResourceFileName("sample.txt");
        Pipeline pipeline = new MRPipeline(getClass());
        PCollection<String> lines = pipeline.read(From.textFile(inputPath));

        PCollection<WeatherRecord> records = lines.parallelDo(
                new DoFn<String, WeatherRecord>() {
                    NcdcRecordParser parser = new NcdcRecordParser();

                    @Override
                    public void process(String input, Emitter<WeatherRecord> emitter) {
                        parser.parse(input);

                        if (parser.isValidTemperature()) {
                            emitter.emit(new WeatherRecord(parser.getYearInt(), parser.getAirTemperature(), parser.getStationId()));
                        }
                    }
                }, Avros.records(WeatherRecord.class));

        PTable<Pair<Integer, Integer>, WeatherRecord> table = records.by(
                new MapFn<WeatherRecord, Pair<Integer, Integer>>() {

                    @Override
                    public Pair<Integer, Integer> map(WeatherRecord input) {
                        return Pair.of(input.getYear(), input.getTemperature());
                    }
                }, Avros.pairs(Avros.ints(), Avros.ints()));

        PCollection<WeatherRecord> sortedRecords = Sort.sort(table).values();

        Iterable<WeatherRecord> materialized = sortedRecords.materialize();

        List<WeatherRecord> expectedContent = Lists.newArrayList(
                new WeatherRecord(1949, 78, "012650-99999"),
                new WeatherRecord(1949, 111, "012650-99999"),
                new WeatherRecord(1950, -11, "011990-99999"),
                new WeatherRecord(1950, 0, "011990-99999"),
                new WeatherRecord(1950, 22, "011990-99999")
        );
        Assert.assertEquals(expectedContent, Lists.newArrayList(materialized));

        pipeline.done();
    }
}
