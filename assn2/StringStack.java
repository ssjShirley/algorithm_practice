// using a reference-based list as its main data structure

public class StringStack {
	private Node top;
	
	// initializes a stack that has no elements
	public StringStack(){
		top = null;
	} // end default constructor
	
	// True if the stack is empty
	public boolean isEmpty(){
		return top == null;
	} // end isEmpty
	
	// inserts an element onto the top of the stack
	public void push(String s){
		top = new Node(s, top);
	} // end push
	
	// Removes the top element from the stack
	public String pop() 
						throws StackException {
		if (!isEmpty()) {
			Node temp = top;
			top = top.next;
			return temp.item;
		}
		else {
			throw new StackException ("StackException on " + "pop: stack empty");
		} // end if
	} // end pop
	
	// Accesses the top element of the stack, without removing it.
	public String peek() 
						throws StackException {
		if (!isEmpty()) {
			return top.item;
		}
		else {
			throw new StackException ("StackException on " + " peek: stack empty");
		} // end if
	} // end peek

	// Empties all the elements from the stack
	public void popAll(){
		top = null;
	} // end popAll
}