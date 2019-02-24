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
		scan.close();
	}
	
	/**
	 * Calculate the result of encoding or decoding a character
	 * @param operation true for encoding, false for decoding
	 * @param c the character to calculate
	 * @param n the shift value
	 * @param low the lower boundary for the character's type e.g. '0' for digits
	 * @param high the higher boundary for the character's type e.g. '9' for digits
	 * @return the result of calculation
	 */
	private static char calc(boolean operation, char c, int n, char low, char high) {
		int diff = (int)c - (int)low;
		diff = operation ? diff + n : diff - n;
		int length = high - low + 1;
		int shift = diff % length;
		char result = shift >= 0 ? (char)(low + shift) : (char)(high + 1 + shift);
		return result;
	}
	
	/**
	 * Process a char in a StringBuilder by identify its type (upper case alphabet, lower case alphabet or digit),
	 * the result will replace the original value in the StringBuilder
	 * @param operation true for encoding, false for decoding
	 * @param message the StringBuilder to modify
	 * @param i the index of the target char
	 * @param n the shift value
	 */
	public static void processChar(boolean operation, StringBuilder message, int i, int n) {
		char c = message.charAt(i);
		if (c >= '0' && c <= '9') // digits
			message.replace(i,i+1, "" + calc(operation, c, n, '0', '9'));
		else if (c >= 'A' && c <= 'Z') // upper case alphabets
			message.replace(i, i+1, "" + calc(operation, c, n, 'A', 'Z'));
		else if (c >= 'a' && c <= 'z') // lower case alphabets
			message.replace(i, i+1, "" + calc(operation, c, n, 'a', 'z'));
	}
	
	/**
	 * Do Caesar-cipher processing for a String
	 * @param operation true for encoding, false for decoding
	 * @param s the String to process
	 * @param n the shift value
	 * @return the processed String
	 */
	private static String processString(boolean operation, String s, int n) {
		if (s == null) {
			return null;
		}
		StringBuilder message = new StringBuilder(s);
		for (int i = 0; i < message.length(); i++) {
			processChar(operation, message, i, n);
		}
		return message.toString();
	}
	
	/**
	 * This is method to encode a string with an n shift
	 * @param s the string to be encoded
	 * @param n the shift digit
	 * @return String the string encoded
	 */
	public String encode(String s, int n) {
		return processString(true, s, n);
	}

	/**
	 * This is method to decode a string with an n shift
	 * @param s the string to be decoded
	 * @param n the shift digit
	 * @return String the string decoded
	 */
	public String decode(String s, int n) {
		return processString(false, s, n);
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
		s = s.toLowerCase();
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
