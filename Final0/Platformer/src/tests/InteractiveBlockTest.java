package tests;

import static org.junit.Assert.assertEquals;

import java.io.File;

import model.Block;
import model.InteractiveBlock;

import org.junit.Test;

public class InteractiveBlockTest {

	@Test
	public void testClassExtension() {
		InteractiveBlock ib = new InteractiveBlock();
		Block b = new InteractiveBlock();
		assertEquals(b.getClass(),ib.getClass());
	}

	@Test
	public void constructor() {
		String file = new File("").getAbsolutePath() + "/images/backgrounds/clouds.png";
		InteractiveBlock block = new InteractiveBlock(1234,1234,"TEST",file);
		assertEquals(block.getType(),"TEST");
	}
}
