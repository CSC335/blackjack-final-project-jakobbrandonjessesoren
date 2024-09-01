package view;

import javafx.geometry.Point2D;
import javafx.scene.text.Text;

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
import model.Player;
import presenter.BlackjackGame;
import javafx.scene.control.Label;

/**
 * custom gridpane that houses all the controls for interacting with blackjack
 * game
 */
public class BlackjackControls extends Pane {

	private BlackjackGame theGame;
	private Label activePlayerLabel;
	private Button hit, stand, doubleDown, split;
	private Button chip1, chip5, chip10, chip25, chip100, chip500;
	private Group chips, actions, insurance;

	private TextField betInput;
	private Button placeBet, yes, no, resetBet;
	private int bet;
	private Runnable listener;

	/**
	 * constructor. Calls methods necessary for initializing the custom gridpane.
	 * 
	 * @param theModel reference to the BlackjackGame object that this pane should
	 *                 control.
	 */
	public BlackjackControls(BlackjackGame theModel, Player player) {
		theGame = theModel;
		initializePanel();
		initializeChips();
		this.showBetElements();
		declareButtonEvents();
		this.updateActivePlayerLabel(player);
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
		resetBet = new Button("Reset bet");
		placeBet.setLayoutX(830);
		placeBet.setLayoutY(410);
		resetBet.setLayoutX(760);
		resetBet.setLayoutY(410);

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
			chips.getChildren().addAll(chip1, chip5, chip10, chip25, chip100, chip500, placeBet, resetBet);
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

		Label insurance = new Label("Purchase insurance of half your bet?");
		insurance.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");
		yes = new Button();
		no = new Button();

		insurance.setLayoutX(365);
		insurance.setLayoutY(370);
		yes.setLayoutX(348);
		yes.setLayoutY(400);
		no.setLayoutX(498);
		no.setLayoutY(400);
		this.insurance = new Group();
		this.insurance.getChildren().addAll(insurance, yes, no);
		this.insurance.setVisible(false);
		try {
			Image yesImage = new Image(new FileInputStream("images/actions/yes.png"), 140, 90, false, true);
			ImageView yesImageView = new ImageView(yesImage);
			yes.setGraphic(yesImageView);
			yes.setStyle("-fx-background-color: transparent");

			Image noImage = new Image(new FileInputStream("images/actions/no.png"), 140, 90, false, true);
			ImageView noImageView = new ImageView(noImage);
			no.setGraphic(noImageView);
			no.setStyle("-fx-background-color: transparent");

			Image hitImage = new Image(new FileInputStream("images/actions/hit.png"), 140, 90, false, true);
			ImageView hitImageView = new ImageView(hitImage);
			hit.setGraphic(hitImageView);
			hit.setStyle("-fx-background-color: transparent");
			hit.setLayoutX(2);
			hit.setLayoutY(370);
			this.getChildren().add(hit);

			Image standImage = new Image(new FileInputStream("images/actions/stand.png"), 140, 90, false, true);
			ImageView standImageView = new ImageView(standImage);
			stand.setGraphic(standImageView);
			stand.setStyle("-fx-background-color: transparent");
			stand.setLayoutX(2);
			stand.setLayoutY(460);
			this.getChildren().add(stand);

			Image doubleImage = new Image(new FileInputStream("images/actions/double.png"), 140, 90, false, true);
			ImageView doubleImageView = new ImageView(doubleImage);
			doubleDown.setGraphic(doubleImageView);
			doubleDown.setStyle("-fx-background-color: transparent");
			doubleDown.setLayoutX(2);
			doubleDown.setLayoutY(550);
			this.getChildren().add(doubleDown);

			Image splitImage = new Image(new FileInputStream("images/actions/split.png"), 140, 90, false, true);
			ImageView splitImageView = new ImageView(splitImage);
			split.setGraphic(splitImageView);
			split.setStyle("-fx-background-color: transparent");
			split.setLayoutX(2);
			split.setLayoutY(640);
			this.getChildren().add(split);

			actions = new Group();
			actions.getChildren().addAll(hit, stand, doubleDown, split);
			this.getChildren().addAll(actions, this.insurance);

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
			doubleDown.setVisible(false);
			split.setVisible(false);
			// Maintain controls if busted on a split hand, but not the last hand
			if (theGame.makeMove(Actions.HIT) && !theGame.isGameOver)
				this.showActionElements();

		});
		hit.setOnMouseEntered(e -> {
			AnimationLibrary.scaleUp(hit);
		});
		hit.setOnMouseExited(e -> {
			AnimationLibrary.scaleDown(hit);
		});

		if (stand == null)
			return;
		stand.setOnAction(event -> {
			doubleDown.setVisible(true);
			split.setVisible(true);
			theGame.makeMove(Actions.STAND);
			theGame.music.playSFX("CardsFlipCard.wav");
			theGame.music.playSFX("PokerChipsSlide2.wav");
		});
		stand.setOnMouseEntered(e -> {
			AnimationLibrary.scaleUp(stand);
		});
		stand.setOnMouseExited(e -> {
			AnimationLibrary.scaleDown(stand);
		});

		if (doubleDown == null)
			return;
		doubleDown.setOnAction(event -> {
			theGame.makeMove(Actions.DOUBLE);
			theGame.music.playSFX("CardsFlipCard.wav");
			theGame.music.playSFX("PokerChipsSlide2.wav");
		});
		doubleDown.setOnMouseEntered(e -> {
			AnimationLibrary.scaleUp(doubleDown);
		});
		doubleDown.setOnMouseExited(e -> {
			AnimationLibrary.scaleDown(doubleDown);
		});

