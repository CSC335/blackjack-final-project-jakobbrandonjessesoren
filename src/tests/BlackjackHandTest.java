package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import model.*;

public class BlackjackHandTest {
	@Test
	public void testConstructors() {
		Player pl1 = new Player("player", true, new PlayerAccount("username", "username"));
		assertTrue("player".equals(pl1.getName()));
		assertTrue(pl1.isPlayer());
		assertEquals(pl1.checkBalance(), 100.00);
		pl1.toString();
		Player pl2 = new Player("tester", 1264.55, false, new PlayerAccount("username", "username"));
		assertTrue("tester".equals(pl2.getName()));
		assertFalse(pl2.isPlayer());
		assertEquals(pl2.checkBalance(), 1264.55);
		pl2.toString();
	}
	
	@Test
	public void testCompare() {
		Player pl1 = new Player("player", true, new PlayerAccount("username", "username"));
		pl1.receiveCards(new Card(Rank.TEN, Suit.DIAMONDS));
		pl1.receiveCards(new Card(Rank.DEUCE, Suit.DIAMONDS));


		Player pl2 = new Player("tester", 1264.55, false, new PlayerAccount("username", "username"));
		pl2.receiveCards(new Card(Rank.TEN, Suit.DIAMONDS));
		pl2.receiveCards(new Card(Rank.THREE, Suit.CLUBS));

		assertEquals(-1, pl1.getHand().compareTo(pl2.getHand()));
		assertFalse(pl1.getHand().isTwentyOne());
		pl1.receiveCards(new Card(Rank.NINE, Suit.DIAMONDS));
		assertTrue(pl1.getHand().isTwentyOne());

	
	}
	
	@Test 
	public void testSplitGeneral() {
		Player pl = new Player("test", true, new PlayerAccount("username", "username"));
		pl.receiveCards(new Card(Rank.TEN, Suit.DIAMONDS));
		pl.receiveCards(new Card(Rank.TEN, Suit.HEARTS));
		assertEquals(pl.getHandTotal(), 20);
		pl.placeBet(15);
		assertEquals(pl.getBet(), 15);
		assertTrue(pl.split(new Card(Rank.TEN, Suit.SPADES), new Card(Rank.TEN, Suit.CLUBS)));
		assertEquals(pl.numOfHands(), 2);
		assertTrue(pl.split(new Card(Rank.NINE, Suit.SPADES), new Card(Rank.NINE, Suit.CLUBS)));
		pl.setCurrentHandIndex(1);
		assertTrue(pl.split(new Card(Rank.TEN, Suit.SPADES), new Card(Rank.TEN, Suit.CLUBS)));
		assertEquals(pl.numOfHands(), 4);
		assertFalse(pl.split(new Card(Rank.TEN, Suit.SPADES), new Card(Rank.TEN, Suit.CLUBS)));
		pl.setCurrentHandIndex(0);
		BlackjackHand hand1 = pl.getHand();
		BlackjackHand hand11 = pl.getHand();
		assertTrue(hand1 == hand11);
		pl.goToNextPlayerHand();
		BlackjackHand hand2 = pl.getHand();
		assertTrue(hand1.getBet() == hand2.getBet());
		
		
		
	}
	
	@Test
	public void testCardsGeneral() {
		Player pl = new Player("test", true, new PlayerAccount("username", "username"));
		pl.receiveCards(new Card(Rank.TEN, Suit.DIAMONDS));
		assertEquals(pl.getHandTotal(), 10);
        assertFalse(pl.hasBlackjack());
        assertFalse(pl.isBusted());
        pl.receiveCards(new Card(Rank.ACE, Suit.HEARTS));
        assertEquals(pl.getHandTotal(), 21);
        assertTrue(pl.hasBlackjack());
        assertFalse(pl.isBusted());
        assertFalse(pl.hit(new Card(Rank.DEUCE, Suit.SPADES)));
        assertEquals(pl.getHandTotal(), 13);
        assertFalse(pl.hasBlackjack());
        assertFalse(pl.isBusted());
        assertFalse(pl.isFolded());
        assertFalse(pl.hit(new Card(Rank.EIGHT, Suit.SPADES)));
        assertEquals(pl.getHandTotal(), 21);
        assertFalse(pl.hasBlackjack());
        assertFalse(pl.isBusted());
        assertFalse(pl.isFolded());
        assertTrue(pl.hit(new Card(Rank.DEUCE, Suit.SPADES)));
        assertEquals(pl.getHandTotal(), 23);
        assertFalse(pl.hasBlackjack());
        assertTrue(pl.isBusted());
        assertTrue(pl.isFolded()); 
        pl.discardCards();
        assertEquals(pl.getHandTotal(), 0);
        assertFalse(pl.hasBlackjack());
        assertFalse(pl.isBusted());
        assertFalse(pl.hit(new Card(Rank.FIVE, Suit.SPADES)));
        assertEquals(pl.getHandTotal(), 5);
        assertFalse(pl.hasBlackjack());
        assertFalse(pl.isBusted());
	}
	
