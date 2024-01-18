package main.java.com.zegline.rpggame.GameEntity.Enemies;

import main.java.com.zegline.rpggame.ChatGame;
import main.java.com.zegline.rpggame.Waves;

import java.util.Random;

public class EnemyFactory {

    public Random random;

    int enemyLevel;

    int boundX, boundY;

    boolean hardmode;

    int counter = 0;

    int timeBetweenSpawn;

    public int enemiesRemaining;

    public EnemyFactory(int enemyLevel, boolean hardmode, int NumEnemiesToSpawn) {
        boundX = ChatGame.screenWidth;
        boundY = ChatGame.screenHeight;
        this.enemyLevel = enemyLevel;
        this.hardmode = hardmode;
        this.enemiesRemaining = NumEnemiesToSpawn;
        counter = 0;

        random = new Random(enemyLevel);

        ChatGame.enemyFactory = this;

        timeBetweenSpawn =   Waves.TOTAL_TIME_ROUND/NumEnemiesToSpawn;
    }

    public void spawnEnemies() {
        if(enemiesRemaining == 0) {
            return;
        }
        counter++;



        if(timeBetweenSpawn - counter < 0) {
            enemiesRemaining--;
            int rX,rY ;
            rX = random.nextInt(boundX+50,boundX+500);
            rX *= random.nextInt(10) > 5 ? -1 : 1;

            rY = random.nextInt(boundY+50,boundY+500);
            rY *= random.nextInt(10) > 5 ? -1 : 1;
            counter = 0;
            int n = random.nextInt() % enemyLevel % 3;
            switch(n){
                case 0, 1:
                    new BasicEnemy(rX,rY,enemyLevel + random.nextInt() % 3);
                    break;
                case 2:
                    new ShootingEnemy(rX,rY, enemyLevel+ random.nextInt() % 3);
                    break;

            }
        }

    }
}
