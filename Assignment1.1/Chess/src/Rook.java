import java.util.Vector;


public class Rook extends Piece {
		
	/* (non-Javadoc)
	 * @see Piece#updateMoves(Piece[][], int, int)
	 */
	public void updateMoves(Piece [][] spaces, int rank, int file) {
		moves = new Vector<Space>();
	
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
