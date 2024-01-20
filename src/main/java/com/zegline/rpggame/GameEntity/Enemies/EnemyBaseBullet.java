package main.java.com.zegline.rpggame.GameEntity.Enemies;

import main.java.com.zegline.rpggame.ChatGame;
import main.java.com.zegline.rpggame.GameEntity.Bullets.Bullet;

public class EnemyBaseBullet extends Bullet {
    public EnemyBaseBullet(int x, int y, double angle) {
        super(x, y, angle);
    }

    public void update() {
        this.handleMovement();
        this.handleCollision();
    }

    protected void handleCollision() {
        if (this.x > ChatGame.screenWidth || this.x < 0 || this.y > ChatGame.screenHeight || this.y < 0) {
            //System.out.println("bullet dead");
            this.death();
        }



        double dx = this.x - ChatGame.max.getX();
        double dy = this.y - ChatGame.max.getY();
        int distance = (int) Math.sqrt(dx * dx + dy * dy);

        // Check if the circles overlap (collision occurs when distance <= sum of radii)
        if (distance <= (this.radius +  ChatGame.max.getRadius())) {
            System.out.println("hit");
            ChatGame.max.doDamage(damage);
            death();

        }
    }
}
