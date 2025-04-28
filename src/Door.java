import acm.program.GraphicsProgram;
import acm.graphics.GImage;

public class Door {
    private boolean status;
    private int requiredCoins;
    private GImage doorImage;

    private static final String CLOSED_IMAGE = "Media/Close.jpg";
    private static final String OPEN_IMAGE = "Media/Open.jpg";

    private GraphicsProgram program;
    private double doorX, doorY;

    public Door(int requiredCoins, double doorX, double doorY) {
        this.requiredCoins = requiredCoins;
        this.doorX = doorX;
        this.doorY = doorY;
        this.status = false;
        doorImage = new GImage(CLOSED_IMAGE, doorX, doorY);
    }

    public void setProgram(GraphicsProgram program) {
        this.program = program;
    }

    public void init() {
        program.requestFocus();
        doorImage.scale(0.05);
        program.add(doorImage);
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
                doorImage.setImage(CLOSED_IMAGE);
            }
            return false;
        }
    }

    public void openDoor() {
        status = true;
        doorImage.setImage(OPEN_IMAGE);
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
        return new double[] { doorX, doorY };
    }

    public void setRequiredCoins(int coinCount) {
        this.requiredCoins = coinCount;
    }

    public int getRequiredCoins() {  // fixed typo: was serRequiredCoins()
        return requiredCoins;
    }

    public void update(hitBox playerHitbox, int coinsCollected) {
        doorCollision(playerHitbox);
        checkIfplayerCanExit(coinsCollected);
    }

    private void doorCollision(hitBox playerHitbox) {
        if (playerHitbox.intersects(doorImage.getBounds())) {
            // You can add an effect later (sound, transition, etc)
        }
    }

    public boolean playerTouchingDoor(hitBox playerHitbox) {
        return playerHitbox.getBounds().intersects(doorImage.getBounds()) && status;
    }

    public String getClosedImage() {
        return CLOSED_IMAGE;
    }

    public String getOpenImage() {
        return OPEN_IMAGE;
    }

    public GImage getDoorImage() {
        return doorImage;
    }
}
