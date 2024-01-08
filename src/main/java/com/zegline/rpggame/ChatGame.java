package main.java.com.zegline.rpggame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class ChatGame extends JFrame {

    private DrawingPanel drawingPanel;
    private Timer gameTimer;

    // Create a StringBuilder to store the typed characters
    private StringBuilder inputText = new StringBuilder();

    public static int screenHeight = 1080;
    public static int screenWidth = 1920;
    private int cameraSpeed = 3; // Adjust the camera movement speed as needed

    public static boolean[] arrowKeyPressed = {false,false,false,false}; // left, right, up, down
    private final Set<Integer> pressedKeys = new HashSet<>();
    private UserAvatar max;


    public ChatGame() {
        setTitle("ChatGame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(screenWidth, screenHeight);
        setResizable(false);

        // Load map
        // Get the resource URL for the image
        ClassLoader classLoader = ChatGame.class.getClassLoader();
        URL mapurl = classLoader.getResource("level1.map");
        World.loadMap(mapurl.getPath());
        max = new UserAvatar(Color.PINK, 32, 32,5, 32);

        // Load cached textures
        World.loadAllTexturesIntoCache();

        // Add Commands
        CommandHandler.initCommand("buy", Commands::buy);
        CommandHandler.initCommand("color", Commands::color);
        CommandHandler.initCommand("equip", Commands::equip);
        CommandHandler.initCommand("unequip", Commands::unequip);

        drawingPanel = new DrawingPanel();
        add(drawingPanel);

        drawingPanel.setFocusable(true);


        drawingPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();

                switch (keyCode) {
                    case KeyEvent.VK_LEFT:
                        arrowKeyPressed[0] = true;
                        break;
                    case KeyEvent.VK_RIGHT:
                        arrowKeyPressed[1] = true;
                        break;
                    case KeyEvent.VK_UP:
                        arrowKeyPressed[2] = true;
                        break;
                    case KeyEvent.VK_DOWN:
                        arrowKeyPressed[3] = true;
                        break;
                    case KeyEvent.VK_ENTER:
                        flushText();
                        break;
                    case KeyEvent.VK_BACK_SPACE:
                        if (inputText.length() >= 1) {
                            inputText.deleteCharAt(inputText.length() - 1);
                        }
                        break;
                    default:
                        char keyChar = e.getKeyChar();
                        handleInput(keyChar);
                        break;
                }


            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                // LOL
                switch (keyCode) {
                    case KeyEvent.VK_LEFT:
                        arrowKeyPressed[0] = false;
                        break;
                    case KeyEvent.VK_RIGHT:
                        arrowKeyPressed[1] = false;
                        break;
                    case KeyEvent.VK_UP:
                        arrowKeyPressed[2] = false;
                        break;
                    case KeyEvent.VK_DOWN:
                        arrowKeyPressed[3] = false;
                        break;
                }
            }
        });
        // Add avatars
       // World.simpleAvatar = new UserAvatar(Color.GREEN, 10, 10, 50, 50);
        World.setMula(500); // initial money
        World.items_available.add(new Item("gun", 30));
        World.items_available.add(new Item("machete", 10));

        // Create a game timer with a 16ms delay (about 60 FPS)
        drawingPanel.startGameLoop();

        setLocationRelativeTo(null);
    }

    private void handleInput(char inputChar) {
        // Append the typed character to the StringBuilder
        inputText.append(inputChar);
    }

    private void flushText() {
        String flushedText = inputText.toString();
        System.out.println("received command: " + flushedText);

        // Send the command to the command handler
        if (!CommandHandler.validateCommand(flushedText)) {
            System.out.println("Command has errors");
            CommandHandler.commandError = "cmd not valid";
            return;
        }

        boolean cmdResult = CommandHandler.executeCommand(flushedText);

        if (cmdResult) {
            // reset status of command error after one successful command
            CommandHandler.commandError = "";
        }
        // Clear the input text when Enter is pressed
        inputText.setLength(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChatGame game = new ChatGame();
            game.setVisible(true);
        });
    }


    private class DrawingPanel extends JPanel {

        private static final int TARGET_FPS = 120; // Target frames per second
        private static final long TARGET_TIME = 1000000000 / TARGET_FPS; // Target time per frame in nanoseconds

        public DrawingPanel() {
            setBackground(Color.BLACK);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawGame(g);
        }

        private void drawGame(Graphics g) {




            //World.simpleAvatar.draw(g);
            World.drawMap(g,0, 0, getWidth(), getHeight());
            World.drawItemsEquipped(g);
            max.draw(g);
            // Draw Money
            g.setColor(Color.lightGray);
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            g.drawString("$" + String.valueOf(World.getMula()), getWidth() - 50, 30);

            // Draw X and Y coord of camera
            g.drawString("x:" + String.valueOf(max.getX()), getWidth() - 50, 50);
            g.drawString("y:" + String.valueOf(max.getY()), getWidth() - 50, 65);

            // Draw the typed text in white at the bottom of the screen
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 16)); // Set the font size and style
            String textToRender = inputText.toString();
            g.drawString(textToRender, 10, getHeight() - 10); // Position it at the bottom

            // Check if the boolean is set to true and draw the question mark
            if (!CommandHandler.commandError.isEmpty()) {
                g.setColor(Color.RED);
                g.setFont(new Font("Arial", Font.BOLD, 24)); // Adjust the font size and style
                g.drawString("?" + CommandHandler.commandError, 50, 50); // Adjust the position of the question mark
            }
        }

        public void startGameLoop() {
            Thread gameLoop = new Thread(() -> {
                long lastLoopTime = System.nanoTime();

                while (true) {
                    long now = System.nanoTime();
                    long updateLength = now - lastLoopTime;
                    lastLoopTime = now;
                    double delta = updateLength / ((double) TARGET_TIME);

                    // Call the update and render methods here
                    update(delta);
                    repaint(); // This will call paintComponent

                    try {
                        long sleepTime = (lastLoopTime - System.nanoTime() + TARGET_TIME) / 1000000; // Time to sleep in milliseconds
                        if (sleepTime > 0) {
                            Thread.sleep(sleepTime);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            gameLoop.start();
        }

        private void update(double delta) {
            max.update();
        }
    }

    // In your main code or initialization logic


}
