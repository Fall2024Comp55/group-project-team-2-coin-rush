import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

import acm.graphics.*;

public class Level1Pane extends GraphicsPane {
    private MainApplication program;
    private Player player;
    private Platform platform;
    private ArrayList<Enemy> enemies;
    private testCoin coin;
    private Door door;
    private UI_Elements UI;

 

    private GImage background;
    private Timer timer;
    private Timer countdownTimer;
    private int timeLeft;
    private hitBox playerHitbox;
    private GCompound pauseButtonCompound;

    // Death screen
    private boolean isDead = false;
    private GCompound deathMenuCompound;
    private GCompound restartButtonCompound;
    private GCompound exitButtonCompound;

    public Level1Pane(MainApplication app) {
        this.program = app;
    }

    @Override
    public void showContent() {
      

        background = new GImage("Media/Background2.png");
        program.add(background);
        background.setSize(MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
        background.sendToBack();

        platform = new Platform();
        platform.setProgram(program);

        platform.addPlatform(0, 600, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false); //player spawn
        platform.addPlatform(200, 500, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);
        platform.addPlatform(400, 400, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);
        platform.addPlatform(400, 600, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);
        platform.addPlatform(600, 600, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);
        platform.addPlatform(600, 310, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);
        platform.addPlatform(820, 500, 100, 30, Platform.PlatformTypes.MOVING, 1, 160,false);
        platform.addPlatform(820, 250, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);
        platform.addPlatform(1100, 500, 100, 30, Platform.PlatformTypes.MOVING, 1, -300,true);
        platform.addPlatform(800, 500, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);

        platform.addPlatform(1000, 200, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false); //has door
        platform.addPlatformsToScreen();

        coin = new testCoin(7);
        coin.setProgram(program);
        coin.spawnCoinsToPlatforms(coin.getCoinsOnPlatforms(), platform.getPlatforms(), true);
        coin.init();

        door = new Door(4, 1040, 115);
        door.setProgram(program);
        door.init();

        player = new Player(0, 560);
        player.setProgram(program);
        player.spawn(0, 560);
        
        playerHitbox = new hitBox();
        playerHitbox.createHitbox(player.getX(), player.getY(), player.getBounds().getWidth(), player.getBounds().getHeight(), 20, 3);
        program.add(playerHitbox.getHitbox());

        enemies = new ArrayList<>();
        Enemy enemy1 = new Enemy();
        enemy1.setProgram(program);
        enemy1.spawnEnemy(platform.getPlatforms().get(2));
        enemies.add(enemy1);

        UI = new UI_Elements();
        UI.setProgram(program);
        UI.createUI(coin, player);

        setupCountdownTimer(45); // 45 seconds Level 1

        timer = new Timer(16, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                player.update();
                playerHitbox.updateHitbox(player.getX(), player.getY(), 20, 3);

                if (!isDead && player.getHP() <= 0) {
                    timer.stop();
                    countdownTimer.stop();
                    showDeathScreen();
                    isDead = true;
                    return;
                }

                platform.handlePlatformInteraction(player, playerHitbox);
                platform.updatePlatforms();
                for (Enemy enemy : enemies) {
                    enemy.update(playerHitbox, player);
                }

                coin.update(playerHitbox);
                door.checkIfplayerCanExit(coin.getCoinsCollected());
                UI.init(door, coin, player);

                if (door.isOpen() && door.playerTouchingDoor(playerHitbox)) {
                    timer.stop();
                    countdownTimer.stop();
                    int levelScore = (timeLeft * 500) + (coin.getCoinsCollected() * 250);
                    program.addToTotalScore(levelScore);
                    program.switchToScoreboard(timeLeft, coin.getCoinsCollected(), 1); // Level 1
                }
            }
        });
        timer.start();

