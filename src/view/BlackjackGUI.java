package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.*;
import presenter.BlackjackGame;

/**
 * this class is a javafx application which allows a player to interact with a
 * blackjack game through a GUI.
 */
public class BlackjackGUI extends Application implements OurObserver<BlackjackGame> {

	public static void main(String[] args) {
		launch(args);
	}

	private BlackjackGame game;
	private PlayerAccountCollection playerCollection = new PlayerAccountCollection();
	private final int gameWidth = 1000;
	private final int gameHeight = 750;
	private Canvas canvas;
	private GraphicsContext gc;
	private Image background;
	private BlackjackControls controlBar;
	private LoginPane login;
	private Image chip1, chip5, chip10, chip25, chip100, chip500;
	private Image cCard, dCard, hCard, sCard;
	private boolean insurance = false;
	private boolean hideCard = false;

	/**
	 * Startup for the Blackjack GUI panel. Is run on startup. It initializes all
	 * the objects and systems.
	 * 
	 * @param stage javafx stage
	 * @throws FileNotFoundException thrown if background image cant be found
	 */
	@Override
	public void start(Stage stage) throws FileNotFoundException {

		// draw background (This should probably be moved to some render method so it's
		// redrawn every frame
		background = new Image(new FileInputStream("images/blackjack-background.png"));
		chip1 = new Image(new FileInputStream("images/chips/chip1.png"), 80, 80, false, false);
		chip5 = new Image(new FileInputStream("images/chips/chip2.png"), 80, 80, false, false);
		chip10 = new Image(new FileInputStream("images/chips/chip3.png"), 80, 80, false, false);
		chip25 = new Image(new FileInputStream("images/chips/chip4.png"), 80, 80, false, false);
		chip100 = new Image(new FileInputStream("images/chips/chip5.png"), 80, 80, false, false);
		chip500 = new Image(new FileInputStream("images/chips/chip6.png"), 80, 80, false, false);
		cCard = new Image(new FileInputStream("images/cCards/blank.png"), 122 / 2 + 3, 173 / 2 + 5, true, true);
		hCard = new Image(new FileInputStream("images/hCards/blank.png"), 122 / 2 + 3, 173 / 2 + 5, true, true);
		dCard = new Image(new FileInputStream("images/dCards/blank.png"), 122 / 2 + 3, 173 / 2 + 5, true, true);
		sCard = new Image(new FileInputStream("images/sCards/blank.png"), 122 / 2 + 3, 173 / 2 + 5, true, true);

		// create canvas
		canvas = new Canvas(gameWidth, gameHeight);
		gc = canvas.getGraphicsContext2D();

		try {
			// read in file into hashtable and then put into the object
			FileInputStream rawBytes = new FileInputStream("objects.ser");
			ObjectInputStream inFile = new ObjectInputStream(rawBytes);
			// if(inFile != null) {
			// set up playerCollection if there was something to read
			Hashtable<String, PlayerAccount> savedPlayerCollection = (Hashtable<String, PlayerAccount>) inFile
					.readObject();
			playerCollection.readInHashtable(savedPlayerCollection);
			// }

		} catch (IOException ioe) {
			System.out.println("Reading objects failed");
		} catch (ClassNotFoundException c) {
			System.out.println("Class is not correct");
		}

		login = new LoginPane(canvas, background, playerCollection);
		Scene loginScene = new Scene(login, gameWidth, gameHeight);
		stage.setScene(loginScene);
		stage.setResizable(false);
		stage.setTitle("Blackjack");
		game = new BlackjackGame();

		// Set the login pane to end when this anonymous function is ran
		login.setOnLoginSuccessListener(() -> {
			displayBlackjackGUI(stage); // Run the main GUI display
		});

		// on closing write Objects
		stage.setOnCloseRequest((event) -> {
			try {
				String fileName = "objects.ser";
				FileOutputStream bytesToDisk = new FileOutputStream(fileName);
				ObjectOutputStream outFile = new ObjectOutputStream(bytesToDisk);

				// write the hashTable to the file
				outFile.writeObject(playerCollection.getPlayerCollection());
				bytesToDisk.close();
			} catch (IOException ioe) {
				System.out.println("Writing objects failed"); // IOException ioe
			}

			Platform.exit();
			System.exit(0);

		});

		stage.show();
	}

	/**
	 * Display the actual Blackjack GUI elements
	 * 
	 * @param stage
	 */
	private void displayBlackjackGUI(Stage stage) {
		// initialize objects
		initializeGame();
		controlBar = new BlackjackControls(game, game.getPlayers().get(0));

		game.addObserver(this);

		Pane pane = new Pane();

		// add nodes to pane
		pane.getChildren().add(canvas);
		pane.getChildren().add(controlBar);

		// initialize scene
		Scene gameScene = new Scene(pane, gameWidth, gameHeight);
		stage.setScene(gameScene);

		// update game
		update(game);
		this.drawChips();
	}

	/**
	 * does initial setup for the game. Currently, this just means creating the new
	 * player objects
	 */
	private void initializeGame() {
		PlayerAccount loggedIn = playerCollection.getPlayer(login.getUsername());
		game.addPlayer(login.getUsername(), loggedIn.getBalance(), true, loggedIn);
	}

	/**
	 * updates the gui with the current game data. Uses an observer/observable
	 * system to do so. The observable (BlackjackGame) will notify this observer to
	 * update whenever the game state changes
	 * 
	 * @param theGame reference to the BlackjackGame object that changed
	 */
	@Override
	public void update(BlackjackGame theGame) {

		// update canvas
		gc.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());

