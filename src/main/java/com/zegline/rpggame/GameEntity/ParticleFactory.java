package main.java.com.zegline.rpggame.GameEntity;

import java.awt.*;

public class ParticleFactory {
    int x; int y; int radius; int sizeSubparticle; Color color; long animationTime;
    public ParticleFactory(int x, int y, int radius, int sizeSubparticle, Color color, long animationTime) {
        this.x = x;
        this.y = y;
        this.radius =radius;
        this.sizeSubparticle = sizeSubparticle;
        this.color = color;
        this.animationTime = animationTime;
    }

    public void spawn(){
        new Particle((int) x, (int) y, radius, sizeSubparticle,color, animationTime);
    }
}
