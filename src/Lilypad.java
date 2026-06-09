/**
 * Lilypad.java
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

public class Lilypad extends Obstacle {
    public Lilypad() {
        super(false, "lilypad64.png");
    }

    @Override
    public Lilypad clone() {
        return new Lilypad();
    }
}
