/*
 * Title: DSA Assignment 15-16
 * Description: 2048 game
 * Student name: Lin Ziqiao 
 * Student ID: 6826257
 * Date: 27 Nov 2015 
 */

public class Start {
	public static void main(String[] args) {
		// initial the undo list(ArrayQueue)
		ArrayQueue undo_grid = new ArrayQueue();
		ArrayQueue undo_score = new ArrayQueue();

		Game game = new Game(undo_grid, undo_score);
		game.start();
	}
}