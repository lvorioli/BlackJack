import java.util.ArrayList;
import java.util.Scanner;

public class Board {
	
	private String[] ranks = new String[] {"TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN", "JACK", "QUEEN", "KING", "ACE"};
	
	private String[] suits = new String[] {"SPADES", "CLUBS", "HEARTS", "DIAMONDS"};
	
	private int[] pointValues = new int[] {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11} ;
	
	private ArrayList<Card> cards;
	
	private ArrayList<Card> dealerCards;
	
	private Deck deck;
	
	private int input;
	
	private boolean dealerWon = false;
	
	private boolean won = false;
	
	private int playerChipCount = 40;
	
	private int betAmount;
	
	Scanner s = new Scanner(System.in);
	
	public Board() {
		cards = new ArrayList<Card>();
		dealerCards = new ArrayList<Card>();
		deck = new Deck(ranks, suits, pointValues);
		game();
	}
	
	public int size() {
		return cards.size();
	}
	
	public boolean isEmpty() {
		for (int k = 0; k < cards.size(); k++) {
			if (cards.get(k) != null) {
				return false;
			}
		}
		return true;
	}
	
	
	public int deckSize() {
		return deck.size();
	}
	
	public Card cardAt(int k) {
		return cards.get(k);
	}
	
	public String toString() {
		String s = "";
		for (int k = 0; k < cards.size(); k++) {
			s = s + k + ": " + cards.get(k) + "\n";
		}
		return s;
	}
	
	public void hit() {
		cards.add(deck.deal());
		cards.get(cards.size() - 1).setTurnedOver(true);
	}
	
	public void hitDealer() {
		dealerCards.add(deck.deal());
	}
	
	
	public void gameIsWon(boolean stand) {
		if((isBust() == false && dealerIsBust() == false) && stand == true) {
			if(totalPoints() == dealerTotalPoints()) {
				won = true;
				dealerWon = true;
				showDealerCards();
			}
			else if(totalPoints() > dealerTotalPoints()) {
				won = true;
				showDealerCards();
			}
			else {
				dealerWon = true;
				showDealerCards();
			}
		}
		else if(isBust() == false && dealerIsBust() == true) {
			won = true;
			showDealerCards();
		}
		else if(isBust() == true && dealerIsBust() == false){
			dealerWon = true;
			showDealerCards();
		}
	}
	public boolean isBust() {
		if(totalPoints() > 21) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean dealerIsBust() {
		if(dealerTotalPoints() > 21) {
			return true;
		}
		else {
			return false;
		}
	}
	public int totalPoints() {
		int totalPoints = 0;
		for(Card card: cards) {
			totalPoints = totalPoints + card.pointValue();
		}
		if(totalPoints > 21) {
			int aceCounter = 0;
			for(Card card: cards) {
				if(card.rank().equals("ACE")) {
					aceCounter++;
				}
			}
			while(aceCounter > 0 && totalPoints > 21) {
				totalPoints = totalPoints - 10;
				aceCounter--;
			}
		}
		return totalPoints;
	}
	
	public int dealerTotalPoints() {
		int totalPoints = 0;
		for(Card card: dealerCards) {
			totalPoints = totalPoints + card.pointValue();
		}
		if(totalPoints > 21) {
			int aceCounter = 0;
			for(Card card: dealerCards) {
				if(card.rank().equals("ACE")) {
					aceCounter++;
				}
			}
			while(aceCounter > 0 && totalPoints > 21) {
				totalPoints = totalPoints - 10;
				aceCounter--;
			}
		}
		return totalPoints;
	}
	
	public boolean anotherHitPossible() {
		if(isBust() == false) {
			return true;
		}
		else {
			return false;
		}
	}

	public void dealMyCards() {
		cards.add(deck.deal());
		cards.add(deck.deal());
		dealerCards.add(deck.deal());
		dealerCards.add(deck.deal());
		cards.get(0).setTurnedOver(true);
		cards.get(1).setTurnedOver(true);
		dealerCards.get(0).setTurnedOver(true);
		showCards();
	}
	
	public void showCards() {
		System.out.println("\n\nYOUR CARDS:");
		for(Card card: cards) {
			System.out.println(card);
		}
		System.out.println("\n\nDEALER'S CARDS:");
		for(Card card: dealerCards) {
			System.out.println(card);
		}
	}
	
	public void selection() {
		do {
			try {
				System.out.println("HIT(1) or STAND(2)");
				input = s.nextInt();
			}
			catch(Exception e){
				input = -1;
				s.nextLine();
			}	
		}while(input != 1 && input != 2);

		
		if(input == 1) {
			hit();
			dealerMove();
			gameIsWon(false);
			showCards();
		}
		else {
			if(dealerMove() == true) {
				gameIsWon(true);
				showCards();
				
			}
			else {
				gameIsWon(false);
				boolean d = false;
				while(d == false && dealerIsBust() == false) {
					d = dealerMove();
				}
				gameIsWon(true);
				showCards();
			}
		}
	}
	public boolean dealerMove() {
		long i = Math.round(Math.random());
		if(i == 1) {
			return true;
		}
		else {
			hitDealer();
			return false;
		}
	}
	
	public void showDealerCards() {
		for(Card card: dealerCards) {
			card.setTurnedOver(true);
		}
	}
	
	public void game() {
		bet();
		dealMyCards();
		do {
			selection();
		}while(dealerWon == false && won ==  false);
		
		if(won == true && dealerWon == false) {
			System.out.println("\nYOU WIN!");
			playerChipCount = playerChipCount + betAmount;
		}
		else if(won == false && dealerWon == true) {
			System.out.println("\nDEALER WINS!");
			playerChipCount = playerChipCount - betAmount;
		}
		else if((won == true && dealerWon == true) || (won == false && dealerWon == false)) {
			System.out.println("\nTIE!");
		}
		System.out.println("PLAYER CHIP AMOUNT: " + playerChipCount);
		
		if(playerChipCount > 0) {
			do {
				try {
					System.out.println("PLAY AGAIN(1) OR CASH OUT(0)? ");
					input = s.nextInt();
				}
				catch(Exception e) {
					input = -1;
					s.nextLine();
				}	
			}while(input != 1 && input != 0);
			
			
			if(input == 1) {
				betAmount = 0;
				cards = new ArrayList<Card>();
				dealerCards = new ArrayList<Card>();
				deck = new Deck(ranks, suits, pointValues);
				dealerWon = false;
				won = false;
				System.out.println("\n\n\n\n");
				game();
			
			}
			else {
			System.out.println("FINAL CHIP AMOUNT: " + playerChipCount);
			}
		}
		else {
			System.out.println("OUT OF CHIPS!");
		}
		
		
		
	}
	
	public void bet() {
		System.out.println("PLAYER CHIP AMOUNT: " + playerChipCount);
		do {
			try {
				System.out.println("ENTER BET AMOUNT: ");
				input = s.nextInt();
			
			}
			catch(Exception e) {
				input = -1;
				s.nextLine();
			}
					
		}while(input < 0 || input > playerChipCount);
		
		
		betAmount = input;
	}
	
	public static void main(String[] args) {
		Board b = new Board();
	}
	
	
}
