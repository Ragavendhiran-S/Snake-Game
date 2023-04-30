import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.IOException;

public class GameFrame extends JFrame {

    GameFrame() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        this.add(new GamePanel());
        this.setTitle("Disco Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
