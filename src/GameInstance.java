import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GameInstance.java
 * Holds the information for
 * the game board and the player
 * location. This extends JPanel
 * so that it can be directly
 * displayed.
 *
 * @author Ben Forman
 * @version April 20, 2026
 */

public class GameInstance extends JPanel {
    private static final int SIZE = 8;
    // Win length is the number of COMBINATIONS of rows. Not the number of rows to the end.
    private static final int WIN_LENGTH = 8;
    private Row[] board;
    // Bottom index shown.
    private AtomicInteger rowIndex;
    private AtomicInteger playerX;
    private AtomicInteger playerY;
    private static final int DISPLAY_SIZE = 512;
    private static final HashMap<String, BufferedImage> PNGS = new HashMap<>();
    private ArrayList<Car> cars;
    private Random rand;
    private Timer timer;
    private PlayerState playerState = PlayerState.UP;


    public GameInstance() {
        board = Row.init(WIN_LENGTH);
        rowIndex = new AtomicInteger(0);
        this.setSize(new Dimension(DISPLAY_SIZE, DISPLAY_SIZE));
        initPngs();
        playerX = new AtomicInteger(3);
        playerY = new AtomicInteger(0);
        cars = new ArrayList<>();
        rand = new Random();
        // Timer for playing and commands that run everytime.
        timer = new Timer(500, e -> {
            checkLossOrWin();
            advancePieces();
            repaint();
            checkLossOrWin();
        });
        timer.start();
    }

    // Called to handle the logic for the cars
    private void advancePieces() {
        // Advance all cars
        cars.removeIf(car -> !car.move());

        checkLossOrWin();

        // Spawn new cars if possible. Only in frame + 2 rows.
        for (int i = rowIndex.get(); i < Math.min(rowIndex.get() + 10, board.length - 1); i++) {
            Row j = board[i];
            if (j.type() != PlaceType.ROAD)
                continue;
            // Only generate a new car with like a third chance!
            if (j.get(0) instanceof Road && ((Road) j.get(0)).canSpawn() && rand.nextInt(3) == 1)
                cars.add(((Road) j.get(0)).spawnCar(i));
        }
    }

    // Confirm no loss
    public void checkLossOrWin() {
        int x = playerX.get();
        int y = playerY.get();

        // Can't be where a car is!
        for (Car c : cars)
            if (c.getX() == x && c.getY() == y)
                lose();
        // Can't be on empty water!
        if (board[y].get(x) instanceof Water && !board[y].get(x).hasObstacle())
            lose();

        // Can't be on bush!
        if (board[y].get(x) instanceof Grass && board[y].get(x).hasObstacle())
            lose();

        if (board[y].get(x) instanceof Finish)
            win();

    }

    private void lose() {
        timer.stop();
        DisplayHandler.lose();

    }

    private void win() {
        timer.stop();
        DisplayHandler.win();
    }

