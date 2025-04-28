import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class Level_0_tests extends GraphicsProgram {
    private Player player;
    private Platform platform;
    private Enemy enemy;
    private testCoin coin;
    private Door door;
    private hitBox playerHitbox;
    private UI_Elements UI;
    private LevelHandler levelHandler;

    private boolean gridVisible = true;
    private ArrayList<GLine> gridLines = new ArrayList<>();
    private ArrayList<GLabel> gridLabels = new ArrayList<>();
    public static final int GRID_SIZE = 40;
    private GImage background;
    private boolean isGameOver = false;
    private Menu deathMenu;

    public void run() {
        setSize(1280, 720);

        if (gridVisible) {
            drawGrid(GRID_SIZE);
        }
        
        addKeyListeners();
        setUpLevel();

        while (true) {
            updateLevel();
            pause(16.66);
        }
    }

    private void setUpLevel() {
        background = new GImage("Media/Background1.png");
        add(background);
        background.setSize(getWidth(), getHeight());
        background.sendToBack();

        platform = new Platform();
        platform.setProgram(this);
        

        platform.addPlatform(100, 400, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);        
        platform.addPlatform(200, 500, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);
        platform.addPlatform(400, 600, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);
        platform.addPlatform(600, 600, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);
        platform.addPlatform(800, 500, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);
        platform.addPlatform(1000, 500, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);

        platform.addPlatformsToScreen();
        
        
        //test coins
        coin = new testCoin(5);
        coin.setProgram(this);
        coin.spawnCoinsToPlatforms(coin.getCoinsOnPlatforms(), platform.getPlatforms(), true);
        coin.init();
  
        door = new Door(3,1025, 415);
        door.setProgram(this);
        door.init();

        player = new Player(100, 300);
        player.setProgram(this);
        player.spawn(100, 300);

        playerHitbox = new hitBox();
        playerHitbox.createHitbox(player.getX(), player.getY(), player.getBounds().getWidth(), player.getBounds().getHeight(), 20, 3);
        add(playerHitbox.getHitbox());

        enemy = new Enemy();
        enemy.setProgram(this);
        enemy.spawnEnemy(platform.getPlatforms().get(0));

        UI = new UI_Elements();
        UI.setProgram(this);
        UI.createUI(coin, player);

        deathMenu = new Menu();
        deathMenu.setProgram(this);

        ArrayList<Enemy> enemies = new ArrayList<>();
        enemies.add(enemy);

        levelHandler = new LevelHandler(player, platform, enemies, coin, door, playerHitbox, UI); // Hooked up
    }

    private void updateLevel() {
        if (!isGameOver) {
            levelHandler.updateLevel();

            if (player.getHP() <= 0) {
                GameOverScreen();
            }
        }
    }

    private void drawGrid(int cellSize) {
        for (int x = 0; x <= 1280; x += cellSize) {
            for (int y = 0; y <= 720; y += cellSize) {
                GLine vertical = new GLine(x, 0, x, 720);
                GLine horizontal = new GLine(0, y, 1280, y);
                vertical.setColor(Color.LIGHT_GRAY);
                horizontal.setColor(Color.LIGHT_GRAY);
                add(vertical);
                add(horizontal);
                gridLines.add(vertical);
                gridLines.add(horizontal);

                String coords = "(" + x + "," + y + ")";
                GLabel label = new GLabel(coords, x + 2, y + 10);
                label.setFont("Courier-8");
                label.setColor(Color.GRAY);
                add(label);
                gridLabels.add(label);
            }
        }
    }

    private void clearGrid() {
        for (GLine line : gridLines) {
            remove(line);
        }
        gridLines.clear();

        for (GLabel label : gridLabels) {
            remove(label);
        }
        gridLabels.clear();
    }

    private void GameOverScreen() {
        if (isGameOver) {
            return;
        } else {
            isGameOver = true;
            deathMenu.showContents();
        }
    }

    private void restartLevel() {
        removeAll();
        isGameOver = false;
        setUpLevel();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
        if (e.getKeyCode() == KeyEvent.VK_G) {
            gridVisible = !gridVisible;
            if (gridVisible) {
                drawGrid(GRID_SIZE);
            } else {
                clearGrid();
            }
        }
        if (isGameOver && e.getKeyCode() == KeyEvent.VK_R) {
            restartLevel();
        }
        if (isGameOver && e.getKeyCode() == KeyEvent.VK_E) {
            System.exit(0);
        }
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
