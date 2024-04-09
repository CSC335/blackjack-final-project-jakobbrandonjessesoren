package model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Jakob Garcia
 */
public class Deck {

	private ArrayList<Card> cardDeck;
	private int deckTop;

	public Deck() {
		// Generate an ArrayList of all 52 cards in a standard deck but for 10 decks
		cardDeck = new ArrayList<Card>();
		deckTop = 0;
		Rank[] ranks = { Rank.DEUCE, Rank.THREE, Rank.FOUR, Rank.FIVE, Rank.SIX, Rank.SEVEN, Rank.EIGHT, Rank.NINE,
				Rank.TEN, Rank.JACK, Rank.QUEEN, Rank.KING, Rank.ACE };
		Suit[] suits = { Suit.SPADES, Suit.CLUBS, Suit.DIAMONDS, Suit.HEARTS };
		for (int i = 0; i < 10; i++) {
			for (Suit suit : suits) {
				for (Rank rank : ranks) {
					cardDeck.add(new Card(rank, suit));
				}
			}
		}
		Collections.shuffle(cardDeck);

	}

	public Deck(int deckAmount) {
		// Generate an ArrayList of all 52 cards in a standard deck for a specified
		// amount of decks
		cardDeck = new ArrayList<Card>();
		deckTop = 0;
		Rank[] ranks = { Rank.DEUCE, Rank.THREE, Rank.FOUR, Rank.FIVE, Rank.SIX, Rank.SEVEN, Rank.EIGHT, Rank.NINE,
				Rank.TEN, Rank.JACK, Rank.QUEEN, Rank.KING, Rank.ACE };
		Suit[] suits = { Suit.SPADES, Suit.CLUBS, Suit.DIAMONDS, Suit.HEARTS };
		for (int i = 0; i < deckAmount; i++) {
			for (Suit suit : suits) {
				for (Rank rank : ranks) {
					cardDeck.add(new Card(rank, suit));
				}
			}
		}
		Collections.shuffle(cardDeck);

	}

	public Card getTopCard() {
		Card topCard = cardDeck.get(deckTop);
		deckTop++;
		return topCard;
	}

	public void resetDeck() {
		deckTop = 0;
		Collections.shuffle(cardDeck);
	}

	public double deckAmountUsed() {
		return deckTop * 1.0 / cardDeck.size();
	}
}
