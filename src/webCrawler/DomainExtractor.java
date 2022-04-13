package webCrawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DomainExtractor {

	public static String extractDomain(String val) {
		int dot = val.indexOf("/");
		int slash = val.indexOf("/", dot + 1);
		dot = val.indexOf("/", slash + 1);
		val = val.substring(slash + 1, dot);
		return val;
	}
	
	public static void DoaminExtractor() throws URISyntaxException {
		File file = new File("src\\TxtFiles\\CrawledURLs.txt");
		Scanner myReader = null;
		try {
			myReader = new Scanner(file);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				char end = '/';
				String url = data + end;
				String domain = extractDomain(url);
				System.out.println("Domain :" + domain);
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
}
