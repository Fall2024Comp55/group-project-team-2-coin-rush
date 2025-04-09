import java.awt.event.KeyEvent;
import acm.program.GraphicsProgram;

public class Level_0_tests extends GraphicsProgram {

    private Player player;
    private Platform platform;
    private Enemy enemy;
    private testCoin coin;

    public void run() {
        setSize(800, 600); // Window size
        addKeyListeners();

        // Initialize player (if separated from Platform logic)
        player = new Player();
        player.setProgram(this);
        player.spawn(100, 300);

        while (true) {
            player.update();
            pause(16.66); // 60 FPS
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        player.keyTyped(e);
    }

    public static void main(String[] args) {
        new Level_0_tests().start();
    }
}
