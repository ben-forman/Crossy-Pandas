/**
 * Water.java
 * An extension of the Place
 * abstract which determines
 * the png location and
 * implements the clone method
 * as required from the abstract.
 * Image was generated using Google's
 * Gemini AI.
 *
 * @author Ben Forman
 * @version April 20, 2026
 */

public class Water extends Place {
    public Water() {
        super("river64.png");
    }

    @Override
    public Water clone() {
        if (hasObstacle()) {
            Water clone = new Water();
            clone.setObstacle(getObstacle().clone());
            return clone;
        }

        return new Water();
    }
}
