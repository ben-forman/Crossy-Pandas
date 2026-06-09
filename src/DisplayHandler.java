import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * DisplayHandler.java
 * Generates an instance of the
 * GameInstance class and begins
 * the game. Using this wrapper
 * class also allows the overlay
 * of start buttons.
 *
 * @author Ben Forman
 * @version April 20, 2026
 */

public class DisplayHandler {
    private static JFrame frame;
    private static GameInstance panel;
    private static StartingScreen startPanel;
    private static JButton startButton;

    public static void start() {
        SwingUtilities.invokeLater(() -> {
            // Initialize JFrame
            frame = new JFrame("Crossy Pandas!");

            // Initialize start button
            startButton = new JButton("Start");
            startButton.addActionListener((e) -> {
                startGame();
            });
            startButton.setPreferredSize(new Dimension(100, 50));


            // Start Screen:
            startPanel = new StartingScreen();
            startPanel.setPreferredSize(new Dimension(512, 512));
            startPanel.add(startButton);
            frame.add(startPanel);
            frame.setFocusable(true);
            frame.requestFocusInWindow();
            frame.pack();

            // Set up the frame parameters and make visible
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public static void startGame() {
        System.out.println("Starting...");
        init();
    }

    // Initialize the game states! Callable whenever necessary (Including restart).
    public static void init() {
        // Make the panel information.
        panel = new GameInstance();
        panel.setPreferredSize(new Dimension(512, 512));
        frame.add(panel);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        // Generates a new KeyAdapter to only override methods I needed:
        frame.addKeyListener(
                new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        // Determine which key was pressed and call respective method.
                        if (e.getKeyChar() == 'W' || e.getKeyChar() == 'w')
                            panel.up();
                        if (e.getKeyChar() == 'A' || e.getKeyChar() == 'a')
                            panel.left();
                        if (e.getKeyChar() == 'S' || e.getKeyChar() == 's')
                            panel.down();
                        if (e.getKeyChar() == 'D' || e.getKeyChar() == 'd')
                            panel.right();
                        panel.checkLossOrWin();
                    }
                }
        );
    }

    // Win conditions!
    public static void win() {
        startPanel = new StartingScreen();
        startPanel.setPreferredSize(new Dimension(512, 512));
        clearFrame();
        JLabel jL = new JLabel("You won! Congrats! Would you like to play again?");

        JButton restartButton = new JButton("Play Again");
        restartButton.addActionListener((e) -> {
            restart();
        });
        restartButton.setPreferredSize(new Dimension(100, 50));
        startPanel.add(jL);
        startPanel.add(restartButton);
        frame.add(startPanel);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.pack();

        // Set up the frame parameters and make visible
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    // Lose conditions!
    public static void lose() {
        startPanel = new StartingScreen();
        startPanel.setPreferredSize(new Dimension(512, 512));
        clearFrame();
        JLabel jL = new JLabel("You lost! Would you like to restart?");

        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener((e) -> {
            restart();
        });
        restartButton.setPreferredSize(new Dimension(100, 50));
        startPanel.add(jL);
        startPanel.add(restartButton);
        frame.add(startPanel);
        frame.setFocusable(true);
        frame.requestFocusInWindow();
        frame.pack();

        // Set up the frame parameters and make visible
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Reset variables so we don't get bleeding!
    public static void restart() {
        panel = null;
        startPanel = null;
        startButton = null;
        clearFrame();
        init();
    }

    // Clear the frame and restart!
    public static void clearFrame() {
        JFrame temp = frame;
        frame = new JFrame("Crossy Pandas");
        temp.dispose();
    }
}
