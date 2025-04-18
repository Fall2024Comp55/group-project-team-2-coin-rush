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

public class Enemy {
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
private GImage EnemyImage;
// Player movement variables
private double playerVelocityX = 0;
private double playerVelocityY = 0;
private double x,y;
private GraphicsProgram program;
public boolean isActive; 
public boolean movingRight;
public void setProgram(GraphicsProgram program) {
    this.program = program;
}

public void spawnEnemy(GRect platformIndex) { 
	if(platformIndex != null ) {
		enemy = new Enemy();
		EnemyImage = new GImage("Media/Enemy_Sprite/Run 01.png");
		EnemyImage.setLocation(platformIndex.getX(), platformIndex.getY() - EnemyImage.getHeight());
	program.add(EnemyImage); 
	isActive = true; 
	}
}


public void EnemyMovement(GRect platform) { 
    double enemyX = EnemyImage.getX();
    double velocity = X_VELOCITY;

    // Move right or left
    if (movingRight) {
        enemyX += velocity;
    } else {
        enemyX -= velocity;
    }

    // Reverse direction if it hits the left boundary
    if (enemyX < platform.getX()) {
        enemyX = platform.getX();
        movingRight = true;
    }

    // Reverse direction if it hits the right boundary
    double platformRightEdge = platform.getX() + platform.getWidth();
    double enemyRightEdge = enemyX + EnemyImage.getWidth();
    if (enemyRightEdge > platformRightEdge ) {
        enemyX = platformRightEdge - EnemyImage.getWidth();
        movingRight = false;
    }

    // Actually move the image to the new position
    EnemyImage.setLocation(enemyX, EnemyImage.getY());
}


public boolean collisionCheck(GRectangle playerBounds, GImage player) {

if(!isActive) return false; 
               if ( isCollisionFromAbove(playerBounds, enemy)) {
                   removeEnemy(enemy);
               } else if (isSideCollision(playerBounds, enemy)) {
                   healthPoint--;
                   System.out.println("Collision detected Health: " + healthPoint);
                   respawnPlayer(player);
                   updateHealthUI();
               }
			return isActive;
           }
      
       //checks for player collision from above
       private boolean isCollisionFromAbove(GRectangle playerBounds, Enemy enemy) {
    	   if(enemy == null || EnemyImage ==null ) {
      		 return false;
      	 }
           GRectangle enemyBounds = EnemyImage.getBounds();
           //returns true if player is touching the enemy's head
           return playerBounds.getY() + playerBounds.getHeight() <= enemyBounds.getY() + 5 &&
                  playerBounds.intersects(enemyBounds); 
       }
////
////       //checks for player collision from both right and left sides 
       private boolean isSideCollision(GRectangle playerBounds, Enemy enemy2) {
    	   //returns true if player is touching either side
           return playerBounds.intersects(EnemyImage.getBounds());
       }
////       
////      // Function to remove an enemy and its associated hitbox and velocity
//      public void removeEnemy(int index) {
//    	    // Defensive checks
//    	    if (index < 0 || index >= enemiesImages.size()) return;
//
//    	    // Remove enemy sprite from screen
////    	    program.remove(enemiesImages.get(index));
//    	    enemy.remove(EnemyImage);;
//    	}
      
       public void removeEnemy(Enemy enemy) {
    	    if (EnemyImage != null) {
    	        program.remove(EnemyImage);
    	        isActive = false; 
    	     
    	    }
    	    this.enemy = null; // stop the logic
    	}
    //acts like a "hitbox" in that it just returns the area of the enemy image.
      public GRectangle getBounds() {
          return EnemyImage.getBounds();
      }


public double getWidth() {
	return WIDTH;
}
public double getHeight() {
	return HEIGHT;
}

    // Respawn player to a fixed starting position
    public void respawnPlayer(GImage player) {
    	player.setLocation(PLAYER_SPAWN, PLAYER_SPAWN);
    }
    
    private void updateHealthUI() {
    	GLabel healthText = new GLabel("Health: " +  healthPoint );
    	program.add(healthText);
//        healthText.setLabel("Health: " + healthPoint);
    }
    
//    setter and getter for enemy count; 
    public void setEnemyCount(int enemyCount) {
    	this.count = enemyCount; 
    }
   public int getEnemyCount() {
	   return count; 
   }
   

    public void update(GRect platform,GRectangle playerBounds, GImage playerImage) {
    	EnemyMovement(platform);
    	collisionCheck(playerBounds, playerImage);
    }
 
    
   }