        addPauseButton();
        program.addKeyListeners();
        program.addMouseListeners();
    }

    private void setupCountdownTimer(int startTime) {
        timeLeft = startTime;
        UI.createTimer(timeLeft);

        countdownTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                UI.updateTimer(timeLeft);
                if (timeLeft <= 0) {
                    countdownTimer.stop();
                    timer.stop();
                    GLabel gameOver = new GLabel("Time's Up!", 500, 300);
                    gameOver.setFont("SansSerif-bold-32");
                    gameOver.setColor(Color.RED);
                    program.add(gameOver);
                }
            }
        });
        countdownTimer.start();
    }

    private void addPauseButton() {
        GImage pauseButton = new GImage("CGB02-yellow_L_btn.png");
        pauseButton.scale(0.3, 0.3);

        GLabel buttonLabel = new GLabel("PAUSE");
        buttonLabel.setFont("SansSerif-bold-18");
        buttonLabel.setColor(Color.WHITE);

        pauseButtonCompound = new GCompound();
        pauseButtonCompound.add(pauseButton, 0, 0);

        double labelX = (pauseButton.getWidth() - buttonLabel.getWidth()) / 2;
        double labelY = (pauseButton.getHeight() + buttonLabel.getAscent()) / 2;
        pauseButtonCompound.add(buttonLabel, labelX, labelY);

        pauseButtonCompound.setLocation(1120, 0);
        program.add(pauseButtonCompound);
    }

    private void showDeathScreen() {
        deathMenuCompound = new GCompound();

        GImage deathMenu = new GImage("Media/death menu.png");
        deathMenu.scale(0.5); // (slightly better size)
        deathMenuCompound.add(deathMenu, 0, 0);

        // Add "You Died" text manually
        GLabel deathLabel = new GLabel("You Died");
        deathLabel.setFont("SansSerif-bold-30");
        deathLabel.setColor(Color.BLACK);
        deathMenuCompound.add(deathLabel, 90, 80); // adjust inside the menu

        // Restart Button
        GImage restartButton = new GImage("CGB02-yellow_L_btn.png");
        restartButton.scale(0.3, 0.3);
        GLabel restartLabel = new GLabel("RESTART");
        restartLabel.setFont("SansSerif-bold-18");
        restartLabel.setColor(Color.BLACK);

        restartButtonCompound = new GCompound();
        restartButtonCompound.add(restartButton, 0, 0);

        double rLabelX = (restartButton.getWidth() - restartLabel.getWidth()) / 2;
        double rLabelY = (restartButton.getHeight() + restartLabel.getAscent()) / 2;
        restartButtonCompound.add(restartLabel, rLabelX, rLabelY);

        restartButtonCompound.setLocation(100, 140); // Move a little lower
        deathMenuCompound.add(restartButtonCompound);

        // Exit Button
        GImage exitButton = new GImage("CGB02-yellow_L_btn.png");
        exitButton.scale(0.3, 0.3);
        GLabel exitLabel = new GLabel("EXIT");
        exitLabel.setFont("SansSerif-bold-18");
        exitLabel.setColor(Color.BLACK);

        exitButtonCompound = new GCompound();
        exitButtonCompound.add(exitButton, 0, 0);

        double eLabelX = (exitButton.getWidth() - exitLabel.getWidth()) / 2;
        double eLabelY = (exitButton.getHeight() + exitLabel.getAscent()) / 2;
        exitButtonCompound.add(exitLabel, eLabelX, eLabelY);

        exitButtonCompound.setLocation(100, 220); // space between buttons
        deathMenuCompound.add(exitButtonCompound);

        // Center death menu compound
        double centerX = (MainApplication.WINDOW_WIDTH - deathMenuCompound.getWidth()) / 2;
        double centerY = (MainApplication.WINDOW_HEIGHT - deathMenuCompound.getHeight()) / 2;
        deathMenuCompound.setLocation(centerX, centerY);

        program.add(deathMenuCompound);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (isDead) {
            double clickX = e.getX() - deathMenuCompound.getX();
            double clickY = e.getY() - deathMenuCompound.getY();
            GObject clicked = deathMenuCompound.getElementAt(clickX, clickY);

            if (clicked != null) {
                if (restartButtonCompound.getElementAt(clickX - restartButtonCompound.getX(), clickY - restartButtonCompound.getY()) != null) {
                    program.restartLevel(1); // Restart Level 1
                } else if (exitButtonCompound.getElementAt(clickX - exitButtonCompound.getX(), clickY - exitButtonCompound.getY()) != null) {
                    program.switchToWelcomeScreen();
                }
            }
            return;
        }

        GObject clicked = program.getElementAt(e.getX(), e.getY());
        if (clicked == pauseButtonCompound) {
            timer.stop();
            countdownTimer.stop();
            program.switchToPauseScreen();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (isDead) {
            if (e.getKeyCode() == KeyEvent.VK_R) {
                program.restartLevel(1);
            } else if (e.getKeyCode() == KeyEvent.VK_E) {
                program.switchToWelcomeScreen();
            }
            return;
        }
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



    public void startTimer() {
        if (timer != null) timer.start();
        if (countdownTimer != null) countdownTimer.start();
    }

    public void pauseGame() {
        if (timer != null) timer.stop();
        if (countdownTimer != null) countdownTimer.stop();
    }

    @Override
    public void hideContent() {
        if (timer != null) timer.stop();
        if (countdownTimer != null) countdownTimer.stop();
    
        program.clear();
    }
}
