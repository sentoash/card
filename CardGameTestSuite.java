import org.junit.runner.RunWith;
import org.junit.runners.Suite;

// Run all tests for the package

@RunWith(Suite.class)
@Suite.SuiteClasses({PlayerTest.class,
        CardTest.class, CardGameTest.class})
public class CardGameTestSuite {
}