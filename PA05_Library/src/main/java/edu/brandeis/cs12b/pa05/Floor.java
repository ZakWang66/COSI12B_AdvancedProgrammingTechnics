package edu.brandeis.cs12b.pa05;

import java.util.ArrayList;

public class Floor extends Block {
	
	public Floor(int casesPerFloor, int[] shelvesPerCase, int[][] shelfCapacity, int floorNum) {
		name = "floors";
		ArrayList<Case> childrenList = getChildrenList();
		for (int i = 0; i < casesPerFloor; i++) {
			childrenList.add(new Case(shelvesPerCase[i], shelfCapacity[i], floorNum, i));
		}
	}
	
}
