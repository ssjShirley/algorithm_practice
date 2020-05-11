/* NiceSimulator.java
   CSC 225 - Summer 2018

   An empty shell of the operations needed by the NiceSimulator
   data structure.

   B. Bird - 06/18/2018
*/


import java.io.*;
import java.util.*;


public class NiceSimulator{

	public static final int SIMULATE_IDLE = -2;
	public static final int SIMULATE_NONE_FINISHED = -1;
	public static final int SIZE = 1000000;
	
	private int index = 0;
	private int size;
	private int maxTasks;
	
	private int[][] item;
	private ArrayList<Integer> heap;
	
	
	
	/* Constructor(maxTasks)
	   Instantiate the data structure with the provided maximum 
	   number of tasks. No more than maxTasks different tasks will
	   be simultaneously added to the simulator, and additionally
	  
	   you may assume that all task IDs will be in the range
	     0, 1, ..., maxTasks - 1
	*/
	public NiceSimulator(int maxTasks){
	
		this.maxTasks = maxTasks;
		item = new int[maxTasks][2];
		
		heap = new ArrayList<Integer>(maxTasks+1);
		
		
		for (int i = 0; i <= maxTasks+1; i++){
			heap.add(null);
		}
		
	}
	
	/* taskValid(taskID)
	   Given a task ID, return true if the ID is currently
	   in use by a valid task (i.e. a task with at least 1
	   unit of time remaining) and false otherwise.
	   
	   Note that you should include logic to check whether 
	   the ID is outside the valid range 0, 1, ..., maxTasks - 1
	   of task indices.
	
	*/
	public boolean taskValid(int taskID){
		// item[][1] = timeleft
		if (taskID >= 0 && taskID < maxTasks){
			if (item[taskID][1] >= 1){
				return true;
			}
		}
		return false;
	}
	
	/* getPriority(taskID)
	   Return the current priority value for the provided
	   task ID. 
	
	   You may assume that the task ID provided is valid.
	
	*/
	public int getPriority(int taskID){
		return item[taskID][0]; // item[][0] = priority
	}
	
	
	/* getRemaining(taskID)
	   Given a task ID, return the number of timesteps
	   remaining before the task completes. 
	 
	   You may assume that the task ID provided is valid.
	*/
	public int getRemaining(int taskID){
		return item[taskID][1]; // item[][1] = timeleft
	}
	
	
	/* add(taskID, time_required)
	   Add a task with the provided task ID and time requirement
	   to the system. 
	   The new task will be assigned nice level 0.
	   
	   You may assume that the provided task ID is in
	   the correct range and is not a currently-active task.
	*/
	public void add(int taskID, int time_required){
		//change the heap
		size++; // the last number of items in the heap
		if(size == 1){
			heap.set(size, taskID);
		}else{
			heap.set(size, taskID);
			bubbleUp(size);
     	}
     	//change the 2D array
		item[taskID][0] = 0; //new task priority = 0
		item[taskID][1] = time_required;
	}
	
	public void bubbleUp(int i) {
		int parent = i / 2;//define parent
        while (i != 1 && item[heap.get(i)][0] <= item[heap.get(parent)][0]) {
			if(item[heap.get(i)][0] == item[heap.get(parent)][0]){
				
				if(heap.get(i) > heap.get(parent)){
					break;
				} else {
					swapitem(i, parent);
					i = parent;
					parent = i / 2;
				}
			} else {
        		swapitem(i, parent);
				i = parent;
				parent = i / 2;
			}
     	}
	}
	
	private void swapitem(int i1, int i2) {
        int temp = heap.get(i1);
        heap.set(i1, heap.get(i2));
        heap.set(i2, temp);
    }
	

    
	/* kill(taskID)
	   Delete the task with the provided task ID from the system.
	   
	   You may assume that the provided task ID is in the correct
	   range and is a currently-active task.
	*/
	public void kill(int taskID){
		// change the heap
		int i = heap.indexOf(taskID);
		int x = heap.get(size);	
		heap.set(i, x);
		heap.remove(size);
		size--;
		bubbleDown(i);
		//change the 2D array
		item[taskID][0] = 0;
		item[taskID][1] = 0;
	}
	
	public void bubbleDown(int i){
		if((i*2) > size){
			return;
		}
		while(heap.get(i*2) != null){
			//define smallest child
			int small;
			
			if(heap.get(i*2+1) != null){
				if (item[heap.get(i*2+1)][0] < item[heap.get(i*2)][0]){
					small = i*2+1;
				} else if (item[heap.get(i*2+1)][0] == item[heap.get(i*2)][0]){
					if(heap.get(i*2+1) < heap.get(i*2)){
						small = i*2+1;
					} else {
						small = i*2;
					}
				} else {
					small = i*2;
				}
			} else {
				small = i*2;
			}
			
			if (item[heap.get(i)][0] > item[heap.get(small)][0]) {
				swapitem(i, small);
				i = small;
			} else if (item[heap.get(i)][0] == item[heap.get(small)][0]){
				if (heap.get(i) > heap.get(small)) {
					swapitem(i, small);
					i = small;
				} else {
					break;
				}
			} else {
				break;
			}
			if((i*2) > size){
				break;
			}
		}
	}
			
	
	/* renice(taskID, new_priority)
	   Change the priority of the the provided task ID to the new priority
       value provided. The change must take effect at the next simulate() step.

	   You may assume that the provided task ID is in the correct
	   range and is a currently-active task.
	
	*/
	public void renice(int taskID, int new_priority){
		//System.out.println("yes");
		if (new_priority > item[taskID][0]){
			//System.out.println("a");
			item[taskID][0] = new_priority;
			bubbleDown(heap.indexOf(taskID));
		}
		
		if (new_priority < item[taskID][0]){
			//System.out.println("b");
			item[taskID][0] = new_priority;
			bubbleUp(heap.indexOf(taskID));
		}
		//System.out.println("c");
	}

	
	/* simulate()
	   Run one step of the simulation:
		 - If no tasks are left in the system, the CPU is idle, so return
		   the value SIMULATE_IDLE.
		 - Identify the next task to run based on the criteria given in the
		   specification (tasks with the lowest priority value are ranked first,
		   and if multiple tasks have the lowest priority value, choose the 
		   task with the lowest task ID).
		 - Subtract one from the chosen task's time requirement (since it is
		   being run for one step). If the task now requires 0 units of time,
		   it has finished, so remove it from the system and return its task ID.
		 - If the task did not finish, return SIMULATE_NONE_FINISHED.
	*/
	public int simulate(){
		//System.out.println("a");
		if (size == 0){ 
			//System.out.println("b");
			return SIMULATE_IDLE;
		}
		int task = heap.get(1);
		
		if (item[task][1] > 1){
			//System.out.println("c");
			item[task][1]--;
			return SIMULATE_NONE_FINISHED;
		} else {
			//System.out.println("d");
			kill(task);
			return task;
		}
	}


}
