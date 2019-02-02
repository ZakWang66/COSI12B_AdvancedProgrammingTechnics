package edu.brandeis.cs12b.pa02;

import edu.brandeis.cs12b.pa02.provided.GameOf15;
import edu.brandeis.cs12b.pa02.provided.Point;
import java.util.*;

public class ComputerPlayer {

	public static void main(String[] args) {
		ComputerPlayer comp = new ComputerPlayer();
		GameOf15 game = new GameOf15();
		game = new GameOf15(new int[][] {{0,1,2,3},{6,7,11,4},{5,9,8,10},{13,14,15,12}});
		System.out.println(game);
		//System.out.println(comp.solveRelaxed(game));
		comp.solveReal(game);
		//System.out.println(comp.solveReal(game));

	}
	
	/**
	 * Here, you should write a method that will print a sequence of moves to solve the GameOf15. Finally,
	 * return the solved GameOf15.
	 * 
	 * However, you don't have to solve the entire game, you merely have to get the blank space
	 * into the right place.
	 * 
	 * In other words, if your board looks like this:
	 * 
	 * 1		13	6	2	
     * 5		8	10	4	
     * 9		7	3	11	
     * 14	15		12 
	 * 
	 * then you need to make only one move (left!!!), in order to get this board:
	 * 
	 * 1		13	6	2	
     * 5		8	10	4	
     * 9		7	3	11	
     * 14	15	12	
     * 
	 * You **must** not modify GameOf15.java for this part.
	 * 
	 * Print out each move on its own line using the same letters as before.
	 * u -- move up
	 * d -- move down
	 * l -- move left (that's an L, not an I)
	 * r -- move right
	 * 
	 * @return the solved game of 15 board
	 */
	
	public void moveBlankTo(GameOf15 game, Point destination) {
		Point blank = game.findBlank();
		int goRow = destination.row - blank.row;
		int goCol = destination.col - blank.col;
		if (goRow >= 0) {
			for (int i = 0; i < goRow; i++) {
				System.out.println("u");
				game.moveUp();
			}
		}
		else {
			for (int i = 0; i < -goRow; i++) {
				System.out.println("d");
				game.moveDown();
			}
		}
		if (goCol >= 0) {
			for (int i = 0; i < goCol; i++) {
				System.out.println("l");
				game.moveLeft();
			}
		}
		else {
			for (int i = 0; i < -goCol; i++) {
				System.out.println("r");
				game.moveRight();
			}
		}
	}
	
	public GameOf15 solveRelaxed(GameOf15 game) {
		//TODO implement me
		moveBlankTo(game,new Point(3,3));
		return game;
	}
	
	/**
	 * BONUS QUESTION
	 * 
     * Solving the entire board can be done for extra credit! 
     * We suggest you to try and solve the entire board with some help from Google, as some of
     * the algorithms can be pretty complex. One option (not the easiest) 
     * is to use the A* search algorithm.
     * 
     * This might help you get started: http://stackoverflow.com/questions/94975/how-do-you-solve-the-15-puzzle-with-a-star-or-dijkstras-algorithm
	 * 
	 * You **must** not modify GameOf15.java for this part.
	 * 
	 * Print out each move on its own line using the same letters as before.
	 * u -- move up
	 * d -- move down
	 * l -- move left (that's an L, not an I)
	 * r -- move right
	 * 
	 * @return the solved game of 15 board
	 */
	
	public GameOf15 duplicate(GameOf15 game) {
		int[][] board = new int[4][4];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				board[i][j] = game.getValue(i, j);
			}
		}
		return new GameOf15(board);
	}
	
	public void generateNextMove(GameOf15 current, LinkedList<GameOf15> nextLayer, Map<GameOf15,GameOf15> graph) {
		GameOf15 next = duplicate(current);
		graph.put(next, current);
		nextLayer.add(next);
	}
	
	public void generateNextLayer(GameOf15 current, HashSet<String>appeared, LinkedList<GameOf15> nextLayer, Map<GameOf15,GameOf15> graph) {
		if (current.moveUp()) {
			if (appeared.add(current.toString())) {
				generateNextMove(current, nextLayer, graph);
			}
			current.moveDown();
		}
		if (current.moveDown()) {
			if (appeared.add(current.toString())) {
				generateNextMove(current, nextLayer, graph);
			}
			current.moveUp();
		}
		if (current.moveLeft()) {
			if (appeared.add(current.toString())) {
				generateNextMove(current, nextLayer, graph);
			}
			current.moveRight();
		}
		if (current.moveRight()) {
			if (appeared.add(current.toString())) {
				generateNextMove(current, nextLayer, graph);
			}
			current.moveLeft();
		}
	}
	
	public void backTrack(GameOf15 result, Map<GameOf15,GameOf15> graph) {
		GameOf15 tracker = result;
		Stack<GameOf15> buffer = new Stack<GameOf15>();
		while (tracker != null) {
			buffer.push(tracker);
			tracker = graph.get(tracker);
		}
		System.out.println("The process is : \n");
		System.out.println(buffer.peek());
		GameOf15 tracker2;
		while (!buffer.isEmpty()) {
			tracker = buffer.pop();
			if (buffer.size() > 0) {
				tracker2 = buffer.peek();
				if (tracker2.findBlank().col - tracker.findBlank().col > 0) {
					System.out.println("l");
				}
				else if (tracker2.findBlank().col - tracker.findBlank().col < 0) {
					System.out.println("r");
				}
				else {
					if (tracker2.findBlank().row - tracker.findBlank().row > 0) {
						System.out.println("u");
					}
					else {
						System.out.println("d");
					}
				}
				System.out.println("\n" + tracker2);
			}
		}
	}
	
	public boolean solvable(GameOf15 game) {
		int N = 0;
		int X = 0;
		if ((game.findBlank().row % 2 == 1 && game.findBlank().col % 2 == 0) || 
			(game.findBlank().row % 2 == 0 && game.findBlank().col % 2 == 1)) {
			X = 1;
		}
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				for (int x = 0; x < 4; x++) {
					for (int y = 0; y < 4; y++) {
						if (x * 4 + y > i * 4 + j && 
							game.getValue(x, y) * game.getValue(i, j) != 0 && 
							game.getValue(x, y) < game.getValue(i, j) ) {
							N++;
						}
					}
				}
			}
		}
		N += 15 - (game.findBlank().row * 4 + game.findBlank().col);
		return (N + X) % 2 == 0;
	}

	public GameOf15 solveReal(GameOf15 game) {
		//implement me for an extra credit!!!
		//if you decided not to implement this part, don't touch the line below.
		//if you decided to implement this part, remove the next line and implement your solution.
		
		if (!solvable(game)) {
			System.out.println("Unable to solve");
			return game;
		}
		
		Map<GameOf15,GameOf15> graph = new HashMap<GameOf15,GameOf15>();
		HashSet<String> appeared = new HashSet<String>();
		LinkedList<GameOf15> currentLayer = new LinkedList<GameOf15>();
		LinkedList<GameOf15> nextLayer = new LinkedList<GameOf15>();
		currentLayer.add(duplicate(game));
		
		while (true) {
			for (GameOf15 current: currentLayer) {
				if (current.hasWon()) {
					backTrack(current, graph);
					return current;
				}
				else {
					generateNextLayer(current, appeared, nextLayer, graph);
				}
			}
			currentLayer = nextLayer;
			nextLayer = new LinkedList<GameOf15>();
		}
	}
}
