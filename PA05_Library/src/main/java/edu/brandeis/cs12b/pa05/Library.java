package edu.brandeis.cs12b.pa05;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.json.JSONObject;

public class Library extends Block{
	
	private Map<String, BookLocation> bookLocationList;
	
	/**
	 * Construct a new library object based on the given parameters
	 * @param floors the number of floors
	 * @param casesPerFloor the number of cases per floor
	 * @param shelvesPerCase the number of shelves per case on each floor
	 * @param shelfCapacity the capacity of each shelf
	 * @return a library object
	 */
	public Library(int floors, int[] casesPerFloor, int[][] shelvesPerCase, int[][][] shelfCapacity) {
		name = "library";
		bookLocationList = new HashMap<String, BookLocation>();
		ArrayList<Floor> childrenList = getChildrenList();
		for (int i = 0; i < floors; i++) {
			childrenList.add(new Floor(casesPerFloor[i], shelvesPerCase[i], shelfCapacity[i], i));
		}
	}
	
	/**
	 * Find a block which is a child or a sub-child of the library at a specific location...
	 * @param loc
	 * @param level
	 * @return the block found, null for no such block
	 */
	private Block findBlock(BookLocation loc, int level) {
		Block b = this;
		int[] array = new int[] {loc.getFloor(), loc.getCase(), loc.getShelf()};
		for (int i = 0; i < level; i++) {
			b = b.findChildren(array[i]);
			if (b == null) {
				return null;
			}
		}
		return b;
	}
	
	/**
	 * Returns the number of floors this library has
	 * @return the number of floors
	 */
	public int getNumberOfFloors() {
		return getChildrenList().size();
	}
	
	/**
	 * Returns the number of cases on the nth floor (floors start at 0)
	 * @param floor 
	 * @return the number of cases
	 */
	public int getCasesOnFloor(int floor) {
		BookLocation loc = new BookLocation(floor, -1, -1);
		return findBlock(loc, 1).getChildrenList().size();
	}
	
	/**
	 * Returns the number of shelves in a given case on a given floor
	 * @param floor
	 * @param bookcase
	 * @return the number of shelves
	 */
	public int getShelvesInCase(int floor, int bookcase) {
		BookLocation loc = new BookLocation(floor, bookcase, -1);
		return findBlock(loc, 2).getChildrenList().size();
	}
	
	/**
	 * Returns the capacity of the nth shelf at the particular floor and case
	 * @param floor
	 * @param bookcase
	 * @param shelf
	 * @return the shelf capacity
	 */
	public int getCapacityOfShelf(int floor, int bookcase, int shelf) {
		BookLocation loc = new BookLocation(floor, bookcase, shelf);
		Shelf s = (Shelf)findBlock(loc, 3);
		return s.getCapacity();
	}

	
	/**
	 * Adds a new book and returns its location. If there is no space for the new book,
	 * return null.
	 * @param title the title of the book to add
	 * @return the location of the book or null
	 */
	public BookLocation addNewBook(String title) {
		Book b = new Book(title);
		if (addBook(b)) {
			bookLocationList.put(title, b.getLocation());
			return b.getLocation();
		}
		else return null;
	}
	
	/**
	 * Return the location of the book with the given title, or null if
	 * it is not in the library
	 * @param title the title of the book to lookup
	 * @return the book's location
	 */
	public BookLocation getLocationOfBook(String title) {
		return bookLocationList.get(title);
	}
	
	/**
	 * Return the bookshelf at the input location, null if no such bookshelf
	 * @param loc location
	 * @return the bookshelf
	 */
	private Shelf findBookShelf(BookLocation loc) {
		return (Shelf)findBlock(loc, 3);
	}
	
	/**
	 * Return the set of all books at the given location, or null if the location is invalid
	 * @param loc the location to list the books at (only checked in books should be listed)
	 * @return the set of books, or null
	 */
	public Set<String> getBooksAt(BookLocation loc) {
		Shelf s = findBookShelf(loc);
		if (s == null)
			return null;
		else
			return s.getBookNameList();
	}
	
