package main.java.com.zegline.rpggame.GameEntity.Bullets;

import main.java.com.zegline.rpggame.GameEntity.Enemies.EnemyBaseBullet;

public class ShootingEnemyBullet extends EnemyBaseBullet {
    public ShootingEnemyBullet(int x, int y, double angle) {
        super(x,y,angle);
        this.damage = 5;
    }
}
