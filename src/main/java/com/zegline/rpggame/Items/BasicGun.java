package main.java.com.zegline.rpggame.Items;

import main.java.com.zegline.rpggame.GameEntity.Bullets.BulletFactory;
import main.java.com.zegline.rpggame.GameEntity.Bullets.BulletType;

public class BasicGun extends BaseGun{

    @Override
    public void shoot(){
        new BulletFactory().shoot(BulletType.BASIC);
    }


    public BasicGun(String name, int cost) {
        super(name, cost, "basic_gun.png");
    }
}