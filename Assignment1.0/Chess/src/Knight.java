import java.util.Vector;


public class Knight extends Piece {
	private static final int [][] L_MOVES = {{ 2, 1},{ 2,-1},
											 {-2, 1},{-2,-1},
											 {-1, 2},{-1,-2},
											 { 1, 2},{ 1,-2}};
	
	/* (non-Javadoc)
	 * @see Piece#updateMoves(Piece[][], int, int)
	 */
	public void updateMoves(Piece[][] spaces, int rank, int file) {
		moves = new Vector<Space>();
		
		for (int r,f,it = 0; it < L_MOVES.length; it++) {
			r = rank + L_MOVES[it][0];
			f = file + L_MOVES[it][1];
			
			if (!outOfBounds(spaces,r,f))
				if (addMove(spaces[r][f],r,f))
					break;
		}
	}
}
