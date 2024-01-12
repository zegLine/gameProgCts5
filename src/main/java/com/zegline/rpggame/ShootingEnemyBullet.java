package main.java.com.zegline.rpggame;

public class ShootingEnemyBullet extends EnemyBaseBullet {
    public ShootingEnemyBullet(int x, int y, double angle) {
        super(x,y,angle);
        this.damage = 5;
    }
}
