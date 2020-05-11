/**
 * Heap.java
 * Provide the necessary header information here, 
 * making sure to delete these lines.
 */

import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

// This is the beginning of the source code to help you get started.
// Do not change the following:

public class Heap<E extends Comparable<E>> {

	private ArrayList<E> heap;
	private int size;
	private static final int CAPACITY = 100;

	/**
	 * Creates an empty heap.
	 */
	public Heap() {
		heap = new ArrayList<E>(CAPACITY);
		for (int i=0; i<CAPACITY; i++) {
			heap.add(null);
		}
	} 

// Complete the rest below:
	
	public boolean isEmpty(){
		return size == 0;
	}
	
	public int size(){
		return size;
	}
	
	public void insert(E element){
		heap.set(size, element);
		size++;
		int place = size - 1;
		int parent = (place - 1)/2;
		while ((parent >= 0) && (heap.get(place).compareTo(heap.get(parent)))< 0){
			E temp = heap.get(parent);
			heap.set(parent, heap.get(place));
			heap.set(place, temp);
			place = parent;
			parent = (place - 1)/2;
		}
	}
	
	public E peek(){
		return heap.get(0);
	}
	
	public E getRootItem()
				throws NoSuchElementException {
		E rootItem = null;
		int loc = size - 1;
		if (!isEmpty()){
			rootItem = heap.get(0);
			heap.set(0, heap.get(loc));
			heap.remove(loc);
			size = size - 1;
			Rebuild(0);
			return rootItem;
		} else {
			throw new NoSuchElementException();
		}
		
	}
	
	protected void Rebuild(int root){
  		int child=2*root+1;
  		if(child<size){
   			int rightChild=child+1;
   			if((rightChild<size)&&(heap.get(rightChild).compareTo(heap.get(child))<0)){
   				child=rightChild;
  			}
  			if((heap.get(root).compareTo(heap.get(child)))>0){
    			E temp=heap.get(root);
    			heap.set(root, heap.get(child));
    			heap.set(child,temp);
    			Rebuild(child);
  	 		}
  		}
 	}
 	
	public static void main(String[] args){
	
	}
}