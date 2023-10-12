import javax.swing.*;

public class CircleFrame extends JFrame
{
    private GraphicSystem panel = null;
    public CircleFrame()
    { this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1000,800);
        panel = new GraphicSystem();
        this.setContentPane(panel); }
    public GraphicSystem getPanel() {return panel;} }