import acm.graphics.*;
import acm.program.*;

public class Enemy {
public static final int xVelocity = 2; 
public static int WINDOW_HEIGHT = 600; //height of window
public static int WINDOW_WIDTH = 600;
public static int HEIGHT = 30;
double x;
public double y; 
Enemy enemy;
public int count =0; 

	public Enemy(double x2, double y2) {
		this.x = x2;
		this.y = y2; 
	 }
	
	public void addEnemy() {
		 enemy = new Enemy(x,y);
		 
	}
	
	public void enemyMovement() {
	
		count++;
       	if(count > 0) {
       		x += xVelocity;
       	}
       	else if(count < 0) {
       		x -= xVelocity; 
       	}
       		return;
    }  

	public static void main(String[] args) {
//		Enemy enemy = new Enemy(600,500); 
//		System.out.println("added enemy");
//		enemy.addEnemy();

	}

	public double getHeight() {
		// TODO Auto-generated method stub
		return HEIGHT;
	}


}
