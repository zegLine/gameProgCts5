import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CircleWorld
{
    private GraphicSystem graphicSystem;
    private final ArrayList<Dot> dots = new ArrayList<>();
    public void init()
    {
        Random rand = new Random();

        for (int i = 0; i < 100; i++) {
            double x_current = rand.nextDouble() * Resources.FRAME_WIDTH;
            double y_current = rand.nextDouble() * Resources.FRAME_HEIGHT;
            dots.add(new Dot(x_current, y_current));
        }

    }
    public void run() {

        long lastTick = System.currentTimeMillis(); while(true)
        {
            // calculate elapsed time
            long currentTick = System.currentTimeMillis(); double diffSeconds = (currentTick-lastTick)/1000.0; lastTick = currentTick;
            graphicSystem.clear();

            moveDots(dots, diffSeconds);
            drawDots(dots);

            graphicSystem.redraw();
        }

    }
    public void setGraphicSystem(GraphicSystem p) { graphicSystem = p; }

    private void drawDots(List<Dot> dots) {
        for (Dot d : dots) {
            graphicSystem.draw(d);
        }
    }

    private void moveDots(List<Dot> dots, double diffSeconds) {
        for (Dot d : dots) {
            d.move(diffSeconds);
        }
    }

}