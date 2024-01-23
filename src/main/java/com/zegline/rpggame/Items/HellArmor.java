package main.java.com.zegline.rpggame.Items;

public class HellArmor extends BaseArmor{
    public HellArmor(String name, int cost) {
        super(name, cost, "hellarmor.png");
        //Why is there an intial boost, i was thinking that
        // amrours dont stack and you have to buy a new one once
        // the old one breaks?
        // if you buy a new one, it replaces the old one
        this.initialArmorBoost = 10;
        this.armorHealth = 100;
        this.armorMultiplier = 0.3;
    }
}
