// using whatever type of list you wish to construct an 
// array or linked list as its main data structure 

import java.util.NoSuchElementException;

public class PostfixTokenizer implements Tokenizer {
	private String[] tokens;
	private int currTokenIndex;
	
	// Constructors
	// Creates a new postfix order Tokenizer. 
	// The ordering of the new tokenizer is postfix, meaning that 
	// the arithmetic operators are arranged in a postfix order. 
	// If all the non-operators and non-parens are valid operands
	// the result will be a valid postfix arithmetic expression
	// with only operands and operators -- no parens.
	public PostfixTokenizer(OperatorTokenizer infixTokens) 
					throws IllegalExpressionException {
		
		StringStack aStack = new StringStack();
		tokens = new String[infixTokens.numTokens()];
		Operator op = new Operator();
		infixTokens.reset();
		
		for (int i = 0; i < infixTokens.numTokens(); i++){
			String ch = infixTokens.next();
			
			// case: operator
			if (op.isOperator(ch)) {
				while( !aStack.isEmpty() && !aStack.peek().equals("(") &&
							op.comparePrecedence(ch, aStack.peek()) >= 0 ) {
					tokens[currTokenIndex] = aStack.pop(); 
					currTokenIndex++;
				}
				aStack.push(ch); // save new operator	
			} 
				
			// case: "("
			else if (ch.equals("(")) {
				aStack.push(ch);
			}
				
			// case: ")"
			else if (ch.equals(")")) {
				if (!aStack.isEmpty()){
					while ( !aStack.peek().equals("(")) {
						tokens[currTokenIndex] = aStack.pop(); 
						currTokenIndex++;
					}
					aStack.pop(); // remove the open parenthesis
				} else {
					throw new IllegalExpressionException("Mismatched parens");
				} //end if
			}
				
			// case: number
			else {
				tokens[currTokenIndex] = ch;  //postfixExp = postfixExp + ch
				currTokenIndex++;
			} // end if
			
		} //end for
		
		// append to postfixExp the operators remaining in the stack
		while (!aStack.isEmpty()) {
			tokens[currTokenIndex] = aStack.pop();//postfixExp = postfixExp + aStack.pop()
			currTokenIndex++;
		} //end while
		currTokenIndex = 0;

	}
	
	
/******* Tokenizer required methods ********/	
	
	// Description copied from interface: Tokenizer
	// sets the iterator start position for the first item in the list
	// reset in interface Tokenizer
	
	public void reset() {
		currTokenIndex = 0;
	}
	
	// numTokens in interface Tokenizer
	public int numTokens(){
		return tokens.length;
	}
	
	// toString in interface Tokenizer
	public String toString(){
		StringBuilder sb = new StringBuilder(tokens.length*10);
		for (int i=0; i<tokens.length-1; i++) {
			sb.append(tokens[i]+' ');
		}
		if (tokens.length > 0) {
			sb.append(tokens[tokens.length-1]);
		}
		return sb.toString();
	}
	
/********** Iterator required methods *******/

	// hasNext in interface Iterator<String>
	public boolean hasNext(){
		if (tokens[currTokenIndex] != null ){
			return currTokenIndex != tokens.length;
		} else {
			return false;
		}
	}

	// next in interface Iterator<String>
	public String next() {
		if (!hasNext()) {
			throw new NoSuchElementException("No more tokens");
		}
		return tokens[currTokenIndex++];
	}
	
	// remove in interface Iterator<String>
	public void remove(){
		throw new UnsupportedOperationException("Tokens may not be removed");
	
	}
	
/************ end of Iterator methods **********/

	public static void main(String[] args){
		String asString = "3*(4+-2)";
		OperatorTokenizer asInfix = new OperatorTokenizer(asString);
		PostfixTokenizer postfix = new PostfixTokenizer(asInfix);
		System.out.print("as infix tokens: ");
		System.out.println(asInfix);
		System.out.println("By individual postfix tokens:");
		while(postfix.hasNext()) {
        	System.out.println("next token: " + postfix.next());
		}
		
	}

}