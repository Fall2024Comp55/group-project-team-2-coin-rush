import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.*;
import acm.program.GraphicsProgram;

public class Menu extends GraphicsPane{
//	public Menu(MainApplication mainScreen) {
//		this.mainScreen = mainScreen;
//	} 

private GraphicsProgram program;

public void setProgram(GraphicsProgram program) {
    this.program = program;
   
}

	public static final int WINDOW_WIDTH = 300;
	public static final int WINDOW_HEIGHT = 400;	
	
	private GLabel label;
	private GImage button;
	private GLabel label1;
    private GImage button2;
	private GLabel button2label ; 
	
	    private GImage menuImage;
	    private GCompound button1Compound;
	   private GCompound button2Compound;
	    private GCompound deathMenuCompound;

	    private GLabel winLabel;
		private GImage nextLevelButton;
	    private GImage winImage;
	    private GLabel levelLabel; 
	    
	    private GCompound winMenuCompound;
	    private GCompound winButtonCompound;
	    
		private boolean deathMenu;



    
	//display content
	public void showContents() {
		
		createDeathMenu();
		deathMenu = true; 
		
	}
	
public void showWinMenu() {	
	createWinMenu();
		
	}

	private void createDeathMenu() {
	    // Create background image for death menu
	    menuImage = new GImage("Media/death menu.png");
	    menuImage.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	    

	    // Create a GCompound to hold everything in the menu
	     deathMenuCompound = new GCompound();

	    // Add background image to the compound
	    deathMenuCompound.add(menuImage, 0, 0); 

	    // Create and add the label (ensure this is initialized)
	    label = new GLabel("You Died");
	    label.setFont("SansSerif-bold-30");
	    label.setColor(Color.black);
	    deathMenuCompound.add(label, WINDOW_WIDTH/2 - label.getWidth()/2, 150); // Position of label

	    // Create and add the restart button (ensure this is initialized)
	    createButton1();  // This will initialize button1Compound properly
	    deathMenuCompound.add(button1Compound, WINDOW_WIDTH/2 - button1Compound.getWidth()/2, 200); // Position of button 1

	    createButton2();  // This will initialize button2Compound properly
	    deathMenuCompound.add(button2Compound, WINDOW_WIDTH/2 - button2Compound.getWidth()/2, 250); // Position of button 2
 
	    double x = (program.getWidth() - menuImage.getWidth()) / 2;
	    double y = (program.getHeight() - menuImage.getHeight()) / 2;
	    deathMenuCompound.setLocation(x,y);
	    // Finally, add the compound to the program's canvas
	    program.add(deathMenuCompound);
	}
	
	private void createWinMenu() {
	    // Create background image for death menu
		winImage = new GImage("Media/death menu.png");
		winImage.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	    

	    // Create a GCompound to hold everything in the menu
		winMenuCompound = new GCompound();

	    // Add background image to the compound
		winMenuCompound.add(winImage, 0, 0); 

	    // Create and add the label (ensure this is initialized)
		winLabel = new GLabel("You Won");
		winLabel.setFont("SansSerif-bold-30");
		winLabel.setColor(Color.black);
		winMenuCompound.add(winLabel, WINDOW_WIDTH/2 - winLabel.getWidth()/2, 150); // Position of label

	    // Create and add the restart button (ensure this is initialized)
	    createWinButton();  // This will initialize button1Compound properly
	    winMenuCompound.add(winButtonCompound, WINDOW_WIDTH/2 - winButtonCompound.getWidth()/2, 200); // Position of button 1

	    
	    double x = (program.getWidth() - winImage.getWidth()) / 2;
	    double y = (program.getHeight() - winImage.getHeight()) / 2;
	    winMenuCompound.setLocation(x,y);
	    // Finally, add the compound to the program's canvas
	    program.add(winMenuCompound);
	}
	// label
    public void deathLabel() {  	
    	 label = new GLabel("You Died"); 
    	label.setFont("SansSerif-bold-30");

    	label.setColor(Color.black);
    	}
    
    
    // restart button
	public void createButton1() {
	   button = new GImage("Media/CGB02-yellow_L_btn.png"); 
		button.scale(0.2);

		 label1 = new GLabel("R for Restart");
		label1.setFont("SansSerif-bold-15");
		label1.setColor(Color.black); 
		
        // used GCompound to group picture and image together. 
	     button1Compound = new GCompound(); 
	     button1Compound.add(button,0,0); 
	    
       //in the middle of button
		double x = (button.getWidth() - label1.getWidth())/2;
		double y =(button.getHeight() - 9); //9 pixels up

	   // contents.add(button1Compound);
		button1Compound.add(label1,x, y); 
		
	}
	
	//same for button 2 exit button.
	public void createButton2() {
		 button2 = new GImage("Media/CGB02-yellow_L_btn.png"); 
		button2.scale(0.2);

		 button2label = new GLabel("E for Exit");
		 button2label.setFont("SansSerif-bold-15");
		 button2label.setColor(Color.black); 
		
		button2Compound = new GCompound(); 
		button2Compound.add(button2,0,0); 
	    
		 
		double x = (button2.getWidth() - button2label.getWidth())/2;
		double y =(button2.getHeight() - 9);
	
		
		//contents.add(button2Compound);
		button2Compound.add(button2label,x, y);
		
	}
	
	public void createWinButton() {
		nextLevelButton = new GImage("Media/CGB02-yellow_L_btn.png"); 
		 nextLevelButton.scale(0.2);

		 levelLabel = new GLabel("N for Level 1");
		 levelLabel.setFont("SansSerif-bold-15");
		 levelLabel.setColor(Color.black); 
		
		 winButtonCompound = new GCompound(); 
		 winButtonCompound.add(nextLevelButton,0,0); 
	    
		 
		double x = (nextLevelButton.getWidth() - levelLabel.getWidth())/2;
		double y =(nextLevelButton.getHeight() - 9);
	
		
		//contents.add(button2Compound);
		winButtonCompound.add(levelLabel,x, y);
	}
	@Override
	public void mouseClicked(MouseEvent e) {

	}
	
	

	
	public static void main(String[] args) {
	 
	}

	
	}
