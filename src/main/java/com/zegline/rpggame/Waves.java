package main.java.com.zegline.rpggame;

import main.java.com.zegline.rpggame.GameEntity.Enemies.BasicEnemy;
import main.java.com.zegline.rpggame.GameEntity.Enemies.EnemyFactory;

public class Waves {

    public static final int TOTAL_TIME_ROUND = 120 * 30;
    public static void startWave(){
        ChatGame.waveOngoing = true;
        ChatGame.currentWave++;
        new EnemyFactory(ChatGame.currentWave,false, 10 + ChatGame.currentWave * 2);
        SoundEngine.play(SoundEngine.FIGHT_SONG_QUEUE);

    }

    public static void finishWave(){
        ChatGame.waveOngoing = false;
        SoundEngine.play(SoundEngine.MAIN_SONG_QUEUE);
    }

}
