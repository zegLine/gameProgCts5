package main.java.com.zegline.rpggame.GameEntity.Bullets;

import main.java.com.zegline.rpggame.ChatGame;
import main.java.com.zegline.rpggame.GameEntity.Enemies.BaseEnemy;
import main.java.com.zegline.rpggame.GameEntity.GameEntity;

import java.util.Iterator;

public class RocketBullet extends Bullet {

    int damageRadius = 100;
    public RocketBullet(int x, int y, double angle) {
        super(x, y, angle,"bullet4.png");
        this.speed = 12;
        this.damage = 10;
        this.radius = 12;
    }
    @Override
    protected void handleCollision() {
        if (x > ChatGame.screenWidth || x < 0 || y > ChatGame.screenHeight || y < 0) {
            //System.out.println("bullet dead");
            this.death();
        }

        Iterator<GameEntity> iterator = ChatGame.gameEntityList.iterator();
        while (iterator.hasNext()) {
            GameEntity entity = iterator.next();

            if(!(entity instanceof BaseEnemy)){
                continue;
            }
            BaseEnemy enemy = (BaseEnemy)entity;
            double dx = this.x - enemy.getX();
            double dy = this.y - enemy.getY();
            int distance = (int) Math.sqrt(dx * dx + dy * dy);

            // Check if the circles overlap (collision occurs when distance <= sum of radii)
            if (distance <= (this.radius + enemy.getRadius() + damageRadius)) {
                System.out.println("hit");
                enemy.doDamage(damage);
                death();
            }
        }
    }
}
