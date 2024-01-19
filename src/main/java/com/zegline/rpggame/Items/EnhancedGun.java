package main.java.com.zegline.rpggame.Items;

import main.java.com.zegline.rpggame.GameEntity.Bullets.BulletFactory;
import main.java.com.zegline.rpggame.GameEntity.Bullets.BulletType;

public class EnhancedGun extends BaseGun{

    @Override
    public void shoot(){
        new BulletFactory().shoot(BulletType.BASIC);
        new BulletFactory().shoot(BulletType.BIGGUN);
    }


    public EnhancedGun(String name, int cost) {
        super(name, cost, "enhanced_gun.png");
    }
}
