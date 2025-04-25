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
	private boolean deathMenu = false; 
	
	    private GImage menuImage;
	    private GCompound button1Compound;
	    private GCompound button2Compound;
	    private GCompound deathMenuCompound;


    
	//display content
	public void showContents() {
		
		createDeathMenu();
		deathMenu = true; 
		
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

//	//Creates Transparent death Menu OPTIONAL
//	public void createDeathMenu() {
//		GRect deathMenue = new GRect(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
//		deathMenue.setFilled(true);
//
//       //it is transparent in color
//		deathMenue.setColor(new Color(0,0,0,80));
//		add(deathMenue); 
//		deathLabel();
//	}
	
	
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

		 label1 = new GLabel("Restart");
		label1.setFont("SansSerif-bold-18");
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

		 button2label = new GLabel("Exit");
		 button2label.setFont("SansSerif-bold-18");
		 button2label.setColor(Color.black); 
		
		button2Compound = new GCompound(); 
		button2Compound.add(button2,0,0); 
	    
		 
		double x = (button2.getWidth() - button2label.getWidth())/2;
		double y =(button2.getHeight() - 9);
	
		
		//contents.add(button2Compound);
		button2Compound.add(button2label,x, y);
		
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {

	}
	
	

	
	public static void main(String[] args) {
	 
	}

	
	}