		if (theGame.isGameOver)
			theGame.getActivePlayer().setCurrentHandIndex(theGame.getActivePlayer().numOfHands() - 1);

		// Display split hands if player has split
		if (theGame.getActivePlayer() != null && theGame.getActivePlayer().numOfHands() > 1) {
			this.showSplitHands(theGame.getActivePlayer().getCurrentHandIndex(), theGame);
		}

		for (Player player : theGame.getPlayers()) { // Display player cards
			ArrayList<Card> cards = player.getHand().getHand();
			for (int i = 0; i < cards.size(); i++) {
				Card card = player.getHand().getHand().get(i);

				// this long line makes the cards center around a point in the bottom center of
				// the canvas
				CardSprite cardSprite = new CardSprite(gc, canvas.getWidth() / 2.0 + (i - cards.size() / 2.0) * 120,
						canvas.getHeight() * 0.7 + 30, 0, card, false);

				cardSprite.draw();
			}
		}

		ArrayList<Card> dealerCards = theGame.getDealerHand().getHand();
		for (int i = 0; i < dealerCards.size(); i++) { // display dealer cards
			Card card = dealerCards.get(i);
			// Hide the second card of the dealer unless the dealer has a blackjack and
			// isn't showing an ace
			if (i == 1 && dealerCards.size() == 2
					&& ((!theGame.getActivePlayer().isFolded() && dealerCards.get(1).getRank() != Rank.ACE)
							|| !theGame.isGameOver))
				hideCard = true;
			// this long line makes the cards center around a point in the top center of the
			// canvas
			CardSprite cardSprite;
			if (theGame.getActivePlayer().numOfHands() > 1 && theGame.isGameOver)
				cardSprite = new CardSprite(gc, canvas.getWidth() / 2.0 + (i - dealerCards.size() / 2.0) * 120,
						canvas.getHeight() * 0.2 - 60, 0, card, hideCard);
			else
				cardSprite = new CardSprite(gc, canvas.getWidth() / 2.0 + (i - dealerCards.size() / 2.0) * 120,
						canvas.getHeight() * 0.2, 0, card, hideCard);

			cardSprite.draw();
			hideCard = false;
		}

		// update control
		if (theGame.getActivePlayer() == null)
			return;
		controlBar.updateActivePlayerLabel(theGame.getActivePlayer());

		// check if should ask players about insurance
		if (theGame.canBuyInsurance() && !this.insurance) {
			controlBar.showInsuranceElements(() -> {
				this.insurance = true;
				this.update(theGame);
				return;
			});

		} else if (theGame.isGameOver) { // check if should display game over
			gc.setFont(new Font(32));
			gc.setEffect(null);
			gc.setTextAlign(TextAlignment.CENTER);
			String resultsText = "ROUND OVER\n Dealer Score: " + game.getDealerHand().getTotal();
			for (Player player : game.getPlayers()) {
				resultsText += "\n" + player.getName() + " Score: " + player.getHandTotal();
				resultsText += "\n" + player.getName() + " Winnings: " + player.getRoundWinnings();
			}
			if (theGame.getActivePlayer().numOfHands() > 1)
				gc.fillText(resultsText, canvas.getWidth() / 2, canvas.getHeight() / 2 - 70);
			else
				gc.fillText(resultsText, canvas.getWidth() / 2, canvas.getHeight() / 2);
			this.game.discardCards();
			controlBar.showBetElements();
			drawChips();
			this.insurance = false;
		}
	}

	/**
	 * shows the gui element for the split hands
	 * 
	 * @param numOfHands: number of hands currently held by the player
	 * @param theGame: the game of blackjack currently being played
	 */
	private void showSplitHands(int numOfHands, BlackjackGame theGame) {
		ArrayList<BlackjackHand> hands = theGame.getActivePlayer().getArrayListHand();
		Image card = null;
		for (int i = 0; i < numOfHands; i++) {
			switch (hands.get(i).getHand().get(0).getSuit()) { // Check the suit of the first card in each hand
			case SPADES:
				card = sCard;
				break;
			case CLUBS:
				card = cCard;
				break;
			case DIAMONDS:
				card = dCard;
				break;
			case HEARTS:
				card = hCard;
				break;
			}

			gc.drawImage(card, canvas.getWidth() / 2.0 + (i - numOfHands / 2.0) * 65, canvas.getHeight() * 0.7 - 70);
			gc.setFont(new Font(24));
			gc.fillText(hands.get(i).getTotal() + "", canvas.getWidth() / 2.0 + (i - numOfHands / 2.0) * 65 + 32,
					canvas.getHeight() * 0.7 - 20);
		}

	}

	/**
	 * draws the chip gui elements
	 */
	private void drawChips() {
		gc.drawImage(chip1, 820, 454);
		gc.drawImage(chip1, 815, 454);
		gc.drawImage(chip5, 920, 454);
		gc.drawImage(chip5, 915, 454);
		gc.drawImage(chip10, 820, 554);
		gc.drawImage(chip10, 815, 554);
		gc.drawImage(chip25, 920, 554);
		gc.drawImage(chip25, 915, 554);
		gc.drawImage(chip100, 820, 654);
		gc.drawImage(chip100, 815, 654);
		gc.drawImage(chip500, 920, 654);
		gc.drawImage(chip500, 915, 654);
	}

}
