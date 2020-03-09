import java.lang.Math.*;

class ExpressionTree {
    private String value;
    private ExpressionTree leftChild, rightChild, parent;
    
    ExpressionTree() {
        value = null; 
        leftChild = rightChild = parent = null;
    }
    
    // Constructor
    /* Arguments: String s: Value to be stored in the node
                  ExpressionTree l, r, p: the left child, right child, and parent of the node to created      
       Returns: the newly created ExpressionTree               
    */
    ExpressionTree(String s, ExpressionTree l, ExpressionTree r, ExpressionTree p) {
        value = s; 
        leftChild = l; 
        rightChild = r;
        parent = p;
    }
    
    /* Basic access methods */
    String getValue() { return value; }

    ExpressionTree getLeftChild() { return leftChild; }

    ExpressionTree getRightChild() { return rightChild; }

    ExpressionTree getParent() { return parent; }


    /* Basic setting methods */ 
    void setValue(String o) { value = o; }
    
    // sets the left child of this node to n
    void setLeftChild(ExpressionTree n) { 
        leftChild = n; 
        n.parent = this; 
    }
    
    // sets the right child of this node to n
    void setRightChild(ExpressionTree n) { 
        rightChild = n; 
        n.parent=this; 
    }
    

    // Returns the root of the tree describing the expression s
    // Watch out: it makes no validity checks whatsoever!
    ExpressionTree(String s) {
        // check if s contains parentheses. If it doesn't, then it's a leaf
        if (s.indexOf("(")==-1) setValue(s);
        else {  // it's not a leaf

            /* break the string into three parts: the operator, the left operand,
               and the right operand. ***/
            setValue( s.substring( 0 , s.indexOf( "(" ) ) );
            // delimit the left operand 2008
            int left = s.indexOf("(")+1;
            int i = left;
            int parCount = 0;
            // find the comma separating the two operands
            while (parCount>=0 && !(s.charAt(i)==',' && parCount==0)) {
                if ( s.charAt(i) == '(' ) parCount++;
                if ( s.charAt(i) == ')' ) parCount--;
                i++;
            }
            int mid=i;
            if (parCount<0) mid--;

        // recursively build the left subtree
            setLeftChild(new ExpressionTree(s.substring(left,mid)));
    
            if (parCount==0) {
                // it is a binary operator
                // find the end of the second operand.F13
                while ( ! (s.charAt(i) == ')' && parCount == 0 ) )  {
                    if ( s.charAt(i) == '(' ) parCount++;
                    if ( s.charAt(i) == ')' ) parCount--;
                    i++;
                }
                int right=i;
                setRightChild( new ExpressionTree( s.substring( mid + 1, right)));
        }
    }
    }


    // Returns a copy of the subtree rooted at this node... 2014
    ExpressionTree deepCopy() {
        ExpressionTree n = new ExpressionTree();
        n.setValue( getValue() );
        if ( getLeftChild()!=null ) n.setLeftChild( getLeftChild().deepCopy() );
        if ( getRightChild()!=null ) n.setRightChild( getRightChild().deepCopy() );
        return n;
    }
    
    // Returns a String describing the subtree rooted at a certain node.
    public String toString() {
        String ret = value;
        if ( getLeftChild() == null ) return ret;
        else ret = ret + "(" + getLeftChild().toString();
        if ( getRightChild() == null ) return ret + ")";
        else ret = ret + "," + getRightChild().toString();
        ret = ret + ")";
        return ret;
    } 
    
    // Converts String into operation.
    static public double op(String operation, double a, double b) {
   	 if (operation.equals("add")) return a+b;
   	 
   	 if (operation.equals("mult")) return a*b;
   	 
   	 if (operation.equals("minus")) return a-b;
   	 
   	 if (operation.equals("sin")) return Math.sin(a);
   	 
   	 if (operation.equals("cos")) return Math.cos(a);
   	 
   	 if (operation.equals("exp")) return Math.exp(a);
   	 
   	 else return 0; 
   	 
   }


