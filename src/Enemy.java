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
public static int X_VELOCITY = 1; //Velocity of enemies
public static final int DEFAULT =0, MOVING = 1; //for sprites later use.
private Enemy enemy; 
private int count = 0; 
private GImage EnemyImage;
private GraphicsProgram program;
public boolean isActive; 
public boolean movingRight;
ArrayList<GImage> sprites;
private int aniTick; 
private int aniIndex = 0;
private int aniTickSpeed =5;

public Enemy() {
	loadAnimations();
}
public void setProgram(GraphicsProgram program) {
    this.program = program;
}

private void loadAnimations() {
	sprites = loadImagesFromFolder("Media/Enemy_Sprite");
}

private ArrayList<GImage> loadImagesFromFolder(String folderPath) {
	 ArrayList<GImage> images = new ArrayList<>();
	 File folder = new File(folderPath);
	 File[] files = folder.listFiles();
	 if (files != null) {
	     for (File file : files) {
	         if (file.isFile() && file.getName().endsWith(".png")) {
	             images.add(new GImage(file.getPath()));
	         }
	     }
	 }
	 return images;
	}
public void spawnEnemy(GRect platformIndex) { 
	if(platformIndex != null ) {
		enemy = new Enemy();
		EnemyImage = new GImage(sprites.get(0).getImage());
		EnemyImage.setLocation(platformIndex.getX(), platformIndex.getY() - EnemyImage.getHeight());
	program.add(EnemyImage); 
	isActive = true; 
	}
}

private void updateAnimation() {
    aniTick++;
    if (aniTick >= aniTickSpeed) {
        aniTick = 0;
        aniIndex++;
        List<GImage> currentAni = sprites;

        if (aniIndex >= currentAni.size()) {
            aniIndex = 0; // Loop back to the first frame when reaching the end
        }

        // Set the image for the enemy
        EnemyImage.setImage(currentAni.get(aniIndex).getImage());
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


public boolean collisionCheck(hitBox playerHitbox, Player player) {

if(!isActive) return false; 
               if (isCollisionFromAbove(playerHitbox, enemy)) {
                   removeEnemy(enemy);
               } else if (isSideCollision(playerHitbox, enemy)) {
                   System.out.println("Enemy detected");
                   player.takeDamage();
                   player.respawn();
               }
			return isActive;
           }

       //checks for player collision from above
       private boolean isCollisionFromAbove(hitBox playerHitbox, Enemy enemy) {
    	   if(enemy == null || EnemyImage ==null ) {
      		 return false;
      	 }
           GRectangle enemyBounds = EnemyImage.getBounds();
           //returns true if player is touching the enemy's head
           return playerHitbox.getY() + playerHitbox.getHeight() <= enemyBounds.getY() + 5 &&
                  playerHitbox.intersects(enemyBounds); 
       }
////
////       //checks for player collision from both right and left sides 
       private boolean isSideCollision(hitBox playerHitbox, Enemy enemy2) {
    	   //returns true if player is touching either side
           return playerHitbox.intersects(EnemyImage.getBounds());
       }
      
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

   public int getEnemyCount() {
	   return count; 
   }


    public void update(GRect platform, hitBox playerHitbox, Player playerImage) {
    	EnemyMovement(platform);
    	collisionCheck(playerHitbox, playerImage);
    	updateAnimation();
    }
 
    
   }
