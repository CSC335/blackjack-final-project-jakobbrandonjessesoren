package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;

import model.Card;
import model.Deck;
import model.Rank;
import model.Suit;

public class DeckTest {
	@Test
	public void testConstructor() {

		Random rand = new Random();
		rand.setSeed(12);
		Deck deck = new Deck(rand);
		assertEquals(0, deck.deckAmountUsed(), 0);
		assertEquals(6, deck.getTopCard().getValue());
		assertEquals(9, deck.getTopCard().getValue());
		assertEquals(7, deck.getTopCard().getValue());
		assertEquals(7, deck.getTopCard().getValue());
		assertEquals(8, deck.getTopCard().getValue());
		assertEquals(4, deck.getTopCard().getValue());
		assertEquals(2, deck.getTopCard().getValue());

		for (int i = 0; i < 52 * 5 - 7; i++) {
			deck.getTopCard();
		}

		assertEquals(0, deck.deckAmountUsed(), 0.5);
		deck.resetDeck();
		assertEquals(0, deck.deckAmountUsed(), 0);
	}
	
	@Test
	public void testNormalDeck() {
		Deck deck = new Deck();
		System.out.println(deck.getTopCard());
		System.out.println(deck.getTopCard());
		System.out.println(deck.getTopCard());
		System.out.println(deck.getTopCard());
	}
	
	@Test
	public void testDeckSize() {
		Deck deck = new Deck(1);
		assertEquals(0, deck.deckAmountUsed(), 0);
		for (int i = 0; i < 52 ; i++) {
			Card card = deck.getTopCard();
			System.out.println(card.getValue());
			System.out.println(card);

		}
		assertEquals(1, deck.deckAmountUsed(), 1);
		deck.resetDeck();
		assertEquals(0, deck.deckAmountUsed(), 0);

	}
}
