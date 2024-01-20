package main.java.com.zegline.rpggame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class SoundEngine {
    private static Map<String, Queue<String>> soundQueues = new HashMap<>();
    private static Thread playbackThread = null;
    private static Clip currentClip = null;
    private static String currentQueue = null;

    public static final String MAIN_SONG_QUEUE = "mainSongs";
    public static final String FIGHT_SONG_QUEUE = "fightSongs";

    static {
        soundQueues.put(MAIN_SONG_QUEUE, new LinkedList<>());
        soundQueues.put(FIGHT_SONG_QUEUE, new LinkedList<>());
    }

    public static synchronized void addToQueue(final String url, String queueName) {
        if (!soundQueues.containsKey(queueName)) {
            System.err.println("Invalid queue name: " + queueName);
            return;
        }

        soundQueues.get(queueName).offer(url);

        if (playbackThread == null || !playbackThread.isAlive()) {
            //startPlayback(queueName);
        }
    }

    public static synchronized void play(String queueName) {
        if (!soundQueues.containsKey(queueName)) {
            System.err.println("Invalid queue name: " + queueName);
            return;
        }

        currentQueue = queueName;
        Queue<String> soundQueue = soundQueues.get(queueName);
        if (currentClip != null && currentClip.isActive()) {
            currentClip.close();
        }


        if (!soundQueue.isEmpty()) {
            startPlayback(queueName);
        }

    }

    public static synchronized void pause() {
        if (currentClip != null && currentClip.isActive()) {
            currentClip.stop();
        }
    }

    public static synchronized void resume() {
        if (currentClip != null && !currentClip.isActive() && currentQueue != null) {
            currentClip.start();
        }
    }

    public static synchronized void playOneShotEffect(String url) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            World.class.getResourceAsStream("/sound/" + url));
                    clip.open(inputStream);
                    clip.start();
                    clip.addLineListener(event -> {
                        if (event.getType() == LineEvent.Type.STOP) {
                            clip.close();
                        }
                    });
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }

    private static synchronized void startPlayback(String queueName) {
        Queue<String> soundQueue = soundQueues.get(queueName);

        if (soundQueue.isEmpty()) {
            return;
        }


        playbackThread = new Thread(new Runnable() {
            public void run() {
                try {
                    String url = soundQueue.poll();
                    currentClip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            World.class.getResourceAsStream("/sound/" + url));
                    currentClip.open(inputStream);
                    currentClip.start();
                    currentClip.addLineListener(event -> {
                        if (event.getType() == LineEvent.Type.STOP &&
                                event.getFramePosition() == currentClip.getFrameLength()) {
                            currentClip.close();
                            currentClip = null;

                            // Add the played song back to the end of the queue
                            soundQueue.offer(url);
                            startPlayback(queueName);
                        }
                    });
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        });

        playbackThread.start();
    }
}
