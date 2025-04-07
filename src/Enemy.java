import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.Timer;

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

/* CHANGES
 * I added the player I used for testCoin here to mess around with collision.
 * 1) removed while(true) loop in run and added a timer. This is need for player movement and collision detection.
 * 2) added a temp label to track player HP. If player collides with enemy, removes a state.
 * 3) added player respawn, which takes a fixed position (pretty much where the player first spawned) and essentially 
 * 		resets the player here if he takes damage.
 * 4) added collision detection, which uses getBounds() to detect.
 * 5) added a temp label update function.
 * 6) moved checkPlayerCollision(), enemyMovement(), and updateHealthUI() to actionsPerformed
 * 
 * ToDos
 * 1) have to find a way to detect if the player is on top of an enemy. If so and colsion within that bound is detected, it should remove the enemy.
 * 		Should add temp labels to track this I think.
 * 		I thnk this uses another collision detection? unsure.
 */

public class Enemy extends GraphicsProgram{
public static int WINDOW_HEIGHT = 600; //height of window
public static int WINDOW_WIDTH = 600; //width of window
public static int X_VELOCITY = 3; //Velocity of enemies
public static int NUM_ENEMIES = 4; //number of enemies
public static int NUM_PLATFORMS = 4; //temporary
public static int PLAYER_SPAWN = 50; 
public static final int DEFAULT =0, MOVING = 1; //for sprites later use.
private GOval player; 
private Timer timer;
ArrayList<GImage> enemies; 
ArrayList<Integer> Xvelocity;
ArrayList<GRect> platforms;
ArrayList<GRect> hitBoxes; //for enemy collision box
private ArrayList<GImage> enemiesImages;
private GImage enemy;
private GRect enemyHitbox;
private GRect platform;
private int playerAction = DEFAULT; 
private int healthPoint = 3;
private GLabel healthText;

// Player movement variables
private double playerVelocityX = 0;
private double playerVelocityY = 0;
 
//incomplete
public int aniTick =0, aniSpeed = 25, aniIndex=0 , maxImages ; 
public void loadImages() {
enemiesImages = loadImagesFromFolder("Media/Enemy Sprite");
 }

//  loading images from the folder
public ArrayList<GImage> loadImagesFromFolder(String folderPath) {
	ArrayList<GImage> images = new ArrayList<>(); 
	File file = new File(folderPath);
	File[] fileList  = file.listFiles();
	if(fileList != null) {
	for(File file1 : fileList) {
		if(file1.isFile() && file1.getName().endsWith(".png")) {
			images.add(new GImage(file.getPath()));
  }
 }
}
return images;
  }
  
  
//  animating enemy not yet completed. 
public void animateEnemy() {
	aniTick++;
	if(aniTick >= aniSpeed) {
		aniTick = 0; 
		aniIndex++; 
	}
}
 
//  creates a single enemy and adds into the array
public void createEnemy() {  
	  for(int i=0;i<NUM_ENEMIES;++i) {
		enemy = new GImage("Media/Enemy Sprite/Run 01.png");
		enemyHitbox = new GRect(enemy.getWidth(),enemy.getHeight());
		enemies.add(enemy);
	    add(enemy);
	    add(enemyHitbox);
	    enemyHitbox.setVisible(false);
	    Xvelocity.add(X_VELOCITY);
	    hitBoxes.add(enemyHitbox);
	     
	   }
   }
      
//    spawns the list of enemies and sets the location
       	public void spawnEnemies() {
	    for(int i=0;i<enemies.size();++i) {
	    GRect platform = platforms.get(i % platforms.size());
	    enemies.get(i).setLocation(platform.getX(), platform.getY() - enemies.get(i).getHeight());
	    hitBoxes.get(i).setLocation(platform.getX() , platform.getY() - enemies.get(i).getHeight());
	   }
   }
   
//   handles enemy movement according to the area of platform
       	public void enemyMovement() {
       	for(int i=0;i<enemies.size();++i) {
        enemy = enemies.get(i);
		int velocity = Xvelocity.get(i);
		enemies.get(i).move(velocity, 0);
		hitBoxes.get(i).move(velocity, 0);
		
		
//		fixed. ensures enemy doesn't go out of platform
		for(GRect platform1 : platforms) {
		boolean ifonTop = (enemy.getY()+enemy.getHeight() == platform1.getY()) ;
		if(ifonTop) {
		boolean leftOfPlatform = enemy.getX()  <= platform1.getX();
		boolean rightOfPlatform = enemy.getX() + enemy.getWidth() >= platform1.getX() + platform1.getWidth();
			
		if( leftOfPlatform || rightOfPlatform){
 	    Xvelocity.set(i, -velocity);
 	    break;
		}	
	  }
     }
    }
       	
}
       	
       	
//    checks for collision whenever player bottom touches the enemy the enemy will be killed
// not working as expected will keep working on it. 
       	public void collisionCheck() {
       	    for (int i = 0; i < enemies.size(); ++i) {
       	    	
       	        // Check if there is a collision with the hitbox of the current enemy
       	    GObject Rightcollision = getElementAt(hitBoxes.get(i).getX() + hitBoxes.get(i).getWidth()+1,
       	                                     hitBoxes.get(i).getY() + hitBoxes.get(i).getHeight()/2 );
       	    
       	    GObject leftCollision = getElementAt(hitBoxes.get(i).getX() - 5,
                                        hitBoxes.get(i).getY() + hitBoxes.get(i).getHeight()/2 );
       	    
       	        // Check if there is a collision with the player
       	    GObject collision1 = getElementAt(player.getX() + player.getWidth() / 2,
       	                                      player.getY() + player.getHeight());

       	        // If the enemy's hitbox collides with the player, remove the enemy
       	        if (enemies.get(i) != null && collision1 == hitBoxes.get(i)) {
       	            removeEnemy(i);  // Pass the index of the enemy to remove
       	        }
       	        // If the player collides with the enemy, reduce health and respawn the player
       	         if (Rightcollision == player || leftCollision == player) {
       	            healthPoint--;  // Reduce health
       	            System.out.println("Collision detected Health: " + healthPoint);
       	            respawnPlayer();
       	            updateHealthUI();
       	        }
       	         
       	    }
       	
      
       	}

