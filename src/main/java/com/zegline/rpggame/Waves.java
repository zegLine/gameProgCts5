package main.java.com.zegline.rpggame;

import main.java.com.zegline.rpggame.GameEntity.Enemies.BasicEnemy;
import main.java.com.zegline.rpggame.GameEntity.Enemies.EnemyFactory;

import static main.java.com.zegline.rpggame.SoundEngine.FIGHT_SONG_QUEUE;
import static main.java.com.zegline.rpggame.SoundEngine.MAIN_SONG_QUEUE;

public class Waves {

    public static final int TOTAL_TIME_ROUND = 120 * 30;
    public static void startWave(){
        ChatGame.waveOngoing = true;
        ChatGame.currentWave++;
        new EnemyFactory(ChatGame.currentWave,false, 10 + ChatGame.currentWave * 2);
        SoundEngine.addToQueue("musical/test2.wav", FIGHT_SONG_QUEUE);
        SoundEngine.play(SoundEngine.FIGHT_SONG_QUEUE);
        ChatGame.max.health = 100;

    }

    public static void finishWave(){
        ChatGame.waveOngoing = false;
        SoundEngine.addToQueue("musical/test.wav", MAIN_SONG_QUEUE);
        SoundEngine.play(SoundEngine.MAIN_SONG_QUEUE);
    }

}