	@Test
	public void testBet() {
		Player pl = new Player("test", 10000.00, true, new PlayerAccount("username", "username"));
		assertEquals(pl.checkBalance(), 10000.00);
		assertEquals(pl.getBet(), 0);
		pl.placeBet(1000.00);
		assertEquals(pl.checkBalance(), 9000.00);
		assertEquals(pl.getBet(), 1000.00);
		pl.placeBet(0.01);
		assertEquals(pl.checkBalance(), 8999.99);
		assertEquals(pl.getBet(), 0000.01);
		assertFalse(pl.doubleDown());
		assertEquals(pl.checkBalance(), 8999.98);
		assertEquals(pl.getBet(), 0000.02);
		pl.placeBet(1000.00);
		assertEquals(pl.checkBalance(), 7999.98);
		assertEquals(pl.getBet(), 1000.00);
		pl.receivePayout(true);
		assertEquals(pl.checkBalance(), 8999.98);
		pl.placeBet(1000.00);
		assertEquals(pl.getBet(), 1000.00);
		pl.receivePayout(false);
		assertEquals(pl.checkBalance(), 9999.98);
		pl.discardCards();
		pl.receiveCards(new Card(Rank.JACK, Suit.CLUBS));
		pl.receiveCards(new Card(Rank.ACE, Suit.CLUBS));
		pl.receivePayout(false);
		assertEquals(pl.checkBalance(), 9999.98);
	}
	
	@Test
	public void testFolding() {
		Player pl = new Player("test", true, new PlayerAccount("username", "username"));
		assertFalse(pl.isFolded());
		pl.fold();
		assertTrue(pl.isFolded());
	}
	
	@Test
    public void testAces() {
        Card ace = new Card(Rank.ACE, Suit.CLUBS);
        Player pl = new Player("test", true, new PlayerAccount("username", "username"));
        assertFalse(pl.hit(ace));
        assertEquals(pl.getHandTotal(), 11);
        assertFalse(pl.hasBlackjack());
        assertFalse(pl.isBusted());
        assertFalse(pl.hit(ace));
        assertEquals(pl.getHandTotal(), 12);
        assertFalse(pl.hasBlackjack());
        assertFalse(pl.isBusted());
        assertFalse(pl.hit(ace));
        assertEquals(pl.getHandTotal(), 13);
        assertFalse(pl.hasBlackjack());
        assertFalse(pl.isBusted());
        assertFalse(pl.hit(ace));
        assertEquals(pl.getHandTotal(), 14);
        assertFalse(pl.hasBlackjack());
        assertFalse(pl.isBusted());
        assertFalse(pl.hit(ace));
        assertEquals(pl.getHandTotal(), 15);
        assertFalse(pl.hasBlackjack());
        assertFalse(pl.isBusted());
        assertFalse(pl.hit(ace));
        assertEquals(pl.getHandTotal(), 16);
        assertFalse(pl.hasBlackjack());
        assertFalse(pl.isBusted());
        assertFalse(pl.hit(ace));
        assertEquals(pl.getHandTotal(), 17);
        assertFalse(pl.hasBlackjack());
        assertFalse(pl.isBusted());
        assertFalse(pl.hit(ace));
        assertEquals(pl.getHandTotal(), 18);
        assertFalse(pl.hasBlackjack());
        assertFalse(pl.isBusted());
        assertFalse(pl.hit(ace));
        assertEquals(pl.getHandTotal(), 19);
        assertFalse(pl.hasBlackjack());
        assertFalse(pl.isBusted());
        assertFalse(pl.hit(ace));
        assertEquals(pl.getHandTotal(), 20);
        assertFalse(pl.hasBlackjack());
        assertFalse(pl.isBusted());
        assertFalse(pl.hit(ace));
        assertEquals(pl.getHandTotal(), 21);
        assertFalse(pl.isBusted());
        assertFalse(pl.hit(ace));
        assertEquals(pl.getHandTotal(), 12);
        assertFalse(pl.hasBlackjack());
        assertFalse(pl.isBusted());
        assertFalse(pl.hit(ace));
        assertEquals(pl.getHandTotal(), 13);
        assertFalse(pl.hasBlackjack());
        assertFalse(pl.isBusted());
        assertFalse(pl.hit(ace));
        assertEquals(pl.getHandTotal(), 14);
        assertFalse(pl.hasBlackjack());
        assertFalse(pl.isBusted());
        assertFalse(pl.hit(ace));
        assertEquals(pl.getHandTotal(), 15);
        assertFalse(pl.hasBlackjack());
        assertFalse(pl.isBusted());
        assertFalse(pl.hit(ace));
        assertEquals(pl.getHandTotal(), 16);
        assertFalse(pl.hasBlackjack());
        assertFalse(pl.isBusted());
        assertFalse(pl.hit(ace));
        assertEquals(pl.getHandTotal(), 17);
        assertFalse(pl.hasBlackjack());
        assertFalse(pl.isBusted());
        assertFalse(pl.hit(ace));
        assertEquals(pl.getHandTotal(), 18);
        assertFalse(pl.hasBlackjack());
        assertFalse(pl.isBusted());
        assertFalse(pl.hit(ace));
        assertEquals(pl.getHandTotal(), 19);
        assertFalse(pl.hasBlackjack());
        assertFalse(pl.isBusted());
        assertFalse(pl.hit(ace));
        assertEquals(pl.getHandTotal(), 20);
        assertFalse(pl.hasBlackjack());
        assertFalse(pl.isBusted());
        assertFalse(pl.hit(ace));
        assertEquals(pl.getHandTotal(), 21);
        assertFalse(pl.isBusted());
        assertTrue(pl.hit(ace));
        assertEquals(pl.getHandTotal(), 22);
        assertFalse(pl.hasBlackjack());
        assertTrue(pl.isBusted());
    }
}
