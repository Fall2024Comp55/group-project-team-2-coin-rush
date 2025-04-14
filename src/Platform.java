import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import java.awt.Color;
import java.util.ArrayList;

public class Platform extends MainApplication {
    private int Width;
    private int Height;
    private double x;
    private double y;
    private int speed;
    private int Distance;
    private PlatformTypes Type;
    private ArrayList<GRect> Platforms = new ArrayList<>(); // Manages multiple platform objects
    private GraphicsProgram program; // Reference to GraphicsProgram
    private double startX; // Starting x position
    
    public enum PlatformTypes {
        STATIC, MOVING;
    }

    // Constructor for individual platforms
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

    // Sets the GraphicsProgram context
    public void setProgram(GraphicsProgram program) {
        this.program = program;
    }

    // Adds a platform to the list and spawns it
    public void addPlatform(double locX, double locY, int width, int height, PlatformTypes type, int speed, int maxDistance) {
        GRect rect = new GRect(locX, locY, width, height);
        rect.setColor(Color.RED);
        rect.setFilled(true);
        program.add(rect);
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

    // Spawns a platform directly
    public void SpawnPlatform() {
        GRect platform = new GRect(x, y, Width, Height);
        platform.setColor(Color.RED);
        platform.setFilled(true);
        program.add(platform); // Adds platform to the GraphicsProgram
        Platforms.add(platform); // Keeps track of platforms
    }
}