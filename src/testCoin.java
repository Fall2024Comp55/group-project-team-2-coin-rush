import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.Timer;
import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.program.GraphicsProgram;

public class testCoin extends GraphicsProgram {
	public static final int WINDOW_SIZE = 500; //default window for now
	
    ArrayList<GOval> coinsInLevel; //list of coins within the level
	public static final int COIN_SIZE = 20; //default coin size
	private GOval coin;
	private int coinCount;
	private int coinsCollected = 0;
	private GOval player;
	
	private GLabel coinsCollectedText;
	private GLabel coinsRemainingText;
	
	private Timer timer;
    
    // Player movement variables
    private double playerVelocityX = 0;
    private double playerVelocityY = 0;
	
	public void run() {
		//generates a player
		player = new GOval(50, 50, 30, 30); //spawn location (x, y, width, height)
		player.setColor(Color.RED);
		player.setFilled(true);
		add(player);
		
		coinsInLevel = new ArrayList<GOval>(); //creates a list of coins
		coinCount = 3; //manages how many coins to add and how many are left remaining
		for (int i = 0; i < coinCount; i++) {
		addCoinToList(createCoin());
		spawnCoins(coinsInLevel);
		}
		
		//displays the number of coins in the level as well as the number of coins collected
		coinsCollectedText = new GLabel("Coins Collected: " + coinsCollected, 5, 15);
		add(coinsCollectedText);
		coinsRemainingText = new GLabel("Coins Remaining: " + coinsInLevel.size(), 5, 30);
		add(coinsRemainingText);
				
		addKeyListeners();
		
        //a timer used to call actionPerformed at regular intervals
        timer = new Timer(50, this); //50 = milliseconds
        timer.start();
	}
	
	//this method creates a single coin
    private GOval createCoin() {
        	coin = new GOval(COIN_SIZE, COIN_SIZE);
        	coin.setFillColor(Color.YELLOW);
        	coin.setFilled(true);
        	return coin;
    }
    
    //this method adds the coin to a list
    private void addCoinToList(GOval coin) {
    	coinsInLevel.add(coin);
    }
    
    //spawns the list of coins.
    //for now I have them spawn next to each other. maybe change it so that it spawns relative to a list of platforms.
    private void spawnCoins(ArrayList<GOval> coinList) {
        int coinLocationX = WINDOW_SIZE / 3;
        int coinLocationY = WINDOW_SIZE / 3;
        for (int i = 0; i < coinList.size(); i++) {
            coinList.get(i).setLocation(coinLocationX, coinLocationY);
            add(coinList.get(i));
            coinLocationX += coinList.get(i).getWidth() + 10; //10 is the space between coins based on its diameter
        }
    }
    
 //checks for player and coin collision
    private void checkCoinCollision() {
    	//iterate over the coins in the list
        for (int i = 0; i < coinsInLevel.size(); i++) {
            GOval coin = coinsInLevel.get(i);
            
            //checks if the player's GOval intersects with the coin's GOval
            if (player.getBounds().intersects(coin.getBounds())) {
                remove(coin); //remove coin from screen
                coinsInLevel.remove(i); //remove the coin from list
                
                coinsCollected++;
                i--; //adjust the index since the list shrinks after removal
            }
        }
        
        //update the UI
        coinsCollectedText.setLabel("Coins Collected: " + coinsCollected);
        coinsRemainingText.setLabel("Coins Remaining: " + coinsInLevel.size());
        System.out.println(coinsInLevel.size()); //used to check if properly removing from list
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
	
	public void init() {
		setSize(WINDOW_SIZE, WINDOW_SIZE);
		}
	
	public static void main(String[] args) {
		new testCoin().start();
	}
}
