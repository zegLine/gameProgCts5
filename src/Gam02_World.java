public class Gam02_World {

    private A_GraphicSystem graphicSystem;
    private A_InputSystem inputSystem;
    private A_UserInput userInput;
    private Gam02_Avatar userDot;
    public void init()
    { userDot = new Gam02_Avatar(500,400);
    }
    public void run()
    {
        long lastTick = System.currentTimeMillis();

        while (true)
        {
            // calculate elapsed time
            long currentTick = System.currentTimeMillis();
            double diffSeconds = (currentTick-lastTick)/1000.0;
            lastTick = currentTick;

            // parse User Input
            userInput = inputSystem.getUserInput();
            if(userInput != null)
            {
                inputSystem.command(userDot, userInput);
            }

            userDot.move(diffSeconds);
            graphicSystem.clear();
            graphicSystem.draw(userDot);
            graphicSystem.redraw();
        }
    }
    public void setGraphicSystem(A_GraphicSystem p) { graphicSystem = p; }
    public void setInputSystem(A_InputSystem p) { inputSystem = p; }

}
