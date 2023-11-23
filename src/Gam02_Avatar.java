public class Gam02_Avatar extends A_GameObject {

    // destination the Avatar shall move to
    private double destX;
    private double destY;
    private boolean isMoving = false;
    public Gam02_Avatar(double x_, double y_)
    {x=x_; y=y_; speed=200;
    }
    public void move(double diffSeconds)
    {
        // If no moving shall occur, return
        if (!isMoving) return;

        // Calculate the distance to the destination
        double diffX = destX - x;
        double diffY = destY - y;
        double distance = Math.sqrt(diffX * diffX + diffY * diffY);

        // If the distance is very small, stop moving
        if (distance < 2) {
            isMoving = false;
            return;
        }

        // Calculate the ratio to move towards the destination
        double moveRatio = speed * diffSeconds / distance;

        // Update the position
        x += diffX * moveRatio;
        y += diffY * moveRatio;

        // Gradually reduce the speed as the avatar approaches its destination
        double minSpeed = 10;  // Minimum speed at which the avatar can move
        double maxDeceleration = 500; // Maximum deceleration rate
        double decelerationRate = Math.min(maxDeceleration, speed / distance);

        if (speed > minSpeed) {
            speed -= decelerationRate * diffSeconds;
            speed = Math.max(minSpeed, speed);
        }


        super.move(diffSeconds);
    }
    public void setDestination(double dx, double dy)
    {
        isMoving = true;
        destX = dx;
        destY = dy;
        alfa = Math.atan2(dy-y, dx-x);
    }

}
