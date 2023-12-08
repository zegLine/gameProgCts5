package test_chatgame;

public class Item {

    public String name;
    public int cost;
    public int min_level;
    public String texture_name;
    public void act(UserAvatar user, World w) {

    }

    public Item(String name, int cost) {
        this.name = name;
        this.cost = cost;
        this.min_level = 1;
    }
}
