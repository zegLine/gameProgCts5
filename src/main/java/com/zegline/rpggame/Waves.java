package main.java.com.zegline.rpggame;

import main.java.com.zegline.rpggame.GameEntity.Enemies.BasicEnemy;
import main.java.com.zegline.rpggame.GameEntity.Enemies.EnemyFactory;

public class Waves {

    public static final int TOTAL_TIME_ROUND = 120 * 30;
    public static void startWave(){
        ChatGame.waveOngoing = true;
        ChatGame.currentWave++;
        new EnemyFactory(ChatGame.currentWave,false, 10 + ChatGame.currentWave * 2);


    }

    public static void finishWave(){
        ChatGame.waveOngoing = false;
    }

}
