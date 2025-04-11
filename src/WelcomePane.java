import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class WelcomePane extends GraphicsPane{
	public WelcomePane(MainApplication mainScreen) {
		this.mainScreen = mainScreen;
	}
	
	@Override
	public void showContent() {
		addPicture();
		AddButton();
		AddButton1();
		AddButton2();
	}

	@Override
	public void hideContent() {
		for(GObject item : contents) {
			mainScreen.remove(item);
		}
		contents.clear();
	}
	
	private void addPicture(){
		GImage startImage = new GImage("start.png", 200, 100);
		startImage.scale(0.5, 0.5);
		startImage.setLocation((mainScreen.getWidth() - startImage.getWidth())/ 2, 70);
		
		contents.add(startImage);
		mainScreen.add(startImage);
	}
	
	// Add a button with "START" label using GCompound
	private void AddButton() {
		GImage moreButton = new GImage("CGB02-yellow_L_btn.png", 200, 400);
		moreButton.scale(0.3, 0.3);
		
		// Create a GLabel object with the text "START"
		GLabel buttonLabel = new GLabel("START LEVEL 0");
		// Set the font and color of the label
		buttonLabel.setFont("SansSerif-bold-18");
		buttonLabel.setColor(Color.WHITE);
		
		// create a GCompound to group the image and text together
		GCompound buttonCompound = new GCompound();
		// Add the moreButton image to the compound at position(0,0)
		buttonCompound.add(moreButton, 0, 0);
		
		// Calculate the horizontal center position for the label on the image
		double labelX = (moreButton.getWidth() - buttonLabel.getWidth()) / 2;
		// Calculate the vertical center position for the label. 
		double labelY =  (moreButton.getHeight() + buttonLabel.getAscent())/ 2;
		// Add the label to the compound at the computed position
		buttonCompound.add(buttonLabel,labelX, labelY);
		
		// Center the entire compound horizontally on the main screen at a fixed y-coordinate (400)
		double x = (mainScreen.getWidth() - buttonCompound.getWidth()) / 2;
		double y = 400;
		
		buttonCompound.setLocation(x,y);
		
		// Add the compound to the contents list and display it on the main screen
		contents.add(buttonCompound);
		mainScreen.add(buttonCompound);

	}
	
	private void AddButton1() {
		GImage moreButton1 = new GImage("CGB02-yellow_L_btn.png", 200, 400);
		moreButton1.scale(0.3, 0.3);
		
		GLabel buttonLabel = new GLabel("START LEVEL 1");
		buttonLabel.setFont("SansSerif-bold-18");
		buttonLabel.setColor(Color.WHITE);
		
		GCompound buttonCompound = new GCompound();
		buttonCompound.add(moreButton1, 0, 0);
		
		double labelX = (moreButton1.getWidth() - buttonLabel.getWidth()) / 2;
		double labelY = (moreButton1.getHeight() + buttonLabel.getAscent()) / 2;
		buttonCompound.add(buttonLabel, labelX, labelY);
		
		double x =  (mainScreen.getWidth() - buttonCompound.getWidth()) / 2;
		double y = 450;
		buttonCompound.setLocation(x, y);
		
		contents.add(buttonCompound);
		mainScreen.add(buttonCompound);
	}
	
	private void AddButton2() {
		GImage moreButton2 = new GImage("CGB02-yellow_L_btn.png", 200, 400);
		moreButton2.scale(0.3, 0.3);
		moreButton2.setLocation((mainScreen.getWidth() - moreButton2.getWidth())/ 2, 500);
		
		contents.add(moreButton2);
		mainScreen.add(moreButton2);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (mainScreen.getElementAtLocation(e.getX(), e.getY()) == contents.get(1)) {
			mainScreen.switchToDescriptionScreen();
		}
	}

}
