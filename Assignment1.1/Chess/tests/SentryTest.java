import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;


public class SentryTest {

	Board board;
	
	@Before
	public void setUp() throws Exception {
		board = new Board();
		board.setPiece("Sentry",4,4,Color.WHITE);
	}

	@Test
	public void setSentry() {
		assertTrue("Sentry set on the board.",board.setPiece("Sentry",7,7,Color.WHITE));
		assertNotNull("Sentry is on the board.",board.getPiece(7,7));
	}
	
	@Test
	public void wierdMove() {
		assertFalse("Sentry cannot move in L-shapes.",board.movePiece(4,4,6,5));
	}
	
	@Test
	public void moveOneDiagonal() {
		assertTrue("Sentry can move 1 space up-left.",board.movePiece(4,4,3,3));
		assertTrue("Sentry can move 1 space down-right.",board.movePiece(3,3,4,4));
		assertTrue("Sentry can move 1 space up-right.",board.movePiece(4,4,5,3));
		assertTrue("Sentry can move 1 space down-left.",board.movePiece(5,3,4,4));
	}
	
	@Test
	public void moveTwoDiagonal() {
		assertTrue("Sentry can move 2 spaces up-left.",board.movePiece(4,4,2,2));
		assertTrue("Sentry can move 2 spaces down-right.",board.movePiece(2,2,4,4));
		assertTrue("Sentry can move 2 spaces up-right.",board.movePiece(4,4,6,6));
		assertTrue("Sentry can move 2 spaces down-left.",board.movePiece(6,6,4,4));
	}
	
	@Test
	public void moveOneHorizontal() {
		assertTrue("Sentry can move 1 left.",board.movePiece(4,4,3,4));
		assertTrue("Sentry can move 1 right.",board.movePiece(3,4,4,4));
	}
	
	@Test
	public void moveOneVertical() {
		assertTrue("Sentry can move 1 up.",board.movePiece(4,4,4,3));
		assertTrue("Sentry can move 1 down.",board.movePiece(4,3,4,4));
	}
	
	@Test
	public void moveTwoHorizontal() {
		assertTrue("Sentry can move 2 left.",board.movePiece(4,4,2,4));
		assertTrue("Sentry can move 2 right.",board.movePiece(2,4,4,4));	
	}
	
	@Test
	public void moveTwoVertical() {
			assertTrue("Sentry can move 2 up.",board.movePiece(4,4,4,2));
			assertTrue("Sentry can move 2 down.",board.movePiece(4,2,4,4));
	}
}
