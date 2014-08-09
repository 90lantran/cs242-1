import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;


public class KnightTest {
	
	Board board;
	
	@Before
	public void setUp() throws Exception {
		board = new Board();
		board.setPiece("Knight", 4, 4, Color.WHITE);
	}

	@Test
	public void knightMove() {
		assertTrue("Knight moves up-left.",board.movePiece(4, 4, 3, 2));
		assertTrue("Knight moves down-right.",board.movePiece(3, 2, 4, 4));
		assertTrue("Knight moves down-left.",board.movePiece(4, 4, 5, 6));
		assertTrue("Knight moves up-right.",board.movePiece(5, 6, 4, 4));
		assertTrue("Knight moves left-up.",board.movePiece(4, 4, 2, 3));
		assertTrue("Knight moves right-down.",board.movePiece(2, 3, 4, 4));
		assertTrue("Knight moves right-up.",board.movePiece(4, 4, 6, 3));
		assertTrue("Knight moves left-down.",board.movePiece(6, 3, 4, 4));
	}
	
	@Test 
	public void horizontalMove() {
		assertFalse("Knight cannot move horizontally 1 space right.",board.movePiece(4, 4, 5, 4));
		assertFalse("Knight cannot move horizontally 2 spaces left.",board.movePiece(5, 4, 3, 4));
	}
	
	@Test
	public void verticalMove() {
		assertFalse("Knight cannot mvoe vertically 1 space up.",board.movePiece(4, 4, 4, 3));
		assertFalse("Knight cannot move vertically 2 space down.",board.movePiece(4, 3, 4, 5));
	}
	
	@Test
	public void diagonalMove() {
		assertFalse("Knight cannot move diagonally 1 space.",board.movePiece(4, 4, 3, 3));
		assertFalse("Knight cannot move diagonally 2 spaces.",board.movePiece(3, 3, 1, 5));
	}

	@Test
	public void captureFriendly() {
		board.setPiece("Rook", 3, 2, Color.WHITE);
		assertFalse("Knight cannot capture friendly pieces.",board.movePiece(4, 4, 3, 2));
	}
	
	@Test
	public void captureOpponent() {
		board.setPiece("Rook", 3, 2, Color.BLACK);
		assertTrue("Knight captured enemy piece.",board.movePiece(4, 4, 3, 2));
	}
	
	@Test
	public void jumpFriendly() {
		board.setPiece("Rook", 3, 4, Color.WHITE);
		board.setPiece("Rook", 3, 3, Color.WHITE);
		assertTrue("Knight can jump friendly pieces.",board.movePiece(4, 4, 3, 2));
	}
	
	@Test
	public void jumpOpponent() {
		board.setPiece("Rook", 3, 4, Color.BLACK);
		board.setPiece("Rook", 3, 3, Color.BLACK);
		assertTrue("Knight can jump enemy pieces.",board.movePiece(4, 4, 3, 2));		
	}
}
