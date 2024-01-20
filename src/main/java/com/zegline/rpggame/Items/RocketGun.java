package main.java.com.zegline.rpggame.Items;

import main.java.com.zegline.rpggame.GameEntity.Bullets.BulletFactory;
import main.java.com.zegline.rpggame.GameEntity.Bullets.BulletType;

public class RocketGun extends BaseGun{

    @Override
    public void shoot(){
        new BulletFactory().shoot(BulletType.ROCKET);
    }


    public RocketGun(String name, int cost) {
        super(name, cost, "rpg.png");
    }
}
