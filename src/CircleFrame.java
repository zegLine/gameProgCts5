import javax.swing.*;

public class CircleFrame extends JFrame
{
    private GraphicSystem panel = null;
    public CircleFrame()
    { this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(Resources.FRAME_WIDTH,Resources.FRAME_HEIGHT);
        panel = new GraphicSystem();
        this.setContentPane(panel); }
    public GraphicSystem getPanel() {return panel;} }