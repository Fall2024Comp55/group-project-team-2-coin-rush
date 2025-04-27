import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class Level_1 extends GraphicsProgram {

    private Player player;
    private Platform platform;
    private Enemy enemy;
    private Enemy enemy1;
    private testCoin coin;
    private Door door; 
    private hitBox playerHitbox;
    UI_Elements UI;
    private Level_1 level;
    private Menu deathMenu; 
    
    private boolean gridVisible = true; //used for key presse
    private ArrayList<GLine> gridLines = new ArrayList<>(); //stores the grid lines
    private ArrayList<GLabel> gridLabels = new ArrayList<>(); //stores the labels that visually show the size of each cell
    public static final int GRID_SIZE = 40; //size of grid cell
    private GImage background; 
    
    private boolean isGameOver = false;
    private GLabel gameOverLabel;
    private GLabel restartLabel;
    private GRect restartBox;
    
    private GRect box; //temp player hitbox

    
    public void run() {
        setSize(1280, 720); // Window size
        
    	if (gridVisible) {
    	    drawGrid(GRID_SIZE);
    	}
    	
        addKeyListeners();
        //setUpLevel(); //creates the level
        showContent();
        while (true) {
        		updateLevel(); //updates the level
                pause(16.66); // 60 FPS
        	}
        }
    
    
    public void showContent() {
        //background
        background = new GImage("Media/Background1.png");
        add(background); // Add it before resizing, so getWidth() is correct
        background.setSize(getWidth(), getHeight()); // Resizes it to fill the window
        background.sendToBack(); // Ensures it's behind all other elements
        
        //platform = new Platform(120, 40,40, 120, Platform.PlatformTypes.STATIC, 0, 0);
        //platform = new Platform(120, 40,240, 240, Platform.PlatformTypes.STATIC, 0, 0);      
        platform = new Platform();
        platform.setProgram(this);
        

        platform.addPlatform(0, 600, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false); //player spawn
        platform.addPlatform(200, 500, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);
        platform.addPlatform(400, 400, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);
        platform.addPlatform(400, 600, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);
        platform.addPlatform(600, 600, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);
        platform.addPlatform(600, 310, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);
        platform.addPlatform(820, 500, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);
        platform.addPlatform(820, 250, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);
        platform.addPlatform(1100, 500, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);
        platform.addPlatform(800, 500, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);

        platform.addPlatform(1000, 200, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false); //has door

        platform.addPlatformsToScreen();
        
        //test coins
        coin = new testCoin(7);
        coin.setProgram(this);
        coin.spawnCoinsToPlatforms(coin.getCoinsOnPlatforms(), platform.getPlatforms(), false);
        //coin.spawnCoinManually(500, 500);
        coin.init();
  
        door = new Door(5, 1025, 115);
        door.setProgram(this);
        door.init();
        
        //player   
        player = new Player(0, 530);
        player.setProgram(this);
        player.spawn(0, 530);
        
        playerHitbox = new hitBox();
     	playerHitbox.createHitbox(player.getX(), player.getY(), player.getBounds().getWidth(), player.getBounds().getHeight(), 20, 3);
    	add(playerHitbox.getHitbox()); // Add hitbox to canvas for debugging purposes
        UI = new UI_Elements();
        UI.setProgram(this); 
        UI.createUI(coin,player);  
        
        
 	   box = new GRect(1,1,player.getBounds().getWidth(),player.getBounds().getHeight());
 	   box.setColor(Color.black);
 	   add(box);

 	  deathMenu = new Menu();
 	 deathMenu.setProgram(this);
    }
     
    private void updateLevel() {
    	if(!isGameOver) {
            player.update(); //updates the Player animation loop & movement
            coin.update(playerHitbox); //updates the collision to check if player is touching a coin
            platform.collision(player.getBounds());
            handlePlatformInteraction();
            
            //door.update(player, coin.getCoinsCollected());
            door.checkIfplayerCanExit(coin.getCoinsCollected());

            UI.init(door,coin,player);
          //hitbox movement
            playerHitbox.updateHitbox(player.getX(),player.getY() , 20, 3);
            //prevents crash
            if (box != null) {
                box.setLocation(player.getX(), player.getY());
            }            
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

      	private void GameOverScreen() {
      	    if (isGameOver) {
      	    	return; //prevent duplicate triggers
      	    } else {
      	    	 isGameOver = true;
           	    
           	    //creates the game over label
//           	  gameOverLabel = new GLabel("YOU DIED");
//               gameOverLabel.setFont("SansSerif-BOLD-48");
//               gameOverLabel.setColor(Color.BLACK);
//               double labelX = (getWidth() - gameOverLabel.getWidth()) / 2;
//               double labelY = getHeight() / 2 - 60; // a bit above center
//               gameOverLabel.setLocation(labelX, labelY);
//               add(gameOverLabel);
            
      	    	 deathMenu.showContents();
      	    	 
           	    //both create the restart label and button box
//               int boxWidth = 160;
//               int boxHeight = 50;
//               int boxX = (int) ((getWidth() - boxWidth) / 2);
//               int boxY = (int) (getHeight() / 2);
//               restartBox = new GRect(boxX, boxY, boxWidth, boxHeight);
//               restartBox.setFilled(true);
//               restartBox.setFillColor(Color.LIGHT_GRAY);
//               restartBox.setColor(Color.BLACK);
//               add(restartBox);
//
//               restartLabel = new GLabel("'R' to restart");
//               restartLabel.setFont("SansSerif-BOLD-24");
//               double restartX = boxX + (boxWidth - restartLabel.getWidth()) / 2;
//               double restartY = boxY + (boxHeight + restartLabel.getAscent()) / 2;
//               restartLabel.setColor(Color.BLUE);
//               restartLabel.setLocation(restartX, restartY);
//               add(restartLabel);	
      	    }
      	}


      	private void restartLevel() {
      	    removeAll(); //clear the screen
      	    //resets the labels
      	    isGameOver = false;
      	    restartBox = null;
      	    restartLabel = null;
      	    gameOverLabel = null;
      	    //setUpLevel(); //re-setup everything
      	  showContent();
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
        
        if(isGameOver && e.getKeyCode() == KeyEvent.VK_E) {
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
        new Level_1().start();
    }
}