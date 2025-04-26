import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class Level_4  extends GraphicsProgram {
	private Player player;
    private Platform platform;
    private Enemy enemy;
    private Enemy enemy1;
    private Enemy enemy2;
    private Enemy enemy3;
    private testCoin coin;
    private Door door; 
    private GImage background; 
    private hitBox playerHitbox;
    UI_Elements UI;
    private Level_4 level;
    public boolean Grapple;
    
    private boolean gridVisible = true; //used for key presse
    private ArrayList<GLine> gridLines = new ArrayList<>(); //stores the grid lines
    private ArrayList<GLabel> gridLabels = new ArrayList<>(); //stores the labels that visually show the size of each cell
    public static final int GRID_SIZE = 40; //size of grid cell

    private boolean isGameOver = false;
    private GLabel gameOverLabel;
    private GLabel restartLabel;
    private GRect restartBox;
    
    private GRect box; //temp player hitbox
    

	public void run() {
    	if (gridVisible) {
    	    drawGrid(GRID_SIZE);
    	}
        setSize(1280, 720); // Window size
        addKeyListeners();
        setUpLevel();

 	 while (true) { 	   
 		 updateLevel();
 	}   
}
	
	 private void setUpLevel() {
	        //background
	        background = new GImage("Media/Background1.png");
	        add(background); // Add it before resizing, so getWidth() is correct
	        background.setSize(getWidth(), getHeight()); // Resizes it to fill the window
	        background.sendToBack(); // Ensures it's behind all other elements
	         
	        platform = new Platform();
	        
	        platform.addPlatform(40,  680, 80, 40, Platform.PlatformTypes.STATIC, 0, 0, false);//spawn platform
	        platform.addPlatform(40, 280, 80, 40, Platform.PlatformTypes.MOVING, 2, -120, true);
	        platform.addPlatform(120, 400, 80, 40, Platform.PlatformTypes.STATIC, 0, 0, false);
	        platform.addPlatform(200, 500, 80, 40, Platform.PlatformTypes.STATIC, 0, 0, false);
	        platform.addPlatform(200, 660, 80, 40, Platform.PlatformTypes.STATIC, 0, 0, false);
	        platform.addPlatform(400, 600, 80, 40, Platform.PlatformTypes.STATIC, 0, 0, false);
	        platform.addPlatform(480, 600, 80, 40, Platform.PlatformTypes.MOVING, .5, 120, false);
	        platform.addPlatform(840, 600, 80, 40, Platform.PlatformTypes.MOVING, .5, -120, false);
	        
	        platform.addPlatform(1040, 680, 120, 40, Platform.PlatformTypes.MOVING, 0, 0, false);
	        platform.addPlatform(160, 160, 80, 40, Platform.PlatformTypes.STATIC, 0, 0, false);
	        
	        platform.addPlatform(320, 160, 80, 40, Platform.PlatformTypes.MOVING, 1, -80, false);
	        platform.addPlatform(300,80, 80, 40, Platform.PlatformTypes.STATIC, 0, 0, false);
	        platform.addPlatform(40, 360, 80, 40, Platform.PlatformTypes.STATIC, 0, 0, false);
	        platform.addPlatform(1160, 680, 120, 40, Platform.PlatformTypes.MOVING, 0, 0, false);//door platform
	        
	        platform.setProgram(this);
	        platform.addPlatformsToScreen();
	        
	        
	        //test coins
	        coin = new testCoin(8);
	        coin.setProgram(this);
	        coin.spawnCoinsToPlatforms(coin.getCoinsOnPlatforms(), platform.getPlatforms(), true);
	        coin.init();
	  
	        door = new Door(1,1200,600);
	        door.setProgram(this);
	        door.init();
	        
	        //player
	        player = new Player(100,300);
	        player.setProgram(this);
	        player.spawn(40,  640);
	        
	        playerHitbox = new hitBox();
	     	playerHitbox.createHitbox(player.getX(), player.getY(), player.getBounds().getWidth(), player.getBounds().getHeight(), 20, 3);
	    	add(playerHitbox.getHitbox()); // Add hitbox to canvas for debugging purposes
	        
	        UI = new UI_Elements();
	        UI.setProgram(this);
	        UI.createUI(coin,player); 
	        
	        //enemy 
	        enemy = new Enemy();
	        enemy.setProgram(this);
	        enemy.spawnEnemy(platform.getPlatforms().get(2));
	        
	        enemy1 = new Enemy();
	        enemy1.setProgram(this);
	        enemy1.spawnEnemy(platform.getPlatforms().get(3));
	        
	        enemy2 = new Enemy();
	        enemy2.setProgram(this);
	        enemy2.spawnEnemy(platform.getPlatforms().get(5));
	        
	        enemy3 = new Enemy();
	        enemy3.setProgram(this);
	        enemy3.spawnEnemy(platform.getPlatforms().get(7));
	       
	    }
	    
	    private void updateLevel() {
	 	    pause(16.66); // 60 FPS

	    	if(!isGameOver) {
	     	    player.update(); // Updates the Player animation loop & movement
	     	    coin.update(playerHitbox); // Updated to use the new player hitbox
	     	    handlePlatformInteraction();
	     	    platform.updatePlatforms();
	     	    door.checkIfplayerCanExit(coin.getCoinsCollected());
	     	    //hitbox movement
	            playerHitbox.updateHitbox(player.getX(),player.getY() , 20, 3);	     	    
	            
	            enemy.update(platform.getPlatforms().get(2), playerHitbox, player); 
	     	    enemy1.update(platform.getPlatforms().get(3), playerHitbox, player); 
	     	    enemy2.update(platform.getPlatforms().get(5), playerHitbox, player); 
	     	    enemy3.update(platform.getPlatforms().get(7), playerHitbox, player); 
	     	    
	     	    UI.init(door, coin, player);
	      
	          //restart the level once hp reaches 0
	            if (player.getHP() <= 0) {
	                GameOverScreen();
	            }
	    	}
	    }
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

	private void add(GLine vertical) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void handlePlatformInteraction() {
	    // Detect collision with a platform
	    GRect touchedPlatform = platform.detectPlatformCollision(playerHitbox);

	    // Assume the player is not grounded by default
	    player.setGrounded(false);

	    if (touchedPlatform != null) {
	        double playerBottom = player.getY() + playerHitbox.getHeight();
	        double playerTop = player.getY();
	        double platformTop = touchedPlatform.getY();
	        double platformBottom = touchedPlatform.getY() + touchedPlatform.getHeight();

	        // Case 1: Player lands on the platform
	        if (playerBottom >= platformTop && playerBottom <= platformTop + 15 && player.getyVelocity() >= 0) {
	            player.setGrounded(true);
	            player.setyVelocity(0); // Stop downward motion
	            player.setY(platformTop - playerHitbox.getHeight()); // Place on top
	            System.out.println("on top");
	        }
	        // Case 2: Player hits the bottom of a platform
	        else if (playerTop <= platformBottom && playerTop >= platformBottom - 15 && player.getyVelocity() < 0) {
	            player.setyVelocity(0); // Reset upward velocity
	            System.out.println("bottom collision");
	        }
	        // Case 3: Catch the player when falling through platforms
	        else if (playerBottom > platformTop && playerBottom <= platformBottom && player.getyVelocity() > 0) {
	            player.setyVelocity(0); // Stop falling
	            player.setY(platformTop - playerHitbox.getHeight()); // Adjust position to the top
	            player.setGrounded(true); // Ground the player
	            System.out.println("caught mid-fall");
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


private void remove(GLine line) {
		// TODO Auto-generated method stub
		
	}
    
  	private void GameOverScreen() {
  	    if (isGameOver) {
  	    	return; //prevent duplicate triggers
  	    } else {
  	    	 isGameOver = true;
       	    
       	    //creates the game over label
       	  gameOverLabel = new GLabel("YOU DIED");
           gameOverLabel.setFont("SansSerif-BOLD-48");
           gameOverLabel.setColor(Color.BLACK);
           double labelX = (getWidth() - gameOverLabel.getWidth()) / 2;
           double labelY = getHeight() / 2 - 60; // a bit above center
           gameOverLabel.setLocation(labelX, labelY);
           add(gameOverLabel);

       	    //both create the restart label and button box
           int boxWidth = 160;
           int boxHeight = 50;
           int boxX = (int) ((getWidth() - boxWidth) / 2);
           int boxY = (int) (getHeight() / 2);
           restartBox = new GRect(boxX, boxY, boxWidth, boxHeight);
           restartBox.setFilled(true);
           restartBox.setFillColor(Color.LIGHT_GRAY);
           restartBox.setColor(Color.BLACK);
           add(restartBox);

           restartLabel = new GLabel("'R' to restart");
           restartLabel.setFont("SansSerif-BOLD-24");
           double restartX = boxX + (boxWidth - restartLabel.getWidth()) / 2;
           double restartY = boxY + (boxHeight + restartLabel.getAscent()) / 2;
           restartLabel.setColor(Color.BLUE);
           restartLabel.setLocation(restartX, restartY);
           add(restartLabel);	
  	    }
  	}


  	private void restartLevel() {
  	    removeAll(); //clear the screen
  	    //resets the labels
  	    isGameOver = false;
  	    restartBox = null;
  	    restartLabel = null;
  	    gameOverLabel = null;
  	    setUpLevel(); //re-setup everything
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
    
    if (isGameOver && e.getKeyCode() == KeyEvent.VK_R) {
        restartLevel();
    }

}

    @Override
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
        if(e.getKeyCode() == KeyEvent.VK_W) {
     	   Grapple= false;
        } 
    }

    @Override
    public void keyTyped(KeyEvent e) {
        player.keyTyped(e);
    }
    

    public static void main(String[] args) {
        new Level_4().start();
    }
}
