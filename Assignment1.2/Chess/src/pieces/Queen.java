package pieces;
import java.util.Vector;

import model.Space;


/**
 * @author Will
 * 
 */
public class Queen extends Piece{

	/**
	 * Updates the possible moves for the piece.
	 * Queens can move as many squares as possible in any direction.
	 * @param spaces the current board state
	 * @param rank the x (rank) location of the piece
	 * @param file the y (file) location of the piece
	 */
	public void updateMoves(Piece[][] spaces, int rank, int file) {
		setMoves(new Vector<Space>());
		
		Piece rook = new Rook();
		rook.setColor(color);
		rook.updateMoves(spaces, rank, file);
		
		Piece bishop = new Bishop();
		bishop.setColor(color);
		bishop.updateMoves(spaces, rank, file);
		
		for (int it = 0; it < rook.getMoves().size(); it++) {
			getMoves().add(rook.getMoves().elementAt(it));
		}
		
		for (int it = 0; it < bishop.getMoves().size(); it++) {
			getMoves().add(bishop.getMoves().elementAt(it));
		}
	}

}
