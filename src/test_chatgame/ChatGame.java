package test_chatgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

public class ChatGame extends JFrame {

    private DrawingPanel drawingPanel;
    private Timer gameTimer;

    // Create a StringBuilder to store the typed characters
    private StringBuilder inputText = new StringBuilder();

    public ChatGame() {
        setTitle("ChatGame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setResizable(false);

        // Add Commands
        CommandHandler.initCommand("buy", Commands::buy);
        CommandHandler.initCommand("color", Commands::color);

        drawingPanel = new DrawingPanel();
        add(drawingPanel);

        drawingPanel.setFocusable(true);
        drawingPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char keyChar = e.getKeyChar();
                int keyCode = e.getKeyCode();

                switch (keyCode) {
                    case KeyEvent.VK_ENTER:
                        flushText();
                        break;
                    case KeyEvent.VK_BACK_SPACE:
                        if (inputText.length() >= 1) {
                            inputText.deleteCharAt(inputText.length() - 1);
                        }
                        break;
                    default:
                        handleInput(keyChar);
                        break;
                }
            }
        });

        // Add avatars
        World.simpleAvatar = new UserAvatar(Color.GREEN, 10, 10, 50, 50);
        World.setMula(500); // initial money
        World.items_available.add(new Item("gun", 30));
        World.items_available.add(new Item("machete", 10));

        // Create a game timer with a 16ms delay (about 60 FPS)
        gameTimer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update game logic here

                // Repaint the drawing panel to update the screen
                drawingPanel.repaint();
            }
        });
        gameTimer.start();

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
        public DrawingPanel() {
            setBackground(Color.BLACK);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            World.simpleAvatar.draw(g);
            World.drawItemsEquipped(g);

            g.setColor(Color.lightGray);
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            g.drawString("$" + String.valueOf(World.getMula()), getWidth() - 50, 30);

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
    }
}
