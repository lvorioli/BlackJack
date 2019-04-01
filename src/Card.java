
public class Card implements Comparable<Card> {

	private String suit;
	private String rank;
	private int pointValue;
	private boolean turnedOver;
	
	public Card(String rank, String suit, int pointValue) {
		this.suit = suit;
		this.rank = rank;
		this.pointValue = pointValue;
		turnedOver = false;
	}
	
	public String suit() {
		return suit;
	}
	
	public String rank() {
		return rank;
	}
	
	public int pointValue() {
		return pointValue;
	}
	public boolean turnedOver() {
		return turnedOver;
	}
	
	public void setTurnedOver(boolean b) {
		this.turnedOver = b;
	}

	@Override
	public int compareTo(Card o) {
		if(pointValue == o.pointValue()) {
			return 1;
		}
		return 0;
	}

	@Override
	public String toString() {
		if(turnedOver == true) {
			return rank + " of " + suit;
		}
		else return "************";
	}

}
