package view;

import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import model.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class CardSprite {
	private Point2D position;
	private double rotation;
	private Card card;
	private Image img;
	private String outerFP;
	private String innerFP;
	private String suitFP;
	private GraphicsContext gc;
	private boolean hideCard;

	/**
	 * constructs the CardSprite class, which represent an image of a corresponding
	 * card
	 * 
	 * @param g:       the graphics context of the canvas to draw on
	 * @param x:       the x position of the card
	 * @param y:       the y position of the card
	 * @param rot:     the rotation of the card
	 * @param theCard: the card to represent
	 */
	public CardSprite(GraphicsContext g, double x, double y, double rot, Card theCard, boolean hideCard) {
		this.gc = g;
		position = new Point2D(x, y);
		rotation = rot;
		card = theCard;
		this.hideCard = hideCard;

		// determine base image
		switch (card.getSuit()) {
		case CLUBS:
			outerFP = "cCards";
			suitFP = "club.png";
			break;

		case SPADES:
			outerFP = "sCards";
			suitFP = "spade.png";
			break;

		case HEARTS:
			outerFP = "hCards";
			suitFP = "heart.png";
			break;

		case DIAMONDS:
			outerFP = "dCards";
			suitFP = "diamond.png";
			break;
		default:
			System.out.println("invalid suit");
			System.out.println(card.getSuit());
		}

		switch (card.getRank().getNum()) {
		case 11:
			innerFP = "jack.png";
			break;

		case 12:
			innerFP = "queen.png";
			break;

		case 13:
			innerFP = "king.png";
			break;

		default:
			innerFP = "blank.png";
		}
	}

	/**
	 * draws the card onto the canvas given the canvas's graphics context
	 */
	public void draw() {
		try {
			if (hideCard)
				img = new Image(new FileInputStream("images/cardBack.png"));
			else
				img = new Image(new FileInputStream("images/" + outerFP + "/" + innerFP));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		DropShadow shadow = new DropShadow();
		gc.setEffect(shadow);
		gc.drawImage(img, position.getX(), position.getY(), 122, 173);
		if (hideCard) {
			hideCard = false;
			gc.setEffect(null);
			return;
		}
		gc.setEffect(null);
		gc.setFont(new Font(16));
		gc.setTextAlign(TextAlignment.CENTER);
		String cardValue;
		if (card.getRank().getNum() <= 10 && card.getRank().getNum() > 1) {
			cardValue = "" + card.getRank().getValue();
		} else if (card.getRank().getNum() <= 11 && card.getRank().getNum() > 1) {
			cardValue = "J";
		} else if (card.getRank().getNum() <= 12 && card.getRank().getNum() > 1) {
			cardValue = "Q";
		} else if (card.getRank().getNum() <= 13 && card.getRank().getNum() > 1) {
			cardValue = "K";
		} else {
			cardValue = "A";
		}
		if (card.getRank().getNum() <= 10 || card.getRank().getNum() == 14)
			this.createCardDisplay();
		gc.fillText(cardValue, position.getX() + 15, position.getY() + 19);
		gc.save();
		gc.translate(1000, 750);
		gc.scale(-1, -1);
		gc.translate(750, 1000);
		gc.scale(1, 1);
		gc.fillText(cardValue, -position.getX() + 144, -position.getY() - 405);
		gc.restore();

	}

	/**
	 * creates the card images
	 */
	private void createCardDisplay() {
		Image suitImage = null;
		try {
			suitImage = new Image(new FileInputStream("images/" + suitFP), 80, 80, true, false);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
//		switch (card.getRank().getNum()) {
//		case 1, 14:
//			gc.drawImage(suitImage, position.getX() + 30, position.getY() + 55, 60, 60);
//			break;
//		case 2:
//			gc.drawImage(suitImage, position.getX() + 34.5, position.getY() + 35, 50, 50);
//			gc.save();
//			gc.rotate(180);
//			gc.drawImage(suitImage, -position.getX() - 85.5, -position.getY() - 140, 50, 50);
//			gc.restore();
//			break;
//		default:
		gc.drawImage(suitImage, position.getX() + 30, position.getY() + 55, 60, 60);

//		}
	}

}
