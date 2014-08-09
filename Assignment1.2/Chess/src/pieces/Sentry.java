package pieces;
import java.util.Vector;

import model.Space;


/**
 * @author Will
 * 
 */
public class Sentry extends Piece {

	/**
	 * Upates the possible moves for a given piece.
	 * Sentries can move 2 spaces in any direction.
	 * @param spaces The state of the chess board.
	 * @param rank the x (rank) location of the piece
	 * @param file the y (file) location of the piece
	 */
	@Override
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
			
			if (filediff <= 2 || rankdiff <= 2)
				getMoves().add(curSpace);
		}
		
	}

}