	/**
	 * Checks out a book
	 * @param title the book to check out
	 * @return true if the book can be checked out
	 */
	public boolean checkOut(String title) {
		BookLocation l = bookLocationList.get(title);
		if (l == null) return false;
		Shelf shelf = findBookShelf(l);
		return shelf.checkOutBook(title);
	}
	
	/**
	 * Checks a book back in
	 * @param title the book
	 */
	public void checkIn(String title) {
		BookLocation l = bookLocationList.get(title);
		if (l == null) return;
		Shelf shelf = findBookShelf(l);
		shelf.checkInBook(title);
	}
	
	/**
	 * Write the Blocks information to JSON recursively
	 * @param blockObject the original blockObject
	 * @param thisBlock the current Block into JSONObject
	 * @return the current Block into JSONObject
	 */
	private JSONObject recursiveJoWriteBlock(Block blockObject, JSONObject thisBlock) {
		ArrayList<Block> children = blockObject.getChildrenList();
		// base case
		if (blockObject.name.equals("shelves")) {
			Shelf s = (Shelf)blockObject;
			return s.toJSON();
		}
		else {
			JSONObject joChildrenlist = new JSONObject();
			if (children.size() == 0) {
				return thisBlock;
			}
			String childerenName = children.get(0).name;
			thisBlock.put("numOf" + childerenName.substring(0,1).toUpperCase() + childerenName.substring(1), children.size());		
			thisBlock.put(childerenName, joChildrenlist);
			for (int i = 0; i < children.size(); i++) {
				joChildrenlist.put(Integer.toString(i), recursiveJoWriteBlock(children.get(i), new JSONObject()));
			}
		}
		return thisBlock;
	}
	
