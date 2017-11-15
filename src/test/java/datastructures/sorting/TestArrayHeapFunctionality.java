package datastructures.sorting;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import misc.BaseTest;
import datastructures.concrete.ArrayHeap;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;
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
    public void testAddDuplicateNodesBasic() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(1);
        heap.insert(1);
        heap.insert(4);
        heap.insert(4);
        assertEquals(4, heap.size());
        assertEquals(1, heap.peekMin());
    }
    
    @Test(timeout=SECOND)
    public void testAddDuplicateNodesPercolate() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        heap.insert(4);
        heap.insert(4);
        heap.insert(8);
        heap.insert(2);
        heap.insert(2);
        assertEquals(6, heap.size());
        assertEquals(2, heap.removeMin());
        assertEquals(2, heap.peekMin());
    }
    
    @Test(timeout=SECOND)
    public void testAddAndRemoveOutOfOrder() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(6);
        heap.insert(4);
        heap.insert(3);
        heap.insert(2);
        heap.insert(1);
        assertEquals(5, heap.size());
        assertEquals(1, heap.removeMin());
        assertEquals(2, heap.removeMin());
        assertEquals(3, heap.removeMin());
        assertEquals(4, heap.removeMin());
        assertEquals(6, heap.removeMin());
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
    
    @Test(timeout=SECOND)
    public void testNullEntry() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
            heap.insert(null);
            fail("Expected something idk");
        } catch (IllegalArgumentException ex) {
        		//This is ok: do nothing
        }
    }
}
