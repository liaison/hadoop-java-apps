package apps.maxtemperature;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;

/**
 * @author Lisong Guo <lisong.guo@me.com>
 * @date   Mar 29, 2015
 */
public class MaxTemperature {

	public static void main(String[] args) throws IOException {
		
		if (args.length != 2) {
			System.err.println(
					"Usage: MaxTemperature <input_path> <output_path>");
			System.exit(-1);
		}
		
		JobConf conf = new JobConf(MaxTemperature.class);
		conf.setJobName("Max Temperature");
		
		FileInputFormat.addInputPath(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));
		
		conf.setMapperClass(MaxTemperatureMapper.class);
		conf.setReducerClass(MaxTemperatureReducer.class);
		
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(IntWritable.class);
	
		JobClient.runJob(conf);
	}

}








