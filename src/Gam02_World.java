public class Gam02_World extends A_World {

    @Override
    protected void init() {
        gameObjects.add(new Avatar(400,500));
        for(int i=0; i<20; i++)
        {
            double x = 1000*Math.random();
            double y = 800*Math.random();
            double alfa = Math.PI*2*Math.random();
            double speed = 100+100*Math.random();
            Dot dot = new Dot(x,y,alfa,speed);
            gameObjects.add(dot);
        }
    }

    @Override
    protected void processUserInput(A_UserInput input) {

    }


}
