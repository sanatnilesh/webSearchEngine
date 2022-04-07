package webCrawler;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SortResults {
	// Get words from each file using reallAll() method
		public static String[] wordsFromFile(File f) {
			In in = new In(f);
			String text = in.readAll();

			text = text.replaceAll("[^a-zA-Z0-9\\s]", "");
			String[] words = text.split(" ");
			return words;

		}

		public static String getURL(File f) {
			In in = new In(f);
			String url = in.readLine();

			return url;
		}

		// Frequency Builder for Each file and compare it with search queries.
		public static void FrqBuilder(ArrayList<String> as, String key) {
			HashMap<String, Integer> wordMap = new HashMap<String, Integer>();
			for (String fileName : as) {
				String[] wordlist = wordsFromFile(new File("src\\TxtFiles\\" + fileName));    
				for (String word : wordlist) {
					if (word.toLowerCase().equals(key.split("\\W+")[0])) {
						if (wordMap.containsKey(fileName)) {
							wordMap.put(fileName, wordMap.get(fileName) + 1);
						} else {
							wordMap.put(fileName, 1);
						}
					}
				}
			}
			sortValuesInverse(wordMap);
		}


		private static void sortValuesInverse(HashMap<String, Integer> map) {
			List list = new LinkedList(map.entrySet());
			Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
	            public int compare(Map.Entry<String, Integer> o1,
	                               Map.Entry<String, Integer> o2)
	            {
	                return (o1.getValue()).compareTo(o2.getValue());
	            }
	        });
			Collections.reverse(list);
			System.out.println("Top 10 Results using Frequency analysis:");

			for (int i = 1; i <= 10; i++) {
				String name = list.get(i).toString();
				name = name.substring(0, name.lastIndexOf('.'));
				String Occu = list.get(i).toString();

				int pos = Occu.lastIndexOf("=");

				String txtURL = name + ".txt";
				System.out.println(i + " : URL: " + getURL(new File("src\\TxtFiles\\" + txtURL)));
				System.out.println("Website name: " + name);
				System.out.println("Total number of Occurences in given file is :" + Occu.substring(pos, Occu.length()));
				System.out.println();

			}

		}


}
