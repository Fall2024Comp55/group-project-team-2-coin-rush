import 
java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GRect;
import acm.graphics.GObject;

public class Level0Pane extends GraphicsPane{
	private MainApplication mainScreen;
    private Player player;
    private Platform platform;
    private Enemy enemy, enemy1;
    private testCoin coin;
    private Door door;
    private UI_Elements UI;
    
    private boolean gridVisible = true;
    private ArrayList<GLine> gridLines = new ArrayList<>();
    private ArrayList<GLabel> gridLabels = new ArrayList<>();
    public static final int GRID_SIZE = 40;

    private GImage background;
    private GRect box;
    private Timer timer;
    private GCompound pauseButtonCompound;
	private hitBox playerHitbox;
    
    
    public Level0Pane(MainApplication mainScreen) {
        this.mainScreen = mainScreen;
    }
    
    public void showContent() {
    	
    	 
    	
    	if (gridVisible) drawGrid(GRID_SIZE);
    	
    	background = new GImage("Media/Background1.png");
        mainScreen.add(background);
        background.setSize(mainScreen.getWidth(), mainScreen.getHeight());
        background.sendToBack();
        contents.add(background);
        
        platform = new Platform();
        platform.setProgram(mainScreen);

        platform.addPlatform(100, 400, 100, 30, Platform.PlatformTypes.STATIC, 0, 0, false);
        platform.addPlatform(200, 500, 100, 30, Platform.PlatformTypes.STATIC, 0, 0, false);
        platform.addPlatform(400, 600, 100, 30, Platform.PlatformTypes.STATIC, 0, 0, false);
        platform.addPlatform(600, 600, 100, 30, Platform.PlatformTypes.STATIC, 0, 0, false);
        platform.addPlatform(800, 500, 100, 30, Platform.PlatformTypes.STATIC, 0, 0, false);
        platform.addPlatform(1000, 500, 100, 30, Platform.PlatformTypes.STATIC, 0, 0, false);

        platform.addPlatformsToScreen();
        
       
        
        coin = new testCoin(5);
        coin.setProgram(mainScreen);
        coin.spawnCoinsToPlatforms(coin.getCoinsOnPlatforms(), platform.getPlatforms(), true);
        coin.init();
        
        door = new Door(3, 1025, 415);
        door.setProgram(mainScreen);
        door.init();
        
        player = new Player(100, 300);
        player.setProgram(mainScreen);
        player.spawn(100, 300);
        
        playerHitbox = new hitBox();
     	playerHitbox.createHitbox(player.getX(), player.getY(), player.getBounds().getWidth(), player.getBounds().getHeight(), 20, 3);
    	//add(playerHitbox.getHitbox()); // Add hitbox to canvas for debugging purposes
    	   
        UI = new UI_Elements();
        UI.setProgram(mainScreen);
        UI.createUI(coin, player);
        
        enemy = new Enemy();
        enemy.setProgram(mainScreen);
        enemy.spawnEnemy(platform.getPlatforms().get(0));
        enemy1 = new Enemy();
        enemy1.setProgram(mainScreen);
        enemy1.spawnEnemy(platform.getPlatforms().get(2));
        
        
        
        box = new GRect(1, 1, player.getBounds().getWidth(), player.getBounds().getHeight());
        box.setColor(Color.BLACK);
        mainScreen.add(box);
        contents.add(box);
        
        
        
        timer = new Timer(16, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Update game elements
                player.update();
                coin.update(playerHitbox);
                platform.collision(player.getBounds());
                //handlePlatformInteraction();
                enemy.update(platform.getPlatforms().get(0), playerHitbox, player);
                enemy1.update(platform.getPlatforms().get(2), playerHitbox, player);
                door.checkIfplayerCanExit(coin.getCoinsCollected());
                UI.init(door, coin, player);
                // Move debug box
                box.setLocation(player.getX(), player.getY());
            }
        });
        timer.start();
        
        addPauseButton();
        mainScreen.addKeyListeners();
        mainScreen.addMouseListeners();
    }
    
    public void hideContent() {
    	if (timer != null) timer.stop();
    	clearGrid();
    	
    	for (GObject obj : contents) {
    		mainScreen.remove(obj);
    	}
    	contents.clear();
    	mainScreen.clear();
    }
    
    private void addPauseButton() {
    	GImage pauseButton = new GImage("CGB02-yellow_M_btn.png");
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
        
        contents.add(pauseButtonCompound);
        mainScreen.add(pauseButtonCompound);
    }
    
    public void startTimer() {
     	timer.start();
     }
    
    public void mouseClicked(MouseEvent e) {
    	GObject clicked = mainScreen.getElementAtLocation(e.getX(), e.getY());
        if (clicked == pauseButtonCompound) {
            timer.stop();
            mainScreen.switchToPauseScreen();
        }
    }
    
    public void keyPressed(KeyEvent e)  {
    	player.keyPressed(e);
    	
    	if (e.getKeyCode() == KeyEvent.VK_G) {
            gridVisible = !gridVisible;
            if (gridVisible) drawGrid(GRID_SIZE);
            else clearGrid();
        }
    }
    
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }
    
    public void keyTyped(KeyEvent e) {
        player.keyTyped(e);
    }
    
    private void drawGrid(int cellSize) {
        for (int x = 0; x <= MainApplication.WINDOW_WIDTH; x += cellSize) {
            for (int y = 0; y <= MainApplication.WINDOW_HEIGHT; y += cellSize) {
                GLine v = new GLine(x, 0, x, MainApplication.WINDOW_HEIGHT);
                GLine h = new GLine(0, y, MainApplication.WINDOW_WIDTH, y);
                v.setColor(Color.LIGHT_GRAY);
                h.setColor(Color.LIGHT_GRAY);
                mainScreen.add(v);
                mainScreen.add(h);
                gridLines.add(v);
                gridLines.add(h);
                GLabel lbl = new GLabel("(" + x + "," + y + ")", x + 2, y + 10);
                lbl.setFont("Courier-8");
                lbl.setColor(Color.GRAY);
                mainScreen.add(lbl);
                gridLabels.add(lbl);
            }
        }
    }
    
    private void clearGrid() {
        for (GLine line : gridLines) mainScreen.remove(line);
        gridLines.clear();
        for (GLabel lbl : gridLabels) mainScreen.remove(lbl);
        gridLabels.clear();
    }
//    
    private void handlePlatformInteraction() {
        GRect touched = platform.detectPlatformCollision(playerHitbox);
        player.setGrounded(false);
        if (touched != null) {
            if (player.getY() + player.getBounds().getHeight() <= touched.getY() + 15) {
                player.setGrounded(true);
                player.setyVelocity(0);
                player.setY(touched.getY() - player.getBounds().getHeight());
            } else if (player.getY() + player.getBounds().getHeight() > touched.getY()
                    && player.getY() < touched.getY() + touched.getHeight()) {
                player.setY(touched.getY() + touched.getBounds().getHeight() - 5);
                player.setyVelocity(0);
                player.setGrounded(false);
            }
        }
    }

}
