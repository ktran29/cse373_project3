package datastructures.sorting;

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
    public void testBackwardsKGreaterThanSize() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 19; i >= 0; i--) {
            list.add(i);
        }
		
        IList<Integer> top = Searcher.topKSort(50, list);
        assertEquals(20, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(i, top.get(i));
        }
    }
    
    @Test(timeout=SECOND)
    public void testSmallKManyEntries() {
        int cap = 100000;
        IList<Integer> list = new DoubleLinkedList<>();
        
        for (int i = 0; i < cap; i++) {
            list.add(i);
        }
        
        IList<Integer> top = Searcher.topKSort(5, list);
        for (int i = 0; i < top.size(); i++) {
            assertEquals(99995 + i, top.get(i));
        }
    }

}
