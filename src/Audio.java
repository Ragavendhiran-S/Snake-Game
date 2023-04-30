import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Audio {

    AudioInputStream audioStream;
    Clip clip;
    Audio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioStream = AudioSystem.getAudioInputStream(new File("C:\\Users\\sivak\\Downloads\\SnakeGameAudio.wav"));
        clip = AudioSystem.getClip();
        clip.open(audioStream);
    }
    public void start() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
