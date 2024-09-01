package view;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.*;
import javafx.animation.Transition;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * A collection of animations that can be used on any JavaFX node.
 * Calling any of these methods will immediately play the associated animation on the node
 */
public final class AnimationLibrary {

    /**
     * Private constructor so that the class cannot be instantiated
     */
    private AnimationLibrary() {};
    private static final ScaleTransition scale =  new ScaleTransition();;

    /**
     *  Scales object as an animation
     * 
     *  @param node: object to apply animation to
     */
    public static void scaleUp(Node node) {
        ScaleTransition scale = new ScaleTransition();
        scale.setDuration(Duration.millis(100));
        scale.setFromX(1.0);
        scale.setFromY(1.0);
        scale.setToX(1.1);
        scale.setToY(1.1);
        scale.setInterpolator(Interpolator.EASE_BOTH);
        scale.setNode(node);
        scale.play();
    }
    
    /**
     *  Scales object down as an animation
     * 
     *  @param node: object to apply animation to
     */
    public static void scaleDown(Node node) {
        ScaleTransition scale = new ScaleTransition();
        scale.setDuration(Duration.millis(100));
        scale.setFromX(1.1);
        scale.setFromY(1.1);
        scale.setToX(1.0);
        scale.setToY(1.0);
        scale.setInterpolator(Interpolator.EASE_BOTH);
        scale.setNode(node);
        scale.play();
    }

    /**
     *  Scales and moves object as an animation
     * 
     *  @param node: object to apply animation to
     *  @param moveTo: point to move to
     *  @param scaleTo: how big to make the object
     */
    public static void moveAndScale(Node node, Point2D moveTo, double scaleTo) {
        ScaleTransition scale = new ScaleTransition();
        scale.setFromX(node.getScaleX());
        scale.setFromY(node.getScaleY());
        scale.setToX(scaleTo);
        scale.setToY(scaleTo);
        scale.setInterpolator(Interpolator.EASE_BOTH);

        TranslateTransition move = new TranslateTransition();
        move.setFromX(node.getTranslateX());
        move.setFromY(node.getTranslateY());
        move.setToX(moveTo.getX());
        move.setToY(moveTo.getY());
        move.setInterpolator(Interpolator.EASE_BOTH);

        ParallelTransition combinedTransition = new ParallelTransition(scale, move);
        combinedTransition.setNode(node);
        combinedTransition.play();
    }
}
