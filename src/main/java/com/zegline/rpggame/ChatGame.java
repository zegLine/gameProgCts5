package main.java.com.zegline.rpggame;

import main.java.com.zegline.rpggame.GameEntity.Bullets.EnemyBulletFactory;
import main.java.com.zegline.rpggame.GameEntity.Enemies.BaseEnemy;
import main.java.com.zegline.rpggame.GameEntity.Enemies.BasicEnemy;
import main.java.com.zegline.rpggame.GameEntity.Enemies.ChargingEnemy;
import main.java.com.zegline.rpggame.GameEntity.Enemies.EnemyFactory;
import main.java.com.zegline.rpggame.GameEntity.GameEntity;
import main.java.com.zegline.rpggame.GameEntity.ShopOwner;
import main.java.com.zegline.rpggame.Items.*;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.*;
import java.util.List;

import static main.java.com.zegline.rpggame.SoundEngine.FIGHT_SONG_QUEUE;
import static main.java.com.zegline.rpggame.SoundEngine.MAIN_SONG_QUEUE;


public class ChatGame extends JFrame {

    public static int currentWave;
    public static boolean waveOngoing;
    public static boolean shiftPressed;
    private MainGamePanel drawingPanel;

    // Create a StringBuilder to store the typed characters
    public static StringBuilder inputText = new StringBuilder();

    public static int baseHeight = 20;
    public static int baseWidth = 30;
    public static int scale = 41;

    public static int screenHeight = baseHeight * scale;
    public static int screenWidth = baseWidth * scale;



    public static boolean[] arrowKeyPressed = {false,false,false,false}; // left, right, up, down
    private final Set<Integer> pressedKeys = new HashSet<>();
    public static UserAvatar max;

    public static double mouseX;

    public static double mouseY;

    public static ArrayList<GameEntity> gameEntityList = new ArrayList<>();

    public static Stack<GameEntity> deathList = new Stack<GameEntity>();

    public static Stack<EnemyBulletFactory> bulletCreateList = new Stack<EnemyBulletFactory>();

    public static EnemyFactory enemyFactory = null;

    public static boolean mouseClicked;

    public static boolean commandMode = false;

    public static boolean debugMode = false;

    public static List<Item> WeaponsList;


    public ChatGame() {
        BaseEnemy.aliveEnemies = 0;

        setTitle("ChatGame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(screenWidth, screenHeight);
        setResizable(false);

        SoundEngine.addToQueue("musical/test.wav", MAIN_SONG_QUEUE);
        SoundEngine.addToQueue("musical/test2.wav", FIGHT_SONG_QUEUE);
        SoundEngine.play(MAIN_SONG_QUEUE);
        // Load map
        // Get the resource URL for the image
        ClassLoader classLoader = ChatGame.class.getClassLoader();
        URL mapurl = classLoader.getResource("level1.map");
        World.loadMap(mapurl.getPath());
        max = new UserAvatar(Color.PINK, 64, screenHeight-140,3, 32, new String[]{"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"}, 16);
        BasicGun basicGun = new BasicGun("simplegun", 69);
        EnhancedGun enhancedGun = new EnhancedGun("enhancedgun", 120);
        ShotGun shotGun = new ShotGun("shotgun", 150);
        RocketGun rocketGun = new RocketGun("rpg", 100);
        //UserAvatar.items_equipped.add(basicGun);
        //UserAvatar.items_equipped.add(enhancedGun);
        //UserAvatar.items_equipped.add(shotGun);
        //new ShootingEnemy(1000,1800,1);
        //new BasicEnemy(500,500,2);
        //new ChargingEnemy(1000,500,1);


        max.equipArmor(new BasicArmor("basicarmor", 100));
        UserAvatar.items_equipped.add(basicGun);
        UserAvatar.item_in_hand = basicGun;

        WeaponsList = new ArrayList<>();
        WeaponsList.add(basicGun);
        WeaponsList.add(enhancedGun);
        WeaponsList.add(shotGun);
        WeaponsList.add(rocketGun);
        //a.add(new BasicGun("basegun", 69, "basic_gun.png"));
        //a.add(new Item("arfifteen", 500));
        new ShopOwner(110, 90, 100, "Heyo", WeaponsList, "basic_shopowner.jpg");
        new ShopOwner(550, 100, 150, "Captain", WeaponsList, "wanderer.jpg");

        currentWave = 0;
        waveOngoing = false;


        mouseClicked = false;

        // Load cached textures
        World.loadAllTexturesIntoCache();

        // Add Commands
        CommandHandler.initCommand("buy", Commands::buy);
        CommandHandler.initCommand("color", Commands::color);
        CommandHandler.initCommand("equip", Commands::equip);
        CommandHandler.initCommand("unequip", Commands::unequip);

        //cheats
        CommandHandler.initCommand("iddqd", Commands::god);
        CommandHandler.initCommand("setwave", Commands::setWave);
        CommandHandler.initCommand("moneypls", Commands::getMoney);
        CommandHandler.initCommand("idfa", Commands::getAllWeapons);

        drawingPanel = new MainGamePanel();
        add(drawingPanel);
        drawingPanel.setFocusable(true);

        drawingPanel.addMouseListener(Input.ml);
        drawingPanel.addKeyListener(Input.ka);

        // Add avatars
       // World.simpleAvatar = new UserAvatar(Color.GREEN, 10, 10, 50, 50);
        UserAvatar.setMula(100); // initial money

        // Create a game timer with a 16ms delay (about 60 FPS)
        drawingPanel.startGameLoop();

        setLocationRelativeTo(null);
    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameInstance.gameInstance = new ChatGame();
            GameInstance.gameInstance.setVisible(true);
        });
    }

    public void resizeWin() {

        screenHeight = baseHeight * scale;
        screenWidth = baseWidth * scale;
        System.out.println(scale);
        setSize(screenWidth, screenHeight);
    }

}
