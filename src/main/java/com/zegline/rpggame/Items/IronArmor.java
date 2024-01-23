package main.java.com.zegline.rpggame.Items;

public class IronArmor extends BaseArmor{
    public IronArmor(String name, int cost) {
        super(name, cost, "ironarmor.png");
        //Why is there an intial boost, i was thinking that
        // amrours dont stack and you have to buy a new one once
        // the old one breaks?
        // if you buy a new one, it replaces the old one
        this.initialArmorBoost = 10;
        this.armorHealth = 50;
        this.armorMultiplier = 0.8;
    }
}
