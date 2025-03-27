import java.awt.Color;
import java.util.ArrayList;

import javax.swing.Timer;

import acm.graphics.*;
import acm.program.GraphicsProgram;
import acm.util.Platform;

public class Enemy extends GraphicsProgram{
	public static int RECT_X = 40;
	public static int RECT_Y = 40;
	public static int WINDOW_HEIGHT = 600; 
	public static int WINDOW_WIDTH = 600; 
	public static int X_VELOCITY = 5; 
	public static int NUM_ENEMIES = 3;
	public static int NUM_PLATFORMS = 3; 
	public static int Y_VAL = 100; 
	ArrayList<GRect> enemies; 
   ArrayList<Integer> Xvelocity;
  ArrayList<GRect> platforms; 
  //Comment
  
  private GOval player;
  private int healthPoint = 3;
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
    
    
    public static void main(String[] args) {
        Enemy enemies = new Enemy();
        enemies.start();
	}
}
