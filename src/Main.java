import javax.swing.*;

/**
 * Main class
 * Initializes the game states
 * by creating the start menu.
 *
 * @author Ben Forman
 * @version April 16, 2026
 */

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> DisplayHandler.start());
    }

}
