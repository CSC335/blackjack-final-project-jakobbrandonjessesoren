package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.*;
import presenter.BlackjackGame;

/**
 * this class is a javafx application which allows a player to interact with a blackjack game through a GUI.
 */
public class BlackjackGUI extends Application implements OurObserver<BlackjackGame> {
	
	public static void main(String[]args) {
		launch(args);
	}
	BlackjackGame game;
	final int gameWidth = 1000;
	final int gameHeight = 750;
	Canvas canvas;
	GraphicsContext g;
	Image background;
	private BlackjackControlBar controlBar;

	/**
	 * Startup for the Blackjack GUI panel. Is run on startup. It initializes all the objects and systems.
	 * @param stage javafx stage
	 * @throws FileNotFoundException thrown if background image cant be found
	 */
	@Override
	public void start(Stage stage) throws FileNotFoundException {


		// initialize objects
		game = new BlackjackGame();
		controlBar = new BlackjackControlBar(game);

		game.addObserver(this);

		BorderPane pane = new BorderPane();


		// create canvas
		canvas = new Canvas(gameWidth,gameHeight-50);
		g = canvas.getGraphicsContext2D();

		// draw background (This should probably be moved to some render method so it's redrawn every frame
		background = new Image(new FileInputStream("images/blackjack-background.png"));

		// add nodes to pane
		pane.setCenter(canvas);
		pane.setBottom(controlBar);

		// initialize scene
		Scene scene = new Scene(pane, gameWidth, gameHeight);
		stage.setScene(scene);

		// initialize game
		initializeGame();
		update(game);
		// This is a timer that runs at 60 frames per second. It can be used for rendering the canvas
//		final long startNanoTime = System.nanoTime();
//		new AnimationTimer() {
//			public void handle(long currentNanoTime) {
//                try {
//					cardSpinAnimation(startNanoTime, currentNanoTime);
//				} catch (FileNotFoundException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//		}.start();

		stage.show();
	}

//	/**
//	 *
//	 * @throws FileNotFoundException
//	 */
//	private void createSampleCard() throws FileNotFoundException {
//		CardSprite card = new CardSprite(g, 100, 100, 0, new Card(Rank.ACE, Suit.CLUBS));
//		card.draw();
//		CardSprite card1 = new CardSprite(g, 300, 300, 0, new Card(Rank.ACE, Suit.DIAMONDS));
//		card1.draw();
//		CardSprite card2 = new CardSprite(g, 500, 500, 0, new Card(Rank.ACE, Suit.HEARTS));
//		card2.draw();
//	}
//
//
//	private void cardSpinAnimation(long startNanoTime, long currentNanoTime) throws FileNotFoundException {
//		double t = (currentNanoTime - startNanoTime) / 1000000000.0;
//		double x = 232 + 128 * Math.cos(t);
//		double y = 232 + 128 * Math.sin(t);
//
//		// this might be running slow because it's initializing three new card objects every frame. instead of reusing objects.
//		CardSprite card = new CardSprite(g, x+200, y, 0, new Card(Rank.ACE, Suit.SPADES));
//		card.draw();
//		CardSprite card1 = new CardSprite(g, x, y-90, 0, new Card(Rank.ACE, Suit.CLUBS));
//		card1.draw();
//		CardSprite card2 = new CardSprite(g, x-100, y, 0, new Card(Rank.ACE, Suit.HEARTS));
//		card2.draw();
//
//	}

	/**
	 * does initial setup for the game. Currently, this just means creating the new player objects
	 */
	private void initializeGame() {
		game.addPlayer("Player 1", true);
	}

	/**
	 * updates the gui with the current game data. Uses an observer/observable system to do so.
	 *  The observable (BlackjackGame) will notify this observer to update whenever the game state changes
	 * @param theGame reference to the BlackjackGame object that changed
	 */
	@Override
	public void update(BlackjackGame theGame) {

		//update canvas
		g.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());

		for (Player player: theGame.getPlayers()) {
			ArrayList<Card> cards = player.getHand().getHand();
			for (int i = 0; i < cards.size(); i++) {
				Card card = player.getHand().getHand().get(i);

				// this long line makes the cards center around a point in the bottom center of the canvas
				CardSprite cardSprite = new CardSprite(g, canvas.getWidth() / 2.0 + (i - cards.size()/2.0) * 120, canvas.getHeight() * 0.7, 0, card);

				cardSprite.draw();
			}
		}

		ArrayList<Card> dealerCards = theGame.getDealerHand().getHand();
		for (int i = 0; i < dealerCards.size(); i++) {
			Card card = dealerCards.get(i);

			// this long line makes the cards center around a point in the top center of the canvas
			CardSprite cardSprite = new CardSprite(g, canvas.getWidth() / 2.0 + (i - dealerCards.size()/2.0) * 120, canvas.getHeight() * 0.2, 0, card);

			cardSprite.draw();
		}

		// update control
		if (theGame.getActivePlayer() == null)
			return;
		controlBar.updateActivePlayerLabel(theGame.getActivePlayer().getName());

		//check if should display game over
		if (theGame.isGameOver) {
			g.setFont(new Font(32));
			g.setTextAlign(TextAlignment.CENTER);
			String resultsText = "ROUND OVER\n Dealer Score: " + game.getDealerHand().getTotal();
			for (Player player : game.getPlayers()) {
				resultsText += "\n" + player.getName() + " Score: " + player.getHandTotal();
				resultsText += "\n" + player.getName() + " Winnings: " + player.getBet();
			}
			g.fillText(resultsText, canvas.getWidth() / 2, canvas.getHeight() / 2);

		}
	}
}
