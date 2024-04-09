package model;

/**
 * class Card represents one of the 52 poker cards. There are no comments before
 * methods because the method name says it all.
 * 
 * @author Rick Mercer and Soren Abrams
 */

public class Card implements Comparable<Card> {
	private final Rank rank;
	private final Suit suit;

	// Constructor
	public Card(Rank rank, Suit suit) {
		this.rank = rank;
		this.suit = suit;
	}

	public Suit getSuit() {
		return (this.suit);
	}

	public Rank getRank() {
		return (this.rank);
	}

	public int getValue() {
		return (this.rank.getValue());
	}

	public String toString() {
		// Use these four Unicode icons for the solid suit icons.
		char suitIcon = '\u2663';
		if (suit == Suit.DIAMONDS)
			suitIcon = '\u2666';
		if (suit == Suit.HEARTS)
			suitIcon = '\u2665';
		if (suit == Suit.SPADES)
			suitIcon = '\u2660';
		String cardValue;
		if (this.rank.getNum() <= 10) {
			cardValue = "" + this.rank.getValue();
		} else {
			if (this.rank.getNum() <= 11) {
				cardValue = "J";
			} else {
				if (this.rank.getNum() <= 12) {
					cardValue = "Q";
				} else {
					if (this.rank.getNum() <= 13) {
						cardValue = "K";
					} else {
						cardValue = "A";
					}
				}
			}
		}

		// Need to get the value instead of "?"
		return (cardValue + suitIcon);
	}

	@Override
	public int compareTo(Card other) {
		return (this.rank.getValue() - other.rank.getValue());
	}

	public boolean equals(Card other) {
		return ((compareTo(other) == 0) && this.suit == other.getSuit());
	}

}