package edu.brandeis.cs12b.pa3;

import java.util.*;
import java.io.*;

public class CaesarCipher {

	File dict;
	Set<String> words = new HashSet<String>();
	
	public CaesarCipher() throws FileNotFoundException {
		dict = new File("dictionary/google10000.txt");
		Scanner scan = new Scanner(dict);
		while (scan.hasNextLine()) {
			words.add(scan.nextLine());
		}
	}
	
	private char calc(char c, int n, char low, char high) {
		int diff = (int)c - (int)low - (int)n;
		char result = diff >= 0 ? (char)(low + diff) : (char)(high + diff % (high - low + 1));
		return result;
	}
	
	/**
	 * This is method to encode a string with an n shift
	 * @param s the string to be encoded
	 * @param n the shift digit
	 * @return String the string encoded
	 */
	public String encode(String s, int n) {
		if (s == null) {
			return null;
		}
		StringBuilder message = new StringBuilder(s);
		for (int i = 0; i < message.length(); i++) {
			char c = message.charAt(i);
			if (c >= '0' && c <= '9') // digits
				message.replace(i,i+1, "" + (char)((c - '0' + n) % 10 + '0'));
			else if (c >= 'A' && c <= 'Z') // upper case alphabets
				message.replace(i, i+1, "" + (char)((c - 'A' + n) % 26 + 'A'));
			else if (c >= 'a' && c <= 'z') // lower case alphabets
				message.replace(i, i+1, "" + (char)((c - 'a' + n) % 26 + 'a'));
		}
		return message.toString();
	}

	/**
	 * This is method to decode a string with an n shift
	 * @param s the string to be decoded
	 * @param n the shift digit
	 * @return String the string decoded
	 */
	public String decode(String s, int n) {
		if (s == null) {
			return null;
		}
		StringBuilder message = new StringBuilder(s);
		for (int i = 0; i < message.length(); i++) {
			char c = message.charAt(i);
			if (c >= '0' && c <= '9') // digits
				message.replace(i,i+1, "" + calc(c, n, '0', '9'));
			else if (c >= 'A' && c <= 'Z') // upper case alphabets
				message.replace(i, i+1, "" + calc(c, n, 'A', 'Z'));
			else if (c >= 'a' && c <= 'z') // lower case alphabets
				message.replace(i, i+1, "" + calc(c, n, 'a', 'z'));
		}
		return message.toString();
	}
	
	/**
	 * This is method to decode a without knowing the shift (if possible)
	 * @param s the string to be decoded
	 * @return String the string decoded
	 */
	public String decode(String s) {
		if (s == null) {
			return null;
		}
		for (int i = 0; i < 26; i++) {
			String decodedMessage =  decode(s, i);
			String[] message = decodedMessage.split(" ");
			int limit = (int)((double)message.length * 0.9);
			int validCount = 0;
			for (int j = 0; j < message.length; j++) {
				if (this.words.contains(message[j])) {
					validCount += 1;
				}
			}
			if (validCount >= limit) {
				return decodedMessage;
			}
		}
		return null;
	}
}
