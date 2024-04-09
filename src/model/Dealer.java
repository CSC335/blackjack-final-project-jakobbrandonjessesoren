package model;

import java.util.ArrayList;

/**
 * Represents a dealer in Blackjack. Requires an ArrayList of players to start
 * 
 * @author Jakob Garcia
 */
public class Dealer {

	private final Deck deck;
	private final ArrayList<Player> players;
	private BlackjackHand dealerHand;
	private boolean folded = false; // indicates if the dealer has folded
	private final int minScore = 16;

	/**
	 * Initialize the dealer and their hand. Depends on a list of players from the
	 * Game
	 */
	public Dealer(ArrayList<Player> players) {
		deck = new Deck();
		this.players = players;
		dealerHand = new BlackjackHand();
	}

	/**
	 * Deal a card to each player and then to the dealer
	 */
	public void dealCards() {
		if (deck.deckAmountUsed() >= 0.5)
			deck.resetDeck();

		dealerHand = new BlackjackHand();
		for (Player player : players) {
			player.discardCards();
			player.receiveCards(deck.getTopCard());
		}

		dealerHand.dealCard(deck.getTopCard());

		for (Player player : players) {
			player.receiveCards(deck.getTopCard());
		}
		dealerHand.dealCard(deck.getTopCard());
	}

	/**
	 * Allows the dealer to continue hitting until bust or above 16
	 */
	public boolean hit() {
		dealerHand.dealCard(this.dealSingleCard());
		if (dealerHand.isBusted() || dealerHand.getTotal() >= 16)
			folded = true;
		return folded;
	}

	public void hitUntilMinScore() {
		while (dealerHand.getTotal() < minScore) { // hit until >= minScore
			dealerHand.dealCard((this.dealSingleCard())); // dealer deals themself a card
		}
	}

	public boolean isFolded() {
		return folded;
	}

	public int getTotal() {
		return dealerHand.getTotal();
	}

	/**
	 * Reshuffle the deck and clear Player cards
	 */
	public void reshuffle() {
		// Reset the Deck and shuffle. Also, clear all Player Cards.
		deck.resetDeck();
		folded = false;
		dealerHand = new BlackjackHand();
		for (Player player : players) {
			player.discardCards();
		}
	}

	/**
	 * Collect a bet from each Player. Depends on the Game
	 */
	public void collectBet(Player player, double amount) {
		player.placeBet(amount);
	}

	/**
	 * Pay the winning Player(s) based on their bets. If they got Blackjack, they
	 * get 1.5x their bet
	 */
	public void payWinners() {
		for (Player player : players) {

			if (player.isBusted())
				continue;
			if (dealerHand.isBusted())
				player.receivePayout(false);
			else if (player.getHandTotal() == dealerHand.getTotal())
				player.receivePayout(true);
			else if (player.getHandTotal() > dealerHand.getTotal())
				player.receivePayout(false);
		}
	}

	/**
	 * Used so the game can deal a card to a player
	 */
	public Card dealSingleCard() {
		return deck.getTopCard();
	}

	public boolean hasTwentyOne() {
		return dealerHand.isBlackJack();
	}

	@Override
	public String toString() { return "DEALER:\n" + dealerHand + "\nTotal Score: " + dealerHand.getTotal(); }
    
	public BlackjackHand getHand() { return dealerHand; }
    
	public ArrayList<Player> getPlayers() {
		return players;
	}

	public ArrayList<Card> getDealerHand() {
		return dealerHand.getHand();
	}
}
