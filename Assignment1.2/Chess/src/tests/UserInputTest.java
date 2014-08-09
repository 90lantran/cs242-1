package tests;

import static org.junit.Assert.assertTrue;
import model.Board;

import org.junit.Before;
import org.junit.Test;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;
import controller.UserInput;


public class UserInputTest {

	static Board board;
	
	@Before
	public void setUp() throws Exception {
		board = new Board();
		UserInput.initialize(board);
	}
	
	@Test
	public void PawnsInitialization() {
		for (int i = 0; i < 8; i++) {
			assertTrue("Pawns in second row.",
				board.getPiece(i,1) instanceof Pawn);
			assertTrue("Pawns in sixth row",
				board.getPiece(i,6) instanceof Pawn);
		}
	}
	
	@Test
	public void KingsInitialization() {
		assertTrue("Kings on the top",
			board.getPiece(4,0) instanceof King);
		assertTrue("King on the bottom",
			board.getPiece(4,7) instanceof King);
	}
	
	
	@Test
	public void QueensInitialization() {
		assertTrue("Queen on top.",
			board.getPiece(3,0) instanceof Queen);
		assertTrue("Queen on bottom.",
			board.getPiece(3,7) instanceof Queen);
	}
	
	@Test
	public void BishopInitialization() {
		assertTrue("Bishop on top-left.",
			board.getPiece(2,0) instanceof Bishop);
		assertTrue("Bishop on top-right.",
			board.getPiece(5,0) instanceof Bishop);
		assertTrue("Bishop on bottom-left.",
			board.getPiece(2,7) instanceof Bishop);
		assertTrue("Bishop on bottom-right.",
			board.getPiece(5,7) instanceof Bishop);
	}
	
	@Test
	public void KnightInitialization() {
		assertTrue("Knight on top-left.",
			board.getPiece(1,0) instanceof Knight);
		assertTrue("Knight on top-right.",
			board.getPiece(6,0) instanceof Knight);
		assertTrue("Knight on bottom-left.",
			board.getPiece(1,7) instanceof Knight);
		assertTrue("Knight on bottom-right.",
			board.getPiece(6,7) instanceof Knight);
	}
	
	@Test
	public void RookInitialization() {
		assertTrue("Rook on top-left.",
			board.getPiece(0,0) instanceof Rook);
		assertTrue("Rook on top-right.",
			board.getPiece(7,0) instanceof Rook);
		assertTrue("Rook on bottom-left.", 
			board.getPiece(0,7) instanceof Rook);
		assertTrue("Rook on bottom-right.", 
			board.getPiece(7,7) instanceof Rook);
	}

}
