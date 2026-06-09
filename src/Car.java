import java.util.concurrent.atomic.AtomicInteger;

/**
 * Car.java
 * The car class extends obstacle.
 * This class contains the general
 * outline and location of cars.
 * Image was generated using Google's
 * Gemini AI.
 *
 * @author Ben Forman
 * @version April 20, 2026
 */

public class Car extends Obstacle {
    private AtomicInteger x;
    private AtomicInteger y;

    public Car(int y) {
        super(true, "car64.png");
        x = new AtomicInteger(0);
        this.y = new AtomicInteger(y);
    }

    private Car(int y, int x) {
        super(true, "car64.png");
        this.x = new AtomicInteger(x);
        this.y = new AtomicInteger(y);
    }

    // Returns false if it must get deleted!
    public boolean move() {
        if (x.get() == 7)
            return false;
        x.incrementAndGet();
        return true;
    }

    public int getX() {
        return x.get();
    }

    public int getY() {
        return y.get();
    }

    @Override
    public Car clone() {
        return new Car(y.get(), x.get());
    }
}