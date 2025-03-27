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

public class testCoin extends GraphicsProgram {
	public static final int COIN_SIZE = 20; //default coin size

    ArrayList<GOval> coinsOnPlatforms; //list of coins on platforms
    ArrayList<GOval> coinsInLevel; //list of coins floating within the level

    private GOval coin;
    private GOval player;

	private int coinCount;
	private int coinsForPlatforms; //coins specifically for platforms
	private int coinsForFloat; //coins that are not tied to platforms but float in level
	private int coinsCollected = 0;
	
	private GLabel coinsCollectedText;
	private GLabel coinsRemainingText;
	
	private Timer timer;
    
    //player movement variables
    private double playerVelocityX = 0;
    private double playerVelocityY = 0;
	
    public void run() {
        // Generate player
        player = new GOval(50, 50, 30, 30); // (x, y, width, height)
        player.setColor(Color.RED);
        player.setFilled(true);
        add(player);

        // Initialize coin lists
        coinsInLevel = new ArrayList<>();
        coinsOnPlatforms = new ArrayList<>();

        // Define the number of coins
        coinsForFloat = 3;
        coinsForPlatforms = 3;
        coinCount = coinsForFloat + coinsForPlatforms;

        // Create platforms
        ArrayList<GRect> platforms = new ArrayList<>();
        /*
        GRect platform1 = new GRect(100, 300, 100, 20);
        GRect platform2 = new GRect(250, 200, 300, 20);
        GRect platform3 = new GRect(300, 150, 50, 20);
        platforms.add(platform1);
        platforms.add(platform2);
        platforms.add(platform3);
		
        // Add platforms to the screen
        for (GRect platform : platforms) {
            platform.setFilled(true);
            add(platform);
        }
		*/
        
        platforms = generateRandomPlatforms(5); // Create 5 random platforms
        
        // Generate and spawn floating coins
        for (int i = 0; i < coinsForFloat; i++) {
            GOval coin = createCoin();
            coinsInLevel.add(coin);
        }
        spawnCoinsToScreen(coinsInLevel, MainApplication.WINDOW_WIDTH, MainApplication.WINDOW_HEIGHT);

        // Generate and spawn platform coins
        for (int i = 0; i < coinsForPlatforms; i++) {
            GOval coin = createCoin();
            coinsOnPlatforms.add(coin);
        }
        spawnCoinsToPlatforms(coinsOnPlatforms, platforms);

        // Add UI elements
        coinsCollectedText = new GLabel("Coins Collected: " + coinsCollected, 5, 15);
        add(coinsCollectedText);
        coinsRemainingText = new GLabel("Coins Remaining: " + (coinsInLevel.size() + coinsOnPlatforms.size()), 5, 30);
        add(coinsRemainingText);

        // Enable keyboard input
        addKeyListeners();

        // Start the game loop timer
        timer = new Timer(30, this); // 30ms for smoother updates
        timer.start();
    }

	

	
	//this method creates a single coin
    private GOval createCoin() {
    	coin = new GOval(COIN_SIZE, COIN_SIZE);
    	coin.setFillColor(Color.YELLOW);
    	coin.setFilled(true);
    	return coin;
    	}
    
    
    
    
    //this method adds the coin to a platform list
    private void addToCoinsOnPlatformList(GOval coin) {
    	coinsOnPlatforms.add(coin);
    	}
    
    private void addToCoinsInLevelList(GOval coin) {
    	coinsInLevel.add(coin);
    }
    
    
    private void addCoinsForPlatforms(int number) {
    	coinsForPlatforms = number;
    }
    
    private void addCoinsForFloat(int number) {
    	coinsForFloat = number;
    }
    
    
    //spawns a list of coins centered to a list of platforms
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
    
    //spawns a list of coins to the screen
    //For now I have it spawn randomly within the screen
    private void spawnCoinsToScreen(ArrayList<GOval> coinList, int windowWidth, int windowHeight) {
        Random random = new Random();
        int i = 0; //used for testing

        for (GOval coin : coinList) {
            double x = random.nextInt(windowWidth - COIN_SIZE);
            double y = random.nextInt(windowHeight - COIN_SIZE);
            
            coin.setLocation(x, y);
            add(coin);
            
            System.out.println("Coins Floating " + i + " " + x + ", " + y); //used for testing.
            i++; //used for testing
        }
    }

    
    
    
    
 // Checks for player and coin collision
    private void checkCoinCollision() {
        // Check floating coins (iterate backward to avoid index errors)
        for (int i = coinsInLevel.size() - 1; i >= 0; i--) {
            GOval coin = coinsInLevel.get(i);
            if (player.getBounds().intersects(coin.getBounds())) {
                remove(coin); // Remove from screen
                coinsInLevel.remove(i); // Remove from list
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
        coinsRemainingText.setLabel("Coins Remaining: " + (coinsInLevel.size() + coinsOnPlatforms.size()));
    }
    
    
    
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
            add(platform);
        }
        return platforms;
    }

    
    
    
    
    
    
    
    // Handles key presses for movement
    @Override
    public void keyPressed(KeyEvent e) {
        // Move the player based on key pressed
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: // Move up
                playerVelocityY = -10;
                break;
            case KeyEvent.VK_A: // Move left
                playerVelocityX = -10;
                break;
            case KeyEvent.VK_S: // Move down
                playerVelocityY = 10;
                break;
            case KeyEvent.VK_D: // Move right
                playerVelocityX = 10;
                break;
                }
        }
    // Handles key release to stop movement
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: // Stop moving up
                playerVelocityY = 0;
                break;
            case KeyEvent.VK_A: // Stop moving left
                playerVelocityX = 0;
                break;
            case KeyEvent.VK_S: // Stop moving down
                playerVelocityY = 0;
                break;
            case KeyEvent.VK_D: // Stop moving right
                playerVelocityX = 0;
                break;
                }
        }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //move the player based on the velocity
        double newX = player.getX() + playerVelocityX;
        double newY = player.getY() + playerVelocityY;
        player.setLocation(newX, newY);
        
        //after each movement, check for collisions
        checkCoinCollision();
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
		new testCoin().start();
		}
}
