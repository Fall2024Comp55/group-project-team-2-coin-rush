import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.graphics.GRectangle;
import acm.program.GraphicsProgram;

public class testCoin {
	public static final int COIN_SIZE = 20; //default coin size

    ArrayList<GOval> coinsOnPlatforms; //list of coins on platforms
  //  ArrayList<GOval> coinsOnFloat; //list of coins floating within the level

	private int coinCount; // might not need
	private int coinsForPlatforms; // Coins specifically for platforms
	//private int coinsForFloat; // Coins that are not tied to platforms but float in level
	private int coinsCollected = 0;
	
	private GLabel coinsCollectedText;
	private GLabel coinsRemainingText;    
	
    private GraphicsProgram program;
    
    // Constructor to assign data
    public testCoin(int numCoinsToAdd) {
        this.coinsOnPlatforms = new ArrayList<>();
        this.coinsForPlatforms = numCoinsToAdd;
        
        coinsOnPlatforms = generateCoins(numCoinsToAdd); // Automatically assigns the number of coins to be made and added to list
        }
    
    public void init() {
    	addCoinsToScreen();
    }

    public void setProgram(GraphicsProgram program) {
        this.program = program;
    }
    
    // Adds GOval coins for platforms to a list of coins that go on platforms
    private void addToCoinsOnPlatformList(GOval coin) {
    	this.coinsOnPlatforms.add(coin);
    	}
    // Adds GOval coins that are unassigned to any platform to a list of free roam floating list of coins 
    /*
    private void addToCoinsInLevelList(GOval coin) {
    	this.coinsOnFloat.add(coin);
    }
        private void addCoinsForFloat(int number) {
    	this.coinsForFloat = number;
    }
    */
    
    public int getCoinsCollected() {
    	return coinsCollected;
    }
    
    public ArrayList<GOval> getCoinsOnPlatforms() {
    	return coinsOnPlatforms;
    }
    
    
    // Uses createCoin() and a given integer to generate a list of coins and return said list
    private ArrayList<GOval> generateCoins(int numCoins) {
        ArrayList<GOval> coins = new ArrayList<>();
        for (int i = 0; i < numCoins; i++) {
            Random rand = new Random();
            int width = rand.nextInt(150) + 50; // Platform width between 50-200
            int x = rand.nextInt(MainApplication.WINDOW_WIDTH - width); // Ensure it stays inside the window
            int y = rand.nextInt(MainApplication.WINDOW_HEIGHT - 100) + 100; // Avoid spawning too high or low
            GOval coin = new GOval(COIN_SIZE, COIN_SIZE);
            coin.setFillColor(Color.YELLOW);
            coin.setFilled(true);
            coin.setLocation(x, y);
            coins.add(coin);
        }
        return coins;
    }
    
    // Specifically sets the location of a list of coins to the center of platforms of a list of platforms
    public void spawnCoinsToPlatforms(ArrayList<GOval> coinList, ArrayList<GRect> platforms) {
        if (platforms.size() <= 1  || coinList.isEmpty()) return;

        for (int i = 0; i < coinList.size(); i++) {
            GRect platform = platforms.get((i % (platforms.size() - 1)) + 1); //avoid out of bounds and 1st platform
            GOval coin = coinList.get(i);

            double coinLocationX = platform.getX() + (platform.getWidth() - coin.getWidth()) / 2; //spawns the coin centered on the top of the platform
            double coinLocationY = platform.getY() - coin.getHeight() - 5; //5px above the platform

            coin.setLocation(coinLocationX, coinLocationY); //sets coin to location
            System.out.println("Coin " + i + " spawned at: (" + coinLocationX + ", " + coinLocationY + ")"); //used for testing
        }
    }


    // These two functions essentially adds to screen
    private void addCoinsToScreen() {
        for (GOval coin : coinsOnPlatforms) {
            program.add(coin);
        }
    }    

    public void update(hitBox playerHitbox) {
        checkCoinCollision(playerHitbox);
    }
    
    // Checks for player and coin collision
    private void checkCoinCollision(hitBox playerHitbox) {
    	/*
        // Check floating coins (iterate backward to avoid index errors)
        for (int i = coinsOnFloat.size() - 1; i >= 0; i--) {
            GOval coin = coinsOnFloat.get(i);
            if (playerBounds.intersects(coin.getBounds())) {
                program.remove(coin); //remove from screen
                coinsOnFloat.remove(i); //remove from list
                coinsCollected++; //update count
            }
        }
        */

        // Check platform coins (iterate backward to avoid index errors)
        for (int i = coinsOnPlatforms.size() - 1; i >= 0; i--) {
            GOval coin = coinsOnPlatforms.get(i);
            if (playerHitbox.intersects(coin.getBounds())) {
                program.remove(coin); //remove from screen
                coinsOnPlatforms.remove(i); //remove from list
                coinsCollected++; //update count
            }
        }
    }
    
    
    /*
     *     // Generates randomly sized/placed list of platforms of a fixed size within the screen. The amount created is dictated by the given integer.
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
    */
}
