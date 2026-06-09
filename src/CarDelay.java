/**
 * CarDelay.java
 * This class allows instantiation
 * and implements Runnable to allow
 * it to create an independent thread.
 * By creating an independent thread,
 * we can create a delay for car
 * generation without halting the
 * game states.
 *
 * @author Ben Forman
 * @version April 20, 2026
 */

public class CarDelay implements Runnable {
    private Road r;
    private int delay;

    public CarDelay(Road r, int delay) {
        this.r = r;
        this.delay = delay;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            System.out.println("Problems with delay!");
            r.allowSpawn();
        }
        r.allowSpawn();
    }
}
