package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * @author Jakob Garcia
 */
public class Deck {

	private ArrayList<Card> cardDeck;
	private int deckTop;
	
	/**
	 *  construct a deck of 10 complete decks each filled with the standard 52 cards
	 */
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
	
	
	/**
	 * Constructs a deck of an inputted amount of decks each filled with the standard 52 cards
	 * 
	 * @param deckAmount number decks you like to be contained within the dealers deck
	 */
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
	
	
	// Used for testing
	/**
	 * deck used for testing
	 * @param seed random seed inputted to keep track of shuffling 
	 */
	public Deck(Random seed) {
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
		Collections.shuffle(cardDeck, seed);

	}

	/**
	 * @return returns a the top card drawn from the deck
	 */
	public Card getTopCard() {
		Card topCard = cardDeck.get(deckTop);
		deckTop++;
		return topCard;
	}
	
	/*
	 * resets the deck by shuffling and resetting the variable that is used to
	 *  move through the deck
	 */
	public void resetDeck() {
		deckTop = 0;
		Collections.shuffle(cardDeck);
	}
	
	/**
	 * returns amount of deck used
	 * @return decimal representation of percentage of deck used
	 */
	public double deckAmountUsed() {
		return deckTop * 1.0 / cardDeck.size();
	}
	
	/**
	 * returns what the "top" index of the deck is
	 * @return index deckTop 
	 */
	public int getDeckTopIndex() {
		return deckTop;
	}
	
	/**
	 * returns the total amount of cards in the deck
	 * @return	total size
	 */
	public int getTotalCards() {
		return cardDeck.size();
	}
}