		if (split == null)
			return;
		split.setOnAction(event -> {
			theGame.makeMove(Actions.SPLIT);
			this.updateActivePlayerLabel(this.theGame.getActivePlayer());
		});
		split.setOnMouseEntered(e -> {
			AnimationLibrary.scaleUp(split);
		});
		split.setOnMouseExited(e -> {
			AnimationLibrary.scaleDown(split);
		});

		if (placeBet == null)
			return;
		placeBet.setOnAction(event -> {
			theGame.music.playSFX("PokerChipsSlide.wav");
			theGame.music.playSFX("CardShuffle2.wav");
			this.showActionElements();
			theGame.setActivePlayerBet(bet);
			theGame.startRound();
		});

		resetBet.setOnAction(event -> {
			theGame.music.playSFX("PokerChipsSlide.wav");
			bet = 0;
			betInput.setText("Bet amount: $" + bet);
		});

		chip1.setOnAction(e -> {
			theGame.music.playSFX("PokerChipsHit.wav");
			bet += 1;
			betInput.setText("Bet amount: $" + bet);
		});
		chip1.setOnMouseEntered(e -> {
			AnimationLibrary.scaleUp(chip1);
		});
		chip1.setOnMouseExited(e -> {
			AnimationLibrary.scaleDown(chip1);
		});

		chip5.setOnAction(e -> {
			theGame.music.playSFX("PokerChipsHit.wav");
			bet += 5;
			betInput.setText("Bet amount: $" + bet);
		});
		chip5.setOnMouseEntered(e -> {
			AnimationLibrary.scaleUp(chip5);
		});
		chip5.setOnMouseExited(e -> {
			AnimationLibrary.scaleDown(chip5);
		});

		chip10.setOnAction(e -> {
			theGame.music.playSFX("PokerChipsHit.wav");
			bet += 10;
			betInput.setText("Bet amount: $" + bet);
		});
		chip10.setOnMouseEntered(e -> {
			AnimationLibrary.scaleUp(chip10);
		});
		chip10.setOnMouseExited(e -> {
			AnimationLibrary.scaleDown(chip10);
		});

		chip25.setOnAction(e -> {
			theGame.music.playSFX("PokerChipsHit.wav");
			bet += 25;
			betInput.setText("Bet amount: $" + bet);
		});
		chip25.setOnMouseEntered(e -> {
			AnimationLibrary.scaleUp(chip25);
		});
		chip25.setOnMouseExited(e -> {
			AnimationLibrary.scaleDown(chip25);
		});

		chip100.setOnAction(e -> {
			theGame.music.playSFX("PokerChipsHit.wav");
			bet += 100;
			betInput.setText("Bet amount: $" + bet);
		});
		chip100.setOnMouseEntered(e -> {
			AnimationLibrary.scaleUp(chip100);
		});
		chip100.setOnMouseExited(e -> {
			AnimationLibrary.scaleDown(chip100);
		});

		chip500.setOnAction(e -> {
			theGame.music.playSFX("PokerChipsHit.wav");
			bet += 500;
			betInput.setText("Bet amount: $" + bet);
		});
		chip500.setOnMouseEntered(e -> {
			AnimationLibrary.scaleUp(chip500);
		});
		chip500.setOnMouseExited(e -> {
			AnimationLibrary.scaleDown(chip500);
		});

		yes.setOnAction(e -> {
			insurance.setVisible(false);
			actions.setVisible(true);
			Player player = theGame.getActivePlayer();
			if (theGame.isGameOver) { // This may need to account for other cases
				theGame.music.playSFX("CardsFlipCard.wav");
				theGame.music.playSFX("PokerChipsSlide2.wav");
				player.betInsurance(bet);
				theGame.getActivePlayer().fold();
			} else {
				player.betInsurance(-bet / 2);
			}
			this.listener.run();
		});

		yes.setOnMouseEntered(e -> {
			AnimationLibrary.scaleUp(yes);
		});
		yes.setOnMouseExited(e -> {
			AnimationLibrary.scaleDown(yes);
		});

		no.setOnAction(e -> {

			if (theGame.isGameOver) // This may need to account for other cases
				theGame.getActivePlayer().fold();
			insurance.setVisible(false);
			actions.setVisible(true);
			this.listener.run();
		});

		no.setOnMouseEntered(e -> {
			AnimationLibrary.scaleUp(no);
		});
		no.setOnMouseExited(e -> {
			AnimationLibrary.scaleDown(no);
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
		doubleDown.setVisible(true);
		split.setVisible(true);
	}

	/**
	 * Show the insurance elements on the GUI to let the players place insurance
	 * bets.
	 * 
	 * @param listener: Runnable to update this.listener to
	 */
	public void showInsuranceElements(Runnable listener) {
		insurance.setVisible(true);
		actions.setVisible(false);
		this.listener = listener;
	}

	/**
	 * setter method for the text of the activePlayerLabel Label
	 * 
	 * @param playerName String representing what name to insert in the label.
	 */
	public void updateActivePlayerLabel(Player player) {
		activePlayerLabel.setText(player.getName() + "'s balance: " + player.checkBalance());
	}

}
