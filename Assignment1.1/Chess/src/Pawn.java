import java.awt.Color;
import java.util.Vector;


public class Pawn extends Piece {
	boolean firstMove = true;
	
	/* (non-Javadoc)
	 * @see Piece#updateMoves(Piece[][], int, int)
	 */
	public void updateMoves(Piece[][] spaces, int rank, int file) {
		moves = new Vector<Space>();
		
		if (color == Color.BLACK) {
			if (firstMove) {
				firstMove = false;
				if (!outOfBounds(spaces,rank,file+2))
					addMove(spaces[rank][file+2],rank,file+2);
			}
			if (!outOfBounds(spaces,rank+1,file+1)) 
				if (spaces[rank+1][file+1] != null)
					addMove(spaces[rank+1][file+1],rank+1,file+1);
			if (!outOfBounds(spaces,rank-1,file+1))
				if (spaces[rank-1][file+1] != null)
					addMove(spaces[rank-1][file+1],rank-1,file+1);
			if (!outOfBounds(spaces,rank,file+1))
				addMove(spaces[rank][file+1],rank,file+1);
		}
		else {
			if (firstMove) {
				firstMove = false;
				if (!outOfBounds(spaces,rank,file-2))
					addMove(spaces[rank][file-2],rank,file-2);
			}
			if (!outOfBounds(spaces,rank+1,file-1))
				if (spaces[rank+1][file-1] != null)
					addMove(spaces[rank+1][file-1],rank+1,file-1);
			if (!outOfBounds(spaces,rank-1,file-1))
				if (spaces[rank-1][file-1] != null)
					addMove(spaces[rank-1][file-1],rank-1,file-1);
			if (!outOfBounds(spaces,rank,file-1)) 
				addMove(spaces[rank][file+1],rank,file-1);
		}
	}

}
