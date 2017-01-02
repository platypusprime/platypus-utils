package platypus.util.general;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * Tests list utils class.
 * 
 * @author Jingchen Xu
 */
public class ListTest {
    
    private static final Logger LOG = LogManager.getLogger(ListTest.class);

  
    /**
     * Tests quick sort method on a list of random list of integers against the
     * native sort implementation.
     */
    @Test
    public void testQuickSort() {
        
        LOG.info("Testing quick sort");
        
        // generate list of random integers
        ArrayList<Integer> list = new ArrayList<Integer>();
        Random rand = new Random();
        for (int i = 0; i < 100000; i++) {
            list.add(rand.nextInt(10000));
        }
        
        LOG.debug("Generated 100000 random integers with max val 10000");

        // make a copy of the list
        List<Integer> sList = new ArrayList<Integer>();
        sList.addAll(list);

        LOG.debug("Sorting with stock sort");
        Collections.sort(list);
        LOG.debug("Finished sorting with stock sort");

        LOG.debug("Sorting with PListUtils.quickSort()");
        sList = PListUtils.quickSort(sList);
        LOG.debug("Finished sorting with PListUtils.quickSort()");
        
        assertEquals(list, sList);
    }

}
