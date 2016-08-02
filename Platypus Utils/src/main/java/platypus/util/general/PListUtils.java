package platypus.util.general;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of methods for sorting and searching lists.
 *
 * @author Jingchen Xu
 */
public class PListUtils {

    private PListUtils() {
    }

    /**
     * Generates a sorted copy of a given list using the quicksort algorithm.
     * The list is sorted in ascending order.
     *
     * @param <T>
     * @param <S>
     * @param list the list to be sort
     * @return a sorted copy of the list
     */
    public static <T extends Comparable<T>> List<T> quickSort(
            List<T> list) {

        if (list == null || list.size() == 0)
            return null;

        List<T> output = new ArrayList<T>();
        output.addAll(list);

        quickSort(output, 0, output.size() - 1);

        return output;
    }

    /**
     * Partially sorts a given list in place using the quicksort algorithm. The
     * elements from index low to high are sorted in ascending order. The
     * remaining elements remain in the order given.
     *
     * @param <T>
     * @param list the list to sort
     * @param low the bottom index of the partition to be sorted (inclusive)
     * @param high the upper index of the partition to be sorted (inclusive)
     */
    public static <T extends Comparable<T>> void quickSort(
            List<T> list, int low, int high) {

        int i = low, j = high;
        T pivot = list.get(low + (high - low) / 2);

        while (i <= j) {

            // make sure elements before pivot are below it
            while (list.get(i).compareTo(pivot) < 0)
                i++;

            // make sure elements after pivot are above it
            while (list.get(j).compareTo(pivot) > 0)
                j--;

            // if not past the pivot, swap and move on
            if (i <= j) {
                swap(list, i, j);
                i++;
                j--;
            }
        }

        // check if there are any lower elements
        if (low < j)
            quickSort(list, low, j);

        // check if there are any higher elements
        if (i < high)
            quickSort(list, i, high);
    }

    /**
     * Swaps two elements of a list in place.
     *
     * @param <T>
     * @param list the list to manipulate
     * @param i the index of the first element to be swapped
     * @param j the index of the second element to be swapped
     */
    public static <T> void swap(List<T> list, int i, int j) {

        T store = list.get(i);
        list.set(i, list.get(j));
        list.set(j, store);
    }

    /**
     * Finds a single index of an element within a list using a binary search.
     * The provided list must be sorted in ascending order.
     *
     * @param list the sorted list to be searched
     * @param query the object to search for
     * @return the index of a matching element in the list. If the query is not
     *         found, -1 is returned instead.
     */
    public static int binSearch(List<Comparable<Object>> list,
            Comparable<Object> query) {
        // TODO
        return -1;
    }

}
