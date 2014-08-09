package tests;

import static org.junit.Assert.*;

import java.awt.Color;

import model.Board;

import org.junit.*;


public class RookTest {

	static Board board;
		
	@Before
	public void setUp() throws Exception {
		board = new Board();
		board.setPiece("Rook",0,0,Color.WHITE);
	}
	
	@Test
	public void rookMove() {
		assertTrue("Move completed.",board.movePiece(0, 0, 6, 0));
		assertNull("Original piece deleted.",board.getPiece(0, 0));
		assertNotNull("New piece placed.",board.getPiece(6, 0));
	}
	
	@Test
	public void outOfBoundsMove() {
		assertFalse("Move did not complete.",board.movePiece(0, 0, -1, -1));
		assertFalse("Move did not complete.",board.movePiece(0, 0, 9, 9));
	}
	
	@Test
	public void diagonalMove() {
		assertFalse("Move failed.",board.movePiece(0, 0, 6, 6));
	}
	
	@Test
	public void weirdMove() {
		assertFalse("L-shaped path is invalid.",board.movePiece(0, 0, 4, 1));
	}
	
	@Test
	public void captureFriendly() {
		board.setPiece("Rook", 1, 0, Color.WHITE);
		assertFalse("Capture failed.",board.movePiece(0, 0, 1, 0));
	}
	
	@Test
	public void captureOpponent() {
		board.setPiece("Rook", 1, 0, Color.BLACK);
		assertTrue("Capture succeeded.",board.movePiece(0, 0, 1, 0));
	}
	
	@Test
	public void jumpFriendlyPieces() {
		board.setPiece("Rook", 1, 0, Color.WHITE);
		assertFalse("Jumping failed.",board.movePiece(0, 0, 2, 0));
	}
	
	@Test
	public void jumpEnemyPiece() {
		board.setPiece("Rook", 1, 0, Color.BLACK);
		board.setPiece("Rook", 2, 0, Color.BLACK);
		assertFalse("Jump failed.",board.movePiece(0, 0, 2, 0));
	}
}
