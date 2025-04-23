import java.awt.Color;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GOval;
import acm.graphics.GRectangle;
import acm.program.GraphicsProgram;

public class UI_Elements {
	
	private GraphicsProgram program;

	// Sets the GraphicsProgram context
    public void setProgram(GraphicsProgram program) {
        this.program = program;
    }
   
	
	private int coinsCollected(testCoin coin) {
		return coin.getCoinsCollected();
	}
	
	private GLabel coinsCollectedText;
	private GLabel coinsRemainingText;    
    private GLabel playerHP; 
    private GLabel doorText; 
    private GCompound gcompound;
    private GOval signal;
    private GLabel label;
	private GLabel healthText;
    
    //Responsible for creating UI in levels
    public void createUI(testCoin coin,Player player) {
    	UIbox();
    	createCoins(coin); 
    	createHP(player);
    	doorSignal();
    }
   
    //responsible for updating health, collected coins, and checking current door state.
    public void init( Door door,testCoin coins, Player player) {
    coinsCollectedText.setLabel(" " + coinsCollected(coins));
    coinsRemainingText.setLabel(" " + coins.coinsOnPlatforms.size());
    playerHP.setLabel(" " + player.getHP());
    	 
    	 //door state check
    if(door.isOpen()) {
       signal.setColor(Color.GREEN);;
       label.setLabel("Open");
       label.setFont("SansSerif-bold-12");
       label.setColor(Color.white);
       }
       else {
   	   label.setLabel("Close");
       }
      }
    
    //door signal and door text
    public void doorSignal() {
    	signal = new GOval(40,40);
 		signal.setLocation(15, 80);
 		signal.setFilled(true);
 		 signal.setColor(Color.red);
 		 
 	     label = new GLabel("close");
 		 label.setFont("SansSerif-bold-12");
 		 label.setColor(Color.white);
 		 
 		  gcompound = new GCompound();
 		 gcompound.add(signal,0,0); 
 		 gcompound.add(label,5, 25);
 	     gcompound.setLocation(80,160);
 		 program.add(gcompound); 
 		 
 		doorText = new GLabel("Door: ");
		doorText.setLocation(20, 185);
		  doorText.setFont("SansSerif-bold-16");
		  doorText.setColor(Color.white);
		program.add(doorText);
    }
    
    // creates coins and labels used GCompound to group label & coin together
    public void createCoins(testCoin coin) {
    	   coinsCollectedText = new GLabel("Coins Collected:" , 20, 40);
           coinsCollectedText.setFont("SansSerif-bold-14");
           coinsCollectedText.setColor(Color.white);
           program.add(coinsCollectedText);
           
           coinsRemainingText = new GLabel("Coins Remaining: " , 20, 80);
           coinsRemainingText.setFont("SansSerif-bold-14");
           program.add(coinsRemainingText); 
           coinsRemainingText.setColor(Color.white);
           
    	GOval coins = new GOval(30, 30);
		  coins.setFilled(true);
		  coins.setFillColor(Color.yellow);
  
    coinsCollectedText = new GLabel(" " + coin.getCoinsCollected());
    coinsCollectedText.setFont("SansSerif-bold-16");
    GCompound coinText = new GCompound(); 
    coinText.add(coins,0,0);
    coinText.add(coinsCollectedText, 5,20);
    coinText.setLocation(160,20); 
    program.add(coinText);
    
    GOval coins1 = new GOval(30, 30);
	  coins1.setFilled(true);
	  coins1.setFillColor(Color.yellow);

    coinsRemainingText = new GLabel(" " + coin.getCoinsCollected());
    coinsRemainingText.setFont("SansSerif-bold-16");
    GCompound coinText1 = new GCompound(); 
    coinText1.add(coins1,0,0);
    coinText1.add(coinsRemainingText, 5,20);
    coinText1.setLocation(160,60); 
    program.add(coinText1);
   
    }
 
    //Responsible for creating HP label and image
  public void createHP(Player player) {
	  healthText = new GLabel("Health:    " , 20, 116);
	   healthText.setFont("SansSerif-bold-14");
	   healthText.setColor(Color.white);
	   program.add(healthText);
	   
	  GImage hpImage = new GImage("Media/hp.png");
	  hpImage.scale(0.1);
	  hpImage.setLocation(78,89);
	  program.add(hpImage);
	  
	  playerHP = new GLabel(" " + player.getHP());
	  playerHP.setFont("SansSerif-bold-16");
	  playerHP.setLocation(90, 117);
	  program.add(playerHP);
	  
  }
  
  public void UIbox() {
	GImage UIimage = new GImage("Media/UI_Image.png");  
	UIimage.scale(0.9);
	UIimage.setLocation(0,0);
	program.add(UIimage);
  }
	  
	  
	
}
