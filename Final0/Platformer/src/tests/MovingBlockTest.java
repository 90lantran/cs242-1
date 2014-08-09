package tests;

import static org.junit.Assert.*;

import java.io.File;

import model.MovingBlock;
import org.junit.Test;

public class MovingBlockTest {

	@Test
	public void constructor() {
		String file = new File("").getAbsolutePath() + "/images/backgrounds/clouds.png";
		MovingBlock block = new MovingBlock(1234,1234,"TEST",file);
		assertEquals(block.getType(),"TEST");
	}

}
