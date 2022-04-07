package webCrawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class crawler {
public static String crawl(String link) {
		
		
		String html = urlToHTML(link);
		
		Document doc = Jsoup.parse(html);  //
		String text = doc.text(); //used to find text
		String title = doc.title(); //used to find title
		createFile(title,text,link);
		
		Elements e = doc.select("a");
		String links = "";
		
		for(Element e2 : e) {
			String href = e2.attr("abs:href");
			if(href.length()>3)
			{
				links = links+"\n"+href;
			}
		}
		return links;
	}
	
	//This method is used create textfile 
	public static void createFile(String title, String text, String link) {
		try {
			String[] titlesplit = title.split("\\|");
			String newTitle = "";
			for(String s : titlesplit) {
				newTitle = newTitle+" "+s;
			}
			File f = new File("src//TxtFiles//"+newTitle+".txt");
			f.createNewFile();			
			PrintWriter pw = new PrintWriter(f); 
			pw.println(link);
			pw.println(text);
			pw.close();
			
		} catch (FileNotFoundException e) {e.printStackTrace();} catch (IOException e) {}
		
	}
	
	//This method is used to convert url to HTML
	public static String urlToHTML(String link){  //this method will take link(url) as an argument
		try {
			URL url = new URL(link);  //Creating URL classes's object
			URLConnection connection = url.openConnection(); //Returns a URLConnection instance that represents a connection to the remote object referred to by the URL.
			connection.setConnectTimeout(5000); //5 milliseconds
			connection.setReadTimeout(5000);
			Scanner sc = new Scanner(connection.getInputStream());
			StringBuffer stringBuffer = new StringBuffer();
			while(sc.hasNext()) {
				stringBuffer.append(" "+sc.next());
			}
			
			String result = stringBuffer.toString();
			sc.close();
			return result;
		}
		catch(IOException e) {} 
		return link;
	}
	
	public static void crawlPages(String links) {
		
		try {

			File crawledURLs = new File("src//TxtFiles//CrawledURLs.txt");
			crawledURLs.createNewFile();
			FileWriter fwt = new FileWriter(crawledURLs);
			fwt.close();
						
			String links2 = "";
			for(String link: links.split("\n")) {
				
				links2 = links2 + crawl(link); 
				System.out.println(link);				
				FileWriter fw = new FileWriter(crawledURLs,true);
				fw.write(link + "\n");
				
				fw.close();
			

			}
			
			String links3 = "";
//			int counter = 0;
			for(String link: links2.split("\n")) {

				In in = new In(crawledURLs);
				String linksRead = in.readAll();
				if(!linksRead.contains(link)) {
					links3 = links3 + crawl(link);
					System.out.println(link);
					FileWriter fw = new FileWriter(crawledURLs,true);
					fw.write(link + "\n");
					fw.close();
					
//					counter++;
//					if(counter == 40) {
//						System.out.println("");
//						System.out.println("WEB CRAWLING FINISHED");
//						System.out.println("");
//						break;
//					} 
				}		
				
			}
			
			int counter = 0;
			for(String link: links3.split("\n")) {
				In in = new In(crawledURLs);
				String linksRead = in.readAll();
				if(!linksRead.contains(link)) {
					crawl(link);
					System.out.println(link);
					FileWriter fw = new FileWriter(crawledURLs,true);
					fw.write(link + "\n");
					fw.close();
					
					counter++;
					if(counter == 50) {
						System.out.println("");
						System.out.println("WEB CRAWLING FINISHED");
						System.out.println("");
						break;
					} 
				}
				//System.out.println(link);				
				
			}

		
		}
		catch(Exception e) {e.printStackTrace();}
	}
	

	
	//This method is used to crawl custom url link
	public static void crawlURL(String line) {  //Here this method takes url as argument
		String[] links = line.split(" ");
		String newLine = "";
		for(String link : links) {              
			newLine = newLine + link + "\n";
		}
		crawlPages(newLine);
	}
	
	//This method is used to delete all the previous crawler link files stored in folder WebPages
	public static void deletePreviousTextFiles() {
		File directory = new File("TextFiles");  
		File files[] = directory.listFiles();  //storing all the crawled link files in files[] array
		for (File f : files) {
			f.delete();
		}
	}

	public static void main(String[] args) {
		System.out.println(
				"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Hello, Welcome to Our Web Search Engine ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println(
				"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("1. Web Crawling");
		System.out.println("2. Domain Extractor");
		System.out.println("3. Auto suggestion");
		System.out.println("4. Search Function");
		System.out.println("5. Pattern Matching");
		System.out.println("6. Spell Checking");
		try (Scanner scan = new Scanner(System.in)) {
			System.out.println("Please enter the number to perfom operation");
			int num = scan.nextInt();
			switch (num) {
			case 1:
				Scanner sc = new Scanner(System.in);
				System.out.println("");
				System.out.println(
						"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ WEB CRAWLER ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				System.out.println("");
				System.out.println();
				String custom_url = null;
				System.out.println("Please enter the URL below to begin crawling.");
				custom_url = sc.nextLine();
				crawlURL(custom_url);
				break;
			case 2:
				System.out.println("You've selected Domain Extractor:");
				try {
					DomainExtractor.DoaminExtractor();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				break;
			case 3:
				System.out.println("You've selected Auto Suggestion feature:");
				Trie.autoSuggest();
				break;
				
			case 4:
				System.out.println("Please enter Search Query");
				Scanner sc1 = new Scanner(System.in);
			    String key = sc1.nextLine();
			    System.out.println("------------------Search Result-------------------");
			    File folder = new File("src\\TxtFiles\\");
	            File[] listOfFiles = folder.listFiles();
	            ArrayList<String> files = new ArrayList<String>();
	            for (File file : listOfFiles) {
	                if (file.isFile()) {
	                	   int i=0;
	                       files.add(file.getName());
	                       i++;          
	                }
	            }
	            try {
				SortResults.FrqBuilder(files, key.toLowerCase());
	            }
	            catch(IndexOutOfBoundsException e)
	            {
	            	e.printStackTrace();
	            }
				
				break;
			case 5:
				PatternMatching.patternmatching();
				break;
			case 6:
				SpellChecker.spellchecker();
				break;	
			default:
				System.out.println("Please select any value from the menu to perfom operation:");
			}
		} catch (Exception e) {
			System.out.println("Input MisMatch:" + e + e.getMessage());
		}

	}
}
