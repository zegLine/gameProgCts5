package main.java.com.zegline.rpggame;

public class EnemyBulletFactory {
    private double x;
    private double y;

    BulletType bulletType;
    public EnemyBulletFactory(BulletType bulletType,double x, double y){
        this.x = x;
        this.y = y;
        this.bulletType = bulletType;
        ChatGame.bulletCreateList.push(this);
    }



    public void shootPlayer() {
        double dx,dy;
        dx =  ChatGame.max.getX() - x ;
        dy =  ChatGame.max.getY() - y;

        double angle;
        angle = Math.atan2(dy, dx);

        switch (bulletType) {
            case SHOOTING_ENEMY -> new ShootingEnemyBullet((int)x,(int)y,angle);
        }

    }
}
