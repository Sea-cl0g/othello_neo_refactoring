package game;

import ddf.minim.AudioPlayer;
import ddf.minim.Controller;
import ddf.minim.Minim;
import java.util.EnumMap;
import java.util.Map;
import processing.core.PApplet;

public class SoundManager {
    public enum Sound {
        CLICK("sounds/決定ボタンを押す12.mp3"),
        TURN("sounds/カーソル移動9.mp3"),
        GAME_END("sounds/カーソル移動11.mp3"),
        AWARD("sounds/決定ボタンを押す24.mp3");

        private final String path;

        Sound(String path) {
            this.path = path;
        }
    }

    private final Map<Sound, AudioPlayer> players = new EnumMap<>(Sound.class);
    private int volume;

    public SoundManager(PApplet app, int volume) {
        Minim minim = new Minim(app);
        for (Sound sound : Sound.values()) {
            AudioPlayer player = minim.loadFile(sound.path);
            if (player == null) {
                minim.stop();
                throw new IllegalStateException("Unable to load sound: " + sound.path);
            }
            if (!player.hasControl(Controller.GAIN)) {
                minim.stop();
                throw new IllegalStateException("Audio device does not support gain: " + sound.path);
            }
            players.put(sound, player);
        }
        setVolume(volume);
    }

    public void setVolume(int volume) {
        if (volume < 0 || volume > 100) {
            throw new IllegalArgumentException("Volume must be 0..100");
        }
        this.volume = volume;
        for (AudioPlayer player : players.values()) {
            float gain = PApplet.map(volume, 0, 100, -80, 10);
            player.setGain(Math.max(player.gain().getMinimum(), Math.min(player.gain().getMaximum(), gain)));
            if (volume == 0) {
                player.pause();
            }
        }
    }

    public void play(Sound sound) {
        if (volume == 0) {
            return;
        }
        AudioPlayer player = players.get(sound);
        player.rewind();
        player.play();
    }
}
