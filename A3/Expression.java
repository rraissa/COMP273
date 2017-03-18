// NAME: FARDON Raïssa
// Student ID: 260689524


import java.io.*;
import java.util.*;

public class Expression {
	static String delimiters="+-*%()";
	
	
	// Returns the value of the arithmetic Expression described by expr
	// Throws an exception if the Expression is malformed
	static Integer evaluate(String expr) throws Exception {
	    /* The code below gives you an example of utilization of the
	     * StringTokenizer class to break the Expression string into
	     * its components */
		//StringTokenizer st = new StringTokenizer( expr , delimiters , true );   
        
	    /* This is just an example of how to use the StringTokenizer */
	    /*while ( st.hasMoreTokens() ) {
		String element = st.nextToken();
		System.out.println("Element ="+element);
	    }*/
        
	    
	    /* YOU WRITE YOUR CODE HERE */
	    
	    StringTokenizer st = new StringTokenizer( expr , delimiters , true );   
	    
	    Stack<Integer> numbersStack = new Stack<Integer>(); // stack of integer for the numbers
        Stack<String> operatorsStack = new Stack<String>(); // stack of string for the operators and parentheses
        int evaluate;
        int leftParentheses = 0;
        int rightParentheses = 0;
     
        
        // proceed as long as there are still elements to be read
        while (st.hasMoreTokens()) {
        	String element = st.nextToken();
        	
        	
        	//if element is an integer, push element on number stack 
        	 if(isInteger(element)){
        		numbersStack.push(Integer.parseInt(element));
        	}
        	
        	//if element is a left parenthesis, push element on operator stack 
         	else if(element.equals("(")){
         		operatorsStack.push(element);
         		leftParentheses += 1;
         	}
        	 
        	//if element is a right parenthesis, apply operator to last two numbers until we reach the left parenthesis, then remove it  
        	else if(element.equals(")")){
        		rightParentheses += 1;
        		while(!operatorsStack.peek().equals("(")){
        			numbersStack.push(apply(operatorsStack.pop(), numbersStack.pop(), numbersStack.pop()));
        		}
        		operatorsStack.pop();
        	}
        	
        	// if element is an operator, push it on operator stack
        	else if((element.equals("+") || element.equals("-")|| element.equals("%")|| element.equals("*"))){
        		if(!operatorsStack.empty() && !operatorsStack.peek().equals("(")){ // if operator stack is not empty or is not a left parenthesis, apply operator on top of stack to first 2 numbers
        		numbersStack.push(apply(operatorsStack.pop(),  numbersStack.pop(),  numbersStack.pop()));
        		}
        		operatorsStack.push(element); // push operator on stack
        	}
        	
        	
        	// apply operator to 2 numbers at top of stack as long as there are still operators to add, the stack of numbers contains at least 2 numbers
        	// and the first element of the operator stack is not a left parenthesis and there are as many right parentheses as left parentheses
        	while((!operatorsStack.empty()) && (numbersStack.size() >= 2) && (!operatorsStack.peek().equals("(")) && (leftParentheses == rightParentheses)){
        		numbersStack.push(apply(operatorsStack.pop(),  numbersStack.pop(),  numbersStack.pop())); 
        	}

        	 // throw exception if there's an invalid character
        	if ((!element.equals("+") && !element.equals("-") && !element.equals("%") && !element.equals("*")) &&  !element.equals("(") && !element.equals(")") && !isInteger(element) ){
        		Exception e = new Exception();
        		throw e;
        	}
        	
        }
        
        // throw exception if the number of right parentheses doesn't match the number of left parentheses
        if(rightParentheses != leftParentheses){
        	Exception e = new Exception();
        	throw e;
        }

        // throw exception if there are no more operators to apply but there are still operands 
        if(numbersStack.size() == 1 && !operatorsStack.isEmpty()){
        	Exception e = new Exception();
        	throw e;
        }
        
        evaluate = numbersStack.pop();
	    
	    return evaluate;
	    
	    }
	
	// applying operator to operands, method found at http://www.geeksforgeeks.org/expression-evaluation/
	 public static int apply(String op, Integer b, Integer a)
	    {
	        switch (op)
	        {
	        case "+":
	            return a + b;
	        case "-":
	            return a - b;
	        case "*":
	            return a * b;
	        case "%":
	            if (b == 0)
	                throw new
	                UnsupportedOperationException("Cannot divide by zero");
	            return a / b;
	        }
	        return 0;
	    }
	 
	 
	// check if element is an integer, method(s) found at http://stackoverflow.com/questions/5439529/determine-if-a-string-is-an-integer-in-java/15801999
	public static boolean isInteger(String s) {
		    return isInteger(s,10);
	}

	public static boolean isInteger(String s, int radix) {
		    if(s.isEmpty()) return false;
		    for(int i = 0; i < s.length(); i++) {
		        if(i == 0 && s.charAt(i) == '-') {
		            if(s.length() == 1) return false;
		            else continue;
		        }
		        if(Character.digit(s.charAt(i),radix) < 0) return false;
		    }
		    return true;
	}
		
		

	public static void main(String args[]) throws Exception { 
		
		String line;	
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		
                                      	                        
		do {
			line=stdin.readLine();
			if (line.length()>0) {
				try {
					Integer x=evaluate(line);
					System.out.println(" = " + x);
				}
				catch (Exception e) {
					System.out.println("Malformed Expression: "+e);
				}
			}
		} while (line.length()>0);
	}
}