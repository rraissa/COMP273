import java.util.*;

class CardDeck {
    LinkedList<Integer> deck;

    // constructor, creates a deck with n cards, placed in increasing order
    CardDeck(int n) {
	deck = new LinkedList<Integer> ();
	for (int i=1;i<=n;i++) deck.addLast(new Integer(i));
    }

    // executes the card trick
    public void runTrick() {
	while (!deck.isEmpty()) {
		
	    // remove the first card and remove it
	    Integer topCard = deck.removeFirst();
	    System.out.println("Showing card "+topCard);

	    // if there's nothing left, we are done
	    if (deck.isEmpty()) break;
	    
	    // otherwise, remove the top card and place it at the back.
	    Integer secondCard = deck.removeFirst();
	    deck.addLast(secondCard);

	   System.out.println("Remaining deck: "+deck);

	}
    }



    public void setupDeck(int n) {
	/* WRITE YOUR CODE HERE */
    	System.out.println("Running trick after setting up deck:");
    	LinkedList<Integer> tmpdeck = new LinkedList<Integer> ();
    	
    	// tmpdeck contains integer from 1 to n, in increasing order
    	for (int i=1;i<=n;i++) tmpdeck.addLast(new Integer(i));
    	
    	
    	for (int j=n;j>1;j--){	
    		
    		// lastCard is the last element in tmpdeck at every iteration
    		int lastCard = tmpdeck.getLast();
    		
    		// add last element of tmpdeck in first position of deck
    		deck.addFirst(lastCard);
    		
    		// remove that element from tmpdeck
    		tmpdeck.removeLast();
    		
    		// take the last element of deck and put it at the front then remove it from the last position
    		if(deck.size()>1){
    		deck.addFirst(deck.getLast());
    		deck.removeLast();
    		
    		}
    	}
    	
    	// add 1 to the top of the list
    	deck.add(0, 1);
    	
    	
    }




	public static void main(String args[]) {
	// this is just creating a deck with cards in increasing order, and running the trick. 
	CardDeck d = new CardDeck(10);
	d.runTrick();
	
	
	// this is calling the method you are supposed to write, and executing the trick.
	CardDeck e = new CardDeck(0);
	e.setupDeck(10);
	e.runTrick();
    }
}

    
