import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;


public class RockTest {

	Board board;
	
	@Before
	public void setUp() throws Exception {
		board = new Board();
	}

	@Test
	public void setRock() {
		assertTrue("Rock was set on the board.",board.setPiece("Rock",5,5,Color.WHITE));
		assertNotNull("Rock is on the board.",board.getPiece(5,5));
	}
	
	@Test
	public void cantMove() {
		board.setPiece("Rock",5,5,Color.WHITE);
		assertFalse("Rocks can't move, silly.",board.movePiece(4,4,4,5));
	}

}
