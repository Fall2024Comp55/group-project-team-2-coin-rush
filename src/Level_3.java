import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class Level_3 extends GraphicsPane {

    private Player player;
    private Platform platform;
    private Enemy enemy1;
    private Enemy enemy2;
    private Enemy enemy3;
    private Enemy enemy4;
    private testCoin coin;
    private Door door; 
    private hitBox playerHitbox;
    UI_Elements UI;
    private LevelManager program;
    
    private boolean gridVisible = true; //used for key presse
    private ArrayList<GLine> gridLines = new ArrayList<>(); //stores the grid lines
    private ArrayList<GLabel> gridLabels = new ArrayList<>(); //stores the labels that visually show the size of each cell
    public static final int GRID_SIZE = 40; //size of grid cell
    private GImage background; 
    
    private boolean isGameOver = false;
    private GLabel gameOverLabel;
    private GLabel restartLabel;
    private GRect restartBox;
    private Menu deathMenu; 
    
    private GRect box; //temp player hitbox
    
    public Level_3(LevelManager program) {
        this.program = program;
    }
    
    
    public void setUpLevel() {
        program.setSize(1280, 720);
    	if (gridVisible) {
    	    drawGrid(GRID_SIZE);
    	}
    	
    	
        //Background
        background = new GImage("Media/Background2.png");
        program.add(background);
        background.setSize(program.getWidth(), program.getHeight()); // Resizes it to fill the window
        background.sendToBack(); // Ensures it's behind all other elements
        
        
        //Platforms     
        platform = new Platform();
        platform.setProgram(program);

        platform.addPlatform(0, 300, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false); //player spawn

        platform.addPlatform(250, 600, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);
        platform.addPlatform(0, 600, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);
        platform.addPlatform(600, 310, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);
        platform.addPlatform(600, 500, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);
        platform.addPlatform(470, 570, 50, 30, Platform.PlatformTypes.STATIC, 0, 0,false);
        platform.addPlatform(1100, 500, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);
        platform.addPlatform(800, 400, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);
        platform.addPlatform(800, 250, 30, 30, Platform.PlatformTypes.STATIC, 0, 0,false);
        platform.addPlatform(1000, 200, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false); 
        platform.addPlatform(200, 200, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);
        platform.addPlatform(400, 400, 100, 30, Platform.PlatformTypes.STATIC, 0, 0,false);

        platform.addPlatform(1100, 500, 100, 30, Platform.PlatformTypes.STATIC, 0, 0, false); //has door

        platform.addPlatformsToScreen();
        
        //Coins
        coin = new testCoin(10);
        coin.setProgram(program);
        coin.spawnCoinsToPlatforms(coin.getCoinsOnPlatforms(), platform.getPlatforms(), false);
        coin.init();
  
        
        //Door
        door = new Door(8, 1125, 415);
        door.setProgram(program);
        program.add(door.getDoorImage());
        
        //player
        player = new Player(0, 230);
        player.setProgram(program);
        player.spawn(0, 230);
        
        
        //playerHitbox
        playerHitbox = new hitBox();
        playerHitbox.createHitbox(player.getX(), player.getY(), player.getBounds().getWidth(), player.getBounds().getHeight(), 20, 3);
        program.add(playerHitbox.getHitbox());
        
    	
        //Enemy(s)
        enemy1 = new Enemy();
        enemy1.setProgram(program);
        enemy1.spawnEnemy(platform.getPlatforms().get(7));
        
        enemy2 = new Enemy();
        enemy2.setProgram(program);
        enemy2.spawnEnemy(platform.getPlatforms().get(5));
        
        enemy3 = new Enemy();
        enemy3.setProgram(program);
        enemy3.spawnEnemy(platform.getPlatforms().get(8));
        
        enemy4 = new Enemy();
        enemy4.setProgram(program);
        enemy4.spawnEnemy(platform.getPlatforms().get(10));
        
        
        //UI
        UI = new UI_Elements();
        UI.setProgram(program);
        UI.createUI(coin, player); 
        

        //death menu
 	  deathMenu = new Menu();
 	  deathMenu.setProgram(program);
    }

    public void updateLevel() {
    	if(!isGameOver) {
            player.update(); //updates the Player animation loop & movement
            coin.update(playerHitbox); //updates the collision to check if player is touching a coin
            platform.collision(player.getBounds());
            handlePlatformInteraction();
            
            door.update(player, coin.getCoinsCollected());

            enemy1.update(platform.getPlatforms().get(7), playerHitbox, player); 
     	    enemy2.update(platform.getPlatforms().get(5), playerHitbox, player); 
     	    enemy3.update(platform.getPlatforms().get(8), playerHitbox, player); 
     	    enemy4.update(platform.getPlatforms().get(10), playerHitbox, player); 

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
      	            program.add(vertical);
      	            program.add(horizontal);
      	            gridLines.add(vertical);
      	            gridLines.add(horizontal);

      	            //adds the coordinate label in top-left of each cell
      	            String coords = "(" + x + "," + y + ")";
      	            GLabel label = new GLabel(coords, x + 2, y + 10); //offset a bit inside the cell
      	            label.setFont("Courier-8");
      	            label.setColor(Color.GRAY);
      	            program.add(label);
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
    	            //System.out.println("on top");
    	        }
    	        // Case 2: Player hits the bottom of a platform
    	        else if (playerTop <= platformBottom && playerTop >= platformBottom - 15 && player.getyVelocity() < 0) {
    	            player.setyVelocity(0); // Reset upward velocity
    	            //System.out.println("bottom collision");
    	        }
    	        // Case 3: Catch the player when falling through platforms
    	        else if (playerBottom > platformTop && playerBottom <= platformBottom && player.getyVelocity() > 0) {
    	            player.setyVelocity(0); // Stop falling
    	            player.setY(platformTop - playerHitbox.getHeight()); // Adjust position to the top
    	            player.setGrounded(true); // Ground the player
    	            //System.out.println("caught mid-fall");
    	        }
    	    }
    	}
      	private void clearGrid() {
      	    for (GLine line : gridLines) {
      	        program.remove(line);
      	    }
      	    gridLines.clear();

      	    for (GLabel label : gridLabels) {
      	        program.remove(label);
      	    }
      	    gridLabels.clear();
      	}

      	private void GameOverScreen() {
      	    if (isGameOver) {
      	    	return; //prevent duplicate triggers
      	    } else {
      	    	 isGameOver = true;
      	    	 
      	    	 deathMenu.showContents();

           	    //creates the game over label
      	    	 /*
      	    	gameOverLabel = new GLabel("YOU DIED");
      	        gameOverLabel.setFont("SansSerif-BOLD-48");
      	        gameOverLabel.setColor(Color.BLACK);
      	        gameOverLabel.setLocation((program.getWidth() - gameOverLabel.getWidth()) / 2, program.getHeight() / 2 - 60);
      	        program.add(gameOverLabel);
      	    	 
      	    	 
           	    //both create the restart label and button box
      	    	 restartBox = new GRect((program.getWidth() - 160) / 2, program.getHeight() / 2, 160, 50);
      	        restartBox.setFilled(true);
      	        restartBox.setFillColor(Color.LIGHT_GRAY);
      	        program.add(restartBox);

      	        restartLabel = new GLabel("RESTART");
      	        restartLabel.setFont("SansSerif-BOLD-24");
      	        restartLabel.setColor(Color.BLUE);
      	        restartLabel.setLocation(restartBox.getX() + (restartBox.getWidth() - restartLabel.getWidth()) / 2,
      	                                 restartBox.getY() + (restartBox.getHeight() + restartLabel.getAscent()) / 2);
      	        program.add(restartLabel);
      	        
      	        */
      	    }
      	}


        public void restartLevel() {
            program.removeAll();
            isGameOver = false;
            restartBox = null;
            restartLabel = null;
            gameOverLabel = null;
            setUpLevel();
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
    
}