/**
 * Road.java
 * This class extends the Place
 * class. It contains the image
 * path as well as a connection
 * to the CarDelay class.
 * Image was generated using Google's
 * Gemini AI.
 *
 * @author Ben Forman
 * @version April 20, 2026
 */

public class Road extends Place {
    private boolean canSpawn;
    private static final int DELAY = 1500;

    public Road() {
        super("road64.png");
        canSpawn = true;
    }

    public boolean canSpawn() {
        return canSpawn;
    }

    public Car spawnCar(int y) {
        if (!canSpawn)
            return null;
        canSpawn = false;
        // Create a delay so we don't get cars spawning together.
        CarDelay cD = new CarDelay(this, DELAY);
        (new Thread(cD)).start();
        return new Car(y);
    }

    public void allowSpawn() {
        canSpawn = true;
    }

    @Override
    public Road clone() {
        return new Road();
    }
}