	/**
	 * Convert the raw JSON String to readable formation, with newlines and indentations 
	 * @param raw the input JSON String
	 * @return convert result
	 */
	private String formatControl(String raw) {
		String indent = "";
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < raw.length(); i++) {
			char c = raw.charAt(i);
			
			// new line
			if (c == '}') indent = indent.substring(0, indent.length() - 2);
			if (c == '}') result.append('\n' + indent);
			
			result.append(c);
			if (c == ':') result.append(" ");
			
			// new line
			if (c == '{') indent += "  ";
			if (c == ',' || c == '{') result.append('\n' + indent); // || (c == '}' && i < raw.length() - 1 && raw.charAt(i + 1) != ',')
		}
		return result.toString();
	}
	
	/**
	 * Writes the contents of the library to the passed file
	 * in a format compatible with the LibraryFactory's makeLibraryFromFile method.
	 * @param f the file to write the library to
	 */
	public void writeToFile(File f) {
		try {
			JSONObject joLibrary = recursiveJoWriteBlock(this, new JSONObject());
			PrintWriter writer = new PrintWriter(f);
			writer.write(formatControl(joLibrary.toString()));			
			writer.close();			
		} catch (IOException ex) {ex.printStackTrace();}
	}

	/**
	 * Base case of recursiveReadFromJSON
	 * @param children
	 * @param sizes
	 * @param recoveryBookList
	 */
	private static void baseCaseofRead(JSONObject children, ArrayList sizes, Set<Book> recoveryBookList) {
		ArrayList result = new ArrayList();
		// Build boxes presenting the capacity;
		int size = children.getInt("capacity");
		for (int i = 0; i < size; i++) {
			result.add(null);
		}
		sizes.add(result);
		JSONObject bookList = children.getJSONObject("books");
		for (String cKey : bookList.keySet()) {
			recoveryBookList.add(new Book((JSONObject)bookList.get(cKey)));
		}
	}
	
	/**
	 * Recursive case of recursiveReadFromJSON
	 * @param children
	 * @param sizes
	 * @param recoveryBookList
	 */
	private static void recursiveCaseofRead(JSONObject children, ArrayList sizes, Set<Book> recoveryBookList) {
		int size = 0;
		JSONObject childrenBlock = null;
		String childrenName = "";
		for (String cKey : children.keySet()) {
			if (cKey.startsWith("numOf")) {
				size = children.getInt(cKey);
			}
			else {
				childrenName = cKey;
				childrenBlock = children.getJSONObject(cKey);
			}
		}
		if (size != 0) {
			sizes.add(recursiveReadFromJSON(recoveryBookList, childrenName, childrenBlock));
		}
	}
	
	/**
	 * Read the JSON file recursively
	 * @param recoveryBookList A HashSet stores the books recovered from file
	 * @param blockName the name(type) of the block
	 * @param joBlock current block in the type of JSONObject
	 * @return An ArrayList that can generate the arrays to rebuild the library
	 */
	private static ArrayList recursiveReadFromJSON(Set<Book> recoveryBookList, String blockName, JSONObject joBlock) {
		ArrayList sizes = new ArrayList();
		for (String key : joBlock.keySet()) {
			JSONObject children = joBlock.getJSONObject(key);
			// base case
			if (blockName.equals("shelves")) {
				baseCaseofRead(children, sizes, recoveryBookList);
			}
			else {
				recursiveCaseofRead(children, sizes, recoveryBookList);
			}
		}
		return sizes;
	}
	
	/**
	 * Convert the ArrayList array to three arrays that can be used to build the library
	 * @param array
	 * @param casesPerFloornew
	 * @param shelvesPerCase
	 * @param shelfCapacity
	 */
	private static void buildArrays(ArrayList array, int[] casesPerFloornew, int[][] shelvesPerCase, int[][][] shelfCapacity) {
		for (int i = 0; i < array.size(); i++) {
			ArrayList cases = (ArrayList)array.get(i);
			casesPerFloornew[i] = cases.size();
			shelvesPerCase[i] = new int[cases.size()];
			shelfCapacity[i] = new int[cases.size()][];
			for (int j = 0; j < cases.size(); j++) {
				ArrayList shelves = (ArrayList)cases.get(j);
				shelvesPerCase[i][j] = shelves.size();
				shelfCapacity[i][j] = new int[shelves.size()];
				for (int k = 0; k < shelves.size(); k++) {
					ArrayList shelfCapacities = (ArrayList)shelves.get(k);
					shelfCapacity[i][j][k] = shelfCapacities.size();
				}
			}
		}
	}
	
	/**
	 * Makes a new library from the passed file. The file will have been produced by your
	 * Library's writeToFile method.
	 * 
	 * @param f the file to read from
	 * @return the reconstructed library
	 */
	public static Library makeLibraryFromFile(File f) {
		try {
			// Read JSON file into JSONObject
			Scanner s = new Scanner(f);
			StringBuilder content = new StringBuilder();
			while (s.hasNextLine()) {
				content.append(s.nextLine());
			}
			JSONObject joLibrary = new JSONObject(content.toString());
			
			// Recursively read the Blocks and Books in the JSON Object
			Set<Book> recoveryBookList = new HashSet<Book>();
			int numOfFloors = joLibrary.getInt("numOfFloors");
			JSONObject floors = joLibrary.getJSONObject("floors");
			ArrayList array = recursiveReadFromJSON(recoveryBookList, "floors", floors);
			
			// Build initializer arrays from the arrayList
			int[] casesPerFloornew = new int[array.size()];
			int[][] shelvesPerCase = new int[array.size()][];
			int[][][] shelfCapacity = new int[array.size()][][];
			buildArrays(array, casesPerFloornew, shelvesPerCase, shelfCapacity);
			
			// Rebuild library
			Library l = new Library(numOfFloors, casesPerFloornew, shelvesPerCase, shelfCapacity);
			l.bookLocationList = new HashMap<String, BookLocation>();
			for (Book b : recoveryBookList) {
				Shelf shelf = l.findBookShelf(b.getLocation());
				shelf.addBook(b);
				l.bookLocationList.put(b.getTitle(), b.getLocation());
			}
			return l;
		} catch (IOException ex) {ex.printStackTrace();return null;}
		
	}
	
}
