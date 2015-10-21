/*
 * Title: DSA Assignment 15-16
 * Description: 2048 game
 * Student name: Lin Ziqiao 
 * Student ID: 6826257
 * Date: 27 Nov 2015 
 */

import java.util.*;
import java.io.*;

public class Game {

	private static final char null_char = '\u0000';
	private static final int grid_width = 4;
	private static char[][] grid;
	private static final char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	private int score;
	private static char[][] clone_grid;
	private int clone_score;
	private static final int undo_list_size = 20;
	private ArrayQueue undo_grid;
	private ArrayQueue undo_score;
	private boolean emptyUndoList = true;

	// constructor for the Game class
	public Game(ArrayQueue undo_grid, ArrayQueue undo_score) {
		this.undo_grid = undo_grid;
		this.undo_score = undo_score;
		grid = new char[grid_width][grid_width];
		score = 0;
	}

	// display the grid and player's score
	public void display() {
		if (gridIsEmpty()) {
			init();
		}

		System.out.println("-----------------");
		for (int i = 0; i < grid.length; i++) {
			System.out.print("| ");
			for (int j = 0; j < grid[i].length; j++) {
				System.out.print(grid[i][j] + " | ");
			}
			System.out.println();
			System.out.println("-----------------");
		}
		System.out.println("Score: " + score);
	}

	// slide up function for the grid
	public void slideUp() {
		cloneArray();

		for (int i = 1; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if (grid[i][j] == grid[i - 1][j] && (grid[i][j] != null_char || grid[i - 1][j] != null_char)) {
					grid[i - 1][j] = mergeTile(grid[i][j]);
					grid[i][j] = null_char;
				} else if (grid[i - 1][j] == null_char && grid[i][j] != null_char) {
					int k = i;
					while (grid[k - 1][j] == null_char) {
						grid[k - 1][j] = grid[k][j];
						grid[k][j] = null_char;
						k--;
						if (k == 0) {
							break;
						}
					}
				}
			}
		}

		for (int i = 1; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if (grid[i][j] == grid[i - 1][j] && (grid[i][j] != null_char || grid[i - 1][j] != null_char)) {
					grid[i - 1][j] = mergeTile(grid[i][j]);
					grid[i][j] = null_char;
				}
			}
		}

		if (moveValidate()) {
			randomFill();
			addToUndoList();
		}
	}

	// slide down function for the grid
	public void slideDown() {
		
		System.out.println("down");
	}

	// slide left function for the grid
	public void slideLeft() {
		System.out.println("left");
	}

	// slide right function for the grid
	public void slideRight() {
		System.out.println("right");
	}

	// random fill tile function for the grid
	public void randomFill() {
		boolean filled = true;
		int x, y;
		char tile;
		Random rd1 = new Random();
		Random rd2 = new Random();
		Random rd3 = new Random();

		int ratio = rd3.nextInt(50) + 1;
		if (ratio <= 40) {
			tile = '0';
		} else if (ratio == 50) {
			tile = 'B';
		} else {
			tile = 'A';
		}

		while (filled) {
			x = rd1.nextInt(4);
			y = rd2.nextInt(4);
			if (grid[x][y] == null_char) {
				grid[x][y] = tile;
				filled = false;
			}
		}
	}

	// game over function for the grid
	public boolean gameover() {
		return true;
	}

	// undo function for grid
	public void undo() {
		if (undo_grid.length() > 0 && undo_score.length() > 0) {
			char[][] undo = new char[grid_width][grid_width];
			undo = (char[][])undo_grid.pop();
			for (int i = 0; i < undo.length; i++) {
			  	for (int j = 0; j < undo[i].length; j++) {
			    	grid[i][j] = undo[i][j];
			  	}
			}
			score = (int)undo_score.pop();
			emptyUndoList = false;
		} else {
			System.out.println("No moves can undo!");
		}
	}

	// reset the game
	public void reset() {
		grid = new char[grid_width][grid_width];
		clone_grid = new char[grid_width][grid_width];
		score = 0;
		clone_score = 0;
		emptyUndoList = true;
		emptyUndoLists();
	}

	// check the grid is empty or not
	public boolean gridIsEmpty() {
		boolean empty = false;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] != null_char) {
					empty = true;
					break;
				}
			}
		}
		if (empty) {
			return false;
		} else {
			return true;
		}
	}

	// initial the grid with random tile
	public void init() {
		randomFill();
		randomFill();
	}

	// using number to represent the letters,
	// used to calculate the score
	public int convertLetter(char c) {
		int x = 0;
		for (int i = 0; i < alphabet.length; i++) {
			if (c == alphabet[i]) {
				x =  i + 1;
				break;
			}
		}
		return x;
	}

	// return next letter of current one for merging tile
	public char mergeTile(char c) {
		score += Math.pow(2, convertLetter(c)); // calculate score
		return alphabet[convertLetter(c)];
	}

	// clone grid array for validation
	public void cloneArray() {
		clone_grid = new char[grid_width][grid_width];
		for (int i = 0; i < grid.length; i++) {
		  	for (int j = 0; j < grid[i].length; j++) {
		    	clone_grid[i][j] = grid[i][j];
		  	}
		}
		clone_score = score;
	}

	// moves validation
	public boolean moveValidate() {
		return !(Arrays.deepEquals(grid, clone_grid));
	}

	// add the grid and score to the undo list
	public void addToUndoList() {
		if (emptyUndoList) {
			if (undo_grid.length() >= undo_list_size && undo_score.length() >= undo_list_size) {
				undo_grid.dequeue(); // remove the last grid and add the new grid
				undo_grid.enqueue(clone_grid); // add grid in to undo list

				undo_score.dequeue(); // remove the last score and add the new score
				undo_score.enqueue(clone_score); // add score in to undo list
			} else {
				undo_grid.enqueue(clone_grid);
				undo_score.enqueue(clone_score);
			}
		} else {
			emptyUndoLists();

			if (undo_grid.length() == 0 && undo_score.length() == 0) {
				emptyUndoList = true;
				undo_grid.enqueue(clone_grid);
				undo_score.enqueue(clone_score);
			}
		}
	}

	// empty the undo lists if user had did the undo before
	public void emptyUndoLists() {
		while (undo_grid.length() != 0 && undo_score.length() != 0) {
			undo_grid.pop();
			undo_score.pop();
		}
	}

	public void start() {
		// read user"s input
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);

		// control the user"s input life cycle
		boolean cont = true;

		// print the menu and grid
		try {
			while(cont) {
				System.out.println();
				System.out.println("      2048");
				display();
				System.out.println("(2) Down");
				System.out.println("(4) Left");
				System.out.println("(6) Right");
				System.out.println("(8) Up");
				System.out.println("(0) Undo");
				System.out.println("(R) Reset");
				System.out.println("(Q) Quit");
				System.out.print("Which Move: ");
				String cmd = br.readLine();
				if (cmd.equals("2")) {
					slideDown();
				} else if (cmd.equals("4")) {
					slideLeft();
				} else if (cmd.equals("6")) {
					slideRight();
				} else if (cmd.equals("8")) {
					slideUp();
				} else if (cmd.equalsIgnoreCase("q")) {
					System.out.println();
					System.out.println("Leave Game. Good Bye!!!");
					System.out.println();

					//end the user"s input life cycle
					cont = false;
				} else if (cmd.equals("0")) {
					undo();
				} else if (cmd.equalsIgnoreCase("r")) {
					reset();
				} else {
					System.out.println("Invalid Input! Please try again.");
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}