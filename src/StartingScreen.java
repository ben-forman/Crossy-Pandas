import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * StartingScreen.java
 * A panel that allows the background
 * to be an image of the grass and
 * then overlay a button on top of it.
 *
 * @author Ben Forman
 * @version April 20, 2026
 */

public class StartingScreen extends JPanel {
    public StartingScreen() {
        this.setLayout(new GridBagLayout());
        this.setPreferredSize(new Dimension(512, 512));
    }

    protected void paintComponent(Graphics g) {
        // This super call is required!
        super.paintComponent(g);
        int size = 8;

        BufferedImage image;
        try {
            image = ImageIO.read(new File("grass64.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Loop through the game states and draw them.
        // Loop down to up since that is how it is drawn.
        for (int i = 0, yLoc = size - 1; i < size; i++, yLoc--) {
            for (int j = 0; j < size; j++) {
                g.drawImage(image, j * 64, yLoc * 64, image.getWidth(), image.getHeight(), null);
            }
        }
    }
}
