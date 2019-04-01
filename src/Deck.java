import java.util.ArrayList;
import java.util.Collection;

public class Deck extends ArrayList<Card> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int size;
	
	
	public Deck(String[] ranks, String[] suits, int[] values) {
		for (int j = 0; j < ranks.length; j++) {
			for (String suitString : suits) {
				add(new Card(ranks[j], suitString, values[j]));
			}
		}
		size = size();
		shuffle();
	}
	
	public int deckSize() {
		return size;
	}
	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	
	
	public void shuffle() {
		for (int k = size - 1; k > 0; k--) {
			int pos = (int) (Math.random() * (k + 1));  // range 0 to k, inclusive
			Card temp = get(pos);
			set(pos, get(k));
			set(k, temp);
		}
		size = size();
	}
	
	public Card deal() {
		if (isEmpty()) {
			return null;
		}
		size--;
		Card c = get(size);
		return c;
	}
	
	@Override
	public String toString() {
		String rtn = "size = " + size + "\nCards: \n";
		
		for(int i = 0; i < size; i++) {
			rtn = rtn + get(i).suit() + ", " + get(i).rank() + ", " + get(i).pointValue() + "\n";
		}
		return rtn;
	}
}
