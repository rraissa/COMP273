import java.util.*;
import java.io.*;


class Sudoku
{
    /* SIZE is the size parameter of the Sudoku puzzle, and N is the square of the size.  For 
     * a standard Sudoku puzzle, SIZE is 3 and N is 9. */
    int SIZE, N;

    /* The grid contains all the numbers in the Sudoku puzzle.  Numbers which have
     * not yet been revealed are stored as 0. */
    int Grid[][];


    /* The solve() method should remove all the unknown characters ('x') in the Grid
     * and replace them with the numbers from 1-9 that satisfy the Sudoku puzzle. */
    
    public void solve() //throws IOException
    {
    	
    	 
    	for(int row = 0; row < N; row++){
			for(int col = 0; col < N; col++){	
				for(int i = SIZE; i <= N; i++){
					if(checkConflict(this, row, col, i) == false || checkZero() == true || Grid[row][col] == 0){
						this.solver(this, row, col); // use solver method	
					}	
					
					/*else{ // throw exception if the input has a number that is repeated on a row or column 
						//System.out.println("The input is invalid");
						 System.out.println("");
					     System.out.println("|￣￣￣￣￣￣￣￣|");		
					   	 System.out.println("");
					   	 System.out.println(" INVALID INPUT       ");		// INVALID INPUT MESSAGE
					   	 System.out.println("");
					   	 System.out.println("|＿＿＿＿＿＿＿＿|");
					   	 System.out.println("(\\__/) ||");
					   	 System.out.println("(•ㅅ•) ||");
					   	 System.out.println("/ 　 づ");
					   	 System.out.println("");
						IOException e = new IOException();
						throw e;
					}*/
				}
    		}
    	}
     System.out.println("");
     System.out.println("|￣￣￣￣￣￣￣￣|");
   	 System.out.println("");
   	 System.out.println("   SUCCESS!       ");		// SUDOKU SOLVED MESSAGE
   	 System.out.println("");
   	 System.out.println("|＿＿＿＿＿＿＿＿|");
   	 System.out.println("(\\__/) ||");
   	 System.out.println("(•ㅅ•) ||");
   	 System.out.println("/ 　 づ");
   	 System.out.println("");
    }
    
    
    
    // method that takes as input a sudoku, its rows, and its columns
    public boolean solver(Sudoku x, int row, int col) /*throws IOException*/ {

    	if (row == N) { // base case
    	    row = 0;
    	    if (col++ == N) 
    		return true;
    	}
    	
    	if(!checkZero() || Grid[row][col] != 0){ // if there are no 0s in the entire sudoku, we're done 
    		return solver(x, row+1, col);
    	}
    	
	
    	
    	for(int n = 1; n <= N; n++){ 
    		if(checkConflict(this, row, col, n) == true){	// if there is no conflict, replace 0 with the new number
    			Grid[row][col] = n;
    			if(solver(x, row+1, col)) return true; // recursive call
    			Grid[row][col] = 0; // undoing for backtracking
    		}
    	}
    	

    	
    	return false;
    }
    
    // this method checks if there are 0s in the grid
    public boolean checkZero(){
    	boolean zero = false;
    	for(int i = 0; i < N; i++){
    		for(int j = 0; j < N; j++){
    			if(Grid[i][j] == 0){
    				zero = true;
    			}
    		}
    	}
    	return zero;
    }
    
    // this method checks if the same number is repeated on a line, column, or in a box
    public boolean checkConflict(Sudoku x, int row, int column, int number){
    	for(int r = 0; r < N; r++){
    		if(number == Grid[r][column]){
    			return false;
    		}
    	}
    	
    	for(int c = 0; c < N; c++){
    		if(number == Grid[row][c]){
    			return false;
    		}
    	}
    	
    	int boxRow = (row/SIZE)*SIZE;
    	int boxCol = (column/SIZE)*SIZE;
    	
    	for(int r = 0; r < SIZE; r++){
    		for(int s = 0; s < SIZE; s++){
    			if(number == Grid[r + boxRow][s + boxCol]){
    				return false;
    			}
    		}
    	}
    	return true;
    }
    



    /*****************************************************************************/
    /* NOTE: YOU SHOULD NOT HAVE TO MODIFY ANY OF THE FUNCTIONS BELOW THIS LINE. */
    /*****************************************************************************/
 
