package edu.brandeis.cs12b.pa02;

import edu.brandeis.cs12b.pa02.provided.GameOf15;
import edu.brandeis.cs12b.pa02.provided.Point;
import java.util.*;

public class ComputerPlayer {
	
	public int[][][] manhattanList = new int[4][4][16];

	public static void main(String[] args) {
		ComputerPlayer comp = new ComputerPlayer();
		GameOf15 game = new GameOf15();
//		game = new GameOf15( new int[][] 
//				{{6, 1, 3, 4},
//				{2, 11, 8, 12},
//				{5, 9, 7, 14},
//				{0, 13, 15, 10}});
		System.out.println(game);
		//System.out.println(comp.solveRelaxed(game));
		System.out.println(comp.solveReal(game));
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
	
	public GameOf15 solveRelaxed(GameOf15 game) {
		//TODO implement me
		while (game.findBlank().row != 3) {
			System.out.println("u");
			game.moveUp();
		}
		while (game.findBlank().col != 3) {
			System.out.println("l");
			game.moveLeft();
		}
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
	
	private void clacManhattan() {
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				manhattanList[row][col][0] = Math.abs(row - 3) + Math.abs(col - 3);
			}
		}
		for (int i = 1; i < 16; i++) {
			for (int row = 0; row < 4; row++) {
				for (int col = 0; col < 4; col++) {
					manhattanList[row][col][i] = Math.abs(row - (i-1) / 4) + Math.abs(col - (i-1) % 4);
				}
			}
		}
	}
	
	private long getKey(Game game) {
		long key = 0;
		for (int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				key = key*16 + game.getValue(i, j);
			}
		}
		return key;
	}
	
	private Game duplicate(Game game) {
		int[][] board = new int[4][4];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				board[i][j] = game.getValue(i, j);
			}
		}
		return new Game(board);
	}
	
	public void backTrack(Game result, Map<Game,Game> graph) {
		Game tracker = result;
		Stack<Game> buffer = new Stack<Game>();
		while (tracker != null) {
			buffer.push(tracker);
			tracker = graph.get(tracker);
		}
		//System.out.println("The process is : \n");
		//System.out.println(buffer.peek());
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
				//System.out.println("\n" + tracker2);
			}
		}
	}
	
	private boolean solvable(GameOf15 game) {
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

	public class Game extends GameOf15 implements Comparable<Game>{
		public int manhattan = 0;
		public char lastMove = ' ';
		public String track = "";
		public int depth = 0;
		public int compareTo(Game anotherGame) {
			return anotherGame.manhattan - this.manhattan;
		}
		
		public Game(int[][] board) {
			super(board);
			manhattan = 0;
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					manhattan += manhattanList[i][j][board[i][j]];
				}
			}			
		}
	}
	
	public GameOf15 solveReal(GameOf15 game) {
		//implement me for an extra credit!!!
		//if you decided not to implement this part, don't touch the line below.
		//if you decided to implement this part, remove the next line and implement your solution.
		
//		if (!solvable(game)) {
//			System.out.println("Unable to solve");
//			return game;
//		}
		
		Map<Game,Game> graph = new HashMap<Game,Game>();
		
		Stack<Game> open = new Stack<Game>();
		//Set<Long> close = new HashSet<Long>();
		PriorityQueue<Game> temp = new PriorityQueue<Game>();
		clacManhattan();
		
		int[][] board = new int[4][4];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				board[i][j] = game.getValue(i, j);
			}
		}
		open.push(duplicate(new Game(board)));
		int maxDepth = open.peek().manhattan;
		while (true) {
			int minDepth = 1000;
			while (!open.isEmpty()) {
				Game current = open.pop();
				//close.add(getKey(current));
				if (current.hasWon()) {
					backTrack(current, graph);
					//System.out.println(current.track);
					return current;
				}
				else {
					if (current.depth < maxDepth) {
						if (current.lastMove != 'd' && current.moveUp()/* && !close.contains(getKey(current))*/) {
							Game next = duplicate(current);
		//					close.add(getKey(next));
							graph.put(next, current);
		//					next.track = new String(current.track);
							temp.add(next);
		//					next.track += "" + 'u' + '\n';
							next.lastMove = 'u';
							next.depth = current.depth + 1;
							current.moveDown();
						}
						if (current.lastMove != 'u' && current.moveDown()) {
							Game next = duplicate(current);
		//					close.add(getKey(next));
							graph.put(next, current);
		//					next.track = new String(current.track);
							temp.add(next);
		//					next.track += "" + 'd' + '\n';
							next.lastMove = 'd';
							next.depth = current.depth + 1;
							current.moveUp();
						}
						if (current.lastMove != 'r' && current.moveLeft()) {
							Game next = duplicate(current);
		//					close.add(getKey(next));
							graph.put(next, current);
		//					next.track = new String(current.track);
							temp.add(next);
		//					next.track += "" + 'l' + '\n';
							next.lastMove = 'l';
							next.depth = current.depth + 1;
							current.moveRight();
						}
						if (current.lastMove != 'l' && current.moveRight()) {
							Game next = duplicate(current);
		//					close.add(getKey(next));
							graph.put(next, current);
		//					next.track = new String(current.track);
							temp.add(next);
		//					next.track += "" + 'r' + '\n';
							next.lastMove = 'r';
							next.depth = current.depth + 1;
							current.moveLeft();
						}
						while (!temp.isEmpty()) {
							Game ready = temp.poll();
							if (ready.manhattan <= maxDepth) {
								open.push(ready);
							}
							else {
								if (ready.manhattan < minDepth) {
									minDepth = ready.manhattan;
								}
							}
						}
					}
				}
			}
			graph =  new HashMap<Game,Game>();
			maxDepth = minDepth;
			open.push(duplicate(new Game(board)));
		}
		//return null;
	}
}
