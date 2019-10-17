package tahawl.com.extracter.util;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

public class Ls_ltrh {
	private static List<String> file = new ArrayList<String>();

	public static List<String> listFilesForFolder(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (!fileEntry.isDirectory() && fileEntry.isFile() && fileEntry.getName().endsWith(".csv")) {
				file.add(fileEntry.getName());
			} else {
				if (FilenameUtils.getExtension(fileEntry.getName()).contains("csv")) {
					listFilesForFolder(fileEntry);
				}

			}
		}
		return file;
	}

}
//2019-03-12 10:33:00+03:00