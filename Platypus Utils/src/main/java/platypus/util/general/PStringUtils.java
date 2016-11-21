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
     * Formats a string such that it is a positive non-zero integer. Takes the
     * substring of the original string before the first <code>'.'</code>
     * character and removes all non-digit characters from that string. If the
     * resulting string represents a valid integer, it is returned; otherwise,
     * <code>"1"</code> is returned.
     * 
     * @param s
     *        the string to be processed
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
    public static List<Integer> extractIntegers(String s) {
        List<Integer> output = new ArrayList<Integer>();
        int current;
        boolean add;

        for (int loop = 0; loop < s.length(); loop++) {
            current = 0;
            add = false;
            while (Character.isDigit(s.charAt(loop))) {
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
     * Capitalizes the first letters of words in a string. Ignored words can be
     * specified which will not be capitalized unless they are the first word in
     * the string.
     *
     * @param s the string to be formatted
     * @param ignoredWords words to not capitalize
     * @return the formatted version of s
     */
    public static String titleFormat(String s, String... ignoredWords) {
        StringBuilder output = new StringBuilder();
        char[] charArray = s.toCharArray();
        int start = 0, end = 0, copy = 0;
        boolean ignore, firstWord = true;

        while (end < charArray.length) {
            ignore = false;

            // find the start of a word
            for (start = end; start < charArray.length; start++) {
                if (!Character.isWhitespace(charArray[start])) {
                    break;
                }
            }

            // find the end of a word
            for (end = start; end < charArray.length; end++) {
                if (Character.isWhitespace(charArray[end])) {
                    break;
                }
            }

            // filter out ignored words
            String currentWord = s.substring(start, end);
            for (String word : ignoredWords) {
                if (currentWord.equalsIgnoreCase(word)) {
                    ignore = true;
                    break;
                }
            }

            // add to the result
            for (; copy < end; copy++) {
                if ((copy == start && !ignore) || (copy == start && firstWord)) {
                    output.append(Character.toUpperCase(charArray[copy]));
                } else {
                    output.append(Character.toLowerCase(charArray[copy]));
                }
            }

            firstWord = false;
        }

        return output.toString();
    }

    /**
     * Capitalizes the first letters of words in a string. Ignores transitive
     * words.
     *
     * @param s the string to be formatted
     * @return the formatted version of s
     */
    public static String titleFormatIgnoreTransitive(String s) {
        return titleFormat(s, "of", "and", "the", "to", "if", "as", "is", "was");
    }
}
