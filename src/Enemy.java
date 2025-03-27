import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.Timer;
import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

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

public class Enemy extends GraphicsProgram{
	public static int RECT_X = 40;
	public static int RECT_Y = 40;
	public static int WINDOW_HEIGHT = 600; 
	public static int WINDOW_WIDTH = 600; 
	public static int X_VELOCITY = 5; 
	public static int NUM_ENEMIES = 3;
	public static int NUM_PLATFORMS = 3; 
	public static int Y_VAL = 100; 
	public static int PLAYER_SPAWN = 50;
	ArrayList<GRect> enemies; 
   ArrayList<Integer> Xvelocity;
  ArrayList<GRect> platforms; 
  //Comment
  
  private GOval player;
  private int healthPoint = 3;
  private GLabel healthText;

  // Player movement variables
  private double playerVelocityX = 0;
  private double playerVelocityY = 0;
  
  private Timer timer;
    

    
//  creates a single enemy and adds into the array
   public void createEnemy() {  
	   for(int i=0;i<NUM_ENEMIES;++i) {
		   GRect enemy=new GRect(RECT_X,RECT_Y);
    	enemy.setColor(Color.RED);
    	enemy.setFilled(true);
    	enemies.add(enemy);
         add(enemy);
         Xvelocity.add(X_VELOCITY);
	   }
   }
//    spawns the list of enemies and sets the location
   public void spawnEnemies() {
	   for(int i=0;i<enemies.size();++i) {
		   add(enemies.get(i));
		   enemies.get(0).setLocation(100, 100);
		   enemies.get(1).setLocation(400,300);
		   enemies.get(2).setLocation(50,400);
		  Y_VAL += enemies.get(i).getHeight() + 150;
	   }
   }
   
//   handles enemy movement according to the area of platform
public void enemyMovement() {
	
	for(int i=0;i<enemies.size();++i) {
		int velocity = Xvelocity.get(i);
		enemies.get(i).move(velocity, 0);
		
//ensures the enemy  doesn't go out of window or platform
if(enemies.get(i).getX() < 0 || enemies.get(i).getX() > WINDOW_WIDTH - RECT_X 
|| enemies.get(i).getX() < platforms.get(i).getX() || 
enemies.get(i).getX() > platforms.get(i).getX()+ platforms.get(i).getWidth() - RECT_X){
			Xvelocity.set(i, -Xvelocity.get(i));
			}
	}
}

// creates temporary platforms
	@Override
	public void run() {
        addKeyListeners();
		// Create and Adds player to the screen
   	 player = new GOval(PLAYER_SPAWN, PLAYER_SPAWN, 30, 30);
        player.setColor(Color.RED);
        player.setFilled(true);
        add(player);
        		
		platforms = new ArrayList<GRect>();
		for(int i=0;i<NUM_PLATFORMS; ++i) {
		GRect platform = new GRect(200, 20);
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
			Y_VAL += platforms.get(i).getHeight() + 150;
		}
		
        healthText = new GLabel("Health: " + healthPoint, 5, 30);
        add(healthText);

        
		enemies = new ArrayList<GRect>();
		Xvelocity = new ArrayList<Integer>();
		createEnemy();
		spawnEnemies();

        // I needed to use a timer instead of a while(true) loop for the player movement and collision detection
        timer = new Timer(30, this);
        timer.start();
	}
	
	
	// Checks if player collides with enemies
    public void checkPlayerCollision() {
        for (GRect enemy : enemies) {
            if (player.getBounds().intersects(enemy.getBounds())) {
                healthPoint--; // Reduce health
                System.out.println("collision detected Health: " + healthPoint); //used for testing
                respawnPlayer();
                updateHealthUI();
                
                /* maybe add a case if health reaches 0
                if (healthPoint <= 0)
                */
                break;
            }
        }
    }
    
    // Respawn player to a fixed starting position
    public void respawnPlayer() {
        player.setLocation(PLAYER_SPAWN, PLAYER_SPAWN);
    }
    
    private void updateHealthUI() {
        healthText.setLabel("Health: " + healthPoint);
    }
    
    // Took the player code from testCoin and used it here to play around with collision
    // Handles key presses for movement
    @Override
    public void keyPressed(KeyEvent e) {
        // Move the player based on key pressed
        switch (e.getKeyCode()) {
        case KeyEvent.VK_W -> playerVelocityY = -10;
        case KeyEvent.VK_A -> playerVelocityX = -10;
        case KeyEvent.VK_S -> playerVelocityY = 10;
        case KeyEvent.VK_D -> playerVelocityX = 10;
        }
        
    }
    // Handles key release to stop movement
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_S -> playerVelocityY = 0;
            case KeyEvent.VK_A, KeyEvent.VK_D -> playerVelocityX = 0;
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e)  {
        player.setLocation(player.getX() + playerVelocityX, player.getY() + playerVelocityY); // Moves the player to new location
		enemyMovement(); // With actionEvent i moved the enemy movement here instead
        checkPlayerCollision(); // After each movement, check for collision
    }
    
    public void init() {
    	setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
    }
    
    
    public static void main(String[] args) {
        Enemy enemies = new Enemy();
        enemies.start();
	}
}
