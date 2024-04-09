package controller;

import controller_view.BlackjackConsole;
import model.Dealer;
import model.Player;

import java.util.ArrayList;

public class Game {
	public static void main(String[] args) {
		BlackjackConsole view = new BlackjackConsole();
		Player p1 = new Player("p1", true);
		Player c1 = new Player("c1", false);
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(p1);
		players.add(c1);
		System.out.println(p1);
		System.out.println(c1);

		Dealer dealer = new Dealer(players);

		for (Player player : players) {
			dealer.collectBet(player, 20); // collect bet from each player
		}
		dealer.dealCards(); // deal cards to each player and the dealer
		if (dealer.hasTwentyOne()) {
			return;
		}

		p1.hit(dealer.dealSingleCard()); // This would be changed to prompt for input using the actions enum
		c1.hit(dealer.dealSingleCard()); // Would need a loop similar to the dealer hit
		while (!dealer.hit()) { // hit until >= 16
			System.out.println(dealer.isFolded());
		}
		System.out.println("p1 total:" + p1.getHandTotal());
		System.out.println("c1 total:" + c1.getHandTotal());
		System.out.println("dealer total:" + dealer.getTotal());

		dealer.payWinners(); // pays all winners. Does nothing if dealer won
		System.out.println(p1);
		System.out.println(c1);

		// splitting has to be handled more by the game, but essentially I have a
		// boolean attribute for whether we're handling splits. If the user splits, run
		// a separate while loop to handle the split first, then once they fold on the
		// split, turn split off, turn fold off, and then handle their original hand

		// TODO handle resetting 
	}
}