    /* Default constructor.  This will initialize all positions to the default 0
     * value.  Use the read() function to load the Sudoku puzzle from a file or
     * the standard input. */
    public Sudoku( int size )
    {
        SIZE = size;
        N = size*size;

        Grid = new int[N][N];
        for( int i = 0; i < N; i++ ) 
            for( int j = 0; j < N; j++ ) 
                Grid[i][j] = 0;
    }


    /* readInteger is a helper function for the reading of the input file.  It reads
     * words until it finds one that represents an integer. For convenience, it will also
     * recognize the string "x" as equivalent to "0". */
    static int readInteger( InputStream in ) throws Exception
    {
        int result = 0;
        boolean success = false;

        while( !success ) {
            String word = readWord( in );

            try {
                result = Integer.parseInt( word );
                success = true;
            } catch( Exception e ) {
                // Convert 'x' words into 0's
                if( word.compareTo("x") == 0 ) {
                    result = 0;
                    success = true;
                }
                // Ignore all other words that are not integers
            }
        }

        return result;
    }


    /* readWord is a helper function that reads a word separated by white space. */
    static String readWord( InputStream in ) throws Exception
    {
        StringBuffer result = new StringBuffer();
        int currentChar = in.read();
	String whiteSpace = " \t\r\n";
        // Ignore any leading white space
        while( whiteSpace.indexOf(currentChar) > -1 ) {
            currentChar = in.read();
        }

        // Read all characters until you reach white space
        while( whiteSpace.indexOf(currentChar) == -1 ) {
            result.append( (char) currentChar );
            currentChar = in.read();
        }
        return result.toString();
    }


    /* This function reads a Sudoku puzzle from the input stream in.  The Sudoku
     * grid is filled in one row at at time, from left to right.  All non-valid
     * characters are ignored by this function and may be used in the Sudoku file
     * to increase its legibility. */
    public void read( InputStream in ) throws Exception
    {
        for( int i = 0; i < N; i++ ) {
            for( int j = 0; j < N; j++ ) {
                Grid[i][j] = readInteger( in );
            }
        }
    }


    /* Helper function for the printing of Sudoku puzzle.  This function will print
     * out text, preceded by enough ' ' characters to make sure that the printint out
     * takes at least width characters.  */
    void printFixedWidth( String text, int width )
    {
        for( int i = 0; i < width - text.length(); i++ )
            System.out.print( " " );
        System.out.print( text );
    }


    /* The print() function outputs the Sudoku grid to the standard output, using
     * a bit of extra formatting to make the result clearly readable. */
    public void print()
    {
        // Compute the number of digits necessary to print out each number in the Sudoku puzzle
        int digits = (int) Math.floor(Math.log(N) / Math.log(10)) + 1;

        // Create a dashed line to separate the boxes 
        int lineLength = (digits + 1) * N + 2 * SIZE - 3;
        StringBuffer line = new StringBuffer();
        for( int lineInit = 0; lineInit < lineLength; lineInit++ )
            line.append('-');

        // Go through the Grid, printing out its values separated by spaces
        for( int i = 0; i < N; i++ ) {
            for( int j = 0; j < N; j++ ) {
                printFixedWidth( String.valueOf( Grid[i][j] ), digits );
                // Print the vertical lines between boxes 
                if( (j < N-1) && ((j+1) % SIZE == 0) )
                    System.out.print( " |" );
                System.out.print( " " );
            }
            System.out.println();

            // Print the horizontal line between boxes
            if( (i < N-1) && ((i+1) % SIZE == 0) )
                System.out.println( line.toString() );
        }
    }


    /* The main function reads in a Sudoku puzzle from the standard input, 
     * unless a file name is provided as a run-time argument, in which case the
     * Sudoku puzzle is loaded from that file.  It then solves the puzzle, and
     * outputs the completed puzzle to the standard output. */
    public static void main( String args[] ) throws Exception
    {
        InputStream in;
        if( args.length > 0 ) 
            in = new FileInputStream( args[0] );
        else
            in = System.in;

        // The first number in all Sudoku files must represent the size of the puzzle.  See
        // the example files for the file format.
        int puzzleSize = readInteger( in );
        if( puzzleSize > 100 || puzzleSize < 1 ) {
            System.out.println("Error: The Sudoku puzzle size must be between 1 and 100.");
            System.exit(-1);
        }

        Sudoku s = new Sudoku( puzzleSize );

        // read the rest of the Sudoku puzzle
        s.read( in );

        // Solve the puzzle.  We don't currently check to verify that the puzzle can be
        // successfully completed.  You may add that check if you want to, but it is not
        // necessary.
        s.solve();

        // Print out the (hopefully completed!) puzzle
        s.print();
    }
}

