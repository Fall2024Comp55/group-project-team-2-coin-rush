import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.graphics.GRectangle;
import acm.program.GraphicsProgram;


public class Player extends GraphicsPane {
	
private int healthPoints = 3;// is the value for the health points of the player
private double x, y; // is the location of the player
public static final int VELOCITY = 5;// player movement speed
public static final int JUMPFORCE = -10;// how much jump power the player has.
public static final double GRAVITY = .5;// a constant pull downwards that the player has should be changed by level or static.
public double jumpMulti =1 ;// Changes the jump force of the player
private final double TERMINAL_VELOCITY =50; // Maximum falling speed
private boolean right, left , up , down;// player movement booleans
private boolean grounded;//The player check if they are touching the ground
private GImage player; // The player's graphical representation
public static final int PLAYER_SIZE = 30; // Diameter of the player 
private double yVelocity = 0;// current upwards or falling speed of the player
private double xVelocity = 0;// not used right now
private static final double MAX_HORIZONTAL_SPEED = 4.0; // Terminal Horizontal Velocity 


private ArrayList<GImage> idleAniRight, movingAniRight, jumpingAniRight; //Original right-facing
private ArrayList<GImage> idleAniLeft, movingAniLeft, jumpingAniLeft;   //Flipped left-facing

private int playerAction; //Default animation
private int lastPlayerAction = IDLE_RIGHT;

//private int aniTickSpeed = 5; //Adjust to change speed of animation speed
private int aniIndex = 0; //Determines which frame of the animation to display
private int playerSizeX = 64;
private int playerSizeY = 40;
private int playerRespawnX;
private int playerRespawnY;
private GLabel healthText;
private boolean facingRight = true; //Tracks the direction the player is facing
private boolean forceRespawn = false;


private static final int IDLE_LEFT = -0, MOVING_LEFT = -1, JUMPING_LEFT = -2; //Adjust to add more player actions
private static final int IDLE_RIGHT = 0, MOVING_RIGHT = 1, JUMPING_RIGHT = 2; //Adjust to add more player actions


//Replace aniTick-related variables
private long lastFrameTime = System.nanoTime();
private long frameDurationNs = 100_000_000; // 100ms = 0.1s per frame (adjust speed here)


private GraphicsProgram program; //Allows screen access from outside


//Load the images automatically in the appropriate GImage lists
public Player(int respawnX, int respawnY) {
   loadAnimations();
   this.playerRespawnX = respawnX;
   this.playerRespawnY = respawnY;
}

//Used by Level_0_tests
public void setProgram(GraphicsProgram program) {
 this.program = program;
}

//public void init() {
//	   
//	  }

//creates and sets player on screen
public void spawn(int spawnX, int spawnY) {
    x = spawnX;
    y = spawnY;
    player = idleAniRight.get(0);
    player.setLocation(x, y);
    program.add(player);
    grounded = true;
}

//Makes use of a function to load images from my sprite sub folders of Media folder to their respective lists 
private void loadAnimations() {
    idleAniRight = loadImagesFromFolder("Media/Sprite_IDLE");
    movingAniRight = loadImagesFromFolder("Media/Sprite_MOVING");
    jumpingAniRight = loadImagesFromFolder("Media/Sprite_JUMPING");

    idleAniLeft = flipImageList(idleAniRight);
    movingAniLeft = flipImageList(movingAniRight);
    jumpingAniLeft = flipImageList(jumpingAniRight);
}



//Pulls the image files from the Media sub folder(s) to add to a list of GImages and return said list
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

//Cycles through animation frames based on the player's action (idle(0), moving(1), jumping(2)).
//When aniTick reaches aniTickSpeed, it resets to 0, and aniIndex is incremented.
private void updateAnimation() {
    List<GImage> currentAni = switch (playerAction) {
        case MOVING_RIGHT -> movingAniRight;
        case JUMPING_RIGHT -> jumpingAniRight;
        case MOVING_LEFT -> movingAniLeft;
        case JUMPING_LEFT -> jumpingAniLeft;
        default -> facingRight ? idleAniRight : idleAniLeft;
    };

    long currentTime = System.nanoTime();
    frameDurationNs = switch (playerAction) {
        case MOVING_RIGHT, MOVING_LEFT -> 75_000_000L;
        case JUMPING_RIGHT, JUMPING_LEFT -> 120_000_000L;
        default -> 150_000_000L;
    };

    if (currentTime - lastFrameTime >= frameDurationNs) {
        aniIndex = (aniIndex + 1) % currentAni.size();
        lastFrameTime = currentTime;
        player.setImage(currentAni.get(aniIndex).getImage());
    }
}



//Replaces all current animation frame images with their horizontally flipped versions.
//This is used to flip the character's direction when turning left or right.
private ArrayList<GImage> flipImageList(ArrayList<GImage> originals) {
    ArrayList<GImage> flipped = new ArrayList<>();
    for (GImage img : originals) {
        GImage flippedImg = flipGImageHorizontally(img);
        flippedImg.setSize(PLAYER_SIZE, PLAYER_SIZE);
        flipped.add(flippedImg);
    }
    return flipped;
}



/* Flips a GImage horizontally by reversing its pixel array.
 * This was a fix I found since GImage doesn't support flipping. 
 * I also found that using negative width with setSize() does not visually flip the image.
*/
private GImage flipGImageHorizontally(GImage original) {
 int[][] pixels = original.getPixelArray();// Get the 2D array of pixels from the image
 int height = pixels.length;
 int width = pixels[0].length;

 int[][] flippedPixels = new int[height][width]; // Create a new pixel array for the flipped image

 // Copy pixels in reverse order horizontally (mirror effect)
 for (int row = 0; row < height; row++) {
     for (int col = 0; col < width; col++) {
         flippedPixels[row][col] = pixels[row][width - col - 1];
     }
 }

 return new GImage(flippedPixels); // Return a new GImage using the flipped pixel data
}




public int getHP(){
		return healthPoints; 
		//gets the health points of the player
}

public void setHP(int HP){
	this.healthPoints = HP;
}

public double getX() {
	return x;
}

public void setX(double X) {
	this.x= X;
}

public double getY() {
	return y;
}

public void setY(double Y) {
	this.y = Y;
}

public double getjumpMulti() {
	return jumpMulti;
}

private void setjumpMulti(double Multi) {
	this.jumpMulti = Multi;
}

private double lerp(double start, double end, double t) {
    return start + (end - start) * t;
}



@Override
public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_D|| e.getKeyCode() == KeyEvent.VK_RIGHT) { // Check if the "D" key is pressed
        right = true;
    } else if (e.getKeyCode() == KeyEvent.VK_A|| e.getKeyCode() == KeyEvent.VK_LEFT) { // Check if the "A" key is pressed
        left = true;
    } else if ((e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) && grounded) { // Check if the "W" key is pressed and the ball is on the ground
        up = true;
        grounded= false; // The player is no longer on the ground
    } else if (e.getKeyCode() == KeyEvent.VK_S|| e.getKeyCode() == KeyEvent.VK_DOWN) { // Check if the "S" key is pressed
        down = true;
    }
}

