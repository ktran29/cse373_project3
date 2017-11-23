/**
 * CSE 373
 * Project 3
 * Kevin Tran and Marcus Deichman
 */

package datastructures.sorting;

import misc.BaseTest;
import misc.Searcher;

import org.junit.Test;

import datastructures.concrete.ArrayHeap;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import datastructures.interfaces.IPriorityQueue;

import static org.junit.Assert.assertTrue;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestSortingStress extends BaseTest {
    protected <T extends Comparable<T>> IPriorityQueue<T> makeInstance() {
        return new ArrayHeap<>();
    }
    
    @Test(timeout=10 * SECOND)
    public void testHeapInsertAndRemoveMany() {
    	IPriorityQueue<Integer> heap = this.makeInstance();
        int cap = 1000000;
		
        for (int i = 1; i <= cap; i++) {
            heap.insert(i);
            assertEquals(1, heap.peekMin());
            assertEquals(i, heap.size());
        }
		
        assertEquals(cap, heap.size());
		
        for (int i = 1; i <= cap; i++) {
            assertEquals(i, heap.peekMin());
            heap.removeMin();
            assertEquals(cap - i, heap.size());
        }
		
        assertTrue(heap.isEmpty());
    }
    
    @Test(timeout=10 * SECOND)
    public void testHeapInsertAndRemoveOutOfOrderMany() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        int cap = 1000000;
    		
        for (int i = cap; i > 0; i--) {
            heap.insert(i);
            assertEquals(i, heap.peekMin());
            assertEquals(cap - (i - 1), heap.size());
        }
    		
        assertEquals(cap, heap.size());
    		
        for (int i = 1; i <= cap; i++) {
            assertEquals(i, heap.peekMin());
            heap.removeMin();
            assertEquals(cap - i, heap.size());
        }
    		
        assertTrue(heap.isEmpty());
    }
    
    @Test(timeout=15 * SECOND)
    public void testLargeKManyEntries() {
        int cap = 100000;
        IList<Integer> list = new DoubleLinkedList<>();
        
        for (int i = 0; i < cap; i++) {
            list.add(i);
        }
        
        IList<Integer> top = Searcher.topKSort(99995, list);
        for (int i = 0; i < top.size(); i++) {
            assertEquals(5 + i, top.get(i));
        }
    }
    
    @Test(timeout=15 * SECOND)
    public void testBackwardsLargeKManyEntries() {
        int cap = 100000;
        IList<Integer> list = new DoubleLinkedList<>();
        
        for (int i = cap - 1; i >= 0; i--) {
            list.add(i);
        }
        
        IList<Integer> top = Searcher.topKSort(99995, list);
        for (int i = 0; i < top.size(); i++) {
            assertEquals(5 + i, top.get(i));
        }
    }
    
    @Test(timeout=SECOND)
    public void testSmallKManyEntries() {
        int cap = 500000;
        IList<Integer> list = new DoubleLinkedList<>();
        
        for (int i = 0; i < cap; i++) {
            list.add(i);
        }
        
        IList<Integer> top = Searcher.topKSort(5, list);
        for (int i = 0; i < top.size(); i++) {
            assertEquals(499995 + i, top.get(i));
        }
    }
    
    @Test(timeout=SECOND)
    public void testStringSmallKManyEntries() {
        int cap = 500000;
        IList<String> list = new DoubleLinkedList<>();
        
        for (int i = 0; i < cap; i++) {
            list.add("a" + i);
        }
        
        IList<String> top = Searcher.topKSort(5, list);
        for (int i = 0; i < top.size(); i++) {
            assertEquals("a" + (99995 + i), top.get(i));
        }
    }
    
    @Test(timeout=10 * SECOND)
    public void testMediumKManyEntries() {
        int cap = 100000;
        IList<Integer> list = new DoubleLinkedList<>();
        
        for (int i = 0; i < cap; i++) {
            list.add(i);
        }
        
        IList<Integer> top = Searcher.topKSort(cap/2, list);
        for (int i = 0; i < top.size(); i++) {
            assertEquals(cap/2 + i, top.get(i));
        }
    }
    
    @Test(timeout=15 * SECOND)
    public void testManyIdenticalEntries() {
        int cap = 200000;
        IList<Integer> list = new DoubleLinkedList<>();
        
        for (int i = 0; i < cap; i++) {
            list.add(1);
        }
        
        IList<Integer> top = Searcher.topKSort(cap/2, list);
        for (int i = 0; i < top.size(); i++) {
            assertEquals(1, top.get(i));
        }
    }
    
    @Test(timeout=15 * SECOND)
    public void testStressKGreaterThanSize() {
        int cap = 100000;
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < cap; i++) {
            list.add(i);
        }
    		
        IList<Integer> top = Searcher.topKSort(cap + 1, list);
        assertEquals(cap, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(i, top.get(i));
        }
    }
    
    
}
