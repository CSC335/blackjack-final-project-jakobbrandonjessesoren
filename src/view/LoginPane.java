package view;

import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import model.PlayerAccount;
import model.PlayerAccountCollection;

/**
 * This class represents the login window for Blackjack GUI. Users cannot
 * progress until they log in.
 */
public class LoginPane extends Pane {

	private PlayerAccountCollection playerCollection;
	private String finalUsername = "";
	private TextField userField;
	private PasswordField passField;
	private Button login, startGame, newAccount;
	private Runnable loginSuccessListener; // A runnable anonymous function passed in to resume the main GUI
	private Group loginElements, userStats;
	private GraphicsContext gc;
	private Canvas canvas;
	private Image background;
	private ComboBox<String> menu;

	/**
	 * Create the login pane
	 * 
	 * @param canvas     - A canvas created in BlackjackGUI
	 * @param background - the background image for the GUI
	 */
	public LoginPane(Canvas canvas, Image background, PlayerAccountCollection playerCollection) {
		this.playerCollection = playerCollection;
		gc = canvas.getGraphicsContext2D();
		this.canvas = canvas;
		this.background = background;
		gc.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());

		this.getChildren().add(canvas);
		gc.setGlobalAlpha(.6);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setFill(Color.LIGHTBLUE);
		gc.setGlobalAlpha(1);
		gc.fillRect(300, 150, 400, 220);
		gc.setFill(Color.BLACK);

