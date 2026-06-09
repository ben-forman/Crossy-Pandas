/**
 * Grass.java
 * Extends Place to allow for the
 * access to super properties such
 * as png and isObstacle. It also
 * implements clone as required by
 * the Place abstract.
 * Image was generated using Google's
 * Gemini AI.
 *
 * @author Ben Forman
 * @version April 20, 2026
 */

public class Grass extends Place {
    public Grass() {
        super("grass64.png");
    }

    @Override
    public Grass clone() {
        if (hasObstacle()) {
            Grass clone = new Grass();
            clone.setObstacle(getObstacle().clone());
            return clone;
        }

        return new Grass();
    }
}
