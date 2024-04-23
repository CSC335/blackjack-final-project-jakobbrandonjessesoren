package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.Actions;
import presenter.BlackjackGame;
import javafx.scene.control.Label;

/**
 * custom gridpane that houses all the controls for interacting with blackjack
 * game
 */
public class BlackjackControlBar extends Pane {

	private BlackjackGame theGame;
	private Label activePlayerLabel;
	private Button hit, stand, doubleDown, split;
	private Button chip1, chip5, chip10, chip25, chip100, chip500;
	private Group chips, actions;

	private TextField betInput;
	private Button placeBet;
	private int bet;

	/**
	 * constructor. Calls methods necessary for initializing the custom gridpane.
	 * 
	 * @param theModel reference to the BlackjackGame object that this pane should
	 *                 control.
	 */
	public BlackjackControlBar(BlackjackGame theModel, String name) {
		theGame = theModel;
		initializePanel();
		initializeChips();
		this.showBetElements();
		declareButtonEvents();
		this.updateActivePlayerLabel(name);
	}

	/**
	 * Sets up all the ui elements for betting
	 */
	private void initializeChips() {
		chip1 = new Button();
		chip5 = new Button();
		chip10 = new Button();
		chip25 = new Button();
		chip100 = new Button();
		chip500 = new Button();
		placeBet = new Button("Place bet and Start Round");
		placeBet.setLayoutX(830);
		placeBet.setLayoutY(410);

		try {
			Image chipImage1 = new Image(new FileInputStream("images/chips/chip1.png"), 80, 80, false, false);
			ImageView chipImageView1 = new ImageView(chipImage1);
			chipImageView1.setEffect(new DropShadow(20, 5, 0, Color.BLACK));
			chip1.setGraphic(chipImageView1);
			chip1.setStyle("-fx-background-color: transparent");
			chip1.setLayoutX(800);
			chip1.setLayoutY(450);

			Image chipImage2 = new Image(new FileInputStream("images/chips/chip2.png"), 80, 80, false, false);
			ImageView chipImageView2 = new ImageView(chipImage2);
			chipImageView2.setEffect(new DropShadow(20, 5, 0, Color.BLACK));
			chip5.setGraphic(chipImageView2);
			chip5.setStyle("-fx-background-color: transparent");
			chip5.setLayoutX(900);
			chip5.setLayoutY(450);

			Image chipImage3 = new Image(new FileInputStream("images/chips/chip3.png"), 80, 80, false, false);
			ImageView chipImageView3 = new ImageView(chipImage3);
			chipImageView3.setEffect(new DropShadow(20, 5, 0, Color.BLACK));
			chip10.setGraphic(chipImageView3);
			chip10.setStyle("-fx-background-color: transparent");
			chip10.setLayoutX(800);
			chip10.setLayoutY(550);

			Image chipImage4 = new Image(new FileInputStream("images/chips/chip4.png"), 80, 80, false, false);
			ImageView chipImageView4 = new ImageView(chipImage4);
			chipImageView4.setEffect(new DropShadow(20, 5, 0, Color.BLACK));
			chip25.setGraphic(chipImageView4);
			chip25.setStyle("-fx-background-color: transparent");
			chip25.setLayoutX(900);
			chip25.setLayoutY(550);

			Image chipImage5 = new Image(new FileInputStream("images/chips/chip5.png"), 80, 80, false, false);
			ImageView chipImageView5 = new ImageView(chipImage5);
			chipImageView5.setEffect(new DropShadow(20, 5, 0, Color.BLACK));
			chip100.setGraphic(chipImageView5);
			chip100.setStyle("-fx-background-color: transparent");
			chip100.setLayoutX(800);
			chip100.setLayoutY(650);

			Image chipImage6 = new Image(new FileInputStream("images/chips/chip6.png"), 80, 80, false, false);
			ImageView chipImageView6 = new ImageView(chipImage6);
			chipImageView6.setEffect(new DropShadow(20, 5, 0, Color.BLACK));
			chip500.setGraphic(chipImageView6);
			chip500.setStyle("-fx-background-color: transparent");
			chip500.setLayoutX(900);
			chip500.setLayoutY(650);

			chips = new Group();
			chips.getChildren().addAll(chip1, chip5, chip10, chip25, chip100, chip500, placeBet);
			this.getChildren().add(chips);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Sets up all the ui elements for the actions for the player
	 */
	private void initializePanel() {
		activePlayerLabel = new Label(theGame.getActivePlayer() + "'s turn");
		hit = new Button();
		stand = new Button();
		doubleDown = new Button();
		split = new Button();
		try {
			Image hitImage = new Image(new FileInputStream("images/actions/hit.png"), 140, 90, false, true);
			ImageView hitImageView = new ImageView(hitImage);
			hit.setGraphic(hitImageView);
			hit.setStyle("-fx-background-color: transparent");
			hit.setLayoutX(2);
			hit.setLayoutY(380);
			this.getChildren().add(hit);

			Image standImage = new Image(new FileInputStream("images/actions/stand.png"), 140, 90, false, true);
			ImageView standImageView = new ImageView(standImage);
			stand.setGraphic(standImageView);
			stand.setStyle("-fx-background-color: transparent");
			stand.setLayoutX(2);
			stand.setLayoutY(470);
			this.getChildren().add(stand);

			Image doubleImage = new Image(new FileInputStream("images/actions/double.png"), 140, 90, false, true);
			ImageView doubleImageView = new ImageView(doubleImage);
			doubleDown.setGraphic(doubleImageView);
			doubleDown.setStyle("-fx-background-color: transparent");
			doubleDown.setLayoutX(2);
			doubleDown.setLayoutY(560);
			this.getChildren().add(doubleDown);

			Image splitImage = new Image(new FileInputStream("images/actions/split.png"), 140, 90, false, true);
			ImageView splitImageView = new ImageView(splitImage);
			split.setGraphic(splitImageView);
			split.setStyle("-fx-background-color: transparent");
			split.setLayoutX(2);
			split.setLayoutY(650);
			this.getChildren().add(split);

			actions = new Group();
			actions.getChildren().addAll(hit, stand, doubleDown, split);
			this.getChildren().add(actions);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		activePlayerLabel.setLayoutX(830);
		activePlayerLabel.setLayoutY(360);
		activePlayerLabel.setStyle("-fx-text-fill: white");
		this.getChildren().add(activePlayerLabel);

		betInput = new TextField("Bet amount: $0");
		betInput.setEditable(false);
		betInput.setStyle("-fx-highlight-fill: null; -fx-highlight-text-fill: null;");
		betInput.setLayoutY(380);
		betInput.setLayoutX(830);

		this.getChildren().add(betInput);
	}

	/**
	 * used when initializing panel. Declares the action events for the UI elements
	 */
	private void declareButtonEvents() {
		if (hit == null)
			return;
		hit.setOnAction(event -> {
			theGame.music.playSFX("CardsFlipCard.wav");
			theGame.makeMove(Actions.HIT);
		});

		if (stand == null)
			return;
		stand.setOnAction(event -> {
			theGame.makeMove(Actions.STAND);
		});

		if (doubleDown == null)
			return;
		doubleDown.setOnAction(event -> {
			theGame.makeMove(Actions.DOUBLE);
		});

		if (split == null)
			return;
		split.setOnAction(event -> {
			theGame.makeMove(Actions.SPLIT);
		});

		if (placeBet == null)
			return;
		placeBet.setOnAction(event -> {
			theGame.music.playSFX("PokerChipsSlide.wav");
			this.showActionElements();
			theGame.setActivePlayerBet(bet);
			theGame.startRound();
		});

		chip1.setOnAction(e -> {
			theGame.music.playSFX("PokerChipsHit.wav");
			bet += 1;
			betInput.setText("Bet amount: $" + bet);
		});

		chip5.setOnAction(e -> {
			theGame.music.playSFX("PokerChipsHit.wav");
			bet += 5;
			betInput.setText("Bet amount: $" + bet);
		});

		chip10.setOnAction(e -> {
			theGame.music.playSFX("PokerChipsHit.wav");
			bet += 10;
			betInput.setText("Bet amount: $" + bet);
		});

		chip25.setOnAction(e -> {
			theGame.music.playSFX("PokerChipsHit.wav");
			bet += 25;
			betInput.setText("Bet amount: $" + bet);
		});

		chip100.setOnAction(e -> {
			theGame.music.playSFX("PokerChipsHit.wav");
			bet += 100;
			betInput.setText("Bet amount: $" + bet);
		});

		chip500.setOnAction(e -> {
			theGame.music.playSFX("PokerChipsHit.wav");
			bet += 500;
			betInput.setText("Bet amount: $" + bet);
		});
	}

	/**
	 * Display the betting system on the Main GUI
	 */
	public void showBetElements() {
		chips.setVisible(true);
		actions.setVisible(false);
	}

	/**
	 * Display the action buttons on the Main GUI
	 */
	public void showActionElements() {
		chips.setVisible(false);
		actions.setVisible(true);
	}

	/**
	 * setter method for the text of the activePlayerLabel Label
	 * 
	 * @param playerName String representing what name to insert in the label.
	 */
	public void updateActivePlayerLabel(String playerName) {
		activePlayerLabel.setText(playerName + "'s turn");
	}
}
