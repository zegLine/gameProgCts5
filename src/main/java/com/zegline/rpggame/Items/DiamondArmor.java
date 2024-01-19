package main.java.com.zegline.rpggame.Items;

public class DiamondArmor extends BaseArmor{
    public DiamondArmor(String name, int cost) {
        super(name, cost, "diamond_armor.png");
        this.initialArmorBoost = 40;
        this.armorHealth = 20;
        this.armorMultiplier = 0.7;
    }
}
