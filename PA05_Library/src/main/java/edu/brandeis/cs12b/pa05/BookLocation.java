package edu.brandeis.cs12b.pa05;

public class BookLocation {
	private int floor;
	private int bookcase;
	private int shelf;
	
	public BookLocation(int floor, int bookcase, int shelf) {
		this.floor = floor;
		this.bookcase = bookcase;
		this.shelf = shelf;
	}
	
	public BookLocation() {
		
	}
	
	public int getFloor() {
		return floor;
	}
	
	public int getCase() {
		return bookcase;
	}
	
	public int getShelf() {
		return shelf;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}


	public void setCase(int bookcase) {
		this.bookcase = bookcase;
	}
	
	
	public void setShelf(int shelf) {
		this.shelf = shelf;
	}
	
}
