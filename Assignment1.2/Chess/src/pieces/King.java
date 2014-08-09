package pieces;
import java.util.Vector;

import model.Space;



/**
 * @author Will
 * 
 */
public class King extends Piece {

	/**
	 * Updates the possible moves for the piece.
	 * Kings can move one space in any direction.
	 * @param spaces the current board state
	 * @param rank the x (rank) location of the piece
	 * @param file the y (file) location of the piece
	 */
	public void updateMoves(Piece[][] spaces, int rank, int file) {
		setMoves(new Vector<Space>());
		
		Piece queen = new Queen();
		queen.setColor(color);
		queen.updateMoves(spaces,rank,file);
		
		int rankdiff, filediff;
		Space curSpace;
		
		for (int it = 0; it < queen.getMoves().size(); it++) {
			curSpace = queen.getMoves().elementAt(it);
			filediff = Math.abs(curSpace.file() - file);
			rankdiff = Math.abs(curSpace.rank() - rank);
			
			if (filediff == 1 || rankdiff == 1)
				getMoves().add(curSpace);
		}
	}
	
}
