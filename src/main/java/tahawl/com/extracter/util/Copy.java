package tahawl.com.extracter.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FilenameUtils;

public class Copy {
	 public static void copyDirectory(File sourceLocation , File targetLocation)
			    throws IOException {


			        if (sourceLocation.isDirectory()) {
			            if (!targetLocation.exists()) {
			                targetLocation.mkdir();
			            }
			          

			            String[] children = sourceLocation.list();
			            for (int i=0; i<children.length; i++) {
			            	String ex =FilenameUtils.getExtension(children[i].toString());
			            	if(ex.contains("csv")) {
			                copyDirectory(new File(sourceLocation, children[i]),
			                        new File(targetLocation, children[i]));
			                
			            }
			            }
			        } else {

			            InputStream in = new FileInputStream(sourceLocation);
			            OutputStream out = new FileOutputStream(targetLocation);

			            // Copy the bits from instream to outstream
			            byte[] buf = new byte[1024];
			            int len;
			            while ((len = in.read(buf)) > 0) {
			                out.write(buf, 0, len);
			            }
			            in.close();
			            out.close();
			        }
			    }
	

}
