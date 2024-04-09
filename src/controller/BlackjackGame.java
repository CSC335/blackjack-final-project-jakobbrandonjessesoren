package controller;

import model.BlackjackHand;
import model.Dealer;
import model.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import model.Actions;
import model.Card;

public class BlackjackGame {
	private ArrayList<Player> players;
	private Iterator<Player> iterator;
	private Dealer dealer;
	private Player activePlayer;
	public boolean isGameOver = false;

	public BlackjackGame() {
		// NOTE: BlackJackGame and Dealer both reference the same ArrayList of players
		// This way, either can modify the list as necessary, and the other object will
		// be able to see those changes.
		players = new ArrayList<Player>();
		dealer = new Dealer(players);
	}

	public void addPlayer(String name, boolean isPlayer) {
		players.add(new Player(name, isPlayer));
	}

	/**
	 * starts a round by collecting everyone's bets and dealing out cards.
	 *
	 * @apiNote currently uses a fixed bet of 20. Eventually need players to choose
	 *          their own bets
	 */
	public void startRound() {
		// sets up iterator and assigns activePlayer to the first player
		try {
			iterator = players.iterator();
			activePlayer = iterator.next();
		} catch (NoSuchElementException e) {
			System.out.println("ERROR: No players have been added to the game!");
			return;
		}
		isGameOver = false; // reset isGameOver flag
		dealer.dealCards();
		for (Player player : players) {
			dealer.collectBet(player, 20); // collect bet from each player
		}
		dealer.dealCards(); // deal cards to each player and the dealer
		if (dealer.hasTwentyOne()) {
			isGameOver = true;
		}
	}

	/**
	 * given a string input of a move type, will perform the correct action
	 * @apiNote Currently assumes that the active player is the one who made a move.
	 * 
	 *          If we want to do online networking, we probably need to verify that
	 *          the request came from the correct player, but depends on how it's
	 *          setup.
	 * @param action string input from user saying what their move is.
     * @return bool stating whether player bust
	 */
     

	/**
	 * tells dealer to hit until it has reached the minimum score requirement.
	 */
	private void playDealersTurn() {
		dealer.hitUntilMinScore();
	}

    public Player getActivePlayer() {
        return activePlayer;
    }
    public String dealerString() { return dealer.toString(); }
	public ArrayList<Player> getPlayers() {
		return players;
	}

	public Player getActivePlayer() {
		return activePlayer;
	}

	public ArrayList<Card> getDealerHand() {
		return dealer.getDealerHand();
	}
}
