package edu.brandeis.cs12b.pa01;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

public class EclipseIntroProblems {
	/**
	 * Returns the number of items that have duplicates in an array. For example,
	 * in the array {3, 1, 5, 5, 3}, countRepeats should return 2, because there are
	 * two numbers that are repeated -- three and five. 
	 * 
	 * @param items - an array of integers
	 * @return the number of integers in items array that are repeated more than once.
	 */
	public static int countRepeats(int[] items) {
		// TODO implement me
		HashSet<Integer> shownItems = new HashSet<Integer>();
		HashSet<Integer> repeatItems = new HashSet<Integer>();
		for (int i = 0; i < items.length; i++) {
			if(shownItems.contains(items[i])) {
				repeatItems.add(items[i]);
			}
			else {
				shownItems.add(items[i]);
			}
		}
		return repeatItems.size();
	}
	
	
	/**
	 * Return true if there are three integers in the items array that sum up to
	 * zero. Return false otherwise.
	 * 
	 * Examples: 
	 * {5, 4, 2, -7} -> TRUE, because (5 + 2 + -7 = 0) 
	 * {3, 4, 1, 52} -> FALSE, because there are no 3 integers in the array that sum up to 0.
	 * 
	 * @param items - an array of integers
	 * @return true - if there exist three integers in items array that add up to zero, 
	 *         otherwise, return false.
	 */
	public static boolean sum3(int[] items) {
		// TODO implement me!
		Arrays.sort(items);
		for (int i = 0; i < items.length; i++) {
			// Two pointers
			int low = i + 1;
			int top = items.length - 1;
			while (top > low) {
				if (items[low] + items[top] > -items[i]) {
					top--;
				}
				else if (items[low] + items[top] < -items[i]) {
					low++;
				}
				else {
					return true;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * Returns true if a string of parenthesis is balanced.
	 * 
	 * Examples: "()"  -> TRUE (balanced)
	 *           "(()" -> FALSE (unmatched "(" )
	 *           
   	 * A string with any character beside '(' or ')' is invalid, and therefore not balanced.
	 *           
	 * @param str - an input string
	 * @return true - if str is a balanced string, 
	 *         otherwise, return false.
	 */
	public static boolean isBalancedParens(String str) {
		// TODO implement me!
		int balancer = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '(') {
				balancer++;
			}
			else if(str.charAt(i) == ')') {
				balancer--;
			}
			// Invalid input
			else {
				return false;
			}
			// If a ')' appear before '(' in a pair, then it is not valid
			if (balancer < 0) {
				return false;
			}
		}
		// If balanced or not
		if (balancer == 0) {
			return true;
		}
		else {
			return false;
		}
	}


	/**
	 * Similar to isBalancedParens, but this time we consider 4 types of brackets:
	 * ( ) , { } , [ ] and < >.
	 * 
	 * A string with any character beside the brackets is invalid, and therefore not balanced.
	 * 
	 * @param str - an input string
	 * @return true - if str is a balanced string, 
	 *         otherwise, return false.
	 */
	public static boolean isBalancedBrackets(String str) {
		// TODO implement me!
		if (str.isEmpty()) {
			return false;
		}
		Stack<Character> stack = new Stack<Character>();
		
		for (int i = 0; i < str.length(); i++) {
			switch (str.charAt(i)) {
			case '(': 
			case '[':
			case '{':
			case '<':
				stack.push(str.charAt(i)); break;
			case ')':
				if (stack.empty() || stack.pop() != '('){
					return false;
				}
				break;
			case ']':
				if (stack.empty() || stack.pop() != '['){
					return false;
				}
				break;
			case '}':
				if (stack.empty() || stack.pop() != '{'){
					return false;
				}
				break;
			case '>':
				if (stack.empty() || stack.pop() != '<'){
					return false;
				}
				break;
			default:
				return false;
			}
		}
		if (stack.empty()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * EXTRA CREDIT: 10 points
	 * 
	 * Returns true if the spiral form of a two-dimensional array is 
	 * either strictly increasing or strictly decreasing.
	 * 
	 * Examples:
	 * Original array: {{1, 2, 3}, {8, 9, 4}, {7, 6, 5}}
	 * Spiral form: {1, 2, 3, 4, 5, 6, 7, 8, 9} -> TRUE (strictly increasing)
	 * 
	 * Original array: {{7, 4}, {2, 3}}
	 * Spiral form: {7, 4, 3, 2} -> TRUE (strictly decreasing) 
	 * 
	 * Original array: {{11, 9}, {2, 9}}
	 * Spiral form: {11, 9, 9, 2} -> FALSE (neither strictly increasing nor strictly decreasing) 
	 * 
	 * You may assume that the traversal of the spiral always begins at the 
	 * "top left" (int[0][0]) and initially proceeds to the right (int[0][1]).
	 * 
	 * @param arr - a two-dimensional array
	 * @return true if the spiral form of the array is either strictly increasing or strictly decreasing.
	 */
	public static boolean extraCreditSpiral(int[][] arr) {
		// TODO implement me!
		boolean isInc = false; // whether it is an increasing spiral or not
		if (arr[0][1] - arr[0][0] > 0) {
			isInc = true;
		}
		int lastVal = arr[0][0];
		int x = 0;
		int y = 0;
		int rightBound = arr[0].length - 1;
		int downBound = arr.length - 1;
		int leftBound = 0;
		int upBound = 0;
		int direction = 0; // Which direction is the next step going: 0-right 1-down 2-left 3-up
		for (int i = 0; i < arr.length * arr[0].length - 1; i++) {
			// This records the value at the coordinate BEFORE the moving operation
			lastVal = arr[y][x];
			// Do the movement
			switch (direction) {
			// Going right
			case 0:
				// Not getting to the right boundary yet, keep moving right
				if(x < rightBound) {
					x++;
				}
				// When reach the right boundary, start going down
				// Since a row counting start from the top has been gone through, the up boundary shrinks by 1
				else {
					y++;
					upBound++;
					direction = 1;
				}
				break;
			// Going down
			case 1:
				if(y < downBound) {
					y++;
				}
				else {
					x--;
					rightBound--;
					direction = 2;
				}
				break;
			// Going left
			case 2:
				if(x > leftBound) {
					x--;
				}
				else {
					y--;
					downBound--;
					direction = 3;
				}
				break;
			// Going up
			case 3:
				if(y > upBound) {
					y--;
				}
				else {
					x++;
					leftBound++;
					direction = 0;
				}
				break;
			}
			if (!(
					isInc && arr[y][x] > lastVal || 
					!isInc && arr[y][x] < lastVal
				)) {
				return false; // if neither strictly increasing nor decreasing, then return false
			}
		}		
		return true;
	}	
}

