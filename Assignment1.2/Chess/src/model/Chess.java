package model;

import java.awt.Color;

/**
 * @author Will
 *
 */
public class Chess {
	private static Color turn = null;
	private static String [] playerNames = null;
	private static int [] scores = null;
	
	/**
	 * A game ends in a draw. 
	 * Both players gain 1 point.
	 */
	public static void restart() {
		if (!initialized())
			return;
		
		for (int it = 0; it < scores.length; it++) {
			scores[it]++;
		}
		
		turn = Color.WHITE;
	}
	
	public static void wins() {
		if (turn == Color.WHITE) 
			scores[0]++;
		else
			scores[1]++;
		
		turn = Color.WHITE;
	}
	/**
	 * Called when a player chooses
	 * to forfeit the game. The opponent
	 * of the current player gets one point.
	 * the game should then be reset.
	 */
	public static void forfeit() {
		if (!initialized())
			return;
		
		if (turn == Color.WHITE)
			scores[1]++;
		else
			scores[0]++;
		
		turn = Color.WHITE;
	}
	
	
	/**
	 * Called when a player ends up in
	 * a stalemate. Neither player gets 
	 * any points.
	 */
	public static void draw() {
		turn = Color.WHITE;
	}
	
	/**
	 * Finds out which player's move is next.
	 * @return the color of the player
	 * who's turn it currently is.
	 */
	public static Color getTurn() {
		return turn;
	}
	
	/**
	 * Gets the name of the current turn's player.
	 * @return the name of the player who's turn it is
	 */
	public static String currentPlayer() {
		if (!initialized())
			return null;
		
		if (turn == Color.WHITE)
			return playerNames[0];
		else
			return playerNames[1];
	}
	/**
	 * Changes the current turn. 
	 * Black follows white and white
	 * follows black.
	 * This method will change turns
	 * if the game has not started.
	 */
	public static void nextTurn() {
		if (!initialized())
			return;
		
		if (turn == Color.WHITE)
			turn = Color.BLACK;
		else
			turn = Color.WHITE;
	}
	
	private static void startGame() {
		if (!initialized())
			turn = Color.WHITE;
	}
	
	/**
	 * Checks whether a game has started or not.
	 * @return true if the game has been initialized
	 */
	private static boolean initialized() {
		return (turn != null) && (playerNames != null) && (scores != null);
	}
	
	/**
	 * Gets a list of the players and scores and concatenates 
	 * them together into a table for the user.
	 * @return a string representation of the game scoreboard.
	 */
	public static String getScoreList() {
		if (!initialized())
			return "Scores unavailable.";
		String result = "[Player] \t\t [Score]\n";
		for (int it = 0; it < playerNames.length; it++) {
			result += playerNames[it] + " : " + scores[it] + "\n";
		}
		return result;
	}
	
	/**
	 * Initializes the names of the players for a given game
	 * of chess.
	 * @param players the list of player names
	 */
	public static void setPlayerNames(String [] players) {
		playerNames = new String[players.length];
		scores = new int[players.length];
		
		for (int it = 0; it < players.length; it++) {
			playerNames[it] = players[it];
			scores[it] = 0;
		}
		
		startGame();
	}
}
