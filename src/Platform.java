import acm.graphics.GRect;
import acm.graphics.GRectangle;
import acm.program.GraphicsProgram;
import java.awt.Color;
import java.util.ArrayList;

public class Platform extends MainApplication {
    private int Width;//platform width
    private int Height;//platform height
    private double x;// Starting y
    private double y;//starting x
    private int speed;//speed of the platform moving
    private int Distance;// max distance the platform can move
    private PlatformTypes Type;
    private ArrayList<GRect> Platforms = new ArrayList<>(); // Manages multiple platform objects
    private GraphicsProgram program; // Reference to GraphicsProgram
    private double startX; // Starting x position
    
    public enum PlatformTypes {
        STATIC, MOVING;
    }

    // Constructor for individual platforms
    public Platform() {} //set it like this to initialise in level_0_tests
    /*
    public Platform(int width, int height, double locX, double locY, PlatformTypes type, int speed, int maxDistance) {
        this.Width = width;
        this.Height = height;
        this.x = locX;
        this.y = locY;
        this.Type = type;
        this.speed = speed;
        this.Distance = maxDistance;
        this.startX = locX;
    }
*/

    // Sets the GraphicsProgram context
    public void setProgram(GraphicsProgram program) {
        this.program = program;
    }
    
    public ArrayList<GRect> getPlatforms() {
    	return Platforms;
    }
    
    public void addPlatformsToScreen() {
        for (GRect platform : Platforms) {
            program.add(platform);
        }
    }   

    // Adds a platform to the list and spawns it
    public void addPlatform(int locX, int locY, int width, int height, PlatformTypes type, int speed, int maxDistance) {
        GRect rect = new GRect(locX, locY, width, height);
        rect.setColor(Color.BLACK);
        rect.setFilled(true);
        Platforms.add(rect); // Adds platform to the ArrayList for tracking

        // Apply attributes to MOVING platforms
        if (type == PlatformTypes.MOVING) {
            this.x = locX;
            this.speed = speed;
            this.Distance = maxDistance; // Update movement boundaries
            this.Type = type; // Update type to MOVING
        }
    }

    public void updatePlatforms() {
        for (GRect rect : Platforms) {
            if (Type == PlatformTypes.MOVING) {
                x += speed;

                // Update the platform's position
                rect.setLocation(x, rect.getY());

                // Reverse direction when reaching the relative boundaries
                if (x >= startX + Distance || x <= startX - Distance) {
                    speed = -speed; // Reverse movement
                }
            }
        }
    }
    public GRect detectPlatformCollision(GRectangle gRectangle) {
        for (GRect platform : Platforms) {
            if (gRectangle.getBounds().intersects(platform.getBounds())) {
                
                return platform;
            }
        }
        return null;
    }


    // Spawns a platform directly
    public void SpawnPlatform() {
        GRect platform = new GRect(x, y, Width, Height);
        platform.setColor(Color.BLACK);
        platform.setFilled(true);
        program.add(platform); // Adds platform to the GraphicsProgram
        Platforms.add(platform); // Keeps track of platforms
    }

	public void collision(GRectangle bounds) {
		//if(bounds.intersects(platform.getbounds)) {
			//Player.setGrounded;
		//}
		
	}

}