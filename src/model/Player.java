package model;

/**
 * Represents a single player in a game of Blackjack
 * 
 * @author Jakob Garcia
 */
public class Player {
	private double balance;
	private BlackjackHand hand; // main hand of Blackjack
	private BlackjackHand splitHand; // second hand for splitting
	private String playerName;
	private double bet = 0; // main bet
	private double splitBet = 0; // second bet for splitting
	private boolean folded = false; // indicates if the player has folded
	private boolean split = false;
	private boolean hasCompletedTurn = false;
	private boolean isPlayer; // indicates whether or not the player is a computer

	// Construct a player with a name and indicate whether it is a computer
	public Player(String name, boolean isPlayer) {
		balance = 100.00;
		playerName = name;
		hand = new BlackjackHand();
		this.isPlayer = isPlayer;
	}

	// Construct a player with a given balance and whether it is a computer
	public Player(String name, double bal, boolean isPlayer) {
		balance = bal;
		playerName = name;
		hand = new BlackjackHand();
		this.isPlayer = isPlayer;

	}

	/*
	 * Receive cards from the dealer, and add them to their BlackjackHand
	 */
	public void receiveCards(Card dealtCard) {
		hand.dealCard(dealtCard);
	}

	/*
	 * Place the bets for this round of blackjack
	 */
	public void placeBet(double amount) {
		balance -= amount;
		bet = amount;
	}

	/*
	 * Receive payout for this bet
	 */
	public void receivePayout(boolean draw) {
		// Receive pay-out

		if (split) {
			if (draw)
				balance += splitBet;
			else if (splitHand.isBlackJack())
				balance += splitBet * 2.5;
			else
				balance += splitBet * 2;
		} else {
			if (draw)
				balance += bet;
			else if (hand.isBlackJack())
				balance += bet * 2.5;
			else
				balance += bet * 2;
		}
	}

	/*
	 * Allows the player to hit and receive a card. Requires the Game to pass the
	 * card from the dealer
	 */
	public boolean hit(Card card) {
		if (split) {
			splitHand.dealCard(card);
			if (splitHand.isBusted())
				folded = true;
			return splitHand.isBusted();
		} else {
			hand.dealCard(card);
			if (hand.isBusted())
				folded = true;
			return hand.isBusted();
		}
	}

	/*
	 * Allows the player to double down and receive a card. Requires the Game to
	 * pass the card from the dealer. Returns true if busted
	 */
	public boolean doubleDown(Card card) {
		if (split) {
			balance -= bet;
			splitBet *= 2;
			splitHand.dealCard(card);
			folded = true;
			return splitHand.isBusted();
		} else {
			balance -= bet;
			bet *= 2;
			hand.dealCard(card);
			folded = true;
			return hand.isBusted();
		}
	}

	/*
	 * Allows the player to split once and play two hands. Requires the game to
	 * handle swapping between both hands
	 */
	public void split() {
		splitHand = new BlackjackHand();
		splitHand.dealCard(hand.split());
		split = true;
		balance -= bet;
		splitBet = bet;
	}

	/*
	 * Resets values needed for next game
	 */
	public void discardCards() {
		folded = false;
		split = false;
		hand = new BlackjackHand();
	}

	public double checkBalance() {
		return balance;
	}

	public String getName() {
		return playerName;
	}

	public boolean hasBlackjack() {
		return hand.isBlackJack();
	}

	public boolean isBusted() {
		return hand.isBusted();
	}

	public boolean isPlayer() {
		return isPlayer;
	}

	public double getBet() {
		return bet;
	}

	public int getHandTotal() {
		if (split)
			return splitHand.getTotal();
		return hand.getTotal();
	}

	public void fold() {
		folded = true;
	}

	public boolean isFolded() {
		return folded;
	}

	public boolean isSplit() {
		return split;
	}
	@Override
	public String toString() { return playerName + "\nBalance: " + balance + "\n" + hand + "\nTotal Score: " + hand.getTotal(); }

}
