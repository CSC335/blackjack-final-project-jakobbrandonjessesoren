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
	private final int minScore = 17;

	/**
	 * Initialize the dealer and their hand. Depends on a list of players from the
	 * Game
	 * 
	 * @param players arralist of players
	 */
	public Dealer(ArrayList<Player> players) {
		deck = new Deck();
		this.players = players;
		dealerHand = new BlackjackHand();
	}

	/**
	 * Deal a card to each player and then to the dealer
	 * 
	 */
	public void dealCards() {
		if (deck.deckAmountUsed() >= 0.5)
			deck.resetDeck();

		dealerHand = new BlackjackHand();
		for (Player player : players) {
			// deal 2 cards to each player
			for (int i = 0; i < player.numOfHands(); i++) {
				player.receiveCards(deck.getTopCard());

				if (player.getCurrentHandIndex() != player.numOfHands() - 1) {
					player.goToNextPlayerHand();// iterate to next player hand
				}
			}
			player.setCurrentHandIndex(0);
		}

		dealerHand.dealCard(deck.getTopCard());
//		dealerHand.dealCard(new Card(Rank.ACE, Suit.CLUBS)); // fix first card to be an ace

		for (Player player : players) {
			for (int i = 0; i < player.numOfHands(); i++) {
				player.receiveCards(deck.getTopCard());
				if (player.getCurrentHandIndex() != player.numOfHands() - 1) {
					player.goToNextPlayerHand();// iterate to next player hand
				}
			}
			player.setCurrentHandIndex(0);
		}
		dealerHand.dealCard(deck.getTopCard());
//		dealerHand.dealCard(new Card(Rank.TEN, Suit.CLUBS)); // fix first card to be an ace
//
	}

	/**
	 * Dealer deals to himself until its over the minScore Implements a soft 17 rule
	 * in which if the hand contains an ace and the
	 * 
	 */
	public void hitUntilMinScore() {
		while (dealerHand.getTotal() < minScore || (dealerHand.aceIndex() != -1 && dealerHand.getTotal() < 18)) {
			// hit until >= minScore
			dealerHand.dealCard((this.dealSingleCard())); // dealer deals themself a card
		}
	}

	/**
	 * total amount from dealer
	 * 
	 * @return int total of dealers hand
	 */
	public int getTotal() {
		return dealerHand.getTotal();
	}

	/**
	 * Reshuffle the deck and clear Player cards
	 */
	public void reshuffle() {
		// Reset the Deck and shuffle. Also, clear all Player Cards.
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
			player.setCurrentHandIndex(0);
			for (int i = 0; i < player.numOfHands(); i++) { // iterates through players hands
				if (player.isBusted()) {

					player.getPlayerAccount().setCurrentWinStreak(0); // reset current Win streak

					if (player.getCurrentHandIndex() != player.numOfHands() - 1) {
						player.goToNextPlayerHand();// iterate to next player hand
						continue;
					}
				} else if (dealerHand.isBusted()) {
					player.receivePayout(false);
				} else if (player.getHandTotal() == dealerHand.getTotal()) {
					player.receivePayout(true);
				} else if (player.getHandTotal() > dealerHand.getTotal()) {
					player.receivePayout(false);
				} else {
					player.getPlayerAccount().setCurrentWinStreak(0); // reset current Win streak
				}

				player.getPlayerAccount().setBalance(player.getBalance());

				if (player.getCurrentHandIndex() != player.numOfHands() - 1) {
					player.goToNextPlayerHand();// iterate to next player hand
				}
			}
			player.setCurrentHandIndex(0);
		}
	}

	/**
	 * Reset all the players cards
	 */
	public void collectPlayerCards() {
		for (Player player : players) {
			player.discardCards();
		}
	}

	/**
	 * Used so the game can deal a card to a player
	 * 
	 * @return the top card from the deck
	 */
	public Card dealSingleCard() {
		return deck.getTopCard();
	}

	/**
	 * returns booelan of whether or not the dealers hand has 21
	 * 
	 * @return boolean value of if dealer's hand total == 21
	 */
	public boolean hasTwentyOne() {
		return dealerHand.isBlackJack();
	}

	public boolean hasVisibleAce() {
		return false;
	}

	@Override
	public String toString() {
		return "DEALER:\n" + this.getHand() + "\nTotal Score: " + this.getTotal();
	}

	/**
	 * returns the dealers hand
	 * 
	 * @return dealerHand which is the dealer's BlackJackHand
	 */
	public BlackjackHand getHand() {
		return dealerHand;
	}

	/**
	 * return arrayList of players
	 * 
	 * @return players which is an arrayList of players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * return the dealers hand
	 * 
	 * @return arraylist of the dealers Hand
	 */
	public ArrayList<Card> getDealerHand() {
		return dealerHand.getHand();
	}

	/**
	 * returns the dealers deck
	 * 
	 * @return deck which is the dealers deck
	 */
	public Deck getDeck() {
		return deck;
	}
}
