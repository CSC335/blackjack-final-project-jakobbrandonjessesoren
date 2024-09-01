 package model;

import java.util.ArrayList;

 /**
  * a class representing a hand in blackjack. contains various methods for manipulating the jand
  */
 public class BlackjackHand implements Comparable<BlackjackHand> {
	private ArrayList<Card> hand;
	private boolean busted;
	private int total;
	private boolean fold = false;
	private double bet = 0;
	private boolean isDoubledDown;
	
	/**
	 *  constructs a blackjack hand
	 */
	public BlackjackHand() {
		hand = new ArrayList<>();
		busted = false;
		total = 0;
	}

	/**
	 * compares to Hand totals returning > 0 if this hand total is more, 0 if they
	 * are equal, and < 0 if other hand total is more
	 * 
	 * @param otherHand another version of a blackjack hand
	 * 
	 * @return an integer value representative of the comparison 
	 */
	@Override
	public int compareTo(BlackjackHand otherHand) {
		return total - otherHand.getTotal();
	}

	/**
	 * returns true if hand is a "blackJack" hand ie (has 2 cards,
	 *  an ace and total == 21)
	 *  
	 *  @return boolean value of whether or not the hand is a blackjack hand
	 */
	public boolean isBlackJack() {
		int aceIndex = aceIndex();
		return (hand.size() == 2 && total == 21 && aceIndex != -1);
	}

	/**
	 * returns true if hand value == 21
	 * 
	 * @return returns true if hand total == 21 false otherwise
	 */
	public boolean isTwentyOne() {
		return total == 21;
	}

//	/**
//	 * Returns true if the hand can be split (both cards are equal value)
//	 */
//	public boolean canSplit() {
//		return hand.get(0).compareTo(hand.get(1)) == 0;
//	}
//
//	/**
//	 * Returns the second card so the hand can be split
//	 */
//	public Card split() {
//		return hand.remove(1);
//	}

	/**
	 * Deals a card to current hand updating the hand, total, and boolean busted if
	 * total > 21
	 * 
	 * @param dealtCard is a single card that is dealt to the player
	 * 
	 */
	public void dealCard(Card dealtCard) {
		// add card to hand
		//System.out.println(hand.get(0) + " now value " + hand.get(0).getValue() + " now rank " + hand.get(0).getRank());
		hand.add(dealtCard);
		total += dealtCard.getValue();
		
		//System.out.println("Card at 0 " + hand.get(0) + " " + hand.get(0).getValue());
		//System.out.println();
		// if total over 21 check for aces
		if (total + dealtCard.getValue() > 21) {

			// replace aces value with one, one at a time until no more aces
			// with a value of 11 or total is under 21
			int aceIndex = this.aceIndex();
			while (aceIndex != -1 && total > 21) {

				Card removedAce = hand.remove(aceIndex);
				total -= 10;
				hand.add(aceIndex, new Card(Rank.ONE, removedAce.getSuit()));
				aceIndex = this.aceIndex();
			}

			if (total > 21) {
				busted = true;
			}
		}
	}

	/**
	 * returns -1 if no ace is present else returns index of first ace
	 * 
	 * @return an index of ace in hand arrayList but -1 if there is none
	 */
	public int aceIndex() {
		for (int i = 0; i < hand.size(); i++) {
			if (hand.get(i).getValue() == 11) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Returns true if hand is splittable and false otherwise
	 * @return boolean value based on if hand is splitable
	 */
	public boolean isSplitable() {
		if(hand.size() == 2) {
			// aces check
			if(hand.get(0).getValue() == 1 && hand.get(1).getValue() == 11) {
				// change first ace value from 1 back to 11
				return true;
			}
			if(hand.get(0).getValue() == hand.get(1).getValue()) {
				// face cards and 10 card
				if(hand.get(0).getValue() == 10) {
					if(hand.get(0).getRank() == hand.get(1).getRank()) {
						return true;
					}
					else {
						return false;
					}
				}
				// otherwise return true
				else {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Sets the total bet amount based upon the parameter
	 * @param betAmount double representing amount bet on this hand
	 */
	public void setBet(double betAmount) {
		bet = betAmount;

	}
	
	/**
	 * returns the total bet amount
	 * @return double representing bet amount
	 */
	public double getBet() {
		return bet;
	}

	/**
	 * 
	 * @return total amount from hand
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * 
	 * @return boolean value to see if the hand is under 22
	 */
	public boolean isBusted() {

		return busted;
	}
	
	/**
	 * fetches the last card dealt in hand that can be split and returns 
	 * it and then removes it from the current hand
	 * @return Card 2nd card in this BlackjackHand
	 */
	public Card getCardForSplitting() {
		
		Card splitCard = hand.get(1);
		hand.remove(1);
		total -= splitCard.getValue();
		if(hand.get(0).getValue() == 1) {
			hand.set(0, new Card(Rank.ACE, hand.get(0).getSuit()));
			total += 10;
		}
		return splitCard;
	}
	/**
	 * 
	 * @return arrayList reference hand
	 */
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	/**
	 * returns true if the current hand is folded
	 * @return boolean folded
	 */
	public boolean folded() {
		return fold;
	}

	 /**
	  * returns true if the player doubled down on this hand
	  * @return boolean isDoubledDown
	  */
	public boolean isDoubledDown() {
		return isDoubledDown;
	}
	 /**
	  * sets the double down flag for the hand
	  */
	 public void setDoubleDown(boolean bool) {
		 isDoubledDown = bool;
	 }

	 /**
	 * sets the fold variable to either true or false dependent on the 
	 * value of truthValue
	 * @param truthValue boolean either true or false
	 */
	public void setFold(boolean truthValue) {
		fold = truthValue;
	}

	public String toString() { return hand.toString() + " bet " + this.bet + " total " + this.total; }
}