       	// Function to remove an enemy and its associated hitbox and velocity
       	 public void removeEnemy(int index) {
       	    // Remove the enemy from the screen
       	    remove(enemies.get(index));

       	    // Remove enemy-related data from the lists
       	    enemies.remove(index);
       	    Xvelocity.remove(index);
       	    remove(hitBoxes.get(index));
       	    hitBoxes.remove(index);
       	}
       	
	@Override
	public void run() {
		addKeyListeners();
    	timer = new Timer(50, this); //50 = milliseconds
        timer.start();

		//generates a player
		player = new GOval(50, 50, 30, 30); //spawn location (x, y, width, height)
		player.setColor(Color.RED);
		player.setFilled(true);
		add(player);
				
		platforms = new ArrayList<GRect>();
		for(int i=0;i<NUM_PLATFORMS; ++i) {
	    platform = new GRect(200, 20);
	    platform.setColor(Color.BLACK);
	    platform.setFilled(true);
	    platforms.add(platform);
		add(platform); 
		}
		
		for(int i=0;i<platforms.size();++i) {
			add(platforms.get(i));
			platforms.get(0).setLocation(100, 140);
			platforms.get(1).setLocation(400, 340);
			platforms.get(2).setLocation(50, 440);
			platforms.get(3).setLocation(20, 300);
		}
		
        healthText = new GLabel("Health: " + healthPoint, 5, 30);
        add(healthText);

        
		enemies = new ArrayList<GImage>();
		Xvelocity = new ArrayList<Integer>();
		hitBoxes = new ArrayList<GRect>();
		createEnemy();
		spawnEnemies();
	
        // I needed to use a timer instead of a while(true) loop for the player movement and collision detection
        timer = new Timer(30, this);
        timer.start();
	}
	
	
	// Checks if player collides with enemies
    
    
    // Respawn player to a fixed starting position
    public void respawnPlayer() {
        player.setLocation(PLAYER_SPAWN, PLAYER_SPAWN);
    }
    
    private void updateHealthUI() {
        healthText.setLabel("Health: " + healthPoint);
    }
    
    // Took the player code from testCoin and used it here to play around with collision

    @Override
    public void actionPerformed(ActionEvent e)  {
        player.setLocation(player.getX() + playerVelocityX, player.getY() + playerVelocityY); // Moves the player to new location
		enemyMovement(); // With actionEvent i moved the enemy movement here instead
//        checkPlayerCollision(); // commented out so I can have the old version if new didn't work
		collisionCheck();
    }
    
    public void init() {
    	setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
    	
    }
    @Override
    public void keyPressed(KeyEvent e) {
        // Move the player based on key pressed
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: // Move up
                playerVelocityY = -10;
                break;
            case KeyEvent.VK_A: // Move left
                playerVelocityX = -10;
                break;
            case KeyEvent.VK_S: // Move down
                playerVelocityY = 10;
                break;
            case KeyEvent.VK_D: // Move right
                playerVelocityX = 10;
                break;
                }
        }
    // Handles key release to stop movement
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: // Stop moving up
                playerVelocityY = 0;
                break;
            case KeyEvent.VK_A: // Stop moving left
                playerVelocityX = 0;
                break;
            case KeyEvent.VK_S: // Stop moving down
                playerVelocityY = 0;
                break;
            case KeyEvent.VK_D: // Stop moving right
                playerVelocityX = 0;
                break;
                }
        }
    
    public static void main(String[] args) {
        Enemy enemies = new Enemy();
        enemies.start();
	}
}

