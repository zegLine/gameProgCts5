import javax.swing.*;

public class Gam02_Frame extends JFrame {
    private static final long serialVersionUID = 2L;
    private Gam02_Panel panel = null;
    public Gam02_Frame()
    { this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(Resources.FRAME_WIDTH,Resources.FRAME_HEIGHT);
        panel = new Gam02_Panel();
        this.setContentPane(panel);
    }
    public Gam02_Panel getPanel() {return panel;}
}
