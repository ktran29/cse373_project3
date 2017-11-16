package datastructures.concrete;

import datastructures.interfaces.IPriorityQueue;
import misc.exceptions.EmptyContainerException;
import misc.exceptions.NotYetImplementedException;

import java.util.Arrays;

/**
 * See IPriorityQueue for details on what each method must do.
 */
public class ArrayHeap<T extends Comparable<T>> implements IPriorityQueue<T> {
    // See spec: you must implement a implement a 4-heap.
    private static final int NUM_CHILDREN = 4;

    // You MUST use this field to store the contents of your heap.
    // You may NOT rename this field: we will be inspecting it within
    // our private tests.
    private T[] heap;
    // Feel free to add more fields and constants.
    private int heapSize;
    
    public ArrayHeap() {
        heap = makeArrayOfT(5);
        heapSize = 0;
    }

    /**
     * This method will return a new, empty array of the given size
     * that can contain elements of type T.
     *
     * Note that each element in the array will initially be null.
     */
    @SuppressWarnings("unchecked")
    private T[] makeArrayOfT(int size) {
        // This helper method is basically the same one we gave you
        // in ArrayDictionary and ChainedHashDictionary.
        //
        // As before, you do not need to understand how this method
        // works, and should not modify it in any way.
        return (T[]) (new Comparable[size]);
    }

    @Override
    public T removeMin() {
    	if (this.size() == 0) {
    		throw new EmptyContainerException();
    	}
        T minValue = heap[0];
        heap[0] = heap[heapSize - 1];
        heap[heapSize - 1] = null;
        heapSize--;
        int parentIndex = 0;
        swapPercolateDown(parentIndex, NUM_CHILDREN);
        return minValue;
    }

    @Override
    public T peekMin() {
    	if (this.size() == 0) {
    		throw new EmptyContainerException();
    	}
        return heap[0];
    }

    @Override
    public void insert(T item) {
    	if (item == null) {
    		throw new IllegalArgumentException();
    	}
    	if (heap[heap.length - 1] != null) { // array is full
    		T[] newHeap = makeArrayOfT(heap.length * 2);
    		for (int i = 0; i < heap.length; i++) {
    			newHeap[i] = heap[i];
    		}
    		heap = newHeap;
    	}
        for (int i = 0; i < heap.length; i++) {
        	if (heap[i] == null) {
        		heap[i] = item;
        		heapSize++;
        		swapHeapNodes(i);
        		i = heap.length;
        	}
        }     
    }

    @Override
    public int size() {
    	return heapSize;
    }
    
    private void swapHeapNodes(int nodeIndex) {
    	int parentIndex = findParentIndex(nodeIndex);
    	while (heap[parentIndex].compareTo(heap[nodeIndex]) > 0) { // parent > child
    		T temp = heap[nodeIndex];
    		heap[nodeIndex] = heap[parentIndex];
    		heap[parentIndex] = temp;
    		nodeIndex = parentIndex;
    		parentIndex = findParentIndex(nodeIndex);
    	}
    }
    
    private int findParentIndex(int index) {
    	if (index == 0) {
    		return 0;
    	}
    	else {
    		if (index % 4 == 0) {
    			index = (index / 4) - 1;
    		}
    		else {
    			index = index / 4;
    		}
    	}
    	return index;
    }
    
    // Swap child node with parent node if node of parentIndex is larger than child node.
    // numChildren represents how many children are possibly available. 
    private void swapPercolateDown(int parentIndex, int numChildren) {
    	int childIndex = (4*parentIndex + 1);
    	if (heap[childIndex] != null) {
        	T smallestChild = heap[childIndex];
            for (int i = 2; i <= numChildren; i++) {
            	int currentIndex = 4*parentIndex + i;
            	if (heap[currentIndex] != null ) {
            		T nextChild = heap[currentIndex];
                	if (smallestChild.compareTo(nextChild) > 0) {
                		smallestChild = nextChild;
                		childIndex = currentIndex;
                	}
            	}
            }
            if (heap[parentIndex].compareTo(smallestChild) > 0) {
            	T temp = heap[parentIndex];
            	heap[parentIndex] = smallestChild;
            	heap[childIndex] = temp;
            	int newIndex = (4*childIndex + 1);
            	if (newIndex < heap.length - 4) {
            		swapPercolateDown(childIndex, 4);
            	} else if (newIndex < heap.length - 3) {
            		swapPercolateDown(childIndex, 3);
            	} else if (newIndex < heap.length - 2) {
            		swapPercolateDown(childIndex, 2);
            	} else if (newIndex < heap.length - 1) {
            		swapPercolateDown(childIndex, 1);
            	}
            }
    	}
    }
}
