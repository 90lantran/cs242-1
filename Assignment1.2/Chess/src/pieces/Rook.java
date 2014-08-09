package pieces;
import java.util.Vector;

import model.Space;


/**
 * @author Will
 *
 */
public class Rook extends Piece {
		
	/**
	 * Upates the possible moves for a given piece.
	 * Rooks can move in straight lines horizontal and vertical.
	 * @param spaces the state of the chess board.
	 * @param rank the x (rank) location of the piece
	 * @param file the y (file) location of the piece
	 */
	public void updateMoves(Piece [][] spaces, int rank, int file) {
		setMoves(new Vector<Space>());
	
		for (int r = rank+1; r < spaces.length; r++) {
			if (addMove(spaces[r][file],r,file)) 
				break;
		} // right
		
		for (int r = rank-1; r >= 0; r--) {
			if (addMove(spaces[r][file],r,file)) 
				break;
		} // left
		
		for (int f = file+1; f < spaces[0].length; f++) {
			if (addMove(spaces[rank][f],rank,f)) 
				break;
		} // down
		
		for (int f = file-1; f >= 0; f--) {
			if (addMove(spaces[rank][f],rank,f)) 
				break;
		} // up
	}

}
