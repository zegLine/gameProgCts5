package main.java.com.zegline.rpggame;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.util.*;

public class ChatGame extends JFrame {

    private MainGamePanel drawingPanel;
    private Timer gameTimer;

    // Create a StringBuilder to store the typed characters
    public static StringBuilder inputText = new StringBuilder();

    public static int screenHeight = 1080;
    public static int screenWidth = 1920;
    private int cameraSpeed = 3; // Adjust the camera movement speed as needed

    public static boolean[] arrowKeyPressed = {false,false,false,false}; // left, right, up, down
    private final Set<Integer> pressedKeys = new HashSet<>();
    public static UserAvatar max;

    public static double mouseX;

    public static double mouseY;

    public static ArrayList<BaseEnemy> enemyList = new ArrayList<>();

    public static ArrayList<GameEntity> bulletList = new ArrayList<>();

    public static ArrayList<GameEntity> gameEntityList = new ArrayList<>();

    public static Stack<GameEntity> deathList = new Stack<GameEntity>();
    public static boolean mouseClicked;

    public static boolean commandMode = false;

    public static MainMenuPanel mmp = new MainMenuPanel();


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

        new BasicEnemy(500,500,1);
        //new BasicEnemy(1500,500,2);

        mouseClicked = false;

        // Load cached textures
        World.loadAllTexturesIntoCache();

        // Add Commands
        CommandHandler.initCommand("buy", Commands::buy);
        CommandHandler.initCommand("color", Commands::color);
        CommandHandler.initCommand("equip", Commands::equip);
        CommandHandler.initCommand("unequip", Commands::unequip);

        drawingPanel = new MainGamePanel();
        add(drawingPanel);
        drawingPanel.setFocusable(true);

        drawingPanel.addMouseListener(Input.ml);
        drawingPanel.addKeyListener(Input.ka);

        // Add avatars
       // World.simpleAvatar = new UserAvatar(Color.GREEN, 10, 10, 50, 50);
        World.setMula(500); // initial money
        World.items_available.add(new Item("gun", 30));
        World.items_available.add(new Item("machete", 10));

        // Create a game timer with a 16ms delay (about 60 FPS)
        drawingPanel.startGameLoop();

        setLocationRelativeTo(null);
    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChatGame game = new ChatGame();
            game.setVisible(true);
        });
    }




}
