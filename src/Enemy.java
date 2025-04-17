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

public class Enemy extends GraphicsProgram implements ActionListener{
public static int WINDOW_HEIGHT = 600; //height of window
public static int WINDOW_WIDTH = 600; //width of window
public static int X_VELOCITY = 2; //Velocity of enemies
public static int NUM_ENEMIES = 4; //number of enemies
public static int NUM_PLATFORMS = 4; //temporary
public static int PLAYER_SPAWN = 60; 
public static final int DEFAULT =0, MOVING = 1; //for sprites later use.
public static int HEIGHT = 30;
public static int WIDTH = 30;
private GOval player; 
private Timer timer;
Platform origPlatform ; 
private Enemy enemy; 
//private GImage enemy;
ArrayList<GRect> platforms;
private GRect enemyHitbox;
private GRect platform;
private int healthPoint = 3;
private int count = 0; 
private GLabel healthText;

// Player movement variables
private double playerVelocityX = 0;
private double playerVelocityY = 0;

private GraphicsProgram program;


public void setProgram(GraphicsProgram program) {
    this.program = program;
}

public void spawnEnemy(GRect Platform, int numEnemy) {
	for(int i=0; i<numEnemy; ++i) {
	if(Platform != null ) {
		GImage EnemyImage = new GImage("Media/Enemy_Sprite/Run 01.png");
		double distance = i*50 ;
		EnemyImage.setLocation(Platform.getX() + distance, Platform.getY() - EnemyImage.getHeight());
	add(EnemyImage); 
	}
	}
}

//    checks for collision whenever player bottom touches the enemy the enemy will be killed

//       public void collisionCheck() {
//           GRectangle playerBounds = player.getBounds();
//
//           for (int i = 0; i < enemies.size(); ++i) {
//               GRect enemyBox = hitBoxes.get(i);
//
//               if (enemies.get(i) != null && isCollisionFromAbove(playerBounds, enemyBox)) {
//                   removeEnemy(i);
//               } else if (isSideCollision(playerBounds, enemyBox)) {
//                   healthPoint--;
//                   System.out.println("Collision detected Health: " + healthPoint);
//                   respawnPlayer();
//                   updateHealthUI();
//               }
//           }
//       }
       
       //checks for player collision from above
       private boolean isCollisionFromAbove(GRectangle playerBounds, GRect enemy) {
           GRectangle enemyBounds = enemy.getBounds();
           //returns true if player is touching the enemy's head
           return player.getY() + player.getHeight() <= enemyBounds.getY() + 5 &&
                  playerBounds.intersects(enemyBounds); 
       }

       //checks for player collision from both right and left sides 
       private boolean isSideCollision(GRectangle playerBounds, GRect enemy) {
    	   //returns true if player is touching either side
           return playerBounds.intersects(enemy.getBounds());
       }
       
      // Function to remove an enemy and its associated hitbox and velocity
//      public void removeEnemy(int index) {
//    	    // Defensive checks
//    	    if (index < 0 || index >= enemiesImages.size()) return;
//
//    	    // Remove enemy sprite from screen
//    	    program.remove(enemiesImages.get(index));
//
//    	    // Remove associated hitbox from screen
//    	    program.remove(hitBoxes.get(index));
//
//    	    // Remove data from lists
//    	    enemiesImages.remove(index);
//    	    hitBoxes.remove(index);
//    	    Xvelocity.remove(index);
//    	}
       	
      
      
    //acts like a "hitbox" in that it just returns the area of the enemy image.
      public GRectangle getBounds() {
          return enemyHitbox.getBounds();
      }


public double getWidth() {
	return WIDTH;
}
public double getHeight() {
	return HEIGHT;
}
	@Override
	public void run() {
		
		
        
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
		

			platforms.get(0).setLocation(100, 140);
			platforms.get(1).setLocation(400, 340);
			platforms.get(2).setLocation(50, 440);
			platforms.get(3).setLocation(20, 300);

	
        healthText = new GLabel("Health: " + healthPoint, 5, 30);
        add(healthText);
   	 
   	    timer = new Timer(50, this); //50 = milliseconds
        timer.start();
        
//        enemy = new Enemy();
       setProgram(this);
  spawnEnemy(platforms.get(1), 2);
  spawnEnemy(platforms.get(2), 2);

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
   

    
    // Took the player code from testCoin and used it here to play around with collision
    @Override
    public void actionPerformed(ActionEvent e)  {
        player.setLocation(player.getX() + playerVelocityX, player.getY() + playerVelocityY); // Moves the player to new location

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
    	new Enemy().start();
	}
}
