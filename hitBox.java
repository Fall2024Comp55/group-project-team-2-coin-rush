import acm.graphics.GRect;
import acm.graphics.GRectangle;
import acm.program.GraphicsProgram;

public class hitBox extends MainApplication {
	 private GraphicsProgram program;
    private GRect hitbox;
    public void setProgram(GraphicsProgram program) {
        this.program = program;
       
    }
    // Initialize the hitbox
    public void createHitbox(double playerX, double playerY, double playerWidth, double playerHeight, double offsetX, double offsetY) {
        double hitboxWidth = playerWidth - 40; // Adjust width
        double hitboxHeight = playerHeight - 10; // Adjust height
        hitbox = new GRect(playerX + offsetX, playerY + offsetY, hitboxWidth, hitboxHeight);
        hitbox.setColor(java.awt.Color.RED);
        hitbox.setFilled(false); // Debug outline
    }
   
    // Update the hitbox position based on player's updated location
    public void updateHitbox(double playerX, double playerY, double offsetX, double offsetY) {
        hitbox.setLocation(playerX + offsetX, playerY + offsetY);
    }

    // Get the hitbox for collision checks
    public GRect getHitbox() {
        return hitbox;
    }
	public double getHeight() {
		
		return hitbox.getHeight();
	}
	public GRectangle getBounds() {
		// TODO Auto-generated method stub
		return hitbox.getBounds();
	}
	public double getY() {
		// TODO Auto-generated method stub
		return hitbox.getY();
	}
	public double getWidth() {
		// TODO Auto-generated method stub
		return hitbox.getWidth();
	}
	public double getX() {
		// TODO Auto-generated method stub
		return hitbox.getX();
	}
	public boolean intersects(GRectangle enemyBounds) {
	    return hitbox.getBounds().intersects(enemyBounds.getBounds());
	}
	

	
}