/**
 * Finish.java
 * Extends Place to allow for the
 * access to super properties such
 * as png and isObstacle. It also
 * implements clone as required by
 * the Place abstract. It is the
 * finish line.
 * Image was generated using OpenAI's
 * ChatGPT.
 *
 * @author Ben Forman
 * @version April 20, 2026
 */

public class Finish extends Place {
    public Finish() {
        super("finish.png");
    }

    @Override
    public Finish clone() {
        if (hasObstacle()) {
            Finish clone = new Finish();
            clone.setObstacle(getObstacle().clone());
            return clone;
        }

        return new Finish();
    }
}
