package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import model.*;


public class DealerTest {
	@Test
	public void testConstructors() {
		ArrayList<Player> playerList = new ArrayList<>();
		Player pl1 = new Player("player1", true, new PlayerAccount("username", "username"));
		Player pl2 = new Player("player2", true, new PlayerAccount("username", "username"));
		Player pl3 = new Player("player3", true, new PlayerAccount("username", "username"));
		Player pl4 = new Player("player4", true, new PlayerAccount("username", "username"));
		
		playerList.add(pl1);
		playerList.add(pl2);
		playerList.add(pl3);
		playerList.add(pl4);
		
		
		Dealer dealer = new Dealer(playerList);
		System.out.println(dealer.getDeck().getTotalCards());
		
		assertEquals(dealer.getPlayers(), playerList);
		
		for(int i = 0; i < 150; i++) {
		// one round of blackjack
		dealer.collectBet(pl1, 2);
		dealer.collectBet(pl2, 2);
		dealer.collectBet(pl3, 2);
		dealer.collectBet(pl4, 2);
		dealer.dealCards();
		
		if(dealer.hasTwentyOne()) {
			dealer.payWinners();
		}
		else {
			pl1.hit(dealer.dealSingleCard());
			pl1.hit(dealer.dealSingleCard());
			pl1.hit(dealer.dealSingleCard());
			System.out.println(Actions.HIT);
			dealer.hitUntilMinScore();
			dealer.payWinners();
		}
		System.out.println(dealer.getDealerHand());
		System.out.println(dealer.toString());
		
		dealer.reshuffle();
		System.out.println(dealer.getDeck().getDeckTopIndex());
		}
		
	}
}
