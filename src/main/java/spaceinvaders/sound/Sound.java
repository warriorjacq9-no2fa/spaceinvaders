package spaceinvaders.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class Sound {

    public static final File audioFile

    static {
        try {
            audioFile = new File(Sound.class.getResource("expl").toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static void playSound(String filePath) {
        try {
            // 1. Locate the audio file
            File audioFile = new File(filePath);

            // 2. Convert the file into an audio input stream
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            // 3. Get a sound clip resource
            Clip clip = AudioSystem.getClip();

            // 4. Open the audio clip and load samples from the stream
            clip.open(audioStream);

            // 5. Start playing the audio
            clip.start();

            // CRITICAL: Prevent the program from closing immediately.
            // The clip plays on a separate background (daemon) thread.
            while (clip.isRunning()) {
                Thread.sleep(10);
            }

        } catch (Exception e) {
            System.err.println("Error playing sound: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Replace with your absolute or relative path to the .wav file
        playSound("path/to/your/audio.wav");
    }
}
