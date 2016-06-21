package hadoop.c3;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static javax.xml.stream.XMLStreamConstants.CHARACTERS;
import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;

/**
 * Created by hua on 20/06/16.
 */
public class XMLMapReduceReader extends Configured implements Tool{

    private static final Logger log = LoggerFactory.getLogger(XMLMapReduceReader.class);

    public static class Map extends Mapper<LongWritable, Text, Text, Text> {

        @Override
        protected void map(LongWritable key, Text value,
                           Mapper.Context context)
                throws IOException, InterruptedException {
            String document = value.toString();
            System.out.println("'" + document + "'");
            try {
                XMLStreamReader reader =
                        XMLInputFactory.newInstance().createXMLStreamReader(new
                                ByteArrayInputStream(document.getBytes()));
                String propertyName = "";
                String propertyValue = "";
                String currentElement = "";
                while (reader.hasNext()) {
                    int code = reader.next();
                    switch (code) {
                        case START_ELEMENT:
                            currentElement = reader.getLocalName();
                            break;
                        case CHARACTERS:
                            if (currentElement.equalsIgnoreCase("name")) {
                                propertyName += reader.getText();
                            } else if (currentElement.equalsIgnoreCase("value")) {
                                propertyValue += reader.getText();
                            }
                            break;
                    }
                }
                reader.close();
                context.write(propertyName.trim(), propertyValue.trim());
            } catch (Exception e) {
                log.error("Error processing '" + document + "'", e);
            }
        }
    }

    private String[] getNandV(String doc){
        String propertyName = "";
        String propertyValue = "";
        String currentElement = "";

        XMLStreamReader reader = null;
        try {
            reader = XMLInputFactory.newInstance().createXMLStreamReader(new
                    ByteArrayInputStream(doc.getBytes()));
            while (reader.hasNext()) {
                int code = reader.next();
                switch (code) {
                    case START_ELEMENT:
                        currentElement = reader.getLocalName();
                        break;
                    case CHARACTERS:
                        if (currentElement.equalsIgnoreCase("name")) {
                            propertyName += reader.getText();
                        } else if (currentElement.equalsIgnoreCase("value")) {
                            propertyValue += reader.getText();
                        }
                        break;
                }
            }
        } catch (XMLStreamException e) {

        } finally {
            try {
                reader.close();
            } catch (XMLStreamException e) {

            }
        }
        String[] result = {propertyName, propertyValue};
        return result;
    }
    /**
     * The MapReduce driver - setup and launch the job.
     *
     * @param args the command-line arguments
     * @return the process exit code
     * @throws Exception if something goes wrong
     */
    public int run(final String[] args) throws Exception {

        Job job = Job.getInstance(getConf(), "Max temperature");

        Path inputPath = new Path(args[0]);
        Path outputPath = new Path(args[1]);

        Configuration conf = super.getConf();

        conf.set("key.value.separator.in.input.line", " ");
        conf.set("xmlinput.start", "<property>");
        conf.set("xmlinput.end", "</property>");

        job.setJarByClass(XMLMapReduceReader.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        job.setMapperClass(Map.class);
        job.setInputFormatClass(XmlInputFormat.class);
        job.setNumReduceTasks(0);
        job.setOutputFormatClass(TextOutputFormat.class);

        FileInputFormat.setInputPaths(job, inputPath);
        FileOutputFormat.setOutputPath(job, outputPath);

        if (job.waitForCompletion(true)) {
            return 0;
        }
        return 1;
    }

    /**
     * Main entry point for the example.
     *
     * @param args arguments
     * @throws Exception when something goes wrong
     */
    public static void main(final String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new XMLMapReduceReader(), args);
        System.exit(res);
    }
}
