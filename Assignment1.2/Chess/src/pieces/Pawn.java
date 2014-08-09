package pieces;
import java.awt.Color;
import java.util.Vector;

import model.Space;


/**
 * @author Will
 * 
 */
public class Pawn extends Piece {
	private boolean firstMove = true;
	
	/**
	 * Updates the possible moves for the piece.
	 * Pawns can only move forward, and can only capture diagonally.
	 * @param spaces the current board state
	 * @param rank the x (rank) location of the piece
	 * @param file the y (file) location of the piece
	 */
	public void updateMoves(Piece[][] spaces, int rank, int file) {
		setMoves(new Vector<Space>());
		
		int moveFactor;
		if (color == Color.BLACK) 
			moveFactor =  1;
		else
			moveFactor = -1;
		
		forward(spaces,rank,file,moveFactor);
		diagonalCapture(spaces,rank,file,moveFactor);
	}
	
	/**
	 * Checks whether a pawn can move forward, and adds the valid
	 * moves to the current state of the piece. If a pawn has not
	 * made its first move, it can move 2 spaces forward.
	 * @param spaces the current board state
	 * @param rank the x (rank) location of the piece
	 * @param file the y (file) location of the piece
	 * @param f a multiplier for moves determined by the starting 
	 * 		  position of the pawn
	 */
	private void forward(Piece [][] spaces, int rank, int file, int f) {
		if (firstMove && !outOfBounds(spaces,rank,file+2*f)) {
			firstMove = false;
			addMove(spaces[rank][file+2*f],rank,file+2*f);
		}
		
		if (!outOfBounds(spaces,rank,file+1*f)) 
			addMove(spaces[rank][file+1*f],rank,file+1*f);
	}
	
	/**
	 * Checks whether a pawn can capture diagonally and adds the 
	 * valid moves to the current state of the piece.
	 * @param spaces the current board state
	 * @param rank the x (rank) location of the piece
	 * @param file the y (file) location of the piece
	 * @param f a multiplier for moves determined by the starting 
	 * 		  position of the pawn
	 */
	private void diagonalCapture(Piece [][] spaces, int rank, int file, int f) {
		if (!outOfBounds(spaces,rank+1,file+1*f) && spaces[rank+1][file+1*f] != null)
			addMove(spaces[rank+1][file+1*f],rank+1,file+1*f);
		if (!outOfBounds(spaces,rank-1,file+1*f) && spaces[rank-1][file+1*f] != null)
			addMove(spaces[rank-1][file+1*f],rank-1,file+1*f);
	}
}
