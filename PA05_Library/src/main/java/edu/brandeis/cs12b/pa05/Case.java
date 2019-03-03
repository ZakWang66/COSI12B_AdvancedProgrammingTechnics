package edu.brandeis.cs12b.pa05;

import java.util.ArrayList;

public class Case extends Block {
	
	public Case(int shelvesPerCase, int[] shelfCapacity, int floorNum, int caseNum) {
		name = "bookcases";
		ArrayList<Shelf> childrenList = getChildrenList();
		for (int i = 0; i < shelvesPerCase; i++) {
			childrenList.add(new Shelf(shelfCapacity[i], floorNum, caseNum, i));
		}
	}
	
}
