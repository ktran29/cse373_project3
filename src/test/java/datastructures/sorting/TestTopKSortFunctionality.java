/**
 * CSE 373
 * Project 3
 * Kevin Tran and Marcus Deichman
 */

package datastructures.sorting;

import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import misc.BaseTest;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import misc.Searcher;
import org.junit.Test;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestTopKSortFunctionality extends BaseTest {
    @Test(timeout=SECOND)
    public void testSimpleUsage() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }

        IList<Integer> top = Searcher.topKSort(5, list);
        assertEquals(5, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(15 + i, top.get(i));
        }
    }
    
    @Test(timeout=SECOND)
    public void testStringSimpleUsage() {
        IList<String> list = new DoubleLinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");

        IList<String> top = Searcher.topKSort(3, list);
        assertEquals(3, top.size());
        assertEquals("c", top.get(0));
        assertEquals("d", top.get(1));
        assertEquals("e", top.get(2));
    }
    
    
    @Test(timeout=SECOND)
    public void testBackwardsSimpleUsage() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 19; i >= 0; i--) {
            list.add(i);
        }

        IList<Integer> top = Searcher.topKSort(5, list);
        assertEquals(5, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(15 + i, top.get(i));
        }
    }
    
    @Test(timeout=SECOND)
    public void testKGreaterThanSize() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }
    		
        IList<Integer> top = Searcher.topKSort(50, list);
        assertEquals(20, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(i, top.get(i));
        }
    }
    
    @Test(timeout=SECOND)
    public void testEmptyList() {
        IList<Integer> list = new DoubleLinkedList<>();
    		
        IList<Integer> top = Searcher.topKSort(1, list);
        assertTrue(top.isEmpty());
    }
    
    @Test(timeout=SECOND)
    public void testZeroK() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }

        IList<Integer> top = Searcher.topKSort(0, list);
        assertEquals(0, top.size());
    }
    
    @Test(timeout=SECOND)
    public void testNegativeK() {
        IList<Integer> list = new DoubleLinkedList<>();
        try {
            Searcher.topKSort(-1, list);
            fail("K cannot be less than 0");
        } catch (IllegalArgumentException ex) {
            //This is ok: do nothing
        }
    }

}
