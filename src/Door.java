import acm.program.GraphicsProgram;
import acm.graphics.GImage;

public class Door extends GraphicsProgram {
<<<<<<< HEAD
    private boolean status;
    private int requiredCoins;
    private GImage doorImage;
=======
	// door status: true means open, false means close
	 private boolean status;
	 // number of coins required to open the door
	 private int requiredCoins;
	 // GImage representing the door
	 private GImage doorImage;
	 
	 private LevelManager manager; //used to check for next level

	 
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
      doorImage.scale(0.05);	  
	 }
	 
	// Sets the GraphicsProgram context
	    public void setProgram(LevelManager manager) {
	        this.manager = manager;
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
	 
	 //will complete once level handler is created 
	 public void doorCollision(Player player) {
		 if(isOpen() && player.getBounds().intersects(doorImage.getBounds())) {
		        manager.nextLevel();
		 }
	 }
	 
	 public String getCloseDoor() {
		 return closedImage; 
	 }
	 
	 public String getOpenDoor() {
		 return openImage; 
	 }
	 
	    public GImage getDoorImage() {
	        return doorImage;
	    }
	 
	 public void update(Player player, int coinsCollected) {
		    checkIfplayerCanExit(coinsCollected);
		    doorCollision(player);
		}
	 // create a door that requires 3 coins to open the door and positions it at (550,  500)
//	 public static void main(String[] args) {
//		 new Door(3, 550, 500).start();
//	 }
>>>>>>> refs/remotes/origin/main

    public static final int WINDOW_WIDTH = 600;
    public static final int WINDOW_HEIGHT = 600;

    private double doorX;
    private double doorY;
    private GraphicsProgram program;

    private String closedImage = "Media/Close.jpg";
    private String openImage = "Media/Open.jpg";

    public Door(int requiredCoins, double doorX, double doorY) {
        this.requiredCoins = requiredCoins;
        this.doorX = doorX;
        this.doorY = doorY;
        this.status = false;
        doorImage = new GImage(closedImage, doorX, doorY);
    }

    public void setProgram(GraphicsProgram program) {
        this.program = program;
    }

    public void init() {
        program.requestFocus();
        doorImage.scale(0.05);
        program.add(doorImage);
    }

    public void run() {
    }

    public boolean checkIfplayerCanExit(int coinsCollected) {
        if (coinsCollected >= requiredCoins) {
            if (!status) {
                openDoor();
            }
            return true;
        } else {
            if (status) {
                status = false;
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
        this.doorY = y;
        doorImage.setLocation(x, y);
    }

    public double[] getDoorPosition() {
        return new double[]{doorX, doorY};
    }

    public void setRequiredCoins(int coinCount) {
        this.requiredCoins = coinCount;
    }

    public int serRequiredCoins() {
        return this.requiredCoins;
    }

    public void doorCollision(hitBox playerHitbox) {
        if (playerHitbox.intersects(doorImage.getBounds())) {
            // You can add something here later if needed
        }
    }

    public String getCloseDoor() {
        return closedImage;
    }

    public String getOpenDoor() {
        return openImage;
    }

    public void update(hitBox playerHitbox, int coinsCollected) {
        doorCollision(playerHitbox);
        checkIfplayerCanExit(coinsCollected);
    }

    // NEW METHOD: detect if player is touching the open door
    public boolean playerTouchingDoor(hitBox playerHitbox) {
        return playerHitbox.getBounds().intersects(doorImage.getBounds()) && status;
    }
}
