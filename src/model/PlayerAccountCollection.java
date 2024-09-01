package model;

import java.util.Hashtable;

public class PlayerAccountCollection {

	private Hashtable<String, PlayerAccount> playerCollection;

	public PlayerAccountCollection() {
		playerCollection = new Hashtable<String, PlayerAccount>();
	}

	/**
	 * Returns the hashtable that holds all Player accounts
	 * 
	 * @return Hashtable playerCollection
	 */
	public Hashtable getPlayerCollection() {
		return playerCollection;
	}

	public void readInHashtable(Hashtable<String, PlayerAccount> playerCollection) {
		if (playerCollection != null) {
			this.playerCollection = playerCollection;
		}
	}

	/**
	 * adds Player to the hashtable if it is not already present returning true if
	 * done successfully and false if not
	 * 
	 * @param username String players username
	 * @param password String players passowrd
	 * @return boolean based on if process is done correctly
	 */
	public boolean addPlayer(String username, String password) {
		if (!this.checkForUsername(username)) {
			PlayerAccount playerToAdd = new PlayerAccount(username, password);
			playerCollection.put(username, playerToAdd);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gets the player account based upon their username
	 * 
	 * @param username String the player's username
	 * @return playerAccount based on player Username
	 */
	public PlayerAccount getPlayer(String username) {
		return playerCollection.get(username);
	}

	/**
	 * Changes players username from an old one to a new one
	 * 
	 * @param oldUsername String username to replace
	 * @param newUsername String replacing username
	 */
	public void changeUsername(String oldUsername, String newUsername) {
		playerCollection.get(oldUsername).setUsername(newUsername);
	}

	/**
	 * Changes players password to a new one
	 * 
	 * @param username    String
	 * @param newPassword String replacing password
	 */
	public void changePassword(String username, String newPassword) {
		playerCollection.get(username).setPassword(newPassword);
	}

	/**
	 * Checks if the username already exists in the database
	 * 
	 * @param Username String representing username
	 * @return boolean based on if username is present already
	 */
	public boolean checkForUsername(String Username) {
		return playerCollection.containsKey(Username);
	}

	/**
	 * Checks if a given password is correct
	 * 
	 * @param username      players username
	 * @param passwordGuess user guess at password
	 * @return boolean based on if the inputted password is correct
	 */
	public boolean checkPassword(String username, String passwordGuess) {
		return playerCollection.get(username).getPassword().equals(passwordGuess);
	}
}
