package main.java.com.zegline.rpggame.Items;

public class BasicArmor extends BaseArmor{
    public BasicArmor(String name, int cost) {
        super(name, cost, "basic_armor.png");
        //Why is there an intial boost, i was thinking that
        // amrours dont stack and you have to buy a new one once
        // the old one breaks?
        // if you buy a new one, it replaces the old one
        this.initialArmorBoost = 10;
        this.armorHealth = 20;
        this.armorMultiplier = 1.0;
    }
}
