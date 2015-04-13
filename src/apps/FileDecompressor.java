package apps;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;

/**
 * Decompress a Gzip file
 * Usage: 
 * 		> hadoop FileDecompressor file.gz
 * 
 * @author Lisong Guo <lisong.guo@me.com>
 * @date   Apr 13, 2015
 */
public class FileDecompressor {

	public static void main(String[] args) throws IOException {
		String uri = args[0];
		
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(URI.create(uri), conf);
		
		// Infer a codec from the suffix of a file.
		Path inputPath = new Path(uri);
		CompressionCodecFactory factory = new CompressionCodecFactory(conf);
		CompressionCodec codec = factory.getCodec(inputPath);
		if (codec == null) {
			System.err.println("No Codec found for " + uri);
			System.exit(1);
		}
		
		InputStream in = null;
		OutputStream out = null;
		
		try {
			in = codec.createInputStream(fs.open(inputPath));
			
			String outputUri = CompressionCodecFactory.removeSuffix(
					uri, codec.getDefaultExtension());
			
			out = fs.create(new Path(outputUri));
			
			// Decompress the Gzip file.
			IOUtils.copyBytes(in, out, conf);
		} finally {
			IOUtils.closeStream(in);
			IOUtils.closeStream(out);
		}
	}

}
