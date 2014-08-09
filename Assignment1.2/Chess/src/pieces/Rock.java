package pieces;




/**
 * @author Will
 * 
 */
public class Rock extends Piece {

	/**
	 * Upates the possible moves for a given piece.
	 * Rocks cannot move at all.
	 * @param spaces The state of the chess board.
	 * @param rank the x (rank) location of the piece
	 * @param file the y (file) location of the piece
	 */
	@Override
	public void updateMoves(Piece[][] spaces, int rank, int file) {
		setMoves(null);
	}

}
