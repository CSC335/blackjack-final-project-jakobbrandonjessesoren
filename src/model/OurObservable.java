/**
 * A simple observable class use to notify observers of any state change in the
 * the observable. The observable class must extend this class OurObserverable.
 * 
 * @author Rick Mercer
 *
 */
package model;
import java.util.ArrayList;

/**
 * a class to be inherited by objects that want to be observed by classes who implement OurObserver
 */
public class OurObservable {
  ArrayList<OurObserver> observers = new ArrayList<>();
  
  /**
   * adds an observer to the observable's list
   * @param anObserver a instance of an Observer
   */
  public void addObserver(OurObserver anObserver) {
    observers.add(anObserver);
  }
  /**
   * send a message to all added observers, so they can read the data and update accordingly.
   * @param theObservable
   */
  public void notifyObservers(OurObservable theObservable) {
    for(OurObserver obs : observers) {
      obs.update(theObservable);
    }
  }
}
