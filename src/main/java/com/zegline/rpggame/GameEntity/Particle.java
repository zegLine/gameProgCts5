package main.java.com.zegline.rpggame.GameEntity;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Timer;
import java.util.TimerTask;

public class Particle extends GameEntity {
    private Color color;
    private long animationTime;
    private Timer animationTimer;

    private int velocidad;
    private int numParticles;
    private int sizeSubparticle;
    private int alpha; // Transparency level

    public Particle(int x, int y, int radius, int sizeSubparticle, Color color, long animationTime) {
        super(x,y,false);
        this.radius = radius;
        this.color = color;
        this.sizeSubparticle = sizeSubparticle;
        this.animationTime = animationTime;
        this.velocidad = 1;
        this.numParticles = (int) (Math.random() * 10);
        this.alpha = 255; // Fully opaque

        this.animationTimer = new Timer();
        startAnimation();
    }

    public Particle(int x, int y, int radius, int sizeSubparticle, Color color, long animationTime, boolean addToList) {
        super(x,y,addToList);
        this.radius = radius;
        this.color = color;
        this.sizeSubparticle = sizeSubparticle;
        this.animationTime = animationTime;
        this.velocidad = 1;
        this.numParticles = (int) (Math.random() * 10);
        this.alpha = 255; // Fully opaque


        //startAnimation();
    }

    public void startAnimation() {
        this.animationTimer = new Timer();
        TimerTask animationTask = new TimerTask() {
            @Override
            public void run() {
                animationTimer.cancel();
            }
        };
        animationTimer.schedule(animationTask, animationTime);

        TimerTask drawTask = new TimerTask() {
            @Override
            public void run() {
                update();
                if (alpha <= 0) {
                    // Cancel animation timer when animation is done
                    animationTimer.cancel();
                    death();
                }
            }
        };
        animationTimer.scheduleAtFixedRate(drawTask, 0, 10);
    }

    @Override
    public void draw(Graphics g) {
        if (isAnimating()) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));

            int centerX = (int) (getX() + radius); // Center of the circle
            int centerY = (int) (getY() + radius); // Center of the circle

            int numEllipses = 5; // Number of ellipses in the circle
            double angleIncrement = 2 * Math.PI / numEllipses; // Angle increment

            for (int i = 0; i < numEllipses; i++) {
                // Calculate ellipse position around the circle
                int xEllipse = (int) (centerX - radius + radius * Math.cos(i * angleIncrement));
                int yEllipse = (int) (centerY - radius + radius * Math.sin(i * angleIncrement));

                g2d.fill(new Ellipse2D.Double(xEllipse, yEllipse, sizeSubparticle, sizeSubparticle));
            }

            // Decrease alpha to make the particle fade out
            alpha -= 2;
        }
    }


    @Override
    public void update() {
        if (isAnimating()) {
            // Update particle's position based on velocity
            radius += velocidad;

            // Decrease alpha to make the particle fade out
            alpha -= 2;
        }
    }

    public boolean isAnimating() {
        return animationTimer != null && animationTimer.purge() == 0 && alpha > 0;
    }
}
