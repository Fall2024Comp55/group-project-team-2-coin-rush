import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.*;
import acm.program.GraphicsProgram;

public class Menu extends GraphicsProgram{
	public static final int WINDOW_WIDTH = 400;
	public static final int WINDOW_HEIGHT = 500;		

	
	//display content
	public void showContent() {
		createDeathMenu();
		createButton1();
		createButton2();
	}
	//Creates death Menu
	public void createDeathMenu() {
		GRect deathMenue = new GRect(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
		deathMenue.setFilled(true);

       //it is transparent in color
		deathMenue.setColor(new Color(0,0,0,80));
		add(deathMenue); 
		deathLabel();
	}
	
	// label
    public void deathLabel() {
    	GLabel label = new GLabel("You Died"); 
    	label.setFont("SansSerif-bold-30");

    	label.setColor(Color.black);
    	add(label,130,150);
    }
    
    // restart button
	public void createButton1() {
		GImage button = new GImage("Media/CGB02-yellow_L_btn.png"); 
		button.scale(0.2);

		GLabel label = new GLabel("Restart");
		label.setFont("SansSerif-bold-18");
		label.setColor(Color.white); 
		
        // used GCompound to group picture and image together. 
	    GCompound compound = new GCompound(); 
	    compound.add(button,0,0); 
	    
       //in the middle of button
		double x = (button.getWidth() - label.getWidth())/2;
		double y =(button.getHeight() - 9); //9 pixels up
	
		compound.add(label,x, y); 
		
		//sets the location of the gCompound
		double x2 = (WINDOW_WIDTH - compound.getWidth()) / 2;
		double y2 = 200;
		
		compound.setLocation(x2,y2);
		add(compound); 
		
	}
	
	//same for button 2 exit button.
	public void createButton2() {
		GImage button = new GImage("Media/CGB02-yellow_L_btn.png"); 
		button.scale(0.2);

		GLabel label = new GLabel("Exit");
		label.setFont("SansSerif-bold-18");
		label.setColor(Color.white); 
		
	    GCompound compound = new GCompound(); 
	    compound.add(button,0,0); 
	    
		 
		double x = (button.getWidth() - label.getWidth())/2;
		double y =(button.getHeight() - 9);
	
		compound.add(label,x, y);
		
		double x2 = (WINDOW_WIDTH - compound.getWidth()) / 2;
		double y2 = 250;
		
		compound.setLocation(x2,y2);
		add(compound); 
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	
	}
	
	public void init() {
		setSize(WINDOW_WIDTH,WINDOW_HEIGHT); 
	}

	@Override
	public void run() {
		showContent();		
	}
	
	public static void main(String[] args) {
	    new Menu().start();
	}

	
}
