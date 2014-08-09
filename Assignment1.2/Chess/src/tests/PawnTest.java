package tests;

import static org.junit.Assert.*;

import java.awt.Color;

import model.Board;

import org.junit.Before;
import org.junit.Test;


public class PawnTest {
	
	Board board;
	
	@Before
	public void setUp() throws Exception {
		board = new Board();
		board.setPiece("Pawn", 1, 1, Color.BLACK);
	}

	@Test
	public void firstMoveTwoSquares() {
		assertTrue("Pawn can move 2 squares vertically on the first move.",board.movePiece(1, 1, 1, 3));
	}
	
	@Test
	public void doubleSpecialMove() {
		board.movePiece(1, 1, 1, 3);
		assertFalse("Pawn cannot move 2 squares forward after the first time.",board.movePiece(1, 3, 1, 5));
	}
	
	@Test
	public void moveBackwards() {
		assertFalse("Pawns can't move backwards.",board.movePiece(1, 1, 1, 0));
	}
	
	@Test
	public void moveHorizontal() {
		assertFalse("Pawns cannot move left.",board.movePiece(1, 1, 0, 1));
		assertFalse("Pawns cannot move right.",board.movePiece(1, 1, 2, 1));
	}
	
	@Test
	public void moveDiagonal() {
		assertFalse("Pawns cannot move diagonally.",board.movePiece(1, 1, 2, 2));
		assertFalse("Pawns cannot move diagonally.",board.movePiece(1, 1, 0, 0));
		assertFalse("Pawns cannot move diagonally.",board.movePiece(1, 1, 0, 2));
		assertFalse("Pawns cannot move diagonally.",board.movePiece(1, 1, 2, 0));
	}
	
	@Test
	public void captureBackwards() {
		board.setPiece("Pawn", 0, 0, Color.WHITE);
		board.setPiece("Pawn", 2, 0, Color.WHITE);
		assertFalse("Pawns cannot capture backwards.",board.movePiece(1, 1, 2, 0));
		assertFalse("Pawns cannot capture backwards.",board.movePiece(1, 1, 0, 0));
	}
	
	@Test
	public void captureForwards() {
		board.setPiece("Pawn", 0, 2, Color.WHITE);
		board.setPiece("Pawn", 2, 2, Color.WHITE);
		assertTrue("Pawn captured forwards.",board.movePiece(1, 1, 0, 2));
		board.setPiece("Pawn", 1, 1, Color.BLACK);
		assertTrue("Pawn captured forwards.",board.movePiece(1, 1, 2, 2));
	}
}
