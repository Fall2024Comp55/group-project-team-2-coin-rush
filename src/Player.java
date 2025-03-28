import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.Timer;
import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.program.GraphicsProgram;


public class Player extends GraphicsProgram implements  KeyListener {
	public static final int WINDOW_SIZE= 500;
private int HealthPoints;// is the value for the health points of the player
private double x, y; // is the location of the player
public static final int VELOCITY = 5;// player movement speed
public static final int JUMPFORCE = -10;
public static final double GRAVITY = .5;
public double jumpMulti =1 ;
private final double TERMINAL_VELOCITY =50; // Maximum falling speed
private boolean right, left , up , down;// player movement booleans
private boolean grounded;//The player check if they are touching the ground
private GOval player; // The player's graphical representation
public static final int PLAYER_SIZE = 30; // Diameter of the player
private double yVelocity = 0;
private double xVelocity = 0;


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
        pause(15); // Control frame rate
    }
}

private void movePlayer() {
    // Horizontal movement
	
    if (right) x += VELOCITY; // Move right
    if (left) x -= VELOCITY;  // Move left
    x = lerp(x, player.getX(), .01);
	
    // Apply gravity
    if (!grounded) {
        yVelocity += GRAVITY; // Gravity accelerates downward
        yVelocity = Math.min(yVelocity, TERMINAL_VELOCITY); // Cap falling speed
    }

    // Jumping
    if (up && grounded) {
        yVelocity = JUMPFORCE*jumpMulti; // Apply an upward force
        grounded = false; // Player is now airborne
    }

    // Update vertical position
    y += yVelocity;

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
public static void main(String[] args) {
    // Start the GraphicsProgram
    new Player().start();
}



}
