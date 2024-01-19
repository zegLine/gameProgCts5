package main.java.com.zegline.rpggame.Items;

import main.java.com.zegline.rpggame.GameEntity.Bullets.BulletFactory;
import main.java.com.zegline.rpggame.GameEntity.Bullets.BulletType;

public class ShotGun extends BaseGun{

    @Override
    public void shoot(){
        new BulletFactory().shoot(BulletType.SHOTGUN);
    }


    public ShotGun(String name, int cost) {
        super(name, cost, "enhanced_gun.png");
    }
}
