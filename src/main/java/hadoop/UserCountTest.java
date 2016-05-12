package hadoop;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huay on 10/05/2016.
 */
public class UserCountTest {


    private static List<String> getLines() throws IOException{
        List<String> lines = new ArrayList();

        try (
                InputStream fis = UserCountTest.class.getClassLoader().getResourceAsStream("input-user");
                InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
                BufferedReader br = new BufferedReader(isr);
        ) {

            String line ="";
            while ((line = br.readLine()) != null ) {
                if (line.length() > 0)
                    lines.add(line);
            }
        }

        return lines;
    }

    public static void main(String[] args) throws IOException {

        List<String> lines = getLines();

        List<CountMapperParser> output_map = new ArrayList<>();



        for (String line: lines) {
            CountMapperParser parser = new CountMapperParser();
            parser.parse("", line);
            if (parser.isValid()){
                output_map.add(parser);
            }
        }

        Map<String, List<String>> input_reduce = groupBy(output_map);

        for (Map.Entry<String, List<String>> mapEntry : input_reduce.entrySet())
        {

            String map_key = mapEntry.getKey();

            List<String> map_v = mapEntry.getValue();

            String name = "";
            int total = 0;

            for (String v : map_v)
            {
                CountReducerParser crp = new CountReducerParser();
                crp.parse(map_key, v);

                if (crp.isUser())
                {
                    name = crp.getK();
                }
                else
                {
                    total++;
                }
            }
            if (total != 0)
            {
                System.out.println("name :" + name + ", total:" + String.valueOf(total));
            }
        }
    }

    static Map<String, List<String>> groupBy(List<CountMapperParser> l) {
        Map<String, List<String>> r = new HashMap();

        for (CountMapperParser kv : l) {
            if (r.containsKey(kv.getK())) {
                r.get(kv.getK()).add(kv.getV());
            } else {
                List<String> vs = new ArrayList();
                vs.add(kv.getV());
                r.put(kv.getK(), vs);
            }
        }
        return r;
    }
}

class UCTest extends Configured implements Tool {

    @Override
    public int run(String[] args) throws Exception {
        Job job = new Job(getConf(), "");


        return 0;
    }
}