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
    	UILevel1();
    	createCoins(coin); 
    	createHP(player);
    	doorSignal();
    }
    
    public void createUILevel2(testCoin coin,Player player) {
    	UILevel2();
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
 		signal.setFilled(true);
 		 signal.setColor(Color.red);
 		 
 	     label = new GLabel("close");
 		 label.setFont("SansSerif-bold-12");
 		 label.setColor(Color.white);
 		 
 		  gcompound = new GCompound();
 		 gcompound.add(signal,0,0); 
 		 gcompound.add(label,5, 25);
 	     gcompound.setLocation(540,5);
 		 program.add(gcompound); 
 		 
 		doorText = new GLabel("Door: ");
		doorText.setLocation(480, 30);
		  doorText.setFont("SansSerif-bold-16");
		  doorText.setColor(Color.white);
		program.add(doorText);
    }
    
    // creates coins and labels used GCompound to group label & coin together
    public void createCoins(testCoin coin) {
    	   coinsCollectedText = new GLabel("Coins Collected:" , 120, 30);
           coinsCollectedText.setFont("SansSerif-bold-14");
           coinsCollectedText.setColor(Color.white);
           program.add(coinsCollectedText);
           
           coinsRemainingText = new GLabel("Coins Remaining: " , 300, 30);
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
    coinText.setLocation(250,10); 
    program.add(coinText);
    
    GOval coins1 = new GOval(30, 30);
	  coins1.setFilled(true);
	  coins1.setFillColor(Color.yellow);

    coinsRemainingText = new GLabel(" " + coin.getCoinsCollected());
    coinsRemainingText.setFont("SansSerif-bold-16");
    GCompound coinText1 = new GCompound(); 
    coinText1.add(coins1,0,0);
    coinText1.add(coinsRemainingText, 3,20);
    coinText1.setLocation(440,10); 
    program.add(coinText1);
   
    }  
 
    //Responsible for creating HP label and image 
  public void createHP(Player player) {
	  healthText = new GLabel("Health:    " , 10, 30);
	   healthText.setFont("SansSerif-bold-14");
	   healthText.setColor(Color.white);
	   program.add(healthText);
	   
	  GImage hpImage = new GImage("Media/hp.png");
	  hpImage.scale(0.1);
	  
	  playerHP = new GLabel(" " + player.getHP());
	  playerHP.setFont("SansSerif-bold-16");
	  
	  GCompound hp = new GCompound(); 
	    hp.add(hpImage,0,0);
	    hp.add(playerHP, 12,30);
	    hp.setLocation(65,2); 
	    program.add(hp);
  }
  
  public void UILevel1() {
	GImage UIimage = new GImage("Media/UI_Image.png");  
	UIimage.setLocation(2,0);
	UIimage.scale(5.0, 0.2);
	program.add(UIimage);
  }
	  
  public void UILevel2() {
		GImage UIimage = new GImage("Media/UI_Level2.png");  
		UIimage.scale(5.0, 0.2);
		UIimage.setLocation(0,0);
		program.add(UIimage);
  }
	
}
