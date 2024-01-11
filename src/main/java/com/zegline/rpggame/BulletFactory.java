package main.java.com.zegline.rpggame;

public class BulletFactory {

    private double dx;
    private double dy;
    private double angle;
    public BulletFactory(){
    }

    public void shoot(BulletType bt){

        dx = ChatGame.mouseX - ChatGame.max.getX();
        dy = ChatGame.mouseY - ChatGame.max.getY();
        angle = Math.atan2(dy, dx);

        switch (bt){
            case BASIC -> new BasicBullet(ChatGame.max.getX(),ChatGame.max.getY(),angle);
        }
    }



}
