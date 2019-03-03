package edu.brandeis.cs12b.pa05;

import org.json.JSONObject;

public class Book implements Comparable<Book> {
	
	private BookLocation location;
	private String title;
	private boolean state; // true for checked-in, false for checked-out
	
	public Book(String title, BookLocation location, boolean state) {
		this.title = title;
		this.location = location;
		this.state= state;
	}
	
	public Book(String title) {
		this.title = title;
	}
	
	public Book(JSONObject info) {
		this.title = info.getString("title");
		JSONObject loc = info.getJSONObject("location");
		this.location = new BookLocation(loc.getInt("floor"), loc.getInt("bookcase"), loc.getInt("shelf"));
		this.state = info.getBoolean("checkedIn");
	}
	
	public String getTitle() {
		return title;
	}
	
	public boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
	
	public void setLocation(BookLocation location) {
		this.location = location;
	}
	
	public BookLocation getLocation() {
		return location;
	}
	
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		JSONObject loc = new JSONObject();
		loc.put("floor", location.getFloor());
		loc.put("bookcase", location.getCase());
		loc.put("shelf", location.getShelf());
		jo.put("location", loc);
		jo.put("checkedIn", getState());
		jo.put("title", getTitle());
		return jo;
	}

	@Override
	public int compareTo(Book b) {
		return this.getTitle().compareTo(b.getTitle());
	}
	
}
