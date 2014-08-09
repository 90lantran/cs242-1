package tests;

import java.io.File;

import model.StaticBlock;

import org.junit.Test;
import static org.junit.Assert.*;

public class StaticBlockTest {
	@Test
	public void constructor() {
		String file = new File("").getAbsolutePath() + "/images/backgrounds/clouds.png";
		StaticBlock block = new StaticBlock(1234,1234,"TEST",file);
		assertEquals(block.getType(),"TEST");
	}
}
