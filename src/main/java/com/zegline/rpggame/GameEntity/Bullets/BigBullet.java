package main.java.com.zegline.rpggame.GameEntity.Bullets;

public class BigBullet extends Bullet {


    public BigBullet(int x, int y, double angle) {
        super(x, y, angle,"bullet2.png");
        this.speed = 25;
        this.damage = 3;
        this.radius = 7;
    }
}
