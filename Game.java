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
	private static final int undo_list_size = 20;
	private static final int randomFillChar_array_size = 50;
	private static final char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	private static char[] randomFillChar;
	private static char[] clone_randomFillChar;
	private static char[][] grid;
	private static char[][] clone_grid;
	private int score;
	private int clone_score;
	private int counter;
	private int clone_counter;
	private ArrayQueue undo_grid;
	private ArrayQueue undo_score;
	private ArrayQueue undo_rancdomFillChar;
	private ArrayQueue undo_randomPosition;
	private boolean gameover;
	private boolean victory;

	// constructor for the Game class
	public Game(ArrayQueue undo_grid, ArrayQueue undo_score, ArrayQueue undo_rancdomFillChar, ArrayQueue undo_randomPosition) {
		this.undo_grid = undo_grid;
		this.undo_score = undo_score;
		this.undo_rancdomFillChar = undo_rancdomFillChar;
		this.undo_randomPosition = undo_randomPosition;
		grid = new char[grid_width][grid_width];
		score = 0;
		clone_score = 0;
		counter = 0;
		clone_counter = 0;
		gameover = true;
		victory = false;
		generateRandomFillChar();
		shuffle(randomFillChar);
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
				if (System.getProperty("os.name").contains("Windows")) { //for display on Window's cmd
					System.out.printf("%s | ", Character.toString(grid[i][j]));
				} else if (System.getProperty("os.name").contains("Mac")) { // for display on Mac OS's terminal
					if (grid[i][j] == null_char) {
						System.out.printf(" %s | ", Character.toString(grid[i][j]));
					} else {
						System.out.printf("%s | ", Character.toString(grid[i][j]));
					}
				}
			}
			System.out.println();
			System.out.println("-----------------");
		}
		System.out.println("Score: " + score);
	}

	// slide up function for the grid
	public void slideUp() {
		cloneArray();

		for (int j = 0; j < grid.length; j++) {
			for (int i = 0; i < grid.length; i++) {
				if (grid[i][j] != null_char) { // check if cell will empty
					if ((i + 1) < grid.length) { // check if array is last cell
						for (int k = i + 1; k < grid.length; k++) {
							if (grid[k][j] != null_char) { // check if cell will empty
								if (grid[i][j] == grid[k][j]) { // merge if two tiles are the same 
									grid[i][j] = mergeTile(grid[k][j]);
									grid[k][j] = null_char;
									break; // break for finished one mapping
								} else {
									break; //break for jump to compare next cell
								}
							}
						}
					}
				}
			}
		}

		// bring all tiles to the user's input direction after merging tiles
		for (int i = 1; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
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

		if (moveValidate()) {
			randomFill();
			addToUndoList();
		}
	}

	// slide down function for the grid
	public void slideDown() {
		cloneArray();

		for (int j = 0; j < grid.length; j++) {
			for (int i = grid.length - 1; i >= 0; i--) {
				if (grid[i][j] != null_char) { // check if cell will empty
					if ((i - 1) >= 0) { // check if array is last cell
						for (int k = i - 1; k >= 0; k--) {
							if (grid[k][j] != null_char) { // check if cell will empty
								if (grid[i][j] == grid[k][j]) { // merge if two tiles are the same 
									grid[i][j] = mergeTile(grid[k][j]);
									grid[k][j] = null_char;
									break; // break for finished one mapping
								} else {
									break; //break for jump to compare next cell
								}
							}
						}
					}
				}
			}
		}

		// bring all tiles to the user's input direction after merging tiles
		for (int i = grid.length - 2; i >= 0; i--) {
			for (int j = 0; j < grid.length; j++) {
				int k = i;
				while (grid[k + 1][j] == null_char) {
					grid[k + 1][j] = grid[k][j];
					grid[k][j] = null_char;
					k++;
					if (k == 3) {
						break;
					}
				}
			}
		}

		if (moveValidate()) {
			randomFill();
			addToUndoList();
		}
	}

	// slide left function for the grid
	public void slideLeft() {
		cloneArray();

		for (int i = 0; i < grid.length; i++) {		
			for (int j = 0; j < grid.length; j++) {
				if (grid[i][j] != null_char) { // check if cell will empty
					if ((j + 1) < grid.length) { // check if array is last cell
						for (int k = j + 1; k < grid.length; k++) {
							if (grid[i][k] != null_char) { // check if cell will empty
								if (grid[i][j] == grid[i][k]) { // merge if two tiles are the same 
									grid[i][j] = mergeTile(grid[i][k]);
									grid[i][k] = null_char;
									break; // break for finished one mapping
								} else {
									break; //break for jump to compare next cell
								}
							}
						}
					}
				}
			}
		}

		// bring all tiles to the user's input direction after merging tiles
		for (int i = 0; i < grid.length; i++) {
			for (int j = 1; j < grid.length; j++) {
				int k = j;
				while (grid[i][k - 1] == null_char) {
					grid[i][k - 1] = grid[i][k];
					grid[i][k] = null_char;
					k--;
					if (k == 0) {
						break;
					}
				}
			}
		}

		if (moveValidate()) {
			randomFill();
			addToUndoList();
		}
	}

	// slide right function for the grid
	public void slideRight() {
		cloneArray();

		for (int i = 0; i < grid.length; i++) {	
			for (int j = grid.length - 1; j >= 0; j--) {
				if (grid[i][j] != null_char) { // check if cell will empty
					if ((j - 1) >= 0) { // check if array is last cell
						for (int k = j - 1; k >= 0; k--) {
							if (grid[i][k] != null_char) { // check if cell will empty
								if (grid[i][j] == grid[i][k]) { // merge if two tiles are the same 
									grid[i][j] = mergeTile(grid[i][k]);
									grid[i][k] = null_char;
									break; // break for finished one mapping
								} else {
									break; //break for jump to compare next cell
								}
							}
						}
					}
				}
			}
		}

		// bring all tiles to the user's input direction after merging tiles
		for (int i = 0; i < grid.length; i++) {
			for (int j = grid.length - 2; j >= 0; j--) {
				int k = j;
				while (grid[i][k + 1] == null_char) {
					grid[i][k + 1] = grid[i][k];
					grid[i][k] = null_char;
					k++;
					if (k == 3) {
						break;
					}
				}
			}
		}

		if (moveValidate()) {
			randomFill();
			addToUndoList();
		}
	}

	// random fill tile function for the grid
	public void randomFill() {
		boolean filled = true;
		int x, y;
		char tile;
		Random rd1 = new Random();
		Random rd2 = new Random();

		if (counter == 50) {
			generateRandomFillChar();
			shuffle(randomFillChar);
			counter = 0;
		}

		tile = randomFillChar[counter];

		while (filled) {
			x = rd1.nextInt(4);
			y = rd2.nextInt(4);
			if (grid[x][y] == null_char) {
				grid[x][y] = tile;
				filled = false;
				counter++;
			}
		}
	}

	// game over function for the grid
	public boolean gameover() {
		gameover = true;

		// validate available moves for slide up
		for (int j = 0; j < grid.length; j++) {
			for (int i = 0; i < grid.length; i++) {
				if (grid[i][j] != null_char) { // check if cell will empty
					if ((i + 1) < grid.length) { // check if array will outbound
						for (int k = i + 1; k < grid.length; k++) {
							if (grid[k][j] != null_char) { // check if cell will empty
								if (grid[i][j] == grid[k][j]) { // check if two tiles are the same 
									gameover = false;
									if (!gameover)
										return gameover;
									break; // break for finished one mapping
								} else {
									break; //break for jump to next cell
								}
							}
						}
					}
				}
			}
		}

		// validate avaliable moves for slide down
		for (int j = 0; j < grid.length; j++) {
			for (int i = grid.length - 1; i >= 0; i--) {
				if (grid[i][j] != null_char) { // check if cell will empty
					if ((i - 1) >= 0) { // check if array will outbound
						for (int k = i - 1; k >= 0; k--) {
							if (grid[k][j] != null_char) { // check if cell will empty
								if (grid[i][j] == grid[k][j]) { // check if two tiles are the same 
									gameover = false;
									if (!gameover)
										return gameover;
									break; // break for finished one mapping
								} else {
									break; //break for jump to next cell
								}
							}
						}
					}
				}
			}
		}

		// validate avaliable moves for slide left
		for (int i = 0; i < grid.length; i++) {		
			for (int j = 0; j < grid.length; j++) {
				if (grid[i][j] != null_char) { // check if cell will empty
					if ((j + 1) < grid.length) { // check if array will outbound
						for (int k = j + 1; k < grid.length; k++) {
							if (grid[i][k] != null_char) { // check if cell will empty
								if (grid[i][j] == grid[i][k]) { // check if two tiles are the same 
									gameover = false;
									if (!gameover)
										return gameover;
									break; // break for finished one mapping
								} else {
									break; //break for jump to next cell
								}
							}
						}
					}
				}
			}
		}

		// validate avaliable moves for slide right
		for (int i = 0; i < grid.length; i++) {	
			for (int j = grid.length - 1; j >= 0; j--) {
				if (grid[i][j] != null_char) { // check if cell will empty
					if ((j - 1) >= 0) { // check if array will outbound
						for (int k = j - 1; k >= 0; k--) {
							if (grid[i][k] != null_char) { // check if cell will empty
								if (grid[i][j] == grid[i][k]) { // check if two tiles are the same 
									gameover = false;
									if (!gameover)
										return gameover;
									break; // break for finished one mapping
								} else {
									break; //break for jump to next cell
								}
							}
						}
					}
				}
			}
		}

		return gameover;
	}

	// undo function for grid
	public void undo() {
		if (undo_grid.length() > 0 && undo_score.length() > 0 && undo_rancdomFillChar.length() > 0 && undo_randomPosition.length() > 0) {
			char[][] undo = new char[grid_width][grid_width]; // initial a new char 2d array
			undo = (char[][])undo_grid.pop(); // assign the previous state to the new char 2d array
			// use nested loop to write into the grid array
			for (int i = 0; i < undo.length; i++) {
			  	for (int j = 0; j < undo[i].length; j++) {
			    	grid[i][j] = undo[i][j];
			  	}
			}
			score = (int)undo_score.pop(); // replace the score to the previous one

			char[] undo1 = new char[randomFillChar_array_size]; // initial a new char array
			undo1 = (char[])undo_rancdomFillChar.pop(); // assign the orevious state to the new char array
			// use loop to write into the randomFillChar array
			for (int i = 0; i < undo1.length; i++) {
				randomFillChar[i] = undo1[i];
			}
			counter = (int)undo_randomPosition.pop(); // replace the counter to the previous one
		} else {
			// message for empty undo lists
			System.out.println("No moves can undo!");
		}
	}

	// reset the game and the variables it needs
	public void reset() {
		grid = new char[grid_width][grid_width];
		clone_grid = new char[grid_width][grid_width];
		score = 0;
		clone_score = 0;
		counter = 0;
		clone_counter = 0;
		gameover = true;
		victory = false;
		emptyUndoLists();
		generateRandomFillChar();
		shuffle(randomFillChar);
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

	// check the grid is full of tiles
	public boolean gridIsFull() {
		boolean full = false;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] != null_char) {
					full = true;
				} else {
					full = false;
					if (!full)
						return full;
				}
			}
		}
		return full;
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
			if (c == 'Z') {
				victory = true;
				return alphabet.length - 1;
			} else {
				if (c == alphabet[i]) {
					x =  i + 1;
					break;
				}
			}
		}
		return x;
	}

	// return next letter of current one for merging tile
	public char mergeTile(char c) {
		score += Math.pow(2, convertLetter(c)); // calculate score
		return alphabet[convertLetter(c)];
	}

	// clone grid array, score, randomFillChar array and randomFillChar array counter 
	public void cloneArray() {
		clone_grid = new char[grid_width][grid_width]; // everytime initial with new char 2d array to avoid duplicate memory address
		// clone grid array
		for (int i = 0; i < grid.length; i++) {
		  	for (int j = 0; j < grid[i].length; j++) {
		    	clone_grid[i][j] = grid[i][j];
		  	}
		}
		clone_score = score; // clone score

		clone_randomFillChar = new char[randomFillChar_array_size]; // everytime initial with new char array to avoid duplicate memory address
		// clone randomFillChar array
		for (int i = 0; i < randomFillChar_array_size; i++) {
			clone_randomFillChar[i] = randomFillChar[i];
		}
		clone_counter = counter; // clone the array counter
	}

	// moves validation
	public boolean moveValidate() {
		return !(Arrays.deepEquals(grid, clone_grid));
	}

	// add the grid and score to the undo list
	public void addToUndoList() {
		if (undo_grid.length() == undo_list_size && undo_score.length() == undo_list_size && undo_rancdomFillChar.length() == undo_list_size && undo_randomPosition.length() == undo_list_size) {
			undo_grid.dequeue(); // remove the last grid
			undo_grid.enqueue(clone_grid); // add grid in to undo list

			undo_score.dequeue(); // remove the last score and add the new score
			undo_score.enqueue(clone_score); // add score in to undo list

			undo_rancdomFillChar.dequeue(); // remove the last array
			undo_rancdomFillChar.enqueue(clone_randomFillChar); // add the array to undo list

			undo_randomPosition.dequeue(); // remove the last random position
			undo_randomPosition.enqueue(clone_counter); // add the random position to undo list
		} else {
			undo_grid.enqueue(clone_grid);
			undo_score.enqueue(clone_score);
			undo_rancdomFillChar.enqueue(clone_randomFillChar);
			undo_randomPosition.enqueue(clone_counter);
		}
	}

	// empty all the undo lists if user reset the game
	public void emptyUndoLists() {
		while (undo_grid.length() > 0 && undo_score.length() > 0 && undo_rancdomFillChar.length() > 0 && undo_randomPosition.length() > 0) {
			undo_grid.pop();
			undo_score.pop();
			undo_rancdomFillChar.pop();
			undo_randomPosition.pop();
		}
	}

	// generate random fill number for the randomFill function
	public void generateRandomFillChar() {
		randomFillChar = new char[randomFillChar_array_size];

		// fill the '0' for 40 times to the array
		for (int i = 0; i < 40; i++) {
			randomFillChar[i] = '0';
		}

		// fill the 'A' for 9 times to the array
		for (int i = 40; i < 49; i++) {
			randomFillChar[i] = 'A';
		}

		// fill the 'B' for  1 times to the array
		randomFillChar[49] = 'B';
	}

	// shuffle the array 
	public void shuffle(char[] c) {
		int len = c.length;
		for (int i = 0; i < c.length; i++) {
			// get the random index from past i
			int random = i + (int)(Math.random() * len - i);
			// swap the element between present one and the random one
			char tmp = randomFillChar[random];
			randomFillChar[random] = randomFillChar[i];
			randomFillChar[i] = tmp;
		}
	}

	public void start() {
		// read user's input
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);

		// control the system input life cycle
		boolean cont = true;

		// print the menu and grid
		try {
			while(cont) {
				System.out.println();
				System.out.println("      2048");
				display();
				if (!victory) { // menu for not yet victory
					System.out.println("(2) Down");
					System.out.println("(4) Left");
					System.out.println("(6) Right");
					System.out.println("(8) Up");
					System.out.println("(0) Undo");
				}
				System.out.println("(R) Reset");
				System.out.println("(Q) Quit");
				if ((gameover() && gridIsFull())) { // 'gamover' message print out here
					System.out.println("Game Over! Please enter 'r' to reset or 'q' to leave.");
				}
				if (victory) { // 'victory' message print out here
					System.out.println("You Win! Please enter 'r' to reset or 'q' to leave.");
				}
				System.out.print("Which Move: ");				
				String cmd = br.readLine();
				if (!victory) {
					// menu logic for not yet victory
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

						//end the system input life cycle
						cont = false;
					} else if (cmd.equals("0")) {
						undo();
					} else if (cmd.equalsIgnoreCase("r")) {
						reset();
					} else {
						System.out.println();
						System.out.println("Invalid Input! Please try again.");
					}
				} else {
					// menu logic for victory
					if (cmd.equalsIgnoreCase("r")) {
						reset();
					} else if (cmd.equalsIgnoreCase("q")) {
						System.out.println();
						System.out.println("Leave Game. Good Bye!!!");
						System.out.println();

						//end the system input life cycle
						cont = false;
					} else {
						System.out.println();
						System.out.println("Invalid Input! Please try again.");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}