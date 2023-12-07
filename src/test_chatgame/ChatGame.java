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

        drawingPanel = new DrawingPanel();
        add(drawingPanel);

        drawingPanel.setFocusable(true);
        drawingPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    flushText();
                } else {
                    handleInput(e.getKeyChar());
                }
            }
        });

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
            return;
        }
        CommandHandler.executeCommand(flushedText);
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

            // Draw the typed text in white at the bottom of the screen
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 16)); // Set the font size and style
            String textToRender = inputText.toString();
            g.drawString(textToRender, 10, getHeight() - 10); // Position it at the bottom
        }
    }
}
