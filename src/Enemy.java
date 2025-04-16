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
ArrayList<Enemy> enemies;
ArrayList<Integer> Xvelocity;
//ArrayList<GRect> platforms;
ArrayList<GRect> hitBoxes; //for enemy collision box
 ArrayList<GImage> enemiesImages;
Platform origPlatform ; 
private Enemy enemy; 
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

private GImage enemyImages; 
private GraphicsProgram program;

public Enemy() {
	
	enemies = new ArrayList<Enemy>();
	Xvelocity = new ArrayList<>();
	hitBoxes = new ArrayList<GRect>();
	enemiesImages = new ArrayList<>();
	//loadAnimation();
 }
//loading animations to enemiesimages
public void loadAnimation() {
	enemiesImages = loadImagesFromFolder("Media/Enemy_Sprite"); 
}


private void updateAnimation() {
	 enemyImages = new GImage(enemiesImages.get(0).getImage());
	program.add(enemyImages);
aniTick++;
if (aniTick >= aniTickSpeed) {
    aniTick = 0;
    aniIndex++;
    List<GImage> currentAni = enemiesImages;

    if (aniIndex >= currentAni.size()) {
        aniIndex = 0; // Loop back to the first frame when reaching the end
    }

    // Set the image for the enemy
    enemyImages.setImage(enemiesImages.get(aniIndex).getImage());
}
} 
//loading images from the folder
public ArrayList<GImage> loadImagesFromFolder(String folderPath) {
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
public void setProgram(GraphicsProgram program) {
    this.program = program;
}
 
public void addEnemy(double x, int y) { 
	
GImage enemyImages = new GImage("Media/Enemy_Sprite/Run 01.png");
enemy  = new Enemy(); 
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


public void moveAllEnemies(ArrayList<GRect> platforms) {
	for(int i=0;i<enemies.size();++i) {
		GImage enemyImages = enemiesImages.get(i);

int velocity = Xvelocity.get(i);
enemyImages.move(velocity, 0);
hitBoxes.get(i).move(velocity, 0);
//updateAnimation();

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





//    checks for collision whenever player bottom touches the enemy the enemy will be killed

       public void collisionCheck() {
           GRectangle playerBounds = player.getBounds();

           for (int i = 0; i < enemies.size(); ++i) {
               GRect enemyBox = hitBoxes.get(i);

               if (enemies.get(i) != null && isCollisionFromAbove(playerBounds, enemyBox)) {
                   removeEnemy(i);
               } else if (isSideCollision(playerBounds, enemyBox)) {
                   healthPoint--;
                   System.out.println("Collision detected Health: " + healthPoint);
                   respawnPlayer();
                   updateHealthUI();
               }
           }
       }
       
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
      public void removeEnemy(int index) {
    	    // Defensive checks
    	    if (index < 0 || index >= enemiesImages.size()) return;

    	    // Remove enemy sprite from screen
    	    program.remove(enemiesImages.get(index));

    	    // Remove associated hitbox from screen
    	    program.remove(hitBoxes.get(index));

    	    // Remove data from lists
    	    enemiesImages.remove(index);
    	    hitBoxes.remove(index);
    	    Xvelocity.remove(index);
    	}
       	
      
      
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
		
		  enemy = new Enemy();
		 setProgram(this);
		 addEnemy(100,110); 
		 addEnemy(400,310); 

        
    	addKeyListeners();
		//generates a player
		player = new GOval(50, 50, 30, 30); //spawn location (x, y, width, height)
		player.setColor(Color.RED);
		player.setFilled(true);
		add(player);
	/*			
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
		*/
        healthText = new GLabel("Health: " + healthPoint, 5, 30);
        add(healthText);
   	 
   	 timer = new Timer(50, this); //50 = milliseconds
        timer.start();

	}
	
//	public void update() {
//		moveAllEnemies(); 
//		//collisionCheck();  
//	}
	
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
   public ArrayList<Enemy> getEnemies() {
	return enemies;
	   
   }
    
    // Took the player code from testCoin and used it here to play around with collision
    @Override
    public void actionPerformed(ActionEvent e)  {
        player.setLocation(player.getX() + playerVelocityX, player.getY() + playerVelocityY); // Moves the player to new location
    	moveAllEnemies(hitBoxes); 
		//collisionCheck();    
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
        Enemy enemy = new Enemy();
        enemy.start();
	}
}
