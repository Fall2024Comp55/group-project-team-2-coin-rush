import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;
import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

/* CHANGES/ToDos
 * I've simplified some of the code.
 * I began to create a constructor. This constructor can pretty much automatically handles much of the data assigning (which I think is the way to do it?)
 * I am trying to think in terms of how other classes my interact with this one
 * 
 * SUGGESTIONS
 * 1) Make a grid and spawn floating coins relative to a cell?
 * 2) Make floating coins spawn to a mouse click and save position?
 * 3) Drag the floating coins to a fixed position?
 * Thank you for reading lol
 */

public class testCoin extends GraphicsProgram {
	public static final int COIN_SIZE = 20; //default coin size

    ArrayList<GOval> coinsOnPlatforms; //list of coins on platforms
    ArrayList<GOval> coinsOnFloat; //list of coins floating within the level
    private ArrayList<GRect> platforms; //used for testing

    private GOval coin;
    private GOval player;

	private int coinCount; // might not need
	private int coinsForPlatforms; // Coins specifically for platforms
	private int coinsForFloat; // Coins that are not tied to platforms but float in level
	private int coinsCollected = 0;
	private int numPlatforms; // might not need
	
	private GLabel coinsCollectedText;
	private GLabel coinsRemainingText;
	
	private Timer timer;
    
    // Player movement variables
    private double playerVelocityX = 0;
    private double playerVelocityY = 0;
    
    
    // Constructor to assign data
    public testCoin(int coinsForFloat, int coinsForPlatforms, int numPlatforms) {
        this.coinsOnFloat = new ArrayList<>();
        this.coinsOnPlatforms = new ArrayList<>();
        this.platforms = new ArrayList<>();
        this.coinsForFloat = coinsForFloat;
        this.coinsForPlatforms = coinsForPlatforms;
        this.numPlatforms = numPlatforms; // Might change this later to just take an array of GRect platforms and uses to assign coins to said platforms
        
        coinsOnFloat = generateCoins(coinsForFloat); // Automatically assigns the number of coins to be made and added to list
        coinsOnPlatforms = generateCoins(coinsForPlatforms); // Automatically assigns the number of coins to be made and added to list
        platforms = generateRandomPlatforms(numPlatforms); // Automatically assigns the number of platforms to be made and added to list
        }

	
    
    
    public void run() {
        // Create and Adds player to the screen
    	 player = new GOval(50, 50, 30, 30);
         player.setColor(Color.RED);
         player.setFilled(true);
         add(player);

         addPlatformsToScreen(); // Adds platforms to screen
         spawnCoinsToPlatforms(coinsOnFloat, platforms); //Sets a list of coin's location to fixed location of platform, centered
         addCoinsToScreen(); // Adds all coins to screen
         
         // Temporary labels to track coin collection within this class
         coinsCollectedText = new GLabel("Coins Collected: " + coinsCollected, 5, 15);
         add(coinsCollectedText);
         coinsRemainingText = new GLabel("Coins Remaining: " + (coinsOnFloat.size() + coinsOnPlatforms.size()), 5, 30);
         add(coinsRemainingText);
         
         
         addKeyListeners();
         timer = new Timer(30, this); // Used for movement and collision detection
         timer.start();
    }

	

	
	// Creates a single coin
    private GOval createCoin() {
    	coin = new GOval(COIN_SIZE, COIN_SIZE);
    	coin.setFillColor(Color.YELLOW);
    	coin.setFilled(true);
    	return coin;
    	}
    
    
    
    // Adds GOval coins for platforms to a list of coins that go on platforms
    private void addToCoinsOnPlatformList(GOval coin) {
    	this.coinsOnPlatforms.add(coin);
    	}
    // Adds GOval coins that are unassigned to any platform to a list of free roam floating list of coins 
    private void addToCoinsInLevelList(GOval coin) {
    	this.coinsOnFloat.add(coin);
    }
    
    // Might not need these two functions
    private void addCoinsForPlatforms(int number) {
    	this.coinsForPlatforms = number;
    }
    private void addCoinsForFloat(int number) {
    	this.coinsForFloat = number;
    }
    
    
    // Uses createCoin() and a given integer to generate a list of coins and return said list
    private ArrayList<GOval> generateCoins(int numCoins) {
        ArrayList<GOval> coins = new ArrayList<>();
        for (int i = 0; i < numCoins; i++) {
            Random rand = new Random();
            int width = rand.nextInt(150) + 50; // Platform width between 50-200
            int x = rand.nextInt(MainApplication.WINDOW_WIDTH - width); // Ensure it stays inside the window
            int y = rand.nextInt(MainApplication.WINDOW_HEIGHT - 100) + 100; // Avoid spawning too high or low
            GOval coin = createCoin();
            coin.setLocation(x, y);
            coins.add(coin);
        }
        return coins;
    }
    
