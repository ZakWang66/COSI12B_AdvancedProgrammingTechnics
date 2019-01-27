package edu.brandeis.cs12b.pa01;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EclipseIntroTest {	
	@Test
	public void countRepeatsTest() {
		assertEquals(0, EclipseIntroProblems.countRepeats(new int[] {1, 2, 3}));
		assertEquals(1, EclipseIntroProblems.countRepeats(new int[] {1, 3, 2, 2}));
		assertEquals(2, EclipseIntroProblems.countRepeats(new int[] {1, 4, 2, 6, 4, 2}));
	}
	
	@Test
	public void sum3Test() {
		assertTrue(EclipseIntroProblems.sum3(new int[] {5, 2, 4, -7}));
		assertFalse(EclipseIntroProblems.sum3(new int[] {5, 2, 4, 7}));
	}
	
	@Test
	public void isBalancedParensTest() {
		assertTrue(EclipseIntroProblems.isBalancedParens("()(())"));
		assertFalse(EclipseIntroProblems.isBalancedParens("(()"));
	}
	
	@Test
	public void isBalancedBracketsTest() {
		assertTrue(EclipseIntroProblems.isBalancedBrackets("(<>)"));
		assertTrue(EclipseIntroProblems.isBalancedBrackets("{}[]<>()"));
		assertFalse(EclipseIntroProblems.isBalancedBrackets("<()"));
	}

//	EXTRA CREDIT
//	Uncomment the "@Test" to run this and test your extra credit problem!
	
	@Test 
	public void extraCreditSpiralTest() {
		int[][] arr1 = {{1, 2, 3}, {8, 9, 4}, {7, 6, 5}};
		int[][] arr2 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
		int[][] arr3 = {{4, 3},{1, 2}};
		assertTrue(EclipseIntroProblems.extraCreditSpiral(arr1));
		assertFalse(EclipseIntroProblems.extraCreditSpiral(arr2));
		assertTrue(EclipseIntroProblems.extraCreditSpiral(arr3));		
	}
}
