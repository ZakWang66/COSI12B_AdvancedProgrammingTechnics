package edu.brandeis.cs12b.pa3;

class MultiCaesarCipher{
	private static String multiProcessString(boolean operation, String s, int[] shifts) {
		if (s == null) {
			return null;
		}
		int csi = 0;
		int cs = shifts[0];
		StringBuilder message = new StringBuilder(s);
		for (int i = 0; i < message.length(); i++) {
			CaesarCipher.processChar(operation, message, i, cs);
			csi = csi < shifts.length - 1 ? csi + 1 : 0;
			cs = shifts[csi];
		}
		return message.toString();
	}
	/**
	 * This is method to encode a string with multiple shift digits
	 * @param s the string to be encoded
	 * @param shifts the set of multiple shift digits
	 * @return String the string encoded
	 */
	public static String multiEncode(String s, int[] shifts){
		return multiProcessString(true, s, shifts);
	}
	
	/**
	 * This is method to decode a string with multiple shift digits
	 * @param s the string to be decoded
	 * @param shifts the set of multiple shift digits
	 * @return String the string decoded
	 */
	public static String multiDecode(String s, int[] shifts){
		return multiProcessString(false, s, shifts);	
	}
}