		initializeElements();
		setEventHandlers();
	}

	/**
	 * Set the event handlers for the the different attributes
	 */
	private void setEventHandlers() {
		userField.setOnAction(e -> {
			if (userField.getText().length() > 0)
				passField.requestFocus();
		});
		passField.setOnAction(e -> {
			if (passField.getText().length() > 0)
				login.requestFocus();
		});
		login.setOnAction(e -> {
			String username = userField.getText();
			String password = passField.getText();
			if (username.length() > 0 && password.length() > 0) {
				if (playerCollection.checkForUsername(username)) {
					// username exists
					if (playerCollection.checkPassword(username, password)) {
						// password is correct
						finalUsername = username;
						this.showUserStats();
						this.displayBalanceGraph(playerCollection.getPlayer(finalUsername));
					} else {
						// password is incorrect
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setHeaderText("Account exists but password is incorrect");
						alert.showAndWait();
					}

				} else {
					// username doesn't exist
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setHeaderText("Player Account does not exist");
					alert.showAndWait();
				}
			}
		});

		startGame.setOnAction(e -> {
			if (loginSuccessListener != null) { // Run the anonymous function passed in
				loginSuccessListener.run();
			}
		});

		newAccount.setOnAction(e -> {
			String username = userField.getText();
			String password = passField.getText();
			// add Player
			boolean isComplete = playerCollection.addPlayer(username, password);
			if (isComplete) {
				// new player created
				finalUsername = username;
				this.showUserStats();
				this.displayBalanceGraph(playerCollection.getPlayer(finalUsername));
			} else {
				// username already taken
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setHeaderText("Player username is already taken");
				alert.showAndWait();
			}
		});

		menu.setOnAction(e -> {
			String selectedOption = menu.getValue();
			if (selectedOption.equals("Total Change in Balance")) {
				this.getChildren().remove(userStats);
				this.showUserStats();
				this.displayBalanceGraph(playerCollection.getPlayer(finalUsername));
			} else if (selectedOption.equals("Recent Change in Balance")) {
				this.getChildren().remove(userStats);
				this.showUserStats();
				this.displayRecentBalanceGraph(playerCollection.getPlayer(finalUsername));
			} else if (selectedOption.equals("Win Rate")) {
				this.getChildren().remove(userStats);
				this.showUserStats();
				this.displayWinRateGraph(playerCollection.getPlayer(finalUsername));
			}

		});
	}

	/**
	 * shows the current user's statistics
	 */
	public void showUserStats() {
		this.redrawBackground();
		loginElements.setVisible(false);

		PlayerAccount playerAccount = playerCollection.getPlayer(finalUsername);
		ArrayList<Integer> values = new ArrayList<Integer>();
		values.add((playerAccount.getHighestBalance() + "").length());
		values.add((playerAccount.getLowestBalance() + "").length());
		values.add((playerAccount.getBiggestBet() + "").length());
		values.add((playerAccount.getBiggestAmountWon() + "").length());
		int max = Collections.max(values);

		Label username = new Label("Welcome " + playerAccount.getUsername() + ",\nWould you care to play a game?");
		username.setLayoutX(10);
		username.setLayoutY(0);
		username.setStyle("-fx-font-size: 36; -fx-text-fill: white; -fx-font-family: Consolas;");

		Label stats = new Label("Your Play Stats:");
		stats.setLayoutX(10);
		stats.setLayoutY(170);
		stats.setStyle("-fx-font-size: 24; -fx-text-fill: white; -fx-font-family: Consolas;");

		Label balance = new Label(
				String.format("%-20s %" + max + ".1f", "Current Balance:", playerAccount.getBalance()));
		balance.setLayoutX(10);
		balance.setLayoutY(220);
		balance.setStyle("-fx-font-size: 24; -fx-text-fill: white; -fx-font-family: Consolas;");

		Label highestBalance = new Label(
				String.format("%-20s %" + max + ".1f", "Highest Balance:", playerAccount.getHighestBalance()));
		highestBalance.setLayoutX(10);
		highestBalance.setLayoutY(270);
		highestBalance.setStyle("-fx-font-size: 24; -fx-text-fill: white; -fx-font-family: Consolas;");

		Label lowestBalance = new Label(
				String.format("%-20s %" + max + ".1f", "Lowest Balance:", playerAccount.getLowestBalance()));
		lowestBalance.setLayoutX(10);
		lowestBalance.setLayoutY(320);
		lowestBalance.setStyle("-fx-font-size: 24; -fx-text-fill: white; -fx-font-family: Consolas;");

		Label biggestBet = new Label(
				String.format("%-20s %" + max + ".1f", "Biggest Bet:", playerAccount.getBiggestBet()));
		biggestBet.setLayoutX(10);
		biggestBet.setLayoutY(370);
		biggestBet.setStyle("-fx-font-size: 24; -fx-text-fill: white; -fx-font-family: Consolas;");

		Label biggestAmountWon = new Label(
				String.format("%-20s %" + max + ".1f", "Biggest Amount Won:", playerAccount.getBiggestAmountWon()));
		biggestAmountWon.setLayoutX(10);
		biggestAmountWon.setLayoutY(420);
		biggestAmountWon.setStyle("-fx-font-size: 24; -fx-text-fill: white; -fx-font-family: Consolas;");

		Label longestWinStreak = new Label(String.format("%-20s %" + max + ".1f", "Longest Win Streak:",
				playerAccount.getLongestWinStreak() * 1.0));
		longestWinStreak.setLayoutX(10);
		longestWinStreak.setLayoutY(470);
		longestWinStreak.setStyle("-fx-font-size: 24; -fx-text-fill: white; -fx-font-family: Consolas;");

		Label currentWinStreak = new Label(String.format("%-20s %" + max + ".1f", "Current Win Streak:",
				playerAccount.getCurrentWinStreak() * 1.0));
		currentWinStreak.setLayoutX(10);
		currentWinStreak.setLayoutY(520);
		currentWinStreak.setStyle("-fx-font-size: 24; -fx-text-fill: white; -fx-font-family: Consolas;");

		Label totalGames = new Label(String.format("%-20s %" + max + ".1f", "Total Games Played:",
				playerAccount.getTotalGamesPlayed() * 1.0));
		totalGames.setLayoutX(10);
		totalGames.setLayoutY(570);
		totalGames.setStyle("-fx-font-size: 24; -fx-text-fill: white; -fx-font-family: Consolas;");

		userStats = new Group();
		userStats.getChildren().addAll(balance, highestBalance, lowestBalance, biggestBet, biggestAmountWon,
				longestWinStreak, currentWinStreak, stats, username, totalGames, startGame, menu);
		userStats.setVisible(true);
		this.getChildren().add(userStats);
	}

	/**
	 * displays the balance graph
	 * 
	 * @param playerAccount: the account to show the graph for
	 */
	private void displayBalanceGraph(PlayerAccount playerAccount) {
		ArrayList<Double> yValues = playerAccount.getPreviousBalances();

		// Create x-values corresponding to indices of the array
		ArrayList<Integer> xValues = new ArrayList<>();
		for (int i = 0; i < yValues.size(); i++) {
			xValues.add(i);
		}

		// Define the axes
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Balance");
		xAxis.setLabel("Round #");
		xAxis.setTickLabelFill(Color.WHITE);
		yAxis.setTickLabelFill(Color.WHITE);

		// Create the line chart
		final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
		lineChart.setTitle("\t\tTotal Change in Balance");
		lineChart.setStyle("-fx-font-size: 16px;");
		lineChart.getXAxis().lookup(".axis-label").setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
		lineChart.getYAxis().lookup(".axis-label").setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
		lineChart.lookup(".chart-title").setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

		// Define the data series
		XYChart.Series<Number, Number> series = new XYChart.Series<>();
		series.setName("Balance over rounds");

		// Add data points to the series
		for (int i = 0; i < yValues.size(); i++) {
			series.getData().add(new XYChart.Data<>(xValues.get(i), yValues.get(i)));
		}

		// Add the series to the chart
		lineChart.getData().add(series);

		lineChart.setLayoutX(500);
		lineChart.setLayoutY(200);

		userStats.getChildren().add(lineChart);
	}

	/**
	 * displays the recent balance graph
	 * 
	 * @param playerAccount: the account to show the graph for
	 */
	private void displayRecentBalanceGraph(PlayerAccount playerAccount) {
		ArrayList<Double> yValues = playerAccount.getPreviousBalances();

		int size = Math.min(10, yValues.size());

		// Create x-values corresponding to indices of the array
		ArrayList<Integer> xValues = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			xValues.add(i);
		}

		// Define the axes
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Balance");
		xAxis.setLabel("Round #");
		xAxis.setTickLabelFill(Color.WHITE);
		yAxis.setTickLabelFill(Color.WHITE);
		yAxis.setAutoRanging(false);

		// Create the line chart
		final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
		lineChart.setTitle("\t\tRecent Change in Balance");
		lineChart.setStyle("-fx-font-size: 16px;");
		lineChart.getXAxis().lookup(".axis-label").setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
		lineChart.getYAxis().lookup(".axis-label").setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
		lineChart.lookup(".chart-title").setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

		// Define the data series
		XYChart.Series<Number, Number> series = new XYChart.Series<>();
		series.setName("Balance over last 10 rounds");

		double max = yValues.get(yValues.size() - size);
		double min = yValues.get(yValues.size() - size);
		// Add data points to the series
		for (int i = 0; i < size; i++) {
			series.getData().add(new XYChart.Data<>(xValues.get(i), yValues.get(yValues.size() - size + i)));
			if (yValues.get(yValues.size() - size + i) > max)
				max = yValues.get(yValues.size() - size + i);
			if (yValues.get(yValues.size() - size + i) < min)
				min = yValues.get(yValues.size() - size + i);
		}
		yAxis.setUpperBound(max + 10 * ("" + max).length());
		yAxis.setLowerBound(min - 10 * ("" + min).length());
		yAxis.setTickUnit((int) Math.abs(max - min) / 10);

		// Add the series to the chart
		lineChart.getData().add(series);

		lineChart.setLayoutX(500);
		lineChart.setLayoutY(200);

		userStats.getChildren().add(lineChart);
	}

	/**
	 * displays the win rate graph
	 * 
	 * @param playerAccount: the account to show the graph for
	 */
	private void displayWinRateGraph(PlayerAccount playerAccount) {
		double winRatio = playerAccount.getWinRatio();

		// Create x-values corresponding to indices of the array
		ArrayList<Integer> xValues = new ArrayList<>();
		ArrayList<Double> yValues = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			xValues.add(i);
			yValues.add(i * winRatio);
		}

		// Define the axes
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("Win Rate");
		xAxis.setTickLabelFill(Color.WHITE);
		yAxis.setTickLabelFill(Color.WHITE);
		yAxis.setAutoRanging(false);

		// Create the line chart
		final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
		lineChart.setTitle("\t\tWin Rate");
		lineChart.setStyle("-fx-font-size: 16px;");
		lineChart.getXAxis().lookup(".axis-label").setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
		lineChart.getYAxis().lookup(".axis-label").setStyle("-fx-font-size: 16px; -fx-text-fill: white;");
		lineChart.lookup(".chart-title").setStyle("-fx-font-size: 16px; -fx-text-fill: white;");

		// Define the data series
		XYChart.Series<Number, Number> series = new XYChart.Series<>();
		series.setName(String.format("Win rate of %.2f", winRatio));

		// Add data points to the series
		for (int i = 0; i < 2; i++) {
			series.getData().add(new XYChart.Data<>(xValues.get(i), yValues.get(i)));
		}
		yAxis.setUpperBound(1);
		yAxis.setLowerBound(-1);

		// Add the series to the chart
		lineChart.getData().add(series);

		lineChart.setLayoutX(500);
		lineChart.setLayoutY(200);

		userStats.getChildren().add(lineChart);
	}

	/**
	 * Set an anonymous function to run upon logging in. This allows the main GUI to
	 * resume displaying
	 * 
	 * @param listener - an anonymous function that ends this scene when ran
	 */
	public void setOnLoginSuccessListener(Runnable listener) {
		this.loginSuccessListener = listener;
	}

	/**
	 * Initialize all the different GUI elements for displaying a log in window
	 */
	private void initializeElements() {
		Label title = new Label("Login or Create a New Account");
		title.setStyle("-fx-font-size: 25; -fx-font-weight: bold;");
		title.setLayoutX(315);
		title.setLayoutY(160);

		Label username = new Label("Username");
		username.setStyle("-fx-font-size: 18;");
		username.setLayoutX(330);
		username.setLayoutY(210);
		userField = new TextField();
		userField.setLayoutX(420);
		userField.setLayoutY(213);
		userField.setPrefWidth(200);

		Label password = new Label("Password");
		password.setStyle("-fx-font-size: 18;");
		password.setLayoutX(330);
		password.setLayoutY(260);
		passField = new PasswordField();
		passField.setLayoutX(420);
		passField.setLayoutY(263);
		passField.setPrefWidth(200);

		login = new Button("Login!");
		login.setStyle("-fx-font-size: 15; -fx-font-weight: bold;");
		login.setLayoutX(395);
		login.setLayoutY(310);

		newAccount = new Button("Create new account");
		newAccount.setStyle("-fx-font-size: 15; -fx-font-weight: bold;");
		newAccount.setLayoutX(475);
		newAccount.setLayoutY(310);

		loginElements = new Group();
		loginElements.getChildren().addAll(title, username, userField, password, passField, login, newAccount);
		this.getChildren().add(loginElements);

		startGame = new Button("Start Game!");
		startGame.setLayoutX(465);
		startGame.setLayoutY(700);
		startGame.setStyle("-fx-font-size: 15; -fx-font-weight: bold;");

		menu = new ComboBox<>();
		// Add items to the ComboBox
		menu.getItems().addAll("Total Change in Balance", "Recent Change in Balance", "Win Rate");
		menu.setValue("Total Change in Balance");
		menu.setLayoutX(660);
		menu.setLayoutY(610);

		userStats = new Group();
		userStats.setVisible(false);
		this.getChildren().add(userStats);

	}

	/**
	 * Return the username to the main GUI
	 * 
	 * @return a string of the username
	 */
	public String getUsername() {
		return this.finalUsername;
	}

	/**
	 * redraws the table image background\
	 */
	private void redrawBackground() {
		gc.drawImage(background, 0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setGlobalAlpha(.6);
		gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setGlobalAlpha(1);
	}

}
