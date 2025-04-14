import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.imageio.*;
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

public class Enemies extends GraphicsProgram implements ActionListener{
public static int WINDOW_HEIGHT = 600; //height of window
public static int WINDOW_WIDTH = 600; //width of window
public static int X_VELOCITY = 3; //Velocity of enemies
public static int NUM_ENEMIES = 4; //number of enemies
public static int NUM_PLATFORMS = 4; //temporary
public static int PLAYER_SPAWN = 60; 
public static final int DEFAULT =0, MOVING = 1; //for sprites later use.
public static int HEIGHT = 30;
public static int WIDTH = 30;
private GOval player; 
private Timer timer;
ArrayList<Enemies> enemies;
ArrayList<Integer> Xvelocity;
ArrayList<GRect> platforms;
ArrayList<GRect> hitBoxes; //for enemy collision box
 ArrayList<GImage> enemiesImages;

private Enemies enemy; 
//private GImage enemy;
private GRect enemyHitbox;
private GRect platform;
private int healthPoint = 3;
private int count = 0; 
private GLabel healthText;
//private GImage enemyImages;
private int aniTickSpeed = 5; //Adjust to change speed of animation speed
private int aniIndex = 0; //Determines which frame of the animation to display
private int aniTick = 0;
// Player movement variables
private double playerVelocityX = 0;
private double playerVelocityY = 0;

private GraphicsProgram program;

public Enemies() {
	
	enemies = new ArrayList<Enemies>();
	Xvelocity = new ArrayList<>();
	hitBoxes = new ArrayList<GRect>();
	enemiesImages = new ArrayList<>();
	
 }
public void setProgram(GraphicsProgram program) {
    this.program = program;
}
 
public void addEnemy(double x, int y) { 
GImage enemyImages = new GImage("Media/Enemy_Sprite/Run 01.png");
enemy  = new Enemies(); 
enemyHitbox = new GRect(enemy.getWidth(),enemy.getHeight());
	
	    enemyImages.setLocation(x, y);
	    enemyHitbox.setLocation(enemyImages.getX(),enemyImages.getY());
	    enemyHitbox.setVisible(true);
	    

	    enemies.add(enemy);
	    enemiesImages.add(enemyImages);
	    program.add(enemyImages);
	    program.add(enemyHitbox);
	    Xvelocity.add(X_VELOCITY);
	    hitBoxes.add(enemyHitbox);
}


public void moveAllEnemies() {
	for(int i=0;i<enemies.size();++i) {
		GImage enemyImages = enemiesImages.get(i);
//		updateAnimation();
int velocity = Xvelocity.get(i);
enemyImages.move(velocity, 0);
hitBoxes.get(i).move(velocity, 0);
//updateAnimation(x,y);

//fixed. ensures enemy doesn't go out of platform
for(GRect platform1 : platforms) {
boolean ifonTop = (enemyImages.getY()+enemyImages.getHeight() == platform1.getY()) ;
if(ifonTop) {
boolean leftOfPlatform = enemyImages.getX()  <= platform1.getX();
boolean rightOfPlatform = enemyImages.getX() + enemyImages.getWidth() >= platform1.getX() + platform1.getWidth();
	
if( leftOfPlatform || rightOfPlatform){
	 System.out.println("Enemy " + i + " reversed velocity due to platform collision");
 Xvelocity.set(i, -velocity);
 break;
}
}
}
}
}

public void collisionCheck() {
    for (int i = enemies.size() - 1; i >= 0; --i) {
    // Check if there is a collision with the hitbox of the current enemy
    GObject Rightcollision = getElementAt(hitBoxes.get(i).getX() + hitBoxes.get(i).getWidth()+1,
    	                                     hitBoxes.get(i).getY() + hitBoxes.get(i).getHeight()/2 );
    GObject leftCollision =getElementAt(hitBoxes.get(i).getX() - 5,
                                     hitBoxes.get(i).getY() + hitBoxes.get(i).getHeight()/2 );
    // Check if there is a collision with the player
    GObject collision1 = getElementAt(player.getX() + player.getWidth() / 2,
    	                                      player.getY() + player.getHeight()+1);

   
    // If the player collides with the enemy, reduce health and respawn the player
    if (Rightcollision == player || leftCollision == player) {
        healthPoint--;  // Reduce health
    	   System.out.println("Collision detected Health: " + healthPoint);
    	   respawnPlayer();
    	   updateHealthUI();
    	        } 
    // If the enemy's hitbox collides with the player, remove the enemy
    if (collision1 == hitBoxes.get(i) || collision1 == enemiesImages.get(i)) {

    	System.out.println("removingg!!!!!!!!!!!!!!");
       removeEnemy(i);  // Pass the index of the enemy to remove
    	  }
    	    }
    	}

public void removeEnemy(int index) {
    // Remove the enemy from the screen
    program.remove(enemiesImages.get(index));
    // Remove enemy-related data from the lists
    enemies.remove(index);
    enemiesImages.remove(index);
     Xvelocity.remove(index);
     program.remove(hitBoxes.get(index));
     hitBoxes.remove(index);
  }


public double getWidth() {
	return WIDTH;
}
public double getHeight() {
	return HEIGHT;
}
	@Override
	public void run() {
		
		  enemy = new Enemies();
		 setProgram(this);
		 addEnemy(100,110); 
		 addEnemy(400,310); 

        
    	addKeyListeners();
		//generates a player
		player = new GOval(50, 50, 30, 30); //spawn location (x, y, width, height)
		player.setColor(Color.RED);
		player.setFilled(true);
		add(player);
				
		platforms = new ArrayList<GRect>();
		for(int i=0;i<NUM_PLATFORMS;++i) {
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
   	 
   	 timer = new Timer(50, this); //50 = milliseconds
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
    
//    setter and getter for enemy count; 
    public void setEnemyCount(int enemyCount) {
    	this.count = enemyCount; 
    }
   public int getEnemyCount() {
	   return count; 
   }
   
//  returns current list of enemies
   public ArrayList<Enemies> getEnemies() {
	return enemies;
	   
   }
    
    // Took the player code from testCoin and used it here to play around with collision
    @Override
    public void actionPerformed(ActionEvent e)  {
        player.setLocation(player.getX() + playerVelocityX, player.getY() + playerVelocityY); // Moves the player to new location
    	moveAllEnemies(); 
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
        Enemies enemy = new Enemies();
        enemy.start();
	}
}
