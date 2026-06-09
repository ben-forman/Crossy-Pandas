import java.util.concurrent.atomic.AtomicInteger;

/**
 * DelayIncrement.java
 * DEPRECATED! I no longer
 * use this class. It was
 * intended to be used as
 * a scroller, but turned
 * out clunky!
 *
 * @author Ben Forman
 * @version April 20, 2026
 */

public class DelayIncrement implements Runnable {
    private AtomicInteger inc;
    private GameInstance game;

    public DelayIncrement(AtomicInteger inc, GameInstance game) {
        this.inc = inc;
        this.game = game;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (game.getPlayerY() <= inc.get() + 1 || inc.get() >= game.getBoardHeight() - 8)
            return;
        inc.incrementAndGet();
        game.repaint();
    }
}
