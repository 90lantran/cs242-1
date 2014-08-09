package pieces;
import java.util.Vector;

import model.Space;


/**
 * @author Will
 * 
 */
public class Knight extends Piece {
	private static final int [][] L_MOVES = {{ 2, 1},{ 2,-1},
											 {-2, 1},{-2,-1},
											 {-1, 2},{-1,-2},
											 { 1, 2},{ 1,-2}};
	
	/**
	 * Updates the possible moves for the piece.
	 * Knights can move in L-shapes 
	 * (combinations of 2 in one direction and 1 in another).
	 * @param spaces the current board state
	 * @param rank the x (rank) location of the piece
	 * @param file the y (file) location of the piece
	 */
	public void updateMoves(Piece[][] spaces, int rank, int file) {
		setMoves(new Vector<Space>());
		
		for (int r,f,it = 0; it < L_MOVES.length; it++) {
			r = rank + L_MOVES[it][0];
			f = file + L_MOVES[it][1];
			
			if (!outOfBounds(spaces,r,f))
				addMove(spaces[r][f],r,f);
		}
	}
}
