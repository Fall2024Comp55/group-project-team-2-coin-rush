import acm.program.GraphicsProgram;
import acm.graphics.GImage;

/**
 * The Door class represents a door that opens when the player collects
 * a required number of coins.
 */
public class Door extends GraphicsProgram {
	private boolean status;
	 private int requiredCoins;
	 private GImage doorImage;
	 
	 public static final int WINDOW_WIDTH = 600;
	 public static final int WINDOW_HEIGHT = 600;
	 
	 private double doorX;
	 private double doorY;
	 
	 private String closedImage = "Media/Close.jpg";
	 private String openImage = "Media/Open.jpg";
	 
	 
	 
	 public Door(int requiredCoins, double doorX, double doorY) {
	  this.requiredCoins = requiredCoins;
	  this.doorX = doorX;
	  this.doorY = doorY;
	  this.status = false;
	  doorImage = new GImage(closedImage, doorX, doorY);
	  
	 }
	 
	 public void init() {
		 setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		 requestFocus();
		 add(doorImage);
	 } 
	 
	 public void run() {
		 
	 }
	 
	 public boolean checkIfplayerCanExit(int coinsCollected) {
		  if (coinsCollected >= requiredCoins) {
			  if (!status) {
				  openDoor();
			  }
			  return true;
		  }
		  
		  else {
			  if (status) {
				  status =false;
				  doorImage.setImage(closedImage);
			  }
			  return false;
		  }
	 }
	 
	 public void openDoor() {
		  this.status = true;
		  doorImage.setImage(openImage);
		 }
	 
	 
	 public boolean isOpen() {
	  return status;
	 }
	 
	 
	 public void setDoorPosition(double x, double y) {
	  this.doorX = x;
	  this.doorY =  y;
	  doorImage.setLocation(x, y);
	 }
	 
	 public double[] getDoorPosition() {
	  return new double[] {doorX, doorY};
	 }
	 
	 public void setRequiredCoins(int coinCount) {
	  this.requiredCoins = coinCount;
	 }
	 
	 public int serRequiredCoins() {
	  return this.requiredCoins;
	 }
	 
	 public static void main(String[] args) {
		 new Door(3, 50, 100).start();
	 }

}
