import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.Timer;
import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.program.GraphicsProgram;


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
private GOval player; // The player's graphical representation
public static final int PLAYER_SIZE = 30; // Diameter of the player
private double yVelocity = 0;// current upwards or falling speed of the player
private double xVelocity = 0;// not used right now
private static final double MAX_HORIZONTAL_SPEED = 10.0; // Terminal Horizontal Velocity 



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
        grounded= false; // The ball is no longer on the ground
    } else if (e.getKeyCode() == KeyEvent.VK_S|| e.getKeyCode() == KeyEvent.VK_DOWN) { // Check if the "S" key is pressed
        down = true;
    }
}

@Override
public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_D|| e.getKeyCode() == KeyEvent.VK_RIGHT) {
        right = false;
    } else if (e.getKeyCode() == KeyEvent.VK_A|| e.getKeyCode() == KeyEvent.VK_LEFT) {
        left = false;
    } else if ((e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) /*&& grounded*/){
        up = false;
    } else if (e.getKeyCode() == KeyEvent.VK_S|| e.getKeyCode() == KeyEvent.VK_DOWN) {
        down = false;
    }
}

@Override
public void keyTyped(KeyEvent e) {
    // Optional: Add behavior for key typing if needed
}

@Override
public void run() {
    addKeyListeners(); // Enable key input

    // Initialize player's position in the center of the window
    x = (MainApplication.WINDOW_WIDTH - PLAYER_SIZE) / 2.0;
    y = (MainApplication.WINDOW_HEIGHT - PLAYER_SIZE) / 2.0;

    // Create and add the player GOval to the canvas
    player = new GOval(x, y, PLAYER_SIZE, PLAYER_SIZE);
    player.setColor(Color.GREEN);
    player.setFilled(true);
    add(player);
    grounded = true;
    // Main game loop
    while (true) {
        movePlayer(); // Update player position
        pause(16.66); // Control frame rate
        System.out.println(xVelocity+ " " + yVelocity);
    }
}

private void movePlayer() {
    // Horizontal movement
	
    if (right) {
    	xVelocity += VELOCITY; // Move right
    	
    }
    	
    if (left) {
    	xVelocity -= VELOCITY;  // Move left
    	
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

    // Update the position of the GOval
    player.setLocation(x, y);
}


public void init() {
	setSize(MainApplication.WINDOW_WIDTH,MainApplication.WINDOW_HEIGHT);
	}
/*public static void main(String[] args) {
    // Start the GraphicsProgram
    new Player().start();
}
*/


}
