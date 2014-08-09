import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Vector;

import javax.swing.JPanel;


public class Board extends JPanel {
	private static final long serialVersionUID = 9195029973755808176L;
	private Piece [][] spaces;
	
	public Board() {
		spaces = new Piece[8][8];
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		paintChessPieces(g);
	}
	
	private void paintChessPieces(Graphics g) {
		int tile_width = 72;
		int tile_height = 75;
		for (int rank = 0; rank < spaces.length; rank++) {
			for (int file = 0; file < spaces[0].length; file++) {
				boolean blacksquare = (rank % 2 == 1) ^ (file % 2 == 1);
				int xpos = tile_width*rank;
				int ypos = tile_height*file;
				
				if (blacksquare) 
					g.setColor(Color.BLACK);
				else
					g.setColor(Color.WHITE);
			
				g.fillRect(xpos,ypos,xpos+tile_width,ypos+tile_width);
				
				if (spaces[rank][file] != null) {
					BufferedImage piece;
					piece = spaces[rank][file].getPieceImage();
					g.drawImage(piece,tile_width*rank,tile_height*file,null);
				}
			}
		}
	}
	
	public Board(Board other) {
		if (other == null) {
			spaces = new Piece[8][8];
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
		
		Piece newPiece = Piece.stringToPiece(piece,player);
		
		if (newPiece == null)
			return false;
		
		spaces[rank][file] = newPiece;
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
		
		Piece start = spaces[rstart][fstart];
		Piece target = spaces[rtarget][ftarget];
		
		if (start == null)
			return false;
		if (start.sameOwner(target))
			return false;
		
		Space attempt = new Space(rtarget, ftarget);
		
		if (start.isValidMove(attempt)) {
			Board copy = new Board(this);
			spaces[rtarget][ftarget] = start;
			spaces[rstart][fstart] = null;
			updatePieces();
			if (inCheck(start.getColor())) {
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
		Space king = findKing(player);
		
		if (king == null)
			return false;
		
		Piece piece;
		for (int rank = 0; rank < spaces.length; rank++) {
			for (int file = 0; file < spaces[0].length; file++) {
				piece = spaces[rank][file];
				if (piece != null && piece.canCapture(king)) {
					return true;
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
		
		if (!inCheck(player))
			return false;
		
		for (int rank = 0; rank < spaces.length; rank++) {
			for (int file = 0; file < spaces[0].length; file++) {
				Piece piece = spaces[rank][file];
				if (piece != null && 
					piece.sameOwner(player) &&
					hasMovesLeft(player, piece.moves, rank, file))
					 return false;
			}
		}
		
		return true;
		
	}

	/**
	 * @param player
	 * @param pieceMoves
	 * @param rank
	 * @param file
	 * @return true if a piece at (rank,file) has available moves
	 */
	private boolean hasMovesLeft(Color player, Vector<Space> moves, int rank, int file) {
		Board copy;
		for (int r,f,it = 0; it < moves.size(); it++) {
			 copy = new Board(this);
			 r = moves.elementAt(it).rank();
			 f = moves.elementAt(it).file();
			 if (movePiece(rank,file,r,f))
					 return true;
			 spaces = copy.spaces;
		}
		
		return false;
	}
	
	/**
	 * @param player
	 * @return true if in stalemate
	 */
	public boolean inStalemate(Color player) {
		if (!inCheck(player)) {
			Space king = findKing(player);
			
			if (king == null)
				return false;
			
			Vector<Space> moves = spaces[king.rank()][king.file()].moves;
			
			if (hasMovesLeft(player,moves,king.rank(),king.file()))
				return false;
			
			return true;
		}
		return false;
	}

	/**
	 * @param player
	 * @param king
	 * @return the space the player's king  is on
	 */
	private Space findKing(Color player) {
		Space king = null;
		for (int rank = 0; rank < spaces.length; rank++) {
			for (int file = 0; file < spaces[0].length; file++) {
				if (spaces[rank][file] instanceof King) {
					if (spaces[rank][file].getColor() == player)
						king = new Space(rank,file);
				}
			}
		}
		return king;
	}
}
