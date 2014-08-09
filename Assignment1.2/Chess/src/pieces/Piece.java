package pieces;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

import model.Space;


/**
 * @author Will
 * 
 */
abstract public class Piece {	
    /**
     * The color of a piece.
     * (BLACK,WHITE,NULL)
     */
    protected Color color = null;
    /**
     * The possible moves for a piece.
     */
    protected Vector <Space> moves = null;
    
	/**
	 * Upates the possible moves for a given piece.
	 * @param spaces The state of the chess board.
	 * @param rank the x (rank) location of the piece
	 * @param file the y (file) location of the piece
	 */
	abstract public void updateMoves(Piece [][] spaces, int rank, int file);
	
	/**
	 * @param target the (rank,file) pair on the board to move to
	 * @return true if a given move is possible for a piece
	 */
	public boolean isValidMove(Space target) {
		if (getMoves() == null) 
			return false;
			
		for (int it = 0; it < getMoves().size(); it++) {
			if (getMoves().elementAt(it).equals(target))
				return true;
		}
		
		return false;
	}
	
	/**
	 * @return (WHITE,BLACK,or NULL) the color of a piece
	 */
	public Color getColor() {
		return color;
	}
	
	/**
	 * @param player (WHITE,BLACK) the color to set the piece to
	 */
	protected void setColor(Color player) {
		color = player;
	}
	
	/**
	 * @param other the piece to compare the current one with
	 * @return true if one piece has the same owner as the other
	 */
	public boolean sameOwner(Piece other) {
		if (other == null)
			return false;
		return color == other.color;
	}
	
	/**
	 * @param player the color of the player to check against
	 * @return true if the current piece is of the same color
	 */
	public boolean sameOwner(Color player) {
		return color == player;
	}
	
	/**
	 * Converts a string to an instance of a new piece.
	 * @param piece the type of piece to instantiate
	 * @param player the color of piece to initialize
	 * @return the initialized piece matching the string input
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
		
		if (color == null) 			return piece;
		if (color == Color.BLACK) 	filename += "black/";
		if (color == Color.WHITE) 	filename += "white/";
		
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
	 * It must not have the same owner or 
	 * @return true if the piece cannot jump past the square
	 */
	protected boolean addMove(Piece curSpace, int rank, int file) {
		if (curSpace == null) {
			getMoves().add(new Space(rank,file));
			return false;
		} 
		else if (sameOwner(curSpace)) {
			return true;
		} 
		else {
			getMoves().add(new Space(rank,file));
			return true;
		}
	}
	
	/**
	 * @param spaces The state of the chess board.
	 * @param rank the x (rank) location of the piece
	 * @param file the y (file) location of the piece
	 * @return true if a given piece is out of bounds or null
	 */
	public static boolean outOfBounds(Piece [][] spaces, int rank, int file) {
		return (rank < 0) || (file < 0) 
			|| (rank >= spaces.length) 
			|| (file >= spaces[0].length);
	}
	
	/**
	 * @param other the space (rank,file) to check if a piece can capture to
	 * @return true if a piece can capture to a space
	 */
	public boolean canCapture(Space other) {
		for (int it = 0; it < getMoves().size(); it++) {
			if(getMoves().elementAt(it).equals(other))
				return true;
		}
		return false;
	}

	/**
	 * @return the moves for a piece
	 */
	public Vector <Space> getMoves() {
		return moves;
	}

	/**
	 * @param moves the moves to set for the piece
	 */
	protected void setMoves(Vector <Space> moves) {
		this.moves = moves;
	}
}