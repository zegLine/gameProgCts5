package main.java.com.zegline.rpggame.GameEntity.Enemies;

import main.java.com.zegline.rpggame.ChatGame;
import main.java.com.zegline.rpggame.UserAvatar;

import java.awt.*;

public class ChargingEnemy extends BaseEnemy {
    private long lastCollisionTime;

    enum movemntState{
        LOOKING_FOR_PLAYER,
        CHARGING
    }

    movemntState currMoveState;
    private static final int DAMAGECONST = 5;

    @Override
    public void addRewardsToPlayer(){
        UserAvatar.setMula(UserAvatar.getMula() + 20);
    }

    public ChargingEnemy(int x, int y, int level) {
        super(x,y,level, new String[]{"chargin/mrchargin.png", "chargin/mrcharginflipped.png"},2);
        this.x = x;
        this.y = y;
        this.speed = 3 + level/5;
        this.c = Color.RED;

        radius = 32;

        this.damage = DAMAGECONST * 2 * level;

        this.health = 15 + 5 * level;

        lastCollisionTime = 0;

        this.maxHealth = this.health;

        this.state = 0;

        this.COOLDOWN_DURATION = 1000;

        this.currMoveState = movemntState.LOOKING_FOR_PLAYER;

    }


    double chargingDistance = 0;

    double XtoCharge = 0;
    double YtoCharge = 0;
    double distanceToPlayer;
    public void handleMovement() {

        int distanceFromPlayer = 150;
        if(currMoveState == movemntState.LOOKING_FOR_PLAYER) {

            double dx = this.x - ChatGame.max.getX();
            double dy = this.y - ChatGame.max.getY();
            int distance = (int) Math.sqrt(dx * dx + dy * dy);


            if (distance > distanceFromPlayer ) {


                if (x > ChatGame.max.getX()) {
                    moveLeft();
                    state = 1;

                }

                if (x < ChatGame.max.getX()) {
                    moveRight();
                    state = 0;
                }

                if (y > ChatGame.max.getY()) {
                    moveUp();
                }

                if (y < ChatGame.max.getY()) {
                    moveDown();
                }
            } else {
                currMoveState = movemntState.CHARGING;
                XtoCharge = ChatGame.max.getX() - this.x;
                YtoCharge = ChatGame.max.getY() - this.y;
                distanceToPlayer = Math.sqrt(dx * dx + dy * dy);
                chargingDistance = (distanceFromPlayer * 2) + 100;
            }
        } else {


            double dx = (XtoCharge / distanceToPlayer) * speed * 3;
            double dy = (YtoCharge / distanceToPlayer) *  speed * 3;

            this.x += dx;
            this.y += dy;

            chargingDistance -= speed * 3;
            if(chargingDistance < 0){
                currMoveState = movemntState.LOOKING_FOR_PLAYER;
            }
        }
    }








}