    // Returns the value of the expression rooted at a given node
    // when x has a certain value
    double evaluate(double x) {
	// WRITE YOUR CODE HERE
    	
    	double y;
    	double z;
    	
    	if(this.getLeftChild() == null){ // we're assuming the expression is valid, so if there's no left child, it's a leaf
    		if(this.getValue().equals("x")){ // we have a leaf, if this is x, we return x
    			return x;
    		}
    		else{
    			return Double.parseDouble(this.getValue()); // otherwise we return the value of this
    		}
    	}
    	
    	else{ // if we don't have a leaf/if we have a left child
    		if(this.getRightChild() != null){ // and if there is a right child
    			y = op(this.getValue(), this.getLeftChild().evaluate(x), this.getRightChild().evaluate(x)); // take right and left children and evaluate with their parent
    			return y; // return the result
    		}
    		else{ 	// if there is no right child
    			z = op(getValue(), getLeftChild().evaluate(x), 0); // evaluate x and 0 
    			return z;
    		}
    	}
    	           
    }                                                 

    /* returns the root of a new expression tree representing the derivative of the
       original expression */
    ExpressionTree differentiate() {
	// WRITE YOUR CODE HERE
    	
    	ExpressionTree one = new ExpressionTree("1");
    	ExpressionTree zero = new ExpressionTree("0");
    	ExpressionTree leftTree;
		ExpressionTree rightTree;
    	ExpressionTree finalTree;
    	
    	if(this.getLeftChild() == null){ // if it's a leaf
    		if(this.getValue().equals("x")){ //if this is x, return 1 since the derivative of x is 1
    			return one;
    		}
    		else{
    			return zero; // otherwise it's a constant, return zero since the derivative of a number is 0
    		}
    	}
    	
    	if(this.getValue().equals("mult")){ // if the node is a multiplication
    		leftTree = new ExpressionTree("mult", this.getLeftChild().differentiate(), this.getRightChild().deepCopy(), this.getParent()); // we make a first tree by differentiating the left child of the original tree
    		rightTree = new ExpressionTree("mult", this.getLeftChild().deepCopy(), this.getRightChild().differentiate(), this.getParent()); // we do the same but with the right child
    		return finalTree = new ExpressionTree("add", leftTree, rightTree, this.getParent()); // we should get two operands, we add those
    	}
    	
    	if(this.getValue().equals("add")){ // if the node is an addition
    		return finalTree = new ExpressionTree("add", this.getLeftChild().differentiate(), this.getRightChild().differentiate(), this.getParent()); // differentiate both sides then do the addition
    	}
    	
    	if(this.getValue().equals("minus")){ // if the node is an subtraction
    		return finalTree = new ExpressionTree("minus", this.getLeftChild().differentiate(), this.getRightChild().differentiate(), this.getParent()); // do the same
    	}
    	
    	if(this.getValue().equals("sin")){ // if the node is sine
    		leftTree = new ExpressionTree("cos", this.getLeftChild().deepCopy(), null, this.getParent()); // this gives us cos and the subtree of the leftChild, so if we have sin(8x), this gives cos(8x)
    		rightTree = this.getLeftChild().differentiate(); // here we differentiate the subtree of the leftChild so 8x gives 8
    		return finalTree = new ExpressionTree("mult", leftTree, rightTree, this.getParent()); // we multiply both previous results, so the derivative of sin(8x) gives 8cos(8x)
    	}
    	
    	if(this.getValue().equals("cos")){ // if the node is cosine
    		ExpressionTree sine = new ExpressionTree("sin", this.getLeftChild().deepCopy(), null, this.getParent());
    		ExpressionTree nullTree = new ExpressionTree("0", null, null, this.getParent());
    		rightTree = new ExpressionTree("mult", sine , this.getLeftChild().differentiate(), this.getParent()); // this gives us cos and the subtree of the leftChild, so if we have cos(8x), this gives sin(8x)
    		return finalTree = new ExpressionTree("minus", nullTree, rightTree, this.getParent()); // we add "minus" in front of the previous tree since the derivative of cos(x) is -sin(x)
    	}
    	
    	if(this.getValue().equals("exp")){ // if it's exponential
    		return finalTree = new ExpressionTree("mult", this.deepCopy(), this.getLeftChild().differentiate(), getParent()); // we return the the exponent multiplied by the derivative of the leftChild
    	}
                   
        return null;
    }
        
    
    public static void main(String args[]) {
        ExpressionTree e = new ExpressionTree("mult(add(2,x),cos(x))");
        System.out.println(e);
        System.out.println(e.evaluate(1));
        System.out.println(e.differentiate());
   
 }
}
