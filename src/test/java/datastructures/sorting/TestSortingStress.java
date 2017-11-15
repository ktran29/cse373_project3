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
        int cap = 100000;
		
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
        int cap = 100000;
    		
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
}
