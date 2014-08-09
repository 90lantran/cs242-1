import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;


public class PieceTest {

	static Board board;
	
	@Before
	public void setUp() throws Exception {
		board = new Board();
	}
	
	@Test 
	public void outofBoundsSetPiece() {
		assertFalse("Out-of-bounds set attempted.",board.setPiece("Rook",-1,0,Color.WHITE));
		assertFalse("Out-of-bounds set attempted.",board.setPiece("Rook",0,-1,Color.WHITE));
		assertFalse("Out-of-bounds set attempted.",board.setPiece("Rook",0,100,Color.WHITE));
		assertFalse("Out-of-bounds set attempted.",board.setPiece("Rook",100,0,Color.WHITE));
	}
	
	@Test
	public void badNameSetPiece() {
		assertFalse("Bad string given to setPiece.",board.setPiece("asjfksjlkd", 1, 1, Color.WHITE));
	}
	
	@Test
	public void validSetPiece() {
		assertTrue(board.setPiece("Rook", 0, 0, Color.WHITE));
		assertNotNull(board.getPiece(0,0));
	}
	
	
	@Test
	public void doubleSetPiece() {
		board.setPiece("Rook", 0, 0, Color.WHITE);
		assertFalse("Double set failed",board.setPiece("Rook", 0, 0, Color.WHITE));
	}
	
	@Test 
	public void emptyMove() {
		assertFalse("Both squares empty.",board.movePiece(0,0,1,1));
	}
	
	@Test
	public void badMove() {
		assertFalse("Invalid move failed.",board.movePiece(-1,0,0,0));
	}
	
	@Test
	public void emptyPieceMove() {
		Piece piece = new Rook();
		assertFalse("Invalid move on unplaced piece.",piece.isValidMove(new Space(-1,-1)));
	}
}
