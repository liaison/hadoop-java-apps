package apps.maxtemperature;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

/**
 * 
 * @author Lisong Guo <lisong.guo@me.com>
 * @date   Mar 29, 2015
 */
public class MaxTemperatureMapper extends MapReduceBase 
	implements Mapper<LongWritable, Text, Text, IntWritable>{

	@Override
	public void map(LongWritable key, Text value,
			OutputCollector<Text, IntWritable> output, Reporter reporter)
			throws IOException {
		
		String line = value.toString();
		String year = line.substring(0, 4);
		
		int temperature = Integer.parseInt(line.substring(5, 7));
		
		output.collect(new Text(year), new IntWritable(temperature));
	}
}



