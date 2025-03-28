import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.*;
import acm.program.GraphicsProgram;
import acm.util.Platform;

public class Enemy extends GraphicsProgram{
	public static int RECT_X = 30;
	public static int RECT_Y = 30;
	public static int WINDOW_HEIGHT = 600; 
	public static int WINDOW_WIDTH = 600; 
	public static int X_VELOCITY = 3; 
	public static int NUM_ENEMIES = 3;
	public static int NUM_PLATFORMS = 3; 
	public static int Y_VAL = 100; 
	private GOval player;
	
	
	private Timer timer;
	
	ArrayList<GRect> enemies; 
   ArrayList<Integer> Xvelocity;
  ArrayList<GRect> platforms; 
    
  //player movement variables
  private double playerVelocityX = 0;
  private double playerVelocityY = 0; 
  
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
       	GRect enemy = enemies.get(i);
       	
//ensures the enemy  doesn't go out of window or platform
		if(enemies.get(i).getX() <= platforms.get(i).getX() || 
				enemies.get(i).getX() + RECT_X >= platforms.get(i).getX() + platforms.get(i).getWidth()) {
				 System.out.println(i);
		      Xvelocity.set(i, -velocity); 
		}	
	}
}
       	public void collisionCheck() {
       	 for (int i=0;i<enemies.size();++i) {
       	     GRect enemy = enemies.get(i);
 GObject collision = getElementAt(enemy.getX()+ enemy.getWidth() , enemy.getY());
       		if(collision instanceof GOval) {
       			remove(enemies.get(i));
       			enemies.remove(i);
       			System.out.println("removed " + i + " " + collision);
           		}
       		}
       	}
       	
// creates temporary platforms
       	
	@Override
	public void run() {
		addKeyListeners();
    	timer = new Timer(50, this); //50 = milliseconds
        timer.start();

			//generates a player
				player = new GOval(50, 50, 30, 30); //spawn location (x, y, width, height)
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
	
		enemies = new ArrayList<GRect>();
		Xvelocity = new ArrayList<Integer>();
		createEnemy();
		spawnEnemies();
		
		while(true) {
			 enemyMovement();
			 pause(30);
		}
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
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //move the player based on the velocity
        double newX = player.getX() + playerVelocityX;
        double newY = player.getY() + playerVelocityY;
        player.setLocation(newX, newY);
        collisionCheck();
       
        
        }


    
    public static void main(String[] args) {
        Enemy enemies = new Enemy();
        enemies.start();
	}
}
