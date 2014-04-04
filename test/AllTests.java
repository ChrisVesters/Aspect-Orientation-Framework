import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import aofC.SmallCTest;
import aofDot.DotTest;


@RunWith(Suite.class)
@SuiteClasses({SmallCTest.class, DotTest.class})
public class AllTests {

}
