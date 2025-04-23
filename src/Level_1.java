import java.awt.Color;
import java.awt.event.KeyEvent;
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
    
    UI_Elements UI;
    private Level_1 level;
    
    private boolean gridVisible = true; //used for key presse
    private ArrayList<GLine> gridLines = new ArrayList<>(); //stores the grid lines
    private ArrayList<GLabel> gridLabels = new ArrayList<>(); //stores the labels that visually show the size of each cell
    public static final int GRID_SIZE = 40; //size of grid cell
    private GImage background; 
    
    public void run() {
        setSize(1280, 720); // Window size
        
    	if (gridVisible) {
    	    drawGrid(GRID_SIZE);
    	}
    	
        addKeyListeners();
        
        //background
        background = new GImage("Media/Background1.png");
        add(background); // Add it before resizing, so getWidth() is correct
        background.setSize(getWidth(), getHeight()); // Resizes it to fill the window
        background.sendToBack(); // Ensures it's behind all other elements
        
        //platform = new Platform(120, 40,40, 120, Platform.PlatformTypes.STATIC, 0, 0);
        //platform = new Platform(120, 40,240, 240, Platform.PlatformTypes.STATIC, 0, 0);      
        platform = new Platform();
        platform.setProgram(this);
        
        platform.addPlatform(0, 600, 100, 30, Platform.PlatformTypes.STATIC, 0, 0); //player spawn
        platform.addPlatform(200, 500, 100, 30, Platform.PlatformTypes.STATIC, 0, 0);
        platform.addPlatform(400, 400, 100, 30, Platform.PlatformTypes.STATIC, 0, 0);
        platform.addPlatform(400, 600, 100, 30, Platform.PlatformTypes.STATIC, 0, 0);
        platform.addPlatform(600, 600, 100, 30, Platform.PlatformTypes.STATIC, 0, 0);
        platform.addPlatform(600, 310, 100, 30, Platform.PlatformTypes.STATIC, 0, 0);
        platform.addPlatform(820, 500, 100, 30, Platform.PlatformTypes.STATIC, 0, 0);
        platform.addPlatform(820, 250, 100, 30, Platform.PlatformTypes.STATIC, 0, 0);
        platform.addPlatform(1100, 500, 100, 30, Platform.PlatformTypes.STATIC, 0, 0);
        platform.addPlatform(800, 500, 100, 30, Platform.PlatformTypes.STATIC, 0, 0);
        
        platform.addPlatform(1000, 200, 100, 30, Platform.PlatformTypes.STATIC, 0, 0); //has door

        platform.addPlatformsToScreen();
        
        //test coins
        coin = new testCoin(7);
        coin.setProgram(this);
        coin.spawnCoinsToPlatforms(coin.getCoinsOnPlatforms(), platform.getPlatforms());
        coin.init();
  
        door = new Door(5, 1025, 115);
        door.setProgram(this);
        door.init();
        
        //player
        player = new Player(0, 530);
        player.setProgram(this);
        player.spawn(0, 530);
        
       // UI = new UI_Elements();
        //UI.setProgram(this);
        //UI.createUI(coin,player);  
        
        
 	   GRect box = new GRect(1,1,player.getBounds().getWidth(),player.getBounds().getHeight());
 	   box.setColor(Color.black);
 	   add(box);


        while (true) {
            player.update(); //updates the Player animation loop & movement
            coin.update(player.getBounds()); //updates the collision to check if player is touching a coin
            platform.collision(player.getBounds());
            handlePlatformInteraction();
            
            //door.update(player, coin.getCoinsCollected());
            door.checkIfplayerCanExit(coin.getCoinsCollected());

           // UI.doorState(door);
           // UI.init(door,coin,player);
           
            box.setLocation(+player.getX(), player.getY());
            pause(16.66); // 60 FPS
           
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
      	    GRect touchedPlatform = platform.detectPlatformCollision(player.getBounds());
      	    player.setGrounded(false);
      	    if (touchedPlatform != null) {
      	   if(player.getY()+player.getBounds().getHeight()<=  touchedPlatform.getY()+15) {
      		   player.setGrounded(true);
      		   System.out.println("on top");
      		   player.setyVelocity(0);
      		   player.setY(touchedPlatform.getY()-player.getBounds().getHeight());
      	   }else if (player.getY() + player.getBounds().getHeight() > touchedPlatform.getY() &&
      		         player.getY() < touchedPlatform.getY() + touchedPlatform.getHeight()) {
      		   System.out.println("bottom"+player.getyVelocity());
      		   player.setY(touchedPlatform.getY()+touchedPlatform.getHeight());
      		   player.setyVelocity(0);
      		   player.setGrounded(false);
      		   player.setY(touchedPlatform.getY()+touchedPlatform.getBounds().getHeight()-5);
      	   }else { 
      		   player.setGrounded(false);
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
        new Level_1().start();
    }
}
