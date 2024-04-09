package controller_view;

import controller.BlackjackGame;
import controller.Game;
import model.Player;
import java.util.ArrayList;
import java.util.Scanner;
import model.Actions;

public class BlackjackConsole {

	private static BlackjackGame game;
	private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        game = new BlackjackGame();
        // hard coding in one player and one computer for now.
        game.addPlayer("p1", true);
        game.addPlayer("c1", false);
        boolean keepPlaying = true;
        while (keepPlaying) {
            game.startRound();
            while (!game.isGameOver) {
                Player player = game.getActivePlayer();
                System.out.println(game.getActivePlayer() + "\nWhat would you like to do? ('hit'/'stand'/'double')");
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

    private static void displayResults() {
        System.out.println(game.dealerString() + "\n");
        for (Player player : game.getPlayers()) {
            System.out.println(player + "\n");
        }
    }
}
