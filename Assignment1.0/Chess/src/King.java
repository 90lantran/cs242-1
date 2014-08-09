import java.util.Vector;



public class King extends Piece {

	/* (non-Javadoc)
	 * @see Piece#updateMoves(Piece[][], int, int)
	 */
	public void updateMoves(Piece[][] spaces, int rank, int file) {
		moves = new Vector<Space>();
		
		Piece queen = new Queen();
		queen.setColor(color);
		queen.updateMoves(spaces,rank,file);
		
		int rankdiff, filediff;
		Space curSpace;
		
		for (int it = 0; it < queen.moves.size(); it++) {
			curSpace = queen.moves.elementAt(it);
			filediff = Math.abs(curSpace.getFile() - file);
			rankdiff = Math.abs(curSpace.getRank() - rank);
			
			if (filediff == 1 || rankdiff == 1)
				moves.add(curSpace);
		}
	}
	
}
