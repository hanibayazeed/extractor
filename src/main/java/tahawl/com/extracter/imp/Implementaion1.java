package tahawl.com.extracter.imp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import tahawl.com.extracter.model.HelperModel;
import tahawl.com.extracter.util.Copy;
import tahawl.com.extracter.util.Ls_ltrh;

public class Implementaion1 {
	static List<HelperModel> holder = new CopyOnWriteArrayList<>();
	static BufferedReader br = null;
	static String line = "";
	static String cvsSplitBy = ",";
	static String incomingName = null;
	static String type = null;
	static Long temp = null;
	static String datamatch = null;
	static String Duration = null;
	static String newFileSaver = null;
	static String datee = null;
	static List<String> list = new ArrayList<>();
	static FileWriter fw;
	static Pattern typePattern = Pattern.compile("(\\{ teleservice:\\{)(.+)(\\} \\})");
	static Pattern UserPattern = Pattern.compile("(\\{ rOUTEName:\\{)(.+)(\\} \\})");
	static Pattern newFilePattern = Pattern.compile("^.*(?=(\\.csv))");
	static Pattern patternDuration = Pattern.compile("[^\"]+");
	/*
	 * static Pattern patternDate =
	 * Pattern.compile("[\\d-]+ [0-9]{2}:[0-9]{2}:[0-9]{2}");
	 */
	static Pattern patternDate = Pattern.compile("([\\d]+-[\\d]+)");


	/*
	 * public static String TimeFormater(String str) { DateTimeFormatter formatter =
	 * DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); String formattedDateTime
	 * = LocalDateTime.parse(str, formatter).toString(); return formattedDateTime; }
	 */
	public static void Start() throws IOException {
		Copy.copyDirectory(new File("./"), new File("./OldFiles"));
		List<String> file = Ls_ltrh.listFilesForFolder(new File("./"));

		for (String csvFileName : file) {
			list.clear();
			Matcher newFileMatcher = newFilePattern.matcher(csvFileName);
			if (newFileMatcher.find()) {
				newFileSaver = newFileMatcher.group(0);
				Files.deleteIfExists(Paths.get("./" + newFileSaver + "_INCOMING.CDR"));

			}

			try {

				br = new BufferedReader(new FileReader("./" + csvFileName));
				while ((line = br.readLine()) != null) {
					if (!line.contains("teleservice")) {
						continue;
					}
					Matcher UserMatcher = UserPattern.matcher(line.split(cvsSplitBy)[4]);
					Matcher TypeMatcher = typePattern.matcher(line.split(cvsSplitBy)[6]);
					Matcher matcherDate = patternDate.matcher(line.split(cvsSplitBy)[7]);
					Matcher matcherDuration = patternDuration.matcher(line.split(cvsSplitBy)[9]);

					if (UserMatcher.find()) {
						incomingName = UserMatcher.group(2);
					}
					if (TypeMatcher.find()) {
						type = TypeMatcher.group(2);
						if (type.contains("0x11")) {
							type = "VOICE";
						} else if (type.contains("0x12")) {
							type = "SMS";
						} else if (type.contains("0x13")) {
							type = "DATA";
						} else {
							type = "NOT_FOUND";
						}
					}
					if (matcherDate.find()) {
						datee = matcherDate.group(0);
						datee= datee+"-01T00:00:00.000Z";
						/* datee = TimeFormater(datamatch); */
					}
					if (matcherDuration.find()) {
						Duration = matcherDuration.group(0);
					}


					holder.add(new HelperModel(datee, Duration, incomingName, type));
		

			
				
				}
				fw = new FileWriter("./" + newFileSaver + "_INCOMING.CDR");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

					for(int i=0;i<holder.size()-1;i++) {
						for(int j=i+1;j<holder.size();j++) {
							if(holder.get(i).getUser().contains(holder.get(j).getUser())) {
								temp =Long.parseLong(holder.get(i).getDuration())+Long.parseLong(holder.get(j).getDuration());
								holder.get(i).setDuration(temp.toString());
								holder.remove(j);
								j--;
								
							}
						}
					}
					

					for (HelperModel hold : holder) {
						list.add(hold.getDate() + ".000Z;" + hold.getDuration() + ";" + hold.getUser() + ";"
								+ hold.getType() + "_IN" + ";;;;;;;;;;;;;;;;;;; \n");
					}
					for (String listt : list) {
						fw.write(listt);
					}
					fw.close();
					holder.clear();
				}
			}

		}
	}
}
