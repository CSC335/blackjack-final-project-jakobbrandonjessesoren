/**
 * This interface must be implemented by any observer that wants
 * to be notified of any change to the state of the model, like
 * the human player or computer player make a move on the TTT board.
 * 
 * @author Rick Mercer
 */
package model;

/**
 * basic interface to be implemented by objects who wish to observe observable objects and update accordingly
 * @param <E> The object type to observer (the type must have implemented observable).
 */
public interface OurObserver<E> {
   void update(E theObserved);
}
