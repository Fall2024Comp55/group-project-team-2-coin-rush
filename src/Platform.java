import acm.graphics.GRect;
import acm.graphics.GRectangle;
import acm.program.GraphicsProgram;
import java.awt.Color;
import java.util.ArrayList;

public class Platform extends MainApplication {
    private int Width; // Platform width
    private int Height; // Platform height
    private double x; // Current x position
    private double y; // Current y position
    private double speed; // Movement speed for MOVING platforms
    private int Distance; // Maximum distance the platform can move
    private PlatformTypes Type; // Type of the platform (STATIC or MOVING)
    private ArrayList<GRect> Platforms = new ArrayList<>(); // List of platform objects
    public ArrayList<PlatformTypes> PlatformTypesList = new ArrayList<>(); // List to track each platform's type
    private ArrayList<Double> PlatformSpeeds = new ArrayList<>(); // List to track each platform's speed
    private ArrayList<Integer> PlatformDistances = new ArrayList<>(); // List to track movement boundaries
    private ArrayList<Double> PlatformStartX = new ArrayList<>(); // Starting x position for each platform
    private ArrayList<Double> PlatformX = new ArrayList<>(); // Current x position for each platform
    private ArrayList<Double> PlatformStartY = new ArrayList<>(); // Starting y position for each platform
    private ArrayList<Double> PlatformY = new ArrayList<>(); // Current y position for each platform
    private ArrayList<Boolean> PlatformVerticality = new ArrayList<>(); // Tracks if platform moves vertically
    private GraphicsProgram program; // Reference to GraphicsProgram

    public enum PlatformTypes {
        STATIC, MOVING;
    }

    public void setProgram(GraphicsProgram program) {
        this.program = program;
    }

    public ArrayList<GRect> getPlatforms() {
        return Platforms;
    }

    public void addPlatformsToScreen() {
        for (GRect rect : Platforms) {
            program.add(rect);
        }
    }

    // Adds a platform to the list and spawns it
    public void addPlatform(int locX, int locY, int width, int height, PlatformTypes type, double speed, int maxDistance, boolean isVert) {
        GRect rect = new GRect(locX, locY, width, height);
        rect.setColor(Color.red);
        rect.setFilled(true);
        Platforms.add(rect);
        PlatformTypesList.add(type);
        PlatformSpeeds.add(speed);
        PlatformDistances.add(maxDistance);
        PlatformVerticality.add(isVert); // Add vertical movement setting

        // Track individual X and Y positions for MOVING platforms
        PlatformX.add((double) locX);
        PlatformStartX.add((double) locX);
        PlatformY.add((double) locY);
        PlatformStartY.add((double) locY);
    }

    public void updatePlatforms() {
        for (int i = 0; i < Platforms.size(); i++) {
            GRect rect = Platforms.get(i); // Get the current platform
            PlatformTypes type = PlatformTypesList.get(i); // STATIC or MOVING
            double platformSpeed = PlatformSpeeds.get(i); // Speed for the platform
            int maxDistance = PlatformDistances.get(i); // Maximum movement distance (positive or negative)
            boolean isVert = PlatformVerticality.get(i); // True = vertical movement
            double currentX = PlatformX.get(i); // Current X position
            double startX = PlatformStartX.get(i); // Starting X position
            double currentY = PlatformY.get(i); // Current Y position
            double startY = PlatformStartY.get(i); // Starting Y position

            if (type == PlatformTypes.MOVING && platformSpeed != 0 && maxDistance != 0) {
                if (isVert) {
                    // Vertical movement (up and down)
                    currentY += platformSpeed; // Adjust Y position
                    if (maxDistance > 0) {
                        // Positive maxDistance: Move downward first
                        if (currentY > startY + maxDistance) {
                            currentY = startY + maxDistance; 
                            PlatformSpeeds.set(i, -Math.abs(platformSpeed)); // Reverse upward
                        } else if (currentY < startY) {
                            currentY = startY; 
                            PlatformSpeeds.set(i, Math.abs(platformSpeed)); // Reverse downward
                        }
                    } else {
                        // Negative maxDistance: Start by moving upward
                        if (currentY < startY + maxDistance) {
                            currentY = startY + maxDistance; 
                            PlatformSpeeds.set(i, Math.abs(platformSpeed)); // Reverse downward
                        } else if (currentY > startY) {
                            currentY = startY; 
                            PlatformSpeeds.set(i, -Math.abs(platformSpeed)); // Reverse upward
                        }
                    }
                } else {
                    // Horizontal movement (left and right)
                    currentX += platformSpeed; // Adjust X position
                    if (maxDistance > 0) {
                        // Positive maxDistance: Move right first
                        if (currentX > startX + maxDistance) {
                            currentX = startX + maxDistance; // Clamp to max distance
                            PlatformSpeeds.set(i, -Math.abs(platformSpeed)); // Reverse leftward
                        } else if (currentX < startX) {
                            currentX = startX; // Clamp to starting position
                            PlatformSpeeds.set(i, Math.abs(platformSpeed)); // Reverse rightward
                        }
                    } else {
                        // Negative maxDistance: Start by moving left
                        if (currentX < startX + maxDistance) {
                            currentX = startX + maxDistance; // Clamp to max distance (negative)
                            PlatformSpeeds.set(i, Math.abs(platformSpeed)); // Reverse rightward
                        } else if (currentX > startX) {
                            currentX = startX; // Clamp to starting position
                            PlatformSpeeds.set(i, -Math.abs(platformSpeed)); // Reverse leftward
                        }
                    }
                }

                // Update position in lists
                PlatformX.set(i, currentX);
                PlatformY.set(i, currentY);
                rect.setLocation(currentX, currentY); // Move the graphical object
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

    public void SpawnPlatform() {
        GRect platform = new GRect(x, y, Width, Height);
        platform.setColor(Color.BLACK);
        platform.setFilled(true);
        program.add(platform); // Adds platform to the GraphicsProgram
        Platforms.add(platform); // Keeps track of platforms
    }

    public void collision(GRectangle gRectangle) {

    }
}