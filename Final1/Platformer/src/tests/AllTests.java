package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ BlockTest.class ,
				InteractiveBlockTest.class ,
				MovingBlockTest.class ,
				StaticBlockTest.class ,
				LevelTest.class,
				UserInputTest.class })
public class AllTests {
}
