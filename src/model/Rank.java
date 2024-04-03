// Class

/**
 * This program allows for the cards to have ranks
 * 
 * @author Rick Mercer and Soren Abrams
 */

package model;

public enum Rank {
	ERROR1(-99), ERROR2(-98), ERROR3(-999), ERROR4(-1001), ERROR5(-1040), DEUCE(2), THREE(3), FOUR(4), FIVE(5), SIX(6),
	SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13), ACE(14);

	private int value;

	Rank(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}