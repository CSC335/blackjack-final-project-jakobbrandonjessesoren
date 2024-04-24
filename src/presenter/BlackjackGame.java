package presenter;

import model.*;
import view.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BlackjackGame extends OurObservable {
	private ArrayList<Player> players;
	private ArrayList<Integer> playerBets;
	private Iterator<Player> iterator;
	private Dealer dealer;
	private Player activePlayer;
	private int activePlayerBet;
	public boolean isGameOver = false;
	public SoundController music;

	/**
	 * constructor for the BlackjackGame. initializes necessary objects.
	 */
	public BlackjackGame() {
		// NOTE: BlackJackGame and Dealer both reference the same ArrayList of players
		// This way, either can modify the list as necessary, and the other object will
		// be able to see those changes.
		players = new ArrayList<Player>();
		dealer = new Dealer(players);
		music = new SoundController();
	}

	public void addPlayer(String name, boolean isPlayer) {
		players.add(new Player(name, isPlayer));
	}

	/**
	 * sets the bet value to be used for the current player.
	 * @param bet integer bet value to be used
	 */
	public void setActivePlayerBet(int bet) {
		activePlayerBet = bet;
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

		for (Player player : players) {
			if (player == activePlayer) {
				dealer.collectBet(player, activePlayerBet); // Collect bet from the active player
			} else
				dealer.collectBet(player, 20); // collect bet from each player
		}
		dealer.dealCards(); // deal cards to each player and the dealer

		if (dealer.hasTwentyOne()) {
			isGameOver = true;
		}
		notifyObservers(this);
	}
	
	
	// TODO need to iterate through other possible hands if it is necessary
	/**
	 * given a string input of a move type, will perform the correct action. Will
	 * also update activePlayer and/or end the round as necessary.
	 *
	 * @apiNote Currently assumes that the active player is the one who made a move.
	 *          If we want to do online networking, we probably need to verify that
	 *          the request came from the correct player, but depends on how it's
	 *          setup.
	 * @param action string input from user saying what their move is.
	 */
	public boolean makeMove(Actions action) {
		if (action == Actions.HIT) {
			activePlayer.hit(dealer.dealSingleCard());
			if (activePlayer.isBusted()) {
				System.out.println("CHECK ME FOR BUSTED");
				
				if(!activePlayer.isInLastPlayerHand()) {
					System.out.println("CHECK ME FOR NOT IN LAST HAND OF SPLIT");
					activePlayer.goToNextPlayerHand(); // go to next hadn within the same player
				}
				else {
					if (iterator.hasNext()) {
					activePlayer = iterator.next();
					} else {
						endRound();
					}
				}
				notifyObservers(this);
				return true;
			}
		} else if (action == Actions.STAND) {
			if(!activePlayer.isInLastPlayerHand()) {
				activePlayer.goToNextPlayerHand(); // go to next hand within the same player
			}
			else {
				if (iterator.hasNext()) {
					activePlayer = iterator.next();
				} else {
					endRound();
			}
			}
		} else if (action == Actions.DOUBLE) {
			activePlayer.doubleDown(dealer.dealSingleCard());
			if(!activePlayer.isInLastPlayerHand()) {
				activePlayer.goToNextPlayerHand(); // go to next hand within the same player
			}
			else {
				if (iterator.hasNext()) {
					activePlayer = iterator.next();
				} else {
					endRound();
				}
			}
		} else if (action == Actions.SPLIT) {
			// TODO: Would I need to iterate?
			
			// splits the hand into 2
			if(activePlayer.getHand().isSplitable()) {
				Card card1 = dealer.dealSingleCard();
				Card card2 = dealer.dealSingleCard();
				activePlayer.split(card1, card2);
			}
			else {
				System.out.println("Not allowed to split on this hand");
			}
			
		}
		notifyObservers(this);
		return false;
	}

	/**
	 * performs necessary actions after all players have completed their turns. 1.
	 * dealer plays their turn 2. dealer pays out to winners 3. set isGameOver flag
	 * to true
	 */
	private void endRound() {
		playDealersTurn();
		dealer.payWinners();
		isGameOver = true;
		notifyObservers(this);
	}

	/**
	 * tells dealer to hit until it has reached the minimum score requirement.
	 */
	private void playDealersTurn() {
		dealer.hitUntilMinScore();

	}

	/**
	 * gets a reference to the currently active player
	 * @return the active player object
	 */
	public Player getActivePlayer() {
		return activePlayer;
	}

	/**
	 * gets string representation of dealer
	 * @return dealer.toString()
	 */
	public String dealerString() {
		return dealer.toString();
	}

	/**
	 * return list of players ingame.
	 * @return ArrayList of players
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * gets the BlackjackHand of the dealer
	 * @return dealer.getHand()
	 */
	public BlackjackHand getDealerHand() {
		return dealer.getHand();
	}

	/**
	 * checks whether the second card of the dealer is an ace, indicating that players can buy insurance
	 * @return true if players can buy insurance
	 */
	public boolean canBuyInsurance() { return (dealer.getDealerHand().size() == 2) && (dealer.getDealerHand().get(1).getValue() == 11); }
}
