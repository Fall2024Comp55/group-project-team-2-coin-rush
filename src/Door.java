import acm.graphics.GImage;

public class Door {
	private boolean status;
	 private int requiredCoins;
	 private GImage doorImage;
	 
	 public static final double DOOR_WIDTH_SIZE = 50;
	 public static final double DOOR_HEIGHT_SIZE = 100;
	 
	 private double doorX;
	 private double doorY;
	 
	 public Door(int requiredCoins, double doorX, double doorY, GImage doorImage) {
	  this.requiredCoins = requiredCoins;
	  this.doorX = doorX;
	  this.doorY = doorY;
	  this.doorImage = doorImage;
	  this.status = false;
	  
	 }
	 
	 public boolean isOpen() {
	  return status;
	 }
	 
	 public void openDoor() {
	  this.status = true;
	 }
	 
	 public boolean checkIfplayerCanExit(int coinsCollected) {
	  if (coinsCollected >= requiredCoins) {
	   openDoor();
	   return true;
	  }
	  
	  return false;
	 }
	 
	 public void setDoorPosition(double x, double y) {
	  this.doorX = x;
	  this.doorY =  y;
	 }
	 
	 public double[] getDoorPosition() {
	  return new double[] {doorX,doorY};
	 }
	 
	 public void setRequiredCoins(int coinCount) {
	  this.requiredCoins = coinCount;
	 }
	 
	 public int serRequiredCoins() {
	  return this.requiredCoins;
	 }


}
