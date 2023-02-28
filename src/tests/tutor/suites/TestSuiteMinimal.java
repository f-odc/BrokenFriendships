package tests.tutor.suites;

import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import tests.categories.Minimal;
import org.junit.runners.Suite;
import tests.tutor.testcases.BoardTest;
import tests.tutor.testcases.DiceTest;
import tests.tutor.testcases.PlayerTest;
import tests.tutor.testcases.TemplateTest;

@RunWith(Categories.class)
@Categories.IncludeCategory(Minimal.class)
@Suite.SuiteClasses({TemplateTest.class, DiceTest.class, PlayerTest.class, BoardTest.class})
public class TestSuiteMinimal {
    // run only tests classified as minimal
}
