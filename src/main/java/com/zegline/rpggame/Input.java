package main.java.com.zegline.rpggame;

import com.sun.tools.javac.Main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Input {

    public static final MouseListener ml = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {


        }

        @Override
        public void mousePressed(MouseEvent e) {
            ChatGame.mouseX = e.getX();
            ChatGame.mouseY = e.getY();

            ChatGame.mouseClicked = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    };


    public static final KeyAdapter ka = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {

            int keyCode = e.getKeyCode();

            if (keyCode == KeyEvent.VK_ESCAPE) {
                if (MainGamePanel.currentGameState == MainGamePanel.GameState.GAMEPLAY)
                    MainGamePanel.currentGameState = MainGamePanel.GameState.PAUSE_MENU;
                else {
                    MainGamePanel.currentGameState = MainGamePanel.GameState.GAMEPLAY;
                }
            }

            if (keyCode == KeyEvent.VK_CONTROL) {
                ChatGame.commandMode = ChatGame.commandMode == true ? false:true ;
                return;
            }
            if (ChatGame.commandMode) {
                switch (keyCode) {
                    case KeyEvent.VK_ENTER:
                        flushText();
                        break;
                    case KeyEvent.VK_BACK_SPACE:
                        if (!ChatGame.inputText.isEmpty()) {
                            ChatGame.inputText.deleteCharAt(ChatGame.inputText.length() - 1);
                        }
                        break;
                    default:
                        if((keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z) || keyCode == KeyEvent.VK_SPACE) {
                            char keyChar = e.getKeyChar();
                            handleInput(keyChar);
                        }
                        break;
                }

            }

            switch (keyCode) {
                case KeyEvent.VK_A:
                    ChatGame.arrowKeyPressed[0] = true;
                    break;
                case KeyEvent.VK_D:
                    ChatGame.arrowKeyPressed[1] = true;
                    break;
                case KeyEvent.VK_W:
                    ChatGame.arrowKeyPressed[2] = true;
                    break;
                case KeyEvent.VK_S:
                    ChatGame.arrowKeyPressed[3] = true;
                    break;

            }
        }
        @Override
        public void keyReleased(KeyEvent e) {
            int keyCode = e.getKeyCode();
            // LOL
            switch (keyCode) {
                case KeyEvent.VK_A:
                    ChatGame.arrowKeyPressed[0] = false;
                    break;
                case KeyEvent.VK_D:
                    ChatGame.arrowKeyPressed[1] = false;
                    break;
                case KeyEvent.VK_W:
                    ChatGame.arrowKeyPressed[2] = false;
                    break;
                case KeyEvent.VK_S:
                    ChatGame.arrowKeyPressed[3] = false;
                    break;
            }
        }
    };

    private static void handleInput(char inputChar) {
        // Append the typed character to the StringBuilder
        ChatGame.inputText.append(inputChar);
    }

    public static void flushText() {
        String flushedText = ChatGame.inputText.toString();
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
        ChatGame.inputText.setLength(0);
    }
}
