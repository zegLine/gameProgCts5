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
// if no moving shall occur
        if(!isMoving) return;
// stop if destination is reached
        double diffX = Math.abs(x-destX);
        double diffY = Math.abs(y-destY);
        if(diffX<2 && diffY<2)
        { isMoving = false;
            return;
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
