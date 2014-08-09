import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;


abstract public class Piece {	
    protected Color color = null;
    protected Vector <Space> moves = null;
    
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
	 * @param player
	 * @return
	 */
	public boolean sameOwner(Color player) {
		return color == player;
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
		if (piece == "Sentry")	retVal = new Sentry();
		if (piece == "Rock")	retVal = new Rock();
		
		if (retVal == null)
			return null;
		
		retVal.setColor(player);
		return retVal;
	}
	
	/**
	 * @return the image file on disk representing the piece
	 */
	public BufferedImage getPieceImage() {
		BufferedImage piece = null;
		String filename = "./images/";
		if (color == null) return piece;
		if (color == Color.BLACK) filename += "black/";
		if (color == Color.WHITE) filename += "white/";
		
		if (this instanceof King) 	filename += "king.png";
		if (this instanceof Queen) 	filename += "queen.png";
		if (this instanceof Bishop) filename += "bishop.png";
		if (this instanceof Knight) filename += "knight.png";
		if (this instanceof Rook)	filename += "rook.png";
		if (this instanceof Pawn)	filename += "pawn.png";
		if (this instanceof Rock)	filename += "rock.png";
		if (this instanceof Sentry) filename += "sentry.png";
		
		try {
			piece = ImageIO.read(new File(filename));
		} catch (IOException e) {
			System.out.println("File read failed.");
		}
		
		return piece;
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
	public static boolean outOfBounds(Piece [][] spaces, int rank, int file) {
		return (rank < 0) || (file < 0) || (rank >= spaces.length) || (file >= spaces[0].length);
	}
	
	/**
	 * @param other
	 * @return true if a piece can capture to a space
	 */
	public boolean canCapture(Space other) {
		for (int it = 0; it < moves.size(); it++) {
			if(moves.elementAt(it).equals(other))
				return true;
		}
		return false;
	}
}