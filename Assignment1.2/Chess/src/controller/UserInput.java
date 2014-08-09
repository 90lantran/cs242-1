package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import pieces.Piece;
import model.Board;
import model.Chess;

/**
 * @author Will
 *
 */
public class UserInput {
	private static Board board;
	private static Board undoCache;
	
	/**
	 * A list of piece names used to initialize the 
	 * back row of each player in the chess game from
	 * left to right. Black is always on top.
	 * White is on the bottom.
	 */
	private static final String [] initList = {"Rook","Knight","Bishop","Queen",
											   "King","Bishop","Knight","Rook"};
	
	/**
	 * An array of possible button commands.
	 */
	public static String[] commands = { "Forfeit", 
										"Restart",
										"Scores", 
										"Undo"	  };
	
	/**
	 * Initializes the chess board for a new
	 * chess game. Used for interaction between
	 * the MVC classes.
	 */
	public static void initialize() {
		board = new Board();
		initialize(board);
	}
	
	/**
	 * Resets the board for a new 
	 * game with all the pieces in the right places.
	 * It also clears the undo cache.
	 */
	private static void modelReset() {
		board.reset();
		initialize(board);
		undoCache = board;
	}
	
	/**
	 * Initializes the chess board for a new game of chess.
	 * (Assumes that the board is currently empty.
	 * @param board The board to set the pieces on
	 */
	public static void initialize(Board b) {
		for (int i = 0; i < 8; i++) {			
			b.setPiece("Pawn",i,1,Color.BLACK);
			b.setPiece("Pawn",i,6,Color.WHITE);
			b.setPiece(initList[i],i,0,Color.BLACK);
			b.setPiece(initList[i],i,7,Color.WHITE);
		}
		undoCache = b;
	}
	
	/**
	 * Adds the current board representation to 
	 * the background of the chess GUI
	 * @param frame the frame for this instance of chess.
	 */
	public static void addBoard(JFrame frame) {
		frame.add(board);
	}
	
	
	/**
	 * Gets the tile clicked by the mouse.
	 * @return the mouselistener.
	 */
	public static MouseListener getMouse(final JFrame frame) {
		MouseListener mouselistener = new MouseListener() {
			int startRank;
			int startFile;
			int endRank;
			int endFile;
			
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {
				translateMouseCoords(e,true);
				
				Piece piece = board.getPiece(startRank, startFile);

				if (!piece.sameOwner(Chess.getTurn())) {
					JOptionPane.showMessageDialog(frame,"Wrong piece color.\nTry again.");
				}	
			}

			/** 
			 * A function called when the mouse is released after
			 * first being pressed.
			 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
			 * @param e the mouseEvent containing the location clicked
			 */
			public void mouseReleased(MouseEvent e) {
				translateMouseCoords(e,false);
				
				Board cache = new Board(board);
				if (board.movePiece(startRank,startFile,endRank,endFile)) {
					undoCache = cache;
					Chess.nextTurn();
					Color player = Chess.getTurn();
					String curPlayer = Chess.currentPlayer();
					
					if (board.inCheckmate(player)) {
						JOptionPane.showMessageDialog(frame,
								"Checkmate. " + curPlayer + " loses.");
						Chess.wins();
						modelReset();
					}
					else if (board.inCheck(player)) {
						JOptionPane.showMessageDialog(frame,
								curPlayer + " is in check.");
					}
				}
				else {
					JOptionPane.showMessageDialog(frame,"Illegal move.\nTry again.");
				}
			}
			
			/**
			 * Translates the floating point mouse coordinates
			 * to a pair of integers representing the
			 * (rank,file) position on the board.
			 * @param e the mouse event (press or release)
			 * @param pressed true if the mouse is pressed down
			 */
			private void translateMouseCoords(MouseEvent e, boolean pressed) {
				double x = e.getX() - 3;
				double y = e.getY() - 48;

				double i = x / 72;
				double j = y / 75;
				
				if (pressed) {
					startRank = (int)Math.floor(i);
					startFile = (int)Math.floor(j);
				}
				else {
					endRank = (int)Math.floor(i);
					endFile = (int)Math.floor(j);
				}
			}
		};
		
		return mouselistener;
	}
	
	/**
	 * Creates a new button listener that will take all
	 * possible commands that we have programmed.
	 * @param frame the frame that the actionlistener is anchored to
	 * @return the actionlistener to take button press input
	 */
	public static ActionListener getButtonListener(final JFrame frame) {
		ActionListener actionlistener = new ActionListener() {
			private static final int YES = 0;
			
			/** 
			 * A function to handle the actions performed by the user
			 * relating to the window menu.
			 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 * @param e the action event (button click)
			 */
			public void actionPerformed(ActionEvent e) {
				int i = getCommandIndex(e);

				switch (i) {
				case 0: // Forfeit
					Chess.forfeit();
					modelReset();
					break;
				case 1: // Restart
					restartGame(frame);
					break;
				case 2: // Scores
					String scoreboard = Chess.getScoreList();
					JOptionPane.showMessageDialog(frame,scoreboard);
					break;
				case 3: // Undo
					board.copy(undoCache);
					Chess.nextTurn();
					break;
				}
			}

			/**
			 * Attempts to restart the current game
			 * based on an evaluation of the conditions in place.
			 * @param frame the display of the game board
			 */
			private void restartGame(final JFrame frame) {
				int n = JOptionPane.showConfirmDialog(
					    frame,
					    Chess.currentPlayer() 
					    + " wants to restart. "
					    + "Do you accept?",
					    "An Inane Question",
					    JOptionPane.YES_NO_OPTION);
				
				if (n == YES) {
					Chess.restart();
					modelReset();
				}
			}
			
			
			/**
			 * Finds the command in the list of programmed
			 * features matching the command requested by a
			 * button click.
			 * @param e the action even (button click)
			 * @return the index in the global command list
			 */
			private int getCommandIndex(ActionEvent e) {
				String s = e.getActionCommand();

				int i;
				for (i = 0; i < commands.length; i++) {
					if (s.equals(commands[i])) {
						break;
					}
				}
				return i;
			}
		};
		
		return actionlistener;
	}
}
