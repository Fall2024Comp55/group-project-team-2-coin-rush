import acm.program.GraphicsProgram;
import acm.graphics.GImage;

/**
 * The Door class represents a door that opens when the player collects
 * a required number of coins.
 */
public class Door extends GraphicsProgram {
	// door status: true means open, false means close
	 private boolean status;
	 // number of coins required to open the door
	 private int requiredCoins;
	 // GImage representing the door
	 private GImage doorImage;
	 
	 public static final int WINDOW_WIDTH = 600;
	 public static final int WINDOW_HEIGHT = 600;
	 
	 private double doorX;
	 private double doorY;
	 
	 // Image file paths
	 private String closedImage = "Media/Close.jpg";
	 private String openImage = "Media/Open.jpg";
	 
	 
	 
	 public Door(int requiredCoins, double doorX, double doorY) {
	  this.requiredCoins = requiredCoins;
	  this.doorX = doorX;
	  this.doorY = doorY;
	  this.status = false;
	  // Initialize the door image with the closed door image
	  doorImage = new GImage(closedImage, doorX, doorY);
	  
	 }
	 
	 public void init() {
		 // Initialize the GraphicsProgram window
		 setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		 requestFocus();
		 // changed the size of the door image
		 doorImage.scale(0.05);
		 // added the door image
		 add(doorImage);
	 } 
	 
	 public void run() {
		 
	 }
	 
	 public boolean checkIfplayerCanExit(int coinsCollected) {
		  if (coinsCollected >= requiredCoins) {
			// if the door is not already open, open it
			  if (!status) { 
				  openDoor();
			  }
			  return true;
		  }
		  
		  else {
			  //ensure the door remains closed if the coin count is insufficient
			  if (status) {
				  status =false;
				  doorImage.setImage(closedImage);
			  }
			  return false;
		  }
	 }
	 
	 //opens the door by setting its status to true and changing the image to the open door image
	 public void openDoor() {
		  this.status = true;
		  doorImage.setImage(openImage);
		 }
	 
	 // returns the current door status, true if the door is open, false if the door is close
	 public boolean isOpen() {
	  return status;
	 }
	 
	 // set the door's position and updates the door image's location
	 public void setDoorPosition(double x, double y) {
	  this.doorX = x;
	  this.doorY =  y;
	  doorImage.setLocation(x, y);
	 }
	 
	 //return the current position of the door
	 public double[] getDoorPosition() {
	  return new double[] {doorX, doorY};
	 }
	 
	 // set the number of the coins required to open the door
	 public void setRequiredCoins(int coinCount) {
	  this.requiredCoins = coinCount;
	 }
	 
	 // returns the number of the coins required to open the door
	 public int serRequiredCoins() {
	  return this.requiredCoins;
	 }
	 
	 // create a door that requires 3 coins to open the door and positions it at (550,  500)
	 public static void main(String[] args) {
		 new Door(3, 550, 500).start();
	 }

}
