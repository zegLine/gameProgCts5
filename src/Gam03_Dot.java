import java.awt.*;

public class Gam03_Dot extends A_GameObject {

    public Gam03_Dot(double x, double y, double a, double s)
    {
        super(x,y,a,s);
        radius = 14;
        int r = (int)(Math.random()*256);
        int g = (int)(Math.random()*256);
        int b = (int)(Math.random()*256);
        color = new Color(r,g,b);
    }
    public void move(double diffSeconds)
    {
        super.move(diffSeconds);
    }

}
