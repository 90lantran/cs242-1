import java.awt.Color;
import java.util.Vector;


public class Board {
	private Piece [][] spaces;
	
	
	/**
	 * 
	 */
	public Board() {
		spaces = new Piece[8][8];
	}
	
	/**
	 * @param other
	 */
	public Board(Board other) {
		if (other == null) {
			spaces = null;
			return;
		}
		
		spaces = new Piece[other.spaces.length][other.spaces[0].length];
		
		for (int rank = 0; rank < spaces.length; rank++) {
			for (int file = 0; file < spaces[0].length; file++) {
				spaces[rank][file] = other.spaces[rank][file];
			}
		}
	}
	
	/**
	 * @param piece
	 * @param rank
	 * @param file
	 * @param player
	 * @return true if the piece was set successfully
	 */
	public boolean setPiece(String piece, int rank, int file, Color player) {
		if (outOfBounds(rank,file))
			return false;
		if (spaces[rank][file] != null)
			return false;
		
		spaces[rank][file] = Piece.stringToPiece(piece,player);
		updatePieces();
		return true;
	}
		
	/**
	 * @param rank
	 * @param file
	 * @return the piece at a given space
	 */
	public Piece getPiece(int rank, int file) {
		return spaces[rank][file];
	}
	
	/**
	 * @param rstart
	 * @param fstart
	 * @param rtarget
	 * @param ftarget
	 * @return true if the move was successful
	 * false otherwise
	 */
	public boolean movePiece(int rstart, int fstart, int rtarget, int ftarget) {
		if (outOfBounds(rstart,fstart) || outOfBounds(rtarget,ftarget))
			return false;
		if (spaces[rstart][fstart] == null)
			return false;
		if (spaces[rstart][fstart].sameOwner(spaces[rtarget][ftarget]))
			return false;
		
		Space target = new Space(rtarget, ftarget);
		
		if (spaces[rstart][fstart].isValidMove(target)) {
			Board copy = new Board(this);
			spaces[rtarget][ftarget] = spaces[rstart][fstart];
			spaces[rstart][fstart] = null;
			updatePieces();
			if (inCheck(spaces[rtarget][ftarget].getColor())) {
				spaces = copy.spaces;
				return false;
			}
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * @param rank
	 * @param file
	 * @return true if the position given is 
	 * not a valid board position.
	 */
	public boolean outOfBounds(int rank, int file) {
		return (rank < 0) || (file < 0) || (rank >= spaces.length) || (file >= spaces[0].length);
	}
	
	/**
	 * We need to maintain possible moves
	 * for each piece to detect game-ending conditions.
	 */
	public void updatePieces() {
		for (int rank = 0; rank < spaces.length; rank++) {
			for (int file = 0; file < spaces[0].length; file++) {
				if (spaces[rank][file] != null) {
					spaces[rank][file].updateMoves(spaces,rank,file);
				}
			}
		}
	}
	
	/**
	 * @return 
	 */
	public boolean inCheck(Color player) {
		Space king = null;
		for (int rank = 0; rank < spaces.length; rank++) {
			for (int file = 0; file < spaces[0].length; file++) {
				if (spaces[rank][file] instanceof King)
					if (spaces[rank][file].getColor() == player)
						king = new Space(rank,file);
			}
		}
		
		if (king == null)
			return false;
		
		Vector<Space> pieceMoves;
		for (int rank = 0; rank < spaces.length; rank++) {
			for (int file = 0; file < spaces[0].length; file++) {
				if (spaces[rank][file] != null) {
					pieceMoves = spaces[rank][file].moves;
					for (int it = 0; it < pieceMoves.size(); it++) {
						if(pieceMoves.elementAt(it).equals(king))
							return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * @param player
	 * @return
	 */
	public boolean inCheckmate(Color player) {
		
		if (inCheck(player)) {
			Board copy;
			Vector<Space> pieceMoves;
	
			for (int rank = 0; rank < spaces.length; rank++) {
				for (int file = 0; file < spaces[0].length; file++) {
					if (spaces[rank][file] != null && spaces[rank][file].getColor() == player) {
						pieceMoves = spaces[rank][file].moves;
						for (int r,f,it = 0; it < pieceMoves.size(); it++) {
							 copy = new Board(this);
							 r = pieceMoves.elementAt(it).getRank();
							 f = pieceMoves.elementAt(it).getFile();
							 if (movePiece(rank,file,r,f))
								 if (!inCheck(player))
									 return false;
							 spaces = copy.spaces;
						}
					}
				}
			}
			
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * @param player
	 * @return
	 */
	public boolean inStalemate(Color player) {
		if (!inCheck(player)) {
			Space king = null;
			for (int rank = 0; rank < spaces.length; rank++) {
				for (int file = 0; file < spaces[0].length; file++) {
					if (spaces[rank][file] instanceof King)
						if (spaces[rank][file].getColor() == player)
							king = new Space(rank,file);
				}
			}
			
			Vector<Space> moves = spaces[king.getFile()][king.getRank()].moves;
			Board copy;

			if (moves == null || moves.isEmpty())
				return true;
			
			for (int it = 0; it < moves.size(); it++) {
				copy = new Board(this);
				int r = moves.elementAt(it).getRank();
				int f = moves.elementAt(it).getFile();
				if (movePiece(king.getFile(),king.getRank(),r,f))
					if (!inCheck(spaces[r][f].color))
						return false;
				spaces = copy.spaces;
			}
			return true;
		}
		return false;
	}
}
