package platypus.util.general;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.Test;

/**
 * Tests list utils class.
 * 
 * @author Jingchen Xu
 */
public class ListTest {

    /**
     * Tests quick sort method on a list of random list of integers against the
     * native sort implementation.
     */
    @Test
    public void testQuickSort() {

        // generate list of random integers
        ArrayList<Integer> list = new ArrayList<Integer>();
        Random rand = new Random();
        for (int i = 0; i < 100000; i++) {
            list.add(rand.nextInt(10000));
        }

        // make a copy of the list
        List<Integer> sList = new ArrayList<Integer>();
        sList.addAll(list);

        // use stock sort
        Collections.sort(list);

        assertEquals(list, PListUtils.quickSort(sList));
    }

}
