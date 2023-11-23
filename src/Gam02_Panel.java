import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class Gam02_Panel extends JPanel implements A_GraphicSystem, A_InputSystem, MouseListener {

    private static final long serialVersionUID = 1L;

    // User Input variables
    private boolean newInput = false;
    private int mousePressedX, mousePressedY, mouseMovedX, mouseMovedY, mouseButton;
    private char keyPressed;


    // GraphicSys variables
    private GraphicsConfiguration graphicsConf = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
    private BufferedImage imageBuffer;
    private Graphics graphics;


    private static final Color COLOR_DOT = new Color(96,96,255);
    private static final int RADIUS_DOT = 20;

    public Gam02_Panel()
    {
        this.setSize(Resources.FRAME_WIDTH, Resources.FRAME_HEIGHT);
        imageBuffer = graphicsConf.createCompatibleImage(this.getWidth(), this.getHeight());
        graphics = imageBuffer.getGraphics();

        this.addMouseListener(this);
    }

    @Override
    public void clear() {
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(0, 0, Resources.FRAME_WIDTH, Resources.FRAME_HEIGHT);
    }

    @Override
    public void draw(A_GameObject dot) {
        int x = (int)dot.x-RADIUS_DOT;
        int y = (int)dot.y-RADIUS_DOT;
        int r = RADIUS_DOT*2;

        graphics.setColor(COLOR_DOT);
        graphics.fillOval(x, y, r, r);
        graphics.setColor(Color.BLACK);
        graphics.drawOval(x,y,r,r);
    }

    @Override
    public void redraw() {
        this.getGraphics().drawImage(imageBuffer, 0, 0, this);
    }

    @Override
    public A_UserInput getUserInput() {
        if(!newInput) return null;
        newInput = false;
        return new A_UserInput(mousePressedX, mousePressedY,
                mouseMovedX, mouseMovedY, mouseButton,
                keyPressed);
    }

    @Override
    public void command(A_GameObject av, A_UserInput input) {
        Gam02_Avatar avatar = (Gam02_Avatar)av;
        avatar.setDestination(input.mousePressedX,input.mousePressedY);
    }

    @Override
    public void mousePressed(MouseEvent evt) {
        // an input Event occurs
        newInput = true;
        mousePressedX = evt.getX();
        mousePressedY = evt.getY();
        mouseButton = evt.getButton();
    }

    @Override
    public void mouseClicked(MouseEvent evt) {

    }

    @Override
    public void mouseReleased(MouseEvent evt) {

    }

    @Override
    public void mouseEntered(MouseEvent evt) {

    }

    @Override
    public void mouseExited(MouseEvent evt) {

    }
}
