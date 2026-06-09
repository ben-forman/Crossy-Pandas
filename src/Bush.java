/**
 * Bush.java
 * The bush class simply
 * acts as an extender of
 * the obstacle class. It
 * cannot move and is stuck
 * in place.
 * Image was generated using Google's
 * Gemini AI.
 *
 * @author Ben Forman
 * @version April 20, 2026
 */

public class Bush extends Obstacle {
    public Bush() {
        super(false, "bush64.png");
    }

    @Override
    public Bush clone() {
        return new Bush();
    }
}
