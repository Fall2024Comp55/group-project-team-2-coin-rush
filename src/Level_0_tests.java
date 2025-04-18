import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.program.GraphicsProgram;

public class Level_0_tests extends GraphicsProgram {

    private Player player;
    private Platform platform;
    
    private Enemy enemy;
    private Enemy enemy1;

    private testCoin coin;
    
    private Door door; 
    
    private boolean gridVisible = true; //used for key presse
    private ArrayList<GLine> gridLines = new ArrayList<>(); //stores the grid lines
    private ArrayList<GLabel> gridLabels = new ArrayList<>(); //stores the labels that visually show the size of each cell
    public static final int GRID_SIZE = 40; //size of grid cell

    
//draws the grid to screen
    	/*	Each grid cell is 40 pixels wide and 40 pixels tall (GRID_SIZE)
    	 *	The grid divides the 1280×720 window into a 32×18 grid (Columm'x'Row)
    	 */
    	private void drawGrid(int cellSize) {
    	    for (int x = 0; x <= 1280; x += cellSize) {
    	        for (int y = 0; y <= 720; y += cellSize) {
    	            //draws the grid lines
    	            GLine vertical = new GLine(x, 0, x, 720);
    	            GLine horizontal = new GLine(0, y, 1280, y);
    	            vertical.setColor(Color.LIGHT_GRAY);
    	            horizontal.setColor(Color.LIGHT_GRAY);
    	            add(vertical);
    	            add(horizontal);
    	            gridLines.add(vertical);
    	            gridLines.add(horizontal);

    	            //adds the coordinate label in top-left of each cell
    	            String coords = "(" + x + "," + y + ")";
    	            GLabel label = new GLabel(coords, x + 2, y + 10); //offset a bit inside the cell
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


    public void run() {
    	if (gridVisible) {
    	    drawGrid(GRID_SIZE);
    	}
        setSize(1280, 720); // Window size
        addKeyListeners();
        //platform = new Platform(120, 40,40, 120, Platform.PlatformTypes.STATIC, 0, 0);
        //platform = new Platform(120, 40,240, 240, Platform.PlatformTypes.STATIC, 0, 0);      
        platform = new Platform();
        platform.addPlatform(100, 400, 100, 10, Platform.PlatformTypes.STATIC, 0, 0);
        platform.setProgram(this);
        platform.addPlatformsToScreen();
        
        platform.addPlatform(200, 500, 100, 10, Platform.PlatformTypes.STATIC, 0, 0);
        platform.addPlatformsToScreen();
        platform.addPlatform(400, 600, 100, 10, Platform.PlatformTypes.STATIC, 0, 0);
        platform.addPlatformsToScreen();
        
        
        //test coins
        coin = new testCoin(5);
        coin.setProgram(this);
        coin.spawnCoinsToPlatforms(coin.getCoinsOnPlatforms(), platform.getPlatforms());
        coin.init();
  
        door = new Door(3,1000,620);
        door.setProgram(this);
        door.init();
        
        //player
        player = new Player();
        player.setProgram(this);
        player.spawn(100, 300); 
        
        
        //enemy 
        enemy = new Enemy();
        enemy.setProgram(this);
        
        enemy.spawnEnemy(platform.getPlatforms().get(0));
        enemy1 = new Enemy();
        enemy1.setProgram(this);
        enemy1.spawnEnemy(platform.getPlatforms().get(1));
        
        Enemy enemy2 = new Enemy();
        enemy2.setProgram(this);
        enemy2.spawnEnemy(platform.getPlatforms().get(2));
       
        
    

        while (true) {
            player.update(); //updates the Player animation loop & movement
            coin.update(player.getBounds()); //updates the collision to check if player is touching a coin
            platform.collision(player.getBounds());
            
            door.checkIfplayerCanExit(coin.getCoinsCollected());

            enemy.update(platform.getPlatforms().get(0), player.getBounds(),player.getImage());
            enemy1.update(platform.getPlatforms().get(1), player.getBounds(),player.getImage());
            enemy2.update(platform.getPlatforms().get(2), player.getBounds(),player.getImage());

            pause(16.66); // 60 FPS
           
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
        //I have it so that I can turn off the grid with 'g' key
        //If you plan to add an "edit mode", do so here.
        if (e.getKeyCode() == KeyEvent.VK_G) {
            gridVisible = !gridVisible;
            if (gridVisible) {
                drawGrid(GRID_SIZE);
            } else {
                clearGrid();
            }
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
