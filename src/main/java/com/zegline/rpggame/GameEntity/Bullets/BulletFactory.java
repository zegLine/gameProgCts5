package main.java.com.zegline.rpggame.GameEntity.Bullets;

import main.java.com.zegline.rpggame.ChatGame;
import main.java.com.zegline.rpggame.GameEntity.Bullets.BasicBullet;
import main.java.com.zegline.rpggame.GameEntity.Bullets.BulletType;

import java.util.Random;

public class BulletFactory {

    private double dx;
    private double dy;
    private double angle;
    private Random random;
    public BulletFactory(){
        random = new Random();
    }

    public void shoot(BulletType bt){

        dx = ChatGame.mouseX - ChatGame.max.getX();
        dy = ChatGame.mouseY - ChatGame.max.getY();
        angle = Math.atan2(dy, dx);


        switch (bt){
            case BASIC -> new BasicBullet(ChatGame.max.getX(),ChatGame.max.getY(),angle);
            case BIGGUN -> new BigBullet(ChatGame.max.getX(),ChatGame.max.getY(),angle);
            case SHOTGUN -> {
                int numberOfBullets = 5 + random.nextInt(6); // Random number between 5 and 10
                for (int i = 0; i < numberOfBullets; i++) {
                    double modifiedAngle = angle + random.nextDouble() * 0.2 - 0.1; // Slightly modify the angle
                    new ShotgunBullet(ChatGame.max.getX(), ChatGame.max.getY(), modifiedAngle);
                }
            }
        }
    }



}
