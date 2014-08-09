import java.util.Vector;


public class Bishop extends Piece {

	/* (non-Javadoc)
	 * @see Piece#updateMoves(Piece[][], int, int)
	 */
	public void updateMoves(Piece[][] spaces, int rank, int file) {
		moves = new Vector<Space>();
		
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
