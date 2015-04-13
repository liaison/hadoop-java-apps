package apps;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionOutputStream;
import org.apache.hadoop.util.ReflectionUtils;

/**
 * 
 * @author Lisong Guo <lisong.guo@me.com>
 * @date   Apr 13, 2015
 */
public class StreamCompressor {

	public static void main(String[] args) 
			throws ClassNotFoundException, IOException {

		String codecClassName = args[0];
		
		Class<?> codecClass = Class.forName(codecClassName);
		
		Configuration conf = new Configuration();
		CompressionCodec codec = (CompressionCodec)
				ReflectionUtils.newInstance(codecClass, conf);
		
		CompressionOutputStream out = codec.createOutputStream(System.out);
		IOUtils.copyBytes(System.in, out, 4096 /*buffer*/, false);

		out.finish();
	}

}
