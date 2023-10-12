public class CircleWorld
{
    private GraphicSystem graphicSystem;
    private Dot dot;
    public void init()
    {
        dot = new Dot(100,400);
    }
    public void run() {

        long lastTick = System.currentTimeMillis(); while(true)
        {
            // calculate elapsed time
            long currentTick = System.currentTimeMillis(); double diffSeconds = (currentTick-lastTick)/1000.0; lastTick = currentTick;
            dot.move(diffSeconds);
            graphicSystem.clear();
            graphicSystem.draw(dot);
            graphicSystem.redraw();
        }

    }
    public void setGraphicSystem(GraphicSystem p) { graphicSystem = p; }

}