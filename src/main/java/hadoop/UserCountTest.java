package hadoop;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by huay on 10/05/2016.
 */
public class UserCountTest {

    public static List<JSONObject> getJSONS() throws IOException, JSONException {

        List<JSONObject> jsons = new ArrayList();

        String line = "";
        try (
                InputStream fis = UserCountTest.class.getClassLoader().getResourceAsStream("input-user");
                InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
                BufferedReader br = new BufferedReader(isr);
        ) {
            while ((line = br.readLine()) != null ) {
                // Deal with the line
                // System.out.println(line);
                if (line.length() > 0)
                jsons.add(new JSONObject(line));
            }
        }

        return jsons;
    }

    public static void main(String[] args) throws IOException, JSONException {

        List<JSONObject> jsons = getJSONS();

        List<CountMapperParser> output_map = new ArrayList<>();


        CountMapperParser parser = new CountMapperParser();

        for (JSONObject json :
                jsons) {
            parser.parse(json);
            output_map.add(parser);
//            System.out.println("key: " + kv.getK() + " value: " + kv.getV());
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
        System.out.println("------------------------------");
        String[] s = "a\t\tb".split("\t");
        SortMapperParser smp = new SortMapperParser();
        smp.parse("\t10");

        System.out.println("------------------------------");
        System.out.println("key" + smp.getKey());
        System.out.println("value" + smp.getValue());

        System.out.println("------------------------------");
        int i = 0;
        System.out.println(s[0]);
        System.out.println(s[s.length -1].trim());
    }

    static Map<String, List<String>> groupBy(List<CountMapperParser> l) {
        Map<String, List<String>> r = new HashMap();

        for (CountMapperParser kv :
                l) {
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
