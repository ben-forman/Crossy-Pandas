/**
 * Place.java
 * An abstract class that allows
 * unified framework of methods
 * for displaying and obstacle
 * handling.
 *
 * @author Ben Forman
 * @version April 20, 2026
 */

public abstract class Place {

    protected String png;
    protected Obstacle obstacle;

    public Place(String png) {
        this.png = png;
        obstacle = null;
    }

    public Place(String png, Obstacle obstacle) {
        this.png = png;
        this.obstacle = obstacle;
    }

    public Obstacle getObstacle() {
        return obstacle;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public boolean hasObstacle() {
        return obstacle != null;
    }

    public String getPng() {
        return png;
    }

    @Override
    public abstract Place clone();

}
