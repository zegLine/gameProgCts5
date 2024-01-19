package main.java.com.zegline.rpggame.Items;

public abstract class BaseSpell extends Item {

    public void useSpell() {
        System.out.println("Using base spell");
    }

    @Override
    public void act(){
        useSpell();
    }
    public BaseSpell(String name, int cost, String texture_name) {
        super(name, cost, texture_name);
    }
}
