public class Dot {

    public double x,y;
    public double speed = 100;
    public double alfa = -0.2;
    public Dot(double x_, double y_) {
        x=x_; y=y_;
    }
    public void move(double diffSeconds) {
        x += Math.cos(alfa)*speed*diffSeconds;
        y += Math.sin(alfa)*speed*diffSeconds;
    }

}