@Override
public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_D|| e.getKeyCode() == KeyEvent.VK_RIGHT) {
        right = false;
        playerAction = MOVING_RIGHT; //Adjusts the playerAction depending on the action (in this case we're moving)
    } else if (e.getKeyCode() == KeyEvent.VK_A|| e.getKeyCode() == KeyEvent.VK_LEFT) {
        left = false;
        playerAction = MOVING_LEFT;
    } else if ((e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) /*&& grounded*/){
        up = false;
        playerAction = facingRight ? JUMPING_RIGHT : JUMPING_LEFT;
    } else if (e.getKeyCode() == KeyEvent.VK_S|| e.getKeyCode() == KeyEvent.VK_DOWN) {
        down = false;
        //playerAction
    }
    if (!right && !left) {
    	playerAction = facingRight ? IDLE_RIGHT : IDLE_LEFT;  //Sets the idle animation
    }
}


private void movePlayer() {
	//checks if the player is out of bounds or detects an enemy
    if (forceRespawn) {
        x = playerRespawnX;
        y = playerRespawnY;
        xVelocity = 0;
        yVelocity = 0;
        grounded = true;
        player.setLocation(x, y);
        forceRespawn = false;
        return;
    }
    
    // Horizontal movement
	if (right) {
	    xVelocity += VELOCITY; // Move right	
	    if (!facingRight) {
	        facingRight = true;
	    }
	}
	if (left) {
	    xVelocity -= VELOCITY;  // Move left
	    if (facingRight) {
	        facingRight = false;
	    }
	}

   
    x = lerp(player.getX(), x, .7);
    xVelocity = Math.max(-MAX_HORIZONTAL_SPEED, Math.min(xVelocity, MAX_HORIZONTAL_SPEED));
    
    if (!right && !left) {
        xVelocity = lerp(xVelocity, 0, 0.1); // Gradually slows velocity to 0
        if (Math.abs(xVelocity) < 0.1) { // Stop completely when velocity is negligible
            xVelocity = 0;
        }
    }
    // Apply gravity
    if (!grounded) {
        yVelocity += GRAVITY;// Gravity accelerates downward
       if(down) yVelocity += VELOCITY/2;
        yVelocity = Math.min(yVelocity, TERMINAL_VELOCITY); // Cap falling speed
    }
    // Jumping
    if (up && grounded) {
        yVelocity = JUMPFORCE*jumpMulti; // Apply an upward force
        grounded = false; // Player is now airborne
    }

    // Update vertical position
    y += yVelocity;
    //Update Horizontal position
    x += xVelocity;
    // Ground collision detection
    
    if (y >= MainApplication.WINDOW_HEIGHT - PLAYER_SIZE) { // Check if player hits the ground
        y = MainApplication.WINDOW_HEIGHT - PLAYER_SIZE; // Snap player to ground level
        grounded = true; // Player is now on the ground
        yVelocity = 0; // Stop vertical movement
        }

    // Ensure the player stays within horizontal bounds
    x = Math.max(0, Math.min(MainApplication.WINDOW_WIDTH - PLAYER_SIZE, x));

    
    if (y >= MainApplication.WINDOW_HEIGHT - player.getHeight()) {
        y = MainApplication.WINDOW_HEIGHT - player.getHeight();
        grounded = true;
        yVelocity = 0;
    }
    
    // Update the position of the GImage
    player.setLocation(x, y);
    
    //Sets player actions
    if (!grounded) {
        playerAction = facingRight ? JUMPING_RIGHT : JUMPING_LEFT;
    } else if (xVelocity != 0) {
        playerAction = facingRight ? MOVING_RIGHT : MOVING_LEFT;
    } else {
        playerAction = facingRight ? IDLE_RIGHT : IDLE_LEFT;
    }

    updateAnimation(); //Updates the animation bases on player actions
    if (playerAction != lastPlayerAction) {
        aniIndex = 0;
        lastPlayerAction = playerAction;
    }
}
public GRectangle getHitbox() {
    double x = player.getX() + 10; // Offset for more precise hitbox placement
    double y = player.getY() + 10;
    double width = player.getWidth();  // Adjusted width
    double height = player.getHeight(); // Adjusted height

    return new GRectangle(x, y, width, height);
}


//acts like a "hitbox" in that it just returns the area of the player image.
public GRectangle getBounds() {
	
	return player.getBounds();
}

//used for respawning
public GImage playerImage() {
	return player;
}


private void checkPlayerFall() {
    if (player.getY() >= MainApplication.WINDOW_HEIGHT - 60) {
        System.out.println("Out of bounds");
        takeDamage();
        respawn();
//        updateHealthUI();
        }
}

//adjusts the player's health
public void takeDamage() {
    healthPoints--;
    System.out.println("Health: " + healthPoints); //used for testing
  //  updateHealthUI(); //updates the UI
}

//enables the player's respawn 
public void respawn() {
	forceRespawn = true;
}



public void update() {
	
    movePlayer();
    checkPlayerFall();
}

public void setGrounded(boolean b) {
this.grounded = b;
	
}

public void setyVelocity(double d) {
	this.yVelocity= d;
	
}

public double getxVelocity() {
	
	return xVelocity;
}

public double getyVelocity() {
	// TODO Auto-generated method stub
	return yVelocity;
}

public GImage getImage() {
	return player;
}

public GraphicsProgram getProgram() {
    return program;
}



}
