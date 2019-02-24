package edu.brandeis.cs12b.pa3;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

//Expected outputs generated at https://planetcalc.com/1434/

public class CaesarCipherTest {
	
	private CaesarCipher cipher;
	
	@Before
	public void setup() throws FileNotFoundException {
		cipher = new CaesarCipher();
	}
	
	@Test //Encode
	public void testEncode() throws FileNotFoundException {
		String original = "PA3 is the best!";
		String encoded = "QB4 jt uif cftu!";
		
		String output = cipher.encode(original, 1);
		assertEquals(encoded, output);
		
		original = "h K&8";
		encoded = "l O&8";
		output = cipher.encode(original, 30);
		assertEquals(encoded, output);
		
		original = " 1O/z";
		encoded = " 6F/q";
		output = cipher.encode(original, -35);
		assertEquals(encoded, output);
		
		original = "";
		encoded = "";
		output = cipher.encode(original, -35);
		assertEquals(encoded, output);
		
		original = null;
		output = cipher.encode(original, -35);
		assertEquals(null, output);
		
		original = "PA3 is the best!";
		encoded = "PA1 is the best!";
		output = cipher.encode(original, -52);
		assertEquals(encoded, output);
		
	}
	
	@Test //Decode
	public void testDecode() throws FileNotFoundException {
		String original = "PA3 is the best!";
		String encoded = "QB4 jt uif cftu!";
		
		String output = cipher.decode(encoded, 1);
		assertEquals(original, output);
		
		original = "h K&8";
		encoded = "l O&8";
		output = cipher.decode(encoded, 30);
		assertEquals(original, output);
		
		original = " 1O/z";
		encoded = " 6F/q";
		output = cipher.decode(encoded, -35);
		assertEquals(original, output);
	}
	
	@Test //Encode and then decode
	public void testEncodeAndDecode() throws FileNotFoundException {
		String original = "PA3 is the best!";
		String encoded = cipher.encode(original, 1);

		String output = cipher.decode(encoded, 1);
		assertEquals(original, output);
		
		original = "h K&8";
		encoded = cipher.encode(original, 30);
		output = cipher.decode(encoded, 30);
		assertEquals(original, output);
		
		original = " 1O/z";
		encoded = cipher.encode(original, -35);
		output = cipher.decode(encoded, -35);
		assertEquals(original, output);
	}
	
	@Test //Decode without n - every word here is in the dictionary
	public void testDecodeNoShift() throws FileNotFoundException {
		String original = "according to all known laws of aviation there is no way a bee should be able to fly its wings are too small to get its fat little body off the ground the bee of course can fly anyway because the bee does not care what humans think is impossible";
		String encoded = "hjjvykpun av hss ruvdu shdz vm hcphapvu aolyl pz uv dhf h ill zovbsk il hisl av msf paz dpunz hyl avv zthss av nla paz mha spaasl ivkf vmm aol nyvbuk aol ill vm jvbyzl jhu msf hufdhf iljhbzl aol ill kvlz uva jhyl doha obthuz aopur pz ptwvzzpisl"; //shift = 7
				
		String output = cipher.decode(encoded);
		assertEquals(original, output);
		
		original = "according to all known laws of aviation there is no way a bee should be able to fly its wings are too small to get its fat little body off the ground the bee of course can fly anyway because the bee does not care what humans think is impossible";
		encoded = "hjjvYkpun av hss ruVdu Shdz vm hcphApvu Aolyl pz uv dhf h Ill zovBsk il hisl av msf Paz dpunz hyl avv zThss av nla paz mha spaasl ivkf vmm aol nyvbuk aol ill vm jvbyzl jhu mSf hufdhf Iljhbzl aol ill kvlz Uva jhyl dOha obthuz aoPur pz ptwvzzpisl"; //shift = 7
			
		output = cipher.decode(encoded);
		assertEquals(original, output);	
		
	}
	
	@Test //Negative shifts
	public void testNegativeShifts() throws FileNotFoundException {
		String original = "PA3 is the best!";
		String encoded = "OZ2 hr sgd adrs!";
		assertEquals(encoded, cipher.encode(original, -1));
		assertEquals(original, cipher.decode(encoded, -1));
	}
	
	 @Test
	 public void multiEncodeTest() {
		 assertEquals("b b b b", MultiCaesarCipher.multiEncode("a a a a", new int[] {1, 2}));
		 assertEquals("WL0 pd eop mlda!", MultiCaesarCipher.multiEncode("PA3 is the best!", new int[] {7, 11}));
		 assertEquals("WL8 th ewl qldi!", MultiCaesarCipher.multiEncode("PA3 is the best!", new int[] {7, 11, 15}));
	 }
	
	 @Test
	 public void multiDecodeTest() {
		 assertEquals("a a a a", MultiCaesarCipher.multiDecode("b b b b", new int[] {1, 2}));
		 assertEquals("PA3 is the best!", MultiCaesarCipher.multiDecode("WL0 pd eop mlda!", new int[] {7, 11}));
		 assertEquals("PA3 is the best!", MultiCaesarCipher.multiDecode("WL8 th ewl qldi!", new int[] {7, 11, 15}));
	 }
	 
}
