import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GraphicSystem extends JPanel {

    private GraphicsConfiguration graphicsConf =
            GraphicsEnvironment.getLocalGraphicsEnvironment().
                    getDefaultScreenDevice().getDefaultConfiguration();
    private BufferedImage imageBuffer;
    private Graphics graphics;
    private static final Color COLOR_DOT = new Color(96, 96, 255);
    private static final Color COLOR_BLACK = new Color(0, 0, 0);

    private static final int RADIUS_DOT = 20;

    public GraphicSystem() {
        this.setSize(1000, 800);

        imageBuffer = graphicsConf.createCompatibleImage(this.getWidth(), this.getHeight());
        graphics = imageBuffer.getGraphics();
    }

    public void clear() {
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(0, 0, 1000, 800);
    }

    public void draw(Dot dot) {
        graphics.setColor(COLOR_DOT);
        graphics.fillOval((int) dot.x - RADIUS_DOT, (int) dot.y - RADIUS_DOT,
                RADIUS_DOT * 2, RADIUS_DOT * 2);
        graphics.setColor(COLOR_BLACK);
        graphics.drawOval((int) dot.x - RADIUS_DOT, (int) dot.y - RADIUS_DOT,
                RADIUS_DOT * 2, RADIUS_DOT * 2);
    }

    public void redraw() {
        this.getGraphics().drawImage(imageBuffer, 0, 0, this);
    }
}