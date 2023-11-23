public class A_Main {

    private Gam02_World world = null;
    public A_Main()
    {
        Gam02_Frame frame = new Gam02_Frame();
        frame.setVisible(true);
        world = new Gam02_World();
        world.setGraphicSystem(frame.getPanel());
        world.setInputSystem(frame.getPanel());
        world.init();
        world.run();
    }
    public static void main(String[] args)
    { new A_Main();
    }

}
