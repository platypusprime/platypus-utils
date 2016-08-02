package platypus.util.general;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of methods for processing strings.
 * 
 * @author Jingchen Xu
 */
public class PStringUtils {

    private PStringUtils() {}

    /**
     * Returns the substring between two boundary strings.
     * 
     * @param s the full, original string
     * @param start the beginning string
     * @param end the ending string
     * @param firstStart if true the method begins from the first instance of
     *            <code>start</code> in <code>s</code>, otherwise the method
     *            searches for the last instance of <code>start</code>
     * @return the substring between the two boundary strings, if they are
     *         valid; returns <code>null</code> if either of the boundary
     *         strings does not exist in <code>s</code> or if the index of
     *         <code>start</code> is greater than that of <code>end</code>
     */
    public static String substring(String s, String start, String end) {

        String output = null;
        if (s.contains(start) && s.contains(end) && s.length() > start.length()
                && s.length() > end.length()) {
            int beginIndex = s.indexOf(start) + start.length();
            int endIndex = s.indexOf(end, beginIndex);

            if (endIndex != -1 && endIndex > beginIndex) {
                output = s.substring(beginIndex, endIndex);
            }
        }

        return output;
    }

    /**
     * Formats a string such that it is a positive non-zero integer. Takes the
     * substring of the original string before the first <code>'.'</code>
     * character and removes all non-digit characters from that string. If the
     * resulting string represents a valid integer, it is returned; otherwise,
     * <code>"1"</code> is returned.
     * 
     * @param s
     *            the string to be processed
     * @return the formatted string
     */
    public static String positiveIntValidation(String s) {

        String content = "0" + s.split("\\.")[0].replaceAll("[\\D]", "");

        int value = Integer.parseInt(content);

        if (value > 0) {
            return Integer.toString(value);
        } else {
            return "1";
        }
    }

    /**
     * Finds all numbers in a given string and returns them in an Integer list.
     * 
     * @param s the string to extract integers from
     * @return a List containing all integers found in the
     *         string; consecutive digits are returned as a single integer
     */
    public List<Integer> extractIntegers(String s) {
        List<Integer> output = new ArrayList<Integer>();
        int current;
        boolean add;

        for (int loop = 0; loop < s.length(); loop++) {
            current = 0;
            add = false;
            while (isDigit(Character.toString(s.charAt(loop)))) {
                current = current * 10
                        + Integer.parseInt(Character.toString(s.charAt(loop)));
                loop++;
                add = true;
            }
            if (add)
                output.add(current);
        }
        return output;
    }

    /**
     * Determines whether a string represents a single digit.
     * 
     * @param s the string to be evaluated
     * @return true if s is a single numerical character, false otherwise
     */
    public boolean isDigit(String s) {
        if (s.length() == 1 && s.matches("\\d"))
            return true;
        return false;
    }

    /**
     * Capitalizes the first letters of words in a string. Transitive words such
     * as 'of' or 'and' will not be capitalized.
     * 
     * @param s the string to be formatted
     * @return the formatted version of s
     */
    public String titleFormat(String s) {
        String output = "";
        boolean cap = true;

        for (int loop = 0; loop < s.length(); loop++) {

            if (loop != 0) {
                // TODO substring
                if (cap && loop < s.length() - 1)
                    cap = !(s.charAt(loop) == 'o' && s.charAt(loop + 1) == 'f')
                            && !(s.charAt(loop) == 'i'
                                    && s.charAt(loop + 1) == 's');
                if (cap && loop < s.length() - 2)
                    cap = !(s.charAt(loop) == 't' && s.charAt(loop + 1) == 'h'
                            && s.charAt(loop + 2) == 'e')
                            && !(s.charAt(loop) == 'a'
                                    && s.charAt(loop + 1) == 'n'
                                    && s.charAt(loop + 2) == 'd');
            }
            if (cap) {
                output = output + Character.toString(s.charAt(loop))
                    .toUpperCase();
                cap = false;
            } else
                output = output + Character.toString(s.charAt(loop));
            if (s.charAt(loop) == ' ')
                cap = true;
        }
        return output;
    }

    // TODO
    private static String[][] HTML_CODES = { { "", "" }, { "", "" } };

    public static String convertHTMLCodes(String s) {

        String output = s;

        for (String[] code : HTML_CODES) {
            output = output.replaceAll(code[0], code[1]);
        }

        return output;
    }

    private static String[][] URL_CODES = { { "%20", " " }, { "%21", "!" },
            { "%22", "\"" },
            { "%23", "#" }, { "%24", "$" }, { "%25", "%" }, { "%26", "&" },
            { "%27", "'" },
            { "%28", "(" }, { "%29", ")" }, { "%2a", "*" }, { "%2b", "+" },
            { "%2c", "," },
            { "%2d", "-" }, { "%2e", "." }, { "%2f", "/" }, { "%3a", ":" },
            { "%3b", ";" },
            { "%3c", "<" }, { "%3d", "=" }, { "%3e", ">" }, { "%3f", "?" },
            { "%40", "@" },
            { "%5b", "[" }, { "%5c", "\\" }, { "%5d", "]" }, { "%5e", "^" },
            { "%5f", "_" },
            { "%60", "`" }, { "%7b", "{" }, { "%7c", "|" }, { "%7d", "}" },
            { "%7e", "~" } };

    public static String convertURLCodes(String s) {

        String output = s;

        for (String[] code : URL_CODES) {
            output = output.replaceAll("(?i)" + code[0], code[1]);
        }

        return output;
    }
}
