package model;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerAccount implements Serializable {

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private double balance;

	private double biggestBet; // done
	private double biggestAmountWon; // done
	private int currentWinStreak; // done
	private int longestWinStreak; // done
	private double lowestBalance; // done
	private double highestBalance; // done
	private ArrayList<Integer> winRate;
	private ArrayList<Double> previousBalances;

	public PlayerAccount(String username, String password) {
		this.username = username;
		this.password = password;
		balance = 1000.0;
		biggestBet = 0;
		longestWinStreak = 0;
		lowestBalance = 1000.0;
		highestBalance = 1000.0;
		winRate = new ArrayList<Integer>();
		previousBalances = new ArrayList<Double>();
		previousBalances.add(balance);
	}

	/**
	 * Gets the player's username
	 * 
	 * @return String username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the player's username
	 * 
	 * @return String password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Gets the players biggest amount won
	 * 
	 * @return
	 */
	public double getBiggestAmountWon() {
		return biggestAmountWon;
	}

	/**
	 * Gets the players balance
	 * 
	 * @return int balance
	 */
	public double getBalance() {
		return balance;
	}

	/**
	 * Gets the players biggestBet
	 * 
	 * @return in biggestBet
	 */
	public double getBiggestBet() {
		return biggestBet;
	}

	/**
	 * Gets the current win streak
	 * 
	 * @return int current win streak
	 */
	public int getCurrentWinStreak() {
		return currentWinStreak;
	}

	/**
	 * Gets the longest win streak
	 * 
	 * @return int current win streak
	 */
	public int getLongestWinStreak() {
		return longestWinStreak;
	}

	/**
	 * Gets the lowest Balance
	 * 
	 * @return int lowestBalance
	 */
	public double getLowestBalance() {
		return lowestBalance;
	}

	/**
	 * Gets the highest balance
	 * 
	 * @return int highestBalance
	 */
	public double getHighestBalance() {
		return highestBalance;
	}

	/**
	 * Return the ArrayList of previous player balances
	 * 
	 * @return ArrayList<Double> previousBalances
	 */
	public ArrayList<Double> getPreviousBalances() {
		return previousBalances;
	}

	/**
	 * Return the win rate of the current player on a range of 1 to -1
	 * 
	 * @return a double indicating the win ratio
	 */
	public double getWinRatio() {
		double sum = 0;
		for (int num : winRate) {
			sum += num;
		}
		if (winRate.size() == 0)
			return 0;
		return sum / winRate.size();
	}

	/**
	 * Return the total amount of games the user has played
	 * 
	 * @return an int of the total amount
	 */
	public int getTotalGamesPlayed() {
		return winRate.size();
	}

	/**
	 * Sets the player's username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Sets the player's username
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Sets the new biggestAmount won by the player
	 * 
	 * @param amount int new amount
	 */
	public void setBiggestAmountWon(double amount) {
		biggestAmountWon = amount;
	}

	/**
	 * Sets the players balance
	 */
	public void setBalance(double balance) {
		this.previousBalances.add(balance);
		this.balance = balance;
	}

	/**
	 * Sets the players biggestBet
	 */
	public void setBiggestBet(double bet) {
		biggestBet = bet;
	}

	/**
	 * Sets the current win streak
	 */
	public void setCurrentWinStreak(int newStreak) {
		if (newStreak > currentWinStreak)
			winRate.add(1);
		else if (newStreak == 0)
			winRate.add(-1);
		else if (newStreak == currentWinStreak)
			winRate.add(0);
		currentWinStreak = newStreak;
	}

	/**
	 * Sets the longest win streak
	 */
	public void setLongestWinStreak(int winStreak) {
		longestWinStreak = winStreak;
	}

	/**
	 * Sets the lowest Balance
	 */
	public void setLowestBalance(double balance) {
		lowestBalance = balance;
	}

	/**
	 * Sets the highest balance
	 */
	public void setHighestBalance(double balance) {
		highestBalance = balance;
	}

}
