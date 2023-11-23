import java.awt.*;

public abstract class A_GameObject {

    public double x,y;
    public double speed = 0;
    public double alfa = 0;

    public Color color;

    public int radius = 20;

    public A_GameObject() {

    }

    public A_GameObject(double x, double y, double a, double s) {
        this.x = x;
        this.y = y;
        this.alfa = a;
        this.speed = s;
    }

    public void move(double diffSeconds)
    {
        double newX = x + Math.cos(alfa) * speed * diffSeconds;
        double newY = y + Math.sin(alfa) * speed * diffSeconds;

        // Check for collisions with the screen borders
        if (newX < 0 || newX > Resources.FRAME_WIDTH) {
            // Reverse the horizontal direction
            alfa = Math.PI - alfa;
            newX = Math.max(0, Math.min(Resources.FRAME_WIDTH, newX)); // Ensure the dot stays within the screen
        }
        if (newY < 0 || newY > Resources.FRAME_HEIGHT) {
            // Reverse the vertical direction
            alfa = -alfa;
            newY = Math.max(0, Math.min(Resources.FRAME_HEIGHT, newY)); // Ensure the dot stays within the screen
        }

        x = newX;
        y = newY;
    }

}
