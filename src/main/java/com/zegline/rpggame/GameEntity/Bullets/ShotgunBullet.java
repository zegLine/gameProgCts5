package main.java.com.zegline.rpggame.GameEntity.Bullets;

public class ShotgunBullet extends Bullet {


    public ShotgunBullet(int x, int y, double angle) {
        super(x, y, angle,"bullet3.png");
        this.speed = 17;
        this.damage = 4;
        this.radius = 3;
    }
}