    // Generates randomly sized/placed list of platforms of a fixed size within the screen. The amount created is dictated by the given integer.
    //primarily used for testing for now. Once we get platforms class going I can remove this.
    private ArrayList<GRect> generateRandomPlatforms(int numPlatforms) {
        ArrayList<GRect> platforms = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < numPlatforms; i++) {
            int width = rand.nextInt(150) + 50; // Platform width between 50-200
            int x = rand.nextInt(MainApplication.WINDOW_WIDTH - width); // Ensure it stays inside the window
            int y = rand.nextInt(MainApplication.WINDOW_HEIGHT - 100) + 100; // Avoid spawning too high or low
            GRect platform = new GRect(x, y, width, 20);
            platform.setFilled(true);
            platform.setColor(Color.DARK_GRAY);
            platforms.add(platform);
        }
        return platforms;
    }
    
    // Specifically sets the location of a list of coins to the center of platforms of a list of platforms
    private void spawnCoinsToPlatforms(ArrayList<GOval> coinList, ArrayList<GRect> platforms) {
        if (platforms.isEmpty() || coinList.isEmpty()) return; //prevents errors

        for (int i = 0; i < coinList.size(); i++) {
            GRect platform = platforms.get(i % platforms.size()); //avoid out-of-bounds
            double coinLocationX = platform.getX() + (platform.getWidth() - coinList.get(i).getWidth()) / 2; //spawns the coin centered on top of the platform
            double coinLocationY = platform.getY() - coinList.get(i).getHeight() - 5; //5px above the platform

            coinList.get(i).setLocation(coinLocationX, coinLocationY); //sets coin to location
            add(coinList.get(i)); //adds to screen
            System.out.println("Coins On Platforms " + i + " spawned at: " + coinLocationX + ", " + coinLocationY); //used just for testing.
        }
    }
    

    

    // These two functions essentially adds to screen
    private void addPlatformsToScreen() {
        for (GRect platform : platforms) {
            add(platform);
        }
    }
    
    private void addCoinsToScreen() {
        for (GOval coin : coinsOnPlatforms) {
            add(coin);
        }
        for (GOval coin : coinsOnFloat) {
            add(coin);
        }
    }

    
    
    
    //large spaces here for my own readability lol
    
    
     // Checks for player and coin collision
    private void checkCoinCollision() {
        // Check floating coins (iterate backward to avoid index errors)
        for (int i = coinsOnFloat.size() - 1; i >= 0; i--) {
            GOval coin = coinsOnFloat.get(i);
            if (player.getBounds().intersects(coin.getBounds())) {
                remove(coin); // Remove from screen
                coinsOnFloat.remove(i); // Remove from list
                coinsCollected++; // Update count
            }
        }

        // Check platform coins (iterate backward to avoid index errors)
        for (int i = coinsOnPlatforms.size() - 1; i >= 0; i--) {
            GOval coin = coinsOnPlatforms.get(i);
            if (player.getBounds().intersects(coin.getBounds())) {
                remove(coin); // Remove from screen
                coinsOnPlatforms.remove(i); // Remove from list
                coinsCollected++; // Update count
            }
        }
        updateCoinUI(); //updates UI through a method instead
    }

    //updates the UI
    private void updateCoinUI() {
        coinsCollectedText.setLabel("Coins Collected: " + coinsCollected);
        coinsRemainingText.setLabel("Coins Remaining: " + (coinsOnFloat.size() + coinsOnPlatforms.size()));
    }
    
    
    // Handles key presses for movement
    @Override
    public void keyPressed(KeyEvent e) {
        // Move the player based on key pressed
        switch (e.getKeyCode()) {
        case KeyEvent.VK_W -> playerVelocityY = -10;
        case KeyEvent.VK_A -> playerVelocityX = -10;
        case KeyEvent.VK_S -> playerVelocityY = 10;
        case KeyEvent.VK_D -> playerVelocityX = 10;
        }
        
    }
    
    // Handles key release to stop movement
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_S -> playerVelocityY = 0;
            case KeyEvent.VK_A, KeyEvent.VK_D -> playerVelocityX = 0;
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e)  {
        player.setLocation(player.getX() + playerVelocityX, player.getY() + playerVelocityY); //moves the player to new location
        checkCoinCollision(); //after each movement, check for collision
    }
    
	//generates a random number given an bound
	public static int generateRandomNumber(int bound) {
        Random random = new Random();
        return random.nextInt(bound);
    }
	
	public void init() {
		setSize(MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);
		}
	
	public static void main(String[] args) {
		new testCoin(3, 3, 5).start();
		}
}
