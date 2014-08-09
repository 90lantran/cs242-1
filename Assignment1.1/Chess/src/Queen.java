import java.util.Vector;


public class Queen extends Piece{

	/* (non-Javadoc)
	 * @see Piece#updateMoves(Piece[][], int, int)
	 */
	public void updateMoves(Piece[][] spaces, int rank, int file) {
		moves = new Vector<Space>();
		
		Piece rook = new Rook();
		rook.setColor(color);
		rook.updateMoves(spaces, rank, file);
		
		Piece bishop = new Bishop();
		bishop.setColor(color);
		bishop.updateMoves(spaces, rank, file);
		
		for (int it = 0; it < rook.moves.size(); it++) {
			moves.add(rook.moves.elementAt(it));
		}
		
		for (int it = 0; it < bishop.moves.size(); it++) {
			moves.add(bishop.moves.elementAt(it));
		}
	}

}
