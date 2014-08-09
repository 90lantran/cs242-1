package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.Color;

import model.Chess;

import org.junit.Test;

public class ChessTest {
			
	@Test
	public void constructor() {
		Chess chess= new Chess();
		assertNotNull(chess);
	}
			
	@Test
	public void startedGame() {
		String [] testNames = {"leonardo","decaprio"};
		Chess.setPlayerNames(testNames);
		assertEquals(Color.WHITE,Chess.getTurn());
	}
	
	@Test
	public void setScoreList() {
		String [] testNames = {"lawrence","fishburne"};
		Chess.setPlayerNames(testNames);
		assertEquals("[Player] \t\t [Score]\nlawrence : 0\nfishburne : 0\n",
					 Chess.getScoreList());
	}
}
