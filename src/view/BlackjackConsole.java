package view;

import presenter.BlackjackGame;
import model.Player;
import model.PlayerAccount;

import java.util.Scanner;
import model.Actions;

/**
 * A java program that allows the player to interact with a blackjack game through a console view.
 */
public class BlackjackConsole {

	private static BlackjackGame game;
	private static final Scanner scanner = new Scanner(System.in);

    /**
     * entrypoint to the console view program. Contains pretty much all the logic for handling console input.
     * @param args the thing that every java program needs to include.
     */
    public static void main(String[] args) {
        game = new BlackjackGame();
        // hard coding in one player and one computer for now.
        game.addPlayer("p1", 100.0, true, new PlayerAccount("username", "password"));
        boolean keepPlaying = true;
        while (keepPlaying) {
            game.startRound();
            while (!game.isGameOver) {
                Player player = game.getActivePlayer();
                System.out.println();
                System.out.println(game.getActivePlayer() + "\nWhat would you like to do? for hand ## " + player.getCurrentHandIndex() + " ('hit'/'stand'/'double'/'split')");
                String userResponse = scanner.nextLine();
                try {
                    Actions action = Actions.valueOf(userResponse.toUpperCase());
                    boolean hasBust = game.makeMove(action);
                   
                    if (hasBust) {
                        // display players stats to signify what their score was. and inform them that they went over.
                        System.out.println(player);
                        System.out.println("bust!");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid action!");
                }
            }
            displayResults();
            System.out.println("Play another round? (y = yes, anything else = no)");
            if (!scanner.nextLine().equals("y")) {
                keepPlaying = false;
            }
        }
    }

    /**
     * displays the results of the game
     */
    private static void displayResults() {
        System.out.println(game.dealerString() + "\n");
        for (Player player : game.getPlayers()) {
            System.out.println(player + "\n");
        }
    }
}