    private void initPngs() {
        // Initialize the pngs.
        try {
            PNGS.put("grass64.png", ImageIO.read(new File("grass64.png")));
            PNGS.put("river64.png", ImageIO.read(new File("river64.png")));
            PNGS.put("road64.png", ImageIO.read(new File("road64.png")));
            PNGS.put("bush64.png", ImageIO.read(new File("bush64.png")));
            PNGS.put("lilypad64.png", ImageIO.read(new File("lilypad64.png")));
            PNGS.put("car64.png", ImageIO.read(new File("car64.png")));
            PNGS.put("panda64.png", ImageIO.read(new File("panda64.png")));
            PNGS.put("pandaLeft.png", ImageIO.read(new File("pandaLeft.png")));
            PNGS.put("pandaRight.png", ImageIO.read(new File("pandaRight.png")));
            PNGS.put("pandaDown.png", ImageIO.read(new File("pandaDown.png")));
            PNGS.put("finish.png", ImageIO.read(new File("finish.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void paintComponent(Graphics g) {
        // This super call is required!
        super.paintComponent(g);

        if (playerY.get() - rowIndex.get() > 2 && rowIndex.get() + 8 < getBoardHeight()) {
            rowIndex.set(playerY.get() - 2);
        }

        // Loop through the game states and draw them.
        // Loop down to up since that is how it is drawn.
        for (int i = 0, yLoc = SIZE - 1; i < SIZE; i++, yLoc--) {
            for (int j = 0; j < board[i].length(); j++) {
                // Calculate which spots need to be viewed since the player moves!
                Place spot = board[rowIndex.get() + i].get(j);
                BufferedImage image;
                // Try-Catch from CS180 Example PixelSprite Class. Slightly modified.

                // Display the obstacle if necessary. Lilypads & Roads are drawn dynamically.
                if (spot.hasObstacle()) {
                    if (spot instanceof Grass) {
                        image = PNGS.get(spot.getObstacle().getPng());
                        g.drawImage(image, j * 64, yLoc * 64, image.getWidth(), image.getHeight(), null);
                    } else {
                        // Either the road or water. Overlays images.
                        image = PNGS.get(spot.getPng());
                        g.drawImage(image, j * 64, yLoc * 64, image.getWidth(), image.getHeight(), null);
                        image = PNGS.get(spot.getObstacle().getPng());
                        g.drawImage(image, j * 64, yLoc * 64, image.getWidth(), image.getHeight(), null);
                    }
                } else {
                    image = PNGS.get(spot.getPng());
                    g.drawImage(image, j * 64, yLoc * 64, image.getWidth(), image.getHeight(), null);
                }
                // If this is the player's location,
                if (j == playerX.get() && i + rowIndex.get() == playerY.get()) {
                    drawPanda(g, j, yLoc);
                }
            }
        }
        BufferedImage image = PNGS.get("car64.png");
        for (Car c : cars) {
            int x = c.getX();
            int y = c.getY();
            if (rowIndex.get() + 8 >= y)
                g.drawImage(image, x * 64,
                        (7 - (y - rowIndex.get())) * 64, image.getWidth(),
                        image.getHeight(), null);
        }
    }

    // Draw the panda helper method to deal with rotations
    public void drawPanda(Graphics g, int j, int yLoc) {
        if (PlayerState.UP == playerState)
            g.drawImage(PNGS.get("panda64.png"), j * 64, yLoc * 64,
                    PNGS.get("panda64.png").getWidth(),
                    PNGS.get("panda64.png").getHeight(), null);
        if (PlayerState.DOWN == playerState)
            g.drawImage(PNGS.get("pandaDown.png"), j * 64, yLoc * 64,
                    PNGS.get("pandaDown.png").getWidth(),
                    PNGS.get("pandaDown.png").getHeight(), null);
        if (PlayerState.LEFT == playerState)
            g.drawImage(PNGS.get("pandaLeft.png"), j * 64, yLoc * 64,
                    PNGS.get("pandaLeft.png").getWidth(),
                    PNGS.get("pandaLeft.png").getHeight(), null);
        if (PlayerState.RIGHT == playerState)
            g.drawImage(PNGS.get("pandaRight.png"), j * 64, yLoc * 64,
                    PNGS.get("pandaRight.png").getWidth(),
                    PNGS.get("pandaRight.png").getHeight(), null);
    }

    public int getPlayerY() {
        return playerY.get();
    }

    // Handlers for movement
    public void up() {
        if (playerY.get() == rowIndex.get() + 7)
            return;
        playerY.incrementAndGet();
        playerState = PlayerState.UP;
        repaint();
    }

    public void down() {
        if (playerY.get() == rowIndex.get())
            return;
        playerY.addAndGet(-1);
        playerState = PlayerState.DOWN;
        repaint();
    }

    public void left() {
        if (playerX.get() == 0)
            return;
        playerX.addAndGet(-1);
        playerState = PlayerState.LEFT;
        repaint();
    }

    public void right() {
        if (playerX.get() == 7)
            return;
        playerX.incrementAndGet();
        playerState = PlayerState.RIGHT;
        repaint();
    }

    public int getBoardHeight() {
        return board.length;
    }
}
