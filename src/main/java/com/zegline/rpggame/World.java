package main.java.com.zegline.rpggame;

import main.java.com.zegline.rpggame.Items.BaseArmor;
import main.java.com.zegline.rpggame.Items.Item;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.awt.*;
import java.net.URL;
import java.util.List;

import static main.java.com.zegline.rpggame.UserAvatar.*;

public class World {

    public static void drawShop(Graphics g) {
        Image backgroundImg =  new ImageIcon(World.class.getClassLoader().getResource("ShopMap.png")).getImage();
        g.drawImage(backgroundImg,0,0,1230,820,null);
    }

    static enum Texture {
        GRASS_TEXTURE("grass.png", 'g'),
        WATER_TEXTURE("water.png", 'w'),
        STONE_TEXTURE("stone.png", 's');

        private String filename;
        private char letter;

        Texture(String fn, char let) {
            this.filename = fn;
            this.letter = let;
        }

        public String getFilename(){
            return filename;
        }

        public char getLetter() {
            return letter;
        }

    }
    static Map<Texture, Image> cachedTextures = new HashMap<>();

    private static Image loadTextureImage(Texture t) {
        ClassLoader classLoader = World.class.getClassLoader();
        URL imageURL = classLoader.getResource(t.getFilename());

        if (imageURL != null) {
            return new ImageIcon(imageURL).getImage();
        } else {
            // Handle the case where the image resource was not found
            System.err.println("Image resource not found: " + t.getFilename());
            return null;
        }
    }

    public static void loadAllTexturesIntoCache() {
        for (Texture t: Texture.values()) {
            cachedTextures.put(t, loadTextureImage(t));
        }
    }

    public static List<char[]> loadedMap = new ArrayList<>();

    public static void loadMap(String mapFilePath) {
        List<char[]> loadedMap = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(mapFilePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                line = line.replace(" ", "");
                // Split the line into characters to populate the map array
                char[] rowChars = line.toCharArray();

                // Add the row to the loadedMap list
                loadedMap.add(rowChars);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set the loadedMap variable
        World.loadedMap = loadedMap;
    }
    public static UserAvatar simpleAvatar;









    private static int initialx = 10;
    private static int initialy = 100;
    public static void drawItemsEquipped(Graphics g) {
        if (items_equipped.isEmpty()) return;
        int y = initialy;
        for (Item i : items_equipped) {
            if (i == item_in_hand) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.LIGHT_GRAY);
            }
            g.setFont(MainGamePanel.retroFont.deriveFont(18.0f));
            g.drawString(i.name, initialx, y);
            y += 15;
        }
    }

    public static void drawMap(Graphics g, int cameraX, int cameraY, int viewportWidth, int viewportHeight) {

        if(true) {
            Image backgroundImg =  new ImageIcon(World.class.getClassLoader().getResource("MainMap.png")).getImage();
            g.drawImage(backgroundImg,0,0,1230,820,null);
            return;
        }


        int tileSize = 64; // Size of each tile

        for (int row = 0; row < loadedMap.size(); row++) {
            char[] mapRow = loadedMap.get(row);

            for (int col = 0; col < mapRow.length; col++) {
                char terrain = mapRow[col];

                // Load and draw the tile image based on the terrain type
                Image tileImage = loadImageForTerrain(terrain);

                // Calculate the position to draw the tile
                int x = col * tileSize ;
                int y = row * tileSize ;

                // Draw the tile image
                g.drawImage(tileImage, x, y, tileSize, tileSize, null);
            }
        }
    }


    // Load and return an image based on the terrain type
    private static Image loadImageForTerrain(char terrain) {
        for (Texture t : Texture.values()) {
            if (terrain == t.getLetter()) return cachedTextures.get(t);
        }
        return cachedTextures.get(Texture.STONE_TEXTURE);
    }


    public static void equipItem(Item item) {
        for (Item i : items_equipped) {
            if (i == item) {
                // If it is armor, we equip it differently
                if (i instanceof BaseArmor) {
                    ChatGame.max.equipArmor((BaseArmor) i);
                    return;
                }

                // Else we put the item in hand
                item_in_hand = i;
            }
        }
    }

    public static void unEquipItem() {
        item_in_hand = null;
    }

}
