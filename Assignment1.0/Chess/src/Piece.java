import java.awt.Color;
import java.util.Vector;


abstract public class Piece {	
    protected Color color = null;
    protected Vector <Space> moves;
    
	/**
	 * @param spaces
	 * @param rank
	 * @param file
	 */
	abstract public void updateMoves(Piece [][] spaces, int rank, int file);
	
	/**
	 * @param target
	 * @return 
	 */
	public boolean isValidMove(Space target) {
		if (moves == null) 
			return false;
			
		for (int it = 0; it < moves.size(); it++) {
			if (moves.elementAt(it).equals(target))
				return true;
		}
		
		return false;
	}
	
	/**
	 * @return WHITE,BLACK,or NULL
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * @param player
	 */
	protected void setColor(Color player) {
		color = player;
	}
	
	/**
	 * @param other
	 * @return
	 */
	public boolean sameOwner(Piece other) {
		if (other == null)
			return false;
		return color == other.color;
	}
	
	/**
	 * @param piece
	 * @param player
	 * @return
	 */
	public static Piece stringToPiece(String piece, Color player) {
		Piece retVal = null;
		
		if (piece == "Pawn") 	retVal = new Pawn();
		if (piece == "Rook") 	retVal = new Rook();
		if (piece == "Knight")  retVal = new Knight();
		if (piece == "Bishop")	retVal = new Bishop();
		if (piece == "Queen")	retVal = new Queen();
		if (piece == "King")	retVal = new King();
		
		if (retVal == null)
			return null;
		
		retVal.setColor(player);
		return retVal;
	}
	
	/**
	 * UpdateMoves helper that
	 * checks if a given square should be added.
	 * @return true if the piece cannot jump past the square
	 */
	protected boolean addMove(Piece curSpace, int rank, int file) {
		if (curSpace == null) {
			moves.add(new Space(rank,file));
			return false;
		} 
		else if (sameOwner(curSpace)) {
			return true;
		} 
		else {
			moves.add(new Space(rank,file));
			return true;
		}
	}
	
	/**
	 * @param spaces
	 * @param rank
	 * @param file
	 * @return
	 */
	protected boolean outOfBounds(Piece [][] spaces, int rank, int file) {
		return (rank < 0) || (file < 0) || (rank >= spaces.length) || (file >= spaces[0].length);
	}
}