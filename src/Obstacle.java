/**
 * Obstacle.java
 * An abstract class that allows
 * unified framework of methods
 * for moving and png displaying.
 *
 * @author Ben Forman
 * @version April 20, 2026
 */

public abstract class Obstacle {
    protected final boolean canMove;
    protected final String png;

    public Obstacle(boolean canMove, String png) {
        this.canMove = canMove;
        this.png = png;
    }

    public boolean canMove() {
        return canMove;
    }

    public String getPng() {
        return png;
    }

    @Override
    public abstract Obstacle clone();
}
