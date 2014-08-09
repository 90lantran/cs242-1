package tests;

import static org.junit.Assert.assertEquals;

import java.io.File;

import model.BlockType;
import model.StaticBlock;

import org.junit.Test;

public class StaticBlockTest {
	@Test
	public void constructor() {
		String file = new File("").getAbsolutePath() + "/images/backgrounds/clouds.jpg";
		StaticBlock block = new StaticBlock(1234,1234,BlockType.BACKGROUND,file);
		assertEquals(block.getType(),BlockType.BACKGROUND);
	}
}
