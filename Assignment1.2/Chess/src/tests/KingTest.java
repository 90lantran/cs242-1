package tests;

import static org.junit.Assert.*;

import java.awt.Color;

import model.Board;

import org.junit.Before;
import org.junit.Test;


public class KingTest {
	
	static Board board;
	
	@Before
	public void setUp() throws Exception {
		board = new Board();
		board.setPiece("King", 4, 4, Color.WHITE);
	}

	@Test
	public void moveVertical() {
		assertTrue("King can move up.",board.movePiece(4, 4, 4, 3));
		assertTrue("King can move down.",board.movePiece(4, 3, 4, 4));
	}
	
	@Test
	public void moveHorizontal() {
		assertTrue("King can move left.",board.movePiece(4, 4, 3, 4));
		assertTrue("King can move right.",board.movePiece(3, 4, 4, 4));
	}
	
	@Test
	public void moveDiagonal() {
		assertTrue("King moves left-up.",board.movePiece(4, 4, 3, 3));
		assertTrue("King moves right-up.",board.movePiece(3, 3, 4, 2));
		assertTrue("King moves left-down.",board.movePiece(4, 2, 3, 3));
		assertTrue("King moves right-down.",board.movePiece(3, 3, 4, 4));
	}
	
	@Test
	public void moveMoreSpaces() {
		assertFalse("King cannot move 2 spaces diagonally.",board.movePiece(4, 4, 6, 6));
		assertFalse("King cannot move 2 spaces horizontally.",board.movePiece(6, 6, 4, 6));
		assertFalse("King cannot move 2 spaces vertically.",board.movePiece(4, 6, 4, 8));
	}
	
	@Test
	public void captureFriendly() {
		board.setPiece("Rook", 3, 3, Color.WHITE);
		assertFalse("King cannot capture a friendly piece.",board.movePiece(4, 4, 3, 3));
	}
	
	@Test
	public void captureEnemy() {
		board.setPiece("Rook", 3, 3, Color.BLACK);
		assertTrue("King captured an enemy piece.",board.movePiece(4, 4, 3, 3));
		assertSame("King moved to enemy square.",Color.WHITE,board.getPiece(3, 3).getColor());
	}
}
