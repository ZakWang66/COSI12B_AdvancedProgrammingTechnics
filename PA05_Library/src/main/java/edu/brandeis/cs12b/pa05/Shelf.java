package edu.brandeis.cs12b.pa05;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.json.JSONObject;

public class Shelf extends Block{

	/**
	 * Shelf Class is the base case of the recursive Block Structures.
	 * To stop the recursion, childrenList is not used in this class. 
	 * Instead, A map called bookList is used to store Books.
	 * addBook method is also overridden.
	 */
	private Map<String, Book> bookList;
	private int capacity;
	private BookLocation shelfLocation;
	
	public Shelf(int capacity, int floorNum, int caseNum, int shelfNum) {
		name = "shelves";
		this.capacity = capacity;
		bookList = new TreeMap<String ,Book>();
		shelfLocation = new BookLocation(floorNum, caseNum, shelfNum);
	}
	
	public boolean isFull() {
		return bookList.size() == capacity ? true : false;
	}
	
	@Override
	public boolean addBook(Book b) {
		if (isFull()) 
			return false;
		else {
			bookList.put(b.getTitle(), b);
			b.setLocation(shelfLocation);
			b.setState(true);
			return true;
		}
	}
	
	public boolean checkOutBook(String bookName) {
		Book b = bookList.get(bookName);
		if (!hasBook(bookName) || !b.getState())
			return false;
		else {
			b.setState(false);
			return true;
		}
	}
	
	public void checkInBook(String bookName) {
		if (hasBook(bookName))
			bookList.get(bookName).setState(true);
	}
	
	public boolean hasBook(String bookName) {
		if (bookList.containsKey(bookName)) return true;
		else return false;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		jo.put("capacity", getCapacity());
		
		JSONObject books = new JSONObject();
		jo.put("books", books);
		
		int index = 0;
		for (Entry<String, Book> e : bookList.entrySet()) {
			books.put(Integer.toString(index), e.getValue().toJSON());
			index++;
		}
		return jo;
	}
	
	public Set<String> getBookNameList() {
		Set<String> nameList = new TreeSet<String>();
		for (Entry<String, Book> e : bookList.entrySet()) {
			if (e.getValue().getState()) {
				nameList.add(e.getKey());
			}
		}
		return nameList;
	}
	
}
