package datastructures.sorting;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import misc.BaseTest;
import datastructures.concrete.ArrayHeap;
import datastructures.interfaces.IPriorityQueue;
import misc.exceptions.EmptyContainerException;
import misc.exceptions.NoSuchKeyException;

import org.junit.Test;

import java.util.*;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestArrayHeapFunctionality extends BaseTest {
    protected <T extends Comparable<T>> IPriorityQueue<T> makeInstance() {
        return new ArrayHeap<>();
    }

    @Test(timeout=SECOND)
    public void testBasicSize() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        assertEquals(1, heap.size());
    }
    
    @Test(timeout=SECOND)
    public void testBasicInsertAndPeek() {
    		IPriorityQueue<Integer> heap = this.makeInstance();
    		heap.insert(1);
    		heap.insert(2);
    		heap.insert(3);
    		assertEquals(3, heap.size());
    		assertEquals(1, heap.peekMin());
    }
    
    @Test(timeout=SECOND)
    public void testBasicRemoveAndSize() {
    		IPriorityQueue<Integer> heap = this.makeInstance();
		heap.insert(1);
		heap.insert(2);
		heap.insert(3);
		assertEquals(3, heap.size());
		assertEquals(1, heap.removeMin());
		assertEquals(2, heap.size());
		assertEquals(2, heap.peekMin());
    }
    
    @Test(timeout=SECOND)
    public void testBasicEmpty() {
    		IPriorityQueue<Integer> heap = this.makeInstance();
    		heap.insert(1);
    		heap.insert(2);
    		heap.removeMin();
    		heap.removeMin();
    		assertTrue(heap.isEmpty());
    }
    
    @Test(timeout=10 * SECOND)
    public void testInsertAndRemoveMany() {
    		IPriorityQueue<Integer> heap = this.makeInstance();
    		int cap = 5000;
    		
    		for (int i = cap; i > 0; i--) {
    			heap.insert(i);
    			assertEquals(i, heap.peekMin());
    			assertEquals(cap - i, heap.size());
    		}
    		
    		assertEquals(cap, heap.size());
    		
    		for (int i = 1; i <= cap; i++) {
    			assertEquals(i, heap.peekMin());
    			heap.removeMin();
    			assertEquals(cap - i, heap.size());
    		}
    		
    		assertTrue(heap.isEmpty());
    }
    
    @Test(timeout=SECOND)
    public void testNullEntry() {
    		IPriorityQueue<Integer> heap = this.makeInstance();
    		try {
    			heap.insert(null);
    			fail("Expected something idk");
    		} catch (NullPointerException ex) {
    			//This is ok: do nothing
    		}
    }
    
}
