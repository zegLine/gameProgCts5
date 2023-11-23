import java.util.Random;

public class Dot {

    // Dot position
    public double x,y;

    // Dot speed
    public double speed;

    // Dot direction
    public double alfa;

    // The maximum border is considered the current frame (window size)
    public double maxX = Resources.FRAME_WIDTH;
    public double maxY = Resources.FRAME_HEIGHT;

    // Create new dot with given position
    public Dot(double x_, double y_) {
        x=x_; y=y_;

        // Assign the new dot a random speed and direction angle
        Random rand = new Random();
        speed = rand.nextDouble() * 100 + 50;
        alfa = rand.nextDouble() * 2 * Math.PI;

    }

    // Move dot
    public void move(double diffSeconds) {
        double newX = x + Math.cos(alfa) * speed * diffSeconds;
        double newY = y + Math.sin(alfa) * speed * diffSeconds;

        // Check for collisions with the screen borders
        if (newX < 0 || newX > maxX) {
            // Reverse the horizontal direction
            alfa = Math.PI - alfa;
            newX = Math.max(0, Math.min(maxX, newX)); // Ensure the dot stays within the screen
        }
        if (newY < 0 || newY > maxY) {
            // Reverse the vertical direction
            alfa = -alfa;
            newY = Math.max(0, Math.min(maxY, newY)); // Ensure the dot stays within the screen
        }

        x = newX;
        y = newY;
    }

}
