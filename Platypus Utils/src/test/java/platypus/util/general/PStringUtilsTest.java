package platypus.util.general;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

/**
 * Test class for PStringUtils.
 * 
 * @author Jingchen Xu
 */
@RunWith(Parameterized.class)
public class PStringUtilsTest {

    private enum Type {
        POSINT_VALID, EXTRACT_INT, TITLE_CASE
    }

    /**
     * Produces the parameters for this test.
     * 
     * @return the parameters
     */
    @Parameters(name = "{0}:{1}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { Type.POSINT_VALID, "as09d87189z/;xclv/4rnmkl;asdivzc9ek92.9012dasd",
                        "9871894992" },
                { Type.POSINT_VALID, ".390340", "1" },
                { Type.POSINT_VALID, "", "1" },
                { Type.POSINT_VALID, null, "1" },
                { Type.EXTRACT_INT, "as09d87123489z/;xclv/4rnmkl;asdivzc9ek929012dasd",
                        Arrays.asList(9, 87123489, 4, 9, 929012) },
                { Type.EXTRACT_INT, null, null },
                { Type.EXTRACT_INT, "", Collections.emptyList() },
                { Type.TITLE_CASE, "the tortoise and the hare", "The Tortoise and the Hare" },
                { Type.TITLE_CASE, "", "" },
                { Type.TITLE_CASE, null, null }
        });
    }

    /**
     * The test type
     */
    @Parameter(value = 0)
    public Type type;

    /**
     * The string output
     */
    @Parameter(value = 1)
    public String input;

    /**
     * The expected output
     */
    @Parameter(value = 2)
    public Object expected;

    /**
     * Tests positive integer validation.
     */
    @Test
    public void testPosIntValidiation() {
        Assume.assumeTrue(type == Type.POSINT_VALID);
        assertEquals(expected, PStringUtils.positiveIntValidation(input));
    }

    /**
     * Tests integer extraction.
     */
    @Test
    public void testExtractIntegers() {
        Assume.assumeTrue(type == Type.EXTRACT_INT);
        assertEquals(expected, PStringUtils.extractIntegers(input));
    }

    /**
     * Tests title-case formatting using the variant ignoring transitive words.
     */
    @Test
    public void testTitleCaseIgnoreTransitive() {
        Assume.assumeTrue(type == Type.TITLE_CASE);
        assertEquals(expected, PStringUtils.titleFormatIgnoreTransitive(input));
    }

}
