package model;

import java.util.ArrayList;

/**
 * Represents a dealer in Blackjack. Requires an ArrayList of players to start
 * 
 * @author Jakob Garcia
 */
public class Dealer {

	private Deck deck;
	private ArrayList<Player> players;
	private BlackjackHand dealerHand;
	private boolean folded = false; // indicates if the dealer has folded

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
		for (Player player : players) {
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

	public ArrayList<Player> getPlayers() { return players; }
}
