package platypus.util.general;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test suite for PStringUtils.
 * 
 * @author Jingchen Xu
 */
public class PStringUtilsTest {

    /**
     * Tests a regular use case for
     * {@link platypus.util.general.PStringUtils#titleFormatIgnoreTransitive
     * titleFormatIgnoreTransitive()}
     */
    @Test
    public void testTitleCaseIgnoreTransitive() {
        String input = "the tortoise and the hare";
        String output = PStringUtils.titleFormatIgnoreTransitive(input);
        String expectedOutput = "The Tortoise and the Hare";
        assertEquals(expectedOutput, output);

    }

}
