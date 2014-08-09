package pieces;

import java.util.Vector;

import model.Space;


/**
 * @author Will
 * 
 */
public class Bishop extends Piece {

	/**
	 * Updates the possible moves for the piece.
	 * Bishops can only move diagonally.
	 * @param spaces the current board state
	 * @param rank the x (rank) location of the piece
	 * @param file the y (file) location of the piece
	 */
	public void updateMoves(Piece[][] spaces, int rank, int file) {
		setMoves(new Vector<Space>());
		
		for (int r = rank+1,f = file+1; r < spaces.length && f < spaces[0].length; r++,f++) {
			if (addMove(spaces[r][f],r,f))
				break;
		}
		
		for (int r = rank-1,f = file+1; r >= 0 && f < spaces[0].length; r--,f++) {
			if (addMove(spaces[r][f],r,f))
				break;
		}
		
		for (int r = rank+1,f = file-1; r < spaces.length && f >= 0; r++,f--) {
			if (addMove(spaces[r][f],r,f))
				break;
		}
		
		for (int r = rank-1,f = file-1; r >= 0 && f >= 0; r--,f--) {
			if (addMove(spaces[r][f],r,f))
				break;
		}
	}
}
