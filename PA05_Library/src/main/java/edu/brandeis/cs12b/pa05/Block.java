package edu.brandeis.cs12b.pa05;

import java.util.ArrayList;

/**
 * A recursive structure that can represent Library, Floor, Case and Shelf.
 * Using this structure allow programmer to easily add more layers.
 * @author Zekai Wang
 *
 * @param <T> The type of children Blocks
 */
public abstract class Block <T extends Block<? super T>> {
	private ArrayList<T> childrenList;
	public String name;
	
	public Block() {
		childrenList = new ArrayList<T>();
	}
	
	public boolean addBook(Book b) {
		for (T c : childrenList) {
			if (c.addBook(b)) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<T> getChildrenList() {
		return childrenList;
	}
	
	public Block findChildren(int index) {
		if (index < getChildrenList().size() && index != -1) {
			return getChildrenList().get(index);
		}
		else {
			return null;
		}
	}

}
