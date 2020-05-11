// has a single static method that computes the solution 
// of a postfix expression

public class Evaluator {
	
	//solve an arithmetic expression
	public static double evaluate(String expression)
						throws IllegalExpressionException {
		
		OperatorTokenizer operExp = new OperatorTokenizer(expression);
		PostfixTokenizer postExp = new PostfixTokenizer(operExp);
		StringStack eStack = new StringStack();
		Operator p = new Operator();
		
		// calculate the good expression
		while(postExp.hasNext()){
			String c = postExp.next();
			if (p.isOperator(c)){
				double operand2 = Double.parseDouble(eStack.pop());
				if (eStack.isEmpty()){
					throw new IllegalExpressionException("Too few operands");
				} else {
					double operand1 = Double.valueOf(eStack.pop());
					eStack.push(String.valueOf(p.evaluate(operand1, operand2, c)));
				} //end if
			}
			else {
				eStack.push(c);
			} // end if
		}// end while
		double answer = Double.valueOf(eStack.pop());			
		if (!eStack.isEmpty()){
			throw new IllegalExpressionException("Too many operands");
		} 	
		return answer;
	}

	public static void main(String[] args){
		String good = "3*(4+2)-4*(-6--3)";
		String badParens = "(4))";
		String toomanyOperands = "(4+6)12";
		String notEnough = "3--6+2*";
		System.out.println(good);
		System.out.println(evaluate(good));
		System.out.println(badParens);
		try {
        	evaluate(badParens);
       	} catch (IllegalExpressionException iee) {
        	System.out.println(iee.getMessage());
        }
		System.out.println(toomanyOperands);
		try {
        	evaluate(toomanyOperands);
		} catch (IllegalExpressionException iee) {
        	System.out.println(iee.getMessage());
		}
       	System.out.println(notEnough);
       	try {
            evaluate(notEnough);
       	} catch (IllegalExpressionException iee) {
            System.out.println(iee.getMessage());
		}
	}
	
}



