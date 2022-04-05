package webCrawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Trie {
	public class TrieNode {
		Map<Character, TrieNode> children;
		char a;
		boolean isWordComplete;

		public TrieNode(char a) {
			this.a = a;
			children = new HashMap<>();
		}

		public TrieNode() {
			children = new HashMap<>();
		}

		public void insert(String w) {
			if (w == null || w.isEmpty())
				return;
			char firstChar = w.charAt(0);
			TrieNode child = children.get(firstChar);
			if (child == null) {
				child = new TrieNode(firstChar);
				children.put(firstChar, child);
			}

			if (w.length() > 1) {
				child.insert(w.substring(1));
			} else {
				child.isWordComplete = true;
			}
		}

	}

	TrieNode r;

	public Trie(List<String> words) {
		r = new TrieNode();
		for (String w : words) {
			r.insert(w);
		}
	}

	public boolean find(String pre, boolean e) {
		TrieNode lastNode = r;
		for (char c : pre.toCharArray()) {
			lastNode = lastNode.children.get(c);
			if (lastNode == null) {
				return false;
			}
		}
		return !e || lastNode.isWordComplete;
	}

	public boolean find(String pre) {
		return find(pre, false);
	}

	public void suggestHelper(TrieNode r, List<String> list, StringBuffer cur) {
		if (r.isWordComplete) {
			list.add(cur.toString());
		}

		if (r.children == null || r.children.isEmpty())
			return;

		for (TrieNode child : r.children.values()) {
			suggestHelper(child, list, cur.append(child.a));
			cur.setLength(cur.length() - 1);
		}
	}

	public List<String> suggest(String pre) {
		List<String> list = new ArrayList<>();
		TrieNode lastNode = r;
		StringBuffer cur = new StringBuffer();
		for (char a : pre.toCharArray()) {
			lastNode = lastNode.children.get(a);
			if (lastNode == null) {
				return list;
			}
			cur.append(a);
		}
		suggestHelper(lastNode, list, cur);
		return list;
	}

	public static void autoSuggest() {

		try {
			File myObj = new File("src\\WebPages\\ Blackboard Learn.txt");
			Scanner input = new Scanner(myObj);
			List<String> words = new ArrayList<String>();
			while (input.hasNext()) {
				String word = input.next();
				words.add(word);
			}
			Trie trie = new Trie(words);
			Scanner scan = new Scanner(System.in);
			System.out.println("Please provide letters .............");
			String s = scan.next();
			System.out.println(trie.suggest(s));
		} catch (FileNotFoundException e) {
			System.out.println("Exception : " + e + e.getMessage());
		}

	}

}
