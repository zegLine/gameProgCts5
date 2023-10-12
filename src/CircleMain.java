public class CircleMain
{
    private CircleWorld world = null;
    public CircleMain()
    {
        CircleFrame frame = new CircleFrame(); frame.setVisible(true);
        world = new CircleWorld(); world.setGraphicSystem(frame.getPanel()); world.init();
        world.run();
    }
    public static void main(String[] args) { new CircleMain();
    }
}