import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.*;
import acm.graphics.*;
import acm.program.*;

public class Enemy {
<<<<<<< HEAD
    public static int WINDOW_HEIGHT = 600;
    public static int WINDOW_WIDTH = 600;
    public static int X_VELOCITY = 1;
    public static final int DEFAULT = 0, MOVING = 1;
=======
public static int WINDOW_HEIGHT = 600; //height of window
public static int WINDOW_WIDTH = 600; //width of window
public static int X_VELOCITY = 1; //Velocity of enemies
public static final int DEFAULT =0, MOVING = 1; //for sprites later use.
private Enemy enemy; 
private int count = 0; 
private GImage EnemyImage;
private LevelManager manager;
public boolean isActive; 
public boolean movingRight;
ArrayList<GImage> sprites;
private int aniTick; 
private int aniIndex = 0;
private int aniTickSpeed =5;
>>>>>>> refs/remotes/origin/main

<<<<<<< HEAD
    private GImage EnemyImage;
    private GraphicsProgram program;
    private boolean isActive;
    private boolean movingRight;
    private GRect platform;
    private ArrayList<GImage> sprites;
    private int aniTick;
    private int aniIndex = 0;
    private int aniTickSpeed = 5;
=======
public Enemy() {
	loadAnimations();
}
public void setProgram(LevelManager program) {
    this.manager = program;
}
>>>>>>> refs/remotes/origin/main

    private double lastPlatformX;
    private double lastPlatformY;

<<<<<<< HEAD
    public Enemy() {
        loadAnimations();
    }
=======
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
	manager.add(EnemyImage); 
	isActive = true; 
	}
}
>>>>>>> refs/remotes/origin/main

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

    public void spawnEnemy(GRect platform) {
        if (platform != null) {
            this.platform = platform;
            EnemyImage = new GImage(sprites.get(0).getImage());
            EnemyImage.setLocation(platform.getX(), platform.getY() - EnemyImage.getHeight());
            program.add(EnemyImage);
            isActive = true;
            movingRight = true;

            lastPlatformX = platform.getX();
            lastPlatformY = platform.getY();
        }
    }

    private void followPlatform() {
        if (platform == null || EnemyImage == null) return;

        double dx = platform.getX() - lastPlatformX;
        double dy = platform.getY() - lastPlatformY;

        EnemyImage.move(dx, dy);

        lastPlatformX = platform.getX();
        lastPlatformY = platform.getY();
    }

    private void updateAnimation() {
        aniTick++;
        if (aniTick >= aniTickSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= sprites.size()) {
                aniIndex = 0;
            }
            EnemyImage.setImage(sprites.get(aniIndex).getImage());
        }
    }

    private void EnemyMovement() {
        if (platform == null || EnemyImage == null) return;

        double enemyX = EnemyImage.getX();
        double velocity = X_VELOCITY;

        if (movingRight) {
            enemyX += velocity;
        } else {
            enemyX -= velocity;
        }

        if (enemyX < platform.getX()) {
            enemyX = platform.getX();
            movingRight = true;
        }

        double platformRightEdge = platform.getX() + platform.getWidth();
        double enemyRightEdge = enemyX + EnemyImage.getWidth();
        if (enemyRightEdge > platformRightEdge) {
            enemyX = platformRightEdge - EnemyImage.getWidth();
            movingRight = false;
        }

        EnemyImage.setLocation(enemyX, EnemyImage.getY());
    }

    public boolean collisionCheck(hitBox playerHitbox, Player player) {
        if (!isActive || EnemyImage == null) return false;

        // Always refresh hitbox with real-time image location
        GRectangle updatedEnemyBounds = new GRectangle(EnemyImage.getX(), EnemyImage.getY(), EnemyImage.getWidth(), EnemyImage.getHeight());

        if (playerHitbox.getY() + playerHitbox.getHeight() <= updatedEnemyBounds.getY() + 5 &&
            playerHitbox.getBounds().intersects(updatedEnemyBounds)) {
            removeEnemy();
        } else if (playerHitbox.getBounds().intersects(updatedEnemyBounds)) {
            System.out.println("Enemy detected");
            player.takeDamage();
            player.respawn();
        }
        return isActive;
    }

    public void removeEnemy() {
        if (EnemyImage != null) {
            program.remove(EnemyImage);
            isActive = false;
        }
    }

    public void update(hitBox playerHitbox, Player player) {
        if (!isActive) return;
        followPlatform();
        EnemyMovement();
        collisionCheck(playerHitbox, player);
        updateAnimation();
    }
}
<<<<<<< HEAD
=======


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
    	        manager.remove(EnemyImage);
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
   
   public GImage getEnemyImage() {
	   return EnemyImage;
   }

	public void update(GRect platform, hitBox playerHitbox, Player player) {
		    if (getEnemyImage() == null) return; // prevent crashing

    	EnemyMovement(platform);
    	collisionCheck(playerHitbox, player);
    	updateAnimation();		
	}
 
    
   }
>>>>>>> refs/remotes/origin/main
