import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import acm.graphics.GImage;


public class Player extends  MainApplication {
	
private int HealthPoints;// is the value for the health points of the player
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

private static final int TEMP_WINDOW_SIZE = 400;
//changed 'MainApplication.WINDOW_WIDTH' and 'MainApplication.WINDOW_HEIGHT' to TEMP_WINDOW_SIZE for now because it doesnt fit my screen

private ArrayList<GImage> idleAni, movingAni, jumpingAni; //Stores the sprite images
private int playerAction = IDLE; //Default animation
private int aniTickSpeed = 5; //Adjust to change speed of animation speed
private int aniIndex = 0; //Determines which frame of the animation to display
private int aniTick = 0; //Acts as a timer for controlling the animation speed
private int playerSizeX = 64;
private int playerSizeY = 40;
private boolean facingRight = true; // Tracks the direction the player is facing

private static final int IDLE = 0, MOVING = 1, JUMPING = 2; //Adjust to add more player actions

//Load the images automatically in the appropriate GImage lists
public Player() {
   loadAnimations();
}

//Makes use of a function to load images from my sprite sub folders of Media folder to their respective lists 
private void loadAnimations() {
    idleAni = loadImagesFromFolder("Media/Sprite_IDLE");
    movingAni = loadImagesFromFolder("Media/Sprite_MOVING");
    jumpingAni = loadImagesFromFolder("Media/Sprite_JUMPING");
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

//Used for testing if the "loadImaesFromFolder" function works [might delete later]
private void printGImageList(ArrayList<GImage> list) {		
	int x = 20;
	int y = 20;

	for (GImage peep : list) {
		peep.setLocation(x, y);
		add(peep);
		x = x + 40;
	}
	return;
}

//Cycles through animation frames based on the player's action (idle(0), moving(1), jumping(2)).
//When aniTick reaches aniTickSpeed, it resets to 0, and aniIndex is incremented.
private void updateAnimation() {
    aniTick++;
    if (aniTick >= aniTickSpeed) {
        aniTick = 0;
        aniIndex++;
        List<GImage> currentAni = switch (playerAction) {
            case MOVING -> movingAni;
            case JUMPING -> jumpingAni;
            default -> idleAni;
        };
        if (aniIndex >= currentAni.size()) aniIndex = 0;
        player.setImage(currentAni.get(aniIndex).getImage());
    }
}


//Replaces all current animation frame images with their horizontally flipped versions.
//This is used to flip the character's direction when turning left or right.
private void flipArrayList() {
 ArrayList<GImage> flippedIdle = new ArrayList<>();
 ArrayList<GImage> flippedMoving = new ArrayList<>();
 ArrayList<GImage> flippedJumping = new ArrayList<>();

 for (GImage image : idleAni) {
     flippedIdle.add(flipGImageHorizontally(image));
 }

 for (GImage image : movingAni) {
     flippedMoving.add(flipGImageHorizontally(image));
 }

 for (GImage image : jumpingAni) {
     flippedJumping.add(flipGImageHorizontally(image));
     
  // Reset the player image after flipping so it points to the new frame list
     player.setImage(idleAni.get(aniIndex % idleAni.size()).getImage());
 }

 // Replace the current animation frame lists with the flipped versions
 idleAni = flippedIdle;
 movingAni = flippedMoving;
 jumpingAni = flippedJumping;
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
		return HealthPoints; 
		//gets the health points of the player
}

private void setHP(int HP){
	this.HealthPoints = HP;
}

public double getX() {
	return x;
}

private void setX(double X) {
	this.x= X;
}

public double getY() {
	return y;
}

private void setY(double Y) {
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
        playerAction = 1; //Adjusts the playerAction depending on the action (in this case we're moving)
    } else if (e.getKeyCode() == KeyEvent.VK_A|| e.getKeyCode() == KeyEvent.VK_LEFT) {
        left = false;
        playerAction = 1;
    } else if ((e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) /*&& grounded*/){
        up = false;
        playerAction = 2;
    } else if (e.getKeyCode() == KeyEvent.VK_S|| e.getKeyCode() == KeyEvent.VK_DOWN) {
        down = false;
        //playerAction
    }
    if (!right && !left) playerAction = 0; //Sets the idle animation
}

@Override
public void keyTyped(KeyEvent e) {
    // Optional: Add behavior for key typing if needed
}


private void movePlayer() {
    // Horizontal movement
	if (right) {
	    xVelocity += VELOCITY; // Move right	
	    if (!facingRight) {
	        flipArrayList(); // Only flip if changing direction
	        facingRight = true;
	    }
	}
	if (left) {
	    xVelocity -= VELOCITY;  // Move left
	    if (facingRight) {
	        flipArrayList(); // Only flip if changing direction
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
    if (y >= TEMP_WINDOW_SIZE - PLAYER_SIZE) { // Check if player hits the ground
        y = TEMP_WINDOW_SIZE - PLAYER_SIZE; // Snap player to ground level
        grounded = true; // Player is now on the ground
        yVelocity = 0; // Stop vertical movement
        }

    // Ensure the player stays within horizontal bounds
    x = Math.max(0, Math.min(TEMP_WINDOW_SIZE - PLAYER_SIZE, x));

    
    if (y >= TEMP_WINDOW_SIZE - player.getHeight()) {
        y = TEMP_WINDOW_SIZE - player.getHeight();
        grounded = true;
        yVelocity = 0;
    }
    // Update the position of the GImage
    player.setLocation(x, y);
    
    //Sets player actions
    if (xVelocity != 0) {
        playerAction = MOVING;
    } else {
        playerAction = IDLE;
    }
    if (!grounded) playerAction = JUMPING;
    updateAnimation(); //Updates the animation bases on player actions
}


@Override
public void run() {
    addKeyListeners(); // Enable key input

    // Initialize player's position in the center of the window
    x = (TEMP_WINDOW_SIZE - PLAYER_SIZE) / 2.0;
    y = (TEMP_WINDOW_SIZE - PLAYER_SIZE) / 2.0;

    //The player
    player = idleAni.get(0); //Sets the idle animation    

    player.setLocation(200, 200);
    add(player);
    grounded = true;
    
  // idleAni = loadImagesFromFolder("Media/Sprite_IDLE");
  // printGImageList(idleAni);
    
    System.out.println("Player size: X" + player.getWidth() + " Y" + player.getHeight());
    // Main game loop
    while (true) {
        movePlayer(); // Update player position
        pause(16.66); // Control frame rate
       // System.out.println(xVelocity+ " " + yVelocity);
    }
}

public void init() {
	setSize(TEMP_WINDOW_SIZE,TEMP_WINDOW_SIZE);
	}
public static void main(String[] args) {
    // Start the GraphicsProgram
    new Player().start();
}



}